const test = window.addEventListener('load', () => {
    const canvas = document.querySelector("#canvas")
    const ctx = canvas.getContext('2d');

    // const aa = 'asdfasdf'
    console.log('durldurldurl')
    console.log(canvas)
    console.log(ctx)
        // resizing
    canvas.height = window.innerHeight
    canvas.width = window.innerWidth


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
    document.getElementById("erase").addEventListener("click", function() {
        if (ctx != null) {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
        }
    })

    let painting = false

    function startPosition(e) {
        painting = true
        draw(e)
    }

    function finishedPosition() {
        painting = false
        ctx.beginPath()
    }

    function reset() {
        painting = false
    }

    function draw(e) {
        if (!painting) return;
        ctx.lineWidth = 10;
        ctx.lineCap = 'round'
        ctx.strokeStyle = 'red'

        ctx.lineTo(e.clientX, e.clientY)
        ctx.stroke();
        ctx.beginPath()
        ctx.moveTo(e.clientX, e.clientY)
    }

    canvas.addEventListener('mousedown', startPosition);
    canvas.addEventListener('mouseup', finishedPosition);
    canvas.addEventListener('mousemove', draw);
    canvas.addEventListener('click', reset);

})






const draw = { test }

export default draw