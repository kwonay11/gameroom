<template>

   <div  v-if="session">
      <div class='participation'>
         <div id="video-container" class="col-lg-12">
         <!-- <div id="video-container" class=""> -->
            <!-- 메인 스트리머 -->
            <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
            <!-- 참가자들 -->
            <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
         </div>
      </div>
      <div class='row p-4'>
         <div id="main-video" class="col-md-8">
            <div class="player">
               <user-video :stream-manager="mainStreamManager"/>
               <div class="answer">
                  <input class="input_answer" placeholder="답을 입력해주세요." type="text" />
               </div>
            </div>
         </div>
         <div class="col-md-4">
            <div>
               <div class="link">
               <img style="width:5%" src="@/assets/link.png" alt="link">
               </div>
               <span class="buttons">
               <img class="m-4" style="width:11%" src="@/assets/microphone (3).png" alt="mike">
               </span>
               <span class="buttons">
               <img class="m-4" style="width:11%" src="@/assets/video-player.png" alt="video">
               </span>
               <span class="buttons">
               <img class="m-4" style="width:11%" src="@/assets/delete.png" alt="delete">
               </span>
               <span class="buttons">
               <img class="m-4" style="width:11%" src="@/assets/question.png" alt="tutorial">
               </span>
            </div>
            <div id='chat-area'>
               <div v-for="val in chat" v-bind:key="val.id">
               <div v-if="val.user === myUserNick" class="mychat">
                  {{ val.text }}
               </div>
              
               <div v-else class="otherchat">
                  {{ val.user }} : {{ val.text }}

               </div>
            
               </div>
         
            </div>
       
            <div class='chat_input'>
               <!-- <textarea v-model='chattings' @keyup.enter='sendMessage' placeholder="채팅" type="text" class="message-input"></textarea> -->
               <input v-model='chattings' @keyup.enter='sendMessage' placeholder="input message.." type="text" class="message_input"/>
               <button :disabled="!chattings" @click='sendMessage' type="submit" class="message_submit">Send</button>

            </div>
         </div>
      </div>
   </div>

