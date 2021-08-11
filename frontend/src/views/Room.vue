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
         <Chatting />
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

import { Video } from '@/mixins/video'

axios.defaults.headers.post['Content-Type'] = 'application/json';

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// const OPENVIDU_SERVER_SECRET = "MY_SECRET";
// const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
   name: 'App',

   components: {
      UserVideo,
      Chatting,
   },

   watch: {
   },
   
   mixins: [Video]
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
  font-size: 20px;
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
