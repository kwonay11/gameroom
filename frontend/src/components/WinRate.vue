<template lang="">
  <div>
      <table class="bluetop">
        <tr><th>게임</th><th>전적</th></tr>
        <tr v-for="value in $store.state.userData.winRateList" v-bind:key="value.id">
          <td>{{ value.gameCategory.name }}</td>
          <td>{{ value.gameCount }}전 {{ value.firstRanked }}승 {{ value.gameCount - value.firstRanked }}패</td>
        </tr>
      </table>
  </div>
</template>
<script>
export default {
  name: 'WinRate',
  data: function () {
    return {
    }
  },

}
</script>

<style scoped>
table {
  table-layout: auto;
  position: relative;
  left: 60%;
  transform: translate(-50%, 0%);
  width: 50%;
  margin-top: 5%;

}

.bluetop {
  border-collapse: collapse;
  border-top: 3px solid #168;
} 

.bluetop tr th {
  height: 50px;
  border: 1px solid #ddd;
  text-align: center;
}
.bluetop tr td {
  height: 60px;
  border: 1px solid #ddd;
}


</style>