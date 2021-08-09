import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
// import createPersistedState from 'vuex-persistedstate';
import { OpenVidu } from 'openvidu-browser';

Vue.use(Vuex)
    // const SERVER_URL = process.env.VUE_APP_SERVER_URL



const video = {
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

                mySessionId: null,
                myUserName: '',
                canJoin: null,
            }
        },

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
            SET_SESSION_ID(state, sessionId) {
                state.mySessionId = sessionId
            }
        },

        actions: {
            joinSession: function({ state, commit, dispatch }, sessionId) {
                commit('SET_SESSION_ID', sessionId);
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

                axios.defaults.headers.common["Authorization"] = `Bearer ${this.$store.state.accessToken}`;


                // 방에 자리가 있으면 토큰 만들어줌 
                // console.log(this.state.conferenceid);
                dispatch('getToken', sessionId).then(() => {
                    console.log('224', this.state.ovToken)
                    session.connect(this.state.ovToken, { clientData: this.state.id })
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


                        })
                        .catch(error => {
                            console.log('으아아아아아')
                            console.log('There was an error connecting to the session:', error.code, error.message);
                        });
                    session.publish(this.publisher);

                });

            },


            leaveSession({ state, commit }) {
                if (state.session) state.session.disconnect();

                commit('SET_SESSION', undefined)
                commit('SET_MAINSTREAMMANAGER', undefined)
                commit('SET_PUBLISHER', undefined)
                commit('SET_SUBSCRIBERS', undefined)
                commit('SET_OV', undefined)


            }
            getters: {

            },

            modules: {}
        }

        export default video