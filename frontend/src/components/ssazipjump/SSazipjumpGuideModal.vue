<template>
	<div class="ssazip-start-modal">
		<div class="show-team-dialog">
			<div class="show-team-content">
				<div class="modal-body">
					<div class="show-team-explainbox">
						<div class="welcome">
							<img style="width:700px; margin-top:-20px;" src="~@/assets/ssazipjump-guide.png" alt="" />
						</div>
						<button class="ssazip-guide-btn" @click="startModal">다음</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<script>
import '@/components/css/ssazipjump/ssazipjump-modal.css'
export default {
	name: 'SSazipjumpGuideModal',
	data: function(){
    return{
			open : false,
			startTime : false,
    }
  },
	methods: {
		startModal(){
			this.$emit("guideModal", this.open);
		}
	},
}
</script>
<style>
body{
  margin: 0;
}
</style>