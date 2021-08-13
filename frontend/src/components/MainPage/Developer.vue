<template>
  <div id="dev">
    
    <main class="card">
      <div>
      <img style="width:8%" src="@/assets/불4조.png" alt="불사조">
      <img style="width:5%" src="@/assets/불.png" alt="불"><br>
      <!-- <button type="button" onclick="js:KaKaoShare()">카카오</button><br> -->
      문의사항<br>
      권예빈 kwonay11@naver.com<br>
      김보민 <br>
      김윤빈 <br>
      정동원 <br>
      © 2021  All rights reserved
<!-- 메인 유튜브 잘되는거 -->
      <button class="card" @click="song">
            노래방
         </button>
         <div class="player" v-if="song_visible === true">
            <Song />
         </div>
      </div>



    </main>
  </div>
</template>

<script>
import Song from '@/components/Game/Song/Song';

export default {
    name:'Developer',
    data(){
      return{
        song_visible: false,
      }
    },
    components: {
      Song,
    },
    methods: {
      song(){
         this.song_visible = !this.song_visible;
      }

   },

}
</script>

<style scoped>
 #dev {
  background: linear-gradient( to right,rgb(152, 84, 183),rgb(142, 78, 172), rgb(107, 64, 148),rgb(77, 53, 141),rgb(77, 53, 141));
  /* background: linear-gradient( to bottom,rgb(152, 84, 183), rgb(94, 43, 126),rgb(62, 29, 82)); */
  height: 60vh;
  background-size:100% 100%;
  padding-top:10%;

} 
.card{
  padding-top: 3%;
  padding-bottom: 3%;
  margin-left: 3%;
  margin-right: 3%;
  background-color: #3a394248;

 
}

</style>