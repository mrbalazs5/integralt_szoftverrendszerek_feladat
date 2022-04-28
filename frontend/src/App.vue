<template>
    <div id="nav">
        <RouterLink to="/">Főoldal</RouterLink>
        <RouterLink to="/about">Rólunk</RouterLink>
        <RouterLink v-if="!this.isLoggedIn" to="/login">Bejelentkezés</RouterLink>
        <RouterLink v-if="!this.isLoggedIn" to="/registration">Regisztráció</RouterLink>
        <a href="#" v-if="this.isLoggedIn" v-on:click="this.logout()">Logout</a>
    </div>

    <main>
        <RouterView />
    </main>
</template>

<script lang="ts">
    import { RouterLink, RouterView } from 'vue-router'
    import { mapGetters, mapActions } from "vuex";

    export default {
        name: 'App',
        computed: {
            ...mapGetters(["isLoggedIn"])
        },
        methods: {
            ...mapActions(["logout"]),
        }
    }
</script>

<style>
    #app {
      font-family: Avenir, Helvetica, Arial, sans-serif;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
      text-align: center;
      color: #2c3e50;
      background: #FFFFFF;
    }

    #nav {
      padding: 30px;
    }

    #nav a {
        border-right: 2px solid #2c3e50;
        padding: 0 5px;
    }

    #nav a:last-of-type {
        border: none;
    }

    #nav a.router-link-exact-active {
        color: #42b983;
    }

    a {
      font-weight: bold;
      color: #2c3e50;
    }
</style>
