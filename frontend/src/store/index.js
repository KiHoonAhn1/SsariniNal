import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [
    createPersistedState({
      paths: ['userId', 'userName', 'visitedRoomId'],
    }),
  ],
  state: {
    userId: null, // 유저 아이디
    userName: null, // 유저 이름
    visitedRoomId: null, // 현재 유저가 방문중인 방의 ID
  },
  getters: {
    // 방 ID 가져오기
    getRoomId(state) {
      return state.visitedRoomId;
    },
  },
  mutations: {
    // 1. 방장 로그인 정보 저장
    LOGIN(state, user) {
      state.userId = user.userId;
      state.userName = user.userName;
    },
    // 2. 유저 방 접속시 정보 저장
    // 3. 유저가 참여중인 방정보 갱신
    JOIN_ROOM(state, roomId) {
      state.visitedRoomId = roomId;
    },
  },
  actions: {
    // 1. 방장 로그인 정보 저장
    login({ commit }, user) {
      commit('LOGIN', user);
    },
    // 2. 유저 방 접속시 정보 저장
    // 3. 유저가 참여중인 방정보 갱신
    joinRoom({ commit }, roomId) {
      commit('JOIN_ROOM', roomId);
    },
  },
  modules: {},
});
