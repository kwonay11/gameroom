<template>
<div class="d-flex p-2 bd-highlight">
  <ol @click="selectVideo" >
    <img :src="youtubeImageSrc" alt="youtube-thumbnail-image" width="100">
    {{ video.snippet.title | stringUnescape }}
    <hr>
  </ol>
</div>
</template>

<script>
import _ from 'lodash'

export default {
  name: 'SongListItem',
  props: {
    video: {
      type: Object,
    }
  },
  filters: {
    stringUnescape: function (rawText) {
      return _.unescape(rawText)
    }
  },
  methods: {
    selectVideo: function () {
      this.$emit('select-video', this.video)
    }
  },
  computed: {
    youtubeImageSrc: function () {
      return this.video.snippet.thumbnails.default.url
    }
  }
}
</script>

<style>

</style>