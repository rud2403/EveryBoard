<template>
  <div>
    <el-row> 나의 댓글 총 개수: {{ this.myReplyTotal }} </el-row>
    <el-table :data="tableData" style="width: 100%" @row-click="rowclick">
      <el-table-column prop="name" width="300"> </el-table-column>
      <el-table-column prop="content" width="600"> </el-table-column>
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      layout="prev, pager, next"
      :total="this.myReplyTotal"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "MyReply",
  data() {
    return {
      tableData: [],
      // 페이지 네이션
      currentPage: 1,
      // 댓글 total 값
      myReplyTotal: null,
    };
  },
  methods: {
    handleCurrentChange() {
      console.log(this.currentPage);
      this.$axios
        .post("/api/user/getMyReplyList", {
          id: this.$store.state.id,
          currentPage: this.currentPage,
        })
        .then((res) => {
          console.log(res);
          this.tableData = res.data.MyReplyList;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    rowclick(val) {
      console.log(val);
      this.$axios
        .post("/api/board/updateReadCount", {
          num: val.num,
        })
        .then((res) => {
          console.log(res);
          val.readcount = res.data;
          this.$router.push({
            name: "BoardDetail",
            params: {
              num: val.num,
              title: val.title,
              content: val.boardContent,
              author: val.author,
              regdate: val.regdate,
              readcount: val.readcount,
              likecount: val.likecount,
              id: val.id,
            },
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created() {
    // 유저 댓글 가져오는 axios
    this.$axios
      .post("/api/user/getMyReplyList", {
        id: this.$store.state.id,
        currentPage: this.currentPage,
      })
      .then((res) => {
        console.log(res);
        this.tableData = res.data.MyReplyList;
        this.myReplyTotal = res.data.total;
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style></style>
