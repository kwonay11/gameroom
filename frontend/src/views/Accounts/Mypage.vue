<template>
  <div>
    
    <div class="v26_66">
    <div class="v26_59"></div>
    <div class="v26_61">
      <img style="width:90%" src="@/assets/free-icon-gamer-3983291.png" alt="img">
    </div>
    <div class="v26_62"></div>
    <!-- 레벨자리 -->
    <span class="v26_63">lev. {{ parseInt(($store.state.userData.exp)/100) }} </span>
    <!-- <span class="v26_63">lev. {{($store.state.userData.exp)}} </span> -->
    <!-- 닉네임자리 -->
    <span class="v26_64">{{ $store.state.userData.nickname }}</span>
    <button class="v113_111" @click="nickname"><img style="width:90%" src="@/assets/연필1.png" alt="img"></button>
    <button class="learn-more" @click="password">비밀번호 변경</button>
    </div>



    <!-- <ul>
      <li v-for='game_info in $store.state.userData.winRateList' ></li>
    </ul>
    <div v-for='game_info in $store.state.userData.winRateList'>
      <span>게임이름 :  {{ game_name }} </span>
      <span>게임전적 :  {{ total }}전 {{ win }}승 {{ lose }}패 </span>
    </div> -->

  <div v-for="value in game_infos" v-bind:key="value.id">
    <!-- {{ value }} -->
    게임이름 : {{ value.gameCategory.name }} | 전적 : {{ value.gameCount }}전 {{ value.firstRanked }}승 {{ value.gameCount - value.firstRanked }}패

  </div>

      
  </div>
</template>

<script>


export default {
     name: "Mypage",
     data: function () {
       return {
        //  win: 0,
        //  lose: 0,
        //  total: 0,
        //  game_name: '',
         game_infos: [],
        //  exp: 0,
       }

     },

    created(){
        const id = this.$store.state.id;
        this.$store.dispatch('fetchUser', id)
        
        // this.win = this.$store.state.userData.winRateList[0].firstRanked;
        // this.total = this.$store.state.userData.winRateList[0].gameCount;
        // this.lose = this.total-this.win

        // this.game_name = this.$store.state.userData.winRateList[0].gameCategory.name;

        this.game_infos = this.$store.state.userData.winRateList
        // console.log(this.$store.state.userData.winRateList)
        // console.log(this.game_infos)
    },

        
        methods: {
          nickname: function(){

          },
          password: function(){

          }
        }

  }

</script>

<style>

.v26_66 {
  width: 435px;
  height: 800px;
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  opacity: 1;
  position: absolute;
  left: 7%;
  top: 30%;
  overflow: hidden;
}
.v26_59 {
  width: 280px;
  height: 280px;
  background: rgba(77,69,69,0.49000000953674316);
  opacity: 1;
  position: absolute;
  top: 48px;
  left: 54px;
  border-radius: 50%;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.v26_61 {
  width: 195px;
  height: 183px;
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  opacity: 1;
  position: absolute;
  top: 99px;
  left: 97px;
  overflow: hidden;
  
}
.v26_62 {
  width: 324px;
  height: 70px;
  background: rgba(90,47,133,1);
  opacity: 1;
  position: absolute;
  top: 348px;
  left: 33px;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  overflow: hidden;
}
.v26_63 {
  width: 80px;
  position: absolute;
  top: 365px;
  left: 57px;
  font-size: 24px;
  opacity: 1;
  text-align: center;
  color: aqua;
}
.v26_64 {
  width: 130px;
  color: rgba(255,255,255,1);
  position: absolute;
  top: 363px;
  left: 137px;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 27px;
  opacity: 1;
  text-align: center;
}
.v113_111 {
  width: 51px;
  height: 51px;
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  opacity: 1;
  position: absolute;
  top: 358px;
  left: 283px;
  overflow: hidden;
}

button.learn-more {
  top:55%;
  left:10%;
  font-weight: 600;
  color: #382b22;
  padding: 0.7em;
  background: #bcb7e6;
  border: 2px solid #9da1da;
  border-radius: 0.75em;
  transform-style: preserve-3d;
   width: 40%;
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more::before {
  position: absolute;
  content: "";
  width: 100%;
  height: 90%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #7c6ebb;
  border-radius: inherit;
  box-shadow: 0 0 0 1px #8985b1, 0 0.625em 0 0 #8985b1;
  transform: translate3d(0, 0.75em, -1em);
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.learn-more:hover {
  background: #ffe9e9;
  transform: translate(0, 0.25em);
}
button.learn-more:hover::before {
  box-shadow: 0 0 0 1px #8985b1, 0 0.5em 0 0 #ffe3e2;
  transform: translate3d(0, 0.5em, -1em);
}
button.learn-more:active {
  background: #ffe9e9;
  transform: translate(0em, 0.75em);
}
button.learn-more:active::before {
  box-shadow: 0 0 0 1px #8985b1, 0 0 #8985b1;
  transform: translate3d(0, 0, -1em);
}
</style>