<template>
  <div>
    <div>{{ this.curScore }}</div>
    <!-- 상단 타이틀 리본 -->
    <div>
      <img class="rank-title" src="../assets/rank-title.png" alt="" />
      <div class="rank-title-word">서울 3 반 우승자들 축하합니다</div>
    </div>
    <!-- 종료버튼 -->
    <button class="rank-button-over" @click="goHome">종료</button>
    <!-- 하단 메인 컨테이너 -->
    <div class="rank-container">
      <div class="rank-container-background">
        <!-- 1등 -->
        <div class="ssazip-container" v-if="curScore.length >= 1">
          <img class="gold-place" src="../assets/gold-place2.png" alt="" />
          <img class="gold-ssazip" src="../assets/gold-ssazip.png" alt="" />
          <!-- <img class="gold-crown" src="../assets/gold-crown.png" alt=""> -->
          <div class="gold-team">{{ curScore[0][0] }}팀</div>
        </div>
        <!-- 2등 -->
        <div class="ssazip-container" v-if="curScore.length >= 2">
          <img class="silver-place" src="../assets/silver-place2.png" alt="" />
          <img class="silver-ssazip" src="../assets/silver-ssazip.png" alt="" />
          <div class="silver-team">{{ curScore[1][0] }}팀</div>
        </div>
        <!-- 3등 -->
        <div v-if="curScore.length >= 3 && parseInt(curScore[2][0])" class="ssazip-container">
          <img class="bronze-place" src="../assets/bronze-place2.png" alt="" />
          <img class="bronze-ssazip" src="../assets/bronze-ssazip.png" alt="" />
          <div class="bronze-team">{{ curScore[2][0] }}팀</div>
        </div>
        <!-- 우측 나머지팀 결과 -->
        <div class="rank-list" v-if="curScore.length >= 4">
          <div style="margin-top:50px">
            <div v-for="(score, idx) in curScore" :key="idx">
              <div v-if="idx >= 3">
                <span class="rank-list-item-rank">{{ idx + 1 }}등</span>
                <span class="rank-list-item-team">{{ score[0] }}팀</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue';
import VueConfetti from 'vue-confetti';
import '../css/rank.css';
import { mapGetters } from 'vuex';
import { homeURL, roomURL } from '@/util/socket-common.js';

Vue.use(VueConfetti);

export default {
  name: 'Rank',
  data() {
    return {
      curScore: [],
      first: [],
      second: [],
      third: [],
    };
  },
  computed: {
    ...mapGetters(['getIsLogin']),
  },
  created() {
    // 꽃가루 효과 바로 실행
    this.$confetti.start({
      particles: [
        {
          type: 'heart',
          colors: ['red', 'pink', 'Gold'],
        },
        {
          type: 'circle',
          colors: ['pink', 'Gold', 'lightblue'],
        },
        {
          type: 'rect',
          colors: ['lightblue', 'pink', 'red', 'yellow'],
        },
      ],
      defaultDropRate: 5,
      defaultSize: 4,
    });
    this.scores();
  },
  methods: {
    // scores가 갱신되면 점수 정보도 갱신
    scores() {
      this.curScore = [];
      for (let i = 1; i < this.$route.query.rank.length; i++) {
        // 팀 순서에 없으면 스킵
        let flag = JSON.parse(this.$route.query.curTeam[i]);
        if (flag) {
          // i팀이 몇점인지 배열화 시켜 저장
          this.curScore.push([i, this.$route.query.rank[i]]);
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
      this.curScore.forEach((e) => {
        console.log(e);
      });
    },
    // 첫 화면으로 이동
    goHome() {
      if (this.getIsLogin) {
        window.location.href = roomURL;
      } else {
        window.location.href = homeURL;
      }
    },
  },
  beforeRouteLeave() {
    this.$confetti.stop();
  },
};
</script>

<style></style>
