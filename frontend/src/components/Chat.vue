<template>
    <div class="Chat">
        <div class="messages">
          <div>
              <table>
                    <tr>
                      <th>Üzenet</th>
                      <th>Küldő</th>
                      <th>Időpont</th>
                    </tr>
                    <tr v-for="(message, k) in this.messages" :key="k">
                      <td>{{ message.text }}</td>
                      <td>{{ message.senderName }}</td>
                      <td>{{ message.timeStamp }}</td>
                    </tr>
                  </table>
                </div>
        </div>
        <input id="message-input" v-model="newMessage" name="message-input" class="message-input" type="text" >
      <button id="send_button" v-on:click="sendMessage(newMessage)">✉️</button>
    </div>
</template>

<script lang="ts">
    import { mapGetters } from "vuex";
    import type { UserType } from "@/types";
 
    type MessageType = {
        text: string,
        senderId: number,
        senderName: string,
        timeStamp: string
    };

    type DataType = {
        socket: WebSocket | null,
        messages: MessageType[]
        users: UserType[]
    }
  
    export default {
        name: "Chat",
        data(): DataType {
            return {               
                socket: null,
                messages: []
            }        
        },
        created() {
            this.initConnection();
        },
        
        methods: {
            initConnection: function() {
                this.socket = new WebSocket("ws://localhost:9000/api/socket/");

                this.socket.onopen = (event: Event) => {
                    console.log('Websocket connected.');
                };

                this.socket.onmessage = (event: MessageEvent) => {
                    const message: MessageType = JSON.parse(event.data);

                    this.messages = [
                        ...this.messages,
                        message
                    ];
                };

                this.socket.onerror = (err: ErrorEvent) => {
                    console.error('WebSocket error', err);
                };

            },
            reset(){
                this.newMessage = ''
            },
            sendMessage(text: string) {
                const user = this.currentUser;
                const timeStamp = new Date().toLocaleString();
                const message: MessageType = {
                    text,
                    senderId: user.id,
                    senderName: user.username,
                    timeStamp: timeStamp
                };

                this.socket.send(JSON.stringify(message));
                this.reset();
            },
        },
        computed: {
            ...mapGetters(["currentUser"])
        }
    }
</script>

<style scoped>
    .message-input {
        width: 100%;
        max-width: 500px;
        margin: 10px auto;
        height: 22px;
        padding: 2px 5px;
        outline: none;
        font-size: 15px;
        border-radius: 0;
        border: 1px solid black;
        position: absolute;
        left: 425px;
        bottom: 0;
    }
    #send_button{
        box-sizing: border-box;
        border-radius: 4px;
        border: 2px solid purple;
        transition: 0.5s;
        font-weight: bold;
        font-size: larger;
        position: absolute;
        right: 370px;
        bottom:7px;

    }
    #send_button:hover {
        background-color: #45a049;
        color: white;
    }

    .messages table {
      margin-left: auto;
      margin-right: auto;
      align-content: center;
      
    }

</style>