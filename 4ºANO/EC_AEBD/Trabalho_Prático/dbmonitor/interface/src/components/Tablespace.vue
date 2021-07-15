<template>
  <div id="maintenance">
    <Navbar />
    <v-row justify="center" align="start" no-gutters class="mb-6">
      <v-card height="100" width="220" class="mt-7 mr-3 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-7 ml-7 title font-weight-bold">
                {{ item.TABLESPACE_NAME }}
              </span>
            </v-row>
            <v-row align="center">
              <v-chip
                v-if="item.STATUS == 'ONLINE'"
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

      <v-card height="100" width="220" class="mt-7 mr-3 rounded-0">
        <v-row>
          <v-col cols="8">
            <v-row align="center">
              <span class="mt-8 ml-7 title font-weight-bold">
                {{ item.CONTENTS }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="ml-7 mt-4 body-2 font-weight-light">Contents</span>
            </v-row>
          </v-col>
          <v-col cols="4">
            <v-icon class="mt-10 mr-7" color="#D32F2F"> source </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="220" class="mt-7 mr-3 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-8 ml-7 title font-weight-bold">
                {{ Math.round((item.FREE + Number.EPSILON) * 100) / 100 }}
              </span>
            </v-row>
            <v-row align="center">
              <v-chip
                class="ml-7 mt-4 white--text rounded-sm"
                color="#8BC34A"
                label
                small
              >
                {{ Math.round((item.PERC_FREE + Number.EPSILON) * 100) / 100 }}
                %
              </v-chip>
              <span class="body-2 mt-4 ml-2">Free space</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#8BC34A">
              hourglass_empty
            </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="220" class="mt-7 mr-3 rounded-0">
        <v-row>
          <v-col cols="9">
            <v-row align="center">
              <span class="mt-8 ml-7 title font-weight-bold">
                {{ Math.round((item.USED + Number.EPSILON) * 100) / 100 }}
              </span>
            </v-row>
            <v-row align="center">
              <v-chip
                class="ml-7 mt-4 white--text rounded-sm"
                color="#F9A825"
                label
                small
              >
                {{ Math.round((item.PERC_USED + Number.EPSILON) * 100) / 100 }}
                %
              </v-chip>
              <span class="body-2 mt-4 ml-2">Used space</span>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-icon class="mt-10 mr-7" color="#F9A825"> hourglass_full </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="220" class="mt-7 mr-3 rounded-0">
        <v-row>
          <v-col cols="8">
            <v-row align="center">
              <h1 class="mt-7 ml-7">
                {{
                  Math.round((item.TABLESPACE_SIZE + Number.EPSILON) * 100) /
                  100
                }}
              </h1>
            </v-row>
            <v-row align="center">
              <span class="ml-7 mt-2 body-2 font-weight-light">Megabytes</span>
            </v-row>
          </v-col>
          <v-col cols="4">
            <v-icon class="mt-10 mr-7" color="#80DEEA"> aspect_ratio </v-icon>
          </v-col>
        </v-row>
      </v-card>

      <v-card height="100" width="220" class="mt-7 mr-3 rounded-0">
        <v-row>
          <v-col cols="8">
            <v-row align="center">
              <span class="mt-8 ml-7 title font-weight-bold">
                {{ item.ALLOCATION_TYPE }}
              </span>
            </v-row>
            <v-row align="center">
              <span class="body-2 mt-4 ml-7">Allocation Type</span>
            </v-row>
          </v-col>
          <v-col cols="4">
            <v-icon class="mt-10 mr-7" color="#9575CD">
              system_update_alt
            </v-icon>
          </v-col>
        </v-row>
      </v-card>
    </v-row>

    <v-row>
      <v-col>
        <v-card class="mt-4 ml-13 rounded-0" height="332" width="685">
          <stackedTimestamps
            :width="500"
            :height="300"
            :id="$route.params.id"
          ></stackedTimestamps>
        </v-card>
      </v-col>
      <v-col class="ml-n5">
        <v-card v-if="datafiles.length == 0" flat class="mt-4" width="682">
          <v-alert border="right" colored-border type="error" elevation="2">
            No datafiles recorded!
          </v-alert>
        </v-card>
        <v-virtual-scroll
          v-else
          :items="datafiles"
          max-height="348"
          :item-height="50"
        >
          <template v-slot:default="{ item }">
            <v-card class="mt-4" max-width="682" tile>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="body-2"
                    >{{ item.FILENAME }}
                  </v-list-item-title>
                </v-list-item-content>
                <v-list-item-action>
                  <v-btn depressed small @click="toDataFile(item)">
                    View DataFile
                    <v-icon color="orange darken-4" right>
                      mdi-open-in-new
                    </v-icon>
                  </v-btn>
                </v-list-item-action>
              </v-list-item>
            </v-card>
          </template>
        </v-virtual-scroll>
      </v-col>
    </v-row>

    <v-card flat color="transparent" class="ml-14 mt-5">
      <span class="overline font-weight-bold">Utilizadores</span>
    </v-card>
    <v-row>
      <v-card v-if="users.length == 0" flat class="ml-16 mt-5" width="685" color="transparent">
        <v-alert border="right" colored-border type="error" elevation="2">
          No users recorded!
        </v-alert>
      </v-card>
      <v-card v-else width="685" class="ml-16 mt-5 rounded-0">
        <v-tabs
          v-model="tab"
          background-color="#ddd4d1"
          color="black"
          show-arrows
        >
          <v-tabs-slider color="#837672"></v-tabs-slider>
          <v-tab
            v-for="(item, index2) in timestamps"
            :key="index2"
            class="text-start"
          >
            {{ timestamp(item.TIMESTAMP) }}
          </v-tab>
        </v-tabs>
        <v-tabs-items v-model="tab">
          <v-tab-item v-for="(item, index1) in timestamps" :key="index1">
            <v-card flat>
              <v-card-text>
                <v-list>
                  <v-list-item v-for="(user, index) in users" :key="index">
                    <v-list-item-content>
                      <v-list-item-title
                        v-text="user.USERNAME"
                      ></v-list-item-title>
                      <v-list-item-subtitle class="text-capitalize">
                        {{ user.ACCOUNT_STATUS }}
                      </v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-avatar>
                      <v-img :src="item.avatar"></v-img>
                    </v-list-item-avatar>
                  </v-list-item>
                </v-list>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs-items>
      </v-card>
    </v-row>
    <v-card color="transparent" flat height="300"></v-card>
    <Footer />
  </div>
</template>

<script>
import axios from "axios";
import Navbar from "@/components/navBar.vue";
import stackedTimestamps from "@/components/stackedTimestamps";
import moment from "moment/moment";
import Footer from "@/components/Footer.vue"

export default {
  name: "Tablespace",
  props: ["id", "id2"],

  data() {
    return {
      item: [],
      datafiles: [],
      timestamps: [],
      users: [],
      tab: null,
    };
  },
  created: async function () {
    let response = await axios.get(
      "http://localhost:5001/tablespacedata/" + this.id + "/" + this.id2
    );
    this.item = response.data.rows[0];

    let response2 = await axios.get(
      "http://localhost:5001/getDataFilesTablespace/" + this.id + "/" + this.id2
    );
    this.datafiles = response2.data.rows;

    let response3 = await axios.post("http://localhost:5001/timestamp", {
      table: "TABLESPACES",
    });
    this.timestamps = response3.data.rows;

    let response4 = await axios.get(
      "http://localhost:5001/getUsersTableSpaceTimeStamp/" +
        this.id +
        "/" +
        this.id2
    );
    this.users = response4.data.rows;
  },
  methods: {
    toDataFile: function (item) {
      var nid = item.FILENAME.replace(/\//g, "!");
      this.$router.push("/datafile/" + nid + "/" + item.TIMESTAMP);
    },
    timestamp: function (time) {
      return moment(time).format("MMM DD, YYYY HH:mm:ss");
    },
  },
  components: {
    Navbar,
    stackedTimestamps,
    Footer
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