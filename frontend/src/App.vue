<template>
  <div id='total'>
    <div v-if="this.$route.name === 'Signup'">
      <div id="signup">
        <navbar />
        <MainLogo />
        <router-view/>
      </div>
    </div>

    <div v-else-if="this.$route.name === 'Room'">
      <div id="room">
        <router-view/>
      </div>
    </div>

    <div v-else>
      <div id="app">
        <navbar />
        <MainLogo />
        <router-view/>
      </div>
    </div>
  </div>
</template>
<script>

import navbar from '@/components/MainPage/nav'
import MainLogo from '@/components//MainPage/MainLogo'

export default {
  name: 'App',
  components: {
    navbar,
    MainLogo,
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
  text-decoration: none;
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
#room {
  // background: url("./assets/sky-5114501_1920.jpg");
  height: 100vh;
  background-size:100% 100%;
}

</style>
