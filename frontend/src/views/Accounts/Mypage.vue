<template>
  <div>
    마이페이지
  </div>
</template>

<script>
// const SERVER_URL = process.env.VUE_APP_SERVER_URL
// import axios from 'axios'

export default {
     name: "Mypage",

    created(){
        const id = this.$store.state.id;
          this.$store.dispatch('fetchUser', id)
        }
  }

</script>

<style>

</style>