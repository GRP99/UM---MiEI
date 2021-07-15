<template>
  <div id="maintenance">
    <Cpu />
  </div>
</template>
<script>
import Cpu from "@/components/cpuSYSTEM.vue";

export default {
  components: {
    Cpu,
  },
};
</script>

<style>
#maintenance {
  background-color: #efdcc3;
  height: 100%;
  background-position: center;
  background-repeat: repeat;
  background-size: contain;
  position: relative;
}
#letra {
  font-family: "Merienda", Helvetica, Arial;
}
</style>