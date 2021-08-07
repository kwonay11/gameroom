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
        // 유저 아이디
        id: null,
        accessToken: null,
        password: '',
        // 경험치, 닉네임, 승률
        userData: [],
        nowpage: '',
        // conference 방 번호
        conferenceid: null,
        gamecategory: null,

        // openviudu 
        OV: undefined,
        session: undefined,
        mainStreamManager: undefined,
        publisher: undefined,
        subscribers: [],


    },
    mutations: {

        SET_USER_DATA(state, data) {
            state.user = data;
        },
        LOGIN: function(state, credentials) {
            state.id = credentials.id;
            state.password = credentials.password;
            state.accessToken = credentials.accessToken;
        },
        LOGOUT(state) {
            state.user = null
            state.id = null
            localStorage.removeItem('user')
            axios.defaults.headers.common['Authorization'] = null
        },
        FETCH_USER: function(state, res) {
            state.userData = res.data
        },
        NOWPAGE: function(state, nowpage) {
            state.nowpage = nowpage
        },
        NEW_NICKNAME: function(state, new_nickname) {
            state.userData.nickname = new_nickname
        },
        NEW_PASSWORD: function(state, new_password) {
            state.password = new_password
        },
        CONFERENCE_ID: function(state, conferenceid) {
            state.conferenceid = conferenceid
        },
        GAMECATEGORY: function(state, gamecategory_id) {
            state.gamecategory = gamecategory_id
        },
        OPENVIDU: function(state, data) {
            console.log('72 ' + data.OV)
            state.OV = data.OV,
            state.session = data.session,
            state.mainStreamManager = data.mainStreamManager,
            state.publisher = data.publisher,
            state.subscribers = data.subscribers
        }
        

        
    },

    actions: {
        signup({ commit }, credentials) {
            return axios
                .post(`${SERVER_URL}/users`, credentials)
                .then(({ data }) => {
                    commit("SET_USER_DATA", data);
                });
        },
        login: function({ commit }, credentials) {
            commit('LOGIN', credentials)
        },
        logout({ commit }) {
            commit("LOGOUT");
        },
        fetchUser: function({ commit }, id) {
            axios.get(`${SERVER_URL}/users/${id}`)
                .then((res) => {
                    commit('FETCH_USER', res)
                })
        },
        nowpage: function({ commit }, nowpage) {
            commit('NOWPAGE', nowpage)
        },
        newnickname: function({ commit }, content) {
            axios.defaults.headers.common[
                "Authorization"
            ] = `Bearer ${this.state.accessToken}`;

            axios.put(`${SERVER_URL}/users/nickname/${this.state.id}`, content)
                .then(() => {
                    commit('NEW_NICKNAME', content.nickname)
                })
        },
        newpassword: function({ commit }, content) {

            axios.defaults.headers.common[
                "Authorization"
            ] = `Bearer ${this.state.accessToken}`;

            axios.put(`${SERVER_URL}/users/${this.state.id}`, content)
                .then(() => {
                    commit('NEW_PASSWORD', content.changePassword)
                })
        },
        joinSession: function({ commit }, contents) {
            return new Promise((resolve, reject) => {
                axios.defaults.headers.common[
                    "Authorization"
                ] = `Bearer ${this.state.accessToken}`;

                axios.post(`${SERVER_URL}/conferences`, contents)
                    .then((res) => {
                        // console.log('sdsdsdsd')
                        // console.log(commit);
                        console.log(res.data.roomId)
                        commit('CONFERENCE_ID', res.data.roomId)
                        resolve();
                    })
                    .catch(() => {
                        reject();
                    })
            })
        },
        gamecategory: function({ commit }, gamecategory_id) {
            commit('GAMECATEGORY', gamecategory_id)
        },
        openvidu: function({ commit}, data){
            
            commit('OPENVIDU',data)
        }

    },
    getters: {
        loggedIn(state) {
            return state.id;
        }
    },
    modules: {}
})