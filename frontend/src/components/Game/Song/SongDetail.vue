<template>
  <div v-if="video" class="p-4">
    <iframe :src="videoURI" frameborder="0" width="500" height="300"></iframe>
    <!-- <h5>{{ video.snippet.title | stringUnescape }}</h5> -->
  </div>
</template>

<script>
export default {
  name: 'SongDetail',
  props: {
    video: {
      type: [String, Object],
    }
  },
  computed: {
    videoURI: function () {
      const videoId = this.video.id.videoId
      return `https://www.youtube.com/embed/${videoId}`
    }
  },
  
}
</script>

<style scoped>

</style>