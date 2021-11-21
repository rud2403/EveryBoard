<template>
  <div>
    <el-row class="wrapper">
      <el-button type="text" @click="goMain"><h1>Every Board</h1></el-button>
      <el-card class="loginCard">
        <h3>로그인</h3>
        <el-form
          :model="loginForm"
          status-icon
          :rules="rules"
          ref="loginForm"
          label-width="120px"
          class="demo-ruleForm"
        >
          <!-- 이메일 입력 -->
          <el-form-item
            label="이메일"
            prop="email"
            :rules="[
              {
                required: true,
                message: '이메일을 입력해 주세요',
                trigger: 'blur',
              },
              {
                type: 'email',
                message: '올바른 이메일을 입력해 주세요',
                trigger: ['blur', 'change'],
              },
            ]"
          >
            <el-input v-model="loginForm.email" autocomplete="off"></el-input>
          </el-form-item>
          <!-- 비밀번호 입력 -->
          <el-form-item label="비밀번호" prop="pass">
            <el-input
              type="password"
              v-model="loginForm.pass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-button type="primary" @click="login('loginForm')">
            로그인
          </el-button>
        </el-form>
      </el-card>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  data() {
    // 비밀번호 유효성 검사
    var validatePass = (rule, value, callback) => {
      // 공백일 때
      if (value === "") {
        callback(new Error("비밀번호를 입력해 주세요."));
        // 공백이 아닐 때
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        email: "",
        pass: "",
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
      },
    };
  },
  methods: {
    login(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store
            .dispatch("login", {
              email: this.loginForm.email,
              password: this.loginForm.pass,
            })
            .then(() => {
              this.$message({
                type: "success",
                message: "로그인 완료.",
              });
            });
        } else {
          alert("양식을 올바르게 입력해 주세요.");
          return false;
        }
      });
    },
    goMain() {
      this.$router.push({ name: "Main" });
    },
  },
};
</script>

<style>
.wrapper {
  margin-top: 10%;
  margin-left: 22%;
  text-align: center;
  width: 50%;
}
.demo-ruleForm {
  padding-top: 20px;
}
.loginCard {
  height: 50%;
}
</style>
