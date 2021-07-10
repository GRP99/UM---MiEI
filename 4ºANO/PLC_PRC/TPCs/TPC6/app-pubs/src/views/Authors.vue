<template>
  	<div id="maintenance">
    	<Navbar v-bind:title="About" />

    	<div>
    		<v-data-table :headers="headers" :items="authors" item-key="id" class="elevation-1" :search="search" :custom-filter="filterOnlyCapsText" @click:row="getAuthor">
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
            authors: [],
			search: '',
			About: "AUTHORS"
        };
    },

    components: {
        Navbar
    },

    created: async function () {
        axios.get("http://localhost:8080/api-server/authors")
		.then((res) => {
            this.authors = res.data;
        })
		.catch((e) => console.log("ERROR in get authors:" + e));
    },

	computed: {
      	headers () {
        	return [
          		{ text: 'ID', align: 'start', value: 'id'},
          		{ text: 'Nome', value: 'name' },
          		{ text: 'Total of Publications', value: 'tot' },
        	]
      	},
    },

	methods: {
    	getAuthor: function (value) {
      		this.$router.push("/authors/" + value.id);
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