</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/UserVideo';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
   name: 'App',

   components: {
      UserVideo,
   },

   data () {
      return {
         OV: undefined,
         session: undefined,
         mainStreamManager: undefined,
         publisher: undefined,
         subscribers: [],

         mySessionId: null,
         myUserName: '',
         myUserNick: '',
         canJoin: null,

         chattings: '',
         // chatting_user: '',
         chat: [],
         chatHeight: "33vh"
   }
   },
   watch: {

      chat() {
         setTimeout(() => {
         var chatDiv = document.getElementById("chat-area");
         chatDiv.scrollTo({
            // document.body.scrollTop = document.body.scrollHeight;
            top: chatDiv.scrollHeight - chatDiv.clientHeight,
            behavior: 'smooth'
         })
         }, 50);
      },
   },
   created: function () {


      // 방 ID 인거 같고
      this.mySessionId = this.$route.params.roomid
      this.myUserName = this.$store.state.id
      this.myUserNick = this.$store.state.userData.nickname
      // console.log(this.mySessionId)
      // console.log(this.myUserName)
      this.OV = new OpenVidu();
         // --- Init a session ---
         this.session = this.OV.initSession();

         // --- Specify the actions when events take place in the session ---

         // On every new Stream received...
         this.session.on('streamCreated', ({ stream }) => {
            const subscriber = this.session.subscribe(stream);
            this.subscribers.push(subscriber);
         });

         // On every Stream destroyed...
         this.session.on('streamDestroyed', ({ stream }) => {
            const index = this.subscribers.indexOf(stream.streamManager, 0);
            if (index >= 0) {
               this.subscribers.splice(index, 1);
            }
         });

         // On every asynchronous exception...
         this.session.on('exception', ({ exception }) => {
            console.warn(exception);
         });
         axios.defaults.headers.common["Authorization"] = `Bearer ${this.$store.state.accessToken}`;
         // --- Connect to the session with a valid user token ---
         console.log('room확인')
         axios.get(`${SERVER_URL}/conferences/${this.$route.params.roomid}`)
         .then((res) => {
            console.log(res.status)
            if(res.status == 200) {
               this.canJoin = true;
            }
            else {
               this.canJoin = false;
            }
            if(!this.canJoin)
          return;
        })
        .catch(() => {
               this.$router.push({ name: 'MainPage' })
          this.canJoin = false;
        });
      
         // 'getToken' method is simulating what your server-side should do.
         // 'token' parameter should be retrieved and returned by your own backend
         this.getToken(this.mySessionId)
         .then(token => {
            this.session.connect(token, { clientData: this.myUserNick })
               .then(() => {

                  // --- Get your own camera stream with the desired properties ---

                  let publisher = this.OV.initPublisher(undefined, {
                     audioSource: undefined, // The source of audio. If undefined default microphone
                     videoSource: undefined, // The source of video. If undefined default webcam
                     publishAudio: true,     // Whether you want to start publishing with your audio unmuted or not
                     publishVideo: true,     // Whether you want to start publishing with your video enabled or not
                     resolution: '640x480',  // The resolution of your video
                     frameRate: 30,         // The frame rate of your video
                     insertMode: 'APPEND',   // How the video is inserted in the target element 'video-container'
                     mirror: false          // Whether to mirror your local video or not
                  });

                  this.mainStreamManager = publisher;
                  this.publisher = publisher;

                  // --- Publish your stream ---

                  this.session.publish(this.publisher);
               })
               .catch(error => {
                  console.log('There was an error connecting to the session:', error.code, error.message);
               });
         });

         window.addEventListener('beforeunload', this.leaveSession)

         // 내가 입력한 채팅
         this.session.on('signal:my-chat', (event) => {
            console.log('여기')
            console.log(event)
            // this.chatting_user = event.from.data["clientData"]
            console.log('내가 입력한 내용')
            console.log(event.data); // Message
            const content = event.data.slice(1, -1) // Message
            console.log('입력한 사람')
            console.log(event.from.data); // Message
            const chatting_user = event.from.data.slice(15,-2)
            console.log(chatting_user)
            

            
            this.chat.push({
               user : chatting_user,
               text : content
            });

      
         });


      },



      methods: {

      sendMessage (){
            this.session.signal({
                data: JSON.stringify(this.chattings),
                type: 'my-chat'
            })
            .then(() => {
                this.chattings = '';
                console.log('Message success');
            })
            .catch(error => {
                console.log(error);
            })
        },


      leaveSession () {
         // --- Leave the session by calling 'disconnect' method over the Session object ---
         if (this.session) this.session.disconnect();

         this.session = undefined;
         this.mainStreamManager = undefined;
         this.publisher = undefined;
         this.subscribers = [];
         this.OV = undefined;

         window.removeEventListener('beforeunload', this.leaveSession);
      },

      updateMainVideoStreamManager (stream) {
         if (this.mainStreamManager === stream) return;
         this.mainStreamManager = stream;
      },

      /**
       * --------------------------
       * SERVER-SIDE RESPONSIBILITY
       * --------------------------
       * These methods retrieve the mandatory user token from OpenVidu Server.
       * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
       * the API REST, openvidu-java-client or openvidu-node-client):
       *   1) Initialize a Session in OpenVidu Server   (POST /openvidu/api/sessions)
       *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
       *   3) The Connection.token must be consumed in Session.connect() method
       */

      getToken (mySessionId) {
         return this.createSession(mySessionId)
         .then(sessionId => this.createToken(sessionId));
      },

      // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
      createSession (sessionId) {
         return new Promise((resolve, reject) => {
            axios
               .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
                  customSessionId: sessionId,
               }), {
                  auth: {
                     username: 'OPENVIDUAPP',
                     password: OPENVIDU_SERVER_SECRET,
                  },
               })
               .then(response => response.data)
               .then(data => resolve(data.id))
               .catch(error => {
                  if (error.response.status === 409) {
                     resolve(sessionId);
                  } else {
                     console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
                     if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
                        location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
                     }
                     reject(error.response);
                  }
               });
         });
      },

      // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
      createToken (sessionId) {
         return new Promise((resolve, reject) => {
            axios
               .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
                  auth: {
                     username: 'OPENVIDUAPP',
                     password: OPENVIDU_SERVER_SECRET,
                  },
               })
               .then(response => response.data)
               .then(data => resolve(data.token))
               .catch(error => reject(error.response));
         });
      },
   },

}

</script>

<style>

.input_answer {
   outline: none !important;
  color: #000000;
  height: 86px;
  display: block;
  font-size: 1.2rem;
  letter-spacing: 0.15rem;
  padding: 20px;
  width: 100%;
  margin-bottom:10px;
}
.answer{
display: inline-block;
width: 30vw;
height: 80px;
/* margin-left: 10vw; */
position: relative;
background: rgba(20, 17, 151, 0.47);
border: 1px solid #FFFFFF;
box-sizing: border-box;
border-radius: 20px;
}

.link {
   right:-40%;
   cursor: pointer;
   position: relative;
   

}
.buttons{
  cursor: pointer;
  position: relative;
}
.participation {
  margin: 0 2.2vw;
  padding: 2.5vh;
  /* width: 95vw; */
   /* height: 22vh; */
   /* text-align: justify; */
  border: 3px solid #ffa500;
   display: flex;
   flex-direction: row;
   /* align-items: center;
   justify-content: space-around;   */
}

