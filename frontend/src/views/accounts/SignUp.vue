<template>
  <div class="signup">
    <img src="@/assets/images/user-background.jpg" alt="background-img" class="background-img" />
    <div>
      <img
        src="@/assets/images/ssazip-signup.png"
        alt="ssazip-signup-img"
        class="ssazip-signup-img"
      />
      <div class="signup-inputs">
        <input
          type="text"
          name="signupName"
          id="signupName"
          placeholder="이름을 입력해주세요"
          v-model="user.name"
        />
        <div class="signupId">
          <input
            type="text"
            name="signupId"
            id="signupId"
            placeholder="아이디를 입력해주세요"
            v-model="user.id"
          />
          <button @click="checkId">중복체크</button>
        </div>
        <input
          type="password"
          name="signupPwd"
          id="signupPwd"
          placeholder="비밀번호를 입력해주세요"
          v-model="user.pass"
        />
        <input
          type="password"
          name="signupRePwd"
          id="signupRePwd"
          placeholder="비밀번호를 재입력해주세요"
          v-model="passCheck"
        />
      </div>
      <div class="signup-buttons">
        <button @click="joinUser">가입</button>
        <router-link to="/login">
          <button>취소</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import '@/css/accounts/signup.css';
import axios from '@/util/http-common.js';
import swal from 'sweetalert';


export default {
  name: 'SignUp',
  data() {
    return {
      user: {
        id: '',
        name: '',
        pass: '',
      },
      passCheck: '',
      checkIdOverlap: true,
    };
  },
  watch: {
    // checkIdOverlap이 true인 상태에서 user.id가 바뀌면 중복체크 무효처리
    'user.id'() {
      this.checkIdOverlap = true;
    },
  },
  methods: {
    // 아이디 중복 체크
    checkId() {
      // 아이디는 최소 3글자 이상, 15글자 이하로 한다.
      if (this.user.id.length < 3 || this.user.id.length > 15) {
        swal({
          // className:'alert',
          title: '아이디는 3글자 이상,',
          text: ' 15글자 이하로 입력해주세요.',
          icon: "/img/ssazip-logo.c407ce8a.png",
          buttons: {
          text: '확인',
        },
        })
        return;
      }
      axios({
        method: 'get',
        url: `/user/checkId`,
        params: {
          id: this.user.id,
        },
      })
        .then((res) => {
          this.checkIdOverlap = res.data;
          if (this.checkIdOverlap) {
              swal({
              // className:'alert',
              title: '아이디가 중복됩니다.',
              icon: "/img/ssazip-logo.c407ce8a.png",
              buttons: {
              text: '확인',
            },
            })
          }
          else {
              swal({
              // className:'alert',
              title: '중복 체크 완료!',
              icon: "/img/ssazip-logo.c407ce8a.png",
              buttons: {
              text: '확인',
            },
            })
          } 
        })
        .catch(() => {});
    },
    // 회원 가입
    joinUser() {
      // 이름은 최소 2글자 이상, 10글자 이하로 한다.
      if (this.user.name.length < 2 || this.user.name.length > 10) {
        swal({
          // className:'alert',
          title: '이름은 2글자 이상,',
          text: ' 10글자 이하로 입력해주세요.',
          icon: "/img/ssazip-logo.c407ce8a.png",
          buttons: {
          text: '확인',
        },
        })
        return;
      }
      // 아이디는 최소 3글자 이상, 15글자 이하로 한다.
      if (this.user.id.length < 3 || this.user.id.length > 15) {
        swal({
          // className:'alert',
          title: '아이디는 3글자 이상',
          text: ' 15글자 이하로 입력해주세요',
          icon: "/img/ssazip-logo.c407ce8a.png",
          buttons: {
          text: '확인',
        },
        })

        return;
      }
      // 아이디는 중복되어서는 안된다.
      if (this.checkIdOverlap) {
        swal({
          // className:'alert',
          title: '아이디 중복 체크를 완료해주세요.',
          icon: "/img/ssazip-logo.c407ce8a.png",
          buttons: {
          text: '확인',
        },
        })
        return;
      }
      // 비밀번호는 최소 2글자 이상, 16글자 이하여야 한다.
      if (this.user.pass.length < 2 || this.user.pass.length > 16) {
        swal({
          // className:'alert',
          title: '비밀번호는 2글자 이상',
          text: ' 16글자 이하로 입력해주세요.',
          icon: "/img/ssazip-logo.c407ce8a.png",
          buttons: {
          text: '확인',
        },
        })
        return;
      }
      if (this.user.pass != this.passCheck) {
        swal({
          // className:'alert',
          title: '비밀번호가 일치하지 않습니다!',
          icon: "/img/ssazip-logo.c407ce8a.png",
          buttons: {
          text: '확인',
        },
        })
        return;
      }
      axios({
        method: 'post',
        url: `/user/signup`,
        data: this.user,
      })
        .then(() => {
          swal({
            // className:'alert',
            title: '회원가입이 완료되었습니다!',
            icon: "/img/ssazip-logo.c407ce8a.png",
            buttons: {
            text: '확인',
          },
          })
          this.$router.push('login');
        })
        .catch(() => {});
    },
  },
};
</script>

<style></style>
