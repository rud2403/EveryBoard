<template>
  <div>
    <el-row class="Header">
      <Header></Header>
    </el-row>
    <el-row class="Content">
      <router-view />
    </el-row>
    <el-row class="Footer">
      <Footer></Footer>
    </el-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
export default {
  name: "Main",
  computed: {
    ...mapState({
      stateValue: "name",
    }),
  },
  components: {
    Header,
    Footer,
  },
};
</script>

<style>
.Content {
  margin-left: 10%;
  margin-right: 10%;
  margin-top: 2%;
}
</style>
