<template>

   <div  v-if="session">
   <!-- <div  > -->
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

         <!-- 메인 화면 -->
         <div id="main-video" class="col-md-8">
            <div class="player">
               <user-video :stream-manager="mainStreamManager"/>
               <div class="answer">
                  <input class="input_answer" placeholder="답을 입력해주세요." type="text" />
               </div>
            </div>
         </div>

         <!--  버튼 -->
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
import { video } from '@/mixins/video'

axios.defaults.headers.post['Content-Type'] = 'application/json';

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// const OPENVIDU_SERVER_SECRET = "MY_SECRET";
// const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
   name: 'Room',

   components: {
      UserVideo,
      Chatting,
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


   }},
   
   mixins: [video]

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
</style>
