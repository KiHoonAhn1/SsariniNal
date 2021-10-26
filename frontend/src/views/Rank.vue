<template>
  <div>
    <!-- 상단 타이틀 리본 -->
    <div>
      <img class="rank-title" src="../assets/rank-title.png" alt="">
      <div class="rank-title-word">서울 3 반 우승자들 축하합니다</div>
    </div>
    <!-- 종료버튼 -->
    <button class="rank-button-over">종료</button>
    <!-- 하단 메인 컨테이너 -->
    <div class="rank-container">
      <div class="rank-container-background">
          <!-- 1등 -->
          <div class="ssazip-container">
            <img class="gold-place" src="../assets/gold-place2.png" alt="">
            <img class="gold-ssazip" src="../assets/ssazip-gold.png" alt="">
            <div class="gold-team">2팀</div>
          </div>
          <!-- 2등 -->
          <div class="ssazip-container">
            <img class="silver-place" src="../assets/silver-place2.png" alt="">
            <img class="silver-ssazip" src="../assets/ssazip-silver.png" alt="">
            <div class="silver-team">1팀</div>
          </div>
          <!-- 3등 -->
          <div class="ssazip-container">
            <img class="bronze-place" src="../assets/bronze-place2.png" alt="">
            <img class="bronze-ssazip" src="../assets/ssazip-bronze.png" alt="">
            <div class="bronze-team">3팀</div>
          </div>
          <!-- 우측 나머지팀 결과 -->
          <div class="rank-list" >
            <div style="margin-top:50px">
              <span class="rank-list-item-rank">4등</span>
              <span class="rank-list-item-team">5팀</span>
            </div>
          </div>
      </div>  
    </div>
  </div>
</template>


<script >
import Vue from 'vue'
import VueConfetti from 'vue-confetti'
import '../css/rank.css'

Vue.use(VueConfetti)

export default {

  name: "Rank",
  created(){
    // 꽃가루 효과 바로 실행
    this.$confetti.start({
      particles: [
        {
          type: 'heart',
          colors: [
            'red',
            'pink',
            'Gold'
          ],
        },
        {
          type: 'circle',
          colors: [
            'pink',
            'Gold',
            'lightblue'
          ],
        },
        {
          type: 'rect',
          colors: [
            'lightblue',
            'pink',
            'red',
            'yellow',
          ],
        },
      ],
      defaultDropRate: 5,
      defaultSize: 4,
    });
  },

}
</script>


<style>

</style>