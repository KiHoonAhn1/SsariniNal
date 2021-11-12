<template>
    <div>
        <Header v-bind:gameTitle="'또박또박 말해요'" />

        <div style="display:flex; justify-content:center">
            <!-- 좌측 게임 -->
            <div class="ssazip-game-outer-container">
                <!-- 윗팀 -->
                <div class="ssazip-game-container">
                    <div id="ssazipbg" class="ssazip-game-background">
                        <canvas id="canvas"></canvas>
                        <div style="display:none;">
                            <img id="ssazip" class="t" src="@/assets/ssazip-blue.png" />
                        </div>
                        <div style="display:none;">
                            <img id="ssazipMe" class="t" src="@/assets/ssazipMe.png" />
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
                    <!-- 아랫팀 -->
                    <div id="ssazipbg2" class="ssazip-game-background2">
                        <canvas id="canvas2"></canvas>
                        <div style="display:none;">
                            <img id="ssazip2" class="t" src="@/assets/ssazip-blue.png" />
                        </div>
                        <div style="display:none;">
                            <img id="ssazipMe2" class="t" src="@/assets/ssazipMe.png" />
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
                //요청으로 받아야 함
                { userId: '이장섭', jump: false },
                { userId: '권희은', jump: false },
                { userId: '김태현', jump: 0 },
                { userId: '차은채', jump: 0 },
                { userId: '술희원', jump: 0 },
                // {userId: '박용미', jump:0},
                // {userId: '이미영', jump:0}
            ],
            userPresent1: [false, false, false, false, false],
            userPresent2: [false, false, false, false, false],
            users2: [
                { userId: '안기훈', jump: false },
                { userId: '꼬륵채', jump: false },
                { userId: '준박', jump: 0 },
                { userId: '이미영', jump: 0 },
                // { userId: 'ddd', jump: 0 },
                { userId: '박용미', jump: 0 },
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
            jumpSendArr1: [false, false, false, false, false],
            jumpSendArr2: [false, false, false, false, false],
            receivedJumpArr1: [false, false, false, false, false],
            receivedJumpArr2: [false, false, false, false, false],
            //임의값
            getRoomId: 123,
            teamIdx: 2,
            reloadFlag: false,
            master: false, //방장여부
            xArr: [], //마스터 장애물 위치
            xArrType: [], //마스터 장애물 타입
            receivedArr: [], //수신된 장애물 위치
            receivedArrType: [], //수신된 장애물 타입
            startFlag: false, //게임 시작 플레그 초기 false여야한다, 방장의 시작으로 동작 구현 필요
            drawFlag: false, //장애물 그리기 플레그
            drawObFlag: false, //장애물 생성 플레그
            enteredUser1: 0,
            enteredUser2: 0,
            gameStopFlag: false,
            receivedGameStopFlag: false,
            //////jsend//////
        };
    },
    components: {
        Header,
    },
    methods: {
        drawSsazip() {
            const ssazip = document.getElementById('ssazip');
            const ssazip2 = document.getElementById('ssazip2');
            const ssazipMe = document.getElementById('ssazipMe');
            const ssazipMe2 = document.getElementById('ssazipMe2');

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
                if (this.userPresent1[i]) {
                    //참여중이라면
                    let alreadyFlag = false;
                    // 캐릭터 속성
                    let dino = {
                        // 캐릭터 등장 좌표(왼쪽 상단으로부터)
                        x: leftSpace + 70 * i,
                        y: 220,
                        width: 50,
                        height: 50,
                        idx: i,
                        me: 0,
                        // 캐릭터 그리기 함수(생성)
                        draw() {
                            ctx.fillStyle = 'green';
                            ctx.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
                            if (this.me == 0) {
                                ctx.drawImage(ssazip, this.x, this.y, this.width, this.height);
                            } else {
                                ctx.drawImage(ssazipMe, this.x, this.y, this.width, this.height);
                            }
                        },
                    };
                    if (user.userId == this.userId) {
                        //본인과 같은 정보가 있다면 본인정보에 넣어주기
                        this.userIdx = i;
                        this.userTeam = 1;
                        dino.me = 1; //본인 표시
                        dino.height = 70;
                        dino.y = 200;
                    }
                    for (let j = 0; j < this.dinos1.length; j++) {
                        if (this.dinos1[j].x == dino.x) {
                            alreadyFlag = true;
                        }
                    }
                    if (!alreadyFlag) {
                        this.dinos1.push(dino); // 팀원수 확인해서 속성넣은 객체리스트로 만들어주기
                    }
                }
            });
            this.users2.forEach((user, i) => {
                if (this.userPresent2[i]) {
                    let alreadyFlag = false;

                    // 캐릭터 속성
                    let dino = {
                        // 캐릭터 등장 좌표(왼쪽 상단으로부터)
                        x: leftSpace + 70 * i,
                        y: 220,
                        width: 50,
                        height: 50,
                        idx: i,
                        me: 0,
                        // 캐릭터 그리기 함수(생성)
                        draw() {
                            ctx2.fillStyle = 'green';
                            ctx2.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
                            if (this.me == 0) {
                                ctx2.drawImage(ssazip2, this.x, this.y, this.width, this.height);
                            } else {
                                ctx2.drawImage(ssazipMe2, this.x, this.y, this.width, this.height);
                            }
                        },
                    };
                    if (user.userId == this.userId) {
                        //본인과 같은 정보가 있다면 본인정보에 넣어주기
                        this.userIdx = i;
                        this.userTeam = 2;
                        dino.me = 1; //본인 표시
                        dino.height = 70;
                        dino.y = 200;
                    }
                    for (let j = 0; j < this.dinos2.length; j++) {
                        if (this.dinos2[j].x == dino.x) {
                            alreadyFlag = true;
                        }
                    }
                    if (!alreadyFlag) {
                        this.dinos2.push(dino); // 팀원수 확인해서 속성넣은 객체리스트로 만들어주기
                    }
                }
            });
            console.log(this.dinos1.length + ' ' + this.dinos2.length);
        },

        drawSsazipgameStart() {
            const ssazip = document.getElementById('ssazip');
            const ssazip2 = document.getElementById('ssazip2');
            const ssazipMe = document.getElementById('ssazipMe');
            const ssazipMe2 = document.getElementById('ssazipMe2');

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
                if (this.userPresent1[i]) {
                    //참여중이라면
                    // 캐릭터 속성
                    let dino = {
                        // 캐릭터 등장 좌표(왼쪽 상단으로부터)
                        x: leftSpace + 70 * i,
                        y: 220,
                        width: 50,
                        height: 50,
                        idx: i,
                        me: 0,
                        // 캐릭터 그리기 함수(생성)
                        draw() {
                            ctx.fillStyle = 'green';
                            ctx.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
                            if (this.me == 0) {
                                ctx.drawImage(ssazip, this.x, this.y, this.width, this.height);
                            } else {
                                ctx.drawImage(ssazipMe, this.x, this.y, this.width, this.height);
                            }
                        },
                    };
                    if (user.userId == this.userId) {
                        //본인과 같은 정보가 있다면 본인정보에 넣어주기
                        this.userIdx = i;
                        this.userTeam = 1;
                        dino.me = 1; //본인 표시
                        dino.height = 70;
                        dino.y = 200;
                    }
                    this.dinos1.push(dino); // 팀원수 확인해서 속성넣은 객체리스트로 만들어주기
                }
            });

            this.users2.forEach((user, i) => {
                if (this.userPresent2[i]) {
                    // 캐릭터 속성
                    let dino = {
                        // 캐릭터 등장 좌표(왼쪽 상단으로부터)
                        x: leftSpace + 70 * i,
                        y: 220,
                        width: 50,
                        height: 50,
                        idx: i,
                        me: 0,
                        // 캐릭터 그리기 함수(생성)
                        draw() {
                            ctx2.fillStyle = 'green';
                            ctx2.fillRect(this.x, this.y, this.width, this.height); //위치, 크기
                            if (this.me == 0) {
                                ctx2.drawImage(ssazip2, this.x, this.y, this.width, this.height);
                            } else {
                                ctx2.drawImage(ssazipMe2, this.x, this.y, this.width, this.height);
                            }
                        },
                    };
                    if (user.userId == this.userId) {
                        //본인과 같은 정보가 있다면 본인정보에 넣어주기
                        this.userIdx = i;
                        this.userTeam = 2;
                        dino.me = 1; //본인 표시
                        dino.height = 70;
                        dino.y = 200;
                    }
                    this.dinos2.push(dino); // 팀원수 확인해서 속성넣은 객체리스트로 만들어주기
                }
            });

            let animation;
            ///////////////////////////// 장애물 생성 ////////////////////////////////
            // let cactusarr = []; //1팀 장애물 리스트
            // let cactusarr2 = []; //2팀 장애물 리스트
            let cactusCnt = 0; // 1팀 연속 나온 장애물 개수
            // let cactusCnt2 = 0; // 2팀 연속 나온 장애물 개수
            const cactusCntArr = [1, 1, 3, 2, 2, 3, 4, 2, 2, 3, 1]; //장애물 1초마다 생성되는 개수 (원소(쉬고)원소(쉬고)...)
            let cactusCntIdx = 0; //1팀 장애물 연속 생성개수 담은 리스트에서 몇번째 연속생성개수 인지 처리해주는 인덱스
            // let cactusCntIdx2 = 0; //2팀 장애물 연속 생성개수 담은 리스트에서 몇번째 연속생성개수 인지 처리해주는 인덱스
            // const obstacle1 = document.getElementById('obstacle1'); // eslint-disable-line no-unused-vars
            // const obstacle2 = document.getElementById('obstacle2'); // eslint-disable-line no-unused-vars
            // const obstacle3 = document.getElementById('obstacle3'); // eslint-disable-line no-unused-vars
            // const obstacle4 = document.getElementById('obstacle4'); // eslint-disable-line no-unused-vars

            // 1팀 장애물 속성
            class Cactus {
                // 장애물 생성기
                constructor() {
                    // 장애물 등장 위치(왼쪽 상단으로부터)
                    (this.x = 1200),
                        (this.y = 220),
                        (this.width = 50),
                        (this.height = 50),
                        // (this.type = Math.floor(Math.random() * (4 - 1 + 1)) + 1);
                        (this.type = 1);
                    // 나온 장애물이 설정했던 장애물 개수만큼 나왔다면 다시 쉬는블록 추가
                    if (cactusCnt == cactusCntArr[cactusCntIdx]) {
                        this.type = 0;
                        cactusCnt = 0;
                        cactusCntIdx++; //쉬었으니까 다음 장애물개수 생성하도록 1증가
                    } else {
                        cactusCnt++; //아직 쉬지않으니 연속 장애물 개수 추가
                    }
                }
                draw() {
                    if (this.type != 0) {
                        ctx.fillStyle = 'red';
                        ctx.fillRect(this.x, this.y, this.width, this.height);
                        ctx.drawImage(eval('obstacle' + this.type), this.x, this.y, this.width, this.height);
                        ctx2.fillStyle = 'red';
                        ctx2.fillRect(this.x, this.y, this.width, this.height);
                        ctx2.drawImage(eval('obstacle' + this.type), this.x, this.y, this.width, this.height);
                    }
                    // else {
                    //     (this.x = 0), (this.y = 0);
                    // }
                }
            }
            // 2팀 장애물 속성
            // class Cactus2 {
            //     // 장애물 생성기
            //     constructor() {
            //         // 장애물 등장 위치(왼쪽 상단으로부터)
            //         (this.x = 1200), (this.y = 220), (this.width = 50), (this.height = 50), (this.type = Math.floor(Math.random() * (4 - 1 + 1)) + 1);
            //         if (cactusCnt2 == cactusCntArr[cactusCntIdx2]) {
            //             this.type = 0;
            //             cactusCnt2 = 0;
            //             cactusCntIdx2++; //쉬었으니까 다음 장애물개수 생성하도록 1증가
            //         } else {
            //             cactusCnt2++; //아직 쉬지않으니 연속 장애물 개수 추가
            //         }
            //     }
            //     draw() {
            //         if (this.type != 0) {
            //             ctx2.fillStyle = 'red';
            //             ctx2.fillRect(this.x, this.y, this.width, this.height);
            //             ctx2.drawImage(eval('obstacle' + this.type), this.x, this.y, this.width, this.height);
            //         } else {
            //             (this.x = 0), (this.y = 0);
            //         }
            //     }
            // }
            ///////////////////////////// 애니메이션 동작 //////////////////////////////////

            // 1초에 60번 실행할 함수
            const frame = (timestamp) => {
                // console.log('drawSsazipgameForAlldrawSsazipgameForAll');
                animation = requestAnimationFrame(frame); //frame을 1초에 60번 실행해줘

                // requestAnimationFrame(frame); //frame을 1초에 60번 실행해줘

                ctx.clearRect(0, 0, canvas.width, canvas.height); //canvas 지워줘(잔상 안 남게)
                ctx2.clearRect(0, 0, canvas2.width, canvas2.height); //canvas2 지워줘(잔상 안 남게)

                // 시간 세기 시작

                const cntTime = parseInt(timestamp / 1000); //1초로 나눔
                const fTime = parseInt(timestamp / 16.7); // 1/60초마다 추가

                if (fTime > this.fTime) {
                    //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
                    this.fTime++;
                }

                //마스터시 장애물 생성
                if (this.getIsLogin && this.drawObFlag) {
                    if (cntTime > this.time) {
                        this.time++;

                        if (this.xArr.length == 0) {
                            this.xArr.push(1200); //장애물 배열 생성
                            this.xArrType.push(Math.floor(Math.random() * (4 - 1 + 1)) + 1); //랜덤 타입
                        } else if (this.xArr[this.xArr.length - 1] < 980) {
                            this.xArrType.push(Math.floor(Math.random() * (4 - 1 + 1)) + 1); //랜덤 타입
                            this.xArr.push(1200);
                        }
                    }

                    // 애니메이션을 1/60초마다 이루어지게 조정
                    if (fTime > this.fTime) {
                        this.xArr.forEach((a, i, o) => {
                            this.xArr[i] -= 4;
                            if (this.xArr[i] < 0) {
                                o.splice(i, 1);
                                this.xArrType.splice(i,1);
                            }
                        });
                        this.sendObstaclePosition();
                    }
                }
                //마스터시 장애물 생성 end

                // 애니메이션을 1/60초마다 이루어지게 조정
                if (fTime > this.fTime) {
                    // console.log('123123213123');
                    // console.log(this.receivedArr.length);
                    // new Cactus();
                    this.receivedArr.forEach((a, i) => {
                        // console.log('qqqqqqqqqqqqqqq');
                        //장애물 리스트에서 하나씩 빼서 그려주기
                        //이미 지나간 장애물은 삭제
                        // if (i + 1 < o.length) {
                        //     if (o[i + 1].x < -50) {
                        //         o.splice(i, 1);
                        //     }
                        // }
                        // //장애물 점점 왼쪽으로 가게 만들기
                        // a.x -= 4; //장애물 속도
                        var cactus = new Cactus();
                        cactus.x = this.receivedArr[i]; //장애물 속도
                        cactus.type = this.receivedArrType[i]; //장애물 타입

                        this.dinos1.forEach((dino) => {
                            checkCollision(dino, cactus); //모든 장애물과 충돌확인
                        });
                        this.dinos2.forEach((dino) => {
                            // dino;
                            checkCollision(dino, cactus); //모든 장애물과 충돌확인
                        });
                        // cactus.draw();
                        cactus.draw();
                        // a.draw(); //1팀 장애물 그리기
                    });
                    ///////////////////////////////점프 애니메이션 //////////////////////////////////
                    //1팀 점프
                    // console.log('dinos1 len =  ' + this.dinos1.length);
                    // animation;
                    this.dinos1.forEach((dino, i) => {
                        if (dino.me == 0) {
                            // 점프
                            if (this.users1[i].jump == true) {
                                this.dinos1[i].y -= 5; //점프속도
                            }
                            // 착지
                            if (this.users1[i].jump == false) {
                                if (dino.y < 220) {
                                    this.dinos1[i].y += 5; //착지속도
                                }
                            }
                            // 점프 중지
                            if (this.dinos1[i].y <= 90-5) {
                                this.users1[i].jump = false;
                            }
                        } else {
                            // 점프
                            if (this.users1[i].jump == true) {
                                this.dinos1[i].y -= 5; //점프속도
                            }
                            // 착지
                            if (this.users1[i].jump == false) {
                                if (dino.y < 200) {
                                    this.dinos1[i].y += 5; //착지속도
                                }
                            }
                            // 점프 중지
                            if (this.dinos1[i].y <= 70-5) {
                                this.users1[i].jump = false;
                            }
                        }
                        this.dinos1[i].draw(); // 1팀 싸집이들 그려주기
                    });
                    // 2팀 점프
                    this.dinos2.forEach((dino, i) => {
                        if (dino.me == 0) {
                            // 점프
                            if (this.users2[i].jump == true) {
                                this.dinos2[i].y -= 5; //점프속도
                            }
                            // 착지
                            if (this.users2[i].jump == false) {
                                if (dino.y < 220) {
                                    this.dinos2[i].y += 5; //착지속도
                                }
                            }
                            // 점프 중지
                            if (this.dinos2[i].y <= 90-5) {
                                this.users2[i].jump = false;
                            }
                        } else {
                            // 점프
                            if (this.users2[i].jump == true) {
                                this.dinos2[i].y -= 5; //점프속도
                            }
                            // 착지
                            if (this.users2[i].jump == false) {
                                if (dino.y < 200) {
                                    this.dinos2[i].y += 5; //착지속도
                                }
                            }
                            // 점프 중지
                            if (this.dinos2[i].y <= 70-5) {
                                this.users2[i].jump = false;
                            }
                        }
                        this.dinos2[i].draw(); // 2팀 싸집이들 그려주기
                    });
                }
                // console.log(this.receivedGameStopFlag);
                if (this.receivedGameStopFlag) {
                    const ssazipbg = document.getElementById('ssazipbg');
                    const ssazipbg2 = document.getElementById('ssazipbg2');
                    cancelAnimationFrame(animation);
                    ssazipbg.style.animation = 'paused';
                    ssazipbg2.style.animation = 'paused';
                }
            };
            frame();
            // 충돌체크함수
            const checkCollision = (dino, cactus) => {
                // console.log(dino);
                // console.log(cactus);
                let xDiff = cactus.x - (dino.x + dino.width);
                let yDiff = cactus.y - (dino.y + dino.height);
                // const ssazipbg = document.getElementById('ssazipbg');
                // const ssazipbg2 = document.getElementById('ssazipbg2');
                if (dino.x <= cactus.x + cactus.width) {
                    if (xDiff < -10 && yDiff < -10) {
                        this.gameStopFlag = true;
                        this.stopGame();
                        // cancelAnimationFrame(animation); //게임중단
                        // console.log(xDiff);
                        // ssazipbg.style.animation = 'paused';
                        // ssazipbg2.style.animation = 'paused';
                        // this.status = false
                        // animation;
                    }
                }
            };
        },

        /////////////////////////////////////////////////////////////////////////////////////
        /**
         * Socket 요청들
         */
        // 게임 방 입장 : 정보 구독 및 유저 정보 전송
        onConnected() {
            // 유저 정보 교환
            this.stompClient.subscribe('/game/jumpgame/' + this.getRoomId, this.onMessageReceived);
            console.log('=========conn start');
            this.stompClient.send(
                '/pub/game/jump/data',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    participantId: this.getUser.id,
                    participantName: this.getUser.name,
                    buserPresent1: this.userPresent1,
                    buserPresent2: this.userPresent2,
                    teamNo: 0,
                    type: 1, //초기 연결
                })
            );
            this.entering();
            console.log('onconnected');
        },
        //점프 명령어 전송
        jumping() {
            console.log('=======jummping');
            // console.log(this.jumpSendArr1);
            // console.log(this.jumpSendArr2);
            this.stompClient.send(
                '/pub/game/jump/data',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    jumpArr1: this.jumpSendArr1,
                    jumpArr2: this.jumpSendArr2,
                    type: 2, //점프 데이터
                })
            );
        },
        //재시작
        reloading() {
            console.log('=======sending reloading');
            this.reloadFlag = true;
            this.stompClient.send(
                '/pub/game/jump/data',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    reloadflag: this.reloadFlag,
                    type: 3,
                })
            );
        },
        //장애물 송신
        sendObstaclePosition() {
            console.log('ooooooooooooooooo');
            this.stompClient.send(
                '/pub/game/jump/obstacle',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    xbArr: this.xArr,
                    xbArrType: this.xArrType,
                    type: 4,
                    obstacleflag: true,
                })
            );
        },
        //입장
        entering() {
            console.log('=======entering');
            // console.log(this.userPresent1),
                this.stompClient.send(
                    '/pub/game/jump/enter',
                    {},
                    JSON.stringify({
                        roomId: this.getRoomId,
                        buserPresent1: this.userPresent1,
                        buserPresent2: this.userPresent2,
                        type: 5,
                    })
                );
        },
        stopGame() {
            console.log('=======gameStopFlag');
            // console.log(this.gameStopFlag);
            this.gameStopFlag = true;
            this.stompClient.send(
                '/pub/game/jump/stop',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    bGameStopFlag: this.gameStopFlag,
                    type: 6,
                })
            );
            this.gameStopFlag = false;
        },
        // 메세지 수신
        onMessageReceived(payload) {
            let info = JSON.parse(payload.body);
            console.log('======got mes=========');
            // console.log(info);
            if (info.type == 1) {
                //초기연결
                console.log('초기 연결 수신');
                // console.log(info);
                return;
            }

            //게임 정지
            if (info.type == 6) {
                // this.receivedGameStopFlag = info.BgameStopFlag;
                this.receivedGameStopFlag = true;
            }
            //유저 입장
            if (info.type == 5) {
                this.userPresent1 = info.buserPresent1;
                this.userPresent2 = info.buserPresent2;
                this.drawSsazip();
                console.log('유저입장');
            }

            //리로드
            if (info.reloadflag && info.type == 3) {
                this.reloadFlag = false;
                this.$router.go();
                return;
            }
            if (info.type == 4) {
                //장애물 생성
                this.receivedArr = info.xbArr;
                this.receivedArrType = info.xbArrType;
                return;
            }
            // console.log(this.receivedArr);
            if (info.obstacleflag && !this.drawFlag) {
                this.drawFlag = true;
                // this.drawSsazipgameForAll();
            }

            if (info.type == 2) {
                //점프
                this.receivedJumpArr1 = info.jumpArr1;
                this.receivedJumpArr2 = info.jumpArr2;
                for (let i = 0; i < this.receivedJumpArr1.length; i++) {
                    if (this.receivedJumpArr1[i]) {
                        //점프 명령
                        this.users1[i].jump = true;
                        //초기화
                        this.receivedJumpArr1[i] = false;
                    }
                    // this.users1[i].jump = this.jumpsss1[i];
                }
                for (let i = 0; i < this.receivedJumpArr2.length; i++) {
                    if (this.receivedJumpArr2[i]) {
                        //점프 명령
                        this.users2[i].jump = true;
                        //초기화
                        this.receivedJumpArr2[i] = false;
                    }
                    // this.users2[i].jump = this.jumpsss2[i];
                }
            }

            // console.log('users');
            // console.log(this.users1);
            // console.log(this.users2);
            // console.log('users');

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
        //////////js end/////////////
    },
    created() {
        // this.checkRoom();
        // this.checkName();
        // 소켓 연결
        this.stompClient = socketConnect(this.onConnected, this.onError);

        // 방정보 초기화
        this.room.id = this.getRoomId;
        // console.log(this.getRoomId);
        this.master = this.getIsLogin;
        // this.room.id = 123;
    },
    mounted() {
        // this.stompClient = socketConnect(this.onConnected, this.onError);

        this.userId = this.getUser.name;
        console.log('==========start i am ' + this.getUser.name);
        //팀 선정
        for (let i = 0; i < this.users1.length; i++) {
            if (this.users1[i].userId == this.getUser.name) {
                this.teamIdx = 1;
                break;
            }
        }
        //초기 선언
        for (let i = 0; i < this.users1.length; i++) {
            console.log(this.userPresent1);
            if (this.users1[i].userId == this.getUser.name) {
                console.log('checker1');
                this.userPresent1[i] = true;
                console.log(this.userPresent1);

                // this.entering();
                // this.jumpsss1.push(false);
                this.users1[i].jump = false;
                let idx = 1;
                for (let i = 0; i < this.userPresent1.length; i++) {
                    if (this.userPresent1[i]) idx++;
                }
                console.log('1팀의 ' + idx + '번째 유저입니다');
                // this.jumping();
            }
        }
        for (let i = 0; i < this.users1.length; i++) {
            if (this.users2[i].userId == this.getUser.name) {
                this.userPresent2[i] = true;
                // this.entering();
                // this.jumpsss2.push(false);
                this.users2[i].jump = false;
                let idx = 1;
                this.userPresent2.forEach((a) => {
                    if (a) idx++;
                });
                console.log('2팀의 ' + idx + '번째 유저입니다');
                // this.jumping();
            }
        }

        if (!this.startFlag) {
            this.startFlag = true;
            this.drawSsazipgameStart();
        }

        //플레이어 조작
        document.addEventListener('keydown', (e) => {
            if (e.code === 'KeyA' && !this.drawObFlag) {
                this.drawObFlag = true;
                console.log('a가 눌렸다');
            }
            //새로 고침
            if (e.code === 'Enter') {
                if (this.getIsLogin == true) {
                    console.log('재시작');
                    this.reloading(); //마스터만 재 시작 가능
                }
            }
            // }),
            //     //스페이스바 누르면 점프값 변경
            //     document.addEventListener('keydown', (e) => {
            //착지해야만 점프가능
            if (this.startFlag) {
                if (this.teamIdx == 1) {
                    if (this.dinos1[this.userIdx].y == 200) {
                        if (e.code === 'Space') {
                            console.log('space pushed team1');
                            this.jumpSendArr1[this.userIdx] = true;
                            this.jumping();
                            this.jumpSendArr1[this.userIdx] = false;
                        }
                    }
                } else {
                    if (this.dinos2[this.userIdx].y == 200) {
                        if (e.code === 'Space') {
                            console.log('space pushed team2');
                            this.jumpSendArr2[this.userIdx] = true;
                            this.jumping();
                            this.jumpSendArr2[this.userIdx] = false;
                        }
                    }
                }
            }
        });
    },
    ///////////////////////////////////

    computed: {
        ...mapGetters(['getRoomId', 'getUser']),
        ...mapGetters(['getUser', 'getIsLogin']),

        // ...mapState(['visitedRoomId']),
        // ...mapActions(['joinRoom']),
    },
    /////////////////////end/////////////
};
</script>

<style></style>
