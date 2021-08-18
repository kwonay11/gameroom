<template>
  <div>       
      <form class="signup" @submit.prevent="signup,check">
        <div class="circle"><img class="card__image" src="@/assets/follower (1).png" alt="login"/></div>
        
        <div class="id_pw">
          <div class="left"><img class="left_image" src="@/assets/user.png" alt="id"/></div>
          <div class="right">
            <input id="id" v-model="credentials.id" class="card__input" placeholder="ID" type="text" /></div>
            <button class="button button--brightness2 left_arrange_1" @click="check(id)">중복체크</button>
        </div>

        <div class="id_pw">
          <div class="left"><img class="left_image" src="@/assets/nickname2.png" alt="nickname"/></div>
          <div class="right">
            <input id="nickname" v-model="credentials.nickname" class="card__input" placeholder="Nickname"  type="text" /></div>
          </div>
       
        <div class="id_pw">
          <div class="left"><img class="left_image" src="@/assets/lock.png" alt="pw"/></div>
          <div class="right">
            <input id="password" v-model="credentials.password" class="card__input" placeholder="Password"  type="password" />
          </div>
        </div>

        <div class="id_pw">
          <div class="left"><img class="left_image" src="@/assets/lock2.png" alt="pw_check"/></div>
          <div class="right">
            <input id="passwordCheck" v-model="credentials.passwordCheck" class="card__input" placeholder="Password Check"  type="password" /></div>
            <div class="pw_ck left_arrange_2" v-if="credentials.passwordCheck != credentials.password">불일치</div>
            <div class="pw_ck left_arrange_2" v-if="credentials.passwordCheck === credentials.password">일치</div>
        </div>
      <!-- 빈칸일 때, 패스워드와 확인이 같지 않을 때 버튼 비활성화 -->
        <button :disabled="!credentials.passwordCheck || !credentials.password || credentials.password != credentials.passwordCheck || !credentials.id || !credentials.nickname"
        @click="signup()" class="signup_box">
          SIGN UP
        </button>
      </form>
      
  </div>
</template>

<script>
import swal from 'sweetalert';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
// import axios from 'axios'

export default {
     name: "Signup",
     components:{
      
     },
     data(){
       return{
        credentials: {
          id:'',
          nickname:'',
          password: '',
          passwordCheck: '',
        }  
       }
     },
     methods: {
      signup:function() {
        this.$store
          .dispatch("signup", this.credentials)
          .then(() => {
            this.$router.push({ name: "Login" });
            swal(`회원가입에 성공하였습니다.`);
          })
          .catch(() => {
             swal(`회원가입에 실패하였습니다.`);
          })
      },
      check: function(){
        
        this.$axios.post(`${SERVER_URL}/users/check`,this.credentials)
         .then((res) => {
           if (res.status === 200) {
            this.id_check = true
            swal(`사용 가능한 아이디입니다.`);
          }
         })
         .catch(() => {
             swal(`이미 있는 아이디입니다.`);
          })

      }
      
    }
     
     

     
}
</script>

<style>
.button--brightness2 {
  color: rgb(217, 211, 230);
  background-color: transparent;
  border: 2px solid #d2ece9;
  border-radius: 5px;
  overflow: hidden;
  transition: background-color 0.7s;
  padding-top:8px;
  width: 12%;
  height: 7%;
  background-color: #412297;

}
.button--brightness2:before, .button--brightness1:after {
  content: "";
  position: absolute;
  width: 60px;
  height: 100%;
  border-radius: 5px;

  
}
.button--brightness2:before {
  top: 0;
  left: 0;
  background-color: rgba(255, 255, 255, 0.5);
  transform: translate3d(-150%, 0, 0) skew(-15deg);
}
.button--brightness2:after {
  top: 0;
  left: 30px;
  width: 30px;
  background-color: #412297;
  transform: translate3d(-100px, 0, 0) skew(-15deg);
}
.button--brightness2:hover {
  border: 2px solid #fff;
  background-color: #412297;
  transition: background-color 0.1s, color 0.7s 0.1s, border 0.7s;
}
.button--brightness2:hover:before, .button--brightness1:hover:after {
  transform: translate3d(180%, 0, 0);
  opacity: 0.6;
  transition: transform 0.7s;
}
.left_arrange_1 {

  position: absolute;
  left: 25vw;
}
.left_arrange_2 {

  position: absolute;
  left: 28vw;
}

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
  width: 26vw;
  height: 50px;
  display: flex;
  flex-direction: row;
  align-items: center;
  
}
.id_left {

  width: 4vw;
  height: 55px;
  background: rgba(49,46,46,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  
}
.left {
  width: 4vw;
  height: 50px;
  background: rgba(49,46,46,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.left_image {
  width: 2vw;
  padding-top: 0.7vh;
}
.right {
  width: 22vw;
  height: 50px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.signup_box {
 width: 26vw;
 height: 50px;
 background: rgba(49,46,46,0.5);
 border-radius: 20px;
 padding-top: 8px;
 color:White;
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
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-sizing: border-box;
  color: #000000;
  display: block;
  font-size: 1.5rem;
  letter-spacing: 0.15rem;
  padding: 10px;
  width: 100%;
  height:98%;
}
.card__input:focus, .card__input:active {
  background: rgba(254, 254, 254, 0.08);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  outline: none;
}

.pw_ck{
  box-sizing: border-box;
  display: block;
  font-size: 0.7rem;
  color: #c42f8b;
  margin:3px;
}

</style>