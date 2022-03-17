<template>
    <div class="LoginPage page">
        <h1>Bejelentkezés</h1>

        <form id="login-form" class="login-form" @submit.prevent="processForm(loginData)">
            <img src="../img/c(h)at.png" alt="Avatar" class="avatar">

            <div class="form-group">
                <div class="input-wrapper">
                    <input
                        v-model="loginData.email"
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

    export default {
        name: 'LoginPage',
        data() {
            return {
                loginData: {
                    email: '',
                    password: ''
                },
                errors: []
            }
        },
        methods: {
            ...mapActions(["login"]),
            processForm: function(loginData) {
                axios
                    .post(`http://localhost:9000/api/login`, {
                        email: loginData.email,
                        password: loginData.password
                    })
                    .then(response => {
                        if(response.status == 200) {
                            const { token, user } = response.data;

                            this.login({token, user});
                            this.$router.push('/');
                        }
                    })
                    .catch(e => {
                        console.log(e);
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
    
    .avatar {
        max-width: 150px;
        border-radius: 50%;
    }

    .login-form {
        max-width: 300px;
        margin: 0 auto;
    }
</style>