<template>
  <div>
    <Header />
    <h2 style="text-align: center">관리자 페이지</h2>
    <el-container style="height: 600px">
      <!-- 사이드 메뉴 시작 -->
      <el-aside width="200px">
        <el-menu>
          <el-menu-item @click="manageUser">유저 관리</el-menu-item>
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
  name: "AdminPage",
  methods: {
    manageUser() {
      this.$router.push({ name: "ManageUser" });
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
