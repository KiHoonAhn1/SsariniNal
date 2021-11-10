<template>
  <div class="waiting-room-modal">
    <div class="wr-selectgame-dialog">
      <div class="wr-selectgame-content">
        <div class="wr-modal-body">
          <div class="wr-explain-game-group">
            <div id="wr-ssafymind-modal" @click="ssafymindExplain()">
              <img id="wr-ssafymind-modal-img" src="~@/assets/ssafymind.png" alt="" />
            </div>
            <div id="wr-speakgame-modal" @click="speakgameExplain()">
              <img id="wr-speakgame-modal-img" src="~@/assets/speakgame.png" alt="" />
            </div>
            <div id="wr-jumpgame-modal" @click="jumpgameExplain()">
              <img id="wr-jumpgame-modal-img" src="~@/assets/jumpgame.png" alt="" />
            </div>
          </div>
          <div class="wr-explainbox">
            <!-- <img id="ssazip-img-modal" src="~@/assets/images/ssazip-create.png" alt=""> -->
            <div
              class="wr-explain-ssafygame-title"
              v-if="
                ssafymind_explain == false &&
                  speakgame_explain == false &&
                  jumpgame_explain == false
              "
            >
              위에 보이는 3가지 게임 중에 하나를 선택하세요!
            </div>
            <div class="wr-explain-ssafymind" v-if="ssafymind_explain == true">
              <div class="wr-explain-ssafygame-title">
                "싸피마인드!"
              </div>
              <div class="wr-explain-ssafymind-content">
                팀원들이 차례대로 주어진 제시어를 그리는 게임입니다.<br />
                자신의 차례가 되면 5초안에 제시어를 그려주세요!<br />
                다른 팀원들은 그림을 보고 제시어를 맞춰주세요!<br />
                주어진 시간 안에 최대한 많은 점수를 획득하세요.
              </div>
              <div class="wr-explain-speakgame-num">
                팀 별 문제 수 :
              </div>
            </div>
            <div class="wr-explain-speakgame" v-if="speakgame_explain == true">
              <div class="wr-explain-ssafygame-title">
                "또박또박 말해요!"
              </div>
              <div class="wr-explain-speakgame-content">
                팀원들이 차례대로 주어진 제시어를 말하는 게임입니다.<br />
                음성인식이 알맞게 되도록 또.박.또.박 읽어주세요!<br />
                모든 팀원이 순서대로 제시어를 모두 읽으면 통과.<br />
                주어진 시간 안에 최대한 많은 제시어를 읽어주세요.
              </div>
              <div class="wr-explain-speakgame-num">
                팀 별 문제 수 :
              </div>
            </div>
            <div class="wr-explain-jumpgame" v-if="jumpgame_explain == true">
              <div class="wr-explain-ssafygame-title">
                "싸집이 점프!"
              </div>
              <div class="wr-explain-jumpgame-content">
                팀원들이 차례대로 장애물을 피하는 게임입니다.<br />
                스페이스바를 사용하여 장애물을 점프해서 피해주세요!<br />
                모든 팀원이 순서대로 장애물을 모두 피하면 통과.<br />
                장애물을 피해 상대방 팀보다 최대한 많은 시간 버텨주세요.
              </div>
            </div>
            <button class="wr-select-game-ok" @click="onModal">선택 완료</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import '@/components/css/selectGameModal.css';
export default {
  name: 'SelectGameModal',
  data: function() {
    return {
      openmodal: false,
      ssafymind_explain: false,
      speakgame_explain: false,
      jumpgame_explain: false,
    };
  },
  methods: {
    ssafymindExplain: function() {
      if (this.speakgame_explain == true) {
        this.speakgame_explain = false;
        this.$emit('infoSpeakGame', this.speakgame_explain);
      } else if (this.jumpgame_explain == true) {
        this.jumpgame_explain = false;
        this.$emit('infoJumpGame', this.jumpgame_explain);
      }
      this.ssafymind_explain = true;
      this.$emit('infoSsafyMind', this.ssafymind_explain);
    },
    speakgameExplain: function() {
      if (this.ssafymind_explain == true) {
        this.ssafymind_explain = false;
        this.$emit('infoSsafyMind', this.ssafymind_explain);
      } else if (this.jumpgame_explain == true) {
        this.jumpgame_explain = false;
        this.$emit('infoJumpGame', this.jumpgame_explain);
      }
      this.speakgame_explain = true;
      this.$emit('infoSpeakGame', this.speakgame_explain);
    },
    jumpgameExplain: function() {
      if (this.speakgame_explain == true) {
        this.speakgame_explain = false;
        this.$emit('infoSpeakGame', this.speakgame_explain);
      } else if (this.ssafymind_explain == true) {
        this.ssafymind_explain = false;
        this.$emit('infoSsafyMind', this.ssafymind_explain);
      }
      this.jumpgame_explain = true;
      this.$emit('infoJumpGame', this.jumpgame_explain);
    },
    onModal() {
      this.$emit('getOpenModal', this.openmodal);
    },
  },
};
</script>
<style>
body {
  margin: 0;
}
</style>
