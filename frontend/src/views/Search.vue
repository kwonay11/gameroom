<template>
  <div>
    <header>
    <SearchBar @input-search="onInputSearch"/>
    </header>
    <section>
      <div v-if="searchGames.length">
        <GameList :searchGames='searchGames' :inputKeyword='inputKeyword'/>
      </div>
     </section>
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

<style>

section,
header {
  width: 60%;       /* 전체 너비의 80% */
  margin: 0 auto;   /* 양 옆 margin을 균등하게 배분*/
  padding: 1rem 0;  /* 위, 아래 padding */

}


</style>
