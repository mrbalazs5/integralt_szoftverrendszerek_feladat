<template>
    <div class="Chat">
        <div class="messages">
          <div
              class="message"
              v-bind:class="{'own-message': message.senderId === this.currentUser.id}"
              v-for="(message, k) in this.messages"
              :key="k"
          >
            <div class="message-text">{{ message.text }}</div>

            <div class="message-meta">
              <span class="sender">{{ message.senderName }}</span>

              <span class="time">{{ message.timeStamp }}</span>
            </div>
          </div>
        </div>

        <div class="input">
          <input v-model="newMessage" name="message-input" class="message-input" type="text" >
          <button class="send-button" v-on:click="sendMessage(newMessage)"><div>&#9993;</div></button>
        </div>
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

    type RoomType = {
        messages: MessageType[]
    };

    type DataType = {
        socket: WebSocket | null,
        messages: MessageType[],
        newMessage: string
    }
  
    export default {
        name: "Chat",
        data(): DataType {
            return {               
                socket: null,
                messages: [],
                newMessage: ""
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

                    this.loadRoom();
                };

                this.socket.onmessage = (event: MessageEvent) => {
                    const room: RoomType = JSON.parse(event.data);

                    this.messages = room.messages.map((message) => {
                      return JSON.parse(message);
                    });
                };

                this.socket.onerror = (err: ErrorEvent) => {
                    console.error('WebSocket error', err);
                };

            },
            resetInput(){
                this.newMessage = ''
            },
            loadRoom() {
              this.socket.send("");
            },
            sendMessage(text: string) {
                if(!text) {
                  return;
                }

                const user = this.currentUser;
                const timeStamp = new Date().toLocaleString();
                const message: MessageType = {
                    text,
                    senderId: user.id,
                    senderName: user.username,
                    timeStamp: timeStamp
                };

                this.socket.send(JSON.stringify(message));

                this.resetInput();
            },
        },
        computed: {
            ...mapGetters(["currentUser"])
        }
    }
</script>

<style scoped>
    .Chat {
      max-width: 300px;
      margin: auto;
    }

    .input {
      width: 100%;
      height: 30px;
      margin-top: 5px;
      display: flex;
    }

    .message-input {
        flex: 1;
        padding: 2px 5px;
        outline: none;
        font-size: 16px;
        border-radius: 0;
        border: 1px solid black;
    }

    .send-button {
        display: flex;
        box-sizing: content-box;
        align-items: center;
        justify-content: center;
        font-weight: bold;
        font-size: 20px;
        line-height: 20px;
        min-width: 30px;
        transition: 0.5s;
        border: 1px solid black;
        border-left: none;
    }

    .send-button:hover {
        background-color: #45a049;
        color: white;
    }

    .messages {
      height: 400px;
      overflow-y: auto;
    }

    .message {
      color: white;
      margin-bottom: 10px;
    }

    .own-message .message-text {
      background-color: #4848fd;
      margin-left: auto;
    }

    .own-message .message-meta {
      text-align: right;
    }

    .message-text {
      background-color: #646464;
      border-radius: 5px;
      width: fit-content;
      padding: 10px;
      text-align: left;
    }

    .message-meta {
      color: #646464;
      margin-top: 5px;
      text-align: left;
    }

    .sender {
      margin-right: 5px;
      font-weight: bold;
    }
</style>