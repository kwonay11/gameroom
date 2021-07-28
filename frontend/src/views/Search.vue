<template>
  <div>
      <div class="search_back pt-4 px-4">
        <v-autocomplete 
          filled
          solo
        ></v-autocomplete>
      </div>
  </div>
</template>

<script>



export default {
     name: "Search",
     data: function () {
       return{
         nowpage: 'search',
       }
     },

    created(){
        const nowpage = this.nowpage
        this.$store.dispatch('nowpage', nowpage)
    },

}
</script>

<style>


.search_back {
  width: 80%;
  height: 90px;
  background: rgba(19,17,150,0.4000000059604645);
  position: relative;
  margin: auto;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content:space-around;
  padding-top: 10px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

}
.input {
  width: 95%;
  height: 85px;
  background: rgba(253,244,244,1);
  border-radius: 15px;
}
</style>