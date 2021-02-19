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

        <v-row>
          <v-col
            v-for="(item, index) in table_data"
            :key="index"
            cols="6"
            sm="2"
            md="2"
            lg="2"
          >
            <v-card height="200" width="200">
              <v-list>
                <v-list-item>
                  <v-list-item-content>
                    <v-row>
                      <v-flex xs12 text-xs-center>
                        <div
                          class="text-center font-weight-bold black--text mt-10"
                        >
                          {{ item.TABLESPACE_NAME }}
                        </div>
                      </v-flex>
                    </v-row>

                    <v-row>
                      <v-flex xs12 text-xs-center>
                        <div class="text-center caption black--text mt-5">
                          Size:
                          {{
                            Math.round(
                              (item.TABLESPACE_SIZE + Number.EPSILON) * 100
                            ) / 100
                          }}
                          Mb
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
                        @click="tablespace(item)"
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
      </v-data-iterator>
    </v-container>

    <v-card flat color="transparent" height="300"></v-card>
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
    };
  },
  components: {
    Navbar,
    Footer
  },
  created: async function () {
    let response = await axios.get("http://localhost:5001/tablespaces");
    var format = "DD-MM-YYYY HH:mm:ss";
    for (var j = 0; j < response.data.rows.length; j++) {
      response.data.rows[j].TIMESTAMP = moment(
        response.data.rows[j].TIMESTAMP
      ).format(format);
    }

    this.items = response.data.rows;

    let response2 = await axios.post("http://localhost:5001/timestamp", {
      table: "TABLESPACES",
    });

    this.itemsTimestamp.push("- Select -");
    for (var i = 0; i < response2.data.rows.length; i++)
      this.itemsTimestamp.push(
        moment(response2.data.rows[i].TIMESTAMP).format("MMM DD, YYYY HH:mm:ss")
      );
    this.itemsTimestamp.sort().reverse()
  },
  methods: {
    tablespace: function (item) {
      this.$router.push(
        "/tablespace/" + item.TABLESPACE_NAME + "/" + item.TIMESTAMP
      );
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

      const format = "DD-MM-YYYY HH:mm:ss";
      let filtered_data = this.items.filter(function (item) {
        if (self.search != "" && self.search != "- Select -") {
          return moment(self.search).format(format) == item.TIMESTAMP;
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