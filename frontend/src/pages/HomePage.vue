<template>
  <div class="HomePage page">
    Welcome
    <button v-on:click="this.sendMessage()">Click me</button>
  </div>
</template>

<script lang="ts">
    type DataType = {
        socket: WebSocket | null
    }

    export default {
        name: 'HomePage',
        data(): DataType {
            return {
                socket: null
            }
        },
        created() {
            this.initConnection();
        },
        methods: {
            initConnection() {
                this.socket = new WebSocket("ws://localhost:9000/api/socket/");

                this.socket.onopen = function(event: Event) {
                    console.log('Connected.');
                };

                this.socket.onmessage = function(event: MessageEvent) {
                    const data = event.data;

                    console.log('Message: ', data);
                };

                this.socket.onerror = function(err: ErrorEvent) {
                    console.error('WebSocket error', err);
                };
            },
            sendMessage() {
                this.socket.send("Hello from client!");
            }
        },
    }
</script>
