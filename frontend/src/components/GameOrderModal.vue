<template>
  <div class="game-order-modal">
    <div class="show-team-dialog">
      <div class="game-explain-modal" v-show="explainmodal == true">
        <div class="game-explain-dialog">
          <div class="game-explain-content">
            <div class="modal-body">
              <div class="game-explain-explainbox">
<<<<<<< HEAD
                <img src="~@/assets/ssafymindBeforeStart.png" id="ssafymind-explain-img" alt="" />
=======
                <img v-if="gameType" img src="@/assets/images/mike.png" id="ssafymind-explain-img" alt="">
                <img v-else src="~@/assets/ssafymindBeforeStart.png" id="ssafymind-explain-img" alt="">
>>>>>>> d58272e81557e2665b88e9dfbe9856a2a108988e
                <button class="understand-btn" @click="closeExplainModal">
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
            <button class="start-game-btn" @click="startModal">
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
<<<<<<< HEAD
  props: ['teamOrder', 'teamCnt', 'host'],
=======
  components:{
    // GameExplainModal
  },
  props: ['teamOrder', 'teamCnt', 'host', 'gameType'],
>>>>>>> d58272e81557e2665b88e9dfbe9856a2a108988e
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
<<<<<<< HEAD
      if (this.host == this.getUser.id) {
        this.$emit('startTimer', this.startTime);
        this.$emit('sendGameStartTrigger');
        this.$emit('getProgressBar', this.getProgressBar);
      }
=======
      this.$emit('startTimer', this.startTime);
      this.$emit('sendGameStartTrigger');
>>>>>>> d58272e81557e2665b88e9dfbe9856a2a108988e
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
