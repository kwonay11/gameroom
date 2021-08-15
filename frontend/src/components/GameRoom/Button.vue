<template lang="">
  <div class='button'>
      <div class="link">
        <!-- 방장만 보이게 하기 -->
        <img  src="@/assets/settings.png" alt="setting" @click='setting'>
        
        <app-my-modal :visible.sync="visible_setting">
          <div class="title">
            게임 변경
          </div>
          <div>
            <div class="row_box row">
          <div class="left">
            <img class="room_img1 p-2" src="@/assets/game.png" alt="games">
          </div>
          <div class="right">
            <select  v-model="games" class="card__input " >
              <option disabled value="">게임 선택</option>
              <option >몸으로 말해요</option>
              <option >캐치마인드</option>
              <option >고요속의 외침</option>
              <option >노래방</option>
              <option >순간포착</option>
              <option >글자 맞추기</option>
            </select>
        </div>
      </div>
      <br>
            <button class="btn-animate" @click="changeGame()">start</button>
          </div>
          </app-my-modal>

        <img  src="@/assets/link.png" alt="link" @click='link'>
      </div>
      <app-my-modal :visible.sync="visible_url">
        <div>
          <input id='url' class="_input" v-model="url"  type="text"/>
          <br>
          <button class="btn-animate" @click="doCopy()">copy</button>
        </div>
      </app-my-modal>

      <div class='control'>
        <!-- 음소거 -->
        <span class="buttons" @click='muteAudio'>
          <div v-if='publisher.stream.audioActive'>
            <img  src="@/assets/microphone (3).png" alt="mike">
          </div>
          <div v-else>
            <img  src="@/assets/microphone (2).png" alt="mike">
          </div>
        </span>

        <!-- 비디오중지 -->
        <span class="buttons" @click='muteVideo'>
          <div v-if='publisher.stream.videoActive'>
            <img  src="@/assets/video-player.png" alt="mike">
          </div>
          <div v-else>
            <img   src="@/assets/video-player (2).png" alt="mike">
          </div>
        </span>

            <span class="buttons" @click="leaveSession">
              <img  src="@/assets/delete.png" alt="delete">
            </span>

            <!-- 게임 설명 -->
            <span class="buttons">
              <img src="@/assets/question.png" alt="tutorial" @click='tutorial'>
            </span>
      </div>
      <app-my-modal :visible.sync="visible_tutorial">
        <div class="gamename">
          <h1>
            {{ roominfo.gameName }}
         </h1>
          <p style="color:white"> {{ roominfo.gameSummary }}</p>
        </div>
      </app-my-modal>
  </div>
</template>
<script>
import myModal from '@/components/myModal'
import swal from 'sweetalert';
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'Button',

  data() {
    return {
       visible_url: false,
       visible_setting: false,
       visible_tutorial: false,
       url: null,
       games:'',
       contents: {
          title:'',
          password:'',
          maxUser: '',
          gamecategory: '',
        },
    }
  },
  components: {
    appMyModal: myModal,
  },

  props: {
    publisher: Object,
    roominfo: Object,
    session: Object,
  },

  
  created: function () {
      this.$axios.get(`${SERVER_URL}/conferences/${this.$route.params.roomid}`)
      .then((res) => {
        // this.cotents.password = this.roominfo.password
        this.roominfo = res.data
        console.log('룸 정보')
        console.log(this.roominfo)
        this.cotents.title = this.roominfo.title
        this.cotents.maxUser = this.roominfo.maxUser

        console.log(this.games)
        if (this.games === '몸으로 말해요'){
          this.contents.gamecategory = 1
        }else if (this.games === '캐치 마인드'){
          this.contents.gamecategory = 2
        }else if (this.games === '고요속의 외침'){
          this.contents.gamecategory = 3
        }else if (this.games === '노래방'){
          this.contents.gamecategory = 4
        }else if (this.games === '순간포착'){
          this.contents.gamecategory = 5
        }else {
          this.contents.gamecategory = 6
        }
      })

    this.session.on('signal:leave',(event) =>{
        console.log('leave')
        console.log(event)
    })
  },

  methods: {
    muteAudio() {
      if (this.publisher.stream.audioActive) {
        this.publisher.publishAudio(false)
      }else {
        this.publisher.publishAudio(true)
      }
    },
    muteVideo() {
      if (this.publisher.stream.videoActive) {
        this.publisher.publishVideo(false)
      }else {
        this.publisher.publishVideo(true)
      }
    },
    link() {
      this.visible_url = !this.visible_url
      this.url = window.document.location.href
    },
    doCopy() {
      const my_url = document.getElementById('url')
      my_url.select();
      document.execCommand('copy')
      swal(`링크 복사 완료!`)
      this.visible_url = false
    },
    tutorial() {
      this.visible_tutorial = !this.visible_tutorial
    },
    setting() {
      this.visible_setting = !this.visible_setting
    },
    changeGame(){
      console.log(this.contents)
      this.$axios.post(`${SERVER_URL}/conferences`, this.contents)
        .then((res) => {
            console.log('성공')
            console.log(res)//contents 빈값 
            // this.$store.dispatch('joinSession',res.data.roomId)
            // this.$router.push({ name: "Room" , params: {roomid: res.data.roomId }});
            
            this.visible_setting = !this.visible_setting
        })
        .catch((error) => {
          console.log('에러')
            console.log(error);
        })
    },
    leaveSession () {
         // --- Leave the session by calling 'disconnect' method over the Session object ---
         this.session.signal({
          
         data: JSON.stringify({
            "roomId" : this.$route.params.roomid,
            "JWT": this.$store.state.accessToken
         }),
         type: 'leave'
         })
         .then(() => {
          console.log('leave success');
          this.$router.push({ name: "MainPage" });
            
			})
			.catch(error => {
				console.log(error);
			})
         if (this.session) this.session.disconnect();

         this.session = undefined;
         this.mainStreamManager = undefined;
         this.publisher = undefined;
         this.subscribers = [];
         this.OV = undefined;

         

         window.removeEventListener('beforeunload', this.leaveSession);
      },

  }

  
}
</script>
<style scoped>
.title{
  text-shadow: 5px 5px 70px rgba(190, 209, 212, 0.582);
  font-size: 65px;
  background: linear-gradient(to bottom,#a769d6 ,#6f92d8);
   -webkit-background-clip: text;
   -webkit-text-fill-color: transparent;
   
}
.button{
  display:flex;
  flex-direction: column;
  width: 100%;
  height: 15vh;
}
.link {
  right:-40%;
  cursor: pointer;
  position: relative;
  padding: 0 2vw;
  width: 90%;
  padding-bottom: 1.5vh;
}
.link > img {
  width : 2.5vw;
  height : 2.5vh;
  padding: 0 0.4vw;
}
.control {
  padding: 0 2vw;
  padding-top:1vh;
  align-items: center;
  display:flex;
  flex-direction: row;
  width: 100%
}
.buttons{
  cursor: pointer;
  position: relative;
  width: 25%;
}
img {
  width : 4vw;
  height : 6.7vh;
}
.gamename h1{
  padding-top:1vh;
  background: linear-gradient(to bottom,#B993D6 ,#8CA6DB);
   -webkit-background-clip: text;
   -webkit-text-fill-color: transparent;
}
</style>