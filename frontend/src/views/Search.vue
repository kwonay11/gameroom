<template>
  <div>
      <div class="search_back">
      <div class="input"></div>
      </div>
  </div>
</template>

<script>
export default {
     name: "Search",
}
</script>

<style>
	* {
  box-sizing: border-box;
}
body {
  font-size: 14px;
}
.search_back {
  width: 80%;
  height: 120px;
  background: rgba(19,17,150,0.4000000059604645);
  position: relative;
  margin: auto;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content:space-around;

}
.input {
  width: 95%;
  height: 85px;
  background: rgba(253,244,244,1);
  border-radius: 15px;
}
</style>