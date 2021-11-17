<template>
  <div class="ssafymind-left">
    <div id="questionTeam-title">문제 내는 팀</div>
    <img
      class="team-img"
      id=""
      :src="require(`../assets/team${teamOrder[0]}.png`)"
      alt=""
      v-if="teamOrder != null && teamOrder[0] != null"
    />
    <h3 v-else>게임이 종료되었습니다!</h3>
    <div class="team-members-group" v-if="teamOrder != null && teamOrder[0] != null">
      <span v-for="(member, idx) in teams[teamOrder[0]].members" :key="idx">
        <span class="team-members myturn" v-if="idx == curPlayer">{{
          member.participantName
        }}</span>
        <span class="team-members" v-else>{{ member.participantName }}</span>
      </span>
    </div>
    <SpeakingTimer v-if="game === 'speak'" />
    <div v-else>
      <div id="progress" v-if="getProgressBar == false">
        <div id="progress-bar"></div>
      </div>
    </div>
    <div class="next-team-block">
      <div
        class="next-team-ready"
        v-if="teamOrder != null && teamOrder[0] != null && teamOrder.length > 1"
      >
        다음 팀 준비하세요~!
      </div>
      <img
        class="team-img"
        id=""
        :src="require(`../assets/team${teamOrder[1]}.png`)"
        alt=""
        v-if="teamOrder != null && teamOrder[0] != null && teamOrder.length > 1"
      />
      <div class="next-team-ready" v-else>
        <div id="end">마지막 팀 입니다!</div>
        <img id="ssazip-group" src="~@/assets/ssazip-group.png" alt="" />
      </div>
    </div>
    <div class="ssafymind-score">
      <div class="ssafymind-score-line">
        <div v-for="(score, idx) in curScore" :key="idx">
          <div class="winner-block" v-if="score[1] != 0 && idx + 1 == 1">
            <span class="winner winner-one">{{ idx + 1 }}등</span>
            <span class="winner-team">{{ score[0] }}팀</span>
            <span class="winner-score winner-one">{{ score[1] }}점</span>
          </div>
          <div class="winner-block" v-else-if="score[1] != 0 && idx + 1 == 2">
            <span class="winner winner-two">{{ idx + 1 }}등</span>
            <span class="winner-team">{{ score[0] }}팀</span>
            <span class="winner-score winner-two">{{ score[1] }}점</span>
          </div>
          <div class="winner-block" v-else-if="score[1] != 0 && idx + 1 == 3">
            <span class="winner winner-three">{{ idx + 1 }}등</span>
            <span class="winner-team">{{ score[0] }}팀</span>
            <span class="winner-score winner-three">{{ score[1] }}점</span>
          </div>
          <div class="winner-block" v-else>
            <span class="winner">{{ idx + 1 }}등</span>
            <span class="winner-team">{{ score[0] }}팀</span>
            <span class="winner-score">{{ score[1] }}점</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '@/components/css/game-status.css';
import SpeakingTimer from '@/components/common/SpeakingTimer.vue';
import '@/components/css/ssafymind/progressbar.css';

export default {
  name: 'GameStatus',
  data: function() {
    return {
      getProgressBar: false,
      curScore: [],
    };
  },
  components: {
    SpeakingTimer,
  },
  props: ['game', 'teamOrder', 'teams', 'curPlayer', 'scores', 'curTeam', 'curTeamCnt'],
  watch: {
    // scores가 갱신되면 점수 정보도 갱신
    scores() {
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

<style></style>
