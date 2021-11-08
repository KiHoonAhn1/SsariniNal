<template>
  <div>
    <canvas id="canvas"></canvas>
      <div style="display:none;">
        <img id="ssazip" class="t"
            src="@/assets/ssazip-blue.png">
      </div>
      <div style="display:none;">
        <img id="obstacle1"
            src="@/assets/obstacle1.png">
      </div>
  </div>
</template>

<script>
export default {
  name: "SSazip",
  data: function (){
    return {
      time: 0,
      fTime: 0,
      SSazips: [{jump:0, username:'장섭'}, {jump:0, username:'태현'}],
    }
  },
  props:{
    ssazip:{},
  },

  methods: {
    ssazipGame: function(){
      const ssazip = document.getElementById("ssazip")
      const obstacle1 = document.getElementById("obstacle1")

      // canvas는 mounted이후에 사용이 가능하다.
      let canvas = document.getElementById('canvas')
      let ctx = canvas.getContext('2d')
      
      // 캔버스 크기 지정
      canvas.width = window.innerWidth/100*71
      canvas.height = window.innerHeight/100*40
      
      // 캐릭터 속성
      let dino = {
        // 캐릭터 등장 좌표(왼쪽 상단으로부터)
        x: 200,
        y: window.innerHeight/100*50.3,
        // 캐릭터 크기
        width : 50,
        height : 50,
        // 캐릭터 그리기 함수(생성)
        draw(){
          ctx.fillStyle = 'green'
          ctx.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
          ctx.drawImage(ssazip, this.x, this.y, this.width, this.height);
          console.log(ssazip)
        }
      }

      // 장애물 속성
      class Cactus {
        // 장애물 생성기
        constructor(){
          // 장애물 등장 위치(왼쪽 상단으로부터)
          this.x = window.innerWidth/100*71,
          this.y = window.innerHeight/100*25.3,
          this.width = 50,
          this.height = 50
        }
        draw() {
          ctx.fillStyle="red"
          ctx.fillRect(this.x, this.y, this.width, this.height);
          ctx.drawImage(obstacle1, this.x, this.y, this.width, this.height);
          console.log(ssazip)
        }
      }

      //장애물 리스트
      let cactusarr = [];
      let animation;

      // 1초에 60번 실행할 함수
      const frame = (timestamp) => {
        animation = requestAnimationFrame(frame) //frame을 1초에 60번 실행해줘
        ctx.clearRect(0,0, canvas.width, canvas.height); //canvas 지워줘(잔상 안 남게)
        // 시간 세기 시작

        const cntTime =  parseInt(timestamp/1000) //1초로 나눔
        const fTime = parseInt(timestamp/16.7) // 1/60초마다 추가

        if (cntTime>this.time){ //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
          this.time++;
        }
        if (fTime>this.fTime){ //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
          this.fTime++;
        }
        // 장애물 생성시간 조정 (3초로 설정)
        if (this.time%2 == 0){
          this.time++;
          var cactus = new Cactus()
          cactusarr.push(cactus) //2초마다 생성되면 장애물 리스트에 넣기
        }
        // 장애물 이동을 1/60초마다 이루어지게 조정
        if (fTime > this.fTime){
          cactusarr.forEach((a, i, o)=>{  //장애물 리스트에서 하나씩 빼서 그려주기
            //이미 지나간 장애물은 삭제
            if (a.x < 0) {
              o.splice(i, 1)
            }
            //장애물 점점 왼쪽으로 가게 만들기
            a.x -= 4; //장애물 속도

            checkCollision(dino, a); //모든 장애물과 충돌확인

            a.draw();
          })
          // 점프
          if (this.SSazips[0].jump == true){
            dino.y -= 4;  //점프속도
          }
          // 착지
          if (this.SSazips[0].jump == false) {
            if(dino.y<window.innerHeight/100*25.3) {
              dino.y += 4; //착지속도
            }
          }
          // 점프 중지
          if (dino.y<=100){
            this.SSazips[0].jump = false;
          }
        }
        dino.draw();
      };
      frame();

      //스페이스바 누르면 점프값 변경 
      document.addEventListener('keydown', (e) =>{
        //착지해야만 점프가능
        if(dino.y== window.innerHeight/100*25.3){
          if (e.code === 'Space'){
            this.SSazips[0].jump = true;
          }
        }
      })

      // 충돌체크함수
      const checkCollision =(dino, cactus)=>{
        let xDiff = cactus.x - (dino.x +dino.width);
        let yDiff = cactus.y - (dino.y + dino.height);
        const ssazipbg = document.getElementById("ssazipbg")
        if (xDiff < -10 && yDiff < -10){
          cancelAnimationFrame(animation) //게임중단
          ssazipbg.style.animation = 'paused';

        }
      }
      },
      
      

  },
  mounted() {
    this.ssazipGame()
  }
}
</script>

<style>

</style>