import Vue from 'vue'
import Vuex from 'vuex'
// import axios from "axios";
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)
    // const SERVER_URL = process.env.VUE_APP_SERVER_URL



export default new Vuex.Store({
    plugins: [
        createPersistedState()
    ],
    state: {

    },

    mutations: {},

    actions: {

    },
    getters: {

    },

    modules: {}
})