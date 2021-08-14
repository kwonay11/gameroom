<template>
<div class="p-2">
  <section @click="selectVideo">
    <img :src="youtubeImageSrc" alt="youtube-thumbnail-image" width="200px">
    <span class="listitem"><h5>{{ video.snippet.title | stringUnescape }}</h5>
    </span>
    
  </section>
</div>
</template>

<script>
import _ from 'lodash'

export default {
  name: 'SongListItem',
  props: {
    video: {
      type: Object,
    },
    
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

<style scoped>
.listitem h5{
  color:white;
  font-size: 15px;
  width:40%;
  float:right;
  padding-top: 30px;

}

</style>