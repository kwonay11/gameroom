import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        accessToken: '',
    },
    mutations: {
        LOGIN: function(state, accessToken) {
            state.accessToken = accessToken
        }
    },
    actions: {
        // 로그인 성공하면 스토어에 액세스 토큰 저장
        Login: function({ commit }, accessToken) {
            commit('LOGIN', accessToken)
        }

    },
    modules: {}
})