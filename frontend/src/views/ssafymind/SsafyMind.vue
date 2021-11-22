<template>
  <div class="ssafymind" v-on:mousedown="isMouseDown" v-on:mouseup="isMouseUp">
    <SsafymindRankModal
      v-if="rankmodal == true"
      @getCloseRankModal="getCloseRankModal"
      v-bind:host="room.host"
      v-bind:scores="room.scores"
      v-bind:curTeam="room.curTeam"
      v-on:sendGoWaitingRoomTrigger="sendGoWaitingRoomTrigger"
    />
    <GameOrderModal
      v-if="ordermodal == true"
      @getCloseModal="getCloseModal"
      v-bind:teamOrder="room.teamOrder"
      v-bind:teamCnt="room.curTeamCnt"
      v-bind:host="room.host"
      v-on:sendGameStartTrigger="sendGameStartTrigger"
      @getProgressBar="getProgressBar"
    />
    <GameAnswerModal
      v-if="answermodal == true"
      @getCloseAnsModal="getCloseAnsModal"
      v-bind:teamOrder="room.teamOrder"
      v-bind:teamCnt="room.curTeamCnt"
      v-bind:host="room.host"
      v-bind:quizzes="room.quizzes"
      v-bind:chat="room.chat"
      v-on:sendGameStartTrigger="sendGameStartTrigger"
    />
    <Header
      v-bind:gameTitle="'싸피마인드'"
      v-bind:host="room.host"
      v-on:onDisconnect="onDisconnect"
    />
    <GameStatus
      game="ssafymind"
      v-bind:teamOrder="room.teamOrder"
      v-bind:teams="room.teams"
      v-bind:curPlayer="room.curPlayer"
      v-bind:scores="room.scores"
      v-bind:curTeam="room.curTeam"
      v-bind:curTeamCnt="room.curTeamCnt"
      @getProgressBar="getProgressBar"
      v-if="room != null"
    />
    <div class="ssafymind-center">
      <div v-if="room.quizzes != null">
        <div
          class="question-word"
          v-if="user.name == room.host || room.teamOrder[0] == user.teamNo"
        >
          {{ room.quizzes[room.quizzes.length - 1].problem }}
        </div>
      </div>
      <Timer v-bind:minutes="minutes" v-bind:seconds="seconds" />
      <div v-if="room.teamOrder != null">
        <div
          class="turn-notice"
          v-if="
            room.teamOrder[0] != null &&
              room.teams[room.teamOrder[0]].members.length != 0 &&
              user.name == room.teams[room.teamOrder[0]].members[room.curPlayer].participantName
          "
        >
          당신 차례 입니다! 빨리 그리세요!
        </div>
        <div class="turn-notice" v-else></div>
      </div>
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
      <div v-if="room.teamOrder != null">
        <div
          class="controls-stop"
          v-if="
            room.teamOrder[0] != null &&
              room.teams[room.teamOrder[0]].members.length != 0 &&
              user.name != room.teams[room.teamOrder[0]].members[room.curPlayer].participantName
          "
        ></div>
      </div>
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
    <div class="ssafymind-right">
      <div id="chat-box">
        <div v-if="room.teamOrder != null">
          <input
            type="text"
            placeholder="정답을 입력하세요"
            v-model="message"
            @keyup.enter="sendAnswerMessage"
            id="send-ans"
            v-if="
              room.teamOrder[0] != null &&
                getUser.teamNo != 0 &&
                room.teamOrder[0] != getUser.teamNo
            "
          />
        </div>
        <div v-for="(msg, idx) in room.chat" :key="idx">
          <p id="ans-block" v-if="msg.correct">
            <strong>{{ msg.name }} <i class="fas fa-check-circle"></i> : {{ msg.message }}</strong>
          </p>
          <p class="chat-block" id="team-1" v-else-if="msg.teamNo == 1">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-2" v-else-if="msg.teamNo == 2">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-3" v-else-if="msg.teamNo == 3">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-4" v-else-if="msg.teamNo == 4">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-5" v-else-if="msg.teamNo == 5">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-6" v-else-if="msg.teamNo == 6">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-7" v-else-if="msg.teamNo == 7">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-8" v-else-if="msg.teamNo == 8">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-9" v-else-if="msg.teamNo == 9">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" id="team-10" v-else-if="msg.teamNo == 10">
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
          <p class="chat-block" v-else>
            <strong>{{ msg.name }}</strong> : {{ msg.message }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Header from '@/components/common/Header.vue';
import GameOrderModal from '@/components/GameOrderModal';
import GameAnswerModal from '@/components/GameAnswerModal';
import GameStatus from '@/components/GameStatus.vue';
import Timer from '@/components/common/Timer.vue';
import '@/css/ssafymind.css';
import { mapGetters, mapState } from 'vuex';
import { socketConnect } from '@/util/socket-common.js';
import '@/components/css/ssafymind/ssafymind-right.css';
import '@/components/css/ssafymind/progressbar.css';
import SsafymindRankModal from '@/components/ssafymind/SsafymindRankModal';
import swal from 'sweetalert';

export default {
  name: 'SsafyMind',
  components: {
    Header,
    GameStatus,
    Timer,
    GameOrderModal, // 게임 순서 모달
    GameAnswerModal, // 게임 정답 모달
    SsafymindRankModal,
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
      minutes: '', // 분
      seconds: '', // 초
      timeReceiveFlag: false, // 시간 수신 메세지 잠시 무시
      canvas: null,
      ctx: null,
      offsetX: 0,
      offsetY: 0,
      lineSize: 2.5,
      color: 'rgb(0, 0, 0)',
      beginPath: true,
      temp: 0,
      startProgress: true,
      answermodal: false,
      canDraw: true, // 내 차례일때만 그림 그리기
      rankmodal: false, // 대기실로 돌아가기 위한 랭킹 모달
      // progressbarMsg: 'start',
      progressbar: null,
    };
  },
  created() {
    history.pushState(null, null, location.href);
    window.onpopstate = () => {
      history.go(1);
      swal({
        // className:'alert',
        title: '게임 내에서는 뒤로가기가 불가능합니다.',
        icon: '/img/ssazip-logo.png',
        buttons: {
          text: '확인',
        },
      });
    };
    this.stompClient = socketConnect(this.onConnected, this.onError);
    this.stompClient.debug = function() {};
  },
  computed: {
    ...mapGetters(['getUser', 'getRoomId']),
    ...mapState(['user']),
  },
  updated() {
    this.scrollDown(); //채팅방 자동 스크롤
  },
  methods: {
    getCloseModal(ordermodal) {
      this.ordermodal = ordermodal;
    },
    getCloseAnsModal(answermodal) {
      this.answermodal = answermodal;
    },
    getCloseRankModal(rankmodal) {
      this.rankmodal = rankmodal;
    },
    getProgressBar(startProgress) {
      this.startProgress = startProgress;
    },
    stopPainting: function() {
      this.painting = false;
    },
    startPainting: function() {
      this.painting = true;
    },
    onMouseMove: function(event) {
      //그리기, 보이진 않음
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      this.offsetX = event.offsetX;
      this.offsetY = event.offsetY;
      this.lineSize = ctx.lineWidth;

      if (!this.painting) {
        this.beginPath = true;
      } else {
        this.beginPath = false;
        if (
          this.room.teamOrder[0] != null &&
          this.getUser.name ==
            this.room.teams[this.room.teamOrder[0]].members[this.room.curPlayer].participantName
        ) {
          this.sendDrawMessage();
        }
        this.drawing();
      }
    },
    strokePath: function(x, y) {
      //안씀 통신에 xy 좌표 보내주려 했던 것
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      var color = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
      var currentColor = ctx.strokeStyle;
      if (color !== null) {
        ctx.strokeStyle = color;
      }
      ctx.lineTo(x, y);
      ctx.stroke();
      ctx.strokeStyle = currentColor;
    },
    drawing: function() {
      //안씀 통신에 실시간으로 확인하려고 했던 것
      if (this.saveData.length > 0) {
        this.saveData.forEach((lookline) => {
          this.ctx.beginPath();
          this.ctx.moveTo(lookline[0].x + 50, lookline[0].y + 50);
          lookline.forEach((index) => {
            this.ctx.lineWidth = index.size;
            this.ctx.lineTo(index.x + 50, index.y + 50);
            this.ctx.stroke();
          });
          this.ctx.closePath();
          this.ctx.save();
        });
      }
    },
    mouseEnter: function() {
      //클릭 하는 순간을 감지
      if (this.clickmouse) {
        this.painting = true;
      } else {
        this.painting = false;
      }
    },
    isMouseDown: function(event) {
      // fill 채워주려고 점 찍기
      if (
        this.room.teamOrder[0] != null &&
        this.getUser.name !=
          this.room.teams[this.room.teamOrder[0]].members[this.room.curPlayer].participantName
      ) {
        return;
      }
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
      //그리기 종료
      // 그리기 종료
      this.clickmouse = false;

      if (this.drawData && this.drawData.length > 0) {
        this.saveData[this.saveData.length] = this.drawData; // 그리는 거 저장하겠다
        this.drawData = []; // 초기화
      }
    },
    // 데이터 받아와서 그림 그리기 => 통신해서 그림을 그리기 위한
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
      } else {
        this.ctx.lineTo(x, y);
        this.ctx.stroke();
        this.drawing();
      }
      // 채워지면 전체 캔버스 색깔 채우기
      if (fill == true) {
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
      }
    },
    handleColorClick: function(color) {
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      ctx.strokeStyle = color;
      ctx.fillStyle = color;
      this.color = color;
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
        canvas.classList.remove(`paintbrush`);
        canvas.classList.add(`paintbucket`);
        this.filling = true;
      }
    },
    handlePaletteModeClick: function() {
      const canvas = document.getElementById('jsCanvas');
      if (this.filling === true) {
        canvas.classList.remove(`paintbucket`);
        canvas.classList.add(`paintbrush`);
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
      this.color = 'rgb(255, 255, 255)';
      this.handlePaletteModeClick();
    },
    handleCM: function(event) {
      //오른쪽 마우스 클릭 방지
      event.preventDefault();
    },
    handleSave: function() {
      const canvas = document.getElementById('jsCanvas');
      const image = canvas.toDataURL();
      const link = document.createElement('a');
      link.href = image;
      link.download = '내가그린기린그림';
      link.click();
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
      canvas.width = 1100;
      canvas.height = 760;
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
    moveProgressBar: function(progressbarMsg) {
      const progress = document.getElementById('progress-bar');
      var width = 100;
      if (progressbarMsg == 'start') {
        clearInterval(this.progressbar);
        this.progressbar = setInterval(() => frame(), 100);
      } else if (progressbarMsg == 'stop') {
        clearInterval(this.progressbar);
        progress.style.width = '100%';
      }
      const frame = () => {
        if (width <= 0) {
          clearInterval(this.progressbar);
        } else {
          width--;
          progress.style.width = width + '%';
        }
      };
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
      // 플레이어 교환 정보 응답 채널
      this.stompClient.subscribe(
        '/ssafymind/change/player/' + this.getRoomId,
        this.onTeamChangeMessageReceived
      );
      // 모달 정보 교환
      this.stompClient.subscribe(
        '/ssafymind/close/modal/' + this.getRoomId,
        this.onModalMessageReceived
      );
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
    },
    onMesseageReceived(payload) {
      if (payload.body == 'exit') {
        this.$router.push('/room/' + this.getRoomId).catch(() => {});
        this.stompClient.disconnect();
        swal({
          // className:'alert',
          title: '방장이 퇴장하여 게임이 종료됩니다!',
          icon: '/img/ssazip-logo.png',
          buttons: {
            text: '확인',
          },
        });
        return;
      }
      const data = JSON.parse(payload.body);
      this.room = data;
      // 모든 팀의 차례가 끝난 경우
      if (this.room.teamOrder[0] == null) {
        // 랭킹 모달 띄우기
        this.rankmodal = true;
      }
    },
    // 게임 방 퇴장 소켓 연결 해제 및 게임 방 유저 정보 삭제
    onDisconnect() {
      let isHost;
      if (this.room.host == this.getUser.id) isHost = true;
      else isHost = false;
      this.stompClient.send(
        '/pub/ssafymind/exit',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
          host: isHost,
        })
      );
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
      this.ctx.lineWidth = data.size;
      this.ctx.strokeStyle = data.color;
      this.ctx.fillStyle = data.color;
      if (data.beginPath) {
        this.ctx.beginPath();
        this.ctx.moveTo(data.x, data.y);
      } else {
        this.ctx.lineTo(data.x, data.y);
        this.ctx.stroke();
      }

      if (data.fillFlag) {
        if (data.beginPath == false) {
          this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        }
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
      // 정답인 경우
      if (data.correct) {
        // 진행바 정지
        this.moveProgressBar('stop');
        // 시간 정지 메세지 띄우고, 모달 다시 띄우기
        if (this.room.host == this.getUser.id) {
          this.sendNextProblemTrigger();
          this.sendTimeTrigger('stop');
          // 진행바 정지
          this.moveProgressBar('stop');
        }
        if (this.room.teamOrder.length == 1) {
          this.room.chat.push(data);
          return;
        }
        this.answermodal = true;
        this.startProgress = true;
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
      }
      this.room.chat.push(data);
    },
    /**
     * 시간 트리거 발동, 수신
     */
    sendTimeTrigger(flag) {
      this.stompClient.send(`/pub/ssafymind/time/${flag}`, {}, this.getRoomId);
    },
    onTimeMessageReceived(payload) {
      // 현재 시간 수신이 거부중인 경우 수신 받지 않기
      const time = JSON.parse(payload.body);
      // 10초 단위로 서버에 플레이어 변경 메세지 전송
      if (time != 0 && time != 90 && time % 10 == 0 && this.room.host == this.getUser.id)
        this.sendTeamChangeTrigger();
      if (time <= 0) {
        // 시간 내에 맞추지 못했다면 다음 문제로
        if (this.room.host == this.getUser.id) {
          // 다음 문제 이동
          this.sendNextProblemTrigger();
          // 시간 정지
          this.sendTimeTrigger('stop');
          // 진행바 정지
          this.moveProgressBar('stop');
        }
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        // 문제 모달 띄우기
        this.answermodal = true;
      }
      this.minutes = parseInt(time / 60);
      this.seconds = time % 60;
    },
    // 팀 변경 트리거 송수신
    sendTeamChangeTrigger() {
      this.stompClient.send(`/pub/ssafymind/change/player`, {}, this.getRoomId);
    },
    onTeamChangeMessageReceived(payload) {
      const curPlayer = JSON.parse(payload.body);
      this.room.curPlayer = curPlayer;
      this.moveProgressBar('start');
    },
    // 문제 변경 트리거
    sendNextProblemTrigger() {
      this.stompClient.send(`/pub/ssafymind/next/problem`, {}, this.getRoomId);
    },
    // 게임 시작 트리거
    sendGameStartTrigger() {
      // 모달 닫기 메세지 전송
      this.sendCloseModalMessage();
      // 타이머 시작
      this.sendTimeTrigger('start');
      // 캔버스 초기화
      const canvas = document.getElementById('jsCanvas');
      const ctx = canvas.getContext('2d');
      ctx.clearRect(0, 0, canvas.width, canvas.height);
    },
    // 전체 모달 닫기
    sendCloseModalMessage() {
      this.stompClient.send(`/pub/ssafymind/close/modal`, {}, this.getRoomId);
    },
    onModalMessageReceived(payload) {
      const flag = JSON.parse(payload.body);
      this.ordermodal = flag;
      this.answermodal = flag;
      this.moveProgressBar('start');
    },
    scrollDown() {
      const scrollbox = document.getElementById('chat-box');
      scrollbox.scrollTop = scrollbox.scrollHeight;
    },
    // 대기실로 돌아가는 트리거
    sendGoWaitingRoomTrigger() {
      this.stompClient.send('/pub/ssafymind/end', {}, this.getRoomId);
      this.onDisconnect();
    },
  },
  mounted: function() {
    this.setCanvas();
  },
};
</script>
<style></style>
