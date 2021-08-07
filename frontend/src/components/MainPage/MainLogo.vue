<template>
  <!-- <div class='font'>
    <div class="neon">
      방구석
    </div>
      <div class="flux">
      게임방
    </div>
  </div> -->

  
  <router-link  :to="{ name: 'MainPage' }" style="text-decoration: none">
  <div class='font'>
    <div class="neon">
      방구석
    </div>
      <div class="flux">
      게임방
    </div>
  </div>

  </router-link>

</template>

<script>
export default {
  name: 'MainLogo',
  
}
</script>

<style>

@font-face {
    font-family: '양진체';
    src: url('https://cdn.jsdelivr.net/gh/supernovice-lab/font@0.9/yangjin.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

.font{
  /* padding-top: 1vh; */
  font-family: '양진체';
  font-size: 4.5vw;
}

.neon {
  text-shadow: 0 0 3vw #2356FF;
}

.flux {
  padding-left: 9vw;
  text-shadow: 0 0 3vw #8b0af4;
}

.neon {
  animation: neon 1.3s ease infinite;
  -moz-animation: neon 1.3s ease infinite;
  -webkit-animation: neon 1.3s ease infinite;
}

@keyframes neon {
  0%,
  100% {
    text-shadow: 0 0 1vw #1041FF, 0 0 3vw #1041FF, 0 0 10vw #1041FF, 0 0 10vw #1041FF, 0 0 .4vw #8BFDFE, .5vw .5vw .1vw #147280;
    color: #28D7FE;
  }
  50% {
    text-shadow: 0 0 .5vw #082180, 0 0 1.5vw #082180, 0 0 5vw #082180, 0 0 5vw #082180, 0 0 .2vw #082180, .5vw .5vw .1vw #0A3940;
    color: #146C80;
  }
}


.flux {
  animation: flux 1.3s ease infinite;
  -moz-animation: flux 1.3s ease infinite;
  -webkit-animation: flux 1.3s ease infinite;
}

@keyframes flux {
  0%,
  100% {
    text-shadow: 0 0 1vw #E82FF5, 0 0 3vw #E82FF5, 0 0 10vw #E82FF5, 0 0 10vw #E82FF5, 0 0 .4vw #FBB4FF, .5vw .5vw .1vw #CB16FA;
    color: #FBB4FF;
  }
  50% {
    text-shadow: 0 0 .5vw #59125E, 0 0 1.5vw #59125E, 0 0 5vw #59125E, 0 0 5vw #59125E, 0 0 .2vw #8F5493, .5vw .5vw .1vw #42114E;
    color: #8F5493;
  }
}

</style>
