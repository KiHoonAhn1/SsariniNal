<template>
  <div class="ssafymind-left">
		<div id="questionTeam-title">문제 내는 팀</div>
		<img class="team-img" id="" src="~@/assets/team8.png" alt="" />
		<div class="team-members-group">
			<span class="team-members">권희은</span>
			<span class="team-members">차은채</span>
			<span class="team-members">안기훈</span>
			<span class="team-members">김태현</span>
			<span class="team-members myturn">이장섭</span>
		</div>
		<ProgressBar />
		<div class="next-team-block">
			<div class="next-team-ready">
				다음 팀 준비하세요~!
			</div>
			<img class="team-img" id="" src="~@/assets/team2.png" alt="" />
		</div>
		<div class="ssafymind-score">
			<div class="ssafymind-score-line">
				<!-- <img class="ssafymind-img" id="" src="~@/assets/onetop.png" alt="" />
				<img class="ssafymind-img" id="" src="~@/assets/twotop.png" alt="" />
				<img class="ssafymind-img" id="" src="~@/assets/threetop.png" alt="" /> -->
				<div class="winner-block">
					<span class="winner winner-onen">1등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score winner-one">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner winner-two">2등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score winner-two">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner winner-three">3등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score winner-three">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner">4등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner">5등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner">6등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner">7등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner">8등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner">9등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score">350점</span>
				</div>
				<div class="winner-block">
					<span class="winner">10등</span>
					<span class="winner-team">2팀</span>
					<span class="winner-score">350점</span>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import "@/css/ssafymind/ssafymind-left.css";
import ProgressBar from '@/components/ssafymind/ProgressBar.vue'
export default {
  name: 'SsafymindLeft',
	components: {
    ProgressBar
  },
}
</script>

<style>

</style>