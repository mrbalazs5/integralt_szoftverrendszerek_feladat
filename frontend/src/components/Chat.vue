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
              <span v-if="message.text">{{ message.text }}</span>

              <img
                  v-if="message.file"
                  v-bind:alt="message.file"
                  v-bind:src="'http://localhost:9000/api/files/' + message.file"
              />
            </div>

            <div class="message-meta">
              <span class="sender">{{ message.senderName }}</span>

              <span class="time">{{ message.timeStamp }}</span>
            </div>
          </div>
        </div>

        <div class="input">
          <div class="message-input-wrapper">
            <input v-model="newMessage" name="message-input" class="message-input" type="text" >
            <button class="send-button" v-on:click="sendMessage(newMessage)"><div>&#9993;</div></button>
          </div>

          <label for="send-image" class="image-input-button">
            <IconSendImage class="send-image-icon" />
            <input id="send-image" class="image-input" type="file" ref="uploadImage" @change="onImageUpload()">
          </label>
        </div>
    </div>
</template>

<script lang="ts">
    import IconSendImage from './icons/IconSendImage.vue';
    import { mapGetters } from "vuex";
    import axios from "axios";
 
    type MessageType = {
        text: string,
        senderId: number,
        senderName: string,
        timeStamp: string,
        file?: string
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
        components: {
          IconSendImage
        },
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
          setTimeout(() => this.scrollDownMessages(), 500);
        },
        methods: {
            scrollDownMessages() {
              const messages = this.$el.querySelector('.messages');

              messages.scrollTop = messages?.lastElementChild?.offsetTop || 0;
            },
            onImageUpload() {
              const file = this.$refs?.uploadImage?.files?.[0] || null;

              const imageFormData = new FormData();
              imageFormData.append("file", file);

              axios({
                url: 'http://localhost:9000/api/upload-image',
                data: imageFormData,
                method: 'POST',
                headers: {
                  'Accept': 'application/json',
                  'Content-type': 'multipart/form-data'
                }
              }).then(response => {
                const fileName = response?.data?.fileName;

                this.sendMessage('', fileName);
              }).catch(e => {
                console.error(e);
              });
            },
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
            loadRoom() {
              this.socket.send("");
            },
            changeChatColor(color: string) {
              const colorData = {
                chatColor: color
              };

              this.socket.send(JSON.stringify(colorData));
            },
            sendMessage(text: string, fileName?: string) {
                if(!text && !fileName) {
                  return;
                }

                const user = this.currentUser;
                const timeStamp = new Date().toLocaleString();
                const message: MessageType = {
                    text,
                    senderId: user.id,
                    senderName: user.username,
                    timeStamp: timeStamp,
                    file: fileName || null
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

    .message-input-wrapper {
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

    .image-input {
      display: none;
    }

    .image-input-button {
      width: fit-content;
      margin-top: 10px;
      border: 1px solid #ccc;
      padding: 6px 12px;
      cursor: pointer;
      transition: 0.5s;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .image-input-button svg {
      transition: 0.5s;
    }

    .image-input-button:hover {
      background-color: #45a049;
      color: white;
    }

    .image-input-button:hover svg {
      fill: white;
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

    .message-text img {
      width: 100%;
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