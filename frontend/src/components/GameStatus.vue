<template>
  <div class="ssafymind-left">
    <div id="questionTeam-title">문제 내는 팀</div>
    <img
      class="team-img"
      id=""
      :src="require(`../assets/team${teamOrder[0]}.png`)"
      alt=""
      v-if="teamOrder != null"
    />
    <div class="team-members-group" v-if="teamOrder != null">
      <span v-for="(member, idx) in teams[teamOrder[0]].members" :key="idx">
        <span class="team-members myturn" v-if="idx == curPlayer">{{
          member.participantName
        }}</span>
        <span class="team-members" v-else>{{ member.participantName }}</span>
      </span>
    </div>
    <SpeakingTimer v-if="game === 'speak'" />
    <!-- <ProgressBar v-else /> -->
    <div v-else>
      <!-- <div v-if="teamOrder != null">
        <div v-for="(member, idx) in teams[teamOrder[0]].members" :key="idx">
          <div v-if="idx == curPlayer">
            <div id="progress" v-if="getProgressBar == false">
              <div id="progress-bar"></div>
            </div>
          </div>
          <div v-else>
            <div id="progress" v-if="getProgressBar == true">
              <div id="progress-bar"></div>
            </div>
          </div>
        </div>
      </div> -->
      <div id="progress" v-if="getProgressBar == false">
        <div id="progress-bar"></div>
      </div>
    </div>
    <div class="next-team-block">
      <div class="next-team-ready" v-if="teamOrder != null && teamOrder.length > 1">
        다음 팀 준비하세요~!
      </div>
      <img
        class="team-img"
        id=""
        :src="require(`../assets/team${teamOrder[1]}.png`)"
        alt=""
        v-if="teamOrder != null && teamOrder.length > 1"
      />
      <div class="next-team-ready" v-else>
        <div id="end">마지막 팀 입니다!</div>
        <img id="ssazip-group" src="~@/assets/ssazip-group.png" alt="" />
      </div>
    </div>
    <div class="ssafymind-score">
      <div class="ssafymind-score-line">
        <!-- <img class="ssafymind-img" id="" src="~@/assets/onetop.png" alt="" />
				<img class="ssafymind-img" id="" src="~@/assets/twotop.png" alt="" />
				<img class="ssafymind-img" id="" src="~@/assets/threetop.png" alt="" /> -->
        <div class="winner-block">
          <span class="winner winner-one">1등</span>
          <span class="winner-team">1팀</span>
          <span class="winner-score winner-one">30점</span>
        </div>
        <div class="winner-block">
          <span class="winner winner-two">2등</span>
          <span class="winner-team">2팀</span>
          <span class="winner-score winner-two">3500점</span>
        </div>
        <div class="winner-block">
          <span class="winner winner-three">3등</span>
          <span class="winner-team">3팀</span>
          <span class="winner-score winner-three">350점</span>
        </div>
        <div class="winner-block">
          <span class="winner">4등</span>
          <span class="winner-team">4팀</span>
          <span class="winner-score">350점</span>
        </div>
        <div class="winner-block">
          <span class="winner">5등</span>
          <span class="winner-team">5팀</span>
          <span class="winner-score">350점</span>
        </div>
        <div class="winner-block">
          <span class="winner">6등</span>
          <span class="winner-team">6팀</span>
          <span class="winner-score">350점</span>
        </div>
        <div class="winner-block">
          <span class="winner">7등</span>
          <span class="winner-team">7팀</span>
          <span class="winner-score">350점</span>
        </div>
        <div class="winner-block">
          <span class="winner">8등</span>
          <span class="winner-team">8팀</span>
          <span class="winner-score">350점</span>
        </div>
        <div class="winner-block">
          <span class="winner">9등</span>
          <span class="winner-team">9팀</span>
          <span class="winner-score">350점</span>
        </div>
        <div class="winner-block">
          <span class="winner">10등</span>
          <span class="winner-team">10팀</span>
          <span class="winner-score">3050점</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '@/components/css/game-status.css';
// import ProgressBar from '@/components/ssafymind/ProgressBar.vue';
import SpeakingTimer from '@/components/common/SpeakingTimer.vue';
import "@/components/css/ssafymind/progressbar.css";

export default {
  name: 'GameStatus',
  data: function() {
    return {
      getProgressBar: false,
    };
  },
  components: {
    // ProgressBar,
    SpeakingTimer,
  },
  props: ['game', 'teamOrder', 'teams', 'curPlayer'],
};
</script>

<style></style>
