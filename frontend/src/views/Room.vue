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
            <!-- 크게 보이는 화면 -->
         <div id="main-video" class="col-md-8">
            <!-- 노래방일때 -->
            <div v-if="roominfo.gameId === 3">
               <Song :session="session"/>
            </div>
            <div v-else class="player">
               <!-- 시작, 레디, 화면 -->
               <div class="main_box">
                  <!-- 시작하기 버튼 -->
                  <div v-if="!start && !ready">
                     <div class="main_box_2">
                        <!-- 방장만 스타트 버튼 보이기 -->
                        <div v-if='myUserNick === roominfo.ownerNicknames'>
                           <div @click="game_start">
                              <Start />
                           </div>
                        </div>
                        <!-- 방장 아닌 사람은 준비중 -->
                        <div v-else>
                           <p>준비중</p>
                        </div>
                     </div>
                  </div>
                  
                  <!-- 321 -->
                  <div v-else-if="!ready && start ">
                     <Ready />
                  </div>
                  
                  <!-- 메인화면 -->
                  <div v-else>
                     <!-- 2. 캐치마인드 -->
                     <!-- <div v-if='roominfo.gameId === 2'>
                        <CatchMind :session="session"/>
                     </div> -->
                     <!-- 4. 노래방 -->
                     <div v-if='roominfo.gameId === 3'>
                        <Song :session="session"/>
                     </div>
                     <!-- 6. 글자맞추기 -->
                     <div v-else-if='roominfo.gameId === 5'>
                        <div class="capture">
                           {{ game_ing.question }}
                        </div>
                     </div>
                     <!-- 5. 순간포착 -->
                     <div v-else-if='roominfo.gameId === 4'>
                        <div v-if="picture" class="picture">                           
                           <img :src="require(`@/assets/images/${picture_keyword}.jpg`)" alt="key" style="width:100%">
                        </div>
                     </div>
                     <!-- 1 : 몸으로 말해요, 3 : 고요속의 외침 -->
                     <div v-else-if='roominfo.gameId === 1 ||  roominfo.gameId === 2'>
                        <user-video :stream-manager="mainStreamManager"/>
                     </div>
                  </div>
               </div>
               <!-- 답 입력창 -->
               <!-- 출제자 일때 -->
               <div v-if="myUserNick === mainStreamManager_nickname">
                  {{ game_ing.keyword }}
               </div>
               <!-- 출제자가 아닐 때 -->
               <div v-else>
                  <div class="answer">
                     <input v-model="game_answer" class="input_answer" placeholder="답을 입력해주세요." type="text" @keyup.enter="check_answer"/>
                  </div> 
               </div>
            </div >

         </div>

         <!--  버튼 -->
         <div class="col-md-4">
            <Button :publisher="publisher" :roominfo="roominfo" :session="session" :myUserNick="myUserNick"/>
            <Chatting :session="session"/>
         </div>
      </div>

      <app-my-modal :visible.sync="visible_result">
         <div class="title">
            게임 결과
         </div>
         <table class="blue_top">
         <tr><th>닉네임</th><th>전적</th></tr>
         <tr v-for="value in game_result" v-bind:key="value.id">
            <td>{{ value.nickname }}</td>
            <td> 맞춘 횟수 : {{ value.answer }} </td>
         </tr>
         </table>
      </app-my-modal>

      <!-- test -->
      <!-- <button @click="gametest" style="color:white"> 게임테스트버튼</button> -->
   </div>

</template>

<script>
import { video } from '@/mixins/video'
import UserVideo from '@/components/UserVideo';
import Chatting from '@/components/GameRoom/Chatting';
import Button from '@/components/GameRoom/Button';
import Ready from '@/components/GameRoom/Ready';
import Start from '@/components/GameRoom/Start';
// import CatchMind from '@/components/Game/CatchMind/CatchMind';
import Song from '@/components/Game/Song/Song';
import Header from '@/components/GameRoom/Header';
import myModal from '@/components/myModal'
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
// import _ from "lodash"

axios.defaults.headers.post['Content-Type'] = 'application/json';

