<template>
  <div>       
    <div class="signup">
      <div class="circle"><img class="card__image" src="@/assets/follower (1).png" alt="login"/></div>
      <div class="id_pw">
        <div class="left"><img class="left_image" src="@/assets/profile.png" alt="id"/></div>
        <div class="right"><input id="id" class="card__input" placeholder="ID" type="text" /></div>
      </div>
      <div class="id_pw">
        <div class="left"><img class="left_image" src="@/assets/nickname.png" alt="nickname"/></div>
        <div class="right"><input id="nickname" class="card__input" placeholder="Nickname"  type="text" /></div>
      </div>
      <div class="id_pw">
        <div class="left"><img class="left_image" src="@/assets/lock.png" alt="pw"/></div>
        <div class="right"><input id="password" class="card__input" placeholder="Password"  type="password" /></div>
      </div>
      <div class="id_pw">
        <div class="left"><img class="left_image" src="@/assets/lock2.png" alt="pw_check"/></div>
        <div class="right"><input id="password" class="card__input" placeholder="Password Check"  type="password" /></div>
      </div>
      <div class="signup_box">
        <router-link :to="{ name: 'Login' }" class='signup_text'>SIGN UP</router-link>
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

.signup {
  width: 35%;
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
  height: 50px;
  display: flex;
  flex-direction: row;
  align-items: center;
  
}
.left {
  width: 77px;
  height: 50px;
  background: rgba(49,46,46,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.right {
  width: 405px;
  height: 50px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.signup_box {
 width: 482px;
 height: 50px;
 background: rgba(49,46,46,0.5);
 padding-top: 8px;
 box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.signup_text {
 color: rgba(255,255,255,1);
 font-family: Roboto;
 font-size: 28px;
 text-decoration: none;
}

.card__image {
  max-width: 76%;
  padding-top: 7px;
  
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