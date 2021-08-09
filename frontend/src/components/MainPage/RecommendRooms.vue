<template>

    <div id="recommend">
    <div class="list_title mt-1">추천방 - 추천 방에 참여해요! </div>
    <vue-horizontal-list :items="recommend_games" :options="options" class="abc">
       <!-- v-for="value in $store.state.userData.winRateList" v-bind:key="value.id" -->

      <template v-slot:default="{ item }">
        <div>
          <div class="image-container">

            <img :src="image_url[item.gameId-1]" />

            <div class="roominfo">
              <p>방 : {{ item.title }}</p>
              <p>{{item.nowUser}}/{{item.maxUser}}</p>
              <p>게임 : {{ item.gameName }}</p>
              <p>방장 : {{ item.ownerNickname }}</p>
            </div>

          <div v-if="item.privateRoom">
            <img class="key" src="@/assets/key.png" alt="key">
          </div>

            <div v-if="loggedIn" class="btn" id="enter">
                <router-link class="btn_text" :to="`/gameroom/${item.id}`">
                  <div class="button button--brightness">입장</div>
                </router-link>
            </div>

             <div v-if="!loggedIn" class="btn" id="enter">
                <router-link class="btn_text" :to="{ name: 'Login' }" >
                    <div class="button button--brightness">입장</div>
                  </router-link>
              </div>

            </div>
          </div>

      </template>
    </vue-horizontal-list>
    </div>

</template>


<script>
import { authComputed } from "@/store/helpers"
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import VueHorizontalList from "vue-horizontal-list";
import axios from 'axios'
import _ from "lodash"

export default {
  name:'RecommendRooms',
  components: {
     VueHorizontalList
    },
    data() {
    return {
      recommend_games: [0],
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
        speed: 5000,
        repeat: true,
      },
      },
    };
  },
  computed: {
    ...authComputed,
  },

created(){
    axios.get(`${SERVER_URL}/conferences`)
    .then((res) => {
      const numbers =_.range(0, res.data.length-1);
      const sampleNums =_.sampleSize(numbers, 5);

      for (const key in sampleNums) {
            this.recommend_games.push(res.data[sampleNums[key]])
        }
        this.recommend_games.shift(); //맨앞에 빈값 들어와서 하나 제거

      const url_value=_.sampleSize(_.range(500,600),6)

      for (var i=0; i<6; i++) {
        this.image_url.push(`https://unsplash.it/${url_value[i]}/${url_value[i]}/`)
      }

    })
  },
}
</script>
<style scoped>
@import './../../common/css/main.css';
#recommend{
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