<template>
	<div>
    	<nav>
			<v-app-bar flat app height="75" color="#2877c6">
				<v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
				<v-app-bar flat color="#2877c6" class="text-uppercase black--text">
					<h3 class="font-weight-light"> {{ this.title }} </h3>
				</v-app-bar>
			</v-app-bar>
			<v-layout align-center justify-space-between column fill-height>
				<v-navigation-drawer class="white" width="230" v-model="drawer" temporary height="100%" app >
					<v-list subheader>
						<v-list-item>
							<v-list-item-icon>
								<v-btn icon to="/"> <v-icon>mdi-home</v-icon></v-btn>
							</v-list-item-icon>
							<v-list-item-title>Home</v-list-item-title>
						</v-list-item>
					</v-list>

					<v-list-item link color="grey" to="/pubs">
						<v-list-item-icon>
							<v-icon small>book</v-icon>
						</v-list-item-icon>
						<v-list-item-title class="ml-n4">Publications</v-list-item-title>
					</v-list-item>

					<v-list-item link color="grey" to="/authors">
						<v-list-item-icon>
							<v-icon small>mdi mdi-account</v-icon>
						</v-list-item-icon>
						<v-list-item-title class="ml-n4">Authors</v-list-item-title>
					</v-list-item>

					<template v-slot:append>
						<div class="pa-2">
							<v-btn text class="body-1" block @click="About()">
								<v-icon left>info</v-icon>About
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
	name: "navBar",

	props:["title"],

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
	background-color: rgb(250, 250, 250);
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