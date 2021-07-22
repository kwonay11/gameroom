<template>
  <div>
    <div class="btn">

       <router-link class="btn_text" :to="{ name: '#' }">
         <div class="button button--brightness">빠른시작</div>
        </router-link>
       <router-link class="btn_text" :to="{ name: 'Creatroom' }">
         <div class="button button--brightness">방만들기</div>
        </router-link>
    </div>
  </div>
</template>

<script>
export default {
    name: "MainPage",
    

}
</script>

<style>
.btn{
  position: absolute;
  width: 397px;
  height: 85px;
  left: 98px;
  top: 316px;
  display: grid;
  grid-template-columns: repeat(5, auto);
}
.button {
  width: 100px;
  height: 40px;
  cursor: pointer;
  position: relative;
  display: grid;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  
}
.btn_text {
  margin: auto;
  font-weight: 600;
  display: block;
  font-size: 1em;
  text-decoration: none;
  
}
.btn span {
  margin: auto;
  font-weight: 600;
  display: block;
  font-size: 1em;
  text-decoration: none;
  
}
/*****BUTTON BRIGHTNESS*****/
.button--brightness {
  color: rgb(217, 211, 230);
  background-color: transparent;
  border: 2px solid #d2ece9;
  border-radius: 5px;
  overflow: hidden;
  transition: background-color 0.7s;
  padding-top:4px;

}
.button--brightness:before, .button--brightness:after {
  content: "";
  position: absolute;
  width: 60px;
  height: 100%;
  border-radius: 5px;

  
}
.button--brightness:before {
  top: 0;
  left: 0;
  background-color: rgba(255, 255, 255, 0.5);
  transform: translate3d(-150%, 0, 0) skew(-15deg);
}
.button--brightness:after {
  top: 0;
  left: 30px;
  width: 30px;
  background-color: #412297;
  transform: translate3d(-100px, 0, 0) skew(-15deg);
}
.button--brightness:hover {
  border: 2px solid #fff;
  background-color: #412297;
  transition: background-color 0.1s, color 0.7s 0.1s, border 0.7s;
}
.button--brightness:hover:before, .button--brightness:hover:after {
  transform: translate3d(180%, 0, 0);
  opacity: 0.6;
  transition: transform 0.7s;
}


</style>