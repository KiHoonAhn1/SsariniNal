<template>
  <div class="ssafymind-right">
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
import '@/components/css/ssafymind/ssafymind-right.css';
export default {
  name: 'SsafymindRight',
};
</script>

<style></style>
