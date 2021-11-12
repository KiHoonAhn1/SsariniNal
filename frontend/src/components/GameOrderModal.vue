<template>
  <div class="game-order-modal">
    <div class="show-team-dialog">
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
            <button class="start-game-btn" @click="startModal">시작</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import '@/components/css/gameOrderModal.css';
export default {
  name: 'GameOrderModal',
  props: ['teamOrder', 'teamCnt'],
  data: function() {
    return {
      ordermodal: false,
      startTime: false,
    };
  },
  methods: {
    startModal() {
      this.$emit('getCloseModal', this.ordermodal);
      this.$emit('startTimer', this.startTime);
      this.$emit('sendGameStartTrigger');
    },
  },
};
</script>
<style>
body {
  margin: 0;
}
</style>
