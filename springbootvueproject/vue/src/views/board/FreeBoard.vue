<template>
  <div>
    <el-row>
      <write-button />
    </el-row>
    <el-row>
      <freeBoard-list />
    </el-row>
  </div>
</template>

<script>
import WriteButton from "@/components/board/WriteButton";
import FreeBoardList from "@/components/board/FreeBoardList";
export default {
  name: "FreeBoard",
  components: {
    WriteButton,
    FreeBoardList,
  },
};
</script>

<style></style>
