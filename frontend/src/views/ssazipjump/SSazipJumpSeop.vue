<template>
    <div>
        <SSazipjumpGuideModal v-if="guideModalOpenFlag == true" :userId="userId" :hostId="hostId" @guideModal="guideModal" />
        <SSazipjumpStartModal v-if="startModalOpenFlag == true" :userId="userId" :hostId="hostId" @getCloseModal="closeStartModal" />
        <SSazipjumpRoundModal
            v-if="roundModalOpenFlag == true"
            :battleTeam1="battleTeam1"
            :battleTeam2="battleTeam2"
            :userId="userId"
            :hostId="hostId"
            @getCloseModal="closeRoundModal"
        />
        <SSazipjumpRankModal v-if="rankModalOpenFlag == true" :userId="userId" :hostId="hostId" :finalScore="finalScore" @getCloseModal="closeStartModal" />
        <Header v-bind:gameTitle="'싸집이 점프 게임'" :host="getUser.id" />
        <!-- <button @click="showRound()">dd</button> -->
        <div style="display:flex; justify-content:center">
            <!-- 좌측 게임 -->
            <div class="ssazip-game-outer-container">
                <!-- 윗팀 -->
                <div class="ssazip-game-container">
                    <div id="ssazipbg" class="ssazip-game-background wrap">
                        <canvas id="canvas"></canvas>
                        <div v-if="showResult">
                            <div v-if="lose1" class="lose1 chatbox">LOSE</div>
                            <div v-if="win1" class="win1 chatbox">WIN</div>
                            <marquee loop="10" v-if="lose1" class="loser1" scrollamount="25">{{ loser }}님이 걸리셨습니다.</marquee>
                        </div>
                        <div>
                            <div class="round1"></div>
                        </div>
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
                    <div id="ssazipbg2" class="ssazip-game-background2 wrap">
                        <canvas id="canvas2"></canvas>
                        <div v-if="showResult">
                            <div v-if="!lose1" class="lose2 chatbox">LOSE</div>
                            <div v-if="!win1" class="win2 chatbox">WIN</div>
                            <marquee loop="10" v-if="!lose1" class="loser2" scrollamount="25">{{ loser }}님이 걸리셨습니다.</marquee>
                        </div>
                        <div>
                            <div class="round2"></div>
                        </div>
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
                                <div class="ssazip-rounds-container">
                                    <img
                                        class="current-round-team-individual"
                                        style="margin-right: 30px;"
                                        :src="require(`@/assets/team${battleTeam1}.png`)"
                                        alt=""
                                    />
                                    <div class="ssazip-vs">vs</div>
                                    <img
                                        class="current-round-team-individual"
                                        style="margin-left: 30px;"
                                        :src="require(`@/assets/team${battleTeam2}.png`)"
                                        alt=""
                                    />
                                    <div class="ssazip-league">{{ remainRound }}강</div>
                                </div>
                            </div>
                        </div>
                        <div class="ssazip-round-item" style="background-color:#EAC16F; margin-top:2.5vh;">
                            <div class="ssazip-round-item-inner">
                                <div class="ssazip-round-item-title">다음 게임</div>
                                <div class="ssazip-rounds-container">
                                    <img
                                        class="current-round-team-individual"
                                        style="margin-right: 30px;"
                                        :src="require(`@/assets/team${nextBattleTeam1}.png`)"
                                        alt=""
                                    />
                                    <div class="ssazip-vs">vs</div>
                                    <img
                                        class="current-round-team-individual"
                                        style="margin-left: 30px;"
                                        :src="require(`@/assets/team${nextBattleTeam2}.png`)"
                                        alt=""
                                    />
                                    <div class="ssazip-league">{{ nextRemainRound }} 강</div>
                                </div>
                            </div>
                        </div>

                        <div class="ssazip-round-item" style="background-color:#B9D272;margin-top:2.5vh;">
                            <div class="ssazip-round-item-inner">
                                <div class="ssazip-round-item-title">{{ nowRoundNum }}ROUND</div>
                                <div class="ssazip-score-container">
                                    <div class="team-round-score">{{ gameScore1 }} : {{ gameScore2 }}</div>
                                    <div class="ssazip-round-score-teams">
                                        <span style="margin-right:25px;">{{ battleTeam1 }}팀</span>
                                        <span style="margin-left:50px">{{ battleTeam2 }}팀</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Header from '@/components/common/Header.vue';
import '../../css/ssazip-jump.css';
import { socketConnect } from '@/util/socket-common.js';
import { mapGetters } from 'vuex';

import SSazipjumpGuideModal from '@/components/ssazipjump/SSazipjumpGuideModal.vue';
import SSazipjumpStartModal from '@/components/ssazipjump/SSazipjumpStartModal.vue';
import SSazipjumpRoundModal from '@/components/ssazipjump/SSazipjumpRoundModal.vue';
import SSazipjumpRankModal from '@/components/ssazipjump/SSazipjumpRankModal.vue';
import swal from 'sweetalert';

