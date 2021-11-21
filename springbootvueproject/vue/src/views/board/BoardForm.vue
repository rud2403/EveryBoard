<template>
  <el-form
    :model="writeForm"
    :rules="rules"
    ref="writeForm"
    label-width="120px"
    class="demo-ruleForm"
  >
    <el-form-item label="제목" prop="title">
      <el-input v-model="writeForm.title"></el-input>
    </el-form-item>
    <el-form-item label="내용" prop="content">
      <el-input
        type="textarea"
        v-model="writeForm.content"
        rows="20"
      ></el-input>
    </el-form-item>
    <el-form-item>
      <el-button
        type="primary"
        @click="edit('writeForm')"
        v-if="this.$route.params.isEdit === true"
      >
        수정
      </el-button>
      <el-button type="primary" @click="submit('writeForm')" v-else>
        작성
      </el-button>
      <el-button @click="cancel"> 취소 </el-button>
    </el-form-item>
  </el-form>
</template>
<script>
export default {
  name: "write",
  data() {
    return {
      writeForm: {
        title: "",
        content: "",
      },
      rules: {
        title: [
          {
            required: true,
            message: "제목을 입력해 주세요.",
            trigger: "blur",
          },
        ],
        content: [
          {
            required: true,
            message: "내용을 입력해 주세요.",
            trigger: "blur",
          },
        ],
      },
      // 게시글 번호
      num: "",
    };
  },
  methods: {
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert("작성 완료");
          this.$axios
            .post("/api/board/write", {
              title: this.writeForm.title,
              content: this.writeForm.content,
              author: this.$store.state.name,
              id: this.$store.state.id,
            })
            .then((res) => {
              console.log(res);
              this.$router.push({ name: "FreeBoard" });
            })
            .catch((err) => {
              console.log(err);
            })
            .finally(() => {});
        } else {
          alert("제목과 내용을 모두 입력해 주세요.");
          return false;
        }
      });
    },
    edit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post("/api/board/edit", {
              title: this.writeForm.title,
              content: this.writeForm.content,
              num: this.num,
            })
            .then((res) => {
              console.log(res);
              this.$router.push({ name: "FreeBoard" });
              alert("수정 완료");
            })
            .catch((err) => {
              console.log(err);
            })
            .finally(() => {});
        } else {
          alert("제목과 내용을 모두 입력해 주세요.");
          return false;
        }
      });
    },
    cancel() {
      this.$router.push({ name: "FreeBoard" });
    },
  },
  created() {
    if (this.$route.params.isEdit === true) {
      this.writeForm.title = this.$route.params.title;
      this.writeForm.content = this.$route.params.content;
      this.num = this.$route.params.num;
      console.log(`수정 페이지에서 이 글의 번호는 ${this.$route.params.num}`);
    }
  },
};
</script>

<style>
.el-form {
  width: 80%;
  margin-left: 5%;
}
</style>
