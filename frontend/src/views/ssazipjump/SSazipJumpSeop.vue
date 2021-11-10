<template>
    <div>
        <Header v-bind:gameTitle="'또박또박 말해요'" />

        <div style="display:flex; justify-content:center">
            <!-- 좌측 게임 -->
            <div class="ssazip-game-outer-container">
                <div class="ssazip-game-container">
                    <div id="ssazipbg" class="ssazip-game-background">
                        <canvas id="canvas"></canvas>
                        <div style="display:none;">
                            <img id="ssazip" class="t" src="@/assets/ssazip-blue.png" />
                        </div>
                        <div style="display:none;">
                            <img id="obstacle1" src="@/assets/obstacle1.png" />
                        </div>
                        <div style="display:none;">
                            <img id="obstacle2" src="@/assets/logo.png" />
                        </div>
                        <div style="display:none;">
                            <img id="obstacle3" src="@/assets/eraser.png" />
                        </div>
                        <div style="display:none;">
                            <img id="obstacle4" src="@/assets/team1.png" />
                        </div>
                    </div>
                    <div id="ssazipbg2" class="ssazip-game-background2">
                        <canvas id="canvas2"></canvas>
                        <div style="display:none;">
                            <img id="ssazip2" class="t" src="@/assets/ssazip-blue.png" />
                        </div>
                        <div style="display:none;">
                            <img id="obstacle12" src="@/assets/obstacle1.png" />
                        </div>
                        <div style="display:none;">
                            <img id="obstacle22" src="@/assets/logo.png" />
                        </div>
                        <div style="display:none;">
                            <img id="obstacle32" src="@/assets/eraser.png" />
                        </div>
                        <div style="display:none;">
                            <img id="obstacle42" src="@/assets/team1.png" />
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
                    <!-- <div class="now-playing-game"></div> -->
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Header from '@/components/common/Header.vue';
import '../../css/ssazip-jump.css';
//////////////////
import { socketConnect } from '@/util/socket-common.js';
import { mapGetters } from 'vuex';

