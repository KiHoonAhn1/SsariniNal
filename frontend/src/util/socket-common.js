import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

// const chatURL = 'http://localhost:8080/playssafy';
const chatURL = 'http://3.38.99.194:8080/playssafy';

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

export { socketConnect };
