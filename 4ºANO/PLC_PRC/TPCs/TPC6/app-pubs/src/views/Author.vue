<template>
    <div id="maintenance">
        <Navbar v-bind:title="About" />
        <Author :ida="$route.params.idAuthor"/>
    </div>
</template>

<script>
import Author from "@/components/Author.vue"
import Navbar from "@/components/navBar.vue"

export default {
    data: () => ({
		About: "Author"
	}),

    components: {
        Author,
        Navbar
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