<template>
  <div id="maintenance">
    <Navbar />
    <v-row align="center" justify="center" class="mt-16">
      <v-card width="1350">
        <v-select
          color="#C62828"
          v-model="search"
          :items="itemsTimestamp"
          label="Choose a TimeStamp"
          class="pa-4"
        ></v-select>
        <v-container class="ml-5" fluid>
          <v-card
            flat
            v-for="(item, index) in table_data"
            :key="index"
            class="mb-3 mr-3"
          >
            <v-layout>
              <v-flex xs6 sm4 md2>
                <div class="caption grey--text">Username</div>
                <div class="body-2">{{ item.USERNAME }}</div>
              </v-flex>
              <v-flex xs6 sm4 md3>
                <div class="caption grey--text">Account Status</div>
                <v-chip
                  v-if="item.ACCOUNT_STATUS === 'OPEN'"
                  color="#C5E1A5"
                  class="body-2 px-4 mb-2"
                  small
                >
                  {{ item.ACCOUNT_STATUS }}
                </v-chip>
                <v-chip v-else color="#EF9A9A" class="body-2 mb-2" small>
                  {{ item.ACCOUNT_STATUS }}
                </v-chip>
              </v-flex>
              <v-flex xs6 sm4 md2 class="ml-n12">
                <div class="caption grey--text">Expiry Date</div>
                <div class="body-2">
                  {{ date(item.EXPIRY_DATE) }}
                  <v-icon
                    v-if="passed(date(item.EXPIRY_DATE)) < 0"
                    small
                    color="#8BC34A"
                    >schedule</v-icon
                  >
                  <v-icon v-else small color="#B70000">schedule</v-icon>
                </div>
              </v-flex>

              <v-flex xs6 sm4 md2>
                <div class="caption grey--text">Created</div>
                <div class="body-2">{{ date(item.CREATED) }}</div>
              </v-flex>
              <v-flex xs6 sm4 md2>
                <div class="caption grey--text">Timestamp</div>
                <div class="body-2">{{ timestamp(item.TIMESTAMP) }}</div>
              </v-flex>
              <v-flex xs6 sm4 md1>
                <v-btn
                  @click="details(item)"
                  outlined
                  small
                  class="mt-3"
                  color="red"
                  >More Details</v-btn
                >
              </v-flex>
            </v-layout>
            <v-divider></v-divider>
          </v-card>
        </v-container>
      </v-card>
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
import axios from "axios";
import Navbar from "@/components/navBar.vue";
import moment from "moment/moment";
import Footer from "@/components/Footer.vue"
export default {
  data() {
    return {
      fab: false,
      search: "",
      headers: [],
      items: [],
      itemsTimestamp: [],
    };
  },
  components: {
    Navbar,
    Footer
  },
  methods: {
    details:function(item){
      const format = "DD-MM-YYYY HH:mm:ss";
      const date = moment(item.TIMESTAMP).format(format)
      this.$router.push(
        "/user/details/" + item.USERNAME + "/" + date
      );
    },
    date: function (data) {
      return moment(data).format("ll");
    },
    timestamp: function (time) {
      const format = "MMM DD, YYYY HH:mm:ss";
      return moment(time).format(format);
    },
    passed: function (time) {
      var now = moment(new Date());
      return moment.duration(now.diff(time), "ms").asMilliseconds();
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
  created: async function () {
    let response = await axios.get("http://localhost:5001/users");
    for (var i = 0; i < response.data.metaData.length; i++) {
      this.headers.push(response.data.metaData[i].name);
    }
    this.items = response.data.rows;

    let response2 = await axios.post("http://localhost:5001/timestamp", {
      table: "USERS",
    });

    this.itemsTimestamp.push("- Select -");
    const format = "MMM DD, YYYY HH:mm:ss";
    for (i = 0; i < response2.data.rows.length; i++)
      this.itemsTimestamp.push(
        moment(response2.data.rows[i].TIMESTAMP).format(format)
      );
    this.itemsTimestamp.sort().reverse()

     
  },
  computed: {
    getHeaders() {
      let s = new Set();
      this.headers.forEach((item) => {
        s.add(item);
      });

      return Array.from(s).map((a) => {
        return {
          text: a.toUpperCase(),
          value: a,
        };
      });
    },
    table_data() {
      let self = this;
      const format = "MMM DD, YYYY HH:mm:ss";
      let filtered_data = this.items.filter(function (item) {
        if (self.search != "" && self.search != "- Select -") {
          return self.search == moment(item.TIMESTAMP).format(format);
        } else {
          return item;
        }
      });
      return filtered_data;
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

