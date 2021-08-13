import Vue from 'vue'
import Vuex from 'vuex'
// import axios from "axios";
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)
const SERVER_URL = process.env.VUE_APP_SERVER_URL

const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
// const API_URL = 'https://www.googleapis.com/youtube/v3/search'


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

        inputValue: '',
        videos: [],
        selectedVideo: '', // SongDetail.vue 로 보내고, 출력


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
            this.$axios.defaults.headers.common['Authorization'] = null
        },
        FETCH_USER: function(state, res) {
            state.userData = res.data
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
        }
    },

    actions: {
        signup({ commit }, credentials) {
            return this.$axios
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
            this.$axios.get(`${SERVER_URL}/users/${id}`)
                .then((res) => {
                    commit('FETCH_USER', res)
                })
        },
        newnickname: function({ commit }, content) {
            this.$axios.defaults.headers.common[
                "Authorization"
            ] = `Bearer ${this.state.accessToken}`;

            this.$axios.put(`${SERVER_URL}/users/nickname/${this.state.id}`, content)
                .then(() => {
                    commit('NEW_NICKNAME', content.nickname)
                })
        },
        newpassword: function({ commit }, content) {

            this.$axios.defaults.headers.common[
                "Authorization"
            ] = `Bearer ${this.state.accessToken}`;

            this.$axios.put(`${SERVER_URL}/users/${this.state.id}`, content)
                .then(() => {
                    commit('NEW_PASSWORD', content.changePassword)
                })
        },
        joinSession: function({ commit }, contents) {
            return new Promise((resolve, reject) => {
                // this.$axios.defaults.headers.common[
                //     "Authorization"
                // ] = `Bearer ${this.state.accessToken}`;

                this.$axios.post(`${SERVER_URL}/conferences`, contents)
                    .then((res) => {
                        // console.log(res.data.roomId)
                        commit('CONFERENCE_ID', res.data.roomId)
                        resolve();
                    })
                    .catch(() => {
                        reject();
                    })
            })
        },
        inputsearch: function({ commit }, song) {
            console.log(song)
            const params = {
                key: API_KEY,
                part: 'snippet',
                type: 'video',
                q: song
            }
            // axios.get(API_URL, {
            fetch(`https://www.googleapis.com/youtube/v3/search?key=AIzaSyBVVoBrjdTV12A560GRn9YiuS8kZRleKbQ&part=snippet&type=video&q=${song}`,)
              .then((res) => {
                // console.log(params)
                
                // console.log(res.json())
                console.log('then부분')//여기로 안가요
                // console.log(res)
                // console.log(res.data.items)
                // this.videos = res.data.items
                // console.log(this.videos)
                commit('GAMECATEGORY', res)
                // if (!this.selectedVideo) {
                //   this.selectedVideo = this.videos[0]
                // }
                return res.json();
              })
              .then((json)=> {
                  console.log('두번째 then')
                  console.log(json.items)
                  
              })
              .catch((err) => {
                console.log(params)
                console.log('안된다')
                console.log(err)

              })


        },
        gamecategory: function({ commit }, gamecategory_id) {
            commit('GAMECATEGORY', gamecategory_id)
        },

    },
    getters: {
        loggedIn(state) {
            return state.id;
        }
    },
    modules: {}
})