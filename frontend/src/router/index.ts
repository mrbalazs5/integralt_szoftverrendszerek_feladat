import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../pages/HomePage.vue'
import AboutPage from '../pages/AboutPage.vue'
import LoginPage from '../pages/LoginPage.vue'
import RegistrationPage from '../pages/RegistrationPage.vue'
import ChatPage from "@/pages/ChatPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage
    },
    {
      path: '/about',
      name: 'about',
      component: AboutPage
    },
    {
      path: '/login',
      name: 'login',
      component: LoginPage
    },
    {
      path: '/registration',
      name: 'registration',
      component: RegistrationPage
    },
    {
      path: '/chat',
      name: 'chat',
      component: ChatPage
    }
  ]
});

export default router