//////////end//////////
export default {
    name: 'SSazipJump',
    data: function() {
        return {
            time: 0,
            fTime: 0,
            otime: 0,
            ofTime: 0,
            users1: [
                { userId: '이장섭', jump: 0 },
                { userId: '권희은', jump: 0 },
                // { userId: '김태현', jump: 0 },
                // { userId: '차은채', jump: 0 },
                // { userId: '안기훈', jump: 0 },
                // {userId: '박용미', jump:0},
                // {userId: '이미영', jump:0}
            ],
            users2: [
                { userId: '안기훈', jump: 0 },
                { userId: '꼬륵채', jump: 0 },
                // { userId: '김태현', jump: 0 },
                // { userId: '차은채', jump: 0 },
                // { userId: 'ddd', jump: 0 },
                // {userId: '박용미', jump:0},
                // {userId: '이미영', jump:0}
            ],
            dinos1: [],
            dinos2: [],
            userId: '안기훈',
            userIdx: 1,
            status: true,
            obstacle1: document.getElementById('obstacle1'),
            obstacle2: document.getElementById('obstacle2'),
            obstacle3: document.getElementById('obstacle3'),
            ////js///////////
            stompClient: null,
            // 방 정보
            room: {
                id: '',
                name: '',
                host: '',
                members: [],
            },
            jumpsss1: [],
            jumpsss2: [],
            //임의값
            getRoomId: 123,
            teamIdx: 2,

            //////jsend//////
        };
    },
    components: {
        Header,
    },
    methods: {
        drawSsazipgame() {
            const ssazip = document.getElementById('ssazip');
            const ssazip2 = document.getElementById('ssazip2');

            // canvas는 mounted이후에 사용이 가능하다.
            let canvas = document.getElementById('canvas');
            let ctx = canvas.getContext('2d');
            let canvas2 = document.getElementById('canvas2');
            let ctx2 = canvas2.getContext('2d');

            // 캔버스 크기 지정
            canvas.width = 1200;
            canvas.height = 350;
            canvas2.width = 1200;
            canvas2.height = 350;

            const leftSpace = 50; //싸집이들 왼쪽 공간

            this.users1.forEach((user, i) => {
                // 캐릭터 속성
                let dino = {
                    // 캐릭터 등장 좌표(왼쪽 상단으로부터)
                    x: leftSpace + 70 * i,
                    y: 220,
                    // 캐릭터 크기
                    width: 50,
                    height: 50,
                    idx: i,
                    // 캐릭터 그리기 함수(생성)
                    draw() {
                        ctx.fillStyle = 'green';
                        ctx.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
                        ctx.drawImage(ssazip, this.x, this.y, this.width, this.height);
                    },
                };
                this.dinos1.push(dino);
                if (user.userId == this.userId) {
                    this.userIdx = i;
                }
            });

            this.users2.forEach((user, i) => {
                // 캐릭터 속성
                let dino = {
                    // 캐릭터 등장 좌표(왼쪽 상단으로부터)
                    x: leftSpace + 70 * i,
                    y: 220,
                    // 캐릭터 크기
                    width: 50,
                    height: 50,
                    idx: i,
                    // 캐릭터 그리기 함수(생성)
                    draw() {
                        ctx2.fillStyle = 'green';
                        ctx2.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
                        ctx2.drawImage(ssazip2, this.x, this.y, this.width, this.height);
                        // console.log(ssazip)
                    },
                };
                this.dinos2.push(dino);
                if (user.userId == this.userId) {
                    this.userIdx = i;
                }
                // this.dinos1[i].draw();
            });

            let animation;
            let cactusarr = [];
            let cactusarr2 = [];
            const obstacle1 = document.getElementById('obstacle1'); // eslint-disable-line no-unused-vars
            const obstacle2 = document.getElementById('obstacle2'); // eslint-disable-line no-unused-vars
            const obstacle3 = document.getElementById('obstacle3'); // eslint-disable-line no-unused-vars
            const obstacle4 = document.getElementById('obstacle4'); // eslint-disable-line no-unused-vars

            // 장애물 속성
            class Cactus {
                // 장애물 생성기
                constructor() {
                    // 장애물 등장 위치(왼쪽 상단으로부터)
                    (this.x = 1200), (this.y = 220), (this.width = 50), (this.height = 50), (this.type = Math.floor(Math.random() * (4 - 0 + 1)) + 0);
                }
                draw() {
                    if (this.type != 0) {
                        ctx.fillStyle = 'red';
                        ctx.fillRect(this.x, this.y, this.width, this.height);
                        ctx.drawImage(eval('obstacle' + this.type), this.x, this.y, this.width, this.height);
                    } else {
                        (this.x = 0), (this.y = 0);
                    }
                }
            }

            // 장애물 속성
            class Cactus2 {
                // 장애물 생성기
                constructor() {
                    // 장애물 등장 위치(왼쪽 상단으로부터)
                    (this.x = 1200), (this.y = 220), (this.width = 50), (this.height = 50), (this.type = Math.floor(Math.random() * (4 - 0 + 1)) + 0);
                }
                draw() {
                    if (this.type != 0) {
                        ctx2.fillStyle = 'red';
                        ctx2.fillRect(this.x, this.y, this.width, this.height);
                        ctx2.drawImage(eval('obstacle' + this.type), this.x, this.y, this.width, this.height);
                    } else {
                        (this.x = 0), (this.y = 0);
                    }
                }
            }

            // 1초에 60번 실행할 함수
            const frame = (timestamp) => {
                // requestAnimationFrame(frame) //frame을 1초에 60번 실행해줘
                // requestAnimationFrame(frame); //frame을 1초에 60번 실행해줘

                animation = requestAnimationFrame(frame); //frame을 1초에 60번 실행해줘
                ctx.clearRect(0, 0, canvas.width, canvas.height); //canvas 지워줘(잔상 안 남게)
                ctx2.clearRect(0, 0, canvas2.width, canvas2.height); //canvas2 지워줘(잔상 안 남게)

                // 시간 세기 시작

                const cntTime = parseInt(timestamp / 1000); //1초로 나눔
                const fTime = parseInt(timestamp / 16.7); // 1/60초마다 추가

                if (cntTime > this.time) {
                    //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
                    this.time++;
                }
                if (fTime > this.fTime) {
                    //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
                    this.fTime++;
                }
                // 장애물 생성시간 조정 (2초로 설정)
                if (this.time % 2 == 0) {
                    this.time++;
                    var cactus = new Cactus();
                    var cactus2 = new Cactus2();
                    cactusarr.push(cactus); //2초마다 생성되면 장애물 리스트에 넣기
                    cactusarr2.push(cactus2); //2초마다 생성되면 장애물 리스트에 넣기
                }
                // 장애물 이동을 1/60초마다 이루어지게 조정
                if (fTime > this.fTime) {
                    cactusarr.forEach((a, i, o) => {
                        //장애물 리스트에서 하나씩 빼서 그려주기
                        //이미 지나간 장애물은 삭제
                        if (i + 1 < o.length) {
                            if (o[i + 1].x < -50) {
                                o.splice(i, 1);
                            }
                        }
                        //장애물 점점 왼쪽으로 가게 만들기
                        a.x -= 4; //장애물 속도
                        // checkCollision(dino, a); //모든 장애물과 충돌확인

                        this.dinos1.forEach((dino) => {
                            checkCollision(dino, a); //모든 장애물과 충돌확인
                        })
                        ,
                            a.draw();
                    });
                    cactusarr2.forEach((a, i, o) => {
                        //장애물 리스트에서 하나씩 빼서 그려주기
                        //이미 지나간 장애물은 삭제
                        if (i + 1 < o.length) {
                            if (o[i + 1].x < -50) {
                                o.splice(i, 1);
                            }
                        }
                        //장애물 점점 왼쪽으로 가게 만들기
                        a.x -= 4; //장애물 속도
                        // checkCollision(dino, a); //모든 장애물과 충돌확인

                        this.dinos2.forEach((dino) => {
                            checkCollision(dino, a); //모든 장애물과 충돌확인
                        })
                        ,
                            a.draw();
                    });
                    // 점프
                    this.dinos1.forEach((dino, i) => {
                        // 점프
                        if (this.users1[i].jump == true) {
                            this.dinos1[i].y -= 4; //점프속도
                        }
                        // 착지
                        if (this.users1[i].jump == false) {
                            if (dino.y < 220) {
                                this.dinos1[i].y += 4; //착지속도
                            }
                        }
                        // 점프 중지
                        if (this.dinos1[i].y <= 90) {
                            this.users1[i].jump = false;
                            this.jumpsss1[i] = false;
                        }
                        // }
                        this.dinos1[i].draw();
                        // console.log(this.dinos[i])
                    });

                    this.dinos2.forEach((dino, i) => {
                        // 점프
                        if (this.users2[i].jump == true) {
                            this.dinos2[i].y -= 4; //점프속도
                        }
                        // 착지
                        if (this.users2[i].jump == false) {
                            if (dino.y < 220) {
                                this.dinos2[i].y += 4; //착지속도
                            }
                        }
                        // 점프 중지
                        if (this.dinos2[i].y <= 90) {
                            this.users2[i].jump = false;
                            this.jumpsss2[i] = false;
                        }

                        this.dinos2[i].draw();
                    });
                }
            };
            frame();
            // 충돌체크함수
            const checkCollision = (dino, cactus) => {
                // console.log(dino)
                let xDiff = cactus.x - (dino.x + dino.width);
                let yDiff = cactus.y - (dino.y + dino.height);
                const ssazipbg = document.getElementById('ssazipbg');
                const ssazipbg2 = document.getElementById('ssazipbg2');
                if (dino.x <= cactus.x + cactus.width) {
                    if (xDiff < -10 && yDiff < -10) {
                        cancelAnimationFrame(animation); //게임중단
                        console.log(xDiff);
                        ssazipbg.style.animation = 'paused';
                        ssazipbg2.style.animation = 'paused';
                        // this.status = false
                    }
                }
            };
        },
        //////////js/////////////
        /**
         * Socket 요청들
         */
        // 게임 방 입장 : 정보 구독 및 유저 정보 전송
        onConnected() {
            // 유저 정보 교환
            this.stompClient.subscribe('/game/jumpgame/' + this.getRoomId, this.onMessageReceived);
            this.stompClient.send(
                '/pub/game/jumpsend',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    jumpArr1: [],
                    jumpArr2: [],
                    // participantId: this.getUser.id,
                    // participantName: this.getUser.name,
                    // teamNo: 0,
                })
            );
            console.log('onconnected');
        },
        // 메세지 수신
        onMessageReceived(payload) {
            let info = JSON.parse(payload.body);
            // console.log(info);
            // if (payload.body.jumpArr1 == null) {
            //     console.log('=========got nothing');
            //     return;
            // }
            console.log('======got mes');

            // 방장이 퇴장한 경우
            this.jumpsss1 = info.jumpArr1;
            this.jumpsss2 = info.jumpArr2;
            console.log('========i got arr');
            console.log(this.jumpsss1);
            console.log(this.jumpsss2);
            for (let i = 0; i < this.jumpsss1.length; i++) {
                this.users1[i].jump = this.jumpsss1[i];
            }
            for (let i = 0; i < this.jumpsss2.length; i++) {
                this.users2[i].jump = this.jumpsss2[i];
            }
            console.log('users');
            console.log(this.users1);
            console.log(this.users2);
            console.log('users');

            if (payload.body == 'exit') {
                // 모든 참가자의 연결을 끊고
                this.onDisconnect();
                alert('방장이 퇴장하여 게임이 종료됩니다!');
                // 모든 참가자 내보내기
                this.$router.push('/room');
                return;
            }

            // let room = JSON.parse(payload.body);
            // console.log(room);
            // this.room.name = room.name;
            // this.room.host = room.host;
            // this.room.members = room.members;
            // if (room.openTeams != null) this.teamline = JSON.parse(room.openTeams);
            // console.log(this.room);
            // 분류하는 함수
            // this.assignTeam();
        },
        onTeamReceived(payload) {
            let teams = JSON.parse(payload.body);
            console.log(teams);
            this.teamline = teams;
        },
        onError() {},
        // 게임 방 퇴장 소켓 연결 해제 및 게임 방 유저 정보 삭제
        onDisconnect() {
            this.stompClient.send(
                '/pub/game/jump/exit',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    participantId: this.getUser.id,
                    participantName: this.getUser.name,
                })
            );
            this.stompClient.disconnect();
        },
        // 팀 번호 변경시 소켓 요청
        // changeTeamMessage() {
        //     this.stompClient.send('/pub/game/change', {}, JSON.stringify(this.room));
        // },
        // // 방장이 팀 창을 열어주면 소켓 요청
        // openTeamMessage() {
        //     this.stompClient.send(`/pub/game/openTeam/${this.getRoomId}`, {}, JSON.stringify(this.teamline));
        // },
        jumping() {
            console.log('=======jummping');
            console.log(this.jumpsss1);
            console.log(this.jumpsss2);
            this.stompClient.send(
                '/pub/game/jumpsend',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    jumpArr1: this.jumpsss1,
                    jumpArr2: this.jumpsss2,
                })
            );
            // this.jumpsss1 = [];
            // this.jumpsss2 = [];
        },
        //////////js end/////////////
    },
    mounted() {
        this.userId = this.getUser.name;
        console.log('==========start i am ' + this.getUser.name);
        for (let i = 0; i < this.users1.length; i++) {
            if (this.users1[i].userId == this.getUser.name) {
                this.teamIdx = 1;
                break;
            }
        }
        for (let i = 0; i < this.users1.length; i++) {
            this.jumpsss1.push(false);
            this.users1[i].jump = false;
        }
        for (let i = 0; i < this.users1.length; i++) {
            this.jumpsss2.push(false);
            this.users2[i].jump = false;
        }
        console.log(this.jumpsss1.length + ' ' + this.jumpsss2.length);
        this.drawSsazipgame();
        //스페이스바 누르면 점프값 변경
        document.addEventListener('keydown', (e) => {
            //착지해야만 점프가능

            if (this.teamIdx == 1) {
                if (this.dinos1[this.userIdx].y == 220) {
                    if (e.code === 'Space') {
                        console.log('space pushed team1');
                        console.log(this.getUser.name);
                        // this.users1[this.userIdx].jump = true;
                        /////////////////////////////////////
                        this.jumpsss1[this.userIdx] = true;
                        // console.log("jump idx== "+this.userIdx);
                        // const newarr= this.room.members;
                        // const newarr = Array;
                        // for (var i = 0; i < this.users1.length; i++) {
                        //     this.jumpsss1.push(this.users1[i].jump);
                        // }
                        // this.room.members = newarr;
                        // console.log(this.users[this.userIdx].jump);
                        // console.log(this.jumpsss1);
                        // console.log(this.getRoomId);
                        // console.log('1');
                        this.jumping();
                        // console.log(this.room);
                        // console.log(this.members);
                        ///////////////////end//////////
                    }
                }
            } else {
                if (this.dinos2[this.userIdx].y == 220) {
                    if (e.code === 'Space') {
                        console.log('space pushed team2');
                        console.log(this.getUser.name);
                        // this.users2[this.userIdx].jump = true;
                        //////////////////////////////////////
                        this.jumpsss2[this.userIdx] = true;

                        // console.log("jump idx== "+this.userIdx);
                        // const newarr= this.room.members;
                        // const newarr = Array;
                        // console.log(this.jumpsss2);
                        // for (var j = 0; j < this.users2.length; j++) {
                        //     this.jumpsss2.push(this.users2[j].jump);
                        // }
                        // this.room.members = newarr;
                        // console.log(this.users[this.userIdx].jump);

                        // console.log(this.getRoomId);
                        // console.log('1');
                        this.jumping();
                        // console.log(this.room);
                        // console.log(this.members);
                        ///////////////////end//////////
                    }
                }
            }
        });

        // document.addEventListener('keydown', (e) => {
        //     //착지해야만 점프가능
        //     if (this.dinos2[4].y == 220) {
        //         if (e.code === 'Enter') {
        //             this.users2[4].jump = true;
        //         }
        //     }
        // });
    },
    ///////////////////////////////////
    created() {
        // this.checkRoom();
        // this.checkName();
        // 소켓 연결
        this.stompClient = socketConnect(this.onConnected, this.onError);
        // 방정보 초기화
        this.room.id = this.getRoomId;
        // this.room.id = 123;
    },
    computed: {
        // ...mapGetters(['getRoomId', 'getUser']),
        ...mapGetters(['getUser']),

        // ...mapState(['visitedRoomId']),
        // ...mapActions(['joinRoom']),
    },
    /////////////////////end/////////////
};
</script>

<style></style>
