<template>
  <div class="room-title">
    <span id="game-title">{{ gameTitle }}</span>
    <button id="game-over-btn" v-if="getUser.id == host" @click="onDisconnect()">게임 중단</button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
  name: 'Header',
  props: {
    gameTitle: {
      type: String,
    },
    host: {
      type: String,
    },
  },
  computed: {
    ...mapGetters(['getUser']),
  },
  methods: {
    onDisconnect() {
      this.$emit('onDisconnect');
    },
  },
};
</script>

<style>
.room-title {
  background-color: #fcc861;
  height: 56px;
  margin-bottom: 1.5%;
  padding-top: 8px;
}
#game-title {
  position: absolute;
  left: 50%;
  font-weight: bold;
  font-size: 2rem;
  transform: translate(-50%);
}
#game-over-btn {
  font-weight: bold;
  font-size: 1.4rem;
  width: 130px;
  height: 40px;
  border-radius: 20px;
  color: white;
  border: 0;
  outline: 0;
  cursor: pointer;
  background-color: #cd5069;
  float: right;
  margin-top: 4px;
  margin-right: 35px;
}
</style>
