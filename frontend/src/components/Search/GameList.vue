<template>

  <div v-if="searchGames.length && inputKeyword">
    <div id="gamelist">
        <div class="search_result">{{ inputKeyword }}의 검색 결과입니다. </div>
        <vue-horizontal-list :items="searchGames" :options="options" class='abc'>
          <template v-slot:default="{ item }">
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

                <div v-if="loggedIn" class="btn">
                    <router-link class="btn_text" :to="{ name: 'RoomPasswordModal',params:{id:item.id} }">
                      <div class="button button--brightness">입장</div>
                    </router-link>
                </div>

                <div v-if="!loggedIn" class="btn" >
                    <router-link class="btn_text" :to="{ name: 'Login' }" >
                        <div class="button button--brightness">입장</div>
                      </router-link>
                  </div>

                </div>

          </template>
        </vue-horizontal-list>
          </div>
      </div>
</template>


<script>
import { authComputed } from "@/store/helpers"
import VueHorizontalList from "vue-horizontal-list";
import _ from "lodash"

export default {
  name:'GameList',
  components: {
     VueHorizontalList
    },
    data() {
    return {
      image_url: [],
      options: {
        responsive: [
          { end: 576, size: 1 },
          { size: 3 },
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
  props: {
    searchGames: Array,
    inputKeyword: String,
  },
  computed: {
    ...authComputed,
  },

  created(){
        const url_value=_.sampleSize(_.range(500,600),6)
        for (var i=0; i<6; i++) {
          this.image_url.push(`https://unsplash.it/${url_value[i]}/${url_value[i]}/`)
        }
      },
  // methods: {
  //   enter:function(){
  //     this.$router.push({name: 'RoomPasswordModal', params: {item.id}})

  //   }
  // }
}
</script>

<style scoped>
@import './../../common/css/main.css';
#gamelist{
  height: 28vh;
}
 p,h5 {
  font-size: 1vw;
  color:white;
}
.roominfo {
  position: absolute;
  margin: 2% 15%;
  text-align:initial;
  top: 0.8vh;
}
.search_result {
  margin: 2.3% 0 0 0;
  font-size: 25px;
  color:white;
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
}

.key {
  width:20px;
  height:20px;
  top: 10%;
  left:85%;
  filter: brightness(100%);
}


</style>