<template>
  <div class="home">
    <img src="@/assets/images/user-background.jpg" alt="background-img" class="background-img" />
    <div>
      <img src="@/assets/images/ssazip-home.png" alt="ssazip-home-img" class="ssazip-home-img" />
      <div class="enter">
        <div class="enter-inputs">
          <input
            type="text"
            name="nickname"
            id="nickname"
            placeholder="이름을 입력해주세요"
            v-model="user.userName"
          />
          <input
            type="text"
            name="part"
            id="part"
            placeholder="참여코드를 입력해주세요"
            v-model="roomId"
          />
        </div>
        <button class="button-enter" @click="joinGameRoom">참여하기</button>
      </div>
      <router-link to="login">
        <button class="button-create">게임을 만드시겠어요?</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import '@/css/home.css';
import { mapActions } from 'vuex';
import axios from '@/util/http-common.js';

export default {
  name: 'Home',
  data() {
    return {
      user: {
        userId: '',
        userName: '',
      },
      roomId: '',
    };
  },
  methods: {
    joinGameRoom() {
      axios({
        method: 'get',
        url: `/game/room/${this.roomId}`,
      })
        .then((res) => {
          let exist = res.data;
          // 방이 존재하지 않는 경우 존재하지 않음을 표시
          if (!exist) {
            alert('해당 방은 존재하지 않습니다. 입장코드를 확인하세요!');
            return;
          }
          // 방이 존재하는 경우
          // 유저 정보 저장
          this.login(this.user);
          // 유저가 참여중인 방 정보 갱신
          this.joinRoom(this.roomId);
          // 방으로 이동
          this.$router.push('/room/' + this.roomId);
        })
        .catch(() => {});
    },
    ...mapActions(['login', 'joinRoom']),
  },
};
</script>

<style></style>
