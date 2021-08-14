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
