<template lang="">
  <div>
    <form class="room_box">
      
      <div class="title">
            방 만들기
          </div>
      
      <div class="row_box">
        <div class="left">
          <img class="room_img" src="@/assets/방제목.png" alt="방제목">
        </div>
        <div class="right">
          <input v-model="contents.title" class="card__input " placeholder="방 이름" type="text" />
            <p v-if="!contents.title" style="color:white; margin-top:10px;">방 이름을 입력해주세요.</p>
        </div>
      </div>

      <div class="row_box row">
        <div class="left">
          <img class="room_img1" src="@/assets/방인원.png" alt="인원수">
        </div>
        <div class="right">
          <select  v-model="contents.maxUser" class="card__input " >
            <option disabled value="">인원수</option>
            <option >2</option>
            <option >3</option>
            <option >4</option>
            <option >5</option>
            <option >6</option>
          </select>
          <p v-if="!contents.maxUser" style="color:white" class="mt-2">방 최대 정원을 선택해주세요.</p>
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
          <div class="row_box" style="margin-bottom:10px">
                <div class="row_box">
                  <div class='left'>
                    <img class="room_img2" src="@/assets/key1.png" alt="key">
                  </div>
                  <div class='pw_right'>
                    <v-text-field
                      :disabled="!enabled"
                      type=password
                      v-model='contents.password'
                    ></v-text-field>
                    <p v-if="!contents.password" style="color:white;" >비밀번호를 입력해주세요.</p>
                  </div>
                </div>
          </div>
      </div>

      <button v-if="contents.title && contents.maxUser" @click="joinSession">
        <span class='btn-animate' > START</span>
      </button>

    </form>
  </div>
</template>


<script>
// import swal from 'sweetalert';
import axios from "axios";
const SERVER_URL = process.env.VUE_APP_SERVER_URL

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
      // joinSession: function() {
      //   this.contents.gamecategory = this.$store.state.gamecategory
        
      //     this.$store
      //       .dispatch("joinSession", this.contents)
      //       .then(() => {
      //         // this.$router.push({ name: "Room" , params: {roomid: this.$store.state.conferenceid }});
      //         swal(`즐거운 게임하세요!`);
      //       })
      //     .catch(() => {
      //        swal(`잘못된 정보입니다.`);
      //     })

      // },
      room_info: function(){
        this.$store.dispatch('roomInfo',this.contents)
      },

      joinSession: function() {
        event.preventDefault();
        this.contents.gamecategory = this.$store.state.gamecategory

        return new Promise((resolve, reject) => {
                axios.defaults.headers.common[
                    "Authorization"
                ] = `Bearer ${this.$store.state.accessToken}`;

                axios.post(`${SERVER_URL}/conferences`, this.contents)
                    .then((res) => {
                        console.log('sdsdsdsd')
                        // console.log(commit);
                        console.log(res.data.roomId)
                        // this.$store.dispatch('roomInfo',this.contents)
                        this.$store.dispatch('joinSession',res.data.roomId)
                        this.$router.push({ name: "Room" , params: {roomid: res.data.roomId }});
                        
                        resolve();
                    })
                    .catch((error) => {
                        reject(error);
                    })
            })


      },

  
    },
    

  }
 //   this.$store.dispatch("createConference", this.contents)
        //     .then((res) => {
        //       console.log(res)
        //       swal(`즐거운 게임하세요!`);
        //       console.log( this.$store.state.conferenceid)
        //       this.$router.push({ name: "Room" , params: {roomid: this.$store.state.conferenceid}});
              
        //     })
        //     .catch(() => {
        //       reject();
        //       console.log('error')
        //       swal(`잘못된 정보입니다.`);
        //     })

        // })
      
</script>


<style scoped >
.title{
  text-shadow: 5px 5px 70px rgba(190, 209, 212, 0.582);
  font-size: 70px;
  background: linear-gradient(to bottom,#a769d6 ,#6f92d8);
   -webkit-background-clip: text;
   -webkit-text-fill-color: transparent;
   
}
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
  width: 26vw;
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
.room_img1 {
  max-width: 90%;
  padding-top: 8px;
}
.room_img2 {
  max-width: 71%;
  padding-top: 4px;
}
.right {
  height: 60px;
}
.pw_box {
  width: 26vw;
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