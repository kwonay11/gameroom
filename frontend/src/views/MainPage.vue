<template>
  <div>
  <MainPageButton />
  <RecommendRooms />
  <WaitingRooms />
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

</style>