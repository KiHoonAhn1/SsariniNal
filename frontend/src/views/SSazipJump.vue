<template>
  <div>
		<Header v-bind:gameTitle="'싸집이 점프 게임'"/>
	  <SSazipjumpModal/>
    <div style="display:flex; justify-content:center">
      <!-- 좌측 게임 -->
      <div class="ssazip-game-outer-container">
        <!-- 윗팀 -->
        <div class="ssazip-game-container">
          <div id="ssazipbg" class="ssazip-game-background">
            <canvas id="canvas"></canvas>
            <div style="display:none;">
              <img id="ssazip" class="t" src="@/assets/ssazip-blue.png">
            </div>
            <div style="display:none;">
              <img id="ssazipMe" class="t" src="@/assets/ssazipMe.png">
            </div>
            <div style="display:none;">
              <img id="obstacle1" src="@/assets/obstacle1.png">
            </div>
            <div style="display:none;">
              <img id="obstacle2" src="@/assets/logo.png">
            </div>
            <div style="display:none;">
              <img id="obstacle3" src="@/assets/eraser.png">
            </div>
             <div style="display:none;">
              <img id="obstacle4" src="@/assets/team1.png">
            </div>
          </div>
          <!-- 아랫팀 -->
          <div id="ssazipbg2" class="ssazip-game-background2">
            <canvas id="canvas2"></canvas>
            <div style="display:none;">
              <img id="ssazip2" class="t" src="@/assets/ssazip-blue.png">
            </div>
            <div style="display:none;">
              <img id="ssazipMe2" class="t" src="@/assets/ssazipMe.png">
            </div>
            <div style="display:none;">
              <img id="obstacle12" src="@/assets/obstacle1.png">
            </div>
            <div style="display:none;">
              <img id="obstacle22" src="@/assets/logo.png">
            </div>
            <div style="display:none;">
              <img id="obstacle32" src="@/assets/eraser.png">
            </div>
             <div style="display:none;">
              <img id="obstacle42" src="@/assets/team1.png">
            </div>
          </div>
          
        </div>
      </div>
      
      <!-- 우측 라운드 표시 -->
      <div class="ssazip-round-outer-container">
        <div class="ssazip-round-container">
          <div class="ssazip-round-inner-container">
            <div class="ssazip-round-item" style="background-color:#9EACDD; margin-top:1.2vh;">
              <div class="ssazip-round-item-inner">
                <div class="ssazip-round-item-title">현재 게임</div>
              </div>
            </div>
            <div class="ssazip-round-item" style="background-color:#EAC16F; margin-top:2.5vh;">
              <div class="ssazip-round-item-inner">
                <div class="ssazip-round-item-title">다음 게임</div>
              </div>
            </div>

            <div class="ssazip-round-item" style="background-color:#B9D272;margin-top:2.5vh;">
              <div class="ssazip-round-item-inner">
                <div class="ssazip-round-item-title">3 ROUND</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./ssazipjump.js">

</script>

<style>
</style>