<template>
<div>
  <userDetails :id="$route.params.id" :id2="$route.params.id2" />
</div>
</template>

<script>
import userDetails from "@/components/userDetails.vue";

export default {
  components: {
    userDetails,
  },
};
</script>