export default {
    name: 'SSazipJump',
    data: function() {
        return {
            time: 0,
            fTime: 0,
            otime: 0,
            ofTime: 0,
            //users => 예정된 해당 플레이어
            users1: [
                //요청으로 받아야 함
                { userId: '이장섭', jump: false },
                { userId: '권희은', jump: false },
                { userId: '김태현', jump: 0 },
                { userId: '차은채', jump: 0 },
                { userId: '술희원', jump: 0 },
            ],
            //let murangsubul = {userId : this.member, jump : 0
            //users1.push(murangsubul)
            users2: [
                { userId: '안기훈', jump: false },
                { userId: '꼬륵채', jump: false },
                { userId: '준박', jump: 0 },
                { userId: '이미영', jump: 0 },
                { userId: '박용미', jump: 0 },
            ],
            //참여 중 여부 확인 어레이
            userPresent1: [false, false, false, false, false, false],
            userPresent2: [false, false, false, false, false, false],
            dinos1: [], //싸집이 정보 배열
            dinos2: [],
            userId: '안기훈',
            userIdx: -1, //팀 내 순서
            userPlayIdx: -1, //실 참가 인원 중 순서
            status: true,
            obstacle1: document.getElementById('obstacle1'),
            obstacle2: document.getElementById('obstacle2'),
            obstacle3: document.getElementById('obstacle3'),
            stompClient: null,
            // 방 정보
            roomNgameInfo: {},

            jumpSendArr1: [false, false, false, false, false],
            jumpSendArr2: [false, false, false, false, false],
            receivedJumpArr1: [false, false, false, false, false],
            receivedJumpArr2: [false, false, false, false, false],
            //임의값
            enteredFlag: false, //입장(전체와 연결) 확인 플레그
            // getRoomId: 123,//dump test
            teamOneOrTwo: 3,
            reloadFlag: false,
            masterKeyFlag: false, //로그인 여부, 방장 체크용
            masterCreatedFlag: false, //마스터가 선 입장 여부 체크
            xArr: [], //마스터 장애물 위치
            xArrType: [], //마스터 장애물 타입
            receivedArr: [], //수신된 장애물 위치
            receivedArrType: [], //수신된 장애물 타입
            startFlag: false, //게임 시작 플레그 초기 false여야한다, 방장의 시작으로 동작 구현 필요
            drawFlag: false, //장애물 그리기 플레그
            obstacleflag: false, //장애물 생성 플레그
            enteredUser1: 0,
            enteredUser2: 0,
            // gameStopFlag: false,
            receivedGameStopFlag: false,
            animationOnFlag: false, //반복 에니매이션 동작 여부 플레그
            hostId: '', //호스트 유저의 이름이 었을껄?
            battleTeam1: 20, // 진행 될 경기 팀 번호
            battleTeam2: 20,
            nextBattleTeam1: 20, //다음 진행 될 경기 팀 번호
            nextBattleTeam2: 20,
            nowRoundNum: 1, // 현재 라운드 회차
            gameScore1: 0, // 현 라운드에서 1팀이 이긴 값
            gameScore2: 0, // ''
            loser: '', //걸린이 이름 : 희은이가 지음
            loseTeam: '',
            isRegistryed: false, //db 등록여부 파악
            remainRound: 0, //몇 강
            nextRemainRound: 0, //다음 기다리는 팀의 배틀은 몇강
            finalScore: [], //최종 결산용

            // guideModalOpen: true, //게임시작시 첫시작 소개모달
            guideModalOpenFlag: false,
            startModalOpenFlag: false, //게임시작시 게임방법 안내 모달
            roundModalOpenFlag: false, //다음 팀 대전표 안내 모달
            rankModalOpenFlag: false, //종료 결산 모달
            lose1: false, //1팀이 지면
            // lose2: true,
            win1: true, //1팀이 이기면
            // win2: false,
            showResult: false, //걸린이 표시
            roundCnt: 0,
            checkCollisionOnFlag: false, //단발성 충돌 감지용 플레그
        };
    },
    components: {
        Header,
        SSazipjumpStartModal,
        SSazipjumpRoundModal,
        SSazipjumpRankModal,
        SSazipjumpGuideModal,
    },
    methods: {
        drawSsazip() {
            console.log('============싸집이를 그린다');
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

            this.dinos1 = [];
            this.dinos2 = [];
            this.users1.forEach((user, i) => {
                if (this.userPresent1[i]) {
                    //참여중이라면
                    // let alreadyFlag = false;
                    // 캐릭터 속성
                    let dino = {
                        // 캐릭터 등장 좌표(왼쪽 상단으로부터)
                        // x: leftSpace + 70 * i,
                        x: leftSpace + 70 * this.dinos1.length,
                        y: 220,
                        width: 50,
                        height: 50,
                        idx: i,
                        me: 0,
                        name: this.users1[i].userId,
                        teamNo: 1,
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
                        this.userPlayIdx = this.dinos1.length;
                        console.log('팀 내' + this.userIdx + '번째이며, 실제로는' + this.userPlayIdx);
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
                    // let alreadyFlag = false;
                    // 캐릭터 속성
                    let dino = {
                        // 캐릭터 등장 좌표(왼쪽 상단으로부터)
                        // x: leftSpace + 70 * i,
                        x: leftSpace + 70 * this.dinos2.length,
                        y: 220,
                        width: 50,
                        height: 50,
                        idx: i,
                        me: 0,
                        name: this.users2[i].userId,
                        teamNo: 2,
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
                        this.userPlayIdx = this.dinos2.length;
                        console.log('팀 내' + this.userIdx + '번째이며, 실제로는' + this.userPlayIdx);
                        this.userTeam = 2;
                        dino.me = 1; //본인 표시
                        dino.height = 70;
                        dino.y = 200;
                    }
                    this.dinos2.push(dino); // 팀원수 확인해서 속성넣은 객체리스트로 만들어주기
                }
            });
        },

        ///////////////////////////캔버스 구역/////////////////////////////////////
        ///////////////////////////캔버스 구역/////////////////////////////////////
        ///////////////////////////캔버스 구역/////////////////////////////////////
        drawSsazipgameStart() {
            // canvas는 mounted이후에 사용이 가능하다.
            let canvas = document.getElementById('canvas');
            let ctx = canvas.getContext('2d');
            let canvas2 = document.getElementById('canvas2');
            let ctx2 = canvas2.getContext('2d');

            let animation;
            let cactusCnt = 0; // 1팀 연속 나온 장애물 개수
            const cactusCntArr = [1, 1, 3, 2, 2, 3, 4, 2, 2, 3, 1]; //장애물 1초마다 생성되는 개수 (원소(쉬고)원소(쉬고)...)
            let cactusCntIdx = 0; //1팀 장애물 연속 생성개수 담은 리스트에서 몇번째 연속생성개수 인지 처리해주는 인덱스

            // 장애물 생성기
            class Cactus {
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
                }
            }

            //////////////////반복 액션//////////////////
            //////////////////반복 액션//////////////////
            //////////////////반복 액션//////////////////
            const frame = (timestamp) => {
                animation = requestAnimationFrame(frame); //frame을 1초에 60번 실행해줘 실행해줘
                this.animationOnFlag = true;

                ctx.clearRect(0, 0, canvas.width, canvas.height); //canvas 지워줘(잔상 안 남게)
                ctx2.clearRect(0, 0, canvas2.width, canvas2.height); //canvas2 지워줘(잔상 안 남게)

                // 시간 세기 시작
                const cntTime = parseInt(timestamp / 1000); //1초로 나눔
                const fTime = parseInt(timestamp / 16.7); // 1/60초마다 추가
                if (fTime > this.fTime) {
                    //이전 time의 초보다 커지면 1초 더해줌(계속 시간 가도록)
                    this.fTime++;
                }

                //마스터 요청시 장애물 생성
                if (this.getIsLogin && this.obstacleflag) {
                    if (cntTime > this.time) {
                        document.getElementById('ssazipbg').style.animationPlayState = 'running';
                        document.getElementById('ssazipbg2').style.animationPlayState = 'running';
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
                                this.xArrType.splice(i, 1);
                            }
                        });
                        this.sendObstaclePosition(); //장애물 송출
                    }
                }
                //마스터 요청시 장애물 생성 end
                // 애니메이션을 1/60초마다 이루어지게 조정
                if (fTime > this.fTime) {
                    //////////장애물 드로잉///////////////
                    //////////장애물 드로잉///////////////
                    //////////장애물 드로잉///////////////
                    this.receivedArr.forEach((a, i) => {
                        // //부전승 경우
                        // //부전승 경우
                        // //부전승 경우
                        // //부전승 경우
                        // if (this.battleTeam1 == 20) {
                        //     this.loseTeam = 1;
                        //     this.loser = '유령';
                        //     this.nowRoundNum = 4;
                        //     this.stopGameNBattleSending();
                        // } else if (this.battleTeam2 == 20) {
                        //     this.loseTeam = 2;
                        //     this.loser = '유령';
                        //     this.nowRoundNum = 4;
                        //     this.stopGameNBattleSending();
                        // }
                        //장애물 위치 배열
                        var cactus = new Cactus();
                        cactus.x = this.receivedArr[i]; //장애물 위치
                        cactus.type = this.receivedArrType[i]; //장애물 타입 배열 이용
                        if (this.masterKeyFlag && !this.receivedGameStopFlag) {
                            this.dinos1.forEach((dino) => {
                                checkCollision(dino, cactus); //모든 장애물과 충돌확인
                            });
                            this.dinos2.forEach((dino) => {
                                checkCollision(dino, cactus); //모든 장애물과 충돌확인
                            });
                        }
                        // 비 충돌이라면 장애물 그리기
                        cactus.draw();
                    });

                    ///////////////////////////////점프 애니메이션 //////////////////////////////////
                    ///////////////////////////////점프 애니메이션 //////////////////////////////////
                    ///////////////////////////////점프 애니메이션 //////////////////////////////////
                    // //1팀 점프
                    let userNum1 = 0;
                    let userNum2 = 0;
                    this.dinos1.forEach((dino, i) => {
                        for (let j = userNum1; j < this.users1.length; j++) {
                            if (!this.userPresent1[j]) userNum1++;
                            if (this.userPresent1[j]) break;
                        }
                        if (dino.me == 0) {
                            // 점프
                            if (this.users1[userNum1].jump == true) {
                                this.dinos1[i].y -= 5; //점프속도
                            }
                            // 착지
                            if (this.users1[userNum1].jump == false) {
                                if (dino.y < 220) {
                                    this.dinos1[i].y += 5; //착지속도
                                }
                            }
                            // 점프 중지
                            if (this.dinos1[i].y <= 90) {
                                this.users1[userNum1].jump = false;
                            }
                        } else {
                            //본인시
                            // 점프
                            if (this.users1[userNum1].jump == true) {
                                this.dinos1[i].y -= 5; //점프속도
                            }
                            // 착지
                            if (this.users1[userNum1].jump == false) {
                                if (dino.y < 200) {
                                    this.dinos1[i].y += 5; //착지속도
                                }
                            }
                            // 점프 중지
                            if (this.dinos1[i].y <= 70) {
                                this.users1[userNum1].jump = false;
                            }
                        }
                        this.dinos1[i].draw(); // 1팀 싸집이들 그려주기
                        userNum1++;
                    });
                    // 2팀 점프
                    this.dinos2.forEach((dino, i) => {
                        for (let j = userNum2; j < this.users2.length; j++) {
                            if (!this.userPresent2[j]) userNum2++;
                            if (this.userPresent2[j]) break;
                        }
                        if (dino.me == 0) {
                            // 점프
                            if (this.users2[userNum2].jump == true) {
                                this.dinos2[i].y -= 5; //점프속도
                            }
                            // 착지
                            if (this.users2[userNum2].jump == false) {
                                if (dino.y < 220) {
                                    this.dinos2[i].y += 5; //착지속도
                                }
                            }
                            // 점프 중지
                            if (this.dinos2[i].y <= 90) {
                                this.users2[userNum2].jump = false;
                            }
                        } else {
                            //본인시
                            // 점프
                            if (this.users2[userNum2].jump == true) {
                                this.dinos2[i].y -= 5; //점프속도
                            }
                            // 착지
                            if (this.users2[userNum2].jump == false) {
                                if (dino.y < 200) {
                                    this.dinos2[i].y += 5; //착지속도
                                }
                            }
                            // 점프 중지
                            if (this.dinos2[i].y <= 70) {
                                this.users2[userNum2].jump = false;
                            }
                        }
                        this.dinos2[i].draw(); // 2팀 싸집이들 그려주기
                        userNum2++;
                    });
                }
                // console.log(this.receivedGameStopFlag);
                if (this.receivedGameStopFlag) {
                    //6
                    this.receivedGameStopFlag = false;
                    console.log('=============== stop ani');
                    console.log('=============== stop ani');
                    console.log('=============== stop ani');
                    console.log('=============== stop ani');
                    // this.loser = this.room.loser;
                    this.showResult = true;
                    const ssazipbg = document.getElementById('ssazipbg');
                    const ssazipbg2 = document.getElementById('ssazipbg2');
                    cancelAnimationFrame(animation);
                    this.animationOnFlag = false;
                    ssazipbg.style.animationPlayState = 'paused';
                    ssazipbg2.style.animationPlayState = 'paused';
                    // if (this.masterKeyFlag) {
                    //     //마스터 재시작
                    //     setTimeout(() => {
                    //         // this.showResult = false;
                    //         console.log('next start');ㅁㅁㄴㅇ
                    //         this.xArr = [];
                    //         this.xArrType = [];
                    //         this.startObstacle();
                    //         //게임시작
                    //         //-------
                    //     }, 3000);
                    // } else {
                    //     setTimeout(() => {
                    //         // this.showResult = false;
                    //         console.log('next start');
                    //     }, 3000);
                    // }
                }
            };
            frame();
            // 충돌체크함수 by master
            const checkCollision = (dino, cactus) => {
                let xDiff = cactus.x - (dino.x + dino.width);
                let yDiff = cactus.y - (dino.y + dino.height);
                if (dino.x <= cactus.x + cactus.width) {
                    if (xDiff < -10 && yDiff < -10 && !this.checkCollisionOnFlag) {
                        console.log('=======충돌');
                        console.log('=======충돌');
                        console.log(dino.name);
                        this.drawFlag = false;
                        this.loser = dino.name;
                        this.loseTeam = dino.teamNo;
                        if (this.loseTeam == 1) {
                            this.gameScore2++;
                        } else {
                            this.gameScore1++;
                        }
                        this.nowRoundNum++;
                        // this.gameStopFlag = true;
                        this.receivedGameStopFlag = true; //정지신호
                        if (this.nowRoundNum < 4 && this.gameScore1 < 2 && this.gameScore2 < 2) {
                            //동일 팀 승패 가르기 중
                            this.stopGameSendingByMaster();
                        } else {
                            //다음 배틀로
                            this.stopGameNBattleSendingByMaster();
                        }
                    }
                }
            };
        },

        /////////////////////////////////// method ///////////////////////////////
        /////////////////////////////////// method ///////////////////////////////
        /////////////////////////////////// method ///////////////////////////////

        //플레이어 세팅
        setUsers() {
            console.log('플레이어 세팅');

            console.log(this.room.teamIdx1 + ' ' + this.room.teamIdx2);

            //배틀 할 팀
            let teamNum1 = this.room.teamOrder[this.room.teamIdx1];
            let teamNum2 = this.room.teamOrder[this.room.teamIdx2];

            // let teamIdx1 = this.room.teamIdx1;
            // let teamIdx2 = this.room.teamIdx2;
            this.users1 = [];
            this.users2 = [];
            console.log(teamNum1 + ' ' + teamNum2);
            if (teamNum1 != undefined) {
                console.log('1팀 수 ' + this.room.teamsMember[teamNum1].members.length);

                for (let i = 0; i < this.room.teamsMember[teamNum1].members.length; i++) {
                    console.log(i + '번 플레이어');
                    console.log(this.room.teamsMember[teamNum1].members[i].participantName);
                    let mem = { userId: this.room.teamsMember[teamNum1].members[i].participantName, jump: false };
                    this.users1.push(mem);
                }
            }
            if (teamNum2 != undefined) {
                // if (this.room.teamsMember[teamNum2] != null) {
                console.log('2팀 수 ' + this.room.teamsMember[teamNum2].members.length);
                for (let i = 0; i < this.room.teamsMember[teamNum2].members.length; i++) {
                    console.log(i + '번 플레이어');
                    console.log(this.room.teamsMember[teamNum2].members[i].participantName);

                    let mem = { userId: this.room.teamsMember[teamNum2].members[i].participantName, jump: false };
                    this.users2.push(mem);
                }
                // }
            }
            // this.broadcastToUser();

            //
            // //마스터가 아니라면  플레이어 여부 확인과 등록 필요
            if (!this.masterKeyFlag) {
                this.isPlayer();
            } else {
                this.drawSsazip();
            }
        },
        isPlayer() {
            // this.userPresent1 = info.beUserPresent1;
            // this.userPresent2 = info.beUserPresent2;
            //플레이어 여부 확인
            for (let i = 0; i < this.users1.length; i++) {
                if (this.users1[i].userId == this.getUser.name) {
                    if (this.userPresent1[i]) {
                        //이미 등록 되어있다면
                        this.drawSsazip();
                    }
                    this.userPresent1[i] = true; //참가 확인 어레이, 플레이어 값
                    this.teamOneOrTwo = 1; //팀 선정
                    // 유저 개인 메세지
                    let idx = 1;
                    for (let i = 0; i < this.userPresent1.length; i++) {
                        if (this.userPresent1[i]) idx++;
                    }
                    console.log('1팀의 ' + idx + '번째 유저입니다');
                    this.playerRegSending();
                }
            }
            if (this.teamOneOrTwo != 1) {
                for (let i = 0; i < this.users2.length; i++) {
                    if (this.users2[i].userId == this.getUser.name) {
                        if (this.userPresent2[i]) {
                            //이미 등록 되어있다면
                            this.drawSsazip();
                        }
                        this.userPresent2[i] = true; //참가 확인 어레이, 플레이어 값
                        this.teamOneOrTwo = 2; //팀 선정
                        // 유저 개인 메세지
                        let idx = 1;
                        for (let i = 0; i < this.userPresent1.length; i++) {
                            if (this.userPresent2[i]) idx++;
                        }
                        console.log('1팀의 ' + idx + '번째 유저입니다');
                        this.playerRegSending();
                    }
                }
            }
        },

        ////////////////////// modal method //////////////////////////
        ////////////////////// modal method //////////////////////////
        ////////////////////// modal method //////////////////////////

        guideModal() {
            //closeGuideModal
            this.closeGuideModalReqSending();
            // this.guideModalOpen = false;
            // this.startModalOpen = true;
        },
        closeStartModal() {
            this.closeStartModalReqSending();
            // this.startModalOpen = open;
            // this.roundModalOpen = true;
            // this.rankModalOpen = open;
            // this.roundModal();
        },
        startObstacle() {
            this.xArr = [];
            this.xArrType = [];
            this.xbArr = [];
            this.xbArrType = [];
            this.receivedArr = [];
            this.receivedArrType = [];
            this.receivedGameStopFlag = false;
            this.obstacleflag = true;
            document.getElementById('ssazipbg').style.animationPlayState = 'running';
            document.getElementById('ssazipbg2').style.animationPlayState = 'running';
            if (!this.animationOnFlag) {
                this.drawSsazipgameStart();
            }
        },
        showRound() {
            console.log('sssssssssssssssssssssssssssrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr');
            console.log('sssssssssssssssssssssssssssrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr');
            console.log('sssssssssssssssssssssssssssrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr');
            console.log('sssssssssssssssssssssssssssrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr');
            console.log('sssssssssssssssssssssssssssrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr');

            //시작 전 라운드와 1, 2, 3 표시
            //round 표시함수
            const round1 = document.querySelector('.round1');
            const round2 = document.querySelector('.round2');
            round1.innerText = `ROUND${this.nowRoundNum}`;
            round2.innerText = `ROUND${this.nowRoundNum}`;
            if (round1.style.opacity == 0) {
                //투명도
                round1.style.opacity = 1;
                round2.style.opacity = 1;
                // this.roundCnt = 3

                setTimeout(function() {
                    round1.style.opacity = 0;
                    round2.style.opacity = 0;
                }, 1500); // ROUND 뜨고 사라지는 시간
                setTimeout(function() {
                    round1.innerText = 3;
                    round2.innerText = 3;
                    round1.style.opacity = 1;
                    round2.style.opacity = 1;
                }, 2000);
                setTimeout(function() {
                    round1.style.opacity = 0;
                    round2.style.opacity = 0;
                }, 2500); // 3 뜨고 사라지는 시간
                setTimeout(function() {
                    round1.innerText = 2;
                    round2.innerText = 2;
                    round1.style.opacity = 1;
                    round2.style.opacity = 1;
                }, 3000);
                setTimeout(function() {
                    round1.style.opacity = 0;
                    round2.style.opacity = 0;
                }, 3500); // 2 뜨고 사라지는 시간
                setTimeout(function() {
                    round1.innerText = 1;
                    round2.innerText = 1;
                    round1.style.opacity = 1;
                    round2.style.opacity = 1;
                }, 4000);
                setTimeout(function() {
                    round1.style.opacity = 0;
                    round2.style.opacity = 0;
                }, 4500); // 1 뜨고 사라지는 시간
                setTimeout(function() {
                    console.log('st');
                    round1.innerText = 'START';
                    round2.innerText = 'START';
                    round1.style.opacity = 1;
                    round2.style.opacity = 1;
                }, 5000);
                setTimeout(() => {
                    console.log('start');
                    this.startObstacle();
                    round1.style.opacity = 0;
                    round2.style.opacity = 0;
                }, 5500); // 시작 뜨고 사라지는 시간
            }
        },

        roundModal() {
            this.roundModalOpenFlag = true;
        },

        closeRoundModal() {
            console.log('closeRoundModalcloseRoundModal');
            console.log('closeRoundModalcloseRoundModal');
            console.log('closeRoundModalcloseRoundModal');
            console.log('closeRoundModalcloseRoundModal');
            this.closeRoundModalReqSending();
            // this.roundModalOpen = open;
            // this.showRound();
        },
        //시작부터 끝을 담은 토탈 함수
        gameResetNStart() {
            console.log('게임이 시작됩니다');
            document.querySelector('.round1').style.opacity = 0;
            document.querySelector('.round2').style.opacity = 0;

            this.showResult = false;
            console.log('초기화 완료, 애니메이트 시작');

            this.roundModal(); // this.roundModalOpenFlag = true; open, 닫기 -closeRoundModalReqSending 43
            //showRound //3,2,1 카운트 다운
            //startObstacle // 장애물 위치 타입 배열 초기화, 장애물 생성기 트루, 배경 움직임
            //충돌 by master
            //다음 게임 stopGameSending 6번 => 데이터 초기화, 루저 값 전송, 일정 시간 뒤 게임 시작
            //openRoundModalreq 44번 => showRound 초기화, gameResetNStart
            //
            //다음 레벨시 stopGameNBattleSending 62 61
        },

        ////////////////////////////////////Socket 요청들/////////////////////////////////////////////////
        ////////////////////////////////////Socket 요청들/////////////////////////////////////////////////
        ////////////////////////////////////Socket 요청들////////////////ss/////////////////////////////////
        ///////////////동작 순으로 배치
        //13. 가이드모달 시작시 필요 여부 확인 통신
        checkNeedGuideModalSending() {
            console.log('checkNeedGuideModalSending');
            this.stompClient.send(
                '/pub/game/jump/modal/checker',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    type: 13,
                })
            );
        },
        //0.게임 방 입장 : 정보 구독 및 유저 정보 전송
        onConnected() {
            //0. 초기 정보 요청
            console.log('==============this room code is ' + this.getRoomId);

            // 구독
            this.stompClient.subscribe('/game/jumpgame/' + this.getRoomId, this.onMessageReceived);
            this.checkNeedGuideModalSending(); //해당 방에 가이드 모달이 필요한지 type 12
            // db 정보 요청 (master act)
            //0.mount : 마스터의 룸, 게임 정보 요청
            if (this.masterKeyFlag) {
                this.stompClient.send(
                    '/pub/game/jump/enter/reqInfoRoomNGame',
                    {},
                    JSON.stringify({
                        roomId: this.getRoomId,
                        type: 0, //정보요청 타입
                    })
                );
            }
            // 11.db 등록 요청 (user act)
            //11. 유저(방장을 제외한 모든 참여자) 등록 및 송출
            else {
                this.stompClient.send(
                    '/pub/game/jump/enter/reg/user',
                    {},
                    JSON.stringify({
                        roomId: this.getRoomId,
                        participantId: this.getUser.id,
                        participantName: this.getUser.name,
                        teamNo: this.getUser.teamNo,
                        //sdfdasdasd
                        type: 11, //정보요청 타입
                    })
                );
            }
            //마스터 새로고침시, 입장 시 플레이어도 새로고침
            if (this.masterKeyFlag) {
                //마스터가 생성시 나머지 새로고침 요청
                this.reloadingPlay();
            }
        },
        //12. 플레이어로 등록 및 송출
        playerRegSending() {
            this.stompClient.send(
                '/pub/game/jump/enter/play',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    participantId: this.getUser.id,
                    participantName: this.getUser.name,
                    beUserPresent1: this.userPresent1,
                    beUserPresent2: this.userPresent2,
                    teamNo: this.getUser.teamNo,
                    type: 12, //정보요청 타입
                })
            );
        },

        //1. 게임 방 퇴장 소켓 연결 해제 및 게임 방 유저 정보 삭제
        onDisconnect() {
            this.stompClient.send(
                '/pub/game/jump/exit',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    participantId: this.getUser.id,
                    participantName: this.getUser.name,
                    drawFlag: false,
                })
            );

            if (this.userPlayIdx != -1) {
                if (this.teamOneOrTwo == 1) {
                    for (let i = 0; i < this.users1.length; i++) {
                        if (this.users1[i].userId == this.getUser.name) {
                            this.userPresent1[i] = false; //참가 확인 어레이, 플레이어 값
                            this.playerRegSending();
                        }
                    }
                }
                if (this.teamOneOrTwo == 2) {
                    for (let i = 0; i < this.users2.length; i++) {
                        if (this.users2[i].userId == this.getUser.name) {
                            this.userPresent2[i] = false; //참가 확인 어레이, 플레이어 값
                            this.playerRegSending();
                        }
                    }
                }
            }
            // this.stompClient.disconnect();
            // this.$router.push('/room/' + this.getRoomId);
        },
        //4. close modal
        closeGuideModalReqSending() {
            if (this.masterKeyFlag) {
                console.log('41 ' + this.getRoomId);
                this.stompClient.send(
                    '/pub/game/jump/closemodal',
                    {},
                    JSON.stringify({
                        roomId: this.getRoomId,
                        type: 41, //정보요청 타입
                    })
                );
            }
        },
        closeStartModalReqSending() {
            if (this.masterKeyFlag) {
                console.log('42 ' + this.getRoomId);
                this.stompClient.send(
                    '/pub/game/jump/closemodal',
                    {},
                    JSON.stringify({
                        roomId: this.getRoomId,
                        type: 42, //정보요청 타입
                    })
                );
            }
        },
        closeRoundModalReqSending() {
            if (this.masterKeyFlag) {
                console.log('43');
                console.log('43');
                console.log('43');
                console.log('43');
                this.stompClient.send(
                    '/pub/game/jump/closemodal',
                    {},
                    JSON.stringify({
                        roomId: this.getRoomId,
                        type: 43, //정보요청 타입
                    })
                );
            }
        },
        openRoundModalReq() {
            if (this.masterKeyFlag) {
                console.log('44 재시작 전체 전송');
                console.log('44 재시작 전체 전송');
                console.log('44 재시작 전체 전송');
                console.log('44 재시작 전체 전송');

                this.stompClient.send(
                    '/pub/game/jump/closemodal',
                    {},
                    JSON.stringify({
                        roomId: this.getRoomId,
                        type: 44, //정보요청 타입
                    })
                );
            }
        },

        // //1. 방 세팅, 장애물 생성, 새 유저 입장, 정지 시 유저에게 송출
        // broadcastToUser() {
        //     this.stompClient.send(
        //         '/pub/game/jump/broc/base',
        //         {},
        //         JSON.stringify({
        //             roomId: this.getRoomId,
        //             beUserPresent1: this.userPresent1,
        //             beUserPresent2: this.userPresent2,
        //             obstacleflag: this.drawObFlag,
        //             xbArr: this.xArr,
        //             xbArrType: this.xArrType,
        //             beGameStopFlag: true,
        //             type: 1, //정보요청 타입
        //         })
        //     );
        // },

        // //5.입장
        // entering() {
        //     console.log('=======entering');
        //     this.stompClient.send(
        //         '/pub/game/jump/enter',
        //         {},
        //         JSON.stringify({
        //             roomId: this.getRoomId,
        //             beUserPresent1: this.userPresent1,
        //             beUserPresent2: this.userPresent2,
        //             type: 5,
        //             lastEntering: this.getUser.name,
        //         })
        //     );
        // },
        ///////////////////////////////////////////////////
        //3. 재시작 => 마스터 화면은 초기화, 플레이어는 재 접속
        reloading() {
            console.log('=======sending reloading fuc');
            this.reloadFlag = true;
            this.obstacleflag = false;
            this.receivedArr = [];
            this.receivedArrType = [];
            this.xArr = [];
            this.xbArrType = [];
            document.getElementById('ssazipbg').style.animationPlayState = 'paused';
            document.getElementById('ssazipbg2').style.animationPlayState = 'paused';
            if (!this.animationOnFlag) {
                console.log('============re animation');
                this.receivedGameStopFlag = false;
                this.drawSsazipgameStart();
            }
            // this.$router.go();

            this.stompClient.send(
                '/pub/game/jump/data',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    beUserPresent1: this.userPresent1,
                    beUserPresent2: this.userPresent2,
                    reloadflag: this.reloadFlag,
                    type: 3,
                })
            );
        },
        // 3.마스터가 새로고침 누를때 플레이어 재 접속
        reloadingPlay() {
            this.reloadFlag = true;
            this.stompClient.send(
                '/pub/game/jump/data',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    beUserPresent1: this.userPresent1,
                    beUserPresent2: this.userPresent2,
                    reloadflag: this.reloadFlag,
                    obstacleflag: false,
                    type: 3,
                })
            );
        },
        ///////////////////////////////////
        //2. 점프 명령어 전송
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
        /////////////////////////////
        //4. 장애물 송신
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
        //////////////////////////////////////
        //6. 게임 정지 by master
        stopGameSendingByMaster() {
            console.log('=======stopGameSendingByMaster');
            //4초 뒤 게임 재시작
            setTimeout(() => {
                console.log('re start');
                this.openRoundModalReq();
            }, 4000);

            this.stompClient.send(
                '/pub/game/jump/stop',
                {},
                JSON.stringify({
                    roomId: this.getRoomId,
                    // beGameStopFlag: this.gameStopFlag,
                    beGameStopFlag: true,
                    loser: this.loser,
                    loseTeam: this.loseTeam,
                    nowRoundNum: this.nowRoundNum,
                    gameScore1: this.gameScore1,
                    gameScore2: this.gameScore2,
                    type: 6,
                })
            );
        },
        //61. 게임 정지 다음 팀 간 배틀 시작 혹은 결산
        stopGameNBattleSendingByMaster() {
            console.log('배틀 중지 다음 게임 탐색');
            console.log(this.remainRound);
            console.log(this.nextRemainRound);
            console.log(this.teamOrder);
            console.log(this.teamOrderNext);

            //공통 초기화
            this.obstacleflag = false;
            this.nowRoundNum = 1;
            this.gameScore1 = 0;
            this.gameScore2 = 0;
            //다음 레벨 (강) 팀 추가
            let orderNext = this.room.teamOrderNext;
            if (this.loseTeam == 2) {
                orderNext.push(this.battleTeam1);
            } else {
                orderNext.push(this.battleTeam2);
            }
            console.log('=======stopGameNBattleSendingByMaster');
            // let isFinalGameFlag=false;

            //Next 레벨  강( 현 종료된 배틀은 현 레벨 마지막 배틀 )
            if (this.remainRound != this.nextRemainRound) {
                //next level
                //게임 종료 상황
                if (this.nextRemainRound == 0) {
                    console.log('게임종료');
                    // isFinalGameFlag=true;
                    //더 이상 다음 레벨은 없습니다.
                    //결산 배열 제작
                    let arr = [];
                    if (this.loseTeam == 2) {
                        //왼쪽팀 우승 일때
                        if (this.battleTeam1 != 20) arr.push(this.battleTeam1);
                        if (this.battleTeam2 != 20) arr.push(this.battleTeam2);
                    } else if (this.loseTeam == 1) {
                        if (this.battleTeam2 != 20) arr.push(this.battleTeam2);
                        if (this.battleTeam1 != 20) arr.push(this.battleTeam1);
                    }
                    console.log(this.teamOrder);
                    console.log(this.teamOrderNext); //
                    for (let i = 0; i < this.teamOrder.length; i++) {
                        let num = this.teamOrder[i];
                        if (this.battleTeam1 == num) continue;
                        if (this.battleTeam2 == num) continue;
                        arr.push(num);
                    }
                    this.finalScore = arr;
                    this.stompClient.send(
                        '/pub/game/jump/stopbattle',
                        {},
                        JSON.stringify({
                            roomId: this.getRoomId,
                            beGameStopFlag: true,
                            loser: this.loser,
                            loseTeam: this.loseTeam,
                            nowRoundNum: this.nowRoundNum,
                            gameScore1: this.gameScore1,
                            gameScore2: this.gameScore2,
                            //값 변경사항
                            finalScore: this.finalScore,
                            type: 62, //종료 타입
                        })
                    );
                    return;
                }
                //다음 게임 시작, 값 변경 및 저장 필요, 곧 진행 할 다음 배틀은 다른 레벨 입니다
                else {
                    if (orderNext.length % 2 == 1) {
                        orderNext.push(20); //부전승을 위한 봇 추가
                    } //넥스트 팀 순서 설계완료
                    // this.nextRemainRound = orderNext.length;
                    // //결산용
                    // if (this.nextRemainRound == 4) {
                    //     this.finalScore = orderNext;
                    // }
                    let nn = orderNext;
                    if (this.teamOrderNext.length == 2) {
                        //다음은 결승
                        nn = [];
                    }
                    // else{
                    // nn=
                    // }

                    this.stompClient.send(
                        '/pub/game/jump/stopbattle',
                        {},
                        JSON.stringify({
                            roomId: this.getRoomId,
                            beGameStopFlag: true,
                            loser: this.loser,
                            loseTeam: this.loseTeam,
                            nowRoundNum: this.nowRoundNum,
                            gameScore1: this.gameScore1,
                            gameScore2: this.gameScore2,
                            //값 변경사항
                            teamIdx1: 0,
                            teamIdx2: 1,
                            teamOrder: orderNext,
                            teamOrderNext: nn,
                            finalScore: this.finalScore,
                            type: 61,
                        })
                    );
                }
            } else {
                //same level, 인덱스, 다음 레벨 추가 값 변경 및 저장 필요 (다음 배틀은 아직 현 레벨에서 배틀이 입니다.)
                //다다음이 다른 레벨일 경우(다음 배틀이 현 레벨에서 마지막 배틀 입니다)
                // let nnextRemainR = this.nextRemainRound;
                // if (this.room.teamIdx2 + 1 == this.remainRound - 2) {
                //     //현 레벨 남은 팀은 2개
                //     nnextRemainR = this.remainRound / 2;
                //     if (this.remainRound % 2 == 1) {
                //         nnextRemainR++;
                //     }
                //     // this.nextBattleTeam2=30;//?인 팀
                // } else {
                //     //현 레벨에 남은 팀은 2개 이상
                //     nnextRemainR = this.nextRemainRound;
                // }
                this.stompClient.send(
                    '/pub/game/jump/stopbattle',
                    {},
                    JSON.stringify({
                        roomId: this.getRoomId,
                        beGameStopFlag: true,
                        loser: this.loser,
                        loseTeam: this.loseTeam,
                        nowRoundNum: this.nowRoundNum,
                        gameScore1: this.gameScore1,
                        gameScore2: this.gameScore2,
                        //값 변경사항
                        teamOrder: this.teamOrder,
                        teamOrderNext: orderNext,
                        teamIdx1: this.room.teamIdx1 + 2,
                        teamIdx2: this.room.teamIdx2 + 2,
                        // remainRound: this.nextRemainRound,
                        // nextRemainRound: nnextRemainR,
                        type: 61,
                    })
                );
            }
            //다음 게임 시작
            setTimeout(() => {
                console.log('re start');
                this.openRoundModalReq();
            }, 4000);
        },

        ////////////////////// 메세지 수신 /////////////////////
        ////////////////////// 메세지 수신 ///////////////////
        ////////////////////// 메세지 수신 ///////////////////

        ////////////////////// 메세지 수신 ///////////////////
        ////////////////////// 메세지 수신 ///////////////////
        ////////////////////// 메세지 수신 ///////////////////
        onMessageReceived(payload) {
            console.log(payload)
            if(payload.body=="exit"){
                console.log("eeeeeeeeeee");
                this.rankModalOpenFlag = true;
                return;
            }
            let info = JSON.parse(payload.body);
            console.log('======got mes=========');
            console.log(info);
            console.log(info.type);

            //13. 가이드모달 시작시 필요 여부 확인 통신
            if (info.type == 13) {
                console.log('가이드라인');
                this.guideModalOpenFlag = info.guideModalFlag;
                console.log(this.guideModalOpenFlag);
            }
            //0. 마스터의 정보 수신 (only master)
            if (info.type == 0 && info.host == this.getUser.name) {
                console.log('======마스터 got : 방, 게임 정보 수신=========');
                this.room = info;
                console.log(this.room);
                //기본 정보
                // this.receivedGameStopFlag = info.beGameStopFlag;
                this.userPresent1 = info.beUserPresent1;
                this.userPresent2 = info.beUserPresent2;
                this.finalScore = info.finalScore;
                this.gameScore1 = info.gameScore1;
                this.gameScore2 = info.gameScore2;
                this.hostId = this.room.host;
                // this.guideModalOpenFlag = info.guideModalFlag;
                // this.startModalOpenFlag = info.startModalFlag;
                // this.roundModalOpenFlag = info.roundModalFlag;
                // this.loser = info.loser;
                // this.loseTeam = info.loseTeam;
                // this.nowRoundNum = info.nowRoundNum;
                // this.obstacleflag = info.obstacleflag;
                this.battleTeam1 = info.teamOrder[info.teamIdx1];
                this.battleTeam2 = info.teamOrder[info.teamIdx2];
                this.remainRound = info.remainRound;
                this.nextRemainRound = info.nextRemainRound;
                this.teamOrder = info.teamOrder;
                // this.teamOrderNext = info.teamOrderNext;
                this.teamIdx1 = info.teamIdx1;
                this.teamIdx2 = info.teamIdx2;
                //다음 경기 출력을 위한 계산
                //다음판이 다음 강 일때
                if (this.remainRound != this.nextRemainRound) {
                    if (this.nextRemainRound == 1) {
                        //현재 판이 결승
                        //결산 호출
                        this.nextBattleTeam1 = 20;
                        this.nextBattleTeam2 = 20;
                    } else {
                        //다음 강의 번호
                        this.nextBattleTeam1 = info.teamOrderNext[0];
                        if (info.teamOrderNext.length == 1) {
                            //현재가 3강이라면 아직 이라
                            this.nextBattleTeam2 = 30;
                        } else {
                            this.nextBattleTeam2 = info.teamOrderNext[1];
                        }
                    }
                }
                //다음 판이 현재 강 일때
                else {
                    if (this.teamOrder.length > info.teamIdx1 + 2) {
                        this.nextBattleTeam1 = info.teamOrder[info.teamIdx1 + 2];
                    }
                    if (this.teamOrder.length > info.teamIdx2 + 2) {
                        this.nextBattleTeam2 = info.teamOrder[info.teamIdx2 + 2];
                    }
                }
                // this.setUsersByMaster();
                this.setUsers();
            }
            //11. 유저의 등록 정보 수신
            if (info.type == 11) {
                console.log('======got : 등록 후 방 정보 수신=========');
                console.log(this.room);
                this.room = info;
                // this.receivedGameStopFlag = info.beGameStopFlag;

                this.userPresent1 = info.beUserPresent1;
                this.userPresent2 = info.beUserPresent2;
                this.finalScore = info.finalScore;
                this.gameScore1 = info.gameScore1;
                this.gameScore2 = info.gameScore2;
                this.hostId = this.room.host;
                // this.guideModalOpenFlag = info.guideModalFlag;
                // this.startModalOpenFlag = info.startModalFlag;
                // this.roundModalOpenFlag = info.roundModalFlag;
                this.loser = info.loser;
                this.loseTeam = info.loseTeam;
                this.nowRoundNum = info.nowRoundNum;
                this.obstacleflag = info.obstacleflag;
                this.battleTeam1 = info.teamOrder[info.teamIdx1];
                this.battleTeam2 = info.teamOrder[info.teamIdx2];
                this.remainRound = info.remainRound;
                this.nextRemainRound = info.nextRemainRound;
                this.teamOrder = info.teamOrder;
                this.teamOrderNext = info.teamOrderNext;
                this.teamIdx1 = info.teamIdx1;
                this.teamIdx2 = info.teamIdx2;

                //다음 경기 출력을 위한 계산
                //다음판이 다음 강 일때
                if (this.remainRound != this.nextRemainRound) {
                    if (this.nextRemainRound == 1) {
                        //현재 판이 결승
                        //결산 호출
                        this.nextBattleTeam1 = 20;
                        this.nextBattleTeam2 = 20;
                    } else {
                        //다음 강의 번호
                        this.nextBattleTeam1 = info.teamOrderNext[0];
                        if (info.teamOrderNext.length == 1) {
                            //현재가 3강이라면 아직 이라
                            this.nextBattleTeam2 = 30;
                        } else {
                            this.nextBattleTeam2 = info.teamOrderNext[1];
                        }
                    }
                }
                //다음 판이 현재 강 일때
                else {
                    if (this.teamOrder.length > info.teamIdx1 + 2) {
                        this.nextBattleTeam1 = info.teamOrder[info.teamIdx1 + 2];
                    }
                    if (this.teamOrder.length > info.teamIdx2 + 2) {
                        this.nextBattleTeam2 = info.teamOrder[info.teamIdx2 + 2];
                    }
                }
                this.setUsers();
            }
            //12. 플레이어 존재 정보 수신
            if (info.type == 12) {
                console.log('======got :플레이어 존재 정보 수신=========');
                this.room = info;
                console.log(this.room);
                this.userPresent1 = info.beUserPresent1;
                this.userPresent2 = info.beUserPresent2;
                this.finalScore = info.finalScore;
                this.gameScore1 = info.gameScore1;
                this.gameScore2 = info.gameScore2;
                this.hostId = this.room.host;
                // this.guideModalOpenFlag = info.guideModalFlag;
                // this.startModalOpenFlag = info.startModalFlag;
                // this.roundModalOpenFlag = info.roundModalFlag;
                this.loser = info.loser;
                this.loseTeam = info.loseTeam;
                this.nowRoundNum = info.nowRoundNum;
                this.obstacleflag = info.obstacleflag;
                this.battleTeam1 = info.teamOrder[info.teamIdx1];
                this.battleTeam2 = info.teamOrder[info.teamIdx2];
                this.remainRound = info.remainRound;
                this.nextRemainRound = info.nextRemainRound;
                this.teamOrder = info.teamOrder;
                this.teamOrderNext = info.teamOrderNext;
                this.teamIdx1 = info.teamIdx1;
                this.teamIdx2 = info.teamIdx2;
                //다음 경기 출력을 위한 계산
                //다음판이 다음 강 일때
                if (this.remainRound != this.nextRemainRound) {
                    if (this.nextRemainRound == 1) {
                        //현재 판이 결승
                        //결산 호출
                        this.nextBattleTeam1 = 20;
                        this.nextBattleTeam2 = 20;
                    } else {
                        //다음 강의 번호
                        this.nextBattleTeam1 = info.teamOrderNext[0];
                        if (info.teamOrderNext.length == 1) {
                            //현재가 3강이라면 아직 이라
                            this.nextBattleTeam2 = 30;
                        } else {
                            this.nextBattleTeam2 = info.teamOrderNext[1];
                        }
                    }
                }
                //다음 판이 현재 강 일때
                else {
                                    if (this.teamOrder.length > info.teamIdx1 + 2) {
                    this.nextBattleTeam1 = info.teamOrder[info.teamIdx1 + 2];
                }
                if (this.teamOrder.length > info.teamIdx2 + 2) {
                    this.nextBattleTeam2 = info.teamOrder[info.teamIdx2 + 2];
                }
                }
                this.drawSsazip();
            }
            //1. 방장 종료
            if (info == 'exit') {
                //랭크 표현
                this.rankModalOpenFlag = true;
                //방장 퇴장
                
                // this.onDisconnect();
                swal({
                    // className:'alert',
                    title: '방장이 퇴장하여 게임이 종료됩니다!',
                    icon: "/img/ssazip-logo.png",
                    buttons: {
                    text: '확인',
                },
                })
                // 모든 참가자 내보내기
                // this.$router.push('/');
                // this.$router.push('/room/' + this.getRoomId).catch(() => {});

                return;
            }
            if (info.type == 400) {
                //방장 퇴장
                this.onDisconnect();
                swal({
                    // className:'alert',
                    title: '알 수 없는 오류로 게임이 종료됩니다!',
                    icon: "/img/ssazip-logo.png",
                    buttons: {
                    text: '확인',
                },
                })
                // 모든 참가자 내보내기
                        this.$router.push('/room/' + this.getRoomId).catch(() => {});

                // this.$router.push('/');
                return;
            }

            //4. close modal
            if (info.type == 41) {
                //guide close
                this.guideModalOpenFlag = false;
                this.startModalOpenFlag = true;
                return;
            }
            //start close
            if (info.type == 42) {
                this.startModalOpenFlag = false;
                this.gameResetNStart();
                // this.roundModal();
                return;
            }
            //round close
            if (info.type == 43) {
                console.log('4333333333333333333333333333333');
                console.log('4333333333333333333333333333333');
                console.log('4333333333333333333333333333333');
                console.log('4333333333333333333333333333333');

                this.roundModalOpenFlag = false;
                this.showRound();
                return;
            }
            //round open
            if (info.type == 44) {
                // this.roundModalOpenFlag = true;
                // this.showRound();
                this.gameResetNStart();
                return;
            }
            //3.리로드
            if (info.reloadflag && info.type == 3) {
                this.reloadFlag = false;
                if (!this.masterKeyFlag) this.$router.go();
                // this.userPresent1 = info.beUserPresent1;
                // this.userPresent2 = info.beUserPresent2;
                // this.drawSsazip();
                return;
            }

            //2. 점핑데이터
            if (info.type == 2) {
                //점프
                this.receivedJumpArr1 = info.jumpArr1;
                this.receivedJumpArr2 = info.jumpArr2;
                if (this.receivedJumpArr1 != null) {
                    for (let i = 0; i < this.receivedJumpArr1.length; i++) {
                        if (this.receivedJumpArr1[i]) {
                            //점프 명령
                            this.users1[i].jump = true;
                            //초기화
                            this.receivedJumpArr1[i] = false;
                        }
                        // this.users1[i].jump = this.jumpsss1[i];
                    }
                }
                if (this.receivedJumpArr2 != null) {
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
                return;
            }

            //4. 장애물
            if (info.type == 4) {
                //장애물 생성
                this.receivedArr = info.xbArr;
                this.receivedArrType = info.xbArrType;
                return;
            }
            // if (info.obstacleflag && !this.drawFlag) {
            //     this.drawFlag = true;
            // }

            //6.게임 알반 정지
            if (info.type == 6) {
                this.checkCollisionOnFlag = false;
                this.receivedGameStopFlag = info.beGameStopFlag;
                this.loser = info.loser;
                this.loseTeam = info.loseTeam;
                if (this.loseTeam == 1) {
                    this.win1 = false;
                    this.lose1 = true;
                } else {
                    this.win1 = true;
                    this.lose1 = false;
                }
                this.nowRoundNum = info.nowRoundNum;
                this.gameScore1 = info.gameScore1;
                this.gameScore2 = info.gameScore2;
                return;
            }
            // 61 62 게임 정지 다음 단계
            if (info.type == 62) {
                this.checkCollisionOnFlag = false;

                //모든 게임 종료, 결산
                console.log('결산 명령 수신 완료');
                this.receivedGameStopFlag = info.beGameStopFlag;

                // this.userPresent1 = info.beUserPresent1;
                // this.userPresent2 = info.beUserPresent2;
                this.finalScore = info.finalScore;
                this.gameScore1 = info.gameScore1;
                this.gameScore2 = info.gameScore2;
                this.hostId = this.room.host;
                // this.guideModalOpenFlag = info.guideModalFlag;
                // this.startModalOpenFlag = info.startModalFlag;
                // this.roundModalOpenFlag = info.roundModalFlag;
                this.loser = info.loser;
                this.loseTeam = info.loseTeam;
                this.nowRoundNum = info.nowRoundNum;
                // this.obstacleflag = info.obstacleflag;
                // this.battleTeam1 = info.teamOrder[info.teamIdx1];
                // this.battleTeam2 = info.teamOrder[info.teamIdx2];
                this.remainRound = info.remainRound;
                this.nextRemainRound = info.nextRemainRound;
                this.teamOrder = info.teamOrder;
                this.teamOrderNext = info.teamOrderNext;
                this.teamIdx1 = info.teamIdx1;
                this.teamIdx2 = info.teamIdx2;
                // if (this.teamOrder.length > 2) {
                //     this.nextBattleTeam1 = info.teamOrder[info.teamIdx1 + 2];
                //     this.nextBattleTeam2 = info.teamOrder[info.teamIdx1 + 2];
                // }
                if (this.loseTeam == 1) {
                    this.win1 = false;
                    this.lose1 = true;
                } else {
                    this.win1 = true;
                    this.lose1 = false;
                }

                // this.rankModalOpenFlag= true;
                console.log('랭킹은 다음과 같습니다.');
                for (let i = 0; i < this.finalScore.length; i++) {
                    console.log(this.finalScore[i]);
                }
                setTimeout(() => {
                    console.log('will end');
                    console.log('will end');
                    console.log('will end');
                    console.log('will end');

                    this.onDisconnect();
                }, 2000);
            }
            if (info.type == 61) {
                this.checkCollisionOnFlag = false;

                //다음 팀간 배틀
                this.receivedGameStopFlag = info.beGameStopFlag;

                this.userPresent1 = info.beUserPresent1;
                this.userPresent2 = info.beUserPresent2;
                this.finalScore = info.finalScore;
                this.gameScore1 = info.gameScore1;
                this.gameScore2 = info.gameScore2;
                this.hostId = this.room.host;
                // this.guideModalOpenFlag = info.guideModalFlag;
                // this.startModalOpenFlag = info.startModalFlag;
                // this.roundModalOpenFlag = info.roundModalFlag;
                this.loser = info.loser;
                this.loseTeam = info.loseTeam;
                this.nowRoundNum = info.nowRoundNum;
                this.obstacleflag = info.obstacleflag;
                this.teamOrder = info.teamOrder;
                this.teamOrderNext = info.teamOrderNext;
                this.battleTeam1 = info.teamOrder[info.teamIdx1];
                this.battleTeam2 = info.teamOrder[info.teamIdx2];
                this.remainRound = info.teamOrder.length;
                // this.nextRemainRound = info.nextRemainRound;
                this.teamIdx1 = info.teamIdx1;
                this.teamIdx2 = info.teamIdx2;
                //현 위치 파악
                if (this.teamIdx1 + 2 < this.team.length) {
                    this.nextRemainRound = this.remainRound;
                    this.nextBattleTeam1 = this.teamOrder[info.teamIdx1 + 2];
                } else if (this.remainRound == 2) {
                    this.nextRemainRound = 0;
                    this.nextBattleTeam1 = 20;
                } else {
                    this.nextRemainRound = this.teamOrderNext.length / 2;
                    if (this.nextRemainRound % 2 == 1) this.nextRemainRound++;
                    this.nextBattleTeam1 = this.teamOrderNext[0];
                }
                if (this.teamIdx2 + 2 < this.team.length) {
                    this.nextRemainRound = this.remainRound;
                    this.nextBattleTeam2 = this.teamOrder[info.teamIdx2 + 2];
                } else if (this.remainRound == 2) {
                    this.nextRemainRound = 0;
                    this.nextBattleTeam2 = 20;
                } else {
                    this.nextRemainRound = this.teamOrderNext.length / 2;
                    if (this.nextRemainRound % 2 == 1) this.nextRemainRound++;
                    if (this.teamOrderNext.length == 1) this.nextBattleTeam2 = this.nextBattleTeam2 = 30;
                    //?대신
                    else this.nextBattleTeam2 = this.teamOrderNext[1];
                }
                if (this.loseTeam == 1) {
                    this.win1 = false;
                    this.lose1 = true;
                } else {
                    this.win1 = true;
                    this.lose1 = false;
                }
            }
            // //1.정보 요청 수신시 ( 마스터만 )
            // if (info.type == 1 && this.masterKeyFlag) {
            //     console.log('======got mes 마스터의 정보 요청에 대한 수신=========');
            //     this.sendState();
            //     return;
            // }
            //1. 방장과 db 로부터의 데이터 값 (유저만)
            // if (info.type == 1 && !this.masterKeyFlag) {
            //     console.log('======got mes 방장과 db 로부터의 데이터 값=========');
            //     if (info.lastEntering == this.getUser.name || this.isRegistryed) {
            //         //유저 본인이 등록 여부 파악
            //         this.isRegistryed = true;
            //         this.userPresent1 = info.beUserPresent1;
            //         this.userPresent2 = info.beUserPresent2;
            //         //플레이어 여부 확인
            //         for (let i = 0; i < this.users1.length; i++) {
            //             if (this.users1[i].userId == this.getUser.name) {
            //                 this.userPresent1[i] = true; //참가 확인 어레이
            //                 this.teamIdx = 1; //팀 선정
            //                 // 유저 개인 메세지
            //                 let idx = 1;
            //                 for (let i = 0; i < this.userPresent1.length; i++) {
            //                     if (this.userPresent1[i]) idx++;
            //                 }
            //                 console.log('1팀의 ' + idx + '번째 유저입니다');
            //                 this.entering();
            //             }
            //         }
            //         if (this.teamIdx == 2) {
            //             for (let i = 0; i < this.users2.length; i++) {
            //                 if (this.users2[i].userId == this.getUser.name) {
            //                     this.userPresent2[i] = true; //참가 확인 어레이
            //                     this.teamIdx = 2; //팀 선정
            //                     this.entering();
            //                     // 유저 개인 메세지
            //                     let idx = 0;
            //                     this.userPresent2.forEach((a) => {
            //                         if (a) idx++;
            //                     });
            //                     console.log('2팀의 ' + idx + '번째 인덱스 유저입니다');
            //                 }
            //             }
            //         }
            //     } else {
            //         this.onConnected();
            //     }
            //     return;
            // }
            // //7.유저 기존 상태 수신 (입장 전 과정)
            // console.log('this.enteredFlag 입장여부');
            // console.log(this.enteredFlag);
            // if (info.type == 7 && !this.enteredFlag) {
            //     console.log('======got mes 기존 상태 마스터로부터 수신=========');
            //     this.enteredFlag = true;
            //     this.userPresent1 = info.beUserPresent1;
            //     this.userPresent2 = info.beUserPresent2;
            //     this.obstacleflag = info.obstacleflag;
            //     //입장자에 대한 플레이어 조건 확인 절차
            //     for (let i = 0; i < this.users1.length; i++) {
            //         if (this.users1[i].userId == this.getUser.name) {
            //             this.userPresent1[i] = true; //참가 확인 어레이
            //             this.teamIdx = 1; //팀 선정

            //             // 유저 개인 메세지
            //             let idx = 1;
            //             for (let i = 0; i < this.userPresent1.length; i++) {
            //                 if (this.userPresent1[i]) idx++;
            //             }
            //             console.log('1팀의 ' + idx + '번째 유저입니다');
            //             this.entering();
            //         }
            //     }
            //     if (this.teamIdx == 2) {
            //         for (let i = 0; i < this.users2.length; i++) {
            //             if (this.users2[i].userId == this.getUser.name) {
            //                 this.userPresent2[i] = true; //참가 확인 어레이
            //                 this.teamIdx = 2; //팀 선정
            //                 this.entering();
            //                 // 유저 개인 메세지
            //                 let idx = 0;
            //                 this.userPresent2.forEach((a) => {
            //                     if (a) idx++;
            //                 });
            //                 console.log('2팀의 ' + idx + '번째 인덱스 유저입니다');
            //             }
            //         }
            //     }
            // }

            // //5. 새로운 유저 입장
            // if (info.type == 5) {
            //     console.log('새로운 유저 입장 확인 receive type5 ');
            //     console.log(info.lastEntering);
            //     this.userPresent1 = info.beUserPresent1;
            //     this.userPresent2 = info.beUserPresent2;
            //     this.drawSsazip();
            // }

            // if (info.type == 9) {
            //     //마스터만 갱신
            //     // 팀 벵이스먼트 기준으로 users 재 생성
            //     this.onConnected();
            //     // this.onDisconnect();
            //     // alert('방장이 퇴장하여 게임이 종료됩니다!');
            //     // 모든 참가자 내보내기
            //     // this.$router.push('/room');
            //     return;
            // }

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

        onError() {},
        // 게임 방 퇴장 소켓 연결 해제 및 게임 방 유저 정보 삭제
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

    //////////////////////////////////환경설정//////////////////////////////////////
    //////////////////////////////////환경설정//////////////////////////////////////
    //////////////////////////////////환경설정//////////////////////////////////////
    created() {
        console.log('=============created');
        history.pushState(null, null, location.href);
        window.onpopstate = () => {
            history.go(1);
            swal({
                // className:'alert',
                title: "게임 내에서는 뒤로가기가 불가합니다.",
                icon: "/img/ssazip-logo.png",
                buttons: {
                text: '확인',
                },
            })
        }
        // // 소켓 연결
        // this.stompClient = socketConnect(this.onConnected, this.onError);
        // 방정보 초기화
        // this.room.id = this.getRoomId;
        this.masterKeyFlag = this.getIsLogin;
        this.userId = this.getUser.name;
        this.masterKeyFlag = this.getIsLogin;
    },
    mounted() {
        console.log('=============mounted');
        console.log('==========start i am ' + this.getUser.name);
        if (this.masterKeyFlag) {
            this.guideModalOpenFlag = true;
        }
        // 소켓 연결
        //0.게임 방 입장 : 정보 구독 및 유저 정보 전송
        this.stompClient = socketConnect(this.onConnected, this.onError);

        // 캔버스 반복 생성 시작
        if (!this.startFlag) {
            this.startFlag = true;
            this.drawSsazipgameStart();
        }

        //플레이어 조작
        document.addEventListener('keydown', (e) => {
            //장애물 생성 요청
            if (e.code === 'KeyA' && !this.obstacleflag && this.masterKeyFlag) {
                this.obstacleflag = true;
                this.showResult = false;
                document.getElementById('ssazipbg').style.animationPlayState = 'running';
                document.getElementById('ssazipbg2').style.animationPlayState = 'running';
                console.log('a가 눌렸다 게임을 시작한다');
            }
            //새로 고침
            if (e.code === 'Enter') {
                if (this.getIsLogin == true) {
                    console.log('재시작');
                    this.reloading(); //마스터만 재 시작 가능
                }
            }
            //착지해야만 점프가능

            if (this.startFlag && this.userPlayIdx != -1) {
                if (this.teamOneOrTwo == 1) {
                    if (this.dinos1[this.userPlayIdx].y == 200) {
                        if (e.code === 'Space') {
                            console.log('space pushed team1');
                            this.jumpSendArr1[this.userIdx] = true;
                            this.jumping();
                            this.jumpSendArr1[this.userIdx] = false;
                        }
                    }
                } else {
                    if (this.dinos2[this.userPlayIdx].y == 200) {
                        if (e.code === 'Space') {
                            console.log('space pushed team2 ' + this.userIdx);
                            this.jumpSendArr2[this.userIdx] = true;
                            this.jumping();
                            this.jumpSendArr2[this.userIdx] = false;
                        }
                    }
                }
            }
        });

        // window.addEventListener('beforeunload', this.unLoadEvent);
    },
    // beforeUnmount() {
    //     window.removeEventListener('beforeunload', this.unLoadEvent);
    // },
    // watch: {
    //     $route(to, from) {
    //         console.log(to);
    //     },
    // },

    computed: {
        ...mapGetters(['getRoomId']),
        ...mapGetters(['getUser', 'getIsLogin']),
    },
    destroyed() {
        console.log('================destroyed');
        // if (this.masterKeyFlag) {
        //     this.onDisconnectByMaster();
        // } else {
        this.onDisconnect();
        // }
    },
};

///////////////////////////routine memo/////////////////////
///////////////////////////routine memo/////////////////////
///////////////////////////routine memo/////////////////////
//각 숫자는 전송시 넣어지는 타입
//
//(when enter this page)
//created() -> set room.id, masterKeyFlag, userId
//
//mounted() -> onconnected() : subscribe, reload after master, socket connecting( getting room & game info by type 0, getting obstacle & player present by type 1)
//          -> start drawing repeatly
//
//socket 0. request room data, game data
//socket 1. request obstacle, player state to master
//socket 7. response to all from master -> user get state before enter
//socket 5. entering and remake dinos list at all
//
//(when reset by master : key 'enter')
//socket 3. reload(reset) : reset at master, request all player to reEnter
//
//(when reload by master)
//reload(real reload of master) : request reload to all player
//
//(when user jump)
//socket 2. jumping : catch button pushed by player and broadcast
//    catch jumping signal and jump
//
//(when master start making obstacle)
//socket 4.start => button 'a' by master
//
//(when someone crushed obstacle)
//socket 6. stop => to all
//
</script>

<style></style>
