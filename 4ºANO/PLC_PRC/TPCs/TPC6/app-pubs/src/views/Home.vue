<template>
  	<div id="maintenance">
		<Navbar v-bind:title="About"/>
		<v-card flat class="mx-auto text-center mb-16 mt-16 pt-16" height="250" color="transparent">
			<v-row align="center" justify="center">
				<v-col>
					<v-row>
						<span id="letra" class="headline font-weight-light text--darken pl-13 ml-13" :style="{ color: 'black' }">
							<h1> JCR Publications </h1>
							<p>Welcome to the Web application for exploring the publications of jcr and authors that collaborate with him.</p>
							<p>The application was built using an ontology.</p>
							<p>
								All information uses a data API developed for the purpose that queries the 
								<a href="http://ontobud.di.uminho.pt/" target="_blank">
									Ontobud
								</a> 
								platform.
							</p>
						</span>
					</v-row>
				</v-col>
		</v-row>
		</v-card>
  	</div>
</template>

<script>
import Navbar from "@/components/navBar.vue";

export default {

	components: { 
		Navbar
	},

  	data() {
    	return {
      		drawer: true,
			About: "HOME"
    	};
  	},
};
</script>

<style>
#maintenance {
	background-color: rgb(255, 255, 255);
	height: 75%;
	background-position: center;
	background-repeat: repeat;
	background-size: contain;
	position: relative;
}
#letra {
  	font-family: "Merienda", Helvetica, Arial;
}
</style>