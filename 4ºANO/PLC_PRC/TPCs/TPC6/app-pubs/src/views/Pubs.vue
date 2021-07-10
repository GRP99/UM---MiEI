<template>
  	<div id="maintenance">
    	<Navbar v-bind:title="About" />

    	<div>
    		<v-data-table :headers="headers" :items="pubs" item-key="id" class="elevation-1" :search="search" :custom-filter="filterOnlyCapsText" @click:row="getPub">
				<template v-slot:top>
					<v-text-field v-model="search" label="Search (UPPER CASE ONLY)" class="mx-4"></v-text-field>
				</template>
				
    		</v-data-table>
  		</div>
    </div>
</template>

<script>
import axios from "axios";
import Navbar from "@/components/navBar.vue"

export default {
    data() {
        return {
            pubs: [],
			search: '',
			About: "Publications"
        };
    },

    components: {
        Navbar
    },

    created: async function () {
        axios.get("http://localhost:8080/api-server/pubs")
		.then((res) => {
            this.pubs = res.data;
        })
		.catch((e) => console.log("ERROR in get pubs:" + e));
    },

	computed: {
      	headers () {
        	return [
          		{ text: 'ID', align: 'start', value: 'id'},
          		{ text: 'Title', value: 'title' },
          		{ text: 'Type', value: 'type' },
          		{ text: 'Year', value: 'year' },
        	]
      	},
    },

	methods: {
    	getPub: function (value) {
      		this.$router.push("/pubs/" + value.id);
    	},

		filterOnlyCapsText (value, search, item) {
        	return value != null && search != null && typeof value === 'string' && value.toString().toLocaleUpperCase().indexOf(search) !== -1
      	},
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