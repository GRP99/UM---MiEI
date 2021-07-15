<template>
  <div>
    <nav>
      <v-app-bar flat app height="75" color="#C62828">
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
        <v-app-bar flat color="#C62828" class="text-uppercase black--text">
          <h3 class="font-weight-light">Menu</h3>
        </v-app-bar>
      </v-app-bar>
      <v-layout align-center justify-space-between column fill-height>
        <v-navigation-drawer
          class="white"
          width="230"
          v-model="drawer"
          temporary
          height="100%"
          app
        >
          <v-list subheader>
            <v-list-item>
              <v-list-item-icon>
                <v-btn icon to="/"> <v-icon>mdi-home</v-icon></v-btn>
              </v-list-item-icon>

              <v-list-item-title>DbMonitor</v-list-item-title>
            </v-list-item>
          </v-list>

          <v-subheader>Users</v-subheader>

          <v-list-item link to="/monitor/users/" color="grey">
            <v-list-item-icon>
              <v-icon small>schedule</v-icon>
            </v-list-item-icon>
            <v-list-item-title class="ml-n4">Timestamps</v-list-item-title>
          </v-list-item>

          <v-list-item link to="/sessions" color="grey">
            <v-list-item-icon>
              <v-icon small>account_box</v-icon>
            </v-list-item-icon>
            <v-list-item-title class="ml-n4">Sessions</v-list-item-title>
          </v-list-item>

          <v-divider></v-divider>

          <v-subheader>Tablespaces</v-subheader>

          <v-list-item link to="/tablespaces" color="grey">
            <v-list-item-icon>
              <v-icon small>poll</v-icon>
            </v-list-item-icon>
            <v-list-item-title class="ml-n4">Tablespaces</v-list-item-title>
          </v-list-item>

          <v-list-item link to="/datafiles" color="grey">
            <v-list-item-icon>
              <v-icon small>description</v-icon>
            </v-list-item-icon>
            <v-list-item-title class="ml-n4">Datafiles</v-list-item-title>
          </v-list-item>

          <v-divider></v-divider>

          <!-- THIS -->
          <v-subheader>CPU</v-subheader>

          <v-list-item link color="grey" to="/cpu/SYS">
            <v-list-item-icon>
              <v-icon small>person</v-icon>
            </v-list-item-icon>
            <v-list-item-title class="ml-n4">SYS</v-list-item-title>
          </v-list-item>

          <v-list-item link color="grey" to="/cpu/SYSTEM">
            <v-list-item-icon>
              <v-icon small>person</v-icon>
            </v-list-item-icon>
            <v-list-item-title class="ml-n4">SYSTEM</v-list-item-title>
          </v-list-item>

          <v-divider></v-divider>

          <v-list-item link color="grey" to="/SGA">
            <v-list-item-icon>
              <v-icon small>public</v-icon>
            </v-list-item-icon>
            <v-list-item-title class="ml-n4">System Global Area</v-list-item-title>
          </v-list-item>

          <v-list-item link color="grey" to="/PGA">
            <v-list-item-icon>
              <v-icon small>public</v-icon>
            </v-list-item-icon>
            <v-list-item-title class="ml-n4">Program Global Area</v-list-item-title>
          </v-list-item>
          <template v-slot:append>
            <div class="pa-2">
              <v-btn text class="body-1" block @click="About()">
                <v-icon left>info</v-icon>Sobre
              </v-btn>
            </div>
          </template>
        </v-navigation-drawer>
      </v-layout>
    </nav>
  </div>
</template>

<script>
export default {
  data() {
    return {
      drawer: false,
      utilizador: {},
    };
  },
  methods: {
    About: function () {
      this.$router.push("/about");
    },
  },
};
</script>

<style>
#maintenance {
  background-color: burlywood;
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