<template>
<div>
  <div>
      <!-- <p>방 제목(공백 제거 후 처리) 혹은 방장 닉네임 검색</p> -->
    <div class="search-bar" :style="{ margin: GameLength ? '2vh auto 2vh' : '18vh auto 6vh' }" > 
      <img class="image" src="@/assets/loupe (4).png" alt="search"/>
      <input v-model='search_input' placeholder="Search" @keyup.enter='SearchKeyword' type="text" class="inner_search" />
      <!-- <v-text-field v-model='search_input' placeholder="search" @keyup.enter='SearchKeyword' :rules="rules" class="inner_search"></v-text-field> -->
  </div>

  </div>
  </div>

</template>
<script>

const SERVER_URL = process.env.VUE_APP_SERVER_URL
import axios from 'axios'

export default {
 
  name: 'SearchBar',
  data: function() {
    return {
      search_input: '',
      GameLength: null,
      rules: [
        value => !!value ,
        value => (value || '').length <= 20 || 'Max 20 characters',
      ],
    }
  },


  methods: {
    SearchKeyword() {
      axios.get(`${SERVER_URL}/conferences?keyword=${this.search_input}`)
        .then((res) => {
          const content = {
            games: res.data,
            keyword: this.search_input,
          }
          this.$emit('input-search', content)
          this.GameLength = res.data.length
          this.search_input = ''
        })
    }
  }
  
}
</script>


<style>
/*  */
.search-bar {
  /* transition: margin 0.5s; */
  text-align: center;
  width: 80%;
  background-color: rgba(20, 17, 151, 0.4);
  border-radius: 20px;
  padding: 1%;

}

.inner_search {
  width: 90%;      /* 가로 너비 배치를 화면의 끝과 끝으로 배치 */
  padding: 0.7rem 1rem;  /* input 안쪽의 margin을 여유롭게 */
  font-size: 2rem;
  background-color: white;
  border-radius: 15px;
  border:none
  
}
.image{
  width:4%;
  /* height:50%; */
  padding-bottom: 0.5%;
  margin-right:2.5%;
}
.inner_search:focus {
  outline: none;
  box-shadow: 0px 0px 20px rgb(2, 1, 12);
  }
</style>