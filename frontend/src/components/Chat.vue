<template>
    <div class="Chat">
        <select
            class="color-selector" v-model="chatColor"
            v-bind:style= "{backgroundColor: this.chatColor}"
            v-on:change="changeChatColor(chatColor)"
        >
          <option
              v-for="(color, k) in this.availableColors" :key="k" :value="color"
              v-bind:style= "{backgroundColor: color}"
          ></option>
        </select>
        <div class="messages">
          <div
              class="message"
              v-bind:class="{'own-message': message.senderId === this.currentUser.id}"
              v-for="(message, k) in this.messages"
              :key="k"
          >
            <div
                class="message-text"
                v-bind:style= "[message.senderId === this.currentUser.id ? {backgroundColor: this.chatColor} : {}]"
            >
              {{ message.text }}
            </div>

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
        messages: MessageType[],
        color: string
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
                newMessage: "",
                chatColor: '#4848fd',
                availableColors: [
                  '#4848fd',
                  '#088522',
                  '#da5812',
                  '#be2828',
                  '#8300b0',
                  '#06c9c4'
                ]
            }        
        },
        created() {
            this.initConnection();
        },
        updated() {
          this.scrollDownMessages();
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

                    this.chatColor = room.color;
                    this.messages = room.messages.map((message) => {
                      return JSON.parse(message);
                    });
                };

                this.socket.onerror = (err: ErrorEvent) => {
                    console.error('WebSocket error', err);
                };

            },
            resetInput() {
                this.newMessage = ''
            },
            scrollDownMessages() {
              const messages = this.$el.querySelector('.messages');

              messages.scrollTop = messages?.lastElementChild?.offsetTop || 0;
            },
            loadRoom() {
              this.socket.send("");
            },
            changeChatColor(color: string) {
              const colorData = {
                chatColor: color
              };

              this.socket.send(JSON.stringify(colorData));
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
      max-width: 400px;
      margin: auto;
    }

    .color-selector {
      margin-left: auto;
      display: block;
      border-radius: 50px;
      width: 50px;
      margin-bottom: 5px;
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
      scroll-behavior: smooth;
      border: 1px solid #a4a4a4;
      padding: 10px;
    }

    .message {
      color: white;
      margin-bottom: 10px;
    }

    .own-message .message-text {
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