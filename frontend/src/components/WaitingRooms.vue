<template>
    <div id="waiting">
    <h5 class="list_title">대기방</h5>
    <vue-horizontal-list :items="waiting_games" :options="options" class="abc">
      <template v-slot:default="{ item }">
        <div>
          <div class="image-container">
            <img :src="image_url[item.id-1]" />
          </div>
          <div class="roominfo">
            <p>{{item.nowUser}}/{{item.maxUser}}</p>
            <p>게임 : {{ item.gameName }}</p>
            <p>방 : {{ item.title }}</p>
            <p>방장 : {{ item.ownerNickname }}</p>
          </div>
        </div>
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
    data: function() {
        return {
          options: {
            responsive: [
              { end: 576, size: 1 },
              { start: 576, end: 768, size: 2 },
              { start: 768, end: 992, size: 3 },
              { size: 4 },
            ],
          },
          waiting_games: [],
          image_url: [],
        };

  },

  created(){
    axios.get(`${SERVER_URL}/conferences/`)
    .then((res) => {
      this.waiting_games = res.data
      console.log('dfdfddd')
      console.log(this.waiting_games)
      console.log(this.waiting_games)
      console.log(this.waiting_games[0])


      const url_value=_.sampleSize(_.range(1000,1100),this.waiting_games.length)

      for (var i=0; i<this.waiting_games.length; i++) {
        this.image_url.push(`https://picsum.photos/id/${url_value[i]}/600/600/`)
      }
      console.log(this.image_url)


    })
  },
}
</script>
<style scoped>

#waiting{
  height: 28vh;
}
 p {
  padding :2%;
  font-size: 20px;
}
.roominfo {
  display: flex;
  margin-left: 15%;

}
.image-container {
  border-radius: 10px;
  overflow: hidden;
  position: relative;
  width: 70%;
  /* 패딩 탑으로 직사각형으로 이미지 */
  padding-top: 40%;
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
}
.abc {
  margin: 0 5rem;
  height: 24vh;
  padding-top:0.8vh;
}


</style>