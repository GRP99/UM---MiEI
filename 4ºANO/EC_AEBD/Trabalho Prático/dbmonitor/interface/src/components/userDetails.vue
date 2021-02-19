<template>
  <div id="maintenance">
    <Navbar />
    <v-card flat>
      <v-list three-line subheader color="#efdcc3">
        <v-subheader>User</v-subheader>

        <v-list-item>
          <v-card class="mx-auto" flat color="#efdcc3">
            <v-row>
              <v-card height="100" width="225" class="mt-7 mx-7 mb-4 rounded-0">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-9 ml-8 title font-weight-bold">
                        {{ user.COMMON }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <span class="body-2 mt-4 ml-8">Common</span>
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="rgba(187,238,17,1)">
                      assistant_photo
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>

              <v-card height="100" width="225" class="mt-7 mx-7 mb-4 rounded-0">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-9 ml-8 title font-weight-bold">
                        {{ user.TEMPORARY_TABLESPACE }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <span class="body-2 mt-4 ml-8">Temporary Tablespace</span>
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="rgba(60,213,255,1)">
                      hourglass_bottom
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>

              <v-card height="100" width="225" class="mt-7 mx-7 mb-4 rounded-0">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-9 ml-8 title font-weight-bold">
                        {{ user.DEFAULT_TABLESPACE }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <span class="body-2 mt-4 ml-8">Default Tablespace</span>
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="rgba(255,156,119,1)">
                      backup_table
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>
              <v-card height="100" width="225" class="mt-7 mx-7 mb-4 rounded-0">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-9 ml-8 title font-weight-bold">
                        {{ user.PROFILE }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <span class="body-2 mt-4 ml-8">Profile</span>
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="rgba(255,99,132,1)">
                      person_pin
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>
            </v-row>
          </v-card>
        </v-list-item>
      </v-list>
      <v-divider></v-divider>

      <v-list three-line subheader color="#efdcc3">
        <v-subheader>Roles for the Current Timestamp</v-subheader>
        <v-card v-if="sessions.length == 0" flat class="mx-auto" width="800">
          <v-alert border="right" colored-border type="error" elevation="2">
            No sessions recorded!
          </v-alert>
        </v-card>
        <v-card width="1050" flat class="mx-auto" v-else>
          <v-data-table
            :headers="headers"
            :items="roles"
            item-key="id"
            class="elevation-1"
          ></v-data-table>
        </v-card>
      </v-list>
      <v-divider> </v-divider>

      <v-list three-line subheader color="#efdcc3">
        <v-subheader>Sessions</v-subheader>
        <v-card v-if="sessions.length == 0" flat class="mx-auto" width="800">
          <v-alert border="right" colored-border type="error" elevation="2">
            No sessions recorded!
          </v-alert>
        </v-card>
        <v-card width="1050" flat class="mx-auto" v-else>
          <v-list-item v-for="(session, index) in sessions" :key="index">
            <v-list-item-content>
              <v-list-item-title class="body-2"
                >{{ timestamp(session.TIMESTAMP) }}
              </v-list-item-title>
            </v-list-item-content>
            <v-list-item-action>
              <v-btn depressed small @click="toSession(session)" class="mt-2">
                View Session
                <v-icon color="orange darken-4" right> mdi-open-in-new </v-icon>
              </v-btn>
            </v-list-item-action>
          </v-list-item>
        </v-card>
      </v-list>
    </v-card>

    <v-card flat height="300" color="transparent"></v-card>

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
import axios from "axios";
import Navbar from "@/components/navBar.vue";
import moment from "moment/moment";
import Footer from "@/components/Footer.vue";
export default {
  props: ["id", "id2"],
  data() {
    return {
      fab: false,
      roles: [],
      user: {},
      sessions: [],
      headers: [
        {
          text: "Role Name",
          align: "start",
          sortable: false,
          value: "ROLE_NAME",
        },
        {
          text: "Authentication Type",
          align: "start",
          sortable: false,
          value: "AUTHENTICATION_TYPE",
        },
      ],
    };
  },

  components: {
    Navbar,
    Footer,
  },
  created: async function () {
    let response2 = await axios.get(
      "http://localhost:5001/getUserNameTS/" + this.id + "/" + this.id2
    );
    this.user = response2.data.rows[0];
    console.log(this.user);

    let response3 = await axios.get(
      "http://localhost:5001/getSessionsUser/" + this.id
    );
    this.sessions = response3.data.rows;

    let response4 = await axios.get(
      "http://localhost:5001/getRolesTS/" + this.id + "/" + this.id2
    );
    this.roles = response4.data.rows;
  },
  methods: {
    toSession: function (session) {
      const format = "DD-MM-YYYY HH:mm:ss";
      const date = moment(session.TIMESTAMP).format(format);
      this.$router.push("/session/" + this.id + "/" + date);
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