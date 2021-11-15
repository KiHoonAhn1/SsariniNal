<template>
	<div class="ssazip-start-modal">
		<div class="show-team-dialog">
			<div class="show-rank-modal">
				<div class="modal-body">
					<div class="congratulation">축하합니다!</div>
					<div class="show-rank-explainbox">
						<div class="round-team-container">
							<div>
								<img class="rank-team-individual" src="~@/assets/onetop.png" alt="" />
								<span class="rank-team-name">2팀</span>
								<span class="rank-team-score">350점</span>
							</div>
							<div>
								<img class="rank-team-individual2" src="~@/assets/twotop.png" alt="" />
								<span class="rank-team-name">3팀</span>
								<span class="rank-team-score">350점</span>

							</div>
							<div>
								<img class="rank-team-individual2" src="~@/assets/threetop.png" alt="" />
								<span class="rank-team-name">1팀</span>
								<span class="rank-team-score">350점</span>
							</div>
							<div>
								<img class="rank-team-individual2" src="~@/assets/threetop.png" alt="" />
								<span class="rank-team-name">1팀</span>
								<span class="rank-team-score">350점</span>
							</div>
					
						</div>
					</div>
					<button class="ssazip-rank-game-btn" @click="startModal">대기실로</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import '@/components/css/ssazipjump/ssazipjump-modal.css'
export default {
	name: 'SSazipjumpRankModal',
	data: function(){
    return{
			open : false,
			startTime : false,
    }
  },
	methods: {
		startModal(){
			this.$emit("getCloseModal", this.open);
			this.$emit("startTimer", this.startTime)
		}
	},
}
</script>
<style>
body{
  margin: 0;
}
</style>