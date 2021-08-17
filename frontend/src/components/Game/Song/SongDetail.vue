<template>
  <div v-if="video" class="p-4">
    <iframe :src="videoURI" frameborder="0" width="500" height="300" allow="autoplay"></iframe>
    <!-- <h5>{{ video.snippet.title | stringUnescape }}</h5> -->
  </div>
</template>
this.videoId
<script>
export default {
  name: 'SongDetail',
  props: {
    video: {
      type: [String, Object],
    },
    session: Object,
    videoId: String,
  },
  computed: {
    videoURI: function () {
      console.log('song detail : ' + this.videoId)
      return `https://www.youtube.com/embed/${this.videoId}?autoplay=1`
    },
  },
  mounted() { 
    console.log('song detail : ' + this.videoId)
  }

  
}
</script>

<style scoped>

</style>