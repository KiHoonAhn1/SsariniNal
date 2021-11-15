<template>
	<div class="ssazip-start-modal">
		<div class="show-team-dialog">
			<div class="show-team-content">
				<div class="modal-body">
					<div class="show-team-explainbox">
						<div class="welcome">다음 팀은 경기를 준비해주세요.</div>
						<div class="round-team-container">
							<div>
								<img class="round-team-individual" src="~@/assets/team1.png" alt="" />
							</div>
							<div class="vs">VS</div>  
							<img class="round-team-individual" src="~@/assets/team3.png" alt="" />
						</div>
						<button class="ssazip-start-game-btn" @click="startModal">시작</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import '@/components/css/ssazipjump/ssazipjump-modal.css'
export default {
	name: 'SSazipjumpRoundModal',
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