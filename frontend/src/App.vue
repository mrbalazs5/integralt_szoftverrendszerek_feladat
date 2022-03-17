<script setup lang="ts">
  import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
    <div id="nav">
        <RouterLink to="/">Főoldal</RouterLink> |
        <RouterLink to="/about">Rólunk</RouterLink> |
        <RouterLink to="/login">Bejelentkezés</RouterLink> |
        <RouterLink to="/registration">Regisztráció</RouterLink>
    </div>

    <main>
        <RouterView />
    </main>
</template>

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

    a {
      font-weight: bold;
      color: #2c3e50;
    }
</style>
