<template>
  <div class="game-order-modal">
    <div class="show-team-dialog">
      <!-- <img src="~@/assets/ssafymindBeforeStart.png" id="ssafymind-before-start-img" alt="" v-show="explainmodal == true"> -->
      <!-- <GameExplainModal/> -->
      <div class="game-explain-modal" v-show="explainmodal == true">
        <div class="game-explain-dialog">
          <div class="game-explain-content">
            <div class="modal-body">
              <div class="game-explain-explainbox">
                <img src="~@/assets/ssafymindBeforeStart.png" id="ssafymind-explain-img" alt="">
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
              <!-- <img id="team-num-img" src="~@/assets/team2.png" alt="" />
              <img id="team-num-img" src="~@/assets/team3.png" alt="" />
              <img id="team-num-img" src="~@/assets/team4.png" alt="" />
              <img id="team-num-img" src="~@/assets/team5.png" alt="" />
              <img id="team-num-img" src="~@/assets/team6.png" alt="" />
              <img id="team-num-img" src="~@/assets/team7.png" alt="" />
              <img id="team-num-img" src="~@/assets/team8.png" alt="" />
              <img id="team-num-img" src="~@/assets/team9.png" alt="" />
              <img id="team-num-img" src="~@/assets/team10.png" alt="" /> -->
            </span>
            <button class="start-game-btn" @click="startModal" v-if="host == this.getUser.id">
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
// import GameExplainModal from '@/components/GameExplainModal';
import { mapGetters } from 'vuex';
export default {
  name: 'GameOrderModal',
  components:{
    // GameExplainModal
  },
  props: ['teamOrder', 'teamCnt', 'host'],
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
      this.$emit('getProgressBar', this.getProgressBar);
    },
    // closeImg: function(){
    //   console.log('나 안보이니?')
    //   setTimeout(this.explainmodal = false, 3000);
    // },
    closeExplainModal: function(){
      this.explainmodal = false;
    }
  },
  mounted: function(){
    // this.closeImg();
  }
};
</script>
<style>
body {
  margin: 0;
}
</style>
