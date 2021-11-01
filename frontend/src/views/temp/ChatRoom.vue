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
  </div>
</template>

<script>
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { chatURL } from '@/util/http-common.js';

export default {
  name: 'Chat',
  data() {
    return {
      id: '',
      name: '',
      message: '',
      stompClient: null,
      receivedMessages: [],
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.name = this.$route.query.name;
    this.socketConnect();
  },
  methods: {
    // 소켓 연결
    socketConnect() {
      var socket = new SockJS(chatURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect('', this.onConnected, this.onError);
    },
    onConnected() {
      // 채널 구독
      this.stompClient.subscribe('/sub/chat/room/' + this.id, this.onMessageReceived);

      // 입장 메세지 출력
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
    sendMessage() {
      if (this.message.trim() && this.stompClient) {
        this.stompClient.send(
          '/pub/chat/message',
          {},
          JSON.stringify({ roomId: this.id, message: this.message, writer: '김태현' })
        );
        this.message = '';
      }
    },
    // 메세지 수신
    onMessageReceived(payload) {
      console.log(payload);
      let receiveMessage = JSON.parse(payload.body);
      this.receivedMessages.push(receiveMessage);
    },
    // 에러 수신
    onError() {},
  },
};
</script>

<style></style>
