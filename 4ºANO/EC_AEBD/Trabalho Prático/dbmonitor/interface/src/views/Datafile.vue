<template>
  <Datafile :id="$route.params.id" :id2="$route.params.id2" />
</template>

<script>
import Datafile from "@/components/Datafile.vue";

export default {
  components: {
    Datafile,
  },
};
</script>