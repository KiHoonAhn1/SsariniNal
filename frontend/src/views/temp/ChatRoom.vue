<template>
  <div>
    <h1>'{{ name }}' 채팅방</h1>
    <div>
      <input
        type="text"
        placeholder="메세지를 입력하세요"
        v-model="message"
        @keyup.enter="sendMessage"
      />
      <div v-for="(msg, idx) in receivedMessages" :key="idx">
        <p>{{ msg.message }}</p>
      </div>
    </div>
    <hr />
    <!-- <div>
      <input type="checkbox" id="chk-hear-mic"><label for="chk-hear-mic">마이크 소리 듣기</label>
      <button id="record">녹음</button>
      <button id="stop">중지</button>
      <div id="sound-clips"></div>
    </div> -->
    <div>
      <p>비디오/오디오 통신</p>
      <!-- <video src="@/assets/images/back.mp4" id="myFace" style="width: 400px; border: 1px solid black;" autoplay playsinline/> -->
      <video id="myFace" style="width: 400px; border: 1px solid black;" autoplay playsinline />
      <br />
      <button id="video">비디오 On</button>
      <button id="audio">소리 On</button>
    </div>
  </div>
</template>

<script src="/socket.io/socket.io.js"></script>
<script>
import { socketConnect } from '@/util/socket-common.js';

export default {
  name: 'Chat',
  data() {
    return {
      id: '',
      name: '',
      message: '',
      stompClient: null,
      receivedMessages: [],
      record: {},
      stop: {},
      soundClips: {},
      chkHearMic: {},
      audioCtx: {},
      analyser: {},
      chunks: [],
      video: false,
      audio: false,
      myStream: {},
      myPeerConnection: {},
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.name = this.$route.query.name;
    this.stompClient = socketConnect(this.onConnected, this.onError);
    // console.log(this.stompClient);
  },
  mounted() {
    // this.getAudio();
    // this.translate();
    this.socketio();
  },
  methods: {
    socketio: async function() {
      let myStream, myPeerConnection;
      const myFace = document.querySelector('#myFace');
      const video = document.querySelector('#video');
      const audio = document.querySelector('#audio');
      await navigator.mediaDevices
        .getUserMedia({
          audio: true,
          video: true,
        })
        .then((res) => {
          console.log(res);
          this.myStream = res;
          myStream = res;
          myFace.srcObject = res;
        });
      console.log(this.myStream);

      const makeConnection = () => {
        this.myPeerConnection = new RTCPeerConnection();
        this.myStream
          .getTracks()
          .forEach((track) => this.myPeerConnection.addTrack(track, this.myStream));
      };
      makeConnection();

      video.addEventListener('click', () => {
        this.myStream.getVideoTracks().forEach((track) => (track.enabled = !track.enabled));
        if (this.video) {
          video.innerText = '비디오 On';
          this.video = false;
        } else {
          video.innerText = '비디오 Off';
          this.video = true;
        }
      });

      audio.addEventListener('click', () => {
        this.myStream.getAudioTracks().forEach((track) => (track.enabled = !track.enabled));
        if (this.audio) {
          audio.innerText = '오디오 On';
          this.audio = false;
        } else {
          audio.innerText = '오디오 Off';
          this.audio = true;
        }
      });

      const getMikes = async () => {
        const devices = await navigator.mediaDevices.enumerateDevices();
        const audios = devices.filter((device) => device.kind === 'audioinput');
        console.log(audios);
      };
      getMikes();
    },
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
              this.stompClient.send(
                '/pub/chat/message',
                {},
                JSON.stringify({ roomId: this.id, message: blob.size, writer: '김태현' })
              );
              console.log(audio);
              console.log('recorder stopped');

              deleteButton.onclick = (e) => {
                const evtTgt = e.target;
                evtTgt.parentNode.parentNode.removeChild(evtTgt.parentNode);
              };
            };

            this.mediaRecorder.ondataavailable = (e) => {
              this.chunks.push(e.data);
            };
          })
          .catch((err) => {
            console.log('The following error occurred: ' + err);
          });
      }
    },
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

      const final_span = document.querySelector('#final_span');
      const interim_span = document.querySelector('#interim_span');
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

      speech.onresult = function(event) {
        let interimTranscript = '';
        if (typeof event.results === 'undefined') {
          speech.onend = null;
          speech.stop();
          return;
        }

        for (let i = event.resultIndex; i < event.results.length; ++i) {
          const transcript = event.results[i][0].transcript;

          if (event.results[i].isFinal) {
            finalTranscript += transcript;
            console.log('파이널', finalTranscript);
            console.log('파이널', transcript);
          } else {
            interimTranscript += transcript;
          }
        }
        final_span.innerHTML = finalTranscript;
        interim_span.innerHTML = interimTranscript;
        console.log(interimTranscript);
      };

      speech.onerror = function(event) {
        if (event.error.match(/no-speech|audio-capture|not-allowed/)) {
          ignoreEndProcess = true;
        }
      };
      console.log(speech);

      document.querySelector('#record').addEventListener('click', () => {
        speech.start();
      });

      document.querySelector('#stop').addEventListener('click', () => {
        speech.stop();
      });

      speech.addEventListener('result', (event) => {
        const transcript = event['results'];
        // this.mediaRecorder.stop();
        console.log(transcript);
      });
    },
    // 채팅 채널 구독 및 입장 메세지 출력
    onConnected() {
      this.stompClient.subscribe('/chat/room/' + this.id, this.onMessageReceived);
      this.stompClient.send(
        '/pub/chat/message',
        {},
        JSON.stringify({ roomId: this.id, message: '입장하셨습니다', writer: '김태현' })
      );
    },
    // 소켓 연결 해제
    socketDisconnect() {
      this.stompClient.socketDisconnect();
    },
    // 메세지 발신
    // async sendMessage() {
    //   if (this.message.trim() && this.stompClient) {
    //     console.log(this.id)
    //     // const offer = await this.myPeerConnection.createOffer();
    //     // this.myPeerConnection.setLocalDescription(offer);
    //     this.stompClient.send(
    //       '/pub/chat/message',
    //       {},
    //       JSON.stringify({ roomId: this.id, message: this.message, writer: '김태현' })
    //     );
    //     this.message = '';
    //   }
    // },
    async sendMessage() {
      const offer = await this.myPeerConnection.createOffer();
      this.myPeerConnection.setLocalDescription(offer);
      if (offer.type && this.stompClient) {
        this.stompClient.send(
          '/pub/chat/audio',
          {},
          JSON.stringify({ roomId: this.id, offer: { type: offer.type }, writer: '안기훈' })
        );
      }
    },
    // 메세지 수신
    onMessageReceived(payload) {
      console.log(payload);
      let receiveMessage = JSON.parse(payload.body);
      this.receivedMessages.push(receiveMessage);
      console.log(receiveMessage);
    },
    // 에러 수신
    onError() {},
  },
};
</script>

<style></style>
