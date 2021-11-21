<template>
  <div>
    <h1 @click="goHome" style="cursor: pointer">Every Board</h1>
    <div class="headerButton">
      <el-button type="text" @click="Login" v-if="!this.$store.state.isLogin">
        로그인
      </el-button>
      <el-button type="text" @click="Logout" v-if="this.$store.state.isLogin">
        로그아웃
      </el-button>
      <el-button type="text" @click="SignUp" v-if="!this.$store.state.isLogin">
        회원가입
      </el-button>
      <el-button type="text" @click="MyPage"> 마이 페이지 </el-button>
      <el-button
        type="text"
        @click="AdminPage"
        v-if="this.$store.state.role === 'ADMIN'"
      >
        관리자 페이지
      </el-button>
      <span v-if="this.$store.state.isLogin">
        | {{ this.$store.state.name }}님 반갑습니다.
      </span>
    </div>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      router
    >
      <el-menu-item index="/popularBoard">인기 게시판</el-menu-item>
      <el-menu-item index="/freeBoard">자유 게시판</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      activeIndex: "/home",
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    Login() {
      this.$router.push({ name: "LoginPage" });
    },
    Logout() {
      this.$store.commit("logout");
      this.$message({
        type: "info",
        message: "로그아웃 됐습니다.",
      });
      this.$router.push({ name: "Main" });
    },
    SignUp() {
      this.$router.push({ name: "SignUpPage" });
    },
    MyPage() {
      this.$router.push({
        name: "MyBoard",
        params: { id: this.$store.state.id },
      });
    },
    goHome() {
      this.$router.push({ name: "PopularBoard" });
    },
    AdminPage() {
      this.$router.push({ name: "ManageUser" });
    },
  },
};
</script>

<style>
.headerButton {
  text-align: right;
}
h1 {
  text-align: center;
}
</style>
