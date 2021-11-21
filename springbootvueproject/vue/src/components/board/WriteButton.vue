<template>
  <el-button type="primary" class="WriteButton" @click="write">
    글쓰기
  </el-button>
</template>

<script>
export default {
  name: "WriteButton",
  methods: {
    write() {
      if (!this.$store.state.isLogin) {
        alert("로그인이 필요한 작업입니다.");
      } else {
        this.$router.push({ name: "BoardForm" });
      }
    },
  },
};
</script>

<style>
.WriteButton {
  float: right;
}
</style>
