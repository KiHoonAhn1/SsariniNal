<template>
	<div class="ssafymind" v-on:mousedown="isMouseDown" v-on:mouseup="isMouseUp">
		<!-- <div class="room-title">
      <span id="game-title">싸피마인드</span>
    </div> -->
		<Header v-bind:gameTitle="'싸피마인드'"/>
		<SsafymindLeft />
		<div class="ssafymind-center">
			<canvas id="jsCanvas" class="canvas" v-on:mousemove="onMouseMove" v-on:mousedown="startPainting" v-on:mouseup="stopPainting" v-on:mouseleave="stopPainting" v-on:mouseenter="mouseEnter" v-on:contextmenu="handleCM"></canvas>
			<div class="controls">
				<div class="controls_colors" id="jsColors">
					<div class="controls_color jsColor" style="background-color:#E00F0F"></div>
					<div class="controls_color jsColor" style="background-color:#FFA500"></div>
					<div class="controls_color jsColor" style="background-color:#EDCD11"></div>
					<div class="controls_color jsColor" style="background-color:#B0CF3D"></div>
					<div class="controls_color jsColor" style="background-color:#058B05"></div>
					<div class="controls_color jsColor" style="background-color:#9BC8DA"></div>
					<div class="controls_color jsColor" style="background-color:#2377F4"></div>
					<div class="controls_color jsColor" style="background-color:#081765"></div>
					<div class="controls_color jsColor" style="background-color:#EDA3ED"></div>
					<div class="controls_color jsColor" style="background-color:#7118CA"></div>
					<div class="controls_color jsColor" style="background-color:#000000"></div>
				<div class="controls_range">
					<input type="range" id="jsRange" min="0.1" max="8.0" value="2.5" step="0.1">
				</div>
				<img class="ssafymind-img" id="jsMode-paint" src="~@/assets/paint-bucket.png" alt="" />
				<img class="ssafymind-img" id="jsMode-palette" src="~@/assets/paint-palette.png" alt="" />
				<img class="ssafymind-img" id="jsErase" @click="handleCanvasErase()" src="~@/assets/eraser.png" alt="" />
				<img class="ssafymind-img" id="" @click="handleSave()" src="~@/assets/floppy-disk.png" alt="" />
				</div>
			</div>
		</div>
		<SsafymindRight />
	</div>
</template>
<script>
import Header from '@/components/common/Header.vue'
import SsafymindLeft from '@/components/ssafymind/SsafymindLeft.vue'
import SsafymindRight from '@/components/ssafymind/SsafymindRight.vue'
import "@/css/ssafymind/ssafymind.css";

export default {
  name: 'SsafyMind',
	components: {
    Header,
		SsafymindLeft,
		SsafymindRight,
  },
	data: function() {
		return {
			painting: false,
			clickmouse: false,
			filling: false,
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
			const ctx = canvas.getContext("2d");
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
		mouseEnter: function(){
			if(this.clickmouse){
				this.painting = true;
			}else{
				this.painting = false;
			}
		},
		isMouseDown: function(){
			this.clickmouse = true;
		},
		isMouseUp: function(){
			this.clickmouse = false;
		},
		handleColorClick: function(event){
			const canvas = document.getElementById("jsCanvas");
			const ctx = canvas.getContext("2d");
			const color = event.target.style.backgroundColor;
			ctx.strokeStyle = color;
			ctx.fillStyle = color;
		},
		handleRangeChange: function(event){
			const canvas = document.getElementById("jsCanvas");
			const ctx = canvas.getContext("2d");
			const size = event.target.value;
			ctx.lineWidth = size;
		},
		handlePaintModeClick: function(){
			if(this.filling === false){
				this.filling = true;
			}
		},
		handlePaletteModeClick: function(){
			if(this.filling === true){
				this.filling = false;
			}
		},
		handleCanvasClick: function(){
			const canvas = document.getElementById("jsCanvas");
			const ctx = canvas.getContext("2d");
			if(this.filling){
				ctx.fillRect(0, 0, canvas.width, canvas.height);
			}
		},
		handleCanvasErase: function(){
			const canvas = document.getElementById("jsCanvas");
			const ctx = canvas.getContext("2d");
			ctx.strokeStyle = "white";
		},
		handleCM: function(event){
			event.preventDefault();
		},
		handleSave:function(){
			const canvas = document.getElementById("jsCanvas");
			const image = canvas.toDataURL();
			const link = document.createElement("a");
			// const imageName = prompt("그림 이름 지어줭");
			link.href = image;
			// link.download = imageName;
			link.download = "내가그린기린그림";
			link.click();
			// console.log(image);
		},
		setCanvas: function(){
			const canvas = document.getElementById("jsCanvas");
			const ctx = canvas.getContext("2d"); // 픽셀로 접근할 수 있는 방법
			const colors = document.getElementsByClassName("jsColor"); // 색깔 변경 시 사용
			const range = document.getElementById("jsRange"); // 선 굵기 변경 시 사용
			const paintmode = document.getElementById("jsMode-paint"); // 색 채우기 시 사용
			const palettemode = document.getElementById("jsMode-palette"); // 펜 바꾸기 시 사용

			const INITIAL_COLOR = "black";
			canvas.width = 1100;
			canvas.height = 760;
			// canvas.width = window.innerWidth;
			// canvas.height = window.innerHeight;
			ctx.fillStyle = "white";
			ctx.fillRect(0, 0, canvas.width, canvas.height);

			ctx.strokeStyle = INITIAL_COLOR;
			ctx.fillStyle = INITIAL_COLOR;
			ctx.lineWidth = 2.5;
			ctx.lineCap = "round";

			// 색깔을 가지고 있는 배열값을 받아오자
			Array.from(colors).forEach(color => color.addEventListener("click", this.handleColorClick));

			if(range){
				range.addEventListener("input", this.handleRangeChange);
			}

			if(paintmode){
				paintmode.addEventListener("click", this.handlePaintModeClick);
			}
			if(palettemode){
				palettemode.addEventListener("click", this.handlePaletteModeClick);
			}

			canvas.addEventListener("click", this.handleCanvasClick);
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