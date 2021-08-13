<template >

    <div class="demo">
      <div class="demo__colored-blocks">
        <div class="demo__colored-blocks-rotater">
          <div class="demo__colored-block"></div>
          <div class="demo__colored-block"></div>
          <div class="demo__colored-block"></div>
        </div>
        <div class="demo__colored-blocks-inner"></div>
        <div class="demo__text">Ready</div>
      </div>
      <div class="demo__inner">
        <svg class="demo__numbers" viewBox="0 0 100 100">
          <defs>
            <path class="demo__num-path-1" d="M40,28 55,22 55,78"/>
            <path class="demo__num-join-1-2" d="M55,78 55,83 a17,17 0 1,0 34,0 a20,10 0 0,0 -20,-10"/>
            <path class="demo__num-path-2" d="M69,73 l-35,0 l30,-30 a16,16 0 0,0 -22.6,-22.6 l-7,7"/>
            <path class="demo__num-join-2-3" d="M28,69 Q25,44 34.4,27.4"/>
            <path class="demo__num-path-3" d="M30,20 60,20 40,50 a18,15 0 1,1 -12,19"/>
          </defs>
          <path class="demo__numbers-path" 
                d="M-10,20 60,20 40,50 a18,15 0 1,1 -12,19 
                  Q25,44 34.4,27.4
                  l7,-7 a16,16 0 0,1 22.6,22.6 l-30,30 l35,0 L69,73 
                  a20,10 0 0,1 20,10 a17,17 0 0,1 -34,0 L55,83 
                  l0,-61 L40,28" />
        </svg>
      </div>
    </div>

</template>
<script>
export default {
  name: 'Start'
  
}
</script>
<style scoped>
  *, *:before, *:after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  background: #32386D;
  font-family: Helvetica, Arial, sans-serif;
}

.demo {
  position: relative;
  left: 32%;
  /* top: 15vh; */
  width: 50vw;
  height: 15vh;
  margin: auto;
  /* padding: 10px; */
  border-radius: 20px;
  transform: translateX(-50%);
  
}
.demo__colored-blocks {
  overflow: hidden;
  position: absolute;
  left: 50%;
  top: 0;
  width: 500px;
  height: 100%;
  margin-left: -250px;
  padding: 10px;
  border-radius: 20px;
  perspective: 1000px;
  -webkit-animation: demoAnim 4s ease-in-out infinite, contAnim 4s infinite;
          animation: demoAnim 4s ease-in-out infinite, contAnim 4s infinite;
}
.demo__colored-blocks-rotater {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  border-radius: inherit;
  -webkit-animation: rotation 1.3s linear infinite;
          animation: rotation 1.3s linear infinite;
}
.demo__colored-blocks-inner {
  overflow: hidden;
  position: relative;
  height: 100%;
  background: #32386D;
  border-radius: inherit;
}
.demo__colored-block {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300%;
  height: 300%;
  transform-origin: 0 0;
}
.demo__colored-block:nth-child(1) {
  transform: rotate(0deg) skewX(-30deg);
  background-color: #FD3359;
}
.demo__colored-block:nth-child(2) {
  transform: rotate(120deg) skewX(-30deg);
  background-color: #F4D302;
}
.demo__colored-block:nth-child(3) {
  transform: rotate(240deg) skewX(-30deg);
  background-color: #21BDFF;
}
.demo__inner {
  overflow: hidden;
  position: relative;
  width: 100%;
  height: 100%;
}
.demo__numbers {
  overflow: visible;
  position: absolute;
  left: 50%;
  top: 50%;
  width: 100px;
  height: 100px;
  margin-left: -50px;
  margin-top: -50px;
}
.demo__numbers-path {
  fill: none;
  stroke-width: 10px;
  stroke: #fff;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke-dasharray: 0, 518.055065155;
  stroke-dashoffset: 0;
  -webkit-animation: numAnim 4s ease-in-out infinite;
          animation: numAnim 4s ease-in-out infinite;
  opacity: 0;
}
.demo__text {
  position: absolute;
  left: 50%;
  top: 0;
  width: 500px;
  height: 100%;
  margin-left: -250px;
  text-align: center;
  line-height: 140px;
  font-size: 100px;
  color: #fff;
  text-transform: uppercase;
  letter-spacing: 15px;
  transform: translateX(10px);
  -webkit-animation: hideText 4s infinite;
          animation: hideText 4s infinite;
}

@-webkit-keyframes contAnim {
  15%, 100% {
    margin-left: -250px;
    width: 500px;
  }
  25%, 90% {
    margin-left: -70px;
    width: 140px;
  }
}

@keyframes contAnim {
  15%, 100% {
    margin-left: -250px;
    width: 500px;
  }
  25%, 90% {
    margin-left: -70px;
    width: 140px;
  }
}
@-webkit-keyframes numAnim {
  15% {
    stroke-dasharray: 0, 518.055065155;
    stroke-dashoffset: 0;
    opacity: 0;
  }
  25%, 41% {
    opacity: 1;
    stroke-dasharray: 144.4256591797, 518.055065155;
    stroke-dashoffset: -40;
  }
  53%, 66% {
    stroke-dasharray: 136.0216217041, 518.055065155;
    stroke-dashoffset: -227.238697052;
  }
  76% {
    stroke-dasharray: 113.4751205444, 518.055065155;
    stroke-dashoffset: -445.8995704651;
  }
  88%, 100% {
    stroke-dasharray: 72.1554946899, 518.055065155;
    stroke-dashoffset: -445.8995704651;
  }
  92% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
@keyframes numAnim {
  15% {
    stroke-dasharray: 0, 518.055065155;
    stroke-dashoffset: 0;
    opacity: 0;
  }
  25%, 41% {
    opacity: 1;
    stroke-dasharray: 144.4256591797, 518.055065155;
    stroke-dashoffset: -40;
  }
  53%, 66% {
    stroke-dasharray: 136.0216217041, 518.055065155;
    stroke-dashoffset: -227.238697052;
  }
  76% {
    stroke-dasharray: 113.4751205444, 518.055065155;
    stroke-dashoffset: -445.8995704651;
  }
  88%, 100% {
    stroke-dasharray: 72.1554946899, 518.055065155;
    stroke-dashoffset: -445.8995704651;
  }
  92% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
@-webkit-keyframes rotation {
  to {
    transform: rotate(360deg);
  }
}
@keyframes rotation {
  to {
    transform: rotate(360deg);
  }
}
@-webkit-keyframes demoAnim {
  15% {
    border-radius: 20px;
    transform: rotate(0);
  }
  30%, 43% {
    border-radius: 50%;
    transform: rotate(360deg);
  }
  52%, 65% {
    border-radius: 0;
    transform: rotate(720deg);
  }
  78%, 90% {
    border-radius: 50%;
    transform: rotate(1080deg);
  }
  100% {
    border-radius: 20px;
    transform: rotate(1440deg);
  }
}
@keyframes demoAnim {
  15% {
    border-radius: 20px;
    transform: rotate(0);
  }
  30%, 43% {
    border-radius: 50%;
    transform: rotate(360deg);
  }
  52%, 65% {
    border-radius: 0;
    transform: rotate(720deg);
  }
  78%, 90% {
    border-radius: 50%;
    transform: rotate(1080deg);
  }
  100% {
    border-radius: 20px;
    transform: rotate(1440deg);
  }
}
@-webkit-keyframes hideText {
  15%, 100% {
    opacity: 1;
  }
  20%, 96% {
    opacity: 0;
  }
}
@keyframes hideText {
  15%, 100% {
    opacity: 1;
  }
  20%, 96% {
    opacity: 0;
  }
}
</style>