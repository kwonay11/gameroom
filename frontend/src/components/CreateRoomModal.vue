<template lang="">
  <div>
    <form class="room_box">
      
      <img class="card__image" src="@/assets/roomcreate.png" alt="roomcreate"/>
      
      <div class="row_box">
        <div class="left"></div>
        <div class="right">
          <input id='title' v-model="contents.title" class="card__input " placeholder="방 이름" type="text" />
        </div>
      </div>

      <div class="row_box">
        <div class="left"></div>
        <div class="right">
          <input id='title' v-model="contents.title" class="card__input " placeholder="방 이름" type="text" />
        </div>
      </div>

      <!-- <div class="v362_54">
        <div class="v362_55"></div>
        <div class="v362_56"></div>
        <span class="v362_57">인원수</span>
        <div class="v362_58"></div>
      </div>
      <div class="v362_59">
        <div class="v362_60"></div>
        <div class="v362_61"></div>
        <span class="v362_62">인원수</span>
        <div class="v362_63"></div>
      </div>
      <div class="v362_64">
        <div class="v362_65"></div>
        <div class="v362_66"></div>
        <span class="v362_67">비밀번호</span>
        <div class="v362_68"></div>
      </div>
      <span class="v362_69">비공개 방</span>
      <div class="v362_70"></div>
      <div class="v362_71"></div>
      <div class="v362_72"></div>
      <span class="v362_73">START</span>
      <div class="name"></div>
      <div class="name"></div> -->


    </form>
  </div>
</template>


<script>
export default {
  name: 'CreateRoomModal',
  data: function() {
    return {
        contents: {
          title:'',
          password:'',
          maxUser: '',
          gamecategory: '',
        },
       }
    }
  }

</script>

<style scoped>

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



/* .card__input {








.v362_53 {
  width: 159px;
  color: rgba(0,0,0,1);
  position: absolute;
  top: 27px;
  left: 108px;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 30px;
  opacity: 1;
  text-align: left;
}
.v362_54 {
  width: 515px;
  height: 82px;
  /* background: url("../images/v362_54.png"); */
  /* background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  opacity: 1;
  position: absolute;
  top: 292px;
  left: 217px;
  overflow: hidden;
} */
.v362_55 {
  width: 405px;
  height: 68px;
  background: rgba(219,219,219,0.6000000238418579);
  opacity: 1;
  position: absolute;
  top: 8px;
  left: 92px;
  border: 1px solid rgba(219,219,219,0);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  overflow: hidden;
}
.v362_56 {
  width: 77px;
  height: 68px;
  background: rgba(49,46,46,0.5);
  opacity: 1;
  position: absolute;
  top: 8px;
  left: 15px;
  border: 1px solid rgba(219,219,219,0.6000000238418579);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  overflow: hidden;
}
.v362_57 {
  width: 159px;
  color: rgba(0,0,0,1);
  position: absolute;
  top: 27px;
  left: 108px;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 30px;
  opacity: 1;
  text-align: left;
}
.v362_58 {
  width: 25px;
  height: 19px;
  background: rgba(196,196,196,1);
  opacity: 1;
  position: absolute;
  top: 54px;
  left: 65px;
  transform: rotate(-178deg);
}
.v362_59 {
  width: 515px;
  height: 82px;
  /* background: url("../images/v362_59.png"); */
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  opacity: 1;
  position: absolute;
  top: 292px;
  left: 217px;
  overflow: hidden;
}
.v362_60 {
  width: 405px;
  height: 68px;
  background: rgba(219,219,219,0.6000000238418579);
  opacity: 1;
  position: absolute;
  top: 8px;
  left: 92px;
  border: 1px solid rgba(219,219,219,0);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  overflow: hidden;
}
.v362_61 {
  width: 77px;
  height: 68px;
  background: rgba(49,46,46,0.5);
  opacity: 1;
  position: absolute;
  top: 8px;
  left: 15px;
  border: 1px solid rgba(219,219,219,0.6000000238418579);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  overflow: hidden;
}
.v362_62 {
  width: 159px;
  color: rgba(0,0,0,1);
  position: absolute;
  top: 27px;
  left: 108px;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 30px;
  opacity: 1;
  text-align: left;
}
.v362_63 {
  width: 25px;
  height: 19px;
  background: rgba(196,196,196,1);
  opacity: 1;
  position: absolute;
  top: 54px;
  left: 65px;
  transform: rotate(-178deg);
}
.v362_64 {
  width: 515px;
  height: 82px;
  /* background: url("../images/v362_64.png"); */
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  opacity: 1;
  position: absolute;
  top: 452px;
  left: 219px;
  overflow: hidden;
}
.v362_65 {
  width: 405px;
  height: 68px;
  background: rgba(219,219,219,0.6000000238418579);
  opacity: 1;
  position: absolute;
  top: 8px;
  left: 92px;
  border: 1px solid rgba(219,219,219,0);
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  overflow: hidden;
}
.v362_66 {
  width: 77px;
  height: 68px;
  background: rgba(49,46,46,0.5);
  opacity: 1;
  position: absolute;
  top: 8px;
  left: 15px;
  border: 1px solid rgba(219,219,219,0.6000000238418579);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  overflow: hidden;
}
.v362_67 {
  width: 159px;
  color: rgba(0,0,0,1);
  position: absolute;
  top: 27px;
  left: 108px;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 30px;
  opacity: 1;
  text-align: left;
}
.v362_68 {
  width: 50px;
  height: 46px;
  /* background: url("../images/v362_68.png"); */
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  opacity: 1;
  position: absolute;
  top: 18px;
  left: 29px;
  overflow: hidden;
}
.v362_69 {
  width: 128px;
  color: rgba(255,255,255,1);
  position: absolute;
  top: 400px;
  left: 261px;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 30px;
  opacity: 1;
  text-align: left;
}
.v362_70 {
  width: 26px;
  height: 28px;
  background: rgba(196,196,196,1);
  opacity: 1;
  position: absolute;
  top: 400px;
  left: 219px;
  overflow: hidden;
}
.v362_71 {
  width: 605px;
  height: 297px;
  /* background: url("../images/v362_71.png"); */
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  opacity: 1;
  position: absolute;
  top: 30px;
  left: 175px;
  overflow: hidden;
}
.v362_72 {
  width: 345px;
  height: 68px;
  background: rgba(196,196,196,0.30000001192092896);
  opacity: 1;
  position: absolute;
  top: 557px;
  left: 305px;
  border: 1px solid rgba(255,250,250,1);
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
  overflow: hidden;
}
.v362_73 {
  width: 108px;
  color: rgba(0,0,0,1);
  position: absolute;
  top: 570px;
  left: 421px;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 36px;
  opacity: 1;
  text-align: center;
}
.name {
  color: #fff;
}
.name {
  color: #fff;
}

</style>