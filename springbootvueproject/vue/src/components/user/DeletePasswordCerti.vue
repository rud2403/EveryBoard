<template>
  <div>
    <el-card class="box-card passWordCertiCard">
      <el-row style="margin-bottom: 5%"> 비밀번호를 입력해 주세요. </el-row>
      <el-row
        ><el-input
          placeholder="비밀번호"
          v-model="deletePasswordCertiInput"
          type="password"
        >
          <el-button slot="append" @click="deletePsswordCerti">확인</el-button>
        </el-input></el-row
      >
    </el-card>
  </div>
</template>

<script>
export default {
  name: "DeletePasswordCerti",
  data() {
    return {
      deletePasswordCertiInput: "",
    };
  },
  methods: {
    deletePsswordCerti() {
      //confirm 확인
      this.$confirm("정말로 회원 탈퇴 하시겠습니까?", "Warning", {
        confirmButtonText: "확인",
        cancelButtonText: "취소",
        type: "warning",
      })
        .then(() => {
          // 비밀번호 확인하는 axios
          this.$axios
            .post("/api/user/passwordCerti", {
              email: this.$store.state.email,
              password: this.deletePasswordCertiInput,
            })
            .then((res) => {
              // 인증 성공
              if (res.data === 1) {
                // 변경 페이지로 이동
                // 1. 계정 삭제하는 actions
                this.$store.dispatch("deleteMemberInfo", {
                  id: this.$store.state.id,
                });
                this.$message({
                  type: "success",
                  message: "회원 탈퇴가 완료됐습니다.",
                });
                // 2. 홈으로 이동
                // router
                this.$router.push({ name: "Main" });
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
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "회원 탈퇴가 취소됐습니다.",
          });
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
