<template>
  <div id="maintenance">
    <Navbar />
    <v-container fluid>
      <v-card color="white" class="mt-16 px-2">
        <SGAChart class="ml-n2" :width="550" :height="500"></SGAChart>
      </v-card>
    </v-container>
    <v-card color="transparent" flat height="300"></v-card>
    <Footer />
  </div>
</template>

<script>
import Navbar from "@/components/navBar.vue";
import SGAChart from "@/components/SGAChart";
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
      item: [],
    };
  },

  components: {
    Navbar,
    Footer,
    SGAChart,
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