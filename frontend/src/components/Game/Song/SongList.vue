<template>
  <div>
    <ul>
      <SongListItem v-for="(video, idx) in videos" :key="idx" :video="video" 
      @select-video="onSelectVideo"
      />
    </ul>
  </div>
</template>

<script>
import SongListItem from '@/components/Game/Song/SongListItem'

export default {
  name: 'SongList',
  props: {
    videos: Array,
  },
  methods: {
    onSelectVideo: function (video) {
      this.$emit('select-video', video)
    }
  },
  components: {
    SongListItem,
  }
}
</script>

<style>

</style>