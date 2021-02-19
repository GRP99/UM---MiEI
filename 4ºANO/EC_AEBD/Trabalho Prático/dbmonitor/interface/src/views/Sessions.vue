<template>
  <div id="maintenance">
    <Navbar />
    <v-container>
      <v-data-iterator
        class="mt-10 mx-10 rounded"
        loading
        loading-text="A carregar... Por favor aguarde"
        :items="items"
        :sort-by="search.toLowerCase()"
        hide-default-footer
        disable-pagination
      >
        <template v-slot:header>
          <v-toolbar dark color="#9e2020" class="mb-6">
            <v-select
              flat
              solo-inverted
              hide-details
              prepend-inner-icon="mdi-magnify"
              v-model="search"
              :items="itemsTimestamp"
              label="Choose a TimeStamp"
            ></v-select>
          </v-toolbar>
        </template>
        <template>
          <v-row>
            <v-col
              v-for="(item, index) in table_data"
              :key="index"
              cols="8"
              sm="2"
              md="2"
              lg="3"
            >
              <v-card height="200" width="300">
                <v-list>
                  <v-list-item>
                    <v-list-item-content>
                      <v-row>
                        <v-flex xs12 text-xs-center>
                          <div
                            class="text-center font-weight-bold black--text mt-10"
                          >
                            {{ item.SID}} {{item.USERNAME}} 
                          </div>
                        </v-flex>
                      </v-row>

                      <v-row>
                        <v-flex xs12 text-xs-center>
                          <div class="text-center black--text mt-10">
                            {{ timestamp(item.TIMESTAMP) }}
                          </div>
                        </v-flex>
                      </v-row>

                      <v-row align="center" justify="center">
                        <v-chip
                          small
                          color="green lighten-4"
                          class="black--text mt-8"
                        >
                          {{ item.STATUS }}
                        </v-chip>
                      </v-row>
                      <v-row justify="center">
                        <v-btn
                          outlined
                          color="#C62828"
                          class="mt-7"
                          small
                          @click="toSession(item)"
                        >
                          Details
                        </v-btn>
                      </v-row>
                    </v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-card>
            </v-col>
          </v-row>
        </template>
      </v-data-iterator>
    </v-container>

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
import Footer from "@/components/Footer.vue"

export default {
  data() {
    return {
      fab: false,
      items: [],
      search: "",
      filter: {},
      sortDesc: false,
      itemsTimestamp: [],
      sortBy: "FILENAME",
    };
  },
  components: {
    Navbar,
    Footer
  },
  created: async function () {
    let response = await axios.get("http://localhost:5001/getSessions");
    this.items = response.data.rows;

    let response2 = await axios.post("http://localhost:5001/timestamp", {
      table: "SESSIONS",
    });

    this.itemsTimestamp.push("- Select -");
    for (var i = 0; i < response2.data.rows.length; i++)
      this.itemsTimestamp.push(
        moment(response2.data.rows[i].TIMESTAMP).format("MMM DD, YYYY HH:mm:ss")
      );
    this.itemsTimestamp.sort().reverse()
  },
  methods: {
    toSession: function (session) {
      const format = "DD-MM-YYYY HH:mm:ss";
      const date = moment(session.TIMESTAMP).format(format);
      this.$router.push(
        "/session/" + session.USERNAME + "/" + date
      );
    },
    timestamp: function (time) {
      const format = "MMM DD, YYYY HH:mm:ss";
      return moment(time).format(format);
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
  computed: {
    table_data() {
      let self = this;
      let filtered_data = this.items.filter(function (item) {
        if (self.search != "" && self.search != "- Select -") {
          return (
            self.search ==
            moment(item.TIMESTAMP).format("MMM DD, YYYY HH:mm:ss")
          );
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