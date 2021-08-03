<template lang="">
  <div>
    <form class="room_box">
      
      <img class="card__image" src="@/assets/roomcreate.png" alt="roomcreate"/>
      
      <div class="row_box">
        <div class="left"></div>
        <div class="right">
          <input v-model="contents.title" class="card__input " placeholder="방 이름" type="text" />
        </div>
      </div>

      <div class="row_box">
        <div class="left"></div>
        <div class="right">
          <select  v-model="contents.maxUser" class="card__input " >
            <option disabled value="">인원수</option>
            <option >2</option>
            <option >3</option>
            <option >4</option>
            <option >5</option>
            <option >6</option>
          </select>
        </div>
      </div>

      <div class="pw_box">
        <v-checkbox
          v-model="enabled"
          hide-details
          class="shrink mr-2 mt-0 "
          label="비밀방"
        ></v-checkbox>
      </div>

      <div v-if="enabled" >
          <div class="row_box">
                <div class="row_box">
                  <div class='left'></div>
                  <div class='pw_right'>
                    <v-text-field
                      :disabled="!enabled"
                      type=password
                      v-model='contents.password'
                    ></v-text-field>
                  </div>
                </div>
          </div>
      </div>

      <button @click="joinSession()">
        <router-link :to="{ name: 'Room' }" class='btn-animate'>START</router-link>
      </button>

    </form>
  </div>
</template>


<script>
// import axios from 'axios'
// const SERVER_URL = process.env.VUE_APP_SERVER_URL
import swal from 'sweetalert';

export default {
  name: 'CreateRoomModal',
  data: function() {
    return {
        enabled: false,

        contents: {
          title:'',
          password:'',
          maxUser: '',
          gamecategory: '',
        },
       }
    },
    methods: {
      joinSession: function() {
        this.contents.gamecategory = this.$store.state.gamecategory
        
          this.$store
            .dispatch("joinSession", this.contents)
            .then(() => {
              this.$router.push({ name: "Room" , params: {roomid: this.$store.state.conferenceid }});
              // swal(`회원가입에 성공하였습니다.`);
            })
          .catch(() => {
             swal(`잘못된 정보입니다.`);
          })

      },
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
.right {
  height: 60px;
}
.pw_box {
  width: 482px;
  height: 30px;
  /* background: rgba(219,219,219,0.6000000238418579); */
  display: flex;
  flex-direction: row;
  align-items: center;
}
.pw_right {
  height: 60px;
  padding: 0 15px;
  width: 405px;
  background: rgba(219,219,219,0.6000000238418579);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}


.pw_box /deep/ label {
  color: rgb(194, 194, 194);
}

.pw_box /deep/ v-checkbox{
  color: #fff;
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