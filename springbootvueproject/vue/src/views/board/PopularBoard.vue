<template>
  <div>
    <el-row style="margin-bottom: 2%"> 인기 게시판 </el-row>
    <el-row>
      <el-table
        :data="tableData"
        style="width: 100%; cursor: pointer; margin-bottom: 5%"
        @row-click="rowclick"
        v-loading="loading"
      >
        <el-table-column prop="num" label="번호" width="80"> </el-table-column>
        <el-table-column prop="title" label="제목" width="700">
        </el-table-column>
        <el-table-column prop="author" label="글쓴이"> </el-table-column>
        <el-table-column prop="regdate" label="작성일"> </el-table-column>
        <el-table-column prop="readcount" label="조회수" width="80">
        </el-table-column>
        <el-table-column prop="likecount" label="추천수" width="80">
        </el-table-column>
      </el-table>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "PopularBoard",
  data() {
    return {
      tableData: [],
      loading: false,
    };
  },
  methods: {
    rowclick(val) {
      console.log(val);
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
  },
  created() {
    this.loading = true;
    // 인기 게시글 가져오는 axios
    this.$axios
      .get("/api/board/getPopList")
      .then((res) => {
        this.tableData = res.data;
        this.loading = false;
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style>
.main {
  text-align: center;
}
</style>
