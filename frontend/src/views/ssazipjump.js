import Header from '@/components/common/Header.vue';
import SSazipjumpModal from '@/components/ssazipjump/SSazipjumpModal.vue';
import './../css/ssazip-jump.css'
import './ssazipjump.js'

export default {
  name: "SSazipJump",
  data: function (){ 
    return {
      time: 0,
      fTime: 0,
      otime: 0,
      ofTime: 0,
      users:[
        {userId: '이장섭', jump:0},
        {userId: '권희은', jump:0},
        {userId: '김태현', jump:0},
        {userId: '차은채', jump:0},
        {userId: '안기훈', jump:0},
        // {userId: '박용미', jump:0},
        // {userId: '이미영', jump:0}
      ],
      users2:[
        {userId: '이장솝', jump:0},
        {userId: '권희운', jump:0},
        {userId: '김태헌', jump:0},
        {userId: '차은차', jump:0},
        {userId: 'ddd', jump:0},
        // {userId: '박용미', jump:0},
        // {userId: '이미영', jump:0}
      ],
      dinos:[],
      dinos2:[],
      userId: '차은차',
      userIdx: 0,
      userTeam: 0,
      status: true,
    }
  },
  components: {
    Header,
    SSazipjumpModal,
  },
  methods: {
    drawSsazipgame(){
      // 싸집이 이미지 선언
      const ssazip = document.getElementById("ssazip")
      const ssazip2 = document.getElementById("ssazip2")
      const ssazipMe = document.getElementById("ssazipMe")
      const ssazipMe2 = document.getElementById("ssazipMe2")

      // canvas 선언
      let canvas = document.getElementById('canvas')
      let ctx = canvas.getContext('2d')
      let canvas2 = document.getElementById('canvas2')
      let ctx2 = canvas2.getContext('2d')
      // 캔버스 크기 지정
      canvas.width = 1200;
      canvas.height = 350
      canvas2.width = 1200;
      canvas2.height = 350

      const leftSpace = 50; //싸집이들 왼쪽 공간

      //게임참여 유저정보 확인하여 객체 생성 및 본인 정보(팀, 위치) 파악
      // 1팀
      this.users.forEach((user,i)=>{
        // 캐릭터 속성
        let dino = {
          // 캐릭터 등장 좌표(왼쪽 상단으로부터)
          x: leftSpace+70*i,
          y: 220,
          width : 50,
          height : 50,
          idx: i,
          me: 0,
          // 캐릭터 그리기 함수(생성)
          draw(){
            ctx.fillStyle = 'green'
            ctx.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
            if (this.me == 0){
              ctx.drawImage(ssazip, this.x, this.y, this.width, this.height);
            } else {
              ctx.drawImage(ssazipMe, this.x, this.y, this.width, this.height);
            }
          }
        }
        if (user.userId == this.userId){ //본인과 같은 정보가 있다면 본인정보에 넣어주기
          this.userIdx = i
          this.userTeam = 1
          dino.me = 1 //본인 표시
          dino.height = 70
          dino.y=200

        }
        this.dinos.push(dino) // 팀원수 확인해서 속성넣은 객체리스트로 만들어주기

      })
      // 2팀
      this.users2.forEach((user,i)=>{
        // 캐릭터 속성
        let dino = {
          // 캐릭터 등장 좌표(왼쪽 상단으로부터)
          x: leftSpace+70*i,
          y: 220,
          width : 50,
          height : 50,
          idx: i, 
          me: 0,
          // 캐릭터 그리기 함수(생성)
          draw(){
            ctx2.fillStyle = 'green'
            ctx2.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
            if (this.me == 0){
              ctx2.drawImage(ssazip2, this.x, this.y, this.width, this.height);
            } else {
              ctx2.drawImage(ssazipMe2, this.x, this.y, this.width, this.height);
            }
          }
        }
        if (user.userId == this.userId){ //본인과 같은 정보가 있다면 본인정보에 넣어주기
          this.userIdx = i
          this.userTeam = 2
          dino.me = 1 //본인 표시
          dino.height = 70
          dino.y=200

        }
        this.dinos2.push(dino) // 팀원수 확인해서 속성넣은 객체리스트로 만들어주기

      })
      console.log(this.dinos, this.dinos2)
      ///////////////////////////// 장애물 생성 ////////////////////////////////
      let cactusarr = []; //1팀 장애물 리스트
      let cactusarr2 = []; //2팀 장애물 리스트
      let cactusCnt=0 // 1팀 연속 나온 장애물 개수
      let cactusCnt2=0  // 2팀 연속 나온 장애물 개수
      const cactusCntArr=[1,1,3,2,2,3,4,2,2,3,1] //장애물 1초마다 생성되는 개수 (원소(쉬고)원소(쉬고)...)
      let cactusCntIdx = 0 //1팀 장애물 연속 생성개수 담은 리스트에서 몇번째 연속생성개수 인지 처리해주는 인덱스
      let cactusCntIdx2 = 0 //2팀 장애물 연속 생성개수 담은 리스트에서 몇번째 연속생성개수 인지 처리해주는 인덱스
      const obstacle1= document.getElementById("obstacle1") // eslint-disable-line no-unused-vars
      const obstacle2= document.getElementById("obstacle2") // eslint-disable-line no-unused-vars
      const obstacle3= document.getElementById("obstacle3") // eslint-disable-line no-unused-vars
      const obstacle4= document.getElementById("obstacle4") // eslint-disable-line no-unused-vars
    
      // 1팀 장애물 속성
      class Cactus {
        // 장애물 생성기
        constructor(){
          // 장애물 등장 위치(왼쪽 상단으로부터)
          this.x = 1200,
          this.y = 220,
          this.width = 50,
          this.height = 50,
          this.type = Math.floor(Math.random()*(4-1+1))+1
          // 나온 장애물이 설정했던 장애물 개수만큼 나왔다면 다시 쉬는블록 추가
          if (cactusCnt == cactusCntArr[cactusCntIdx]){
            this.type = 0
            cactusCnt =0
            cactusCntIdx++ //쉬었으니까 다음 장애물개수 생성하도록 1증가
          } else {
            cactusCnt++ //아직 쉬지않으니 연속 장애물 개수 추가
          }
        }
        draw() {
          if(this.type!=0){
            ctx.fillStyle="red"
            ctx.fillRect(this.x, this.y, this.width, this.height);
            ctx.drawImage(eval('obstacle'+this.type), this.x, this.y, this.width, this.height);
          }else{
            this.x = 0,
            this.y = 0
          }
          
        }
      }
      // 2팀 장애물 속성
      class Cactus2 {
        // 장애물 생성기
        constructor(){
          // 장애물 등장 위치(왼쪽 상단으로부터)
          this.x = 1200,
          this.y = 220,
          this.width = 50,
          this.height = 50,
          this.type = Math.floor(Math.random()*(4-1+1))+1
          if (cactusCnt2 == cactusCntArr[cactusCntIdx2]){
            this.type = 0
            cactusCnt2 =0
            cactusCntIdx2++ //쉬었으니까 다음 장애물개수 생성하도록 1증가
          } else {
            cactusCnt2++ //아직 쉬지않으니 연속 장애물 개수 추가
          }
        }
        draw() {
          if(this.type!=0){
            ctx2.fillStyle="red"
            ctx2.fillRect(this.x, this.y, this.width, this.height);
            ctx2.drawImage(eval('obstacle'+this.type), this.x, this.y, this.width, this.height);
          }else{
            this.x = 0,
            this.y = 0
          }
        }
      }

      ///////////////////////////// 애니메이션 동작 //////////////////////////////////
      
       // 1초에 60번 실행할 함수
      let animation; //애니메이션 작동 변수
      const frame = (timestamp) => {
        animation = requestAnimationFrame(frame) //frame을 1초에 60번 실행해줘
        ctx.clearRect(0,0, canvas.width, canvas.height); //canvas 지워줘(잔상 안 남게)
        ctx2.clearRect(0,0, canvas2.width, canvas2.height); //canvas2 지워줘(잔상 안 남게)
        
        // 시간 세기 시작
        const cntTime =  parseInt(timestamp/1000) //1초로 나눔
        const fTime = parseInt(timestamp/16.7) // 1/60초마다 추가
 
        if (fTime>this.fTime){ //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
          this.fTime++;
        }

        // 장애물 생성시간 조정 (2초로 설정)
        // if (this.time%2 == 0){
        //1초마다 생성
        if (cntTime>this.time){
          this.time++;
          var cactus = new Cactus()
          var cactus2 = new Cactus2()
          cactusarr.push(cactus) //2초마다 생성되면 장애물 리스트에 넣기
          cactusarr2.push(cactus2) //2초마다 생성되면 장애물 리스트에 넣기
        }
        // if (cntTime>this.time){ //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
        //   this.time++;
        // }

        // 애니메이션을 1/60초마다 이루어지게 조정
        if (fTime > this.fTime){
          cactusarr.forEach((a, i, o)=>{  //장애물 리스트에서 하나씩 빼서 그려주기
            //이미 지나간 장애물은 삭제
            if (i+1 < o.length){
              if (o[i+1].x < -50) {
                o.splice(i, 1)
              }
            }
            //장애물 점점 왼쪽으로 가게 만들기
            a.x -= 4; //장애물 속도
            this.dinos.forEach((dino)=>{
              checkCollision(dino, a); //모든 장애물과 충돌확인     
            }),
            a.draw(); //1팀 장애물 그리기
          })
          cactusarr2.forEach((a, i, o)=>{  //장애물 리스트에서 하나씩 빼서 그려주기
            //이미 지나간 장애물은 삭제
            if (i+1 < o.length){
              if (o[i+1].x < -50) {
                o.splice(i, 1)
              }
            }
            //장애물 점점 왼쪽으로 가게 만들기
            a.x -= 4; //장애물 속도
            this.dinos2.forEach((dino)=>{
              checkCollision(dino, a); //모든 장애물과 충돌확인
            }),
            a.draw(); //2팀 장애물 그리기
          })
          ///////////////////////////////점프 애니메이션 //////////////////////////////////
          // 1팀 점프
          this.dinos.forEach((dino,i)=>{
            if(dino.me == 0){
              // 점프
              if (this.users[i].jump == true){
                this.dinos[i].y -= 5;  //점프속도
              }
              // 착지
              if (this.users[i].jump == false) {
                if(dino.y<220) {
                  this.dinos[i].y += 5; //착지속도
                }
              }
              // 점프 중지
              if ( this.dinos[i].y<=90){
                this.users[i].jump = false;
              }
            } else {
              // 점프
              if (this.users[i].jump == true){
                this.dinos[i].y -= 5;  //점프속도
              }
              // 착지
              if (this.users[i].jump == false) {
                if(dino.y<200) {
                  this.dinos[i].y += 5; //착지속도
                }
              }
              // 점프 중지
              if ( this.dinos[i].y<=70){
                this.users[i].jump = false;
              }
            }
             
            this.dinos[i].draw(); // 1팀 싸집이들 그려주기

          })
          // 2팀 점프
          this.dinos2.forEach((dino,i)=>{
            if (dino.me == 0) {
              // 점프
              if (this.users2[i].jump == true){
                this.dinos2[i].y -= 4;  //점프속도
              }
              // 착지
              if (this.users2[i].jump == false) {
                if(dino.y<220) {
                  this.dinos2[i].y += 4; //착지속도
                }
              }
              // 점프 중지
              if ( this.dinos2[i].y<=90){
                this.users2[i].jump = false;
              }
            } else {
              // 점프
              if (this.users2[i].jump == true){
                this.dinos2[i].y -= 4;  //점프속도
              }
              // 착지
              if (this.users2[i].jump == false) {
                if(dino.y<200) {
                  this.dinos2[i].y += 4; //착지속도
                }
              }
              // 점프 중지
              if ( this.dinos2[i].y<=70){
                this.users2[i].jump = false;
              }
            }
            this.dinos2[i].draw(); // 2팀 싸집이들 그려주기
          })
        }
      }
      frame();

      // 충돌체크함수
      const checkCollision= (dino, cactus)=>{
        let xDiff = cactus.x - (dino.x +dino.width);
        let yDiff = cactus.y - (dino.y + dino.height);
        const ssazipbg = document.getElementById("ssazipbg")
        const ssazipbg2 = document.getElementById("ssazipbg2")
        if(dino.x<=cactus.x+cactus.width){
          if (xDiff < -10 && yDiff < -10){
            cancelAnimationFrame(animation) //게임중단
            console.log(xDiff)
            ssazipbg.style.animation = 'paused';
            ssazipbg2.style.animation = 'paused';
            // this.status = false
          }
        }
      }
    },
  },
  mounted() {
    this.drawSsazipgame()
    //스페이스바 누르면 점프값 변경 
    document.addEventListener('keydown', (e) =>{
      // 본인이 1팀일 경우
      if (this.userTeam == 1){
        if(this.dinos[this.userIdx].y== 200){ //착지해야만 점프가능
          if (e.code === 'Space'){
            this.users[this.userIdx].jump = true;
          }
        }
      } 
      // 본인이 2팀일 경우
      if (this.userTeam == 2){
        if(this.dinos2[this.userIdx].y== 200){ //착지해야만 점프가능
          if (e.code === 'Space'){
            this.users2[this.userIdx].jump = true;
          }
        }
      }
    })

    document.addEventListener('keydown', (e) =>{
      //착지해야만 점프가능
      if(this.dinos2[4].y== 220){
        if (e.code === 'Enter'){
          this.users2[4].jump = true;
        }
      }
    })
  }
}