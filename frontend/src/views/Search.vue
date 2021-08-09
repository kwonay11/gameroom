<template>
  <div>
    <SearchBar @input-search="onInputSearch"/>
      <div v-if="searchGames.length" class='card'>
        <GameList :searchGames='searchGames' :inputKeyword='inputKeyword'/>
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
    }
  },

  methods: {
    onInputSearch: function(data) {
      this.searchGames = data.games
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
</style>
