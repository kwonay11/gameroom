import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)
const SERVER_URL = process.env.VUE_APP_SERVER_URL



export default new Vuex.Store({
    plugins: [
        createPersistedState()
    ],
    state: {
        user: null,
        id: null,
        accessToken: null,
        userData: [],
    },
    mutations: {
        // 가입 후 응답 받은 값 저장하기
        SET_USER_DATA(state, data) {
            state.user = data;
        },

        LOGIN: function(state, id) {
            state.id = id

        },
        LOGOUT(state) {
            state.user = null
            state.id = null
            localStorage.removeItem('user')
            axios.defaults.headers.common['Authorization'] = null
        },
        FETCH_USER: function(state, res) {
            console.log(res.data)
            state.userData = res.data
            console.log(state.userData)
        },

    },
    actions: {
        signup({ commit }, credentials) {
            return axios
                .post(`${SERVER_URL}/users`, credentials)
                .then(({ data }) => {
                    commit("SET_USER_DATA", data);
                });
        },

        login: function({ commit }, id) {
            commit('LOGIN', id)
        },

        logout({ commit }) {
            commit("LOGOUT");
        },
        fetchUser: function({ commit }, id) {
            axios.get(`${SERVER_URL}/users/${id}`)
                .then((res) => {
                    commit('FETCH_USER', res)
                })

        }

    },
    getters: {
        loggedIn(state) {
            return state.id;
        }
    },
    modules: {}
})