<template>
  <div class="HomePage page">
    Welcome
  </div>
</template>

<script lang="ts">
    export default {
        name: 'HomePage',
        sockets: {
            connect: function () {
                console.log('socket connected')
            },
            customEmit: function (data) {
                console.log('this method was fired by the socket server. eg: io.emit("customEmit", data)')
            }
        },
        created() {
            this.initConnection()
        },
        methods: {
            initConnection() {
                this.$socket.emit('emit_method', {
                    user: "test",
                    message: "test text"
                })
            }
        },
    }
</script>
