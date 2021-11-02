<template>
	<div class="ssafymind">
		<div class="room-title">
      <span id="game-title">싸피마인드</span>
    </div>
		<canvas id="jsCanvas" class="canvas" v-on:mousemove="onMouseMove" v-on:mousedown="startPainting" v-on:mouseup="stopPainting" v-on:mouseleave="stopPainting"></canvas>
		<div class="controls">
			<div class="controls_colors" id="jsColors">
				<div class="controls_color" style="background-color:#E00F0F"></div>
				<div class="controls_color" style="background-color:#EDCD11"></div>
				<div class="controls_color" style="background-color:#058B05"></div>
				<div class="controls_color" style="background-color:#2377F4"></div>
				<div class="controls_color" style="background-color:#7118CA"></div>
				<div class="controls_color" style="background-color:#000000"></div>
				<div class="controls_color" style="background-color:#FFFFFF"></div>
			<div class="controls_range">
				<input type="range" id="jsRange" min="0.1" max="5.0" value="2.5" step="0.1">
			</div>
			<div class="controls_btns">
				<button id="jsMode">채우기</button>
				<button id="jsSave">저장</button>
			</div>
			</div>
		</div>
	</div>
</template>
<script>
import "@/css/ssafymind/ssafymind.css";

export default {
  name: 'SsafyMind',
	data:function(){
		return{
			painting: false,
			// canvas: document.getElementById("jsCanvas"),
			// ctx: canvas.getContext("2d"), // 픽셀로 접근할 수 있는 방법
		}
	},
	methods: {
		stopPainting: function(){
			this.painting = false;                                                                                           
		},
		startPainting: function(){
			this.painting = true;
		},
		onMouseMove: function(event){
			const canvas = document.getElementById("jsCanvas");
			const ctx = canvas.getContext("2d"); // 픽셀로 접근할 수 있는 방법
			const x = event.offsetX;
			const y = event.offsetY;
			// console.log(x, y)
			// console.log(ctx)

			if(!this.painting){
				ctx.beginPath(); // 새로운 경로를 만든다. 경로가 생성되었다면, 이후 그리기 명령들은 경로를 구성하고 만드는 데에 사용된다.
				ctx.moveTo(x, y); // 해당 좌표로 펜을 이동하는 메소드
				// console.log(ctx.beginPath)
			}else{
				ctx.lineTo(x, y); // 현재 위치에서 해당 좌표까지 선 그리기
				ctx.stroke(); // 윤곽선을 이용해 선 그리기
				// console.log(ctx.stroke)
			}
			
		},
		onMouseDown(){
			this.painting = true;
		},
		setCanvas: function(){
			const canvas = document.getElementById("jsCanvas");
			const ctx = canvas.getContext("2d"); // 픽셀로 접근할 수 있는 방법
			canvas.width = 1000;
			canvas.height = 760;

			ctx.strokeStyle = "black";
			ctx.lineWidth = 2.5;
		}
	},
	mounted: function(){
		this.setCanvas();
	},
	created: function(){
	}
}
</script>
<style>
    
</style>