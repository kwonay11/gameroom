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
        SET_USER_DATA(state, userData) {
            //state에 유저 데이터 저장
            state.user = userData;
            // console.log('store user 확인')
            // console.log(state.user)

        },

        LOGIN: function(state, id) {
            state.id = id
                // console.log(state.id)
        },
        LOGOUT(state) {
            state.user = null
            state.id = null
            localStorage.removeItem('user')
            axios.defaults.headers.common['Authorization'] = null
        },
        FETCH_USER: function(state, data) {
            state.userData = data
            console.log(state.userData)
        },

    },
    actions: {
        signup({ commit }, credentials) {
            return axios
                .post(`${SERVER_URL}/users`, credentials)
                .then(({ data }) => {
                    // console.log('토큰여부확인')
                    // console.log(data)
                    // console.log(credentials)
                    // console.log("user data is", data);
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
                    commit('FETCH_USER', res.data)
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