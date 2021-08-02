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
      <div class='row m-4'>
         <div id="main-video" class="col-md-8">
            <div class="player">
               <user-video :stream-manager="mainStreamManager"/>
               <div class="answer"><input class="input_answer" placeholder="답을 입력해주세요." type="text" /></div>
            </div>
         </div>
         <div class="col-md-4">
            <div class='player'>
               <div class="link">
               <img style="width:8%" src="@/assets/link.png" alt="link">
               </div>
               <span class="buttons">
               <img class="m-4" style="width:13%" src="@/assets/microphone (3).png" alt="mike">
               </span>
               <span class="buttons">
               <img class="m-4" style="width:13%" src="@/assets/video-player.png" alt="video">
               </span>
               <span class="buttons">
               <img class="m-4" style="width:13%" src="@/assets/delete.png" alt="delete">
               </span>
               <span class="buttons">
               <img class="m-4" style="width:13%" src="@/assets/question.png" alt="tutorial">
               </span>
            </div>
            <div class='player'>
               채팅
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
         canJoin: null,
      }
   },
   created: function () {
      this.mySessionId = this.$route.params.roomid
      this.myUserName = this.$store.state.id
      console.log(this.mySessionId)
      console.log(this.myUserName)
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
         this.getToken(this.mySessionId).then(token => {
            this.session.connect(token, { clientData: this.myUserName })
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
      },
      methods: {

      

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
         return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
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
   }
}

</script>

<style>

.input_answer {
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
  
width: 485px;
height: 87px;
margin: 0% 29% 1% 29%;

background: rgba(20, 17, 151, 0.47);
border: 1px solid #FFFFFF;
box-sizing: border-box;
/* border-radius: 20px; */
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
   border: 3px solid #ffa500;
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


</style>
