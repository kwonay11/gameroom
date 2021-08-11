<template>
  <div>            
    <form class="room_box">
      <img class="card__image" style="width:60%" src="@/assets/방비밀번호.png" alt="roomcreate"/>
      <div class="row_box">
        <div class="left">
          <img class="room_img" src="@/assets/key1.png" alt="key">
        </div>
        <div class="right">
          <input v-model="room_password" class="card__input" placeholder="방 비밀번호" type="password" />
      <p v-if="!room_password" style="color:white; margin-top:10px">방 비밀번호를 입력해주세요.</p>
      <!-- 비번일치하지 않을 때 처리해주기 -->
        </div>
      </div>
      <div v-if="room_password">
        <router-link  :to="`/gameroom/${$route.params.id}`" class='btn-animate'>
        입장
        </router-link>
      </div>


    </form>
      

  </div>
</template>


<script>

export default {
  name: 'RoomPasswordModal',
  data: function() {
    return {
        enabled: false,
        room_password:'',
      
       }
    },
    created(){
    //  this.room_password = this.$store.state.
    }


  }

</script>


<style scoped >

.room_box {
  width: 38%;
  height:58vh;
  border-radius: 15px;
  background: rgba(58,40,106,1);
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

.row_box {
  width: 482px;
  height: 60px;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.left {
  height: 60px;
}
.room_img {
  max-width: 50%;
  padding-top: 10px;
}
.right {
  height: 60px;
}

.btn-animate {
  text-decoration: none;
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