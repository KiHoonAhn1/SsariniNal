<template>
  <div></div>
</template>

<script>
import swal from 'sweetalert';

export default {
  name: 'alert',
  methods: {
    showAlert() {
      swal({
        // className:'alert',
        title: '문구는 여기 넣으세요.',
        buttons: {
          text: '네',
          cancel: '아니요',
        },
      }).then((value) => {
        if (value == 'text') {
          //네 를 눌렀을때 일어날 일은 여기 넣으세요.
        } else {
          //취소를 눌렀을 때 일어날 일은 여기 넣으세요.
        }
      });
    },
  },
};
</script>

<style></style>
