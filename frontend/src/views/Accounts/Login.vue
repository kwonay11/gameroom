<template>
  <div>        
    <div class="login_box">
      <div class="circle"></div>
      <div class="id_pw">
        <div class="id_left"></div>
        <div class="id_right"></div>
      </div>
      <div class="id_pw">
        <div class="pw_left"></div>
        <div class="pw_right"></div>
      </div>
      <div class="signup_box">
        <router-link :to="{ name: 'MainPage' }" class='signup_text'>Login</router-link>
      </div>
      <div class="signup_box">
        <router-link :to="{ name: 'Signup' }" class='signup_text'>GO TO SIGN UP</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
     name: "Login",

}
</script>

<style >



* {
  box-sizing: border-box;
}
body {
  font-size: 14px;
}
.login_box {
  width: 600px;
  height: 58vh;
  border-radius: 15px;
  backdrop-filter: blur(10px);
  background: rgba(101, 51, 126, 0.6);
  position: relative;
  margin: 0 auto;
  margin-top: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content:space-around;
  padding: 10px 0 10px 0;
}

.circle {
  width: 153px;
  height: 152px;
  background: rgba(219, 214, 214, 0.1);
  border-radius: 50%;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}

.id_pw {
  width: 482px;
  height: 82px;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.id_left {

  width: 77px;
  height: 68px;
  background: rgba(49,46,46,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
}
.id_right {
  width: 405px;
  height: 68px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
}
.pw_left {
  width: 77px;
  height: 68px;
  background: rgba(49,46,46,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
}
.pw_right {
  width: 405px;
  height: 68px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
}
.signup_box {
 width: 482px;
 height: 65px;
 background: rgba(49,46,46,0.5);
 padding-top: 10px;
}
.signup_text {
 color: rgba(255,255,255,1);
 font-family: Roboto;
 font-size: 30px;
 text-decoration: none;
}
</style>