<template lang="">
  <div >
    <nav>
     <ul>
       <li>
         <router-link id="a" :to="{ name: 'Search' }">
        <img style="width:30px" src="@/assets/loupe.png" alt="search"></router-link>
        </li>

        <li v-if="!loggedIn">  
          <router-link  id="a" to="/login">
          <img style="width:30px" src="@/assets/check-in.png" alt="login">
          </router-link>
        </li>

        <li v-else>
          <button  id="a" type="button" class="logoutButton" @click="logout">
          <img style="width:30px" src="@/assets/exit (2).png" alt="logout">
          </button>
        </li>
        
        <li v-if="!loggedIn">  
          <router-link  id="a" :to="{ name: 'Signup' }">
          <img style="width:30px" src="@/assets/add-user.png" alt="Signup"></router-link>
        </li>

        <li v-else>
         <router-link id="a" :to="{ name: 'Mypage' }">
        <img style="width:30px" src="@/assets/profile.png" alt="search"></router-link>

       </li>
     </ul>


     <!-- <router-link  id="a" :to="{ name: 'MainPage' }">
     <img src="@/assets/logo.png" alt="logo">
     <MainLogo />
     </router-link> -->
        
    </nav>
    
  </div>
</template>
<script>
import { authComputed } from "@/store/helpers"
import swal from 'sweetalert';

export default {
  name:'nav',
  computed: {
    ...authComputed,
    // MainLogo,
  },  
  methods: {
    logout() {
      this.$store.dispatch('logout')
      this.$router.push({ name: "MainPage" });
      swal(`로그아웃 되었습니다.`);
    }
  }

}
</script>
<style lang="scss">
nav{
  text-align: center;
  // position: float;

}
nav ul {
  text-align: right;
  background: linear-gradient(90deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.2) 25%, rgba(255, 255, 255, 0.2) 75%, rgba(255, 255, 255, 0) 100%);
  box-shadow: 0 0 25px rgba(0, 0, 0, 0.1), inset 0 0 1px rgba(255, 255, 255, 0.6);
}

nav ul li {
  display: inline-block;
}

nav ul li #a {
  padding: 18px;
  font-family: "Open Sans";
  text-transform:uppercase;
  color: rgba(0, 35, 122, 0.5);
  font-size: 18px;
  text-decoration: none;
  display: block;
}

nav ul li #a:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1), inset 0 0 1px rgba(255, 255, 255, 0.6);
  background: rgba(255, 255, 255, 0.1);
  color: rgba(0, 35, 122, 0.7);
}

  
</style>