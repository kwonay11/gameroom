<template lang="">
  <div class='button'>
      <div class="link">
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
  display:flex;
  flex-direction: column;
  width: 100%;
  height: 15vh;
  padding: 1vh;
}
 img {
  width : 7vh;
}
.link > img {
  width : 4vh;
}
.control {
  padding: 0 2vw;
  margin-top:1vh;
  align-items: center;
  display:flex;
  flex-direction: row;
  width: 100%
}
.link {
  right:-40%;
  cursor: pointer;
  position: relative;
}
.buttons{
  cursor: pointer;
  position: relative;
  width: 25%;
}
</style>