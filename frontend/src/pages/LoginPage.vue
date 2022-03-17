<template>
    <div class="LoginPage page">
        <h1>Bejelentkezés</h1>

        <form id="login-form" class="login-form" @submit.prevent="processForm(loginData)">
            <img src="../img/c(h)at.png" alt="Avatar" class="avatar">

            <div class="form-group">
                <div class="input-wrapper">
                    <input type="text" name="username" placeholder="Felhasználónév" />
                </div>
                <div class="input-wrapper">
                    <input type="password" name="password" placeholder="Jelszó" />
                </div>
                <div class="input-wrapper">
                    <input type="submit" value="Engedj be!">
                </div>
            </div>
        </form>

        <RouterLink to="/registration">Regisztráció</RouterLink>
    </div>
</template>

<script lang="ts">
    export default {
        name: 'LoginPage'
    }
</script>


<style scoped>
    input, select {
        width: 100%;
        padding: 12px;
        margin: 8px 0;
        box-sizing: border-box;
        border-radius: 4px;
        border: 2px solid purple;
        alignment: center;
        transition: 0.5s;
    }

    option {
        padding: 12px;
        margin: 8px 0;
    }

    label {
        padding: 12px 12px 12px 0;
        display: inline-block;
        alignment: center;
        width: 50%;
        margin-top: 6px;
        font-weight: bold;
        font-size: larger;
    }

    input:focus {
        background-color: lightblue;
    }

    input[type = submit] {
        font-weight: bold;
        font-size: larger;
    }

    input[type=submit]:hover {
        background-color: #45a049;
        color: white;
    }
    
    img {
        max-width: 150px;
        border-radius: 50%;
    }

    .login-form {
        max-width: 300px;
        margin: 0 auto;
    }
</style>