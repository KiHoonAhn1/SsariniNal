<template>
  <div id="app">
    <div class="sound" style="display:inline-block">
      <img v-if="!bgm" style="width:50px;" src="@/assets/mute.png" @click="onBgm" alt="">
      <img v-if="bgm" style="width:50px;" src="@/assets/sound.png" @click="onBgm" alt="">
    </div>
    <audio id="bgm" autoplay controls loop class="bgm">
      <source src="@/assets/bgm.mp3">
    </audio>
    <router-view/>
  </div>
</template>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
body{
  background-color: #66C5CF;
}

body {
  margin: 0;
}

.bgm{
  display: none;
}
.sound{
  position: absolute;
  left:20px;
  margin-top:10px;
  width:50px;
  display: inline;
}
</style>
<script>
import { mapState } from 'vuex';

export default {
  data: function() {
    return {
      path: '',
      bgm: false,
    }
  },
  methods:{
    onBgm(){
      let bgm = document.getElementById('bgm')
      if (this.bgm == false) {
        bgm.play()
        this.bgm = true
        console.log(this.bgm)
      } else {
        bgm.pause()
        this.bgm = false
      }
    }
  },
  created() {
    this.path = this.$route.path
    window.addEventListener('beforeunload', (event) => {
      event.preventDefault();
      event.returnValue = '';
    })
    // const myAudio = new Audio(); 
    // myAudio.src = "@/assets/bgm.mp3"; 
    // myAudio.play(); // 음원 재생 myAudio.pause(); // 일시 정지


  },
  mounted() {
  },
  computed: {
    ...mapState(['isLogin', 'user', 'visitedRoomId']),
  },
  // watch: {
  //   path: function() {
  //     // 방장
  //     if(this.isLogin) {
  //       if(this.path === '/' || this.path === '/login' || this.path === '/signup' || this.path === '/room') {
  //         // 참가했던 방이 있는 경우
  //         if (this.visitedRoomId) {
  //           this.$router.push(`/room/${this.visitedRoomId}`);
  //         // 참가했던 방이 없는 경우
  //         } else {
  //           // home, login, signup 페이지에 있으면 '방 생성' 페이지로 이동
  //           this.$router.push('/room');
  //           return
  //         }
  //       }
  //     // 참가자
  //     } else if(!this.isLogin) {
  //       // 방이 있고, userName이 남아있는 경우
  //       // 나갔을 때 방이 사라진 경우 Room.vue의 'checkRoom'에서 지워줌
  //       if (this.user.userName && this.visitedRoomId) {
  //         this.$router.push(`/room/${this.visitedRoomId}`);
  //         return
  //       // 방과 이름이 둘 중 하나라도 없으면 다시 참가 페이지로 보낸다.
  //       } else {
  //         if (this.path !== '/' && this.path !== '/login' && this.path !== '/signup') {
  //           this.$router.push('/');
  //           return
  //         }
  //       }
  //     }
  //   }
  // },
  // beforeUpdate() {
  //   this.path = this.$route.path
  //   console.log(this.path)
  // },
}
</script>
