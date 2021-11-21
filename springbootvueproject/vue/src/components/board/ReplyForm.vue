<template>
  <div>
    <el-row>
      <el-input placeholder="댓글을 입력해 주세요." v-model="reply">
        <el-button slot="append" @click="ReplySub">작성</el-button>
      </el-input>
    </el-row>
    <el-row style="margin-top: 40px">
      <el-row>댓글 {{ this.replyTotal }}개</el-row>
      <el-row>
        <el-table :data="replyData" style="width: 100%">
          <el-table-column prop="name" width="180"> </el-table-column>
          <el-table-column prop="content"> </el-table-column>
          <el-table-column fixed="right" width="120">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                @click.prevent="editReply(scope)"
              >
                수정
              </el-button>
              <el-button
                type="text"
                size="small"
                @click.prevent="deleteReply(scope)"
                >삭제</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "ReplyForm",
  props: ["num"],
  data() {
    return {
      // 댓글 내용
      reply: "",
      // 댓글 테이블
      replyData: [],
      // 댓글 개수
      replyTotal: null,
    };
  },
  methods: {
    // 댓글 작성
    ReplySub() {
      if (this.$store.state.isLogin === true) {
        // 댓글 작성하는 axios
        this.$axios
          .post("/api/board/regReply", {
            userId: this.$store.state.id,
            boardNum: this.num,
            content: this.reply,
          })
          .then((res) => {
            console.log(res);
            alert("댓글 등록 완료");
            this.reply = "";
            // 댓글 가져오는 axios
            this.$axios
              .post("/api/board/getReply", {
                boardNum: this.num,
              })
              .then((response) => {
                this.replyData = response.data.replyList;
                this.replyTotal = response.data.total;
              })
              .catch((error) => {
                console.log(error);
              })
              .finally(() => {});
          })
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {});
      } else {
        alert("로그인이 필요한 동작입니다.");
      }
    },
    // 댓글 수정
    editReply(val) {
      if (this.$store.state.id === val.row.userId) {
        // 댓글 수정 axios
        this.$prompt("댓글 수정", {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          inputValue: val.row.content,
        })
          .then(({ value }) => {
            this.$axios
              .post("/api/board/editReply", {
                content: value,
                num: val.row.num,
              })
              .then((res) => {
                console.log(res);
                // 댓글 가져오기
                this.$axios
                  .post("/api/board/getReply", {
                    boardNum: this.num,
                  })
                  .then((response) => {
                    console.log(response);
                    this.replyData = response.data.replyList;
                    this.replyTotal = response.data.total;
                  })
                  .catch(() => {
                    this.$message({
                      type: "info",
                      message: "댓글을 가져오는데 실패했습니다.",
                    });
                  })
                  .finally(() => {});
                this.$message({
                  type: "success",
                  message: "댓글 수정이 완료 됐습니다.",
                });
              })
              .catch((err) => {
                console.log(err);
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "댓글 수정이 취소 됐습니다.",
            });
          });
      } else {
        alert(" 본인의 댓글만 수정 가능합니다.");
      }
    },
    // 댓글 삭제
    deleteReply(val) {
      if (this.$store.state.id === val.row.userId) {
        this.$axios
          .post("/api/board/deleteReply", {
            num: val.row.num,
          })
          .then((res) => {
            console.log(res);
            // 댓글 새로고침
            this.$axios
              .post("/api/board/getReply", {
                boardNum: this.num,
              })
              .then((response) => {
                console.log(response);
                this.replyData = response.data.replyList;
                this.replyTotal = response.data.total;
              })
              .catch((error) => {
                console.log(error);
              });
            this.$message({
              type: "success",
              message: "댓글 삭제 완료 됐습니다.",
            });
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        alert(" 본인의 댓글만 삭제 가능합니다.");
      }
    },
  },
  created() {
    // 댓글 가져오는 axios
    this.$axios
      .post("/api/board/getReply", {
        boardNum: this.num,
      })
      .then((response) => {
        console.log(response);
        this.replyData = response.data.replyList;
        this.replyTotal = response.data.total;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style></style>
