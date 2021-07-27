<template>
  <div id="app">
    <!-- <div ></div> -->
    
    <!-- <div v-if ="$store.state.nowpage != search">
      <navbar />
      asdfasdfasdfasdf
    </div> -->
    <navbar />
    <!-- <span v-if="$store.state.nowpage === true">
      aswdfasdfadsf
    </span> -->
    <!-- <div v-else>
      ggggggggg
    </div> -->
  </div>
</template>
<script>
// import { authComputed } from "@/store/helpers"
import navbar from '@/components/nav'

// import swal from 'sweetalert';

export default {
  name: 'App',
  components: {
    navbar,
    },

  // computed: {
  //     ...authComputed
  //   },
  // methods: {
  //   logout() {
  //     this.$store.dispatch('logout')
  //     this.$router.push({ name: "MainPage" });
  //     swal(`로그아웃 되었습니다.`);
  //   }
  // }
};

</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #000000;
  background: url("./assets/landscape-5547401.png");
  height: 100vh;
  background-size:100% 100%;
}
@import url('https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap');
// nav {
//   margin: 0 auto;
//   padding: 30px 0;
//   background-image: linear-gradient(to right top, #400f55 0%, #0c3957 100%);
// }
p{
  font-family: 'Gamja Flower', cursive;
}
nav ul {
  text-align: right;
  background: linear-gradient(90deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.2) 25%, rgba(255, 255, 255, 0.2) 75%, rgba(255, 255, 255, 0) 100%);
  box-shadow: 0 0 25px rgba(0, 0, 0, 0.1), inset 0 0 1px rgba(255, 255, 255, 0.6);
}

nav ul li {
  display: inline-block;
}

nav ul li #a {
  padding: 18px;
  font-family: "Open Sans";
  text-transform:uppercase;
  color: rgba(0, 35, 122, 0.5);
  font-size: 18px;
  text-decoration: none;
  display: block;
}

nav ul li #a:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1), inset 0 0 1px rgba(255, 255, 255, 0.6);
  background: rgba(255, 255, 255, 0.1);
  color: rgba(0, 35, 122, 0.7);
}


</style>
