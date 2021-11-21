<template>
  <div>
    <el-row>
      <el-row>
        <el-button-group
          v-if="
            this.$route.params.id === this.$store.state.id ||
            this.$store.state.role === 'ADMIN'
          "
        >
          <el-button
            type="primary"
            icon="el-icon-edit"
            @click="Edit"
          ></el-button>
          <el-button
            type="primary"
            icon="el-icon-delete"
            @click="Delete"
          ></el-button>
        </el-button-group>
      </el-row>
      <el-row class="title">
        <el-row>
          <el-col :span="19">
            {{ this.author }} |
            {{ this.regdate }}
          </el-col>
          <el-col :span="5">
            조회수: {{ this.readcount }} | 추천수:
            {{ this.likecount }}
          </el-col>
        </el-row>
        <el-row>
          <h2>{{ this.title }}</h2>
        </el-row>
      </el-row>
      <hr />
      <el-row class="content" style="margin-left: 2%; margin-top: 2%">
        {{ this.content }}
      </el-row>
      <el-row style="text-align: center; margin-bottom: 20px">
        <el-button type="success" plain @click="liked">추천</el-button>
        <el-button type="info" plain @click="goList">목록으로</el-button>
      </el-row>
      <el-row style="margin-bottom: 30px">
        <reply-form :num="this.num"></reply-form>
      </el-row>
    </el-row>
  </div>
</template>

<script>
import ReplyForm from "@/components/board/ReplyForm";
export default {
  name: "BoardDetail",
  data() {
    return {
      num: "",
      title: "",
      content: "",
      author: "",
      readcount: "",
      likecount: "",
      regdate: "",
    };
  },
  methods: {
    Edit() {
      this.$router.push({
        name: "BoardForm",
        params: {
          isEdit: true,
          title: this.title,
          content: this.content,
          num: this.num,
        },
      });
    },
    Delete() {
      this.$axios
        .post("/api/board/delete", {
          num: this.$route.params.num,
        })
        .then((res) => {
          console.log(res);
          this.$router.push({ name: "Main" });
          alert("게시글 삭제 완료");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goList() {
      this.$router.go(-1);
    },
    liked() {
      if (this.$store.state.isLogin) {
        this.$axios
          .post("/api/board/liked", {
            boardNum: this.num,
            userId: this.$store.state.id,
          })
          .then((res) => {
            if (res.data === 0) {
              alert("이미 추천 누른 게시글입니다.");
            } else {
              alert("게시글 추천 완료");
              // 게시글 추천수 새로고침
              this.$axios
                .post("/api/board/getliked", {
                  num: this.num,
                })
                .then((res) => {
                  this.likecount = res.data;
                })
                .catch((err) => {
                  console.log(err);
                });
            }
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        alert("로그인이 필요한 동작입니다.");
      }
    },
  },
  components: {
    ReplyForm,
  },
  created() {
    this.num = this.$route.params.num;
    this.title = this.$route.params.title;
    this.content = this.$route.params.content;
    this.author = this.$route.params.author;
    this.readcount = this.$route.params.readcount;
    this.likecount = this.$route.params.likecount;
    this.regdate = this.$route.params.regdate;
  },
};
</script>

<style>
.el-button-group {
  float: right;
  margin-bottom: 2%;
}
</style>
