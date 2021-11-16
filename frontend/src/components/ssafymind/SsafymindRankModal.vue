<template>
	<div class="ssafymind-rank-modal">
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
					
						</div>
					</div>
					<button class="ssazip-rank-game-btn" @click="closeModal">대기실로</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import '@/components/css/ssafymind/ssafymind-rank-modal.css'
import { mapGetters } from 'vuex';
export default {
	name: 'SsafymindRankModal',
  props: ['host'],
	data: function(){
    return{
			rankmodal : false,
    }
  },
  computed: {
    ...mapGetters(['getUser']),
  },
	methods: {
		closeModal(){
			this.$emit("getCloseRankModal", this.rankmodal);
      // this.$emit('sendGoWaitingRoomTrigger');
		}
	},
}
</script>
<style>
body{
  margin: 0;
}
</style>