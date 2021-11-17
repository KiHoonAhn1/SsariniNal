import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

// const chatURL = 'http://localhost:8080/playssafy';
const chatURL = 'https://k5a302.p.ssafy.io:8080/playssafy';
// const homeURL = 'http://localhost:3000';
// const roomURL = 'http://localhost:3000/room';
const homeURL = 'https://k5a302.p.ssafy.io:8080';
const roomURL = 'https://k5a302.p.ssafy.io:8080/room';

/**
 *
 * @param {function} onConnected 구독 채널 정보
 * @param {function} onError 에러 반환 정보
 * @returns
 */
const socketConnect = (onConnected, onError) => {
  let socket = new SockJS(chatURL);
  let stompClient = Stomp.over(socket);
  stompClient.connect('', onConnected, onError);
  return stompClient;
};

export { socketConnect, homeURL, roomURL };
