import { createStore } from 'vuex';
import Axios from 'axios';
import createPersistedState from 'vuex-persistedstate';
import type { UserType } from "@/types";

const getDefaultState = () => {
    return {
        token: '',
        user: null
    };
};

export default createStore({
    strict: true,
    plugins: [createPersistedState()],
    state: getDefaultState(),
    getters: {
        isLoggedIn: (state): boolean => {
            return !!state.token;
        },
        currentUser: (state): UserType => {
            return state.user;
        }
    },
    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token;
        },
        SET_USER: (state, user: UserType) => {
            state.user = user;
        },
        RESET: state => {
            Object.assign(state, getDefaultState());
        }
    },
    actions: {
        login: ({commit, dispatch}, {accessToken, user}) => {
            commit('SET_TOKEN', accessToken);
            commit('SET_USER', user);
            dispatch;

            Axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
        },
        logout: ({ commit }) => {
            commit('RESET', '');
        }
    }
});