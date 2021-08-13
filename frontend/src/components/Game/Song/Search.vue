<template>
  <div>
    <v-text-field class="card col-md-4" 
    dark
    label="노래를 검색하세요 :) "
    color="#BDBDBD"
    @keyup.enter="onInputKeyword"
  ></v-text-field>
  </div>
</template>

<script>
export default {
    name:'Search',
    methods: {
        onInputKeyword: function(event){
          console.log('검색어')
          console.log(event.target.value)
          this.$emit('input-search', event.target.value)
        },
    }
}

</script>

<style scoped>
.card{
    position: relative;
    left:33%;
    padding:2vh 1vw 0 1vw ;
    background: rgba(77, 14, 114, 0.47);
    border: 3px solid #FFFFFF;
}

</style>