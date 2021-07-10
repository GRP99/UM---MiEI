<template>
  	<div id="maintenance">
		<h3 style="text-align:center"><b>{{ ida.toUpperCase() }}</b></h3>
    	<div>
    		<v-data-table :headers="headers" :items="author_pubs" item-key="id" class="elevation-1" :search="search" :custom-filter="filterOnlyCapsText">
				<template v-slot:top>
					<v-text-field v-model="search" label="Search (UPPER CASE ONLY)" class="mx-4"></v-text-field>
				</template>
				
    		</v-data-table>
  		</div>
    </div>
</template>

<script>
import axios from "axios";

export default {
	name: "Author",

  	props: ["ida"],

  	data() {
        return {
            author_pubs: [],
			search: '',
        };
    },

  	created: function () {
    	axios.get("http://localhost:8080/api-server/authors/" + this.ida)
		.then((res) => {
            this.author_pubs = res.data;
        })
		.catch((e) => console.log("ERROR in get author:" + e));
  	},

	computed: {
      	headers () {
        	return [
          		{ text: 'ID', align: 'start', value: 'id_pub'},
          		{ text: 'Title', value: 'title' },
          		{ text: 'Type', value: 'type' },
          		{ text: 'Year', value: 'year' },
        	]
      	},
    },

	methods: {
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