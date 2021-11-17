<template>
  <div>
    <input type="checkbox" id="chk-hear-mic" /><label for="chk-hear-mic">마이크 소리 듣기</label>
    <button @click="onRecAudio">녹음</button>
    <button @click="offRecAudio">녹음 정지</button>
    <button @click="onSubmitAudioFile"></button>
    <button>초기화</button>
  </div>
</template>

<script>
import '@/css/speak-game.css';

export default {
  name: 'SpeakGame',
  data: () => {
    return {
      onRec: false,
      analyser: '',
      source: '',
      stream: '',
      media: '',
      audioUrl: '',
    };
  },
  mounted: function() {
    this.onRecAudio();
  },
  methods: {
    // 음원 정보를 담은 노드를 생성하거나 음원을 실행 또는 디코딩시키는 일을 한다.
    onRecAudio: function() {
      const audioCtx = new (window.AudioContext || window.AudioWorkletNode)();
      // 자바스크립트를 통해 음원의 진행상태에 직접 접근에 사용된다
      this.analyser = audioCtx.createScriptProcessor(0, 1, 1);

      const makeSound = (stream) => {
        // 내 컴퓨터의 마이크나 다른 소스를 통해 발생한 오디오 스트림의 정보를 담아준다.
        this.source = audioCtx.createMediaStreamSource(stream);
        this.source.connect(this.analyser);
        this.analyser.connect(audioCtx.destination);
      };

      // 마이크 사용 권한 획득
      navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
        const mediaRecorder = new MediaRecorder(stream);
        mediaRecorder.start();
        this.stream = stream;
        this.media = mediaRecorder;
        makeSound(this.stream);

        this.analyser.onaudioprocess = function(e) {
          // 3분(180초) 지나면 자동으로 음성 저장 및 녹음 중지
          if (e.playbackTime > 180) {
            this.stream.getAudioTracks().forEach(function(track) {
              track.stop();
            });
            mediaRecorder.stop();
            // 메서드가 호출된 노드 연결 해제
            this.analyser.disconnect();
            audioCtx.createMediaStreamSource(stream).disconnect();

            mediaRecorder.ondataavailable = function(e) {
              this.audioUrl = e.data;
              this.onRec = true;
            };
          } else {
            this.onRec = false;
          }
        };
      });
    },
    offRecAudio: function() {
      this.media.ondataavailable = function(e) {
        this.audioUrl = e.data;
        this.onRec = true;
      };

      this.stream.getAudioTracks().forEach(function(track) {
        track.stop();
      });

      this.media.stop();

      this.analyser.disconnect();
      this.source.disconnect();
    },
    onSubmitAudioFile: function() {
      if (this.audioUrl) {
        console.log(URL.createObjectURL(this.audioUrl));
      }
      const sound = new File([this.audioUrl], 'soundBlob', {
        lastModified: new Date().getTime(),
        type: 'audio',
      });
      console.log(sound);
    },
  },
};
</script>

<style></style>
