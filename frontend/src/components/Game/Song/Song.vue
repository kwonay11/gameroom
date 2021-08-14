<template>
  <div>
    <h3 style="text-align:center;color:white">방구석 노래방
          <img style="width:6%" src="@/assets/노래방.png" alt="노래방">
      </h3>
      <Search @input-search="onInputSearch" v-model="show"/>
      <div class="d-flex">
        <SongDetail :video="selectedVideo" />
        <!-- 검색어가 있어야 리스트 뜸 -->
        <div v-if="show"> 
        <SongList :videos="videos" @select-video="onVideoSelect" />
        </div>
      </div>
      
  </div>
</template>

<script>
import Search from '@/components/Game/Song/Search'
import SongList from '@/components/Game/Song/SongList'
import SongDetail from '@/components/Game/Song/SongDetail'

const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY


export default {
  name: 'Song',
  data: function () {
    return {
      inputValue: '',
      videos: [],
      selectedVideo: '', // SongDetail.vue 로 보내고, 출력
      show:false,
    }
  },
  components: {
    Search,
    SongList,
    SongDetail,
    UserVideo,
  },
  props: {
    mainStreamManager: Object,
    publisher: Object,
  },
  created() {
    console.log('이건언제됨')
    this.mainStreamManager = this.publisher;
  },
  methods: {
    onInputSearch: function (inputText) {
      this.show = !this.show
      console.log('데이터가 SearchBar로부터 올라왔다.')
      console.log(inputText)
      this.inputValue = inputText
      // part(필수), key(필수), q(검색어), type(video만) 매개 변수를 요청에 넣어서 보냄
      const params = {
        key: API_KEY,
        part: 'snippet',
        type: 'video',
        q: this.inputValue +'가사'
        
      }

      fetch(`https://www.googleapis.com/youtube/v3/search?key=AIzaSyBVVoBrjdTV12A560GRn9YiuS8kZRleKbQ&part=snippet&type=video&q=${params.q}`)
      .then((res) => {
        console.log(this.videos)
        return res.json();
      })
      .then((data)=> {
        // console.log('두번째 then')
        // console.log(data.items)
        this.videos = data.items
      })
      .catch((err) => {
        console.log('에러')
        console.log(err)
      })

    },
    onVideoSelect: function (video) {
      this.selectedVideo = video
    }
  },
  
}
</script>

<style>

</style>
