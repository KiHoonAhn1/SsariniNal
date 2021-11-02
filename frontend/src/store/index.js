import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [
    createPersistedState({
      paths: ['user', 'isLogin', 'visitedRoomId'],
    }),
  ],
  state: {
    // 유저 정보
    user: {
      id: null, // 유저 아이디
      name: null, // 유저 이름
    },
    isLogin: false,
    visitedRoomId: null, // 현재 유저가 방문중인 방의 ID
  },
  getters: {
    // 유저 정보 가져오기
    getUser(state) {
      return state.user;
    },
    // 로그인 여부 가져오기
    getIsLogin(state) {
      return state.isLogin;
    },
    // 방 ID 가져오기
    getRoomId(state) {
      return state.visitedRoomId;
    },
  },
  mutations: {
    // 1. 로그인, 접속 정보 저장
    LOGIN(state, user) {
      state.user.id = user.id;
      state.user.name = user.name;
    },
    // 2. 로그인 여부 변환
    CHANGE_LOGIN_STATE(state, payload) {
      state.isLogin = payload;
    },
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
    // 2. 로그인 여부 변환
    changeLoginState({ commit }, payload) {
      commit('CHANGE_LOGIN_STATE', payload);
    },
    // 3. 유저가 참여중인 방정보 갱신
    joinRoom({ commit }, roomId) {
      commit('JOIN_ROOM', roomId);
    },
  },
  modules: {},
});
