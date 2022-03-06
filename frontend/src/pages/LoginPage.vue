<template>
    <div id="login">
        <form id="login-form" class="login-form" @submit.prevent="processForm(loginData)">
            <div>
                <img src="../img/c(h)at.png" alt="Avatar" class="avatar">
            </div>
            <div class="form-group">
                <h1>Login</h1>      
                <div>   
                    <input type="text" name="username" placeholder="Username" />
                </div>
                <div>
                    <input type="password" name="password" placeholder="Password" />
                </div>
                <div>
                    <input type="submit" value="Engedj be!">
                </div>
            </div>
        </form>
        <br />
        <a href="regist">Regisztrálok</a>
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
    
  img{
      width: 10%;
        border-radius: 50%;
 }
</style>