<template lang="">
  <div class='ranking'>
    <table class="bluetop">
      <tr><th>게임</th><th>전적</th></tr>
      <div v-for="value in game_infos" v-bind:key="value.id">
        <tr><td>게임이름 : {{ value.gameCategory.name }}</td><td>전적 : {{ value.gameCount }}전 {{ value.firstRanked }}승 {{ value.gameCount - value.firstRanked }}패</td></tr>
      </div>
    </table>
  </div>
</template>
<script>
export default {
  name: 'WinRate',
  data: function () {
    return {
      game_infos: [],
    }
  },

  created(){
      this.game_infos = this.$store.state.userData.winRateList
  },
}
</script>

<style>
.ranking {
  position: relative;
  left: 60%;

  transform: translate(-50%, 0%);
  margin-top: 60px;
  margin-bottom: 100px;

  padding: 5% 5%;
  width: 50%;
  /* background-color: #ffecd2; */
  border: 3px solid #ffa500;

}
.bluetop {
  border-collapse: collapse;
  border-top: 3px solid #168;
}  
.bluetop th {
  color: #168;
  background: #f0f6f9;
}
.bluetop th, .bluetop td {
  padding: 10px;
  border: 1px solid #ddd;
}
.bluetop th:first-child, .bluetop td:first-child {
  border-left: 0;
}
.bluetop th:last-child, .bluetop td:last-child {
  border-right: 0;
}

</style>