<template>
  <div>
  <MainPageButton />
  <div class="card">
    <RecommendRooms />
    <WaitingRooms />
  </div>
  </div>
</template>

<script>

import { authComputed } from "@/store/helpers"
import WaitingRooms from "@/components/MainPage/WaitingRooms"
import RecommendRooms from "@/components/MainPage/RecommendRooms"
import MainPageButton from "@/components/MainPage/MainPageButton"

export default {
    name: "MainPage",
    computed: {
    ...authComputed
    },
    components: {
      MainPageButton,
      WaitingRooms,
      RecommendRooms

    }

}
</script>

<style scoped>
.card{
  padding-top: 0.8%;
  margin-left: 3%;
  margin-right: 3%;
  background-color: #3a394248;
}
</style>