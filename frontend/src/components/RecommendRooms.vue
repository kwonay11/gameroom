<template>

    <div id="recommend">
    <div class="list_title">추천방 - 추천 방에 참여해요! </div>
    <vue-horizontal-list :items="recommend_games" :options="options" class="abc">
      <template v-slot:default="{ item }">
        <div>
          <div class="image-container">

            <div v-if="item.gameName === 'game1'">
              <img :src="image_url[0]" />
            </div>
            <div v-else-if="item.gameName === 'game2'">
              <img :src="image_url[1]" />
            </div>
            <div v-else-if="item.gameName === 'game3'">
              <img :src="image_url[2]" />
            </div>
            <div v-else-if="item.gameName === 'game4'">
              <img :src="image_url[3]" />
            </div>
            <div v-else-if="item.gameName === 'game5'">
              <img :src="image_url[4]" />
            </div>
            <div v-else>
              <img :src="image_url[5]" />
            </div> 
            <!-- <img class="card" :src="image_url[item.id-1]" /> -->


            <div class="roominfo">
              <p>{{item.nowUser}}/{{item.maxUser}}</p>
              <p>게임 : {{ item.gameName }}</p>
              <p>방 : {{ item.title }}</p>
              <p>방장 : {{ item.ownerNickname }}</p>
            </div>

            <!-- 비밀방일 때 열쇠 띄워줌 v-if 처리 해주기 -->
          <!-- <div v-if="item.privateRooms"> -->
          <img class="key" src="@/assets/key.png" alt="key">
          <!-- </div> -->

            <div class="btn">
                <router-link class="btn_text" :to="{ name: '#' }">
                  <div class="button button--brightness">입장</div>
                </router-link>
            </div>

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
  name:'RecommendRooms',
  components: {
     VueHorizontalList
    },
    data() {
    return {
      options: {
        responsive: [
          { end: 576, size: 1 },
          { start: 576, end: 768, size: 2 },
          { start: 768, end: 992, size: 3 },
          { size: 4 },
        ],
        autoplay: {
          // enable/disable playing slideshow
          play: true,
          // the delay duration between slides in milliseconds
          speed: 4000,
          // if setup, the slideshow will be in the loop.
          repeat: true,
        },
      },
        recommend_games: [],
        image_url: [],
    };
  },

    created(){
    axios.get(`${SERVER_URL}/conferences/`)
    .then((res) => {
      this.recommend_games = res.data
      console.log('dfdfddd')
      console.log(this.recommend_games)
      console.log(this.recommend_games)
      console.log(this.recommend_games[0])


      const url_value=_.sampleSize(_.range(500,600),6)

      for (var i=0; i<6; i++) {
        this.image_url.push(`https://unsplash.it/${url_value[i]}/${url_value[i]}/`)
      }
      console.log('ffffffffff')
      console.log(this.image_url)


    })
  },
}
</script>
<style scoped>
@import './../common/css/main.css';
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