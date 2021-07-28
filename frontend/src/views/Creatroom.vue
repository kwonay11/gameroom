<template>
  
  <div>

  <p class="head">처음에 시작할 게임을 선택하고 시작하기 버튼을 눌러주세요.<br>
  게임중에 게임을 변경할 수 있습니다.</p>
  <div class="cols mt-5">
      <div class="col" ontouchstart="this.classList.toggle('hover');">
        <div class="container">
          <div class="front" style="background-image: url(https://unsplash.it/500/500/)">
            <div class="inner">
              <p>몸으로 말해요</p>
            </div>
          </div>
          <div class="back">
            <div class="inner">
              <p>한 사람이 몸으로만 단어설명을 하고 다른 사람들이 맞추는 게임</p>
               <button class="start">
                <span class="circle" aria-hidden="true">
                  <span class="icon arrow"></span>
                </span>
                <span class="button-text">start</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="col" ontouchstart="this.classList.toggle('hover');">
        <div class="container">
          <div class="front" style="background-image:url(https://unsplash.it/510/510/)">
            <div class="inner">
              <p>캐치마인드</p>
            </div>
          </div>
          <div class="back">
            <div class="inner">
              <p>한 사람이 그림으로 그려 다른 사람들이 맞추는 게임</p>
              <button class="start">
                <span class="circle" aria-hidden="true">
                  <span class="icon arrow"></span>
                </span>
                <span class="button-text">start</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="col" ontouchstart="this.classList.toggle('hover');">
        <div class="container">
          <div class="front" style="background-image: url(https://unsplash.it/502/502/)">
            <div class="inner">
              <p>고요속의 외침</p>
            </div>
          </div>
          <div class="back">
            <div class="inner">
              <p>음성 없이 제시어를 설명하는 게임</p>
              <button class="start">
                <span class="circle" aria-hidden="true">
                  <span class="icon arrow"></span>
                </span>
                <span class="button-text">start</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="col" ontouchstart="this.classList.toggle('hover');">
        <div class="container">
          <div class="front" style="background-image: url(https://unsplash.it/503/503/)">
            <div class="inner">
              <p>노래방</p>
            </div>
          </div>
          <div class="back">
            <div class="inner">
              <p>랜선 노래방! <br>다른사람의 노래를 듣고 평가 해보아요!</p>
              <button class="start">
                <span class="circle" aria-hidden="true">
                  <span class="icon arrow"></span>
                </span>
                <span class="button-text">start</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="col" ontouchstart="this.classList.toggle('hover');">
        <div class="container">
          <div class="front" style="background-image: url(https://unsplash.it/504/504/">
            <div class="inner">
              <p>순간포착</p>
            </div>
          </div>
          <div class="back">
            <div class="inner">
              <p>순식간에 지나가는 사진을 맞춰보아요!</p>
              <button class="start">
                <span class="circle" aria-hidden="true">
                  <span class="icon arrow"></span>
                </span>
                <span class="button-text">start</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="col" ontouchstart="this.classList.toggle('hover');">
        <div class="container">
          <div class="front" style="background-image: url(https://unsplash.it/505/505/)">
            <div class="inner">
              <p>글자 맞추기</p>

            </div>
          </div>
          <div class="back">
            <div class="inner">
              <p>구멍뚫린 단어를 맞춰라!</p>
              <button class="start">
                <span class="circle" aria-hidden="true">
                  <span class="icon arrow"></span>
                </span>
                <span class="button-text">start</span>
              </button>
            </div>
          </div>
        </div>
     
      </div>
     </div>
  </div>
</template>

<script>
export default {
    name:"Creatroom"

}
</script>

<style>
.cols{
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -ms-flex-wrap: wrap;
      flex-wrap: wrap;
  -webkit-box-pack: center;
      -ms-flex-pack: center;
          justify-content: center;
}

.col{
  width: calc(25% - 2rem);
  margin: 1rem;
  cursor: pointer;
}

.container{
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
  -webkit-perspective: 1000px;
          perspective: 1000px;
}

