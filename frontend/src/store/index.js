import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";

// import SERVER from '@/api/api';

Vue.use(Vuex)
const SERVER_URL = 'http://localhost:8080'



export default new Vuex.Store({
    state: {
        accessToken: '',
        user: null,
    },
    mutations: {
        // 가입 후 응답 받은 값 저장하기
        SET_USER_DATA(state, userData) {
            //state에 유저 데이터 저장
            state.user = userData;
            // console.log(state.user)
            //로컬스토리지에 저장(문자열만 저장 가능-> JSON 문자열로 변환)
            localStorage.setItem("user", JSON.stringify(userData));
            //엑시오스 헤더에 토큰 추가
            axios.defaults.headers.common[
                "Authorization"
            ] = `Bearer ${userData.token}`;
        },
        //토큰이 담기기 전에 로그인으로 감 async await검색해서 하기
        LOGIN: function(state, accessToken) {
            state.accessToken = accessToken
        },
        LOGOUT() {
          localStorage.removeItem('user')
          location.reload();
        },
    },
    actions: {
        signup({ commit }, credentials) {
            return axios
                .post(`${SERVER_URL}/users`, credentials)
                .then(({ data }) => {
                    console.log(credentials)
                    console.log("user data is", data);
                    commit("SET_USER_DATA", data);
                });
        },
        // 로그인 성공하면 스토어에 액세스 토큰 저장
        Login: function({ commit }, accessToken) {
            commit('LOGIN', accessToken)
        },
        logout([commit]){
          commit('LOUTGOUT')
        }

    },
    getters: {
      //로그인 했는지 확인하는 getter
      loggedIn(state) {
        return !!state.user
      }
    },
    modules: {}
})