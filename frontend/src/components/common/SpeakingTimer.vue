<template>
  <div class="speaking-timer">
    <p id="countdown">{{ this.getMinutes }}:{{ this.getSeconds }}</p>
  </div>
</template>

<script>
export default {
  name: 'Timer',
  data: function() {
    return {
      minutes: 2,
      seconds: 0,
    };
  },
  methods: {
    setTimer: function() {
      let interval = 0;

      const timeMinus = () => {
        if (this.seconds !== 0) {
          this.seconds--;
        } else if ((this.seconds === 0) & (this.minutes !== 0)) {
          this.minutes--;
          this.seconds = 59;
        } else if ((this.seconds === 0) & (this.minutes === 0)) {
          clearInterval(interval);
        }
      };
      interval = setInterval(timeMinus, 1000);
    },
  },
  mounted: function() {
    this.setTimer();
  },
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
.speaking-timer {
  margin: 30px auto;
}
.speaking-timer > p {
  font-size: 3rem;
  margin: -3%;
  font-weight: 700;
  color: #cd5069;
}
</style>
