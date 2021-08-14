<template>

   <div v-if="session">
      <Header :roominfo="roominfo" />
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
         <div id="main-video" class="col-md-8">
            <!-- 크게 보이는 화면 -->
            <div class="player">
               <!-- <CatchMind /> -->
              <div class="main_box">
                  <!-- 시작하기 버튼 -->
                  <div v-if="!start && !ready">
                     <div class="main_box_2">
                        <div @click="game_start">
                           <Start />
                        </div>
                     </div>
                  </div>
                  
                  <!-- 321 -->
                  <div v-else-if="!ready && start ">
                     <Ready />
                  </div>
                  
                  <!-- 메인화면 -->
                  <div v-else>
                     <div v-if='roominfo.gameId == 2'>
                        <CatchMind />
                     </div>
                     <div v-else>
                        <user-video :stream-manager="mainStreamManager"/>
                     </div>
                  </div>
               </div>

               <!-- 답 입력창 -->
               <div>
                  <!-- 출제자 일때 -> 답 입력창 안보이고, 캐치마인드는 색 변경, 클리어버튼 -->
                  <div>

                  </div>
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
// import axios from 'axios';
// import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/UserVideo';
import Chatting from '@/components/GameRoom/Chatting';
import Button from '@/components/GameRoom/Button';
import Ready from '@/components/GameRoom/Ready';
import Start from '@/components/GameRoom/Start';
// import Song from '@/components/Game/Song/Song';
import CatchMind from '@/components/Game/CatchMind/CatchMind';

import { video } from '@/mixins/video'

// axios.defaults.headers.post['Content-Type'] = 'application/json';

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// const OPENVIDU_SERVER_SECRET = "MY_SECRET";
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
   name: 'Room',

   components: {
      UserVideo,
      Chatting,
      Button,
      // Song,
      Ready, 
      Start,
      CatchMind,    
      
   },

    data() {
        return {
            song_visible: false,
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
        console.log('여기ㅐ')
        console.log(this.roominfo)
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
         }, 3600);
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
   width: 33vw;
   height: 48vh;
   /* background: rgba(192, 192, 199, 0.47); */
   /* background: blue;
   border: 3px solid white; */
   border-radius:20px;
   margin: 0 auto 2.5vh;
   display:flex;
   /* justify-content: center; */
   align-items: center;

}
.main_box_2 {
   position: relative;
   width: 33vw;
   height: 48vh;
   /* width: 150%; */
   background: rgba(192, 192, 199, 0.47);
   border: 3px solid white;
   border-radius:20px;
   /* margin: 0 auto 2.5vh; */
   display:flex;
   justify-content: center;
   align-items: center;

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
   display: block;
   font-size: 1rem;
   letter-spacing: 0.15rem;
   padding: 1.5vh 2vh;
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
   font-family:'IM_Hyemin-Bold';
   display: inline-block;
   background: #f8f8f8;
   padding-left: 5px;
   padding-right: 5px;
   color: #3c90c9;
   font-weight: bold;
   border-radius: 8px;
}

video {
   
   padding-top:1.8vh;
   /* 맨 아래에 나오는 카메라화면 */
   /* width: ; */
      width: 90%;
   /* height: 48vh; */
   height: auto;
   position: relative;

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
