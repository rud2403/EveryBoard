<template>
  <div>
    <el-card class="box-card passWordCertiCard">
      <el-row style="margin-bottom: 5%"> 비밀번호를 입력해 주세요. </el-row>
      <el-row>
        <el-input
          placeholder="비밀번호"
          v-model="passwordCertiInput"
          type="password"
        >
          <el-button slot="append" @click="psswordCerti">확인</el-button>
        </el-input></el-row
      >
    </el-card>
  </div>
</template>

<script>
export default {
  name: "PasswordCerti",
  data() {
    return {
      passwordCertiInput: "",
    };
  },
  methods: {
    psswordCerti() {
      // 비밀번호 확인하는 axios
      this.$axios
        .post("/api/user/passwordCerti", {
          email: this.$store.state.email,
          password: this.passwordCertiInput,
        })
        .then((res) => {
          console.log(res);
          // 인증 성공
          if (res.data === 1) {
            // 변경 페이지로 이동
            this.$router.push({
              name: "UserEdit",
              params: {
                email: this.$store.state.email,
                name: this.$store.state.name,
              },
            });
          } else {
            this.$message({
              type: "error",
              message: "비밀번호가 틀립니다.",
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
.passWordCertiCard {
  width: 480px;
  height: 150px;
  text-align: center;
}
</style>
