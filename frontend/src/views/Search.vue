<template>
  <div>
    <SearchBar @input-search="onInputSearch"/>
      <div v-if="searchGames.length" class='card'>
        <GameList :searchGames='searchGames' :inputKeyword='inputKeyword'/>
      </div>
      <div v-else-if="searchGames_length === 0" >
        <div class="search_result">{{ inputKeyword }}의 검색 결과가 없습니다. </div>
      </div>
  </div>
</template>

<script>

import SearchBar from "@/components/Search/SearchBar"
import GameList from "@/components/Search/GameList"

export default {
  name: 'Search',
  components: {
    SearchBar,
    GameList,
  },
  data: function() {
    return {
        inputKeyword: '',
        searchGames: [],
        searchGames_length: -1,
    }
  },

  methods: {
    onInputSearch: function(data) {
      this.searchGames = data.games
      this.searchGames_length = this.searchGames.length
      this.inputKeyword = data.keyword
      }}

    }

</script>

<style scoped>
.card{
  height: 35vh;
  margin-left: 10%;
  margin-right: 10%;
  background-color: #3a394248;
}

.search_result {
  margin: 0.8% 0 0 0;
  font-size: 25px;
  color:white;
}
</style>
