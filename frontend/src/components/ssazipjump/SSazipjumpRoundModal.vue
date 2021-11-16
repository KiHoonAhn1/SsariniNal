<template>
	<div class="ssazip-start-modal">
		<div class="show-team-dialog">
			<div class="show-team-content">
				<div class="modal-body">
					<div class="show-team-explainbox">
						<div class="welcome">다음 팀은 경기를 준비해주세요.</div>
						<div class="round-team-container">
							<div>
								<img class="round-team-individual" :src="require(`@/assets/team${battleTeam1}.png`)" alt="" />
							</div>
							<div class="vs">VS</div>  
							<img class="round-team-individual" :src="require(`@/assets/team${battleTeam2}.png`)" alt="" />
						</div>
						<button v-if="userId==hostId" class="ssazip-start-game-btn" @click="startModal">시작</button>
						<button v-if="userId!=hostId" class="ssazip-start-game-btn">준비!!</button>
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
	props: ['battleTeam1', 'battleTeam2', 'userId', 'hostId'],
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