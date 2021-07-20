<template>
  <div id="app">
    
    <nav>
      <!-- 로그인 전 -->
     <ul>
       <li>
         <router-link id="a" :to="{ name: 'Search' }">
        <img style="width:30px" src="@/assets/loupe.png" alt="search"></router-link>
        </li>
        <li>  
          <router-link  id="a" :to="{ name: 'Login' }">
        <img style="width:30px" src="@/assets/check-in.png" alt="login"></router-link>
        </li>
        <li>  
          <router-link  id="a" :to="{ name: 'Signup' }">
          <img style="width:30px" src="@/assets/add-user.png" alt="Signup"></router-link>
        </li>
     </ul>
     <router-link  id="a" :to="{ name: 'MainPage' }">
     <img src="@/assets/로고 (2).png" alt="logo"></router-link>
    </nav>

    <router-view/>

    
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #000000;
  background: url("./assets/landscape-5547401.png");
}

// nav {
//   margin: 0 auto;
//   padding: 30px 0;
//   background-image: linear-gradient(to right top, #400f55 0%, #0c3957 100%);
// }

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
