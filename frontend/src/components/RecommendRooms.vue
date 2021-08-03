<template>
  <div>
    <div id="recommend">
    <h5>추천방</h5>
    <vue-horizontal-list :items="items" :options="options" class="m-5">
      <template v-slot:default="{ item }">
        <div>
          <div class="image-container">
            <img :src="item.image" />
          </div>

          <div class="roominfo">
            <p>3/5</p>
            <p>{{ item.title }}</p>
          </div>

          
        </div>
      </template>
    </vue-horizontal-list>
    </div>
  </div>
  
</template>
<script>
import VueHorizontalList from "vue-horizontal-list";
export default {
  name:'RecommendRooms',
  components: {
     VueHorizontalList
    },
    data() {
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
    };
  },
}
</script>
<style scoped>
#recommend{
  height: 350px;
}
 p {
  /* margin-top: 6px; */
  margin:2%;
  font-size: 20px;
}
.roominfo {
  /* margin-top: 8px; */
  /* margin-bottom: 6px; */
  display: flex;
  margin-left: 15%;
  /* justify-content: space-between; */
  /* font-size: 14px; */
  /* font-weight: 500; */
}
.image-container {
  border-radius: 10px;
  overflow: hidden;
  position: relative;
  width: 70%;
  /* 패딩 탑으로 직사각형으로 이미지 */
  padding-top: 45%;
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

</style>