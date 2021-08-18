<template>
  <div >
    <div class="gradient-border" >
      <div class="box">START</div>
    </div>
  </div>

</template>
<script>
export default {
  name: 'Start'
  
}
</script>
<style scoped>
@import url('https://fonts.googleapis.com/css?family=Raleway:200');

.box {
  display: flex;
  align-items: center;
  justify-content: center;
  height:8vh;
  color: black;
  font-family: 'Raleway';
  font-size: 2.5rem;
  z-index: 10;
  font-weight: bold;
}
.gradient-border{
  padding-top:7vh;
  content: '';
  position: relative;
  height:20vh;
  width: 20vw;
  background: linear-gradient(60deg, #f79533, #f37055, #ef4e7b, #a166ab, #5073b8, #1098ad, #07b39b, #6fba82);
  border-radius: 20px;
  z-index: 3;
  -webkit-animation: animatedgradient 3s ease alternate infinite;
          animation: animatedgradient 3s ease alternate infinite;
  background-size: 300% 300%;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}


@-webkit-keyframes animatedgradient {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}


@keyframes animatedgradient {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}




</style>