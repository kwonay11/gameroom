<template>
   <div  v-if="session">
      <h5 style="color:white" class="card1">
         방제목 : {{ roominfo.title }}  
         게임 종류 : {{ roominfo.gameName }}  
         최대 인원 : {{ roominfo.maxUser}}
      </h5>
   <!-- <div  > -->
      <!-- 참가자가 나오는 부분 -->
      <div class='participation'>
         <div id="video-container" class="col-lg-12">
         <!-- <div id="video-container" class=""> -->
            <!-- 나 -->
            <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
            <!-- 나 빼고 나머지 참가자들 -->
            <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
         </div>
      </div>


      <!-- 메인 화면 -->
      <div class='row p-4'>
         <div class="col-md-8">
            <!-- 크게 보이는 화면 -->
            <div class="player">
                  <!-- 노래방 -->
                  <button class="card" @click="song">노래방</button>
                  <div  v-if="song_visible === true">
                     <Song :mainStreamManager="mainStreamManager" :publisher='publisher'/>
                  </div>


                  <!-- 크게 보이는 화면 -->
                  <div class="main_box" v-else>
                     <!-- <user-video :stream-manager="mainStreamManager"/> -->

                     <!-- 시작하기 버튼 -->
                     <div>
                        <div v-if="!start && !ready">
                           <button style='margin: 20vh' @click="game_start">시작</button>
                           
                        </div>

                        <div v-else-if="!ready && start ">
                           <Start />
                        </div>
                        <div v-else>
                           <!-- 나 -->
                           <!-- 메인스트리머가 = 나 -->
                           <user-video :stream-manager="mainStreamManager"/>
                        </div>

                     </div>
                  </div>

                  <!-- 답 입력창 -->
                  <div>
                     <div class="answer">
                        <input class="input_answer" placeholder="답을 입력해주세요." type="text" />
                     </div>
                  </div>

            </div>
         </div>

         <!--  버튼 -->
         <div class="col-md-4">
            <Button :publisher="publisher" :roominfo="roominfo" />
            <Chatting :session="session"/>
         </div>
      </div>
      <!-- </div> -->
   </div>

</template>

<script>
import axios from 'axios';
// import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/UserVideo';
import Chatting from '@/components/GameRoom/Chatting';
import Button from '@/components/GameRoom/Button';
import Start from '@/components/GameRoom/Start';
import Song from '@/components/Game/Song/Song';
import { video } from '@/mixins/video'

axios.defaults.headers.post['Content-Type'] = 'application/json';

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// const OPENVIDU_SERVER_SECRET = "MY_SECRET";
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
   name: 'Room',

   components: {
      UserVideo,
      Chatting,
      Button,
      Song,
      Start,     
      
   },

    data() {
        return {
            // song_visible: false,
            // OV: undefined,
            // session: undefined,
            // mainStreamManager: undefined,
            // // 이게 나
            // publisher: undefined,
            // // 이게 나를 뺀 방에 들어와있는 나머지 사람들
            // subscribers: [],

            // mySessionId: null,
            // myUserName: '',
            // myUserNick: '',
            // canJoin: null,

            // roominfo: {},

            start: false,
            ready: false,
            aa: false

        }
    },
   created() {
      

    axios.get(`${SERVER_URL}/conferences/info/${this.mySessionId}`)
      .then((res) => {
        this.roominfo = res.data
      })

  },
  methods: {
      song(){
         this.song_visible = !this.song_visible;
      },
      game_start() {
         this.start = true
         setTimeout(() => {
            this.ready = true;
            console.log('ready')
            console.log(this.ready)
            console.log(this.start)
         }, 3500);
         console.log('이건언제됨')
         this.mainStreamManager = this.publisher;


      },
   },

   mixins: [video]

}

</script>

<style >
.player {
   /* 젤 크게 나오는 메인스트리머 화면 */
   /* border: 0.5px solid white; */
   padding-top:3vh;
   border-radius:20px;
   align-items: center;
   
}
.main_box {
   width: 60%;
   height: 48vh;
   background: rgba(192, 192, 199, 0.47);
   /* border: 3px solid white; */
   
   border-radius:20px;
   margin: 0 auto 2.5vh;
   display:flex;
}
.card1{
   width: 100%;
   float:right;
   background: rgba(104, 102, 163, 0.47);
   color:white;
}

.input_answer {
   outline: none !important;
   color:white;
   /* height: 83px; */
   display: block;
   font-size: 1rem;
   letter-spacing: 0.15rem;
   padding: 2vh;
   width: 100%;
 
}
.answer{
display: inline-block;
width: 20vw;
height: 60px;
/* margin-left: 10vw; */
position: relative;
background: rgba(20, 17, 151, 0.47);
border: 3px solid #FFFFFF;
box-sizing: border-box;
border-radius: 20px;

}


.participation {
  margin: 0 2.2vw;
  padding: 2vh;
  /* width: 95vw; */
   /* height: 22vh; */
   /* text-align: justify; */
  /* border: 3px solid #ffa500; */
   display: flex;
   flex-direction: row;
   /* align-items: center;
   justify-content: space-around;   */
}


#video-container video {
   /* position: relative; */
   float: left;
   width: 16%;
   margin-left:0.6%;
   border:3px solid;
   border-color:rgb(255, 255, 255);
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
   margin-left:-28.5%;
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
   border-radius: 8px;
}

video {
   
   padding-top:1.8vh;
   /* 맨 아래에 나오는 카메라화면 */
   width: 40%;
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
   border-radius: 5px;
}


/* start 버튼*/

</style>
