<template>
  <div class="game-explain-modal">
    <div class="game-explain-dialog">
      <div class="game-explain-content">
        <div class="modal-body">
          <div class="game-explain-explainbox">
            <img src="~@/assets/ssafymindBeforeStart.png" id="ssafymind-explain-img" alt="">
						<button class="start-game-btn" @click="startModal">
              시작
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import '@/components/css/gameExplainModal.css';
export default {
  name: 'GameExplainModal',
  data: function() {
    return {
      
    };
  },
  methods: {
    closeModal() {
      console.log('일단 확인')
    },
  },
	mounted: function(){
		this.closeModal();
	}
};
</script>
<style>
body {
  margin: 0;
}
</style>
