<template>
  <div>
    <canvas id='canvas' width=650 height=460></canvas>
    <button id="erase" class='button'>지우기 </button>
    <button id="red" class='button'>red </button>
    <button id="blue" class='button'>blue </button>
    <button id="yellow" class='button'>yellow </button>
    <button id="black" class='button'>black </button>
    <button id="green" class='button'>green </button>
  </div>
</template>

<script>
export default {
  name: 'CatchMind',
   
  mounted: function() {
    this.pen()
  },

  methods: {
    pen() {

      const canvas = document.querySelector("#canvas")
      const ctx = canvas.getContext('2d');
      



      // canvas.height = window.innerHeight
      // canvas.width = window.innerWidth


      // // 색을 넣으려면 직사각형이 만들어 지기 전에 색 부터 정의하고 시작
      // ctx.strokeStyle = 'red'
      //     // 선 너비 변경
      // ctx.lineWidth = '5'
      //     // 색 칠해져있는 사각형이 나옴
      //     // x,y,가로, 세로 -> like position:absolute
      // ctx.fillRect(50, 50, 200, 300)

      // // 색 없이 테두리만 있는 직사각형 나옴
      // ctx.strokeStyle = 'blue'
      // ctx.strokeRect(400, 50, 200, 300)

      // // 점 시작 경로
      // ctx.beginPath()
      // ctx.moveTo(500, 500)
      // ctx.lineTo(600, 500)
      // ctx.stroke()


      let painting = false

      function startPosition(e) {
          painting = true
          draw(e)
      }

      function finishedPosition() {
          painting = false
          ctx.beginPath()
      }

      function draw(e) {
          console.log(e)
          if (!painting) return;
          ctx.lineWidth = 5;
          ctx.lineCap = 'round'
          // ctx.strokeStyle = 'black'


          ctx.lineTo(e.clientX-310, e.clientY-330)
          ctx.stroke();
          ctx.beginPath()
          ctx.moveTo(e.clientX-310, e.clientY-330)
      }

      document.getElementById("red").addEventListener("click", function() {
          ctx.strokeStyle = 'red'
      })
      document.getElementById("blue").addEventListener("click", function() {
        ctx.strokeStyle = 'blue'
      })
      document.getElementById("green").addEventListener("click", function() {
        ctx.strokeStyle = 'green'
      })
      document.getElementById("yellow").addEventListener("click", function() {
        ctx.strokeStyle = 'yellow'
      })
      document.getElementById("black").addEventListener("click", function() {
        ctx.strokeStyle = 'black'
      })
      document.getElementById("erase").addEventListener("click", function() {
          if (ctx != null) {
              ctx.clearRect(0, 0, canvas.width, canvas.height);
              console.log('크릴어')
          }
      })

      canvas.addEventListener('mousedown', startPosition);
      canvas.addEventListener('mouseup', finishedPosition);
      canvas.addEventListener('mouseout', finishedPosition);
      canvas.addEventListener('mousemove', draw);

    }
  }
}

</script>

<style scoped>
* {
  box-sizing : border-box;
}#canvas {
  border: 2px solid black;
  background-color: white;
  /* width: 33vw;
  height: 48vh; */
}
.button {
  background: blue;
}
</style>
