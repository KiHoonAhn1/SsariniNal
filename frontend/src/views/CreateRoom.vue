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
      <input
        type="text"
        placeholder="방 제목을 설정해주세요"
        v-model="roomName"
        @enter="createGameRoom"
      />
      <button @click="createGameRoom">방 만들기</button>
    </div>
  </div>
</template>

<script>
import '@/css/create-room.css';
import axios from '@/util/http-common.js';
import { mapActions, mapGetters } from 'vuex';
import swal from 'sweetalert';


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
        teamNo: '',
      };
      this.login(user);
      // 로그아웃 상태로 전환
      this.changeLoginState(false);
      // 메인페이지로 이동
      this.$router.push('/');
    },
    // 게임 방 만들기
    createGameRoom() {
      // 방 이름은 최소 1글자 이상, 15글자 이하로 한다
      if (this.roomName.length < 1 || this.roomName.length > 15) {
        swal({
            // className:'alert',
            title: "방 이름은 1글자 이상,",
            text: "15글자 이하로 입력해주세요",
            icon: "/img/ssazip-logo.png",
            buttons: {
            text: '확인',
          },
        })
        return;
      }
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
