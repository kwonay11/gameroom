<template>
  <div>        
    <div class="login_box">
      <div class="circle"><img class="card__image" src="@/assets/user.png" alt="login"/></div>
      <div class="id_pw">
        <div class="id_left"><img class="left_image" src="@/assets/profile.png" alt="user"/></div>
        <div class="id_right"><input id="id" class="card__input" placeholder="ID" style="height:66px" type="text" /></div>
      </div>
      <div class="id_pw">
        <div class="pw_left"><img class="left_image" src="@/assets/password.png" alt="pw"/></div>
        <div class="pw_right"><input id="password" class="card__input" placeholder="Password" style="height:66px" type="password" /></div>
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





.login_box {
  width: 35%;
  height:58vh;
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
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  
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
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  
}
.id_right {
  width: 405px;
  height: 68px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.pw_left {
  width: 77px;
  height: 68px;
  background: rgba(49,46,46,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.pw_right {
  width: 405px;
  height: 68px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.signup_box {
 width: 482px;
 height: 65px;
 background: rgba(49,46,46,0.5);
 padding-top: 10px;
 box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
 border-radius: 20px;
}
.signup_text {
 color: rgba(255,255,255,1);
 font-family: Roboto;
 font-size: 30px;
 text-decoration: none;
}

.left_image {
  max-width: 60%;
  padding-top: 10px;
}

.card__image {
  border-radius: 50%;
  max-width: 100%;
}
.card__input {
  border-radius: 20px;
  box-sizing: border-box;
  color: #000000;
  display: block;
  font-size: 1.5rem;
  letter-spacing: 0.15rem;
  padding: 10px;
  width: 100%;
}
.card__input:focus, .card__input:active {
  background: rgba(254, 254, 254, 0.08);
  
  outline: none;
}


</style>

