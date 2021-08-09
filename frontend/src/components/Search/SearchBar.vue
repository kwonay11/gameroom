<template>
  <div>
    <div class="search-bar" :style="{ margin: GameList ? '0' : '18vh 0' }">
      <p>방 제목(공백 제거 후 처리) 혹은 방장 닉네임 검색</p>
      <input  v-model="search_input" placeholder="search" @keyup.enter='search' type="text" />
    </div>
  </div>
</template>
<script>

// const SERVER_URL = process.env.VUE_APP_SERVER_URL
// import axios from 'axios'

export default {
  name: 'SearchBar',
  // data: function() {
  //   return {

  //   }
  // },

  // methods: {
  //     search: function( ) {
  //       console.log(this.search_input)
  //       axios.get(`${SERVER_URL}/conferences?keyword=${this.search_input}`)
  //       .then((res) => {
  //         console.log(res.data)
  //       })

  //       this.search_input = '';
  //     }
  //   }
}
</script>
<style >
.search-bar {
  transition: margin 0.5s;
}

.search-bar > input {
  width: 100%;      /* 가로 너비 배치를 화면의 끝과 끝으로 배치 */
  padding: 0.5rem;  /* input 안쪽의 margin을 여유롭게 */
  font-size: 2rem;
  background-color: red;
}
</style>