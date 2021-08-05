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

        // openvidu 
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
        // Openvidu
        SET_OV: function(state, OV) {
            state.OV = OV;
        },
        SET_OVTOKEN: function(state, token) {
            state.ovToken = token;
        },
        SET_SESSION: function(state, session) {
            state.session = session;
        },
        SET_MAINSTREAMMANAGER: function(state, mainStreamManager) {
            state.mainStreamManager = mainStreamManager;
        },
        SET_PUBLISHER: function(state, publisher) {
            state.publisher = publisher;
        },
        SET_SUBSCRIBERS: function(state, subscribers) {
            state.subscribers = subscribers;
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
        createConference: function({state,commit, dispatch }, contents) {
            return new Promise((resolve, reject) => {
                axios.defaults.headers.common[
                    "Authorization"
                ] = `Bearer ${state.accessToken}`;
      
                axios.post(`${SERVER_URL}/conferences`, contents)
                    .then((res) => {
                        console.log('방번호 만들기')
                        // console.log(commit);
                        console.log(res.data.roomId)
                        commit('CONFERENCE_ID', res.data.roomId)

                        const contents ={
                          'conferenceId': res.data.roomId,
                          'userid': state.id,
                          'isFull':true,  
                        }
                        dispatch('joinSession', contents)
                        
                        resolve();
                        
                    })
                    .catch(() => {
                        reject();
                    })
            
            })   
        },
        checkNumConferences: function({state,dispatch}){
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
        joinSession: function({commit, dispatch},contents){
            console.log(commit,contents)
            console.log('세션 만들기')
            const OV = new OpenVidu();
			// --- Init a session ---
			const session = OV.initSession();
			// --- Specify the actions when events take place in the session ---
			// On every new Stream received...
            const subscribers = [];
            
            session.on('streamCreated', ({ stream }) => {
				const subscriber = session.subscribe(stream);
				subscribers.push(subscriber);
			});
            console.log('1')
            // On every Stream destroyed...
			session.on('streamDestroyed', ({ stream }) => {
				const index = subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					subscribers.splice(index, 1);
                }
            });
            console.log('2')
            // On every asynchronous exception...
            // 여기서 signal 등록하면됨 
			session.on('exception', ({ exception }) => {
				console.warn(exception);
			});
			session.on('signal:my-chat', (event) => {
				console.log(event.data);
				console.log(event);
			})
			session.on('signal:game',(event) =>{
				console.log('game')
				console.log(event)
			})
            console.log('여기까지')
            // 방에 자리가 있으면 토큰 만들어줌 
            if(contents.isFull){
                console.log(contents.conferenceId);
                dispatch('getToken', contents.conferenceId).then(token => {
                    session.connect(token, {clientData: contents.userid})
                    .then(() => {
                        let publisher = OV.initPublisher(undefined, {
                            audioSource: undefined, // The source of audio. If undefined default microphone
                            videoSource: undefined, // The source of video. If undefined default webcam
                            publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
                            publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
                            resolution: '640x480',  // The resolution of your video
                            frameRate: 30,			// The frame rate of your video
                            insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
                            mirror: true,       	// Whether to mirror your local video or not
                          });
                        commit('SET_OV', OV);
                        commit('SET_MAINSTREAMMANAGER', publisher);
                        commit('SET_PUBLISHER', publisher);
                        commit('SET_SESSION', session);
                        commit('SET_SUBSCRIBERS', subscribers);
                        commit('SET_OVTOKEN', token);
                        session.publish(publisher);

                    })  
                    .catch(error => {
                        console.log('There was an error connecting to the session:', error.code, error.message);
                    })          
                });
            }
        },
        getToken ({ dispatch }, mySessionId) {
            console.log(mySessionId)
            return dispatch('createSession', mySessionId).then(sessionId => dispatch('createToken', sessionId));
        },
        createSession (sessionId) {
            console.log(sessionId)
            return new Promise((resolve, reject) => {
                console.log(OPENVIDU_SERVER_URL,OPENVIDU_SERVER_SECRET,sessionId);
                axios.
                    post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
                        customSessionId : sessionId
                    }), {
                    auth: {
                        username: 'OPENVIDUAPP',
                        password: OPENVIDU_SERVER_SECRET,
                    },
                    
                })
                .then(response => response.data)
                .then(data => resolve(data.id))
                .catch(error => {
                        
                    if (error.response.status === 409) {
                        resolve(sessionId);
                        
                    } else {
                        console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
                        if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
                            location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
                        }
                        reject(error.response);
                    }
                });
            });
        },
        createToken (sessionId) {
            return new Promise((resolve, reject) => {
               axios
                  .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
                     auth: {
                        username: 'OPENVIDUAPP',
                        password: OPENVIDU_SERVER_SECRET,
                     },
                  })
                  .then(response => response.data)
                  .then(data => resolve(data.token))
                  .catch(error => reject(error.response));
            });
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
    modules: {
        
    }
})