<template>
  <div class="RegistrationPage page">
    <h1>Regisztráció</h1>

    <form id="registration-form" class="registration-form" @submit.prevent="processForm(registData)">
      <div class="input-wrapper">
        <label for="name">Felhasználónév</label>
        <input name="name" placeholder="Felhasználónév" required="required" v-model="registData.username">
      </div>

      <div class="input-wrapper">
        <label for="email">E-mail cím</label>
        <input type="email" name="email" placeholder="E-mail cím" required="required" v-model="registData.email">
      </div>

      <div class="input-wrapper">
        <label for="password">Jelszó</label>
        <input name="password"  placeholder="Jelszó" type="password" required="required" minlength="8" v-model="registData.password">
      </div>

      <input type="submit" value="Regisztrálok!">

      <div v-if="errors.length > 0" class="errors">
                <ul>
                    <li v-for="(error, k) in errors" :key="k">
                        {{ error }}
                    </li>
                </ul>
     </div>

    </form>
  </div>

</template>

<script lang="ts">
    import axios from "axios";
    import { mapActions } from "vuex";

    export default {
        name: 'RegistrationPage',
        data() {
            return {
                registData: {
                    username: '',
                    email:'',
                    password: ''
                },
                errors: []
            }
        },
        methods: {
            ...mapActions(["/registration"]),
            processForm: function(registData) {
                axios
                    .post("http://localhost:9000/api/auth/registration", {
                        username: registData.username,
                        email: registData.email,
                        password: registData.password
                    })
                    .then(response => {
                        console.log(response);
                        alert('Yay! :D')
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
        alignment: center;
        transition: 0.5s;
    }

    option {
        padding: 12px;
        margin: 8px 0;
    }

    label {
        display: block;
        width: 100%;
        margin-top: 6px;
        font-weight: bold;
        font-size: larger;
        text-align: left;
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

    .registration-form {
        max-width: 300px;
        margin: 0 auto;
    }
</style>