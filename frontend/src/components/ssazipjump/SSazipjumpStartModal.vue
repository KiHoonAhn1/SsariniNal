<template>
	<div class="ssazip-start-modal">
		<div class="show-team-dialog">
			<div class="show-team-content">
				<div class="modal-body">
					<div class="show-team-explainbox">
						<div class="welcome">게임 조작법은 다음과 같습니다!</div>
						<span class="guide">스페이스바를 눌러 장애물을 피하세요.</span>
						<div>
							<img class="ssazip-guide box bounce-7" src="~@/assets/ssazipguide.png" alt="" />
							<img class="ssazip-arrow" src="~@/assets/ssaziparrow.png" alt="" />
							<img class="ssazip-spacebar" src="~@/assets/spacebar.png" alt="" />
							<img class="ssazip-spacebar2 blinking" src="~@/assets/spacebar.png" alt="" />
						</div>
						<button v-if="userId==hostId" class="start-game-btn" @click="startModal">시작</button>
						<button v-if="userId!=hostId" class="start-game-btn">준비!!</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<script>
import '@/components/css/ssazipjump/ssazipjump-modal.css'
export default {
	name: 'SSazipjumpStartModal',
	props: ['userId', 'hostId'],
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