<template>
  <div class="room-create">
    <img src="@/assets/images/user-background.jpg" alt="background-img" class="background-img" />
    <div class="logout">
      <span class="logout-text">{{ getUser.name }}님 안녕하세요</span>
      <button class="logout-button" @click="logout">로그아웃</button>
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
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'CreateRoom',
  component: {},
  data() {
    return {
      roomName: '',
    };
  },
  computed: {
    ...mapGetters(['getUser']),
  },
  methods: {
    // 로그아웃
    logout() {
      // 유저 정보 없애기
      let user = {
        id: '',
        name: '',
      };
      this.login(user);
      // 로그아웃 상태로 전환
      this.changeLoginState(false);
      // 메인페이지로 이동
      this.$router.push('/login');
    },
    // 게임 방 만들기
    createGameRoom() {
      axios({
        method: 'post',
        url: `/game/create/${this.roomName}`,
        data: {
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
        },
      })
        .then((res) => {
          // room 정보 받기
          let room = res.data;
          console.log(room);
          // 방장이 참가중인 방 갱신
          this.joinRoom(room.id);
          // 방으로 이동
          this.$router.push('/room/' + room.id);
        })
        .catch(() => {});
    },
    ...mapActions(['joinRoom', 'changeLoginState', 'login']),
  },
};
</script>

<style></style>
