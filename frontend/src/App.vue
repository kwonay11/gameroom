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
@font-face {
    font-family: '양진체';
    src: url('https://cdn.jsdelivr.net/gh/supernovice-lab/font@0.9/yangjin.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
@font-face {
    font-family: 'IM_Hyemin-Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2106@1.1/IM_Hyemin-Bold.woff2') format('woff');
    font-weight: normal;
    font-style: normal;
}
#total {
  font-family: '양진체','IM_Hyemin-Bold';
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
  background: url("./assets/sky-5114501_1920.jpg");
  height: 100vh;
  background-size:100% 100%;
}

</style>
