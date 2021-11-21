<template>
  <div>
    <el-row> 나의 게시글 총 개수: {{ this.myBoardTotal }} </el-row>
    <el-table :data="tableData" style="width: 100%" @row-click="rowclick">
      <el-table-column prop="num" label="번호" width="80"> </el-table-column>
      <el-table-column prop="title" label="제목" width="700"> </el-table-column>
      <el-table-column prop="author" label="글쓴이"> </el-table-column>
      <el-table-column prop="regdate" label="작성일"> </el-table-column>
      <el-table-column prop="readcount" label="조회수" width="80">
      </el-table-column>
      <el-table-column prop="likecount" label="추천수" width="80">
      </el-table-column>
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      layout="prev, pager, next"
      :total="this.myBoardTotal"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "MyBoard",
  data() {
    return {
      tableData: [],
      // 페이지 네이션
      currentPage: 1,
      // 게시글 total
      myBoardTotal: null,
    };
  },
  methods: {
    rowclick(val) {
      // 해당 게시글 조회수 올리는 axios
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
              content: val.content,
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
    handleCurrentChange() {
      console.log(this.currentPage);
      this.$axios
        .post("/api/user/getMyBoardList", {
          id: this.$store.state.id,
          currentPage: this.currentPage,
        })
        .then((res) => {
          console.log(res);
          this.tableData = res.data.MyBoardList;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created() {
    // 유저가 쓴 게시글 가져오는 axios
    this.$axios
      .post("/api/user/getMyBoardList", {
        id: this.$store.state.id,
        currentPage: this.currentPage,
      })
      .then((res) => {
        console.log(res);
        this.tableData = res.data.MyBoardList;
        this.myBoardTotal = res.data.total;
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style>
.el-pagination {
  text-align: center;
  margin-top: 20px;
}
</style>
