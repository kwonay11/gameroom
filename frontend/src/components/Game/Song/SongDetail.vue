<template>
  <div v-if="video" class="m-4">
    <iframe :src="videoURI" frameborder="0" width="140px" height="80px" class="m-2"></iframe>
    <h2>{{ video.snippet.title | stringUnescape }}</h2>
  </div>
</template>

<script>
import _ from 'lodash'

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
  filters: {
    stringUnescape: function (rawText) {
      return _.unescape(rawText)
    }
  },
}
</script>

<style>

</style>