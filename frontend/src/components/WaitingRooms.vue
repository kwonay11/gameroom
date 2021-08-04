<template>
    <div id="waiting">
    <h5 class="list_title">대기방</h5>
    <vue-horizontal-list :items="waiting_games" :options="options" class="abc">
      <template v-slot:default="{ item }">
        <div>
          <div class="image-container">
            <!-- <img :src="image_url[item.id-1]" /> -->
            <img :src="item.image" />
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

// import swal from 'sweetalert';

export default {
  name:'WaitingRooms',
  components: {
     VueHorizontalList
    },
    data: function() {
        return {
          items: [
            {
              title: "Sed non ante non cras amet",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas non sagittis leo. Vestibulum sit amet metus nec neque dignissim dapibus.",
              image: "https://picsum.photos/id/1015/600/600",
            },
            {
              title: "Curabitur sit amet nunc",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id mollis erat. Aliquam erat volutpat. Nunc erat lacus, rhoncus nec.",
              image: "https://picsum.photos/id/1019/600/600",
            },
            {
              title: "Proin pharetra, ante metus",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi ac diam ac ex efficitur posuere. Pellentesque cursus pellentesque risus, non.",
              image: "https://picsum.photos/id/1039/600/600",
            },
            {
              title: "Cras pharetra non enim a",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi malesuada varius nibh, a malesuada nisi feugiat eget. Aenean convallis semper.",
              image: "https://picsum.photos/id/1042/600/600",
            },
            {
              title: "Proin vulputate, augue eu accumsan",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas fringilla tempor libero sit amet mollis. Praesent quis leo erat. Integer.",
              image: "https://picsum.photos/id/1044/600/600",
            },
            {
              title: "Maecenas feugiat magna sapien in",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sit amet fringilla ante. Quisque at ipsum non lacus consequat dictum.",
              image: "https://picsum.photos/id/1057/600/600",
            },
            {
              title: "Donec commodo sed enim at",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eu condimentum risus. Praesent dignissim, neque nec pharetra vestibulum, libero odio.",
              image: "https://picsum.photos/id/1063/600/600",
            },
            {
              title: "In bibendum urna et turpis",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse vitae ante volutpat leo vulputate volutpat et sed ex. Vivamus eu.",
              image: "https://picsum.photos/id/1076/600/600",
            },
            {
              title: "Phasellus iaculis dignissim erat at",
              content:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla mattis quam scelerisque, eleifend purus gravida, scelerisque orci. Ut et turpis.",
              image: "https://picsum.photos/id/1083/600/600",
            },
          ],
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
      console.log('dfdfddd')
      this.waiting_games = res.data
      console.log(this.waiting_games)
      console.log(this.waiting_games.length)


      const url_value=_.sampleSize(_.range(1000,1100),this.waiting_games.length)
      console.log(url_value)

      for (var i=0; i<this.waiting_games.length; i++) {
        this.image_url.push(`url(https://picsum.photos/${url_value[i]}/600/600/)`)
      }

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
}

.cols{
  margin-top: 8vh;
  display: flex;
  -ms-flex-wrap: wrap;
      flex-wrap: wrap;
  -webkit-box-pack: center;
      -ms-flex-pack: center;
          justify-content: space-around;
}


.col{
  width: calc(25% - 2rem);
  margin: 1rem;
  cursor: pointer;
}

.container{
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
  -webkit-perspective: 1000px;
          perspective: 1000px;
}

.front,
.back{
  background-size: cover;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.25);
  border-radius: 10px;
  background-position: center;
  -webkit-transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  -o-transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1), -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  -webkit-backface-visibility: hidden;
          backface-visibility: hidden;
  text-align: center;
  min-height: 280px;
  height: auto;
  border-radius: 10px;
  color: #fff;
  font-size: 1.5rem;
}

