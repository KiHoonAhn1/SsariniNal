<template>
  <div>
    <div class="speak-game">
      <Header v-bind:gameTitle="'또박또박 말해요'" />
      <div class="game-screen">
        <GameStatus game="speak" />
        <div class="game-board">
          <p class="sentence">"삼성 청년 소프트웨어 아카데미"</p>
          <div v-for="(member, i) in teamMember" :key="i" class="sentence-board">
            <div v-if="i % 2 != 1" class="member-left">
              <div class="member-name">
                <span>{{ member }}</span>
                <img src="@/assets/images/speak-success.png" alt="" />
              </div>
              <div class="member-sentence member-sentence-left">
                <span id="doin"></span>
              </div>
            </div>
            <div v-else class="member-right">
              <div class="member-sentence member-sentence-right">
                <span>삼성 청년 소프트웨어 아카데미</span>
              </div>
              <div class="member-name">
                <span>{{ member }}</span>
                <img src="@/assets/images/speak-fail.png" alt="" />
              </div>
            </div>
          </div>
          <img src="@/assets/images/mike-off.png" alt="" class="game-mike" id="record" />
        </div>
      </div>
    </div>

    <!-- 아래는 나중에 사용할 아이들 -->
    <div style="display:none;">
      <p>녹음하기</p>
      <input type="checkbox" id="chk-hear-mic" /><label for="chk-hear-mic">마이크 소리 듣기</label>
      <!-- <button id="record">녹음</button> -->
      <!-- <button id="stop">녹음 정지</button> -->
      <span id="final_span"></span>
      <span id="interim_span"></span>
      <div id="sound-clips"></div>
    </div>
  </div>
</template>

<script>
import '@/css/speaking-game.css';
import { mapGetters } from 'vuex';
import { socketConnect } from '@/util/socket-common.js';
import Header from '@/components/common/Header.vue';
import GameStatus from '@/components/GameStatus.vue';

