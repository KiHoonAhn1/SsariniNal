<template>
  <div class="room-create">
    <img src="@/assets/images/user-background.jpg" alt="background-img" class="background-img" />
    <div class="logout">
      <span class="logout-text">이장섭님 안녕하세요</span>
      <button class="logout-button">로그아웃</button>
    </div>
    <div class="create-form">
      <img
        src="@/assets/images/ssazip-create.png"
        alt="ssazip-create-img"
        class="ssazip-create-img"
      />
      <input type="text" placeholder="방 제목을 설정해주세요" v-model="roomName" />
      <button @click="createGameRoom">방 만들기</button>
    </div>
  </div>
</template>

<script>
import '@/css/create-room.css';
import axios from '@/util/http-common.js';
import { mapActions } from 'vuex';

export default {
  name: 'CreateRoom',
  component: {},
  data() {
    return {
      roomName: '',
      userName: '김싸피', // temp
    };
  },
  methods: {
    // 게임 방 만들기
    createGameRoom() {
      axios({
        method: 'post',
        url: `/game/room/${this.roomName}/${this.userName}`,
      })
        .then((res) => {
          // room 정보 받기
          let room = res.data;
          // 방장이 참가중인 방 갱신
          this.joinRoom(room.roomId);
          // 방으로 이동
          this.$router.push('/room/' + room.roomId);
        })
        .catch(() => {});
    },
    ...mapActions(['joinRoom']),
  },
};
</script>

<style></style>
