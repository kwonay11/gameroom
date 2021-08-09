<template>
  <div>
    <header>
    <SearchBar />
    </header>
    <section>
    <GameList />
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
  }
}
</script>

<style>

section,
header {
  width: 80%;       /* 전체 너비의 80% */
  margin: 0 auto;   /* 양 옆 margin을 균등하게 배분*/
  padding: 1rem 0;  /* 위, 아래 padding */
}

section {
  display: flex;  /* Detail, List를 가로 배치 */
}
</style>
