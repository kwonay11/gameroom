import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
// import createPersistedState from 'vuex-persistedstate';
import { OpenVidu } from 'openvidu-browser';

Vue.use(Vuex)

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
const SERVER_URL = process.env.VUE_APP_SERVER_URL


const video = {
    namespaced: true,
    // plugins: [
    //     createPersistedState()
    // ],
    state() {
        return {
            // openvidu
            OV: undefined,
            session: undefined,
            mainStreamManager: undefined,
            publisher: undefined,
            subscribers: [],

            conferenceid: null,
            mySessionId: null,
            myUserName: '',
            canJoin: null,
        }
    },
    // getters: {
    //     createSessionId(state, getters, rootState, rootGetters) {
    //         return (rootGetters.conferenceid)
    //     },
    // },

    mutations: {
        SET_OV(state, OV) {
            state.OV = OV
        },
        SET_SESSION(state, session) {
            state.session = session
        },
        SET_MAINSTREAMMANAGER(state, mainStreamManager) {
            state.mainStreamManager = mainStreamManager
        },
        SET_PUBLISHER(state, publisher) {
            state.publisher = publisher
        },
        SET_SUBSCRIBERS(state, subscribers) {
            state.subscribers = subscribers
        },
        SET_CONFERENCE_ID: function(state, conferenceid) {
            state.conferenceid = conferenceid
        },
        SET_CANJOIN: function(state, canjoin) {
            state.canJoin = canjoin
        }
    },

    actions: {
        createConference: function({ state, rootState, commit, }, contents) {
            return new Promise((resolve, reject) => {

                axios.defaults.headers.common[
                    "Authorization"
                ] = `Bearer ${rootState.accessToken}`;
                axios.post(`${SERVER_URL}/conferences`, contents)
                    .then((res) => {
                        // console.log(res.data.roomId)
                        commit('SET_CONFERENCE_ID', res.data.roomId)
                        console.log('여긴?')
                        console.log(`gg ${res.data.roomId}`)
                        console.log(`GGGG ${state.conferenceid}`)
                            // dispatch('joinSession')
                        resolve();
                    })
                    .catch(() => {
                        reject();
                    })
            })
        },

        joinSession: function({ state, rootState, commit, dispatch }) {
            console.log('dfdfdf')



            const OV = new OpenVidu();
            // --- Init a session ---
            const session = OV.initSession();

            // console.log(session)
            // --- Specify the actions when events take place in the session ---
            // On every new Stream received...
            const subscribers = [];
            // commit('SET_SESSION', session);


            // On every new Stream received...
            // const subscribers = [];
            session.on('streamCreated', ({ stream }) => {
                const subscriber = session.subscribe(stream);
                subscribers.push(subscriber);
            });


            // On every Stream destroyed...
            session.on('streamDestroyed', ({ stream }) => {
                const index = subscribers.indexOf(stream.streamManager, 0);
                if (index >= 0) {
                    subscribers.splice(index, 1);
                }
            });


            // On every asynchronous exception...
            // 여기서 signal 등록하면됨 
            //  이거뭐임????
            session.on('exception', ({ exception }) => {
                console.warn(exception);
            });

            axios.defaults.headers.common[
                "Authorization"
            ] = `Bearer ${rootState.accessToken}`;

            console.log(`룸아이디 ${state.conferenceid}`)

            console.log(`${SERVER_URL}/conferences/${state.conferenceid}`)
            console.log('여긴??nnn')
            console.log('딱 여기까지 찍히는 중!!!!!!!!!')
            axios.get(`${SERVER_URL}/conferences/${state.conferenceid}`)
                .then((res) => {
                    console.log('여긴??되고')
                    console.log(res.status)
                    if (res.status == 200) {
                        commit('SET_CANJOIN', true)
                    } else {
                        commit('SET_CANJOIN', false)
                    }
                    if (!state.canJoin)
                        return;
                })
                .catch(() => {
                    console.log('여긴??안되고')
                        //   this.$router.push({ name: 'MainPage' })
                    commit('SET_CANJOIN', false)
                });

            console.log('asdfasdfasdfasdfasdfasdf')

            // 방에 자리가 있으면 토큰 만들어줌 
            // console.log(this.state.conferenceid);
            dispatch('getToken', state.conferenceid)
                .then((token) => {
                    console.log(token)
                    console.log('토큰')
                    console.log('여기낙???!!!!!!!!!!!!!!!!!!!!!!!!!!!!!')
                    session.connect(token, { clientData: rootState.userData.nickname })
                        // console.log('224', this.staToken)
                        // session.connect(this.state.ovTokte.oven, { clientData: this.state.id })
                        .then(() => {
                            // console.log('하 제발요')
                            let publisher = OV.initPublisher(undefined, {
                                audioSource: undefined, // The source of audio. If undefined default microphone
                                videoSource: undefined, // The source of video. If undefined default webcam
                                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                                resolution: '640x480', // The resolution of your video
                                frameRate: 30, // The frame rate of your video
                                insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
                                mirror: true, // Whether to mirror your local video or not
                            });

                            console.log(OV, publisher, session, subscribers)
                            commit('SET_OV', OV);
                            commit('SET_MAINSTREAMMANAGER', publisher);
                            commit('SET_PUBLISHER', publisher);
                            commit('SET_SESSION', session);
                            commit('SET_SUBSCRIBERS', subscribers);

                            session.publish(state.publisher);
                        })
                        .catch(error => {
                            console.log('으아아아아아')
                            console.log('There was an error connecting to the session:', error.code, error.message);
                        })
                });
            // session.publish(state.publisher);
        },






        leaveSession({ state, commit }) {
            if (state.session) state.session.disconnect();

            commit('SET_SESSION', undefined)
            commit('SET_MAINSTREAMMANAGER', undefined)
            commit('SET_PUBLISHER', undefined)
            commit('SET_SUBSCRIBERS', undefined)
            commit('SET_OV', undefined)
        },

        updateMainVideoStreamManager({ state, commit }, stream) {
            if (state.mainStreamManager === stream) return;
            commit('SET_MAINSTREAMANAGER', stream)
        },

        getToken({ dispatch }, conferenceid) {
            console.log('여기지???')
            return dispatch('createSession', conferenceid)
                .then(sessionId => dispatch('createToken', sessionId));
        },

        createSession({ rootState }, sessionId) {
            console.log('ㅎㅎㅎㅎㅎㅎ')
            return new Promise((resolve, reject) => {
                axios.defaults.headers.common[
                    "Authorization"
                ] = `Bearer ${rootState.accessToken}`;

                // axios.defaults.headers.common[{
                //     // 'Authorization': 'Basic ' + btoa('OPENVIDUAPP:' + OPENVIDU_SERVER_SECRET),
                //     // 'Authorization',
                //     'Content-Type': 'application/json',
                //     // 'Access-Control-Allow-Origin': "*",
                //     // 'Access-Control-Allow-Methods': "GET,POST"
                // }] = `Bearer ${rootState.accessToken}`;
                // const body = JSON.stringify({ customSessionId: sessionId });
                // const options = {
                //     // headers: {
                //     //     'Authorization': 'Basic ' + btoa('OPENVIDUAPP:' + OPENVIDU_SERVER_SECRET),
                //     //     'Content-Type': 'application/json',
                //     // },
                // };
                axios
                    .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
                        // customSessionId: sessionId,
                    }), {
                        auth: {
                            username: 'OPENVIDUAPP',
                            password: OPENVIDU_SERVER_SECRET,
                        },
                    })
                    .then(response => response.data)
                    .then(data => resolve(data.id))
                    .catch(error => {
                        console.log(error.response.status)
                        if (error.response.status === 409) {
                            resolve(sessionId);
                            // return {};
                        } else {
                            console.log('여기여기여기여기여기여기역이ㅕㄱ이ㅕㄱ이ㅕ기여')
                            console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
                            if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
                                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
                            }
                            reject(error.response);
                        }
                        // }
                    });
            });
        },

        createToken({ rootState }, sessionId) {
            console.log('!!!!!!!!!!!!!!!!!!!!!!!!')
            return new Promise((resolve, reject) => {
                // console.log('uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu')

                axios.defaults.headers.common[
                    "Authorization"
                ] = `Bearer ${rootState.accessToken}`;
                // axios.defaults.headers.common[{
                //         // 'Authorization': 'Basic ' + btoa('OPENVIDUAPP:' + OPENVIDU_SERVER_SECRET),
                //         // 'Authorization',
                //         'Content-Type': 'application/json',
                //         // 'Access-Control-Allow-Origin': "*",
                //         // 'Access-Control-Allow-Methods': "GET,POST"
                //     }]
                //     // }] = `Bearer ${rootState.accessToken}`;
                // const body = JSON.stringify({});
                // const options = {
                //     headers: {
                //         'Authorization': 'Basic ' + btoa('OPENVIDUAPP:' + OPENVIDU_SERVER_SECRET),
                //         'Content-Type': 'application/json',
                //     },
                // };

                axios

                    .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, JSON.stringify({
                        // "type": "IPCAM",
                        // "data": "Office security camera",
                        // "record": true,
                        // "rtspUri": "rtsp://your.camera.ip.sdp",
                        // "adaptativeBitrate": true,
                        // "onlyPlayWithSubscribers": true,
                        // "networkCache": 2000
                        // "kurentoOptions": {
                        //    "allowedFilters": ["GStreamerFilter", "FaceOverlayFilter"]
                        //    }
                    }), {
                        // headers: ({
                        //     // 'Authorization': 'Basic ' + btoa('OPENVIDUAPP:' + OPENVIDU_SERVER_SECRET),
                        //     // 'Authorization',
                        //     'Content-Type': 'application/json',
                        //     // 'Access-Control-Allow-Origin': "*",
                        //     // 'Access-Control-Allow-Methods': "GET,POST"
                        // }),
                        auth: {
                            username: 'OPENVIDUAPP',
                            password: OPENVIDU_SERVER_SECRET,
                        },

                    })
                    .then(response => response.data)
                    .then(

                        data => resolve(data.token),
                    )
                    .catch(error => reject(error.response));
            })
        },

        // createToken(sessionId) {
        //     return new Promise((resolve, reject) => {
        //         axios
        //             .post(`${SERVER.OPENVIDU_URL}/api/tokens`, JSON.stringify({
        //                 "session": sessionId,
        //                 "kurentoOptions": {
        //                     "allowedFilters": ["GStreamerFilter", "FaceOverlayFilter"]
        //                 }
        //             }), {
        //                 headers: {
        //                     'Content-Type': 'application/json'
        //                 },
        //                 auth: {
        //                     username: 'OPENVIDUAPP',
        //                     password: secrets.OPENVIDU.SECRET_KEY,
        //                 },
        //             })
        //             .then(response => response.data)
        //             .then(data => resolve(data.token))
        //             .catch(error => reject(error.response));
        //     })
        // },



        // createToken(sessionId) {
        //     return new Promise((resolve, reject) => {
        //         console.log('여기까지???')
        //         axios
        //             .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
        //                 // headers: {
        //                 //     'Content-Type': 'application/json'
        //                 // },
        //                 auth: {
        //                     username: 'OPENVIDUAPP',
        //                     password: OPENVIDU_SERVER_SECRET,
        //                 },
        //             })
        //             .then(
        //                 response => response.data,
        //                 // console.log('여긴???')
        //             )
        //             .then(data => resolve(data.token), console.log('여긴???'))
        //             .catch(error => reject(error.response));
        //     })
        // },
        // createToken({ rootState }, sessionId) {

    },



    modules: {},
}



export default video