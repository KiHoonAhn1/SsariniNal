<template>
  <div class="waiting-room">
    <!-- 게임 선택 모달창 -->
    <SelectGameModal
      v-if="openmodal == true"
      @getOpenModal="getOpenModal"
      @infoSsafyMind="infoSsafyMind"
      @infoSpeakGame="infoSpeakGame"
      @infoJumpGame="infoJumpGame"
    />

    <div class="room-title">
      <span id="game-title">{{ room.name }}</span>
      <span id="game-code"
        >입장코드
        <button @click="copyCode" id="game-code-btn">복사</button>
      </span>
    </div>
    <div class="d-flex justify-content-center">
      <div class="room-left">
        <div class="btn-group">
          <button id="btn-1" class="btn" @click="showTeam1()">1</button>
          <button id="btn-2" class="btn" @click="showTeam2()">2</button>
          <button id="btn-3" class="btn" @click="showTeam3()">3</button>
          <button id="btn-4" class="btn" @click="showTeam4()">4</button>
          <button id="btn-5" class="btn" @click="showTeam5()">5</button>
          <button id="btn-6" class="btn" @click="showTeam6()">6</button>
          <button id="btn-7" class="btn" @click="showTeam7()">7</button>
          <button id="btn-8" class="btn" @click="showTeam8()">8</button>
          <button id="btn-9" class="btn" @click="showTeam9()">9</button>
          <button id="btn-10" class="btn" @click="showTeam10()">10</button>
        </div>
        <div class="team-group">
          <div class="team-group team-group-1" v-show="teamline1">
            <div id="team-1" class="team">1</div>
            <div id="team-1-name" class="team-name">권희은</div>
            <div id="team-1-name" class="team-name">김태현</div>
            <div id="team-1-name" class="team-name">안기훈</div>
            <div id="team-1-name" class="team-name">이장섭</div>
            <div id="team-1-name" class="team-name">차은채</div>
            <div id="team-1-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-2" v-show="teamline2">
            <div id="team-2" class="team">2</div>
            <div id="team-2-name" class="team-name">권희은</div>
            <div id="team-2-name" class="team-name">김태현</div>
            <div id="team-2-name" class="team-name">안기훈</div>
            <div id="team-2-name" class="team-name">이장섭</div>
            <div id="team-2-name" class="team-name">차은채</div>
            <div id="team-2-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-3" v-show="teamline3">
            <div id="team-3" class="team">3</div>
            <div id="team-3-name" class="team-name">권희은</div>
            <div id="team-3-name" class="team-name">김태현</div>
            <div id="team-3-name" class="team-name">안기훈</div>
            <div id="team-3-name" class="team-name">이장섭</div>
            <div id="team-3-name" class="team-name">차은채</div>
            <div id="team-3-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-4" v-show="teamline4">
            <div id="team-4" class="team">4</div>
            <div id="team-4-name" class="team-name">권희은</div>
            <div id="team-4-name" class="team-name">김태현</div>
            <div id="team-4-name" class="team-name">안기훈</div>
            <div id="team-4-name" class="team-name">이장섭</div>
            <div id="team-4-name" class="team-name">차은채</div>
            <div id="team-4-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-5" v-show="teamline5">
            <div id="team-5" class="team">5</div>
            <div id="team-5-name" class="team-name">권희은</div>
            <div id="team-5-name" class="team-name">김태현</div>
            <div id="team-5-name" class="team-name">안기훈</div>
            <div id="team-5-name" class="team-name">이장섭</div>
            <div id="team-5-name" class="team-name">차은채</div>
            <div id="team-5-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-6" v-show="teamline6">
            <div id="team-6" class="team">6</div>
            <div id="team-6-name" class="team-name">권희은</div>
            <div id="team-6-name" class="team-name">김태현</div>
            <div id="team-6-name" class="team-name">안기훈</div>
            <div id="team-6-name" class="team-name">이장섭</div>
            <div id="team-6-name" class="team-name">차은채</div>
            <div id="team-6-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-7" v-show="teamline7">
            <div id="team-7" class="team">7</div>
            <div id="team-7-name" class="team-name">권희은</div>
            <div id="team-7-name" class="team-name">김태현</div>
            <div id="team-7-name" class="team-name">안기훈</div>
            <div id="team-7-name" class="team-name">이장섭</div>
            <div id="team-7-name" class="team-name">차은채</div>
            <div id="team-7-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-8" v-show="teamline8">
            <div id="team-8" class="team">8</div>
            <div id="team-8-name" class="team-name">권희은</div>
            <div id="team-8-name" class="team-name">김태현</div>
            <div id="team-8-name" class="team-name">안기훈</div>
            <div id="team-8-name" class="team-name">이장섭</div>
            <div id="team-8-name" class="team-name">차은채</div>
            <div id="team-8-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-9" v-show="teamline9">
            <div id="team-9" class="team">9</div>
            <div id="team-9-name" class="team-name">권희은</div>
            <div id="team-9-name" class="team-name">김태현</div>
            <div id="team-9-name" class="team-name">안기훈</div>
            <div id="team-9-name" class="team-name">이장섭</div>
            <div id="team-9-name" class="team-name">차은채</div>
            <div id="team-9-name" class="team-name">싸린이</div>
          </div>
          <div class="team-group team-group-10" v-show="teamline10">
            <div id="team-10" class="team">10</div>
            <div id="team-10-name" class="team-name">권희은</div>
            <div id="team-10-name" class="team-name">김태현</div>
            <div id="team-10-name" class="team-name">안기훈</div>
            <div id="team-10-name" class="team-name">이장섭</div>
            <div id="team-10-name" class="team-name">차은채</div>
            <div id="team-10-name" class="team-name">싸린이</div>
          </div>
          <!-- 여기서부터는 관전자 -->
          <div class="team-group-extra">
            <div id="team-extra">관전</div>
            <div id="team-extra-name" class="team-name">권희은</div>
            <div id="team-extra-name" class="team-name">김태현</div>
            <div id="team-extra-name" class="team-name">안기훈</div>
            <div id="team-extra-name" class="team-name">이장섭</div>
            <div id="team-extra-name" class="team-name">차은채</div>
            <div id="team-extra-name" class="team-name">싸린이</div>
            <div id="team-extra-name" class="team-name">싸린이</div>
            <div id="team-extra-name" class="team-name">싸린이</div>
            <div id="team-extra-name" class="team-name">싸린이</div>
            <div id="team-extra-name" class="team-name">싸린이</div>
            <div id="team-extra-name" class="team-name">싸린이</div>
          </div>
        </div>
      </div>
      <div class="room-right">
        <div class="score">
          <div class="score-line d-flex justify-content-center">
            <p id="score-title">총 누적 점수</p>
            <div style="float:left">
              <span id="score-team">1팀</span>
              <span id="score-number">350점</span>
              <br /><br />
              <span id="score-team">2팀</span>
              <span id="score-number">350점</span>
              <br /><br />
              <span id="score-team">3팀</span>
              <span id="score-number">350점</span>
              <br /><br />
              <span id="score-team">4팀</span>
              <span id="score-number">350점</span>
              <br /><br />
              <span id="score-team">5팀</span>
              <span id="score-number">350점</span>
            </div>
            <div style="float:right">
              <span id="score-team">6팀</span>
              <span id="score-number">350점</span>
              <br /><br />
              <span id="score-team">7팀</span>
              <span id="score-number">350점</span>
              <br /><br />
              <span id="score-team">8팀</span>
              <span id="score-number">350점</span>
              <br /><br />
              <span id="score-team">9팀</span>
              <span id="score-number">350점</span>
              <br /><br />
              <span id="score-team">10팀</span>
              <span id="score-number">350점</span>
            </div>
          </div>
        </div>
        <div class="thumbnail">
          <div class="thumbnail-line">
            <div
              v-if="
                ssafymind_explain === false &&
                  speakgame_explain === false &&
                  jumpgame_explain === false
              "
            >
              <img id="ssazip-img" src="~@/assets/images/ssazip-create.png" alt="" />
              <div id="selectgame-name">게임 선택 버튼을 눌러 게임을 고르세요~!</div>
            </div>
            <div v-if="ssafymind_explain === true">
              <div id="game-name">싸피 마인드</div>
              <img id="ssafymind-img" src="~@/assets/ssafymind.png" alt="" />
            </div>
            <div v-if="speakgame_explain === true">
              <div id="game-name">또박또박 말해요</div>
              <img id="speakgame-img" src="~@/assets/speakgame.png" alt="" />
            </div>
            <div v-if="jumpgame_explain === true">
              <div id="game-name">싸집이 점프</div>
              <img id="jumpgame-img" src="~@/assets/jumpgame.png" alt="" />
            </div>
          </div>
        </div>
        <div>
          <button class="waiting-room-btn select-game" @click="openmodal = true">게임 선택</button>
          <button class="waiting-room-btn final-score">최종 결과</button>
          <i class="fas fa-play-circle play-btn fa-5x"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '@/css/room.css';
