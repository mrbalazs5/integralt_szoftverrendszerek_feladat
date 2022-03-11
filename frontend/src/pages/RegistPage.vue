<template>
  <div id="registrationForm">
    <h1>Registration page</h1>
    <form>
      <div>
        <label for="name">Username: </label>
        <input name="name" placeholder="Username" required="required">
      </div>
      <div>
        <label for="email">Email: </label>
        <input type="email" name="email" placeholder="email" required="required">
      </div>
      <div>
        <label for="password">Password: </label>
        <input name="password"  placeholder="password" type="password" required="required">
      </div>
      <input type="submit" value="Regisztrálok!">
    </form>
  </div>

</template>


<style scoped>
    input, select {
    width: 50%;
    padding: 12px;
    margin: 8px 0;
    box-sizing: border-box;
    border-radius: 4px;
    border: 2px solid purple;
    alignment: center;
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
</style>