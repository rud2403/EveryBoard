<template>
  <div>
    <Header />
    <h2 style="text-align: center">마이페이지</h2>
    <el-container style="height: 400px">
      <!-- 사이드 메뉴 시작 -->
      <el-aside width="200px">
        <el-menu>
          <el-menu-item @click="myBoard">내 게시글</el-menu-item>
          <el-menu-item @click="myReply">내 댓글</el-menu-item>
          <el-menu-item @click="editInfo">정보 수정</el-menu-item>
          <el-menu-item @click="deleteUser">회원 탈퇴</el-menu-item>
        </el-menu>
      </el-aside>
      <!-- 사이드 메뉴 끝 -->

      <!-- 본문 시작 -->
      <el-container>
        <router-view />
      </el-container>
      <!-- 본문 끝 -->
    </el-container>
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
export default {
  name: "MyPage",
  methods: {
    myBoard() {
      this.$router.push({ name: "MyBoard" });
    },
    myReply() {
      this.$router.push({ name: "MyReply" });
    },
    editInfo() {
      this.$router.push({ name: "PasswordCerti" });
    },
    deleteUser() {
      this.$router.push({ name: "DeletePasswordCerti" });
    },
  },
  components: {
    Header,
    Footer,
  },
};
</script>

<style>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.el-container {
  justify-content: center;
  align-items: center;
}
</style>