import SelectGameModal from '@/components/room/SelectGameModal';
import { mapGetters } from 'vuex';
import { socketConnect } from '@/util/socket-common.js';

export default {
  name: 'Room',
  components: {
    SelectGameModal, // 게임 선택 모달
  },
  data: function() {
    return {
      teamline1: false,
      teamline2: false,
      teamline3: false,
      teamline4: false,
      teamline5: false,
      teamline6: false,
      teamline7: false,
      teamline8: false,
      teamline9: false,
      teamline10: false,
      openmodal: false,
      ssafymind_explain: false,
      speakgame_explain: false,
      jumpgame_explain: false,
      // 방 정보
      room: {
        name: '',
        host: '',
        members: [],
      },
      // socket Client
      stompClient: null,
    };
  },
  // 방 생성시
  created() {
    // 소켓 연결
    this.stompClient = socketConnect(this.onConnected, this.onError);
  },
  // 방 삭제시
  destroyed() {
    this.onDisconnect();
  },
  computed: {
    ...mapGetters(['getRoomId', 'getUser']),
  },
  methods: {
    showTeam1: function() {
      const btn = document.querySelector('#btn-1');
      if (this.teamline1 == true) {
        this.teamline1 = false;
        btn.classList.remove('btn-1');
      } else if (this.teamline1 == false) {
        this.teamline1 = true;
        btn.classList.add('btn-1');
      }
    },
    showTeam2: function() {
      const btn = document.querySelector('#btn-2');
      if (this.teamline2 == true) {
        this.teamline2 = false;
        btn.classList.remove('btn-2');
      } else if (this.teamline2 == false) {
        this.teamline2 = true;
        btn.classList.add('btn-2');
      }
    },
    showTeam3: function() {
      const btn = document.querySelector('#btn-3');
      if (this.teamline3 == true) {
        this.teamline3 = false;
        btn.classList.remove('btn-3');
      } else if (this.teamline3 == false) {
        this.teamline3 = true;
        btn.classList.add('btn-3');
      }
    },
    showTeam4: function() {
      const btn = document.querySelector('#btn-4');
      if (this.teamline4 == true) {
        this.teamline4 = false;
        btn.classList.remove('btn-4');
      } else if (this.teamline4 == false) {
        this.teamline4 = true;
        btn.classList.add('btn-4');
      }
    },
    showTeam5: function() {
      const btn = document.querySelector('#btn-5');
      if (this.teamline5 == true) {
        this.teamline5 = false;
        btn.classList.remove('btn-5');
      } else if (this.teamline5 == false) {
        this.teamline5 = true;
        btn.classList.add('btn-5');
      }
    },
    showTeam6: function() {
      const btn = document.querySelector('#btn-6');
      if (this.teamline6 == true) {
        this.teamline6 = false;
        btn.classList.remove('btn-6');
      } else if (this.teamline6 == false) {
        this.teamline6 = true;
        btn.classList.add('btn-6');
      }
    },
    showTeam7: function() {
      const btn = document.querySelector('#btn-7');
      if (this.teamline7 == true) {
        this.teamline7 = false;
        btn.classList.remove('btn-7');
      } else if (this.teamline7 == false) {
        this.teamline7 = true;
        btn.classList.add('btn-7');
      }
    },
    showTeam8: function() {
      const btn = document.querySelector('#btn-8');
      if (this.teamline8 == true) {
        this.teamline8 = false;
        btn.classList.remove('btn-8');
      } else if (this.teamline8 == false) {
        this.teamline8 = true;
        btn.classList.add('btn-8');
      }
    },
    showTeam9: function() {
      const btn = document.querySelector('#btn-9');
      if (this.teamline9 == true) {
        this.teamline9 = false;
        btn.classList.remove('btn-9');
      } else if (this.teamline9 == false) {
        this.teamline9 = true;
        btn.classList.add('btn-9');
      }
    },
    showTeam10: function() {
      const btn = document.querySelector('#btn-10');
      if (this.teamline10 == true) {
        this.teamline10 = false;
        btn.classList.remove('btn-10');
      } else if (this.teamline10 == false) {
        this.teamline10 = true;
        btn.classList.add('btn-10');
      }
    },
    getOpenModal(openmodal) {
      this.openmodal = openmodal;
      // console.log("모달 닫히고 열리고", openmodal);
    },
    infoSsafyMind(ssafymind_explain) {
      this.ssafymind_explain = ssafymind_explain;
      this.speakgame_explain = false;
      this.jumpgame_explain = false;
      // console.log("싸피마인드 사진", ssafymind_explain);
    },
    infoSpeakGame(speakgame_explain) {
      this.speakgame_explain = speakgame_explain;
      this.ssafymind_explain = false;
      this.jumpgame_explain = false;
      // console.log("또박또박 사진", speakgame_explain);
    },
    infoJumpGame(jumpgame_explain) {
      this.jumpgame_explain = jumpgame_explain;
      this.ssafymind_explain = false;
      this.speakgame_explain = false;
      // console.log("점프 사진", jumpgame_explain);
    },
    // 입장코드 복사
    copyCode() {
      this.$copyText(this.getRoomId);
      alert('입장 코드를 복사했습니다!');
    },
    /**
     * Socket 요청들
     */
    // 게임 방 입장 : 정보 구독 및 유저 정보 전송
    onConnected() {
      this.stompClient.subscribe('/game/room/' + this.getRoomId, this.onMessageReceived);
      this.stompClient.send(
        '/pub/game/enter',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
          teamNo: 0,
        })
      );
    },
    // 메세지 수신
    onMessageReceived(payload) {
      // 방장이 퇴장한 경우
      if (payload.body == 'exit') {
        // 모든 참가자의 연결을 끊고
        this.onDisconnect();
        alert('방장이 퇴장하여 게임이 종료됩니다!');
        // 모든 참가자 내보내기
        this.$router.push('/room');
        return;
      }
      let room = JSON.parse(payload.body);
      this.room.name = room.name;
      this.room.host = room.host;
      this.room.members = room.members;
      console.log(this.room);
    },
    onError() {},
    // 게임 방 퇴장 소켓 연결 해제 및 게임 방 유저 정보 삭제
    onDisconnect() {
      this.stompClient.send(
        '/pub/game/exit',
        {},
        JSON.stringify({
          roomId: this.getRoomId,
          participantId: this.getUser.id,
          participantName: this.getUser.name,
        })
      );
      this.stompClient.disconnect();
    },
  },
};
</script>

<style>
body {
  margin: 0;
}
</style>
