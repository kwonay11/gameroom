<template>
  <div class="list p-4">
      <SongListItem v-for="(video, idx) in videos" :key="idx" :video="video"
      @select-video="onSelectVideo"
      />
  
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
.list{
 color:white;
 overflow-y: scroll;
 height:32vh;
 margin-top:2.5vh;

}

.list::-webkit-scrollbar {
  width: 8px; 
  height: 8px;
 
}
.list::-webkit-scrollbar-track {
   background: #3f3150;
   border-radius: 20px;
}
.list::-webkit-scrollbar-corner {
   background: #3f3150;
   border-radius: 20px;
  
}
.list::-webkit-scrollbar-thumb {
   background:  #b0a2c8;
   border-radius: 20px;
}
.list::-webkit-scrollbar-button {
  background-color: #dccbe0;
 border-radius: 20px;
}
</style>