<template>
  <div class="speak-game">
    <GameOrderModal
      v-if="ordermodal == true"
      v-bind:teamOrder="room.teamOrder"
      v-bind:teamCnt="room.curTeamCnt"
      v-bind:host="room.host"
      v-on:sendGameStartTrigger="sendGameStartTrigger"
      :gameType="'speaking'"
    />
    <Header v-bind:gameTitle="'또박또박 말해요'" :host="getUser.id" />
    <div class="game-screen">
      <GameStatus game="speak" />
      <div class="game-board">
        <p class="sentence">"{{ quiz }}"</p>
        <div v-for="(member, i) in teamMember" :key="i" class="sentence-board" id="sentence-board">
          <div v-if="i % 2 != 1" class="member-left sentence-board-child">
            <div class="member-name">
              <span>{{ member.participantName }}</span>
              <img :src="speakImg[0]" alt="speaking progress image" />
            </div>
            <div class="member-sentence member-sentence-left">
              <span></span>
            </div>
          </div>
          <div v-else class="member-right sentence-board-child">
            <div class="member-sentence member-sentence-right">
              <span></span>
            </div>
            <div class="member-name">
              <span>{{ member.participantName }}</span>
              <img :src="speakImg[0]" alt="speaking progress image" />
            </div>
          </div>
        </div>
        <img :src="micImg[0]" alt="mic img" class="game-mic-default" id="record" />
      </div>

      <!-- 아래는 나중에 사용할 아이들 -->
      <div style="display:none;">
        <p>녹음하기</p>
        <input type="checkbox" id="chk-hear-mic" /><label for="chk-hear-mic"
          >마이크 소리 듣기</label
        >
        <!-- <button id="record">녹음</button> -->
        <!-- <button id="stop">녹음 정지</button> -->
        <span id="final_span"></span>
        <span id="interim_span"></span>
        <div id="sound-clips"></div>
      </div>
    </div>
  </div>
</template>

<script>
import '@/css/speaking-game.css';
import { mapGetters } from 'vuex';
import { socketConnect } from '@/util/socket-common.js';
import Header from '@/components/common/Header.vue';
import GameOrderModal from '@/components/GameOrderModal';
import GameStatus from '@/components/GameStatus.vue';
import swal from 'sweetalert';

