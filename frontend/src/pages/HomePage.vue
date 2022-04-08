<template>
  <div class="HomePage page">
    <div v-if="this.isLoggedIn" class="chat-content">
      <Chat />
    </div>

    <div v-if="!this.isLoggedIn" class="welcome-content">
      <h1>Üdvözöllek!</h1>
      <p>Ahhoz, hogy chatelj és játszhass jelentkezz be!</p>
      <RouterLink to="/login">Bejelentkezés</RouterLink>
    </div>
  </div>
</template>

<script lang="ts">
    import { mapGetters } from "vuex";
    import Chat from '../components/Chat.vue'

    export default {
        name: 'HomePage',
        components: {
            Chat
        },
        computed: {
            ...mapGetters(["isLoggedIn"])
        }
    }
</script>

<style scoped>
  .welcome-content p {
    padding: 0;
    margin: 0;
  }
</style>