.player {
   border: 3px solid white;
   border-radius:20px;
   align-items: center;
}
#video-container video {
   /* position: relative; */
   float: left;
   width: 16%;
   margin-left:0.6%;
   border:4px solid;
   border-color:rgb(255, 230, 0);
   /* cursor: pointer; */
   /* margin:  2%;  */
   /* margin-left: 5%; */
   display: flex;
   align-items: center;
   justify-content: space-around;
}

#video-container video + div {
  
  text-align: center;
  /* line-height: 75px; */
   float: left;
   width: 28%;
   position: relative;
   margin-left:-28%;
   /* display: flex; */
   /* justify-content: space-around; */
}

#video-container p {
   display: inline-block;
   background: #f8f8f8;
   padding-left: 5px;
   padding-right: 5px;
   color: #777777;
   font-weight: bold;
   border-bottom-right-radius: 4px;
}

video {
   margin-top:2.5vh;
   /* 맨 아래에 나오는 카메라화면 */
   width: 45%;
   height: auto;

}

#main-video p {
   /* position: absolute; */
   display: inline-block;
   background: #f8f8f8;
   padding-left: 5px;
   padding-right: 5px;
   font-size: 22px;
   color: #777777;
   font-weight: bold;
   border-bottom-right-radius: 4px;
}
.chat_input {
   margin-top: 6px;
   border: 3px solid rgb(255, 255, 255);
   border-radius:20px;
   flex: 0 1 40px;
   width: 100%;
   background: rgba(0, 0, 0, 0.3);
   padding: 20px;
   position: relative;
}
#chat-area {
   padding:5px;
   overflow-y: scroll;
   border: 3px solid rgb(255, 255, 255);
   border-radius:20px;
   align-items: center;
   height: 34vh;
   background: rgba(0, 0, 0, 0.5);
   color:white;
 
}
#chat-area::-webkit-scrollbar {
  width: 8px; 
  height: 8px;
 
}
#chat-area::-webkit-scrollbar-track {
   background: #3f3150;
   border-radius: 20px;
}
#chat-area::-webkit-scrollbar-corner {
   background: #3f3150;
   border-radius: 20px;
  
}
#chat-area::-webkit-scrollbar-thumb {
   background:  #b0a2c8;
   border-radius: 20px;
}
#chat-area::-webkit-scrollbar-button {
  background-color: #dccbe0;
 border-radius: 20px;
}
.mychat{
   position: relative;
   float: right;
   display: block;
   color: #fff;
   text-align: right;
   background: linear-gradient(120deg, #df80bf, #56136b);
   border-radius: 10px 10px 0 10px;
   margin-bottom:10px;
   margin-left:30px;
   margin-right: 10px;
   padding:5px;
   /* 한줄씩 나오는거 */
   clear: both;

}
/* 말풍선 */
.mychat:after{
   content: '';
	position: absolute;
	bottom: 0;
	left: 97%;
	width: 0;
	height: 0;
	border: 5px solid transparent;
	border-top-color: #56136b;
	border-bottom: 0;
	border-right: 0;
	margin-left: -2.5px;
	margin-bottom: -5px;
}


.otherchat{
   position: relative;
   float: left;
   display: block;
   color: #fff;
   text-align: left;
   background: linear-gradient(120deg, #4f2fc2,#24a89d);
   border-radius: 10px 10px 10px 0px;
   margin-bottom:10px;
   margin-left:8px;
   margin-right: 30px;
   padding:5px;
   clear: both;


}
/* 말풍선 */
.otherchat:after{
   content: '';
	position: absolute;
	bottom: 0;
	left: 3%;
	width: 0;
	height: 0;
	border: 5px solid transparent;
	border-top-color: #4f2fc2;
	border-bottom: 0;
	border-left: 0;
	margin-left: -2.5px;
	margin-bottom: -5px;
}
.message_input{
   background: none;
  outline: none !important;
  resize: none;
  color: white;
  font-size: 16px;
  height: 25px;
  margin: 0;
  padding-right: 10px;
  width: 300px;
}
.message_submit{
   position: absolute;
  z-index: 1;
  right: 20px;
  color: #fff;
  border: none;
  background: #401d77;
  font-size: 15px;
  text-transform: uppercase;
  line-height: 1;
  padding: 6px 10px;
  border-radius: 50px;
  outline: none !important;
  transition: background 0.1s ease;

}
.message_submit:hover {
  background: #8647eb;
}



</style>
