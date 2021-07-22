import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";

// import SERVER from '@/api/api';

Vue.use(Vuex)
const SERVER_URL = process.env.VUE_APP_SERVER_URL



export default new Vuex.Store({
  
  state: {
    user: null
  },
  mutations: {
    // 가입 후 응답 받은 값 저장하기
    SET_USER_DATA(state, userData) {
      //state에 유저 데이터 저장
      state.user = userData;
      //로컬스토리지에 저장(문자열만 저장 가능-> JSON 문자열로 변환)
      localStorage.setItem("user", JSON.stringify(userData));
      //엑시오스 헤더에 토큰 추가
      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${userData.token}`;
    }
  },
  actions: {
    signup({ commit }, credentials) {
      return axios
        .post(`${SERVER_URL}/signup`, credentials)
        .then(({ data }) => {
          console.log("user data is", data);
          commit("SET_USER_DATA", data);
        });
    }
  },
  modules: {
  }
})
