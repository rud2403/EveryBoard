<template>
  <div>
    <el-card class="box-card UserEditCard">
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="120px"
        class="demo-ruleForm"
      >
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
          <el-input v-model="ruleForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="이름" prop="name">
          <el-input
            type="text"
            v-model="ruleForm.name"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">
            변경
          </el-button>
          <el-button @click="cancel()">취소</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "UserEdit",
  data() {
    var validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("이름을 입력해 주세요."));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        email: "",
        name: "",
      },
      rules: {
        name: [{ validator: validateName, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 정보 변경하는 axios
          this.$store
            .dispatch("changMemberInfo", {
              id: this.$store.state.id,
              email: this.ruleForm.email,
              name: this.ruleForm.name,
            })
            .then(() => {
              // this.$router.push({ name: "Main" });
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  created() {
    this.ruleForm.email = this.$store.state.email;
    this.ruleForm.name = this.$store.state.name;
  },
};
</script>

<style>
.UserEditCard {
  width: 480px;
  height: 200px;
  text-align: center;
}
</style>
