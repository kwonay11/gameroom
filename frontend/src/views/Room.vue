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
            <Button :publisher="publisher"/>
            <Chatting :session="session"/>
         </div>
      </div>
      <!-- test -->
      <!-- <button @click="gametest"> -->
      <!-- </div> -->
   </div>

</template>

<script>
import axios from 'axios';
// import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/UserVideo';
import Chatting from '@/components/GameRoom/Chatting';
import Button from '@/components/GameRoom/Button';
import { video } from '@/mixins/video'
import { mapState } from 'vuex'
axios.defaults.headers.post['Content-Type'] = 'application/json';

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// const OPENVIDU_SERVER_SECRET = "MY_SECRET";
// const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
   name: 'Room',

   components: {
      UserVideo,
      Chatting,
      Button,
   },

   data () {
      return {
         
         OV: undefined,
         session: undefined,
         mainStreamManager: undefined,
         publisher: undefined,
         subscribers: [],
         
         

         // mySessionId: '',
         myUserName: '',
         myUserNick: '',
         canJoin: null,


   }},
   computed: mapState(['conferenceid']),
   methods: {
      gametest : function() {

      }
   },
   
   mixins: [video]

}

</script>

<style>

.input_answer {
   outline: none !important;
   color:white;
   height: 83px;
   display: block;
   font-size: 1.2rem;
   letter-spacing: 0.15rem;
   padding: 3vh;
   width: 100%;
 
}
.answer{
display: inline-block;
width: 30vw;
height: 80px;
/* margin-left: 10vw; */
position: relative;
background: rgba(20, 17, 151, 0.47);
border: 3px solid #FFFFFF;
box-sizing: border-box;
border-radius: 20px;

}


.participation {
  margin: 0 2.2vw;
  padding: 2.5vh;
  /* width: 95vw; */
   /* height: 22vh; */
   /* text-align: justify; */
  /* border: 3px solid #ffa500; */
   display: flex;
   flex-direction: row;
   /* align-items: center;
   justify-content: space-around;   */
}

.player {
   /* 젤 크게 나오는 메인스트리머 화면 */
   /* border: 3px solid white; */
   padding-top:2vh;
   border-radius:20px;
   align-items: center;
}
#video-container video {
   /* position: relative; */
   float: left;
   width: 16%;
   margin-left:0.6%;
   border:4px solid;
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
   
   margin-top:1.8vh;
   /* 맨 아래에 나오는 카메라화면 */
   width: 55%;
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
</style>
