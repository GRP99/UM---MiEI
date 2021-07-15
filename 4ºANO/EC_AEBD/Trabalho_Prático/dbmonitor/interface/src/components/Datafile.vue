<template>
  <div id="maintenance">
    <Navbar />
    <v-row align="center" justify="center">
      <v-card height="50" width="1500" class="mt-8 mb-5" flat>
        <v-card-text class="center--text" align="center" justify="center">
          {{ item.FILENAME }}
        </v-card-text>
      </v-card>
    </v-row>
    <v-row justify="center" align="start" no-gutters class="mb-6">
      <v-card height="100" width="225" class="mt-7 mr-7 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-6 ml-7 caption">Tablespace</span>
            </v-row>
            <v-row align="center">
              <span class="mt-1 ml-7 title font-weight-bold">
                {{ item.TABLESPACE_NAME }}
              </span>
            </v-row>
            <v-row align="center">
              <v-chip
                v-if="item.ONLINE_STATUS == 'ONLINE'"
                class="ml-7 mt-4 white--text rounded-sm"
                color="green"
                label
                small
              >
                status
              </v-chip>
              <v-chip
                v-else
                class="ml-7 mt-4 white--text rounded-sm"
                color="orange"
                label
                small
              >
                status
              </v-chip>
              <span class="body-2 mt-4 ml-2">{{ item.ONLINE_STATUS }}</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="green"> info </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-7 rounded-0">
        <v-row>
          <v-col cols="8">
            <v-row align="center">
              <span class="mt-8 ml-7 title font-weight-bold">
                {{ item.STATUS }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="ml-7 mt-4 body-2 font-weight-light">Status</span>
            </v-row>
          </v-col>
          <v-col cols="4">
            <v-icon class="mt-10 mr-7" color="#D32F2F"> source </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-7 rounded-0">
        <v-row>
          <v-col cols="8">
            <v-row align="center">
              <span class="mt-9 ml-9 title font-weight-bold">
                {{ item.FILE_ID }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-9">FileID</span>
            </v-row>
          </v-col>
          <v-col cols="4">
            <v-icon class="mt-10 mr-8" color="#8BC34A"> fingerprint </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-7 rounded-0">
        <v-row>
          <v-col cols="8">
            <v-row align="center">
              <span class="mt-9 ml-7 title font-weight-bold">
                {{ item.AUTOEXTENSIBLE }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-7">Autoextensible</span>
            </v-row>
          </v-col>
          <v-col cols="4">
            <v-icon class="mt-10 mr-7" color="#F9A825"> extension </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-7 rounded-0">
        <v-row>
          <v-col cols="8">
            <v-row align="center">
              <span class="ml-7 mt-7 caption font-weight-light">Timestamp</span>
            </v-row>
            <v-row align="center">
              <h3 class="mt-2 ml-7">
                {{ timestamp(item.TIMESTAMP) }}
              </h3>
            </v-row>
          </v-col>
          <v-col cols="4">
            <v-icon class="mt-10 mr-7" color="#80DEEA"> query_builder </v-icon>
          </v-col>
        </v-row>
      </v-card>
    </v-row>

    <!-- THIS -->
    <v-row>
      <v-col class="ml-13">
        <v-card class="mt-2 ml-16 rounded-0" height="332" width="610">
          <DatafilesBytes
            class="ml-n2"
            :width="550"
            :height="300"
            :id="$route.params.id"
          ></DatafilesBytes>
        </v-card>
      </v-col>
      <v-col class="ml-n16">
        <v-card class="mt-2 ml-n3 rounded-0" height="332" width="610">
          <DatafilesBlocks
            class="ml-n2"
            :width="550"
            :height="300"
            :id="$route.params.id"
          ></DatafilesBlocks>
        </v-card>
      </v-col>
    </v-row>
    <v-btn
      v-scroll="onScroll"
      v-show="fab"
      icon
      depressed
      fixed
      dark
      bottom
      right
      class="ma-2"
      color="#C62828"
      @click="toTop"
    >
      <v-icon>keyboard_arrow_up</v-icon>
    </v-btn>
    <v-card color="transparent" flat height="300"></v-card>
    
    <Footer />

    
  </div>
</template>
<script>
import Navbar from "@/components/navBar.vue";
import axios from "axios";
import moment from "moment/moment";
// THIS
import DatafilesBytes from "@/components/DatafilesBytes";
import DatafilesBlocks from "@/components/DatafilesBlocks";
import Footer from "@/components/Footer.vue"

export default {
  props: ["id", "id2"],
  data() {
    return {
      item: [],
      fab: false,
    };
  },
  components: {
    Navbar,

    // THIS
    DatafilesBytes,
    DatafilesBlocks,
    Footer
  },

  created: async function () {
    var format = "DD-MM-YYYY HH:mm:ss";
    var nid2 = moment(this.id2).format(format);
    let response = await axios.get(
      "http://localhost:5001/getDataFileTimeStamp/" + this.id + "/" + nid2
    );
    this.item = response.data.rows[0];
  },
  methods: {
    timestamp: function (time) {
      return moment(time).format("MMM DD, YYYY HH:mm:ss");
    },
    onScroll(e) {
      if (typeof window === "undefined") return;
      const top = window.pageYOffset || e.target.scrollTop || 0;
      this.fab = top > 20;
    },
    toTop() {
      this.$vuetify.goTo(0);
    },
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