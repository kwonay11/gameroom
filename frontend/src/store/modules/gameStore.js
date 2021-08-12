// import Vue from 'vue'
// import Vuex from 'vuex'
// import axios from "axios";
// import createPersistedState from 'vuex-persistedstate';

// Vue.use(Vuex)
// const SERVER_URL = process.env.VUE_APP_SERVER_URL
import axios from 'axios'
const YOUTUBE_URL = 'https://www.googleapis.com/youtube/v3/search'
const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
const gameStore ={
    state:{
        songs: null,
        selectedSong: null,
        currentSongTime: null,



    },

    mutations: {
        SET_SONGS(state, songs) {
            state.songs = songs;
        },
        SET_SELECTED_SONG(state, song) {
            state.selectedSong = song;
        },
        SET_CURRENT_SONGTIME(state, currentSongTime) {
            state.currentSongTime = currentSongTime
        },
        

        
    },

    actions: {
        // singing
        searchSong({ commit }, keyword) {
            axios.get(YOUTUBE_URL, {
            params: {
                key: API_KEY,
                part: 'snippet',
                type: 'video',
                q: '[KY 금영노래방]' + keyword,
                maxResults: 4
            }
            })
            .then(res => {
                res.data.items.forEach(item => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(item.snippet.title, 'text/html');
                item.snippet.title = doc.body.innerText;
                });
                commit('SET_SONGS', res.data.items);
            });
        },
        selectSong({ state }, song) {
            state.session.signal({
            type: 'song',
            data: JSON.stringify(song),
            to: [],
            })
        },
        checkSongSync({ state }, currentSongTime) {
            state.session.signal({
            type: 'songsync',
            data: currentSongTime + 0.05,
            to: [],
            })
        },
        }

    
}

export default gameStore