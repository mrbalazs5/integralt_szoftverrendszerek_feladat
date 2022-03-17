<template>
  <div class="RegistrationPage page">
    <h1>Regisztráció</h1>

    <form id="registration-form" class="registration-form">
      <div class="input-wrapper">
        <label for="name">Felhasználónév</label>
        <input name="name" placeholder="Felhasználónév" required="required">
      </div>

      <div class="input-wrapper">
        <label for="email">E-mail cím</label>
        <input type="email" name="email" placeholder="E-mail cím" required="required">
      </div>

      <div class="input-wrapper">
        <label for="password">Jelszó</label>
        <input name="password"  placeholder="Jelszó" type="password" required="required">
      </div>

      <input type="submit" value="Regisztrálok!">
    </form>
  </div>

</template>

<script lang="ts">
    export default {
        name: 'RegistrationPage'
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

    .input-wrapper {
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
    }

    option {
        padding: 12px;
        margin: 8px 0;
    }

    label {
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