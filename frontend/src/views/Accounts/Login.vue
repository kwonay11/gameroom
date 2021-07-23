<template>
  <div>        
    <div class="login_box">
      <div class="circle"><img class="card_image" src="@/assets/user.png" alt="login"/></div>
      <div class="id_pw">
        <div class="id_left"><img class="left_image" src="@/assets/profile.png" alt="user"/></div>
        <div class="id_right">
          <input id="id" class="card__input" v-model="credentials.id" placeholder="ID"  type="text" />
        </div>
      </div>
      <div class="id_pw">
        <div class="pw_left"><img class="left_image" src="@/assets/password.png" alt="pw"/></div>
        <div class="pw_right">
          <input id="password" class="card__input" v-model="credentials.password" placeholder="Password"  type="password" />
        </div>
      </div>
      <div class="login_button">
        <!-- <router-link :to="{ name: 'MainPage' }" class='signup_text'>Login</router-link> -->
        <button class='signup_text' @click="login(credentials)">
              Login
        </button>
      </div>
      <div class="login_button">
        <router-link :to="{ name: 'Signup' }" class='signup_text'>GO TO SIGN UP</router-link>
      </div>
    </div>
  </div>
</template>

<script>
const SERVER_URL = 'http://localhost:8080'
import axios from 'axios'

export default {
     name: "Login",
     data: function () {
       return {
         credentials: {
           id: '',
           password: '',
           loginSuccess: false,
         }
       }
     },
     methods: {
       login: function (res) {
        // 입력 아이디랑 비밀번호 콘솔창에 띄워지고
         console.log(res)
        //  서버에서 요청 받아온게 res로 들어가게 되어있음
         axios.post(`${SERVER_URL}/users/login`, this.credentials)
         .then((userinfo) => {
          //  들어온 userinfo 콘솔에서 확인하고
          console.log(userinfo)
          // 로그인 성공이면 변수 값 바꾸기
          
          if (res.status === 200) {
            this.credentials.loginSuccess = true
            console.log('로그인성공')
          }
          // res에 뭐들어오는지 확인하고 userinfo 이름 변수명 바꾸고
          if ( this.credentials.loginSuccess) {
            this.$storethis.$store.dispatch('Login', userinfo);
          }
           this.credentials.loginSuccess = false
         })
         .catch((err) => {
            console.log(err)
            console.log('로그인 실패')

         })         
       }
       
     }
}


</script>

<style>


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
  height: 55px;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 0px;
}
.id_left {

  width: 77px;
  height: 55px;
  background: rgba(49,46,46,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  
}
.id_right {
  width: 405px;
  height: 55px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.pw_left {
  width: 77px;
  height: 55px;
  background: rgba(49,46,46,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.pw_right {
  width: 405px;
  height: 55px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.login_button {
 width: 482px;
 height: 55px;
 background: rgba(49,46,46,0.5);
 box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
 border-radius: 20px;
padding-top: 8px;
}
.signup_text {
 color: rgba(255,255,255,1);
 font-family: Roboto;
 font-size: 30px;
 text-decoration: none;
}

.left_image {
  max-width: 50%;
  padding-top: 8px;
}

.card_image {
  max-width: 87%;
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

