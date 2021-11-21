<template>
  <div>
    <el-row class="wrapper">
      <el-button type="text" @click="goMain"><h1>Every Board</h1></el-button>
      <el-card class="SignUpCard">
        <h3>회원 가입</h3>
        <el-form
          :model="signInForm"
          status-icon
          :rules="rules"
          ref="signInForm"
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
                message: '올바른 이메일 주소를 입력해 주세요.',
                trigger: ['blur', 'change'],
              },
            ]"
          >
            <el-input v-model="signInForm.email" autocomplete="off"></el-input>
          </el-form-item>
          <!-- 비밀번호 입력 -->
          <el-form-item label="비밀번호" prop="pass">
            <el-input
              type="password"
              v-model="signInForm.pass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <!-- 비밀번호 확인 입력 -->
          <el-form-item label="비밀번호 확인" prop="checkPass">
            <el-input
              type="password"
              v-model="signInForm.checkPass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <!-- 이름 입력 -->
          <el-form-item label="이름" prop="name">
            <el-input v-model="signInForm.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-button type="primary" @click="submitForm('signInForm')">
            회원가입
          </el-button>
        </el-form>
      </el-card>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "SignUpPage",
  data() {
    // 비밀번호 유효성 검사
    var validatePass = (rule, value, callback) => {
      // 공백일 때
      if (value === "") {
        callback(new Error("비밀번호를 입력해 주세요."));
        // 공백이 아닐 때
      } else {
        // 비밀번호 확인과 값이 다를 때
        if (this.signInForm.checkPass !== "") {
          this.$refs.signInForm.validateField("비밀번호를 확인해주세요.");
        }
        callback();
      }
    };
    // 비밀번호 확인 유효성 검사
    var validatePass2 = (rule, value, callback) => {
      // 공백일 때
      if (value === "") {
        callback(new Error("비밀번호 확인을 입력해주세요."));
        // 공백은 아닌데 비밀번호와 값이 다를 때
      } else if (value !== this.signInForm.pass) {
        callback(new Error("비밀번호와 다릅니다."));
        // 공백도 아니고 비밀번호도 일치할 때
      } else {
        callback();
      }
    };
    // 이름 유효성 검사
    var checkName = (rule, value, callback) => {
      // 공백일 때
      if (value === "") {
        return callback(new Error("이름을 입력해 주세요"));
      } else {
        callback();
      }
    };
    return {
      signInForm: {
        email: "",
        pass: "",
        checkPass: "",
        name: "",
        role: "USER",
      },
      rules: {
        // email: [{ validator: checkEmail, trigger: "blur" }],
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        name: [{ validator: checkName, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post("/api/signUp", {
              email: this.signInForm.email,
              password: this.signInForm.pass,
              name: this.signInForm.name,
              role: this.signInForm.role,
            })
            .then((res) => {
              console.log(res);
              if (res.data === 1) {
                this.$confirm(
                  "회원가입 완료됐습니다. 로그인 하시겠습니까?",
                  "Warning",
                  {
                    confirmButtonText: "확인",
                    cancelButtonText: "취소",
                    type: "warning",
                  }
                )
                  .then(() => {
                    this.$router.push({ name: "LoginPage" });
                  })
                  .catch(() => {
                    this.$router.push({ name: "Main" });
                  });
              } else {
                alert("중복되는 이메일입니다.");
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          alert("모든 양식에 맞춰 기입해주세요.");
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
.SignUpCard {
  height: 50%;
}
</style>
