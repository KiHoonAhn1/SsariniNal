<template>
  <div class="timer">
    <p id="countdown">{{ this.getMinutes }}:{{ this.getSeconds }}</p>
  </div>
</template>

<script>
export default {
  name: 'Timer',
  props: ['minutes', 'seconds'],
  computed: {
    getSeconds() {
      return this.seconds < 10 ? '0' + this.seconds : this.seconds;
    },
    getMinutes() {
      return this.minutes < 10 ? '0' + this.minutes : this.minutes;
    },
  },
};
</script>

<style>
.timer {
  position: absolute;
  margin-left: 49%;
}
.timer > p {
  font-size: 3.5rem;
  margin-top: -2%;
  font-weight: 700;
  color: #cd5069;
}
</style>