.front,
.back{
  background-size: cover;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.25);
  border-radius: 10px;
  background-position: center;
  -webkit-transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  -o-transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1), -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  -webkit-backface-visibility: hidden;
          backface-visibility: hidden;
  text-align: center;
  min-height: 280px;
  height: auto;
  border-radius: 10px;
  color: #fff;
  font-size: 1.5rem;
}

.back{
  background: #a7aed6;
  background: -webkit-linear-gradient(45deg,  #cedce7 0%,#5751a3 100%);
  background: -o-linear-gradient(45deg,  #cedce7 0%,#5751a3  100%);
  background: linear-gradient(45deg,  #cedce7 0%,#5751a3  100%);
}

.front:after{
  position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
    width: 100%;
    height: 100%;
    content: '';
    display: block;
    opacity: .6;
    background-color: #000;
    -webkit-backface-visibility: hidden;
            backface-visibility: hidden;
    border-radius: 10px;
}
.container:hover .front,
.container:hover .back{
    -webkit-transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    -o-transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1), -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
}

.back{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
}

.inner{
    -webkit-transform: translateY(-50%) translateZ(60px) scale(0.94);
            transform: translateY(-50%) translateZ(60px) scale(0.94);
    top: 50%;
    position: absolute;
    left: 0;
    width: 100%;
    padding: 2rem;
    -webkit-box-sizing: border-box;
            box-sizing: border-box;
    outline: 1px solid transparent;
    -webkit-perspective: inherit;
            perspective: inherit;
    z-index: 2;
}

.container .back{
    -webkit-transform: rotateY(180deg);
            transform: rotateY(180deg);
    -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
}

.container .front{
    -webkit-transform: rotateY(0deg);
            transform: rotateY(0deg);
    -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
}

.container:hover .back{
  -webkit-transform: rotateY(0deg);
          transform: rotateY(0deg);
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
}

.container:hover .front{
  -webkit-transform: rotateY(-180deg);
          transform: rotateY(-180deg);
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
}

.front .inner p{
  font-size: 2rem;
  margin-bottom: 2rem;
  position: relative;
}

.front .inner p:after{
  content: '';
  width: 4rem;
  height: 2px;
  position: absolute;
  background: #C6D4DF;
  display: block;
  left: 0;
  right: 0;
  margin: 0 auto;
  bottom: -.75rem;
}

.front .inner span{
  color: rgba(255,255,255,0.7);
  font-family: 'Montserrat';
  font-weight: 300;
}

@media screen and (max-width: 64rem){
  .col{
    width: calc(33.333333% - 2rem);
  }
}

@media screen and (max-width: 48rem){
  .col{
    width: calc(50% - 2rem);
  }
}

@media screen and (max-width: 32rem){
  .col{
    width: 100%;
    margin: 0 0 2rem 0;
  }
}
.head {
  color: #ffffff;
  font-family: Roboto;
  font-weight: Regular;
  font-size: 24px;
  opacity: 1;
  text-shadow: 0px 4px 4px #060606;
}

button {
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
  background: transparent;
  padding: 0;
  font-size: inherit;
  font-family: inherit;
}
button.start {
  width: 12rem;
  height: auto;
}
button.start .circle {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: relative;
  display: block;
  margin: 0;
  width: 3rem;
  height: 3rem;
  background: #282936;
  border-radius: 1.625rem;
}
button.start .circle .icon {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: absolute;
  top: 0;
  bottom: 0;
  margin: auto;
  background: #fff;
}
button.start .circle .icon.arrow {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  left: 0.625rem;
  width: 1.125rem;
  height: 0.125rem;
  background: none;
}
button.start .circle .icon.arrow::before {
  position: absolute;
  content: "";
  top: -0.25rem;
  right: 0.0625rem;
  width: 0.625rem;
  height: 0.625rem;
  border-top: 0.125rem solid #fff;
  border-right: 0.125rem solid #fff;
  transform: rotate(45deg);
}
button.start .button-text {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  padding: 0.75rem 0;
  margin: 0 0 0 1.85rem;
  color: #282936;
  font-weight: 700;
  line-height: 1.6;
  text-align: center;
  text-transform: uppercase;
}
button:hover .circle {
  width: 100%;
}
button:hover .circle .icon.arrow {
  background: #fff;
  transform: translate(1rem, 0);
}
button:hover .button-text {
  color: #fff;
}
</style>