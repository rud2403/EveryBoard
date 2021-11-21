<template>
  <div>
    <el-row> 자유 게시판 </el-row>
    <el-row class="boardSelect">
      <!-- 검색 -->
      <div style="margin-top: 15px">
        <el-input
          placeholder="Please input"
          v-model="searchInput"
          class="input-with-select"
        >
          <!-- select -->
          <el-select v-model="searchSelect" slot="prepend" placeholder="Select">
            <el-option label="선택" value="null"></el-option>
            <el-option label="제목" value="title"></el-option>
            <el-option label="글쓴이" value="author"></el-option>
          </el-select>
          <!-- 검색 버튼 -->
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="searching"
          ></el-button>
        </el-input>
      </div>
    </el-row>
    <!-- 테이블 -->
    <el-table
      :data="tableData"
      style="width: 100%; cursor: pointer"
      @row-click="rowclick"
      v-loading="loading"
    >
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
      :total="this.boardTotal"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "FreeBoardList",
  data() {
    return {
      // 테이블 데이터
      tableData: [],
      // 페이지 네이션
      currentPage: 1,
      // 검색 값
      searchInput: null,
      // select 값
      searchSelect: null,
      // 게시글 total 값
      boardTotal: null,
      loading: false,
    };
  },
  methods: {
    // 테이블 가져오기 구간 ( 페이지 선택 시 )
    handleCurrentChange() {
      this.$axios
        .post("/api/board/getFreeList", {
          searchInput: this.searchInput,
          searchSelect: this.searchSelect,
          currentPage: this.currentPage,
        })
        .then((res) => {
          this.tableData = res.data.FreeBoardList;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    selectChange() {
      console.log(this.value);
    },
    // 테이블 가져오기 구간 ( 검색 시 )
    searching() {
      this.$axios
        .post("/api/board/getFreeList", {
          searchInput: this.searchInput,
          searchSelect: this.searchSelect,
          currentPage: this.currentPage,
        })
        .then((res) => {
          this.tableData = res.data.FreeBoardList;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    rowclick(val) {
      console.log(val);
      // 해당 게시글 조회수 올리는 axios
      this.$axios
        .post("/api/board/updateReadCount", {
          num: val.num,
        })
        .then((res) => {
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
  // 테이블 가져오기 구간 ( 시작 시 )
  mounted() {
    this.loading = true;
    this.$axios
      .post("/api/board/getFreeList", {
        searchInput: this.searchInput,
        searchSelect: this.searchSelect,
        currentPage: this.currentPage,
      })
      .then((res) => {
        this.tableData = res.data.FreeBoardList;
        this.boardTotal = res.data.total;
        this.loading = false;
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
.el-select {
  text-align: right;
  width: 110px;
}
.el-input {
  width: 50%;
}
.boardSelect {
  margin-left: 70%;
}
</style>
