<template>
  <div class="ssafymind" v-on:mousedown="isMouseDown" v-on:mouseup="isMouseUp">
    <!-- <div class="room-title">
      <span id="game-title">싸피마인드</span>
    </div> -->
    <Header v-bind:gameTitle="'싸피마인드'" />
    <GameStatus />
    <div class="ssafymind-center">
      <div class="question-word">문제: SSA.zip</div>
      <Timer />
      <div class="turn-notice">당신 차례 입니다! 빨리 그리세요!</div>
      <canvas
        id="jsCanvas"
        class="canvas"
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
    <SsafymindRight />
  </div>
</template>
<script>
import Header from '@/components/common/Header.vue';
import GameStatus from '@/components/GameStatus.vue';
import SsafymindRight from '@/components/ssafymind/SsafymindRight.vue';
import Timer from '@/components/common/Timer.vue';
import '@/css/ssafymind.css';
import { mapGetters } from 'vuex';
import { socketConnect } from '@/util/socket-common.js';

export default {
  name: 'SsafyMind',
  components: {
    Header,
    GameStatus,
    SsafymindRight,
    Timer,
  },
  data: function() {
    return {
      painting: false,
      clickmouse: false,
      filling: false,
      drawData: [],
      colorData: [],
      // socket
      stompClient: null,
    };
  },
  created() {
    this.stompClient = socketConnect(this.onConnected, this.onError);
  },
  computed: {
    ...mapGetters(['getUser', 'getRoomId']),
  },
  methods: {
    stopPainting: function() {
      this.painting = false;
    },
    startPainting: function() {
      this.painting = true;
    },
    onMouseMove: function(event) {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      const x = event.offsetX;
      const y = event.offsetY;
      const size = ctx.lineWidth;
      // console.log(x, y)
      // console.log(event)

      if (!this.painting) {
        ctx.beginPath(); // 새로운 경로를 만든다. 경로가 생성되었다면, 이후 그리기 명령들은 경로를 구성하고 만드는 데에 사용된다.
        ctx.moveTo(x, y); // 해당 좌표로 펜을 이동하는 메소드

        // console.log(ctx.beginPath)
      } else {
        ctx.lineTo(x, y); // 현재 위치에서 해당 좌표까지 선 그리기
        ctx.stroke(); // 윤곽선을 이용해 선 그리기
        // console.log(x, y)
        this.drawData.push({ x, y, size });
        console.log(this.drawData);
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
    mouseEnter: function() {
      if (this.clickmouse) {
        this.painting = true;
      } else {
        this.painting = false;
      }
    },
    isMouseDown: function() {
      this.clickmouse = true;
    },
    isMouseUp: function() {
      this.clickmouse = false;
    },
    handleColorClick: function(event) {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      const color = event.target.style.backgroundColor;
      ctx.strokeStyle = color;
      ctx.fillStyle = color;
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
      if (this.filling === false) {
        this.filling = true;
      }
    },
    handlePaletteModeClick: function() {
      if (this.filling === true) {
        this.filling = false;
      }
    },
    handleCanvasClick: function() {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      if (this.filling) {
        ctx.fillRect(0, 0, canvas.width, canvas.height);
      }
    },
    handleCanvasErase: function() {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      ctx.strokeStyle = 'white';
    },
    handleCM: function(event) {
      event.preventDefault();
    },
    handleSave: function() {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      // ctx.clearRect(0, 0, 1100, 760)
      // ctx.lineWidth = 30;
      ctx.strokeStyle = 'red';
      ctx.beginPath();
      if (this.drawData.length > 0) {
        this.drawData.forEach((lookline) => {
          ctx.lineWidth = lookline.size;
          // ctx.fillRect(0,0,lookline.x, lookline.y)
          ctx.moveTo(lookline.x + 50, lookline.y + 50);
          ctx.lineTo(lookline.x + 50, lookline.y + 50);
          // console.log(lookline.x, lookline.y, lookline.size);
          console.log(ctx.lineWidth);
        });
      }
      // console.log(this.drawData.length)
      ctx.stroke();
      ctx.closePath();

      // const canvas = document.getElementById("jsCanvas");
      // const image = canvas.toDataURL();
      // const link = document.createElement("a");
      // // const imageName = prompt("그림 이름 지어줭");
      // link.href = image;
      // // link.download = imageName;
      // link.download = "내가그린기린그림";
      // link.click();
      // // console.log(image);
    },
    setCanvas: function() {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d'); // 픽셀로 접근할 수 있는 방법
      const colors = document.getElementsByClassName('jsColor'); // 색깔 변경 시 사용
      const range = document.getElementById('jsRange'); // 선 굵기 변경 시 사용
      const paintmode = document.getElementById('jsMode-paint'); // 색 채우기 시 사용
      const palettemode = document.getElementById('jsMode-palette'); // 펜 바꾸기 시 사용

      const INITIAL_COLOR = 'black';
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
      Array.from(colors).forEach((color) => color.addEventListener('click', this.handleColorClick));

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
      this.stompClient.subscribe('/ssafymind/' + this.getRoomId, this.onMesseageReceived);
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
      // this.test();
    },
    onMesseageReceived(payload) {
      const data = JSON.parse(payload.body);
      console.log(data);
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
