<template>
  <div class="game-answer-modal">
    <div class="show-team-dialog">
      <div class="show-team-content">
        <div class="modal-body">
					<div class="show-team-explainbox" v-if="this.correctTeam == 12 || this.correctTeam == 0">
						<div class="show-answer-title">아쉽군요!</div>
							<div class="show-answer">
								친구야 힘내자^^
							</div>
							<span class="team">다음 문제를 맞춰보아요~</span>
            <button class="start-game-btn" @click="startModal" v-if="host == this.getUser.id">
              다음
            </button>
					</div>
          <div class="show-team-explainbox" v-else>
            <div class="show-answer-title">정답</div>
						<div class="show-answer">
							{{ this.quizAnswer }}
						</div>
						<img
							id="team-num-img"
							:src="require(`../assets/team${this.correctTeam}.png`)"
							alt=""
						/>
						<span class="team">팀</span>
						<span class="score">+100점</span>
						<button class="start-game-btn" @click="startModal" v-if="host == this.getUser.id">
							다음{{this.teamOrder.length}}
						</button>
						<!-- <div v-if="teamOrder.length == 1">
							<button class="start-game-btn" @click="goBackModal" v-if="host == this.getUser.id">
								결과
							</button>
						</div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import '@/components/css/gameAnswerModal.css';
import { mapGetters } from 'vuex';
export default {
  name: 'GameAnswerModal',
  props: ['teamOrder', 'teamCnt', 'host', 'quizzes', 'chat'],
  data: function() {
    return {
      answermodal: false,
      startTime: false,
      getProgressBar: false,
			quizAnswer: '',
			correctTeam: 0,
    };
  },
  computed: {
    ...mapGetters(['getUser']),
  },
  methods: {
    startModal() {
      this.$emit('getCloseAnsModal', this.answermodal);
      this.$emit('startTimer', this.startTime);
      this.$emit('sendGameStartTrigger');
      this.$emit('getProgressBar', this.getProgressBar);
    },
		goBackModal(){
			this.$emit('getCloseAnsModal', this.answermodal);
			this.$emit('onMesseageReceived');
		},
		quizzAnswer(){
			console.log('퀴즈가 나오는가')
			console.log(this.teamOrder)
			// console.log(this.chat)
			// console.log(this.quizzes)
			
			for(let quiz of this.quizzes){
				// console.log('퀴즈 답')
				// console.log(quiz.answer)
				for(let ans of this.chat){
					// console.log('채팅 답')
					// console.log(ans.message)
					if(quiz.answer == ans.message){
						this.quizAnswer = quiz.answer;
						this.correctTeam = ans.teamNo;
						// console.log('정답은', this.quizAnswer)
					}else{
						this.correctTeam = 12;
					}
				}
			}
		}
  },
	mounted: function(){
		this.quizzAnswer();
	}
};
</script>
<style>
body {
  margin: 0;
}
</style>
