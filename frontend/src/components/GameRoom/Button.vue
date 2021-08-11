<template lang="">
  <div class='button'>
      <div class="link">
        <!-- 방장만 보이게 하기 -->
        <img  src="@/assets/settings.png" alt="setting">

        <img  src="@/assets/link.png" alt="link">
      </div>

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

            <span class="buttons">
              <img  src="@/assets/question.png" alt="tutorial">
            </span>

      </div>
  </div>
</template>
<script>
export default {
  name: 'Button',

  props:{
    publisher: Object,
  },
  

  methods: {
    muteAudio() {
      if (this.publisher.stream.audioActive) {
        this.publisher.publishAudio(false)
      }
      else {
        this.publisher.publishAudio(true)
      }

    },
    muteVideo() {
      if (this.publisher.stream.videoActive) {
        this.publisher.publishVideo(false)
      }
      else {
        this.publisher.publishVideo(true)
      }

    }
  }

  
}
</script>
<style scoped>
.button{
     /* border: 3px solid rgb(255, 255, 255); */
   border-radius:20px;
  display:flex;
  flex-direction: column;
  width: 100%;
  height: 15vh;
  padding: 0 1vh;
}
.link {
  right:-40%;
  cursor: pointer;
  position: relative;
  padding: 0 2vw;
  width: 90%;
  margin-bottom: 1.5vh;
}
.link > img {
  width : 4.5vh;
  padding: 0 0.4vw;
}
.control {
  padding: 0 2vw;
  margin-top:1vh;
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

</style>