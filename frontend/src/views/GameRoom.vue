<template lang="">
  <div>
    dfdfdfdf
  </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'

export default {
  name:'GameRoom',

  computed: {
    ...mapState('video'),
    ...mapActions('video')
  },
  
}
</script>
<style lang="">
  
</style>