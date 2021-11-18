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
								<span class="rank-team-name">{{finalScore[0]}}팀</span>
								<span class="rank-team-score">500점</span>
							</div>
							<div>
								<img class="rank-team-individual2" src="~@/assets/twotop.png" alt="" />
								<span class="rank-team-name">{{finalScore[1]}}팀</span>
								<span class="rank-team-score">400점</span>
							</div>
							<div>
								<img class="rank-team-individual2" src="~@/assets/threetop.png" alt="" />
								<span v-if="finalScore[2]" class="rank-team-name">{{finalScore[2]}}팀</span>
								<span v-else class="rank-team-name">- 팀</span>
								<span v-if="finalScore[2]" class="rank-team-score">300점</span>
								<span v-else class="rank-team-score"> --- 점</span>
							</div>
							<div>
								<img class="rank-team-individual2" src="~@/assets/threetop.png" alt="" />
								<span v-if="finalScore[3]" class="rank-team-name">{{finalScore[3]}}팀</span>
								<span v-else class="rank-team-name">- 팀</span>
								<span v-if="finalScore[3]" class="rank-team-score">300점</span>
								<span v-else class="rank-team-score"> --- 점</span>
							</div>
					
						</div>
					</div>
					<button v-if="userId==hostId" class="ssazip-rank-game-btn" @click="startModal">대기실로</button>
					<!-- <button v-if="userId!=hostId" class="ssazip-rank-game-btn">곧 대기실로 이동</button> -->
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import '@/components/css/ssazipjump/ssazipjump-modal.css'
export default {
	name: 'SSazipjumpRankModal',
	props: ['userId', 'hostId', 'finalScore'],

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