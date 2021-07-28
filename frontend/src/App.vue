<template>
  <div id='total'>
    <div v-if="this.$route.name === 'Signup'">
      <div id="signup">
        <navbar />
      </div>
    </div>
    <!-- <div v-else-if="this.$route.name === 'Search'">
      <div id="search">
        <navbar />
      </div>
    </div> -->

    <div v-else>
      <div id="app">
        <navbar />
      </div>
    </div>
  </div>
</template>
<script>

import navbar from '@/components/nav'

export default {
  name: 'App',
  components: {
    navbar,
    },

};

</script>

<style lang="scss">

#total {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #000000;
  text-align: center;
}

#app {
  background: url("./assets/landscape-5547401.png");
  height: 100vh;
  background-size:100% 100%;
}
#signup {
  background: url("./assets/desert-5171724_1920.png");
  height: 100vh;
  background-size:100% 100%;

}
#search {

}
@import url('https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap');

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
