<template>
  <div class="login">
    <img src="@/assets/images/user-background.jpg" alt="background-img" class="background-img" />
    <div>
      <img src="@/assets/images/ssazip-login.png" alt="ssazip-login-img" class="ssazip-login-img" />
      <p></p>
      <input type="text" name="userId" id="userId" placeholder="ID" v-model="user.id" />
      <input
        type="password"
        name="userPassword"
        id="userPassword"
        placeholder="PW"
        v-model="user.pass"
      />
      <div>
        <button class="button-login" @click="baseLogin">로그인</button>
        <button @click="loginCancel" class="button-cancel">취소</button>
      </div>
      <router-link to="/signup">
        <button class="button-signup">회원가입하기</button>
      </router-link>
      <p>소설 로그인</p>
      <div>
        <div id="naverIdLogin" />
        <button @click="kakaoLogin">카카오 로그인</button>
        <button>구글 로그인</button>
      </div>
    </div>
  </div>
</template>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script
  type="text/javascript"
  src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
  charset="utf-8"
></script>
<script>
import '@/css/accounts/login.css';
import axios from '@/util/http-common.js';
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'Login',
  data: () => {
    return {
      key:
        'AAAAO_FZJrDT8XmalCPwIuCAjbIHiDgqCdNL1UevV20cuFH5T8aOzM5LUwxsZn2pYh8dlNfCtSivT5jdnELDbtwnDek',
      user: {
        id: '',
        pass: '',
        name: '',
      },
    };
  },
  created: function() {
    this.checkLogin();
  },
  mounted: function() {
    this.initializeNaverLogin();
  },
  computed: {
    ...mapGetters(['getIsLogin']),
  },
  methods: {
    ...mapActions(['login', 'changeLoginState']),
    // 로그인 여부 체크
    checkLogin() {
      if (this.getIsLogin) {
        this.$router.push('/room');
      }
    },
    // 일반 로그인
    baseLogin() {
      // 아이디를 입력하지 않은 경우
      if (this.user.id.length == 0) {
        alert('아이디를 입력하세요!');
        return;
      }
      // 비밀번호를 입력하지 않은 경우
      if (this.user.pass.length == 0) {
        alert('비밀번호를 입력하세요!');
        return;
      }
      axios({
        method: 'post',
        url: `/user/login`,
        data: this.user,
      })
        .then((res) => {
          let user = res.data;
          // 유저가 존재하지 않는 경우
          if (user == '') {
            alert('아이디나 비밀번호를 확인하세요.');
            return;
          }
          // 존재하는 경우
          // 유저 정보 로컬에 저장
          this.login(user);
          // 로그인 여부 갱신
          this.changeLoginState(true);
          this.$router.push('/room');
        })
        .catch(() => {});
    },
    kakaoLogin: function() {
      Kakao.Auth.login({
        success: function(authObj) {
          Kakao.API.request({
            url: '/v2/user/me',
            success: function(res) {
              console.log(res);
              window.location.replace('http://localhost:8080/room');
            },
            fail: function(err) {
              console.log(err);
            },
          });
        },
        fail: function(err) {
          alert(JSON.stringify(err));
        },
      });
    },
    kakaoLogout: function() {
      console.log(Kakao.Auth.getAccessToken());
      if (Kakao.Auth.getAccessToken()) {
        Kakao.Auth.logout(function() {
          // success: function (response) {
          //   console.log(response)
          // },
          // fail: function (error) {
          //   console.log(error)
          // }
          alert(Kakao.Auth.getAccessToken());
          window.location.replace('http://localhost:8080/');
        });
      }
    },
    loginCancel: function() {
      this.$router.push('/');
    },
    initializeNaverLogin: () => {
      const { naver } = window;
      const naverLogin = new naver.LoginWithNaverId({
        clientId: 'J8ASU80_cvgYfiW0WCqD',
        callbackUrl: 'http://localhost:8080/login',
        isPopup: false,
        loginButton: { color: 'white', type: 1, height: 47 },
        callbackHandle: true,
      });
      naverLogin.init();

      window.addEventListener('load', function() {
        naverLogin.getLoginStatus(function(status) {
          if (status) {
            const email = naverLogin.user.getEmail();
            if (email == undefined || email == undefined) {
              alert('이메일과 이름을 체크해주세요.');
              naverLogin.reprompt();
              return;
            }
            // window.location.replace('http://localhost:8080/login');
          } else {
            alert('로그인 실패');
          }
        });
      });
    },
  },
};
</script>