export default {
  name: 'SpeakGame',
  components: {
    Header,
    GameStatus,
    GameOrderModal,
  },
  data: () => {
    return {
      ordermodal: true,
      record: {},
      stop: {},
      soundClips: {},
      chkHearMic: {},
      audioCtx: {},
      analyser: {},
      chunks: [],
      mediaRecorder: {},
      isRecording: false,
      // Socket 정보
      stompClient: null,
      room: {},
      interimTranscript: '',
      finalTranscript: '',
      teamMember: [],
      answerIdx: 0,
      speakImg: [
        '/img/speak/speak-wait.png',
        '/img/speak/speak-loading.png',
        '/img/speak/speak-fail.png',
        '/img/speak/speak-success.png',
      ],
      micImg: ['/img/mic/mic-disabled.png', '/img/mic/mic-off.png', '/img/mic/mic-on.png'],
      talkFinish: false,
      quiz: null,
      lastTeamLen: 0,
      talker: 0,
    };
  },
  created() {
    history.pushState(null, null, location.href);
    window.onpopstate = () => {
      history.go(1);
      swal({
          // className:'alert',
          title: "게임 내에서는 뒤로가기가 불가능합니다.",
          icon: "/img/ssazip-logo.png",
          buttons: {
          text: '확인',
        },
      })
    }
    this.stompClient = socketConnect(this.onConnected, this.onError);
  },
  mounted() {
    // this.stompClient.debug = function() {};
    // this.getAudio();
    this.translate();
  },
  destroyed() {
    // this.onDisconnect();
  },
  watch: {},
  computed: {
    ...mapGetters(['getUser', 'getRoomId']),
  },
  methods: {
    /*  */
    // 음성을 텍스트로 번역
    /*  */
    translate: function() {
      if (typeof webkitSpeechRecognition !== 'function') {
        swal({
          // className:'alert',
          title: "크롬에서만 동작합니다.",
          icon: "/img/ssazip-logo.png",
          buttons: {
          text: '확인',
        },
      })
        return false;
      }
      const speech = new (window.SpeechRecognition || window.webkitSpeechRecognition)();

      let isRecognizing = false;
      let ignoreEndProcess = false;
      let finalTranscript = '';

      // 원하는 언어를 앞으로 뺴라(해당 언어만 지원)
      // 비어있으면 영어, 한국어 둘 다 지원(한국어 우선)
      speech.lang = ['ko-KR', 'en-US'];
      speech.continuous = true;
      speech.interimResults = true;

      speech.onstart = function() {
        isRecognizing = true;
      };

      speech.onend = function() {
        isRecognizing = false;
        console.log(isRecognizing);

        if (ignoreEndProcess) {
          return false;
        }

        if (!finalTranscript) {
          return false;
        }
      };

      speech.onresult = (event) => {
        const talker = this.answerIdx;
        let interimTranscript = '';
        this.interimTranscript = '';
        if (typeof event.results === 'undefined') {
          speech.onend = null;
          speech.stop();
          return;
        }

        for (let i = event.resultIndex; i < event.results.length; ++i) {
          const transcript = event.results[i][0].transcript;

          if (event.results[i].isFinal) {
            finalTranscript += transcript;
            this.finalTranscript += transcript;
            console.log('파이널', finalTranscript);
            console.log('파이널', transcript);
          } else {
            interimTranscript += transcript;
            this.interimTranscript += transcript;
          }
        }
        if (this.isRecording) {
          this.stompClient.send(
            `/pub/speaking/talk/${this.getRoomId}`,
            {},
            JSON.stringify({
              sentence: finalTranscript + interimTranscript,
              talker: talker,
            })
          );
        }
      };

      speech.onerror = function(event) {
        if (event.error.match(/no-speech|audio-capture|not-allowed/)) {
          ignoreEndProcess = true;
        }
      };

      const record = document.querySelector('#record');
      record.addEventListener('click', async () => {
        if (
          this.getUser.name !==
            this.room.teams[this.room.teamOrder[0]].members[this.answerIdx].participantName &&
          !this.isRecording
        ) {
          console.log(
            this.room.teams[this.room.teamOrder[0]].members[this.answerIdx].participantName
          );
          this.isRecording = true;
          return;
        } else if (
          this.getUser.name !==
            this.room.teams[this.room.teamOrder[0]].members[this.answerIdx].participantName &&
          this.isRecording
        ) {
          this.isRecording = false;
        } else {
          const speakImg = document.querySelectorAll('.member-name > img');
          if (this.isRecording) {
            let message = finalTranscript;
            this.setMic(2);

            // 마이크 버튼을 눌서 finalTranscript가 없을 때
            if (finalTranscript === this.finalTranscript) {
              message = this.finalTranscript + this.interimTranscript;
            }
            console.log('-----------------');
            console.log(message);
            console.log('-----------------');

            this.stompClient.send(
              `/pub/speaking/answer/${this.getRoomId}`,
              {},
              JSON.stringify({
                name: '안기훈',
                teamNo: 1,
                message: message,
                correct: false,
              })
            );
            finalTranscript = '';
            this.finalTranscript = '';
            this.isRecording = false;
            this.talkFinish = true;
            speech.stop();
          } else {
            this.setMic(1);
            speakImg[this.answerIdx].src = this.speakImg[1];
            finalTranscript = '';
            this.finalTranscript = '';
            const sentenceBox = document.querySelectorAll(
              '.sentence-board > .sentence-board-child > .member-sentence > span'
            )[this.answerIdx];
            sentenceBox.innerText = '';
            this.isRecording = true;
            this.talkFinish = false;
            this.stompClient.send(
              `/pub/speaking/talk/${this.getRoomId}`,
              {},
              JSON.stringify({
                sentence: '',
                talker: this.answerIdx,
              })
            );
            speech.start();
          }
        }
      });

      // const stop = document.querySelector('#stop');
      // console.log(stop.src);
      // stop.addEventListener("click", () => {
      // })

      speech.addEventListener('result', (event) => {
        const transcript = event['results'];
        // this.mediaRecorder.stop();
        return transcript;
      });
    },
    // 멤버 설정
    setTeamMembers: async function() {
      this.teamMember = this.room.teams[this.room.teamOrder[0]].members;
    },
    nextTurn: function() {
      this.stompClient.send('/pub/speaking/next/team', {}, this.getRoomId);
      this.userAnswerIdx++;
      this.answerIdx++;
    },
    setMic: function(micNum) {
      const record = document.querySelector('#record');
      record.src = this.micImg[micNum];
      record.classList.add('game-mic');
      // before
      // game-mic 클래스 없애주기
      // 사진 mic[0]으로

      // after
      // game-mic 클래스 넣어주기
      // 사진 mic[1]로
    },
    removeMic: function() {
      const record = document.querySelector('#record');
      record.src = this.micImg[0];
      record.classList.remove('game-mic');
    },
    setAudio: function() {
      console.log('');
    },
    /**
     * 소켓 통신
     */
    async onConnected() {
      // 방 정보 교환 채널
      this.stompClient.subscribe('/speaking/' + this.getRoomId, this.onMessageReceived);
      // 정답 데이터 채널
      this.stompClient.subscribe(
        '/speaking/answer/' + this.getRoomId,
        this.onAnswerMessageReceived
      );
      // 현재 진행 중인 사람의 문장 전송
      this.stompClient.subscribe('/speaking/talk/' + this.getRoomId, this.onTalkingMessageReceived);
      // 플레이어 변경
      this.stompClient.subscribe(`/speaking/change/player/` + this.getRoomId, this.onChangePlayer);
      // 다음 팀으로
      this.stompClient.subscribe(`/speaking/next/team/` + this.getRoomId, this.onChangeTeam);
      // 모달 정보 교환
      this.stompClient.subscribe(
        '/ssafymind/close/modal/' + this.getRoomId,
        this.onModalMessageReceived
      );

      // 입장 시 데이터 수신
      this.stompClient.send(
        '/pub/speaking/enter',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
          teamNo: this.getUser.teamNo,
        })
      );
    },
    async onMessageReceived(payload) {
      // if (payload.body == 'exit') {
      //   // 모든 참가자의 연결을 끊고
      //   this.onDisconnect();
      //   alert('방장이 퇴장하여 게임이 종료됩니다!');
      //   // 모든 참가자 내보내기
      //   this.$router.push('/room/' + this.getRoomId);
      //   return;
      // }
      const data = JSON.parse(payload.body);
      this.room = data;
      this.quiz = data.quizzes[data.quizzes.length - 1].problem;
      await this.setTeamMembers();
      if (this.getUser.name === data.teams[data.teamOrder[0]].members[0].participantName) {
        // 마이크 권한 주기
        this.setAudio(); // 이전 사람과 이번 턴의 index
        // 마이크 아이콘 변경
        this.setMic(2);
        console.log('hi!!');
      }
    },
    onDisconnect() {
      // this.stompClient.send(
      //   '/pub/speaking/exit',
      //   {},
      //   JSON.stringify({
      //     roomId: this.getRoomId,
      //     participantId: this.getUser.id,
      //     participantName: this.getUser.name,
      //   })
      // );
      // this.stompClient.disconnect();
      // this.$router.push('/room/', this.getRoomId);
    },
    async onAnswerMessageReceived(payload) {
      this.talkFinish = true;
      const data = JSON.parse(payload.body);
      const speakImg = document.querySelectorAll('.member-name > img');
      const sentenceBox = document.querySelectorAll(
        '.sentence-board > .sentence-board-child > .member-sentence > span'
      )[this.answerIdx];
      sentenceBox.innerText = await data.message;
      if (data.correct) {
        speakImg[this.answerIdx].src = this.speakImg[3];
        this.setMic(0);
        this.removeMic();
        if (
          this.getUser.name ===
          this.room.teams[this.room.teamOrder[0]].members[this.answerIdx].participantName
        ) {
          console.log('이거 혹시 되냐???');
          console.log('이거 혹시 되냐???');
          console.log('이거 혹시 되냐???');
          this.stompClient.send('/pub/speaking/change/player', {}, this.getRoomId);
        }
      } else {
        speakImg[this.answerIdx].src = this.speakImg[2];
      }
    },
    onTalkingMessageReceived(payload) {
      console.log('talking 시작');
      console.log(this.talker);
      console.log(this.answerIdx);
      const data = JSON.parse(payload.body);
      if (
        this.talker !== this.answerIdx &&
        data.talker !== this.answerIdx &&
        this.talker !== data.talker
      ) {
        this.talker = data.talker;
        return;
      }
      console.log(data.talker);
      console.log(data.sentence);
      console.log('talking 끝');
      if (data.sentence === '') {
        this.talkFinish = false;
      }
      if (!this.talkFinish || data.sentence === '') {
        const speakImg = document.querySelectorAll('.member-name > img');
        speakImg[this.answerIdx].src = this.speakImg[1];
        const sentenceBox = document.querySelectorAll(
          '.sentence-board > .sentence-board-child > .member-sentence > span'
        )[this.answerIdx];
        sentenceBox.innerText = data.sentence;
      }
    },
    onChangePlayer(payload) {
      const data = JSON.parse(payload.body);
      this.isRecording = false;
      this.talkFinish = false;
      console.log('------------------change player--------------');
      console.log(data);
      console.log(this.room.teams[this.room.teamOrder[0]].members.length);
      console.log('------------------change player--------------');
      // 다음 팀으로 넘겨야 하는 경우
      if (
        data === 0 &&
        this.answerIdx + 1 === this.room.teams[this.room.teamOrder[0]].members.length
      ) {
        if (
          this.getUser.name ===
          this.room.teams[this.room.teamOrder[0]].members[this.answerIdx].participantName
        ) {
          this.stompClient.send('/pub/speaking/next/team', {}, this.getRoomId);
          this.answerIdx = data;
          this.setMic(0);
          this.removeMic();
        }
        // 다른 선수로 넘겨야 하는 경우
      } else {
        this.answerIdx = data;
        const speakImg = document.querySelectorAll('.member-name > img');
        speakImg[data].src = this.speakImg[0];
        if (
          this.getUser.name ===
          this.room.teams[this.room.teamOrder[0]].members[data].participantName
        ) {
          // 마이크 권한 주기
          this.setAudio(); // 이전 사람과 이번 턴의 index
          // 마이크 아이콘 변경
          this.setMic(2);
        }
      }
    },
    onChangeTeam(payload) {
      const data = JSON.parse(payload.body);
      this.room = data;
      this.setMic(2); // 이전 사람과 이번 팀의 index
      this.answerIdx = 0;
      console.log('===================onTeamChange==================');
      console.log('==================onTeamChange===================');
      // answerIdx 바꿔주기
      // 팀원들 싹 다시 뿌려주기
      // span 싹 지워주기
      // 0번에게 마이크 권한
    },
    onModalMessageReceived(payload) {
      const flag = JSON.parse(payload.body);
      this.ordermodal = flag;
    },
    onError() {},
    sendGameStartTrigger: function() {
      this.stompClient.send(`/pub/ssafymind/close/modal`, {}, this.getRoomId);
    },
  },
};
</script>
