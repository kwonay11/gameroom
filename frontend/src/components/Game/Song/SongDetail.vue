<template>
  <div v-if="video" class="p-4">
    <iframe :src="`https://www.youtube.com/embed/${videoId}?autoplay=1`" frameborder="0" width="500" height="300" allow="autoplay"></iframe>
    <!-- <h5>{{ video.snippet.title | stringUnescape }}</h5> -->
  </div>
</template>
this.videoId
<script>
export default {
  name: 'SongDetail',
  data: function () {
    return {
      videoId: undefined,
      video: false,
    }
  },
  props: {
    session: Object,
  },
  created() { 
      this.video = false;
      this.session.on('signal:song', (event) => {
        const id = event.data.slice(1, -1)  
        console.log('session에서 받은 id : ' + id)
        this.videoId = id;
        this.video = true;
      });
  }
}
</script>

<style scoped>

</style>