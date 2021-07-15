<template>
  <Session :id="$route.params.id" :id2="$route.params.id2" />
</template>

<script>
import Session from "@/components/Session.vue";

export default {
  components: {
    Session,
  },
};
</script>