<template>
  <div id="maintenance">
    <Navbar />

    <v-row justify="center" align="start" no-gutters class="mb-6">
      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-6 ml-7 caption">User Identification</span>
            </v-row>
            <v-row align="center">
              <span class="mt-2 ml-7 title font-weight-bold">
                {{ item.SID }}
                {{ item.USERNAME }}
              </span>
            </v-row>
            <v-row align="center">
              <v-chip
                v-if="item.STATUS == 'ACTIVE'"
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
              <span class="body-2 mt-4 ml-2">{{ item.STATUS }}</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="green">
              published_with_changes
            </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-9 ml-7 title font-weight-bold">
                {{ item.SERVER }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-7">Server</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#8BC34A"> fingerprint </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-9 ml-7 title font-weight-bold">
                {{ item.SCHEMANAME }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-7">Schemaname</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#B71C1C"> extension </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <h3 class="mt-7 ml-7">
                {{ timestamp(item.TIMESTAMP) }}
              </h3>
            </v-row>
            <v-row align="center">
              <span class="ml-7 mt-2 body-2 font-weight-light">Timestamp</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#80DEEA"> query_builder </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-9 ml-7 title font-weight-bold">
                {{ item.OSUSER }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-7">OS Username</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#F9A825"> person </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-9 ml-7  font-weight-bold">
                {{ item.MACHINE }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-7">OS Machine</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#F9A825"> computer </v-icon>
          </v-col>
        </v-row>
      </v-card>
      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-9 ml-7 title font-weight-bold">
                {{ item.PORT }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-7">Port</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#536DFE"> mediation </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-9 ml-7 title font-weight-bold">
                {{ item.TYPE }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-7">Type</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#C0CA33"> help </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="225" class="mt-7 mr-2 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <h3 class="mt-7 ml-7 font-weight-bold">
                {{ timestamp(item.LOGON_TIME) }}
              </h3>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-2 ml-7">Logon Time</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#FFD54F"> login </v-icon>
          </v-col>
        </v-row>
      </v-card>
    </v-row>

    <!-- THIS -->
    <v-row>
      <v-col class="ml-13">
        <v-card class="mt-2 rounded-0 mx-auto" height="350" width="1100">
          <WaitTime
            class="ml-n2"
            :width="550"
            :height="300"
            :id="$route.params.id"
          ></WaitTime>
        </v-card>
      </v-col>
    </v-row>
    <v-card color="transparent" flat height="300"></v-card>
    <Footer />

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

  </div>
</template>
<script>
import Navbar from "@/components/navBar.vue";
import axios from "axios";
import moment from "moment/moment";
// THIS
import WaitTime from "@/components/waitTimeChart.js"
import Footer from "@/components/Footer.vue"

export default {
  props: ["id", "id2"],
  data() {
    return {
      item: [],
      fab:false, 
    };
  },
  components: {
    Navbar,
    Footer,
    // THIS
    WaitTime
  },

  created: async function () {
   let response = await axios.get(
      "http://localhost:5001/getSession/" + this.id + "/" + this.id2
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