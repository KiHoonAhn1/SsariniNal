<template>
  <div>
    <div class="temp"></div>
    <!-- 좌측 게임 -->
    <div class="ssazip-game-container">
      <div id="ssazipbg" class="ssazip-game-background">
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
    </div>
    <!-- 우측 라운드 표시 -->
    <div class="ssazip-round-container">
      <div class="ssazip-round-inner-container">
        <div class="ssazip-round-item" style="background-color:#9EACDD; margin-top:1.2vh;">
          <div class="ssazip-round-item-inner">
            <div class="ssazip-round-item-title">현재 게임</div>

          </div>
        </div>
        <div class="ssazip-round-item" style="background-color:#EAC16F; margin-top:2.5vh;">
          <div class="ssazip-round-item-inner">
            <div class="ssazip-round-item-title">다음 게임</div>
          </div>
        </div>

        <div class="ssazip-round-item" style="background-color:#B9D272;margin-top:2.5vh;">
          <div class="ssazip-round-item-inner">
            <div class="ssazip-round-item-title">3 ROUND</div>
          </div>
        </div>
      </div>
      <!-- <div class="now-playing-game"></div> -->
    </div>
  </div>
</template>

<script>
import '../../css/ssazip-jump.css'
export default {
  name: "SSazipJumpTemp",
  data: function (){ 
    return {
      time: 0,
      fTime: 0,
      obstacleArr:[],
      users:[
        {userId: '이장섭', jump:0},
        {userId: '권희은', jump:0},
        {userId: '김태현', jump:0},
        {userId: '차은채', jump:0},
        {userId: '안기훈', jump:0},
        {userId: '박용미', jump:0},
        {userId: '이미영', jump:0}
      ],
      dinos:[],
      userId: '이미영',
      userIdx: 0,
    }
  },
  methods: {
    drawSsazip(user,i,o){
      const ssazip = document.getElementById("ssazip")
      console.log(user,i,o)
      // const obstacle1 = document.getElementById("obstacle1")

      // canvas는 mounted이후에 사용이 가능하다.
      let canvas = document.getElementById('canvas')
      let ctx = canvas.getContext('2d')
      
      // 캔버스 크기 지정
      canvas.width = window.innerWidth/100*71
      canvas.height = window.innerHeight/100*40

      const leftSpace = 50; //싸집이들 왼쪽 공간

      
      // 캐릭터 속성
      let dino = {
        // 캐릭터 등장 좌표(왼쪽 상단으로부터)
        x: leftSpace+70*i,
        y: window.innerHeight/100*25.3,
        // 캐릭터 크기
        width : 50,
        height : 50,
        // 캐릭터 그리기 함수(생성)
        draw(){
          ctx.fillStyle = 'green'
          ctx.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
          ctx.drawImage(ssazip, this.x, this.y, this.width, this.height);
          // console.log(ssazip)
        }
      }
      this.dinos.push(dino)
      // dino.draw();
       //장애물 리스트
      // let cactusarr = [];
      let animation;
      console.log(animation)
      const frame = (timestamp) => {
        animation = requestAnimationFrame(frame) //frame을 1초에 60번 실행해줘
        ctx.clearRect(this.dinos[i].x, 0, dino.width, canvas.height); //canvas 지워줘(잔상 안 남게)
        // 시간 세기 시작

        const cntTime =  parseInt(timestamp/1000) //1초로 나눔
        const fTime = parseInt(timestamp/16.7) // 1/60초마다 추가

        if (cntTime>this.time){ //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
          this.time++;
        }
        if (fTime>this.fTime){ //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
          this.fTime++;
        }
        // 장애물 생성시간 조정 (2초로 설정)
        // if (this.time%2 == 0){
        //   this.time++;
        //   var cactus = new Cactus()
        //   cactusarr.push(cactus) //2초마다 생성되면 장애물 리스트에 넣기
        // }
        // 장애물 이동을 1/60초마다 이루어지게 조정
        // if (fTime > this.fTime){
        //   cactusarr.forEach((a, i, o)=>{  //장애물 리스트에서 하나씩 빼서 그려주기
        //     //이미 지나간 장애물은 삭제
        //     if (a.x < 0) {
        //       o.splice(i, 1)
        //     }
        //     //장애물 점점 왼쪽으로 가게 만들기
        //     a.x -= 4; //장애물 속도
        //     console.log('장애물확인',a,i)
        //     checkCollision(dino, a); //모든 장애물과 충돌확인

        //     a.draw();
        //   })
        // 점프
        if (this.users[i].jump == true){
          this.dinos[i].y -= 4;  //점프속도
        }
        // 착지
        if (this.users[i].jump == false) {
          if(dino.y<window.innerHeight/100*25.3) {
             this.dinos[i].y += 4; //착지속도
          }
        }
        // 점프 중지
        if ( this.dinos[i].y<=100){
          this.users[i].jump = false;
        }
        // }
       
     
        
        this.dinos[i].draw();
        // console.log(this.dinos[i])
      };
      frame();

      
    }

  },
  mounted() {
    
    this.users.forEach((user,i,o)=>{
      this.drawSsazip(user,i,o)
      if (user.userId == this.userId){
        this.userIdx = i
      }
    }),
    //스페이스바 누르면 점프값 변경 
    document.addEventListener('keydown', (e) =>{
      //착지해야만 점프가능
      if(this.dinos[this.userIdx].y== window.innerHeight/100*25.3){
        if (e.code === 'Space'){
          this.users[this.userIdx].jump = true;
          console.log(this.dinos)
        }
      }
    })
  }
}
</script>

<style>

</style>