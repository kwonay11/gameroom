<template>
  <div>
    
    <div class="v26_66">
    <div class="v26_59"></div>
    <div class="v26_61">
      <img style="width:90%" src="@/assets/free-icon-gamer-3983291.png" alt="img">
    </div>
    <div class="v26_62"></div>
    <span class="v26_63">lev. {{ parseInt(($store.state.userData.exp)/100) }} </span>
    <span class="v26_64">{{ $store.state.userData.nickname }}</span>

    <!-- 닉네임 변경 -->
    <button class="v113_111" @click="nickname"><img style="width:90%" src="@/assets/연필1.png" alt="img"></button>
    <app-my-modal :visible.sync="visible">
      <div>
        <img style="width:90%" src="@/assets/닉네임변경.png" alt="img">
        <input class="_input" v-model="new_nickname" placeholder="새로운 닉네임" type="text" />
        <div v-if="!new_nickname" style="color:white">변경할 닉네임을 입력해 주세요.</div>
        <button v-if="new_nickname" @click="nick_save" class="btn-animate">확인
        </button>
      </div>
    </app-my-modal>




    </div>
    <!-- 비밀번호 변경 -->
    <button class="learn-more" @click="password">비밀번호 변경</button>
      <app-my-modal :visible.sync="visible1">
        <div>
          <img style="width:90%" src="@/assets/비번변경.png" alt="img">
          <input class="_input" 
          v-model="origin_password" placeholder="현재 비밀번호" type="password" />
          <div v-if="origin_password === $store.state.password">
          <input class="_input" 
          v-model="new_password" placeholder="새비밀번호" type="password" />
          <input class="_input"
          v-model="new_password_check" placeholder="새비밀번호 확인" type="password" />
          </div>
          <div class="pw_ck" v-if="origin_password != $store.state.password && origin_password">현재 비밀번호와 불일치합니다.</div>
          <div class="pw_ck" v-if="new_password != new_password_check">새 비밀번호와 불일치합니다.</div>
          <div v-if="new_password != new_password_check || !origin_password || !new_password || !new_password_check" style="color:white">모두 입력해 주세요.</div>
          <button :disabled="new_password != new_password_check || !origin_password || !new_password" 
          @click="pw_save" class="btn-animate">확인
        </button>
        </div>
      </app-my-modal>
      <!-- 회원 탈퇴 -->
      <button class="out" @click="out">회원탈퇴</button>
      <app-my-modal :visible.sync="visible2">
        <div>
          <img style="width:90%" src="@/assets/회원탈퇴.png" alt="img">
          <input class="_input" 
          v-model="origin_password" placeholder="비밀번호 확인" type="password" />
          <div v-if="!origin_password" style="color:white">비밀번호를 입력해 주세요.</div>
          <div v-else-if="origin_password != $store.state.password" style="color:white">비밀번호가 불일치합니다.</div>
          <button v-if="origin_password === $store.state.password" @click="out_save" class="btn-animate">탈퇴 확인</button>
        </div>
      </app-my-modal>
    <WinRate />

  </div>
</template>

<script>
import swal from 'sweetalert';
import WinRate from '@/components/WinRate'
import myModal from '@/components/myModal'
// import myModal1 from '@/components/myModal1'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import { authComputed } from "@/store/helpers"
import axios from 'axios'

export default {
     name: "Mypage",
     data: function () {
       return {
        visible: false,
        visible1: false,
        visible2: false,
        new_nickname: '',
        origin_password:'',
        new_password: '',
        new_password_check: '',
       }
     },
       computed: {
    ...authComputed
    },  

     components: {
      WinRate,
      appMyModal: myModal,
     },

    created(){
        const id = this.$store.state.id;
        this.$store.dispatch('fetchUser', id)

    },

    methods: {
      nickname: function(){
        this.visible = !this.visible

      },
      nick_save: function(){
        const content = {
          nickname: this.new_nickname,
        }
        this.$store.dispatch('newnickname', content)
        swal(`닉네임이 변경되었습니다.`)
        this.visible = false
      },
      password: function(){
        this.visible1 = !this.visible1
 
      },
      pw_save: function(){
        const content = {
          changePassword: this.new_password,
          password: this.$store.state.password,
        }
        this.$store.dispatch('newpassword', content)
        swal(`비밀번호가 변경되었습니다.`)
        this.visible1= false
      },
      out:function(){
        this.visible2 = !this.visible2
      },
      out_save: function () {
             axios.defaults.headers.common[
                "Authorization"
            ] = `Bearer ${this.$store.state.accessToken}`;
            
          axios.delete(`${SERVER_URL}/users/${this.$store.state.id}`)
          .then(() => {
            this.$store.dispatch('logout')
            swal(`탈퇴되었습니다.`)
            this.visible2= false
            this.$router.push({ name: 'MainPage' })
          })

      }

}
}
  

