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


          <select class="card__input " >
            <option >인원수</option>
            <option value="volvo">2</option>
            <option value="volvo">3</option>
            <option value="volvo">4</option>
            <option value="volvo">5</option>
            <option value="volvo">6</option>
          </select>

          <!-- <v-app id="inspire">
                <v-container >
                      <v-select
                        :items="items"
                        
                        label="인원수"
                        required
                      ></v-select>
                </v-container>
          </v-app> -->

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
                      ></v-text-field>
                    </div>
                  </div>
            </div>
        </div>

  <!-- //////////////////////////////////////////////////////////////// -->
       <button class="btn-animate">START</button>
      
    </form>
  </div>
</template>


<script>

export default {
  name: 'CreateRoomModal',
  data: function() {
    return {
        items: ['2','3','4','5','6'],
        includeFiles: true,
        enabled: false,

        contents: {
          title:'',
          password:'',
          maxUser: '',
          gamecategory: '',
        },
       }
    },
  components: {
    // Dropdown,
  },
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

</style>