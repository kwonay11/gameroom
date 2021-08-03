<template>
  <div >
  <MainPageButton />
  <RecommendRooms />
  <WaitingRooms />
  </div>
</template>

<script>

import { authComputed } from "@/store/helpers"
import WaitingRooms from "@/components/WaitingRooms"
import RecommendRooms from "@/components/RecommendRooms"
import MainPageButton from "@/components/MainPageButton"

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

<style>

</style>