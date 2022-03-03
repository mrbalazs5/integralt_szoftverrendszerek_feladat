import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import VueSocketIO from 'vue-3-socket.io'

const vueSocket = new VueSocketIO({
    debug: true,
    connection: 'http://chat_app_backend:9000',
    vuex: {
        store,
        actionPrefix: 'SOCKET_',
        mutationPrefix: 'SOCKET_'
    },
    options: { path: "/api/socket" } //Optional options
});

createApp(App).use(router).use(store).use(vueSocket).mount('#app');