export default {
  name: 'SpeakGame',
  components: {
    Header,
    GameStatus,
  },
  data: () => {
    return {
      record: {},
      stop: {},
      soundClips: {},
      chkHearMic: {},
      audioCtx: {},
      analyser: {},
      chunks: [],
      mediaRecorder: {},
      teamMember: ['김태현', '권희은', '차은채', '안기훈', '이장섭', '오일남'],
      answerIdx: 0,
      userAnswer: [],
      userAnswerIdx: 0,
      isRecording: false,
      // Socket 정보들
      stompClient: null,
      room: {},
      interimTranscript: '',
      finalTranscript: '',
    };
  },
  created() {
    this.stompClient = socketConnect(this.onConnected, this.onError);
  },
  mounted() {
    // this.getAudio();
    this.translate();
  },
  destroyed() {
    this.onDisconnect();
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
        alert('크롬에서만 동작합니다');
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
        console.log(isRecognizing);
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
        console.log('파이널', finalTranscript);
        console.log('파이널', interimTranscript);
        this.stompClient.send(
          `/pub/speaking/talk/${this.getRoomId}`,
          {},
          JSON.stringify({
            sentence: finalTranscript + interimTranscript,
          })
        );
      };

      speech.onerror = function(event) {
        if (event.error.match(/no-speech|audio-capture|not-allowed/)) {
          ignoreEndProcess = true;
        }
      };

      const record = document.querySelector('#record');
      record.addEventListener('click', async () => {
        if (this.isRecording) {
          let message = finalTranscript;
          record.src = '/img/mike-off.10e24890.png';

          // 마이크 버튼을 눌서 finalTranscript가 없을 때
          if (finalTranscript === this.finalTranscript) {
            message = this.finalTranscript + this.interimTranscript;
          }
          console.log('-----------------');
          console.log(message);
          console.log('-----------------');

          await this.stompClient.send(
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
          speech.stop();
        } else {
          record.src = '/img/mike-on.d4e34f6f.png';
          finalTranscript = '';
          this.finalTranscript = '';
          const doin = document.querySelector('#doin');
          doin.innerText = '';
          this.isRecording = true;
          speech.start();
        }
      });

      const stop = document.querySelector('#stop');
      console.log(stop.src);
      // stop.addEventListener("click", () => {

      // })

      speech.addEventListener('result', (event) => {
        const transcript = event['results'];
        // this.mediaRecorder.stop();
        console.log(transcript);
      });
    },
    nextTurn: function() {
      this.userAnswerIdx++;
      this.answerIdx++;
    },
    /**
     * 소켓 통신
     */
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
    },
    onConnected() {
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
      this.stompClient.subscribe(
        '/speaking/change/player/' + this.getRoomId,
        this.onChangePlayerMessageReceived
      );
      // 입장 시 데이터 수신
      this.stompClient.send(
        '/speaking/enter',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
          teamNo: this.getUser.teamNo,
        })
      );
    },
    onMessageReceived(payload) {
      if (payload.body === 'exit') {
        // 모든 참가자의 연결을 끊고
        this.onDisconnect();
        alert('방장이 퇴장하여 게임이 종료됩니다!');
        // 모든 참가자 내보내기
        this.$router.push('/room/' + this.getRoomId);
        return;
      }
      const data = JSON.parse(payload.body);
      this.room = data;
      console.log(data);
    },
    onDisconnect() {
      this.stompClient.send(
        '/pub/speaking/exit',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
        })
      );
      this.stompClient.disconnect();
      this.$router.push('/room/', this.getRoomId);
    },
    onAnswerMessageReceived(payload) {
      const data = JSON.parse(payload.body);
      const doin = document.querySelector('#doin');
      doin.innerText = data.message;
      console.log('answer Received---------------------');
      console.log(data);
      console.log('answer Received---------------------');
    },
    onTalkingMessageReceived(payload) {
      console.log(this.isRecording);
      if (this.isRecording) {
        const data = JSON.parse(payload.body);
        const doin = document.querySelector('#doin');
        doin.innerText = data.sentence;
        console.log(data.sentence);
      }
    },
    onChangePlayerMessageReceived(payload) {
      const data = JSON.parse(payload.body);
      console.log(data);
    },
    onError() {},
    /*  */
    // 음성 녹음해 blob 파일로 만들기
    /*  */
    getAudio: function() {
      this.record = document.getElementById('record');
      this.stop = document.getElementById('stop');
      this.soundClips = document.getElementById('sound-clips');
      this.chkHearMic = document.getElementById('chk-hear-mic');

      const audioCtx = new (window.AudioContext || window.webkitAudioContext)();
      this.audioCtx = audioCtx;

      const analyser = audioCtx.createAnalyser();
      this.analyser = analyser;

      function makeSound(stream) {
        const source = this.audioCtx.createMediaStreamSource(stream);
        source.connect(this.analyser);
        this.analyser.connect(this.audioCtx.destination);
      }

      if (navigator.mediaDevices) {
        console.log('getUserMedia supported.');

        navigator.mediaDevices
          .getUserMedia({ audio: true })
          .then((stream) => {
            const mediaRecorder = new MediaRecorder(stream);
            this.mediaRecorder = mediaRecorder;
            this.chkHearMic.onchange = (e) => {
              if (e.target.checked == true) {
                audioCtx.resume();
                makeSound(stream);
              } else {
                audioCtx.suspend();
              }
            };
            this.record.onclick = () => {
              this.mediaRecorder.start();
              console.log(this.mediaRecorder.state);
            };
            this.stop.onclick = () => {
              this.mediaRecorder.stop();
              console.log(this.mediaRecorder.state);
            };

            this.mediaRecorder.onstop = () => {
              console.log('data available after MediaRecorder.stop() called.');

              const clipName = prompt('오디오 파일 제목을 입력하세요.', new Date());

              const clipContainer = document.createElement('article');
              const clipLabel = document.createElement('p');
              const audio = document.createElement('audio');
              const deleteButton = document.createElement('button');

              clipContainer.classList.add('clip');
              audio.setAttribute('controls', '');
              deleteButton.innerHTML = '삭제';
              clipLabel.innerHTML = clipName;

              clipContainer.appendChild(audio);
              clipContainer.appendChild(clipLabel);
              clipContainer.appendChild(deleteButton);
              this.soundClips.appendChild(clipContainer);
              audio.controls = true;
              const blob = new Blob(this.chunks, {
                type: 'audio/ogg codecs=opus',
                // type: 'audio/wav; codecs=0'
              });
              console.log(blob);
              this.chunks = [];
              const audioURL = URL.createObjectURL(blob);
              audio.src = audioURL;
              console.log(audio);
              console.log('recorder stopped');

              deleteButton.onclick = (e) => {
                const evtTgt = e.target;
                evtTgt.parentNode.parentNode.removeChild(evtTgt.parentNode);
              };
            };

            this.mediaRecorder.ondataavailable = (e) => {
              this.chunks.push(e.data);
              console.log(this.chunks);
            };
          })
          .catch((err) => {
            console.log('The following error occurred: ' + err);
          });
      }
    },
  },
};
</script>

<style></style>
