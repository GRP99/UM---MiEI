<template>
    <div id="maintenance">
        <Navbar v-bind:title="About"/>
        <Pub :idp="$route.params.idPub"/>
    </div>
</template>

<script>
import Pub from "@/components/Pub.vue"
import Navbar from "@/components/navBar.vue"

export default {
    data: () => ({
		About: "Publication"
	}),

    components: {
        Pub,
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