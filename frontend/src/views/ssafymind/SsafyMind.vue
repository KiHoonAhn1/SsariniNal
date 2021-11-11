<template>
  <div class="ssafymind" v-on:mousedown="isMouseDown" v-on:mouseup="isMouseUp">
    <button @click="sendTimeTrigger()">눌러</button>
    <GameOrderModal v-if="ordermodal == true" @getCloseModal="getCloseModal" />
    <!-- <div class="room-title">
      <span id="game-title">싸피마인드</span>
    </div> -->
    <Header
      v-bind:gameTitle="'싸피마인드'"
      v-bind:host="room.host"
      v-on:onDisconnect="onDisconnect()"
    />
    <GameStatus v-bind:teamOrder="room.teamOrder" />
    <div class="ssafymind-center">
      <div class="question-word" v-if="room.quizzes != null">
        {{ room.quizzes[room.quizzes.length - 1].problem }}
      </div>
      <!-- <div class="question-word">{{ 싸집 }}</div> -->
      <Timer v-bind:minutes="minutes" v-bind:seconds="seconds" />
      <div class="turn-notice">당신 차례 입니다! 빨리 그리세요!</div>
      <canvas
        id="jsCanvas"
        class="canvas paintbrush"
        v-on:mousemove="onMouseMove"
        v-on:mousedown="startPainting"
        v-on:mouseup="stopPainting"
        v-on:mouseleave="stopPainting"
        v-on:mouseenter="mouseEnter"
        v-on:contextmenu="handleCM"
      ></canvas>
      <!-- <div class="controls-stop"></div> -->
      <div class="controls">
        <div class="controls_colors" id="jsColors">
          <div class="controls_color jsColor" style="background-color:#E00F0F"></div>
          <div class="controls_color jsColor" style="background-color:#FFA500"></div>
          <div class="controls_color jsColor" style="background-color:#EDCD11"></div>
          <div class="controls_color jsColor" style="background-color:#B0CF3D"></div>
          <div class="controls_color jsColor" style="background-color:#058B05"></div>
          <div class="controls_color jsColor" style="background-color:#9BC8DA"></div>
          <div class="controls_color jsColor" style="background-color:#2377F4"></div>
          <div class="controls_color jsColor" style="background-color:#081765"></div>
          <div class="controls_color jsColor" style="background-color:#EDA3ED"></div>
          <div class="controls_color jsColor" style="background-color:#7118CA"></div>
          <div class="controls_color jsColor" style="background-color:#000000"></div>
          <div class="controls_range">
            <input type="range" id="jsRange" min="0.1" max="8.0" value="2.5" step="0.1" />
          </div>
          <img class="ssafymind-img" id="jsMode-paint" src="~@/assets/paint-bucket.png" alt="" />
          <img class="ssafymind-img" id="jsMode-palette" src="~@/assets/paint-palette.png" alt="" />
          <img
            class="ssafymind-img"
            id="jsErase"
            @click="handleCanvasErase()"
            src="~@/assets/eraser.png"
            alt=""
          />
          <img
            class="ssafymind-img"
            id=""
            @click="handleSave()"
            src="~@/assets/floppy-disk.png"
            alt=""
          />
        </div>
      </div>
    </div>
    <!-- <SsafymindRight /> -->
    <div class="ssafymind-right">
      <div>
        <input
          type="text"
          placeholder="메세지를 입력하세요"
          v-model="message"
          @keyup.enter="sendAnswerMessage"
        />
        <div v-for="(msg, idx) in room.chat" :key="idx">
          <p>{{ msg.name }} : {{ msg.message }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Header from '@/components/common/Header.vue';
import GameOrderModal from '@/components/GameOrderModal';
import GameStatus from '@/components/GameStatus.vue';
// import SsafymindRight from '@/components/ssafymind/SsafymindRight.vue';
import Timer from '@/components/common/Timer.vue';
import '@/css/ssafymind.css';
import { mapGetters } from 'vuex';
import { socketConnect } from '@/util/socket-common.js';
import '@/components/css/ssafymind/ssafymind-right.css';

export default {
  name: 'SsafyMind',
  components: {
    Header,
    GameStatus,
    // SsafymindRight,
    Timer,
    GameOrderModal, // 게임 순서 모달
  },
  data: function() {
    return {
      ordermodal: true,
      startTime: false,
      painting: false,
      clickmouse: false,
      filling: false,
      drawData: [], // 그린 좌표들 저장하는 배열
      colorData: [],
      saveData: [], // 마우스를 떼었을 때 멈추고 다시 저장하기 위한 배열
      stompClient: null, // socket
      room: {}, // 방 정보
      message: '', // 보낼 메세지
      minutes: '',
      seconds: '',
      canvas: null,
      ctx: {},
      offsetX: 0,
      offsetY: 0,
      lineSize: 2.5,
      color: 'rgb(0, 0, 0)',
      beginPath: true,
    };
  },
  created() {
    this.stompClient = socketConnect(this.onConnected, this.onError);
  },
  computed: {
    ...mapGetters(['getUser', 'getRoomId']),
  },
  destroyed() {
    this.onDisconnect();
  },
  methods: {
    startTimer(startTime) {
      this.startTime = startTime;
    },
    getCloseModal(ordermodal) {
      this.ordermodal = ordermodal;
    },
    stopPainting: function() {
      this.painting = false;
    },
    startPainting: function() {
      this.painting = true;
    },
    onMouseMove: function(event) {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      this.offsetX = event.offsetX;
      this.offsetY = event.offsetY;
      this.lineSize = ctx.lineWidth;
      // console.log(x, y)
      // console.log(event)

      if (!this.painting) {
        this.beginPath = true;
        // ctx.beginPath(); // 새로운 경로를 만든다. 경로가 생성되었다면, 이후 그리기 명령들은 경로를 구성하고 만드는 데에 사용된다.
        // ctx.moveTo(this.offsetX, this.offsetY); // 해당 좌표로 펜을 이동하는 메소드
        // console.log(ctx.beginPath)
      } else {
        this.beginPath = false;
        // this.strokePath(x, y);
        // ctx.lineTo(this.offsetX, this.offsetY); // 현재 위치에서 해당 좌표까지 선 그리기
        // ctx.stroke(); // 윤곽선을 이용해 선 그리기
        // console.log(x, y)
        // this.drawData.push({ x, y, size });

        this.sendDrawMessage();
        this.drawing();
        // console.log(this.drawData)
      }

      // // 실시간으로 그려지나 확인
      // // const canvas = document.getElementById("jsCanvas");
      // // const ctx = canvas.getContext("2d");
      // ctx.clearRect(0, 0, 1100, 760)
      // // ctx.lineWidth = 30;
      // // ctx.strokeStyle = "red";
      // ctx.beginPath();
      // if(this.drawData.length > 0){
      // 	this.drawData.forEach((lookline) => {
      // 		ctx.lineWidth = lookline.size;
      // 		// ctx.fillRect(0,0,lookline.x, lookline.y)
      // 		if(!this.painting){
      // 			ctx.moveTo(lookline.x+50, lookline.y+50);
      // 		}
      // 		else{ctx.lineTo(lookline.x+50, lookline.y+50);}
      // 		// console.log(lookline.x, lookline.y, lookline.size);
      // 		console.log(ctx.lineWidth)
      // 	})
      // }
      // // console.log(this.drawData.length)
      // ctx.stroke();
      // ctx.closePath();
    },
    strokePath: function(x, y) {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      var color = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
      var currentColor = ctx.strokeStyle;
      console.log('색깔이 보이나?', arguments.length);
      if (color !== null) {
        ctx.strokeStyle = color;
      }
			ctx.lineTo(x, y);
			ctx.stroke();
			ctx.strokeStyle = currentColor;
		},
		drawing: function(){
			// 실시간으로 그려지나 확인
			// ctx.clearRect(0, 0, 1100, 760)
			// console.log("saveData 확인", this.saveData.length)
			if(this.saveData.length > 0){
				this.saveData.forEach((lookline) => {
					this.ctx.beginPath()
					this.ctx.moveTo(lookline[0].x+50, lookline[0].y+50);
					lookline.forEach((index) => {
						// console.log('확인',index.x)
						this.ctx.lineWidth = index.size;
						// console.log('lookline', index.x+50)
						this.ctx.lineTo(index.x+50, index.y+50);
						this.ctx.stroke();
					})
					this.ctx.closePath();
					this.ctx.save()
				})
			}
			// ctx.save()
			
			// ctx.restore();
			// if(this.saveData.length > 0){
			// 	this.saveData.forEach((lookline) => {
			// 		ctx.lineWidth = lookline.size;
			// 		ctx.moveTo(lookline.x+50, lookline.y+50);
			// 		ctx.lineTo(lookline.x+50, lookline.y+50);
			// 	})
			// }
			// ctx.save()
			// ctx.beginPath()

      // this.drawData.forEach((lookline) => {
      // 	ctx.lineWidth = lookline.size;
      // 	ctx.moveTo(lookline.x, lookline.y);
      // 	ctx.lineTo(lookline.x, lookline.y);
      // })
      // ctx.stroke();
      // ctx.closePath();
      // ctx.restore();
    },
    mouseEnter: function() {
      if (this.clickmouse) {
        this.painting = true;
      } else {
        this.painting = false;
      }
    },
    isMouseDown: function(event) {
      // 그리기 시작
      this.clickmouse = true;

      this.offsetX = event.offsetX;
      this.offsetY = event.offsetY;
      this.ctx.beginPath();
      this.ctx.moveTo(this.offsetX, this.offsetY);
      this.ctx.lineTo(this.offsetX, this.offsetY);
      this.ctx.stroke();
      this.sendDrawMessage();
    },
    isMouseUp: function() {
      // 그리기 종료
      this.clickmouse = false;

      // console.log('여기서부터 4개 확인')
      // console.log(this.drawData)
      // console.log(this.drawData.length)
      // console.log(this.saveData)
      // console.log(this.saveData.length)
      if (this.drawData && this.drawData.length > 0) {
        this.saveData[this.saveData.length] = this.drawData; // 그리는 거 저장하겠다
        this.drawData = []; // 초기화
      }
    },
    // 데이터 받아와서 그림 그리기
    getPoint: function(x, y, size, color, fill, painting) {
      // 펜 굵기 설정
      this.ctx.lineWidth = size;
      // color 적용
      if (this.color != color) {
        this.handleColorClick(color);
      }
      // 그림그리는 함수
      if (!painting) {
        this.ctx.beginPath();
        this.ctx.moveTo(x, y);
        // console.log(ctx.beginPath)
      } else {
        this.ctx.lineTo(x, y);
        this.ctx.stroke();
        // console.log(x, y)
        this.drawing();
        // console.log(this.drawData)
      }
      // 채워지면 전체 캔버스 색깔 채우기
      if (fill == true) {
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
      }
    },
    handleColorClick: function(color) {
      console.log('색깔', color);
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      ctx.strokeStyle = color;
      ctx.fillStyle = color;
      this.color = color;
      // this.colorData.push({color});
      // console.log(this.colorData)
    },
    handleRangeChange: function(event) {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      const size = event.target.value;
      ctx.lineWidth = size;
    },
    handlePaintModeClick: function() {
      const canvas = document.getElementById('jsCanvas');
      if (this.filling === false) {
        // canvas.classList.remove(`painteraser`);
        canvas.classList.remove(`paintbrush`);
        canvas.classList.add(`paintbucket`);
        this.filling = true;
      }
    },
    handlePaletteModeClick: function() {
      const canvas = document.getElementById('jsCanvas');
      if (this.filling === true) {
        // canvas.classList.remove(`painteraser`);
        canvas.classList.remove(`paintbucket`);
        canvas.classList.add(`paintbrush`);
        this.filling = false;
      }
    },
    handleCanvasClick: function() {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      if (this.filling) {
        // this.fill();
        ctx.fillRect(0, 0, canvas.width, canvas.height);
      }
    },
    // fill: function(){
    // 	const canvas = document.getElementById("jsCanvas");
    // 	const ctx = canvas.getContext("2d");

    // 	var color = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : null;
    // 	var currentColor = ctx.fillStyle;

    // 	if (color !== null) {
    // 		ctx.fillStyle = color;
    // 	}

    // 	ctx.fillRect(0, 0, canvas.width, canvas.height);
    // 	ctx.fillStyle = currentColor;

    // 	console.log('fill이 되나', arguments)
    // },
    handleCanvasErase: function() {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      // canvas.classList.remove(`paintbucket`);
      // canvas.classList.remove(`paintbrush`);
      // canvas.classList.add(`painteraser`);
      ctx.strokeStyle = 'white';
      this.color = 'rgb(255, 255, 255)';
      // ctx.lineWidth = 30;
      this.handlePaletteModeClick();
    },
    handleCM: function(event) {
      event.preventDefault();
    },
    handleSave: function() {
      const canvas = document.getElementById("jsCanvas");
      const image = canvas.toDataURL();
      const link = document.createElement("a");
      // const imageName = prompt("그림 이름 지어줭");
      link.href = image;
      // link.download = imageName;
      link.download = "내가그린기린그림";
      link.click();
      // console.log(image);
    },
    setCanvas: function() {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d'); // 픽셀로 접근할 수 있는 방법
      const colors = document.getElementsByClassName('jsColor'); // 색깔 변경 시 사용
      const range = document.getElementById('jsRange'); // 선 굵기 변경 시 사용
      const paintmode = document.getElementById('jsMode-paint'); // 색 채우기 시 사용
      const palettemode = document.getElementById('jsMode-palette'); // 펜 바꾸기 시 사용

      this.canvas = canvas;
      this.ctx = ctx;
      const INITIAL_COLOR = 'black';
			console.log(INITIAL_COLOR)
      canvas.width = 1100;
      canvas.height = 760;
      // canvas.width = window.innerWidth;
      // canvas.height = window.innerHeight;
      ctx.fillStyle = 'white';
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      ctx.strokeStyle = INITIAL_COLOR;
      ctx.fillStyle = INITIAL_COLOR;
      ctx.lineWidth = 2.5;
      ctx.lineCap = 'round';

      // 색깔을 가지고 있는 배열값을 받아오자
      Array.from(colors).forEach((color) =>
        color.addEventListener('click', () => {
          const backgroundColor = color.style.backgroundColor;
          this.handleColorClick(backgroundColor);
        })
      );

      if (range) {
        range.addEventListener('input', this.handleRangeChange);
      }

      if (paintmode) {
        paintmode.addEventListener('click', this.handlePaintModeClick);
      }
      if (palettemode) {
        palettemode.addEventListener('click', this.handlePaletteModeClick);
      }

      canvas.addEventListener('click', this.handleCanvasClick);
    },
    /**
     * 소켓 통신
     */
    // 게임 방 입장 : 정보 구독 및 유저 정보 전송
    onConnected() {
      // 방 정보 교환 채널
      this.stompClient.subscribe('/ssafymind/' + this.getRoomId, this.onMesseageReceived);
      // 그림 정보 교환 채널
      this.stompClient.subscribe('/ssafymind/draw/' + this.getRoomId, this.onDrawMessageReceived);
      // 메세지 교환 채널
      this.stompClient.subscribe(
        '/ssafymind/message/' + this.getRoomId,
        this.onAnswerMessageReceived
      );
      // 정답 여부 교환 채널
      this.stompClient.subscribe(
        '/ssafymind/correct/' + this.getRoomId,
        this.onCorrectMessageReceived
      );
      // 시간 정보 교환 채널
      this.stompClient.subscribe('/ssafymind/time/' + this.getRoomId, this.onTimeMessageReceived);
      this.stompClient.send(
        '/pub/ssafymind/enter',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
          teamNo: this.getUser.teamNo,
        })
      );
      this.test();
    },
    onMesseageReceived(payload) {
      if (payload.body == 'exit') {
        // 모든 참가자의 연결을 끊고
        this.onDisconnect();
        alert('방장이 퇴장하여 게임이 종료됩니다!');
        // 모든 참가자 내보내기
        this.$router.push('/room/' + this.getRoomId);
        return;
      }
      const data = JSON.parse(payload.body);
      this.room = data;
			console.log('포인트를 알고싶다고')
      console.log(data);
      // 여기에 받은 데이터를 기반으로 그리고 있는 그림 초기화하는 로직 구현
      // for(this.room.points)
			if(data.points.length > 0){
				data.points.forEach((lookline) => {
					this.ctx.beginPath()
					this.ctx.moveTo(lookline[0].x, lookline[0].y);
					lookline.forEach((index) => {
						// console.log('확인',index.x)
						this.ctx.lineWidth = index.size;
						// console.log('lookline', index.x+50)
						this.ctx.lineTo(index.x, index.y);
						this.ctx.stroke();
					})
					this.ctx.closePath();
					this.ctx.save()
				})
			}
    },
    // 게임 방 퇴장 소켓 연결 해제 및 게임 방 유저 정보 삭제
    onDisconnect() {
      this.stompClient.send(
        '/pub/ssafymind/exit',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
        })
      );
      this.stompClient.disconnect();
      this.$router.push('/room/' + this.getRoomId);
    },
    /**
     * 좌표 보내기, 받기
     */
    sendDrawMessage() {
      // 보내는 객체 여기다가 넣기
      this.stompClient.send(
        `/pub/ssafymind/draw/${this.getRoomId}`,
        {},
        JSON.stringify({
          x: this.offsetX,
          y: this.offsetY,
          size: this.lineSize,
          color: this.color,
          fillFlag: this.filling,
          beginPath: this.beginPath,
        })
      );
    },
    onDrawMessageReceived(payload) {
      const data = JSON.parse(payload.body);
      // console.log(data);
      // 여기에 실시간으로 그리는 로직 작성

			this.ctx.lineWidth = data.size;
			this.ctx.strokeStyle = data.color;
			this.ctx.fillStyle = data.color;
			if (data.beginPath) {
        this.ctx.beginPath();
        this.ctx.moveTo(data.x, data.y);
        // console.log(ctx.beginPath)
      } else {
        this.ctx.lineTo(data.x, data.y);
        this.ctx.stroke();
      }

			if(data.fillFlag){
				this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
			}
    },
    /**
     * 메세지 보내기, 받기
     */
    sendAnswerMessage() {
      this.stompClient.send(
        `/pub/ssafymind/message/${this.getRoomId}`,
        {},
        JSON.stringify({
          name: this.getUser.name,
          teamNo: this.getUser.teamNo,
          message: this.message,
        })
      );
      this.message = '';
    },
    onAnswerMessageReceived(payload) {
      const data = JSON.parse(payload.body);
      console.log(data);
      this.room.chat.push(data);
    },
    /**
     * 정답 여부 수신
     */
    onCorrectMessageReceived(payload) {
      console.log(payload.body);
      const flag = JSON.parse(payload.body);
      if (flag) alert('정답입니다!');
    },
    /**
     * 시간 트리거 발동, 수신
     */
    sendTimeTrigger() {
      // this.stompClient.send('/pub/ssafymind/time', {}, 'stop');
      this.stompClient.send(`/pub/ssafymind/time/${90}`, {}, this.getRoomId);
    },
    onTimeMessageReceived(payload) {
      const time = JSON.parse(payload.body);
      this.minutes = parseInt(time / 60);
      this.seconds = time % 60;
    },
    test() {
      this.stompClient.send(
        '/pub/ssafymind/enter',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: 1,
          participantName: '김태현',
          teamNo: '1',
        })
      );
      this.stompClient.send(
        '/pub/ssafymind/enter',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: 2,
          participantName: '김태현2',
          teamNo: '2',
        })
      );
      this.stompClient.send(
        '/pub/ssafymind/enter',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: 3,
          participantName: '김태현3',
          teamNo: '3',
        })
      );
      this.stompClient.send(
        '/pub/ssafymind/enter',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: 4,
          participantName: '김태현4',
          teamNo: '3',
        })
      );
    },
  },
  mounted: function() {
    this.setCanvas();
  },
};
</script>
<style></style>
