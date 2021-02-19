<template>
  <Tablespace :id="$route.params.id" :id2="$route.params.id2" />
</template>

<script>
import Tablespace from "@/components/Tablespace.vue";
export default {
  components: {
    Tablespace,
  },
};
</script>