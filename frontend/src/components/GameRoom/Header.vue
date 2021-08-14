<template>
  <div>
      <div class="card1 p-1">
      <h5 class="mt-2">
         방제목 : {{ roominfo.title }}&nbsp;
         게임 종류 : {{ roominfo.gameName }}&nbsp;
         최대 인원 : {{ roominfo.maxUser}}
      </h5>
      </div>
  </div>
</template>

<script>
export default {
    name:'header',
     props: {
    roominfo: Object,
  },


}
</script>

<style>
.card1{
   width: 60%;
   background: rgba(104, 102, 163, 0.47);
   border-radius: 20px;
   margin:0 auto;
}
.card1 h5{
   background: linear-gradient(to bottom,#B993D6 ,#8CA6DB);
   -webkit-background-clip: text;
   -webkit-text-fill-color: transparent;
}
.h{
    background: linear-gradient(to bottom,#cad693 ,#8CA6DB);
   -webkit-background-clip: text;
   -webkit-text-fill-color: transparent;

}
</style>