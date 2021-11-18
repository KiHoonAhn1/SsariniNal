<template>
  <div class="game-order-modal">
    <div class="show-team-dialog">
      <div class="game-explain-modal" v-show="explainmodal == true">
        <div class="game-explain-dialog">
          <div class="game-explain-content">
            <div class="modal-body">
              <div class="game-explain-explainbox">
                <img
                  v-if="gameType === 'speaking'"
                  img
                  src="@/assets/images/speaking-modal.png"
                  id="ssafymind-explain-img"
                  alt=""
                />
                <img
                  v-else
                  src="~@/assets/ssafymindBeforeStart.png"
                  id="ssafymind-explain-img"
                  alt=""
                />
                <button v-if="gameType === 'speaking'" class="speaking-understand-btn" @click="closeExplainModal">
                  알겠습니다!
                </button>
                <button v-else class="understand-btn" @click="closeExplainModal">
                  알겠습니다!
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="show-team-content">
        <div class="modal-body">
          <div class="show-team-explainbox">
            <div class="show-team-order-group">
              문제 출제 순서는 다음과 같습니다!
            </div>
            <span class="show-team-order-img" v-for="idx in teamCnt" :key="idx">
              <img
                id="team-num-img"
                :src="require(`../assets/team${teamOrder[idx - 1]}.png`)"
                alt=""
              />
            </span>
            <button v-if="host == getUser.id" class="start-game-btn" @click="startModal">
              시작
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import '@/components/css/gameOrderModal.css';
import '@/components/css/gameExplainModal.css';
import { mapGetters } from 'vuex';
export default {
  name: 'GameOrderModal',
  props: ['teamOrder', 'teamCnt', 'host', 'gameType', 'user'],
  data: function() {
    return {
      ordermodal: false,
      startTime: false,
      getProgressBar: false,
      explainmodal: true,
    };
  },
  computed: {
    ...mapGetters(['getUser']),
  },
  methods: {
    startModal() {
      this.$emit('getCloseModal', this.ordermodal);
      this.$emit('startTimer', this.startTime);
      this.$emit('sendGameStartTrigger');
    },
    closeExplainModal: function() {
      this.explainmodal = false;
    },
  },
};
</script>
<style>
body {
  margin: 0;
}
</style>
