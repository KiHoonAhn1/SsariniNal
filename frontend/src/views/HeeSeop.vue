<template>
    <div>
        <canvas id="canvas"></canvas>
    </div>
</template>

<script>
import { socketConnect } from '@/util/socket-common.js';

export default {
    name: 'HeeSeop',
    data: function() {
        return {
            cactusArr: [],
            showArr: [],
            xArr: [],
            receivedArr: [],
            time: 0,
            fTime: 0,
        };
    },
    methods: {
        start() {
            let canvas = document.getElementById('canvas');
            let ctx = canvas.getContext('2d');
            canvas.width = 1200;
            canvas.height = 350;

            class Cactus {
                // 장애물 생성기
                constructor() {
                    // 장애물 등장 위치(왼쪽 상단으로부터)
                    (this.x = 1200), (this.y = 220), (this.width = 50), (this.height = 50);
                }
                draw() {
                    ctx.fillStyle = 'red';
                    ctx.fillRect(this.x, this.y, this.width, this.height);
                    // ctx.drawImage(eval('obstacle'+this.type), this.x, this.y, this.width, this.height);
                }
            }

            const frame = (timestamp) => {
                requestAnimationFrame(frame); //frame을 1초에 60번 실행해줘
                ctx.clearRect(0, 0, canvas.width, canvas.height); //canvas 지워줘(잔상 안 남게)

                // 시간 세기 시작
                const cntTime = parseInt(timestamp / 1000); //1초로 나눔
                const fTime = parseInt(timestamp / 16.7); // 1/60초마다 추가

                if (fTime > this.fTime) {
                    //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
                    this.fTime++;
                }

                if (cntTime > this.time) {
                    this.time++;
                    var cactus = new Cactus();
                    this.cactusArr.push(cactus); //2초마다 생성되면 장애물 리스트에 넣기
                    this.xArr.push(1200);
                    // console.log(this.xArr);
                }

                // 애니메이션을 1/60초마다 이루어지게 조정
                if (fTime > this.fTime) {
                    // this.cactusArr.forEach((a) => {
                    //     //장애물 리스트에서 하나씩 빼서 그려주기
                    //     a.x -= 4; //장애물 속도
                    // });
                    // console.log(this.xArr);
                    this.xArr.forEach((a, i) => {
                        // console.log(typeof a);
                        this.xArr[i] -= 4;
                    });
                    // this.sending();
                    ///////여기서 cactusArr보내서 showArr에 넣어주기
                    // this.showArr=this.cactusArr;
                    // console.log('----------------');
                    // console.log(this.receivedArr);
                    // this.showArr.forEach((a, i) => {
                    //     //장애물 리스트에서 하나씩 빼서 그려주기
                    //     if(this.receivedArr.length>i+1) {

                    //     a.x = this.receivedArr[i]; //장애물 속도
                    //     a.draw();
                    //     }
                    // });
                    this.receivedArr.forEach((a, i) => {
                        //장애물 리스트에서 하나씩 빼서 그려주기
                        var cactus = new Cactus();
                        cactus.x = this.receivedArr[i]; //장애물 속도
                        cactus.draw();
                    });
                }
            };
            frame();
        },
        onConnected() {
            // 유저 정보 교환
            this.stompClient.subscribe('/game/jumpgame/' + 123123, this.onMessageReceived);
            this.stompClient.send(
                '/pub/game/jump/obstacle',
                {},
                JSON.stringify({
                    flag: false,
                })
            );
            console.log('onconnected');
        },
        // 메세지 수신
        onMessageReceived(payload) {
            // let canvas = document.getElementById('canvas');
            // let ctx = canvas.getContext('2d');
            // class Cactus {
            //     // 장애물 생성기
            //     constructor() {
            //         // 장애물 등장 위치(왼쪽 상단으로부터)
            //         (this.x = 1200), (this.y = 220), (this.width = 50), (this.height = 50);
            //     }
            //     draw() {
            //         ctx.fillStyle = 'red';
            //         ctx.fillRect(this.x, this.y, this.width, this.height);
            //         // ctx.drawImage(eval('obstacle'+this.type), this.x, this.y, this.width, this.height);
            //     }
            // }

            let info = JSON.parse(payload.body);
            console.log('======got mes=========');
            // console.log(info.xbArr);
            this.receivedArr = info.xbArr;
            // var cactus = new Cactus();
            // this.showArr.push(cactus);
            // console.log(this.showArr);
            return;
        },
        sending() {
            console.log('=======sending');
            // console.log("wwwwwwwwwwwwwwwwwwwwwwwww")
            // console.log(this.xArr);
            this.stompClient.send(
                '/pub/game/jump/obstacle',
                {},
                JSON.stringify({
                    xbArr: this.xArr,
                })
            );
        },
        onError() {},
        // 게임 방 퇴장 소켓 연결 해제 및 게임 방 유저 정보 삭제
    },
    mounted() {
        this.start();
    },
    created() {
        // 소켓 연결
        this.stompClient = socketConnect(this.onConnected, this.onError);
    },
};
</script>

<style></style>
