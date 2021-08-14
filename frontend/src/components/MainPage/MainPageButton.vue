<template>
  <div >
    <div class="btn1">
      <router-link class="btn_text1" :to="`/gameroom/${fast_start}`">
        <div class="button1 button--brightness1">빠른시작</div>
      </router-link>
      <router-link class="btn_text1" :to="{ name: 'Creatroom' }" >
        <div class="button1 button--brightness1" v-if="loggedIn">방만들기</div>
      </router-link>

      <router-link class="btn_text1" :to="{ name: 'Login' }" >
        <div v-if="!loggedIn">
        <div class="button1 button--brightness1">방만들기</div>
        </div>
        </router-link>
    </div>
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import { authComputed } from "@/store/helpers"
// import axios from 'axios'

export default {
    name: "button",
    data: function() {
      return {
        fast_start:{
          'id':0
        },
      }
    },
    computed: {
    ...authComputed
    },

    created(){
    this.$axios.get(`${SERVER_URL}/conferences`)
    .then((res) => {
      this.fast_start = res.data[0]
    })
  },





}
</script>

<style>
.btn1{
  margin-left: 5vw;
  padding-bottom: 1.5vh;
  width: 397px;

  display: grid;
  grid-template-columns: repeat(5, auto);
}
.button1 {
  width: 100px;
  height: 40px;
  cursor: pointer;
  position: relative;
  display: grid;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  
}
.btn_text1 {
  font-family: 'IM_Hyemin-Bold';
  margin: auto;
  font-weight: 600;
  display: block;
  font-size: 1em;
  text-decoration: none;
  background-color:rgba(37, 44, 139, 0.76);
  
}
.btn1 span {
  margin: auto;
  font-weight: 600;
  display: block;
  font-size: 1em;
  text-decoration: none;
  
}
/*****BUTTON BRIGHTNESS*****/
.button--brightness1 {
  color: rgb(217, 211, 230);
  background-color: transparent;
  border: 2px solid #d2ece9;
  border-radius: 5px;
  overflow: hidden;
  transition: background-color 0.7s;
  padding-top:8px;

}
.button--brightness1:before, .button--brightness1:after {
  content: "";
  position: absolute;
  width: 60px;
  height: 100%;
  border-radius: 5px;

  
}
.button--brightness1:before {
  top: 0;
  left: 0;
  background-color: rgba(255, 255, 255, 0.5);
  transform: translate3d(-150%, 0, 0) skew(-15deg);
}
.button--brightness1:after {
  top: 0;
  left: 30px;
  width: 30px;
  background-color: #412297;
  transform: translate3d(-100px, 0, 0) skew(-15deg);
}
.button--brightness1:hover {
  border: 2px solid #fff;
  background-color: #412297;
  transition: background-color 0.1s, color 0.7s 0.1s, border 0.7s;
}
.button--brightness1:hover:before, .button--brightness1:hover:after {
  transform: translate3d(180%, 0, 0);
  opacity: 0.6;
  transition: transform 0.7s;
}


</style>