<template>
  <div>       
      <form class="signup" @submit.prevent="signup,check">
        <div class="circle"><img class="card__image" src="@/assets/follower (1).png" alt="login"/></div>
        
        <div class="id_pw">
          <div class="left"><img class="left_image" src="@/assets/user.png" alt="id"/></div>
          <div class="right">
            <input id="id" v-model="credentials.id" class="card__input" placeholder="ID" type="text" /></div>
            <button class="button button--brightness" @click="check(id)">중복체크</button>
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
            <div class="pw_ck" v-if="credentials.passwordCheck != credentials.password">불일치</div>
            <div class="pw_ck" v-if="credentials.passwordCheck === credentials.password">일치</div>
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
import axios from 'axios'

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
        
        axios.post(`${SERVER_URL}/users/check`,this.credentials)
         .then((res) => {
           console.log(res);
           if (res.status === 200) {
            this.id_check = true
            swal(`사용 가능한 아이디입니다.`);
            console.log('중복안함')
          }
         })
         .catch(() => {
             swal(`이미 있는 아이디입니다.`);
             console.log('중복')
             
          })

      }
      
    }
     
     

     
}
</script>

<style >

/* #signup {
  background: url("../../assets/desert-5171724_1920.png");
  height: 100vh;
  background-size:100% 100%;
} */

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

.pw_ck{
  box-sizing: border-box;
  display: block;
  font-size: 0.7rem;
  color: #ffffff;
  margin:3px;
}
</style>