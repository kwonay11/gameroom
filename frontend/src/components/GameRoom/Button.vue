<template lang="">
  <div class='button'>
      <div class="link">
        <!-- 방장만 보이게 하기 -->
        <img  src="@/assets/settings.png" alt="setting">

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

            <span class="buttons">
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

export default {
  name: 'Button',

  data() {
    return {
       visible_url: false,
       visible_tutorial: false,
       url: null,
    }
  },
  components: {
    appMyModal: myModal,
  },

  props: {
    publisher: Object,
    roominfo: Object,
  },
  
  created() {

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
      
      // const urlInput = document.getElementById('urlInput');
      this.url = window.document.location.href
    },
    doCopy() {
      const my_url = document.getElementById('url')
      my_url.select();
      document.execCommand('copy')
      swal(`링크 복사 완료!`)
      this.visible = false
      
    },
    tutorial() {
      this.visible_tutorial = !this.visible_tutorial
    }
  }

  
}
</script>
<style scoped>
.button{
  padding-top:3.6vh;
  /* border: 1px solid rgb(255, 255, 255); */
   border-radius:20px;
  display:flex;
  flex-direction: column;
  width: 100%;
  height: 18vh;
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
  width : 4.5vh;
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
  width : 7vh;
}
.gamename h1{
  padding-top:1vh;
  background: linear-gradient(to bottom,#B993D6 ,#8CA6DB);
   -webkit-background-clip: text;
   -webkit-text-fill-color: transparent;
}
</style>