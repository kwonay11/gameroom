<template>

  <div id="waiting">
    <div class="list_title">대기방 - 대기중인 방에 참여해요! </div>
    <vue-horizontal-list :items="waiting_games" :options="options" class="abc">
      <template v-slot:default="{ item }">
          <div class="image-container">

            <img :src="image_url[item.gameId-1]" />
            <!-- <div v-if="item.gameName === '몸으로 말해요'">
              <img :src="image_url[0]" />
            </div>
            <div v-else-if="item.gameName === '캐치마인드'">
              <img :src="image_url[1]" />
            </div>
            <div v-else-if="item.gameName === '고요속의 외침'">
              <img :src="image_url[2]" />
            </div>
            <div v-else-if="item.gameName === '노래방'">
              <img :src="image_url[3]" />
            </div>
            <div v-else-if="item.gameName === '순간포착'">
              <img :src="image_url[4]" />
            </div>
            <div v-else>
              <img :src="image_url[5]" />
            </div>  -->


            <div class="roominfo">
              <p>방 : {{ item.title }}</p>
              <p>{{item.nowUser}}/{{item.maxUser}}</p>
              <p>게임 : {{ item.gameName }}</p>
              <p>방장 : {{ item.ownerNickname }}</p>
            </div>

            <!-- 비밀방일 때 열쇠 띄워줌 v-if 처리 해주기 -->
          <div v-if="item.privateRoom">
          <img class="key" src="@/assets/key.png" alt="key">
          </div>

            <div class="btn">
                <router-link class="btn_text" :to="`/gameroom/${item.id}`">
                  <div class="button button--brightness">입장</div>
                </router-link>
            </div>

            </div>
       

        <!-- </div> -->
      </template>
    </vue-horizontal-list>
    </div>

</template>


<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import VueHorizontalList from "vue-horizontal-list";
import axios from 'axios'
import _ from "lodash"

export default {
  name:'WaitingRooms',
  components: {
     VueHorizontalList
    },
    data() {
    return {
      waiting_games: [0],
      image_url: [],

      options: {
        responsive: [
          { end: 576, size: 1 },
          { start: 576, end: 768, size: 2 },
          { start: 768, end: 992, size: 3 },
          { size: 4 },
        ],
      position: {
          start: 0,
        },
      autoplay: {
        play: true,
        speed: 4000,
        repeat: true,
      },
      },
    };
  },

    created(){
    axios.get(`${SERVER_URL}/conferences`)
    .then((res) => {
      this.waiting_games = res.data
      // console.log(this.waiting_games)
      const url_value=_.sampleSize(_.range(500,600),6)

      for (var i=0; i<6; i++) {
        this.image_url.push(`https://unsplash.it/${url_value[i]}/${url_value[i]}/`)
      }



    })
  },
}
</script>
<style scoped>
@import './../common/css/main.css';
#waiting{
  height: 28vh;
}
 p,h5 {
  /* padding :2%; */
  font-size: 20px;
  color:white;
}
.roominfo {
  /* display: flex; */
  position: absolute;
  margin-left: 15%;
  margin-right: 15%;
  text-align:initial;
  top: 0.8vh;
  
  
}
.image-container {
  border-radius: 10px;
  overflow: hidden;
  position: relative;
  width: 70%;
  /* 패딩 탑으로 직사각형으로 이미지 */
  padding-top: 20%;
  margin-left: 15%;
  box-shadow: 0px 6px 6px rgba(56, 56, 56, 0.753);
}
img {
  object-fit: cover;
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  filter: brightness(40%);
  
}
.abc {
  margin: 0 5rem;
  /* height: 24vh; */
}
.key {
  width:20px;
  height:20px;
  top: 10%;
  left:85%;
  filter: brightness(100%);
}


</style>