<template>
  <div>
    <el-row>
      <el-table
        :data="
          tableData.filter(
            (data) =>
              !search || data.name.toLowerCase().includes(search.toLowerCase())
          )
        "
        style="width: 100%"
      >
        <el-table-column label="Email" prop="email" width="250px">
          <template slot-scope="scope">
            <el-input v-model="scope.row.email"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="Name" prop="name" width="150px">
          <template slot-scope="scope">
            <el-input v-model="scope.row.name"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="Role" prop="role" width="150px">
          <template slot-scope="scope">
            <el-select v-model="scope.row.role">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column align="right" width="300px">
          <!-- eslint-disable-next-line vue/no-unused-vars -->
          <template slot="header" slot-scope="scope">
            <el-input v-model="search" size="mini" placeholder="검색" />
          </template>
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
              >수정</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              >삭제</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-row>
      <el-pagination
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :total="this.total"
      >
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "ManageUser",
  data() {
    return {
      tableData: [],
      search: "",

      // 페이지네이션 현재 페이지
      currentPage: 1,

      // 페이지 네이션 토탈 값
      total: 0,

      // role select 범위
      options: [
        {
          value: "ADMIN",
          label: "ADMIN",
        },
        {
          value: "USER",
          label: "USER",
        },
      ],
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
      // 회원 정보 수정하는 axios
      this.$axios
        .post("/api/user/adminChangeUser", {
          id: row.id,
          email: row.email,
          name: row.name,
          role: row.role,
        })
        .then((res) => {
          console.log(res);
          this.$message({
            type: "success",
            message: "회원 정보 수정 완료",
          });
        })
        .catch((err) => {
          console.log(err);
          this.$message({
            type: "error",
            message: "회원 정보 수정 실패",
          });
        });
    },
    handleDelete(index, row) {
      console.log(index, row);
      this.$axios
        .post("/api/user/adminDeleteUser", {
          id: row.id,
        })
        .then((res) => {
          console.log(res);
          this.$axios
            .get("/api/user/adminGetUser", {
              params: {
                currentPage: this.currentPage,
              },
            })
            .then((res) => {
              console.log(res);
              this.tableData = res.data.userList;
              this.total = res.data.total;
            })
            .catch((err) => {
              console.log(err);
            });
          this.$message({
            type: "success",
            message: "회원 탈퇴 완료",
          });
        })
        .catch((err) => {
          console.log(err);
          this.$message({
            type: "error",
            message: "회원 탈퇴 실패",
          });
        });
    },
    // 페이지 변경
    handleCurrentChange(val) {
      console.log(`current page: ${val}`);
      this.$axios
        .get("/api/user/adminGetUser", {
          params: {
            currentPage: val,
          },
        })
        .then((res) => {
          this.tableData = res.data.userList;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created() {
    // 유저 테이블 가져오는 axios 현재 페이지 들고가기
    this.$axios
      .get("/api/user/adminGetUser", {
        params: {
          currentPage: this.currentPage,
        },
      })
      .then((res) => {
        console.log(res);
        this.tableData = res.data.userList;
        this.total = res.data.total;
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style></style>
