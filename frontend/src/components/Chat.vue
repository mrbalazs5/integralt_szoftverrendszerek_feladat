<template>
    <div class="Chat">
        <div class="messages">
            <div v-for="(message, k) in this.messages" :key="k">
                {{ message.text }}
            </div>
        </div>

        <input id="message-input" name="message-input" class="message-input" type="text" >
      <button v-on:click="this.sendMessage('Szia')">Test</button>
    </div>
</template>

<script lang="ts">
    import { mapGetters } from "vuex";

    type MessageType = {
        text: string,
        senderId: number,
        senderName: string,
    };

    type DataType = {
        socket: WebSocket | null,
        messages: MessageType[]
    };

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
            sendMessage(text: string) {
                const user = this.currentUser;

                const message: MessageType = {
                    text,
                    senderId: user.id,
                    senderName: user.username
                };

                this.socket.send(JSON.stringify(message));
            }
        },
        computed: {
            ...mapGetters(["currentUser"])
        }
    }
</script>

<style scoped>
    .message-input {
        width: 100%;
        max-width: 250px;
        margin: 10px auto;
        height: 22px;
        padding: 2px 5px;
        outline: none;
        font-size: 15px;
        border-radius: 0;
        border: 1px solid black;
    }
</style>