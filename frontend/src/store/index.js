import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import { OpenVidu } from 'openvidu-browser';
import createPersistedState from 'vuex-persistedstate';


const SERVER_URL = process.env.VUE_APP_SERVER_URL
// axios.defaults.headers.post['Content-Type'] = 'application/json';
const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

// axios.defaults.headers.common["Authorization"] = `Bearer ${$store.state.accessToken}`;


Vue.use(Vuex)



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
        gamecategory: null,

        // conference 방 번호
        conferenceid: null,
        canJoin: null,

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
        gamecategory: function({ commit }, gamecategory_id) {
            commit('GAMECATEGORY', gamecategory_id)
        },

        createConference: function({commit }, conferenceid) {
            commit('CONFERENCE_ID', String(conferenceid))
        },

        checkNumConferences: function({state,dispatch}, ){
            console.log('방에 인원수 체크하는곳')
         // --- Connect to the session with a valid user token ---
         axios.get(`${SERVER_URL}/conferences/${state.conferenceId}`)
         .then((res) => {
            console.log(res.status)
            if(res.status == 200) {
                dispatch('joinSession',{isFull : true});
            }
            else{
                console.log(res)
            }
            
            
        })
        .catch(() => {
            this.$router.push({ name: 'MainPage' })
          
        });
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
    modules: {
        
    }
})