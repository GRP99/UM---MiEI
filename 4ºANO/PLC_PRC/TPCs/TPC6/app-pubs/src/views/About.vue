<template>
  <div id="maintenance" class="back">
    <Navbar v-bind:title="About" />
    <v-card class="mx-auto justify-center" color="transparent" flat>
      	<v-row justify="center" align="start">
        	<v-col>
          		<v-card class="mx-auto mt-4 text-center justify-center" flat color="transparent" >
        			<v-card-title class="headline justify-center font-weight-medium"> TPC6 </v-card-title>
          		</v-card>
        	</v-col>
      	</v-row>

      	<v-row align="center" justify="center">
        	<v-col>
          	<v-row justify="center" align="start">
            	<v-card class="mx-auto justify-center" color="transparent" flat>
              		<v-card-title class="font-weight-light"> <strong>Author</strong> : Gonçalo Rodrigues Pinto </v-card-title>
              		<v-card-text class="text-center">MiEI - 4ºAno - 2ºSemestre - PRC 20/21</v-card-text>
			  		<v-card-text class="text-center">
				  		<a href="mailto:a83732@alunos.uminho.pt"> a83732@alunos.uminho.pt</a>
					</v-card-text>
			  		<v-card-text class="text-center">
						<a href="https://github.com/GRP99/PRC2021" target="_blank">
                			<img src="https://raw.githubusercontent.com/github/explore/78df643247d429f6cc873026c0622819ad797942/topics/github/github.png" alt="Github" style="width:75px;height:75px;"/>
						</a>
					</v-card-text>
            </v-card>
          </v-row>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import Navbar from "@/components/navBar.vue";

export default {
	data: () => ({
		About: "About"
	}),
	
  	components: {
    	Navbar,
  	},
};
</script>

<style>
#maintenance {
	background-color: rgb(255, 255, 255);
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