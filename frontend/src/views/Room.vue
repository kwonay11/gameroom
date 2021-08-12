<template>

   <div  v-if="session">
      <h5 style="color:white" class="card1">
         방제목 : {{ roominfo.title }}  
         게임 종류 : {{ roominfo.gameName }}  
         최대 인원 : {{ roominfo.maxUser}}
      </h5>
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
            <button class="card" @click="song">
            노래방
         </button>
         <div class="player" v-if="song_visible === true">
            <Song />
         </div>

            <div class="player" v-else>
               <user-video :stream-manager="mainStreamManager"/>
               <div class="answer">
                  <input class="input_answer" placeholder="답을 입력해주세요." type="text" />
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
   },


   
   data () {
      return {
         song_visible: false,
         OV: undefined,
         session: undefined,
         mainStreamManager: undefined,
         publisher: undefined,
         subscribers: [],

         mySessionId: null,
         myUserName: '',
         myUserNick: '',
         canJoin: null,

         roominfo: {},

   }},

   created() {
    console.log('방 id')
    console.log(this.mySessionId)

    axios.get(`${SERVER_URL}/conferences/info/${this.mySessionId}`)
      .then((res) => {
        this.roominfo = res.data
        console.log('룽 정보')
        console.log(this.roominfo)
        console.log(this.roominfo.gameSummary)

      })
  },
  methods: {
      song(){
         this.song_visible = !this.song_visible;
      }

   },


   
   mixins: [video]

}

</script>

<style>
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
   width: 50%;
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