.back{
  background: #a7aed6;
  background: -webkit-linear-gradient(45deg,  #cedce7 0%,#5751a3 100%);
  background: -o-linear-gradient(45deg,  #cedce7 0%,#5751a3  100%);
  background: linear-gradient(45deg,  #cedce7 0%,#5751a3  100%);
}

.front:after{
  position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
    width: 100%;
    height: 100%;
    content: '';
    display: block;
    opacity: .6;
    background-color: #000;
    -webkit-backface-visibility: hidden;
            backface-visibility: hidden;
    border-radius: 10px;
}
.container:hover .front,
.container:hover .back{
    -webkit-transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    -o-transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1), -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
}

.back{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
}

.inner{
    -webkit-transform: translateY(-50%) translateZ(60px) scale(0.94);
            transform: translateY(-50%) translateZ(60px) scale(0.94);
    top: 50%;
    position: absolute;
    left: 0;
    width: 100%;
    padding: 2rem;
    -webkit-box-sizing: border-box;
            box-sizing: border-box;
    outline: 1px solid transparent;
    -webkit-perspective: inherit;
            perspective: inherit;
    z-index: 2;
}

.container .back{
    -webkit-transform: rotateY(180deg);
            transform: rotateY(180deg);
    -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
}

.container .front{
    -webkit-transform: rotateY(0deg);
            transform: rotateY(0deg);
    -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
}

.container:hover .back{
  -webkit-transform: rotateY(0deg);
          transform: rotateY(0deg);
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
}

.container:hover .front{
  -webkit-transform: rotateY(-180deg);
          transform: rotateY(-180deg);
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
}

.front .inner p{
  font-size: 2rem;
  margin-bottom: 2rem;
  position: relative;
}

.front .inner p:after{
  content: '';
  width: 4rem;
  height: 2px;
  position: absolute;
  background: #C6D4DF;
  display: block;
  left: 0;
  right: 0;
  margin: 0 auto;
  bottom: -.75rem;
}

.front .inner span{
  color: rgba(255,255,255,0.7);
  font-family: 'Montserrat';
  font-weight: 300;
}

@media screen and (max-width: 64rem){
  .col{
    width: calc(33.333333% - 2rem);
  }
}

@media screen and (max-width: 48rem){
  .col{
    width: calc(50% - 2rem);
  }
}

@media screen and (max-width: 32rem){
  .col{
    width: 100%;
    margin: 0 0 2rem 0;
  }
}
.head {
  color: #ffffff;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 24px;
  opacity: 1;
  text-shadow: 0px 4px 4px #060606;
}

button {
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
  background: transparent;
  padding: 0;
  font-size: inherit;
  font-family: inherit;
}
button.start {
  width: 12rem;
  height: auto;
}
button.start .circle {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: relative;
  display: block;
  margin: 0;
  width: 3rem;
  height: 3rem;
  background: #282936;
  border-radius: 1.625rem;
}
button.start .circle .icon {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: absolute;
  top: 0;
  bottom: 0;
  margin: auto;
  background: #fff;
}
button.start .circle .icon.arrow {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  left: 0.625rem;
  width: 1.125rem;
  height: 0.125rem;
  background: none;
}
button.start .circle .icon.arrow::before {
  position: absolute;
  content: "";
  top: -0.25rem;
  right: 0.0625rem;
  width: 0.625rem;
  height: 0.625rem;
  border-top: 0.125rem solid #fff;
  border-right: 0.125rem solid #fff;
  transform: rotate(45deg);
}
button.start .button-text {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  padding: 0.75rem 0;
  margin: 0 0 0 1.85rem;
  color: #282936;
  font-weight: 700;
  line-height: 1.6;
  text-align: center;
  text-transform: uppercase;
}
button:hover .circle {
  width: 100%;
}
button:hover .circle .icon.arrow {
  background: #fff;
  transform: translate(1rem, 0);
}
button:hover .button-text {
  color: #fff;
}

</style>