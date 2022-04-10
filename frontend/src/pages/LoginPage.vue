<template>
    <div class="LoginPage page">
        <h1>Bejelentkezés</h1>

        <form id="login-form" class="login-form" @submit.prevent="processForm(loginData)">
            <img src="../img/c(h)at.png" alt="Avatar" class="avatar">

            <div class="form-group">
                <div class="input-wrapper">
                    <input
                        v-model="loginData.username"
                        type="text"
                        name="username"
                        placeholder="Felhasználónév"
                    />
                </div>
                <div class="input-wrapper">
                    <input
                        v-model="loginData.password"
                        type="password"
                        name="password"
                        placeholder="Jelszó"
                    />
                </div>
                <div class="input-wrapper">
                    <input type="submit" value="Engedj be!">
                </div>
            </div>

            <div v-if="errors.length > 0" class="errors">
                <ul>
                    <li v-for="(error, k) in errors" :key="k">
                        {{ error }}
                    </li>
                </ul>
            </div>
        </form>

        <RouterLink to="/registration">Regisztráció</RouterLink>
    </div>
</template>

<script lang="ts">
    import axios from "axios";
    import { mapActions } from "vuex";
    import type { UserType } from "../types";


    export default {
        name: 'LoginPage',
        data() {
            return {
                loginData: {
                    username: '',
                    password: ''
                },
                errors: []
            }
        },
        methods: {
            ...mapActions(["login"]),
            processForm: function(loginData) {
                axios
                    .post("http://localhost:9000/api/auth/login", {
                        username: loginData.username,
                        password: loginData.password
                    })
                    .then(response => {
                        const { id, username, email, accessToken } = response.data;

                        const user: UserType | null = id ? {
                            id,
                            username,
                            email
                        } : null;

                        this.login({accessToken, user});

                        this.$router.push({path: '/'});
                    })
                    .catch(e => {
                        console.error(e);

                        this.errors = e?.response?.data?.errors || [];
                    });
            }
        }
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
        transition: 0.5s;
    }

    option {
        padding: 12px;
        margin: 8px 0;
    }

    label {
        padding: 12px 12px 12px 0;
        display: inline-block;
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
    
    .avatar {
        max-width: 150px;
        border-radius: 50%;
    }

    .login-form {
        max-width: 300px;
        margin: 0 auto;
    }

    .errors ul{
        list-style: none;
        color: red;
        text-align: center;
        padding: 0;
    }
</style>