export default {
   name: 'Room',

   components: {
      UserVideo,
      Chatting,
      Button,
      Ready, 
      Start,
      // CatchMind,
      Song,
      Header,
      appMyModal: myModal,
   },

    data() {
        return {
            song_visible: false,
            start: false,
            ready: false,
            roominfo: {}, //gameId: 1, gameName: "1", gameSummary: "1-1", maxUser: 4, ownerNicknames: "ASDFADF"
            game_ing: undefined, // {"questioner":"ddd","round":1,"keyword":"key5"}
            game_answer: '',
            gameStatus: 0,
            round: 0,
            picture: false,
            picture_keyword: undefined,

            questioner: undefined,
            mainStreamManager_nickname: undefined,

            visible_result: false,
            game_result: undefined
        }
    },
   created() {
      
      this.myUserNick = this.$store.state.userData.nickname
      console.log('room.vue 닉네임')
      console.log(this.myUserNick)

      // 게임 정보 가져와서 게임 화면 맨 위에 띄우려고
      const room_id = this.$route.params.roomid;
      this.$axios.get(`${SERVER_URL}/conferences/info/${room_id}`)
      .then((res) => {
        this.roominfo = res.data
      })


      // 스타트 버튼이 눌렸다는 신호가 오면 사람들한데도 알려줌
      this.session.on('signal:start-btn', () => {
         this.start = true

         setTimeout(() => {
            this.ready = true;
            this.picture = true
            }, 3600);

            
         setTimeout(() => {
            this.ready = true;
            this.picture = false
            }, 3900);

         this.gameStatus = 1

      })

      // 게임 중 일때
      this.session.on('signal:game', (event) => {
         // 순간포착 어케함
         this.picture = true;
         setTimeout(() => {
            this.picture = false;
            }, 400);
            
         // 게임 변경 됐을 때
         console.log(event);
         const status = JSON.parse(event.data.data);
         if (status.gameStatus == 3){
            this.changecategory(status.category);
         }
         console.log('status')
         console.log(status)

         // 게임중일때
         this.game_ing = status
         console.log('게임 키워드 데이터들')
         console.log(this.game_ing)
         this.round = this.game_ing.round
         
         // 순간포착
         this.picture_keyword = this.game_ing.keyword
         console.log(this.picture_keyword)

         console.log('게임끝나고 데이터')
         this.game_result = this.game_ing.data
         console.log(this.game_result)


         // 몸으로 말하기때 필요
         // 출제자 인덱스
         this.questioner = this.game_ing.questioner
         this.mainStreamManager = this.members[this.questioner]
         console.log('ㅁㅇㅁㅇㅁㅇ')
         console.log(this.mainStreamManager)
         console.log('출제자닉네임')
         const main_nick = JSON.parse(this.mainStreamManager.session.connection.data)
         console.log(main_nick.clientData)
         this.mainStreamManager_nickname = main_nick.clientData




         
         
         
         

         // const main_nickname = JSON.parse(this.members[this.questioner].session.connection.data)
         // console.log('출제자닉네임')
         // console.log(main_nickname.participantPublicId)
         // this.mainStreamManager_nickname = main_nickname.participantPublicId
         // this.mainStreamManager = this.members[this.questioner]
         // console.log('메인스트리머 확인')
         // console.log(this.mainStreamManager.stream.streamId)

         // this.mainStreamManager.publishAudio(false)
      

        });

      //   게임 끝났다고 참가자들한테 알리기
      this.session.on('signal:game_finish', (event) => {
         console.log('게임끝')
         console.log(event)
         this.ready = false
         this.start = false
         this.gameStatus = 2
         this.picture = true
         this.mainStreamManager_nickname = undefined
         this.visible_result = !this.visible_result


      })

      //   });

            console.log('내 닉네임')
      
      console.log(this.myUserNick)

  },
  methods: {
      song(){
         this.song_visible = !this.song_visible;
      },

      game_start() {
         // ----------------스타트 버튼 누르고-------------------
         this.session.signal({
            data: JSON.stringify(this.ready),
            type: 'start-btn'
         })
         .then(() => {
            console.log('스타트버튼')
         })
         .catch(err => {
            console.log(err)
         })

         // ----------------게임 시작할 때 참가자들 한테 시그널 보내기-------------------
         this.session.signal({
            data: JSON.stringify({
               "gameStatus": 0, // 게임 상태 (게임시작)
               "category" :this.roominfo.gameId, // 게임 종류
               "round": 0, //라운드
               "conferenceId": this.$route.params.roomid, //방 id
               "JWT":this.$store.state.accessToken //토큰?
            }),
            type: 'game'
         })
         .then(() => {
            
         })
         .catch(error => {
            console.log(error);
         })
      },

      //  ----------------답 입력 맞추기---------------
      check_answer() {
         
         console.log('게임 status 바뀌는거 확인')
         console.log(this.gameStatus)
         if(this.game_answer === this.game_ing.keyword) {
            console.log('라운드헷갈림')
            console.log(this.round)

            // 라운드가 5면 게임종료임을 알린다
            if (this.round === 5) {
               this.gameStatus = 2
               // 준비, 시작도 다 false로 바껴서 다시 start버튼 나오게 돌아감
               this.ready = false,
               this.start = false,
            
               // 참가자들한테 게임 끝났다고 알려주기
               this.session.signal({
               data: JSON.stringify(this.ready, this.start),
               type: 'game_finish'
               })
               .then(() => {
                  console.log('게임끝')
               })
               .catch(err => {
                  console.log(err)
               }),


               // 라운드 5까지 갔을 때 게임 끝 오픈비두 서버한테 보내기
               this.session.signal({
               data: JSON.stringify({
                  "gameStatus": 2, // 게임 상태(게임종료)
                  "category" :this.roominfo.gameId, // 게임 종류
                  "round":this.round, //라운드
                  "conferenceId": this.$route.params.roomid, //방 id
                  "JWT":this.$store.state.accessToken //토큰?
               }),
               type: 'game'
               })
               .then(() => {
                  console.log('게임끝')
                  this.game_answer = ''
               })
               .catch(err => {
                  console.log(err)
               })     
            }

            //마지막 라운드가 아니라면 게임을 계속 진행한다. 
            else{
               console.log('erewr')
               console.log(this.questioner)
               this.session.signal({
                  data: JSON.stringify({
                     "gameStatus": 1, // 게임 상태 (진행중)
                     "category" :this.roominfo.gameId, // 게임 종류
                     "round":this.round, //라운드
                     "conferenceId": this.$route.params.roomid, //방 id
                     "JWT":this.$store.state.accessToken, //토큰?
                     "mainstream_idx": this.questioner
                  }),
                  type: 'game'
               })
               .then(() => {
                  console.log('몸으로 말해요')
                  this.game_answer = ''
                  
               })
               .catch(error => {
                  console.log(error);
               })
            }
         }
         this.game_answer = ''
            
      },
  
   
      changecategory(category) {
         this.roominfo.gameId=category;
         console.log('되나??')

         if (category === 1) {
            this.roominfo.gameName = '몸으로 말해요'
         }else if (category === 2) {
            this.roominfo.gameName = '고요속의 외침'
         }else if (category === 3) {
            this.roominfo.gameName = '노래방'
         }else if (category === 4) {
            this.roominfo.gameName = '순간 포착'
         }else if (category === 5) {
            this.roominfo.gameName = "글자 맞추기"
         }
      },    
   },
   mixins: [video]
}

</script>

<style >
.title{
  
  text-shadow: 5px 5px 70px rgba(190, 209, 212, 0.582);
  font-size: 65px;
  background: linear-gradient(to bottom,#a769d6 ,#6f92d8);
   -webkit-background-clip: text;
   -webkit-text-fill-color: transparent;
   
}
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
.capture {
   position: relative;
   width: 33vw;
   height: 48vh;
   /* width: 150%; */
   background: white;
   border: 3px solid white;
   border-radius:20px;
   font-size: 9vw;
   /* margin: 0 auto 2.5vh; */
   display:flex;
   justify-content: center;
   align-items: center;

}
.picture{
   position: relative;
   width: 33vw;
   height: 48vh;
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


table {
  table-layout: auto;
  position: relative;
  width: 80%;
  margin: 5% auto 0;
}

.blue_top {
  border-collapse: collapse;
  border-top: 3px solid #168;
} 

.blue_top tr th {
  height: 50px;
  border: 1px solid #ddd;
  text-align: center;
  background-color: white;

}
.blue_top tr td {
  height: 60px;
  border: 1px solid #ddd;
  color: white
}

</style>