</script>

<style>
._input {
  border-radius: 20px;
  box-sizing: border-box;
  color: #000000;
  background-color:white;
  display: block;
  font-size: 1.2rem;
  letter-spacing: 0.15rem;
  padding: 20px;
  width: 100%;
  margin-bottom:10px;
}

.v26_66 {
  width: 370px;
  height: 450px;
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
  position: absolute;
  width: 8%;
  top: 68vh;
  left:16%;
  font-weight: 600;
  color: #382b22;
  padding: 0.7em;
  background: #bcb7e6;
  border: 2px solid #9da1da;
  border-radius: 0.75em;
  transform-style: preserve-3d;
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

button.out {
  position: absolute;
  width: 7%;
  height: 45px;
  bottom: 40px;
  right: 5%;
  margin: 1px;
  font-weight: 600;
  color: #382b22;
  padding: 0.7em;
  background: #bcb7e6;
  border: 2px solid #9da1da;
  border-radius: 0.75em;
  transform-style: preserve-3d;
   /* width: 10%; */
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1);
}
button.out::before {
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
button.out:hover {
  background: #ffe9e9;
  transform: translate(0, 0.25em);
}
button.out:hover::before {
  box-shadow: 0 0 0 1px #8985b1, 0 0.5em 0 0 #ffe3e2;
  transform: translate3d(0, 0.5em, -1em);
}
button.out:active {
  background: #ffe9e9;
  transform: translate(0em, 0.75em);
}
button.out:active::before {
  box-shadow: 0 0 0 1px #8985b1, 0 0 #8985b1;
  transform: translate3d(0, 0, -1em);
}

.ranking {
  position: relative;
  left: 60%;

  transform: translate(-50%, 0%);
  margin-top: 60px;
  margin-bottom: 100px;

  padding: 5% 5%;
  width: 50%;
  /* background-color: #ffecd2; */
  border: 3px solid #ffa500;

}
.bluetop {
  border-collapse: collapse;
  border-top: 3px solid #168;
}  
.bluetop th {
  color: #168;
  background: #f0f6f9;
}
.bluetop th, .bluetop td {
  padding: 10px;
  border: 1px solid #ddd;
}
.bluetop th:first-child, .bluetop td:first-child {
  border-left: 0;
}
.bluetop th:last-child, .bluetop td:last-child {
  border-right: 0;
}

.btn-animate {
  color: #fff;
  font-size: 14px;
  font-weight: 600;
  letter-spacing: 2px;
  cursor: pointer;
  position: relative;
  z-index: 1;
  padding: 15px 30px;
  border: none;
  border-radius: 4px;
  box-shadow: 0px 16px 47px -15px #cda6ee;
  background: none;
  transition: all 0.2s cubic-bezier(0.19, 1, 0.22, 1);
  border-radius: 8px;
  overflow: hidden;
  outline: none;
  transform: translateZ(0);
}
.btn-animate span {
  position: relative;
  z-index: 2;
}
.btn-animate:before, .btn-animate:after {
  border-radius: 8px;
  content: "";
  z-index: -1;
  background: linear-gradient(100deg, #a6c1ee, #cc96eb);
  
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.btn-animate:after {
  
  background: linear-gradient(100deg, #560a9b, #9e158f);

  transform: scaleY(0);
  transform-origin: top;
  transition: transform 0.5s cubic-bezier(0.19, 1, 0.22, 1);
  transition-delay: 0.1s;
}
.btn-animate:hover {
  box-shadow: 0px 16px 47px -15px  #cda6ee;
}
.btn-animate:hover:after {
  transform: scaleY(1);
  transform-origin: bottom;
  transition-delay: 0s;
}
.btn-animate:active {
  transform: translateY(4px) translateZ(0);
  box-shadow: 0px 8px 10px -6px  #cda6ee;
}

</style>