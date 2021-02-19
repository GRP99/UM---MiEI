<template>
  <div>
    <Navbar />
    <v-container fluid>
      <v-card color="white" class="mt-16 px-2">
        <cpuChart
          class="ml-n2"
          :width="550"
          :height="500"
          id="SYSTEM"
        ></cpuChart>
      </v-card>
    </v-container>
    <v-card color="transparent" flat height="300"></v-card>
    <Footer />
  </div>
</template>

<script>

import Navbar from "@/components/navBar.vue";
import cpuChart from "@/components/cpuChart";
import Footer from "@/components/Footer.vue";

export default {
  props: ["id"],
  data() {
    return {
      item: [],
    };
  },

  components: {
    Navbar,
    cpuChart,
    Footer
  },
};
</script>