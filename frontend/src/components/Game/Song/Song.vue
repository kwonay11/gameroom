<template>
  <div>
    <h3 style="text-align:center;color:white">방구석 노래방
          <img style="width:6%" src="@/assets/노래방.png" alt="노래방">
      </h3>
      <Search @input-search="onInputSearch" />
      <hr>
      <div class="d-flex">
        <SongDetail :video="selectedVideo" />
        <SongList :videos="videos" @select-video="onVideoSelect" />
      </div>
      
  </div>
</template>

<script>
import axios from 'axios'

import Search from '@/components/Game/Song/Search'
import SongList from '@/components/Game/Song/SongList'
import SongDetail from '@/components/Game/Song/SongDetail'

const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
const API_URL = 'https://www.googleapis.com/youtube/v3/search'

export default {
  name: 'App',
  data: function () {
    return {
      inputValue: '',
      videos: [],
      selectedVideo: '', // SongDetail.vue 로 보내고, 출력
    }
  },
  components: {
    Search,
    SongList,
    SongDetail,
  },
  methods: {
    onInputSearch: function (inputText) {
      console.log('데이터가 SearchBar로부터 올라왔다.')
      console.log(inputText)
      this.inputValue = inputText
      // part(필수), key(필수), q(검색어), type(video만) 매개 변수를 요청에 넣어서 보냄
      const params = {
        key: API_KEY,
        part: 'snippet',
        type: 'video',
        q: '[KY 금영노래방]'+ this.inputValue
      }

      axios.get(API_URL, {
        params,
      })
      .then((res) => {
        
        console.log('되냐 파람스')//안되네
        console.log(res)
        console.log(res.data.items)
        this.videos = res.data.items
        console.log(this.videos)
        if (!this.selectedVideo) {
          this.selectedVideo = this.videos[0]
        }
      })
      .catch((err) => {
        console.log(err)
      })

    },
    onVideoSelect: function (video) {
      this.selectedVideo = video
    }
  }
}
</script>

<style>

</style>
