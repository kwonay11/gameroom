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

    props:{
       session: Object,
    },

    data() {
      return {
        x: undefined,
        y: undefined,
        coor: {},
      }
    },

  
   
  mounted: function() {
    this.pen(this.session)

  },
    created() {
    // console.log('캐치마인드세션')
    // console.log(this.session)
    this.session.on('signal:catch-mind', () => {
        console.log('캐치마인드임!!')
        // console.log(JSON.parser(event.data))
        // console.log(this.coor)
    })
    },

  methods: {
    pen(session) {

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
          console.log('드로우의 이벤트')
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

      function aa(e) {
        console.log(this.session)
        

        // const coordinate = {
        //   clientX: e.clientX, cilentY: e.clientY,
        // }

        
              console.log(JSON.stringify(e))
              session.signal({
                  data: JSON.stringify(e),
                  // data: coordinate,
                  type: 'catch-mind'
              })
              .then(() => {
                  console.log('캐치마인드 시그널 보냄')
              })
              .catch(err => {
                  console.log(err)
                  console.log('여기에러????????')
              })

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
              console.log('클리어')
          }
      })

      canvas.addEventListener('mousedown', startPosition);
      canvas.addEventListener('mousedown', aa);
      canvas.addEventListener('mouseup', finishedPosition);
      canvas.addEventListener('mouseup', aa);
      canvas.addEventListener('mouseout', finishedPosition);
      canvas.addEventListener('mouseout',  aa);
      canvas.addEventListener('mousemove', draw);
      canvas.addEventListener('mousemove', aa);

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
