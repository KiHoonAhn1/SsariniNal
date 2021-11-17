<template>
  <div class="ssafymind-rank-modal">
    <div class="show-team-dialog">
      <div class="show-rank-modal">
        <div class="modal-body">
          <div class="congratulation">게임이 종료되었습니다!</div>
          <div class="show-rank-explainbox">
            <div class="round-team-container">
              <div v-if="curScore.length >= 1">
                <img class="rank-team-individual" src="~@/assets/onetop.png" alt="" />
                <span class="rank-team-name">{{ curScore[0][0] }}팀</span>
                <span class="rank-team-score">{{ curScore[0][1] }}점</span>
              </div>
              <div v-if="curScore.length >= 2">
                <img class="rank-team-individual2" src="~@/assets/twotop.png" alt="" />
                <span class="rank-team-name">{{ curScore[1][0] }}팀</span>
                <span class="rank-team-score">{{ curScore[1][1] }}점</span>
              </div>
              <div v-if="curScore.length >= 3">
                <img class="rank-team-individual2" src="~@/assets/threetop.png" alt="" />
                <span class="rank-team-name">{{ curScore[2][0] }}팀</span>
                <span class="rank-team-score">{{ curScore[2][1] }}점</span>
              </div>
            </div>
          </div>
          <button class="ssazip-rank-game-btn" @click="closeModal" v-if="host == getUser.id">
            대기실로
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '@/components/css/ssafymind/ssafymind-rank-modal.css';
import { mapGetters } from 'vuex';
export default {
  name: 'SsafymindRankModal',
  props: ['host', 'scores', 'curTeam'],
  data: function() {
    return {
      rankmodal: false,
      curScore: [],
    };
  },
  computed: {
    ...mapGetters(['getUser']),
  },
  created() {
    this.setScores();
  },
  methods: {
    closeModal() {
      this.$emit('getCloseRankModal', this.rankmodal);
      this.$emit('sendGoWaitingRoomTrigger');
    },
    setScores() {
      this.curScore = [];
      for (let i = 1; i < this.scores.length; i++) {
        // 팀 순서에 없으면 스킵
        if (this.curTeam[i]) {
          // i팀이 몇점인지 배열화 시켜 저장
          this.curScore.push([i, this.scores[i]]);
        }
      }
      // 다차원 배열 정렬
      this.curScore.sort((o1, o2) => {
        if (o2[1] > o1[1]) {
          return 1;
        } else if (o2[1] < o1[1]) {
          return -1;
        } else {
          if (o1[0] > o2[0]) {
            return 1;
          } else {
            return -1;
          }
        }
      });
    },
  },
};
</script>
<style>
body {
  margin: 0;
}
</style>
