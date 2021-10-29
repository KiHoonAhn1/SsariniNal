<template>
  <div>
    <div>
      <h1>채팅방 목록</h1>
      <div>
        <input type="text" placeholder="방 제목을 입력하세요." v-model="roomName" />
        <button @click="createChatRoom">방 만들기</button>
      </div>
      <div v-for="(room, idx) in this.rooms" :key="idx">
        <router-link
          :to="{ name: 'Chat', query: { name: `${room.name}`, id: `${room.roomId}` } }"
          >{{ room.name }}</router-link
        >
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import axios from '@/util/http-common.js';

export default {
  name: 'App',
  component: {},
  data() {
    return {
      roomName: '',
      rooms: [],
    };
  },
  created() {
    this.getChatRooms();
  },
  methods: {
    // 채팅방 만들기
    createChatRoom() {
      axios({
        method: 'post',
        url: `/chat/room/${this.roomName}`,
      })
        .then(() => {
          this.getChatRooms();
        })
        .catch(() => {});
    },
    // 채팅방 목록 조회
    getChatRooms() {
      axios({
        method: 'get',
        url: `/chat/rooms`,
      })
        .then((res) => {
          this.rooms = res.data;
        })
        .catch((err) => {}); // eslint-disable-line no-unused-vars
    },
  },
};
</script>

<style></style>
