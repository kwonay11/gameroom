import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
// import UserVideo from '@/components/UserVideo';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://127.0.0.1:5443";
// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
// const SERVER_URL = process.env.VUE_APP_SERVER_URL


export const video = {

    data() {
        return {

            OV: undefined,
            session: undefined,
            mainStreamManager: undefined,
            // 이게 나
            publisher: undefined,
            // 이게 나를 뺀 방에 들어와있는 나머지 사람들
            subscribers: [],

            mySessionId: null,
            myUserName: '',
            myUserNick: '',
            canJoin: null,

            roominfo: {},

        }
    },
    created: function() {
        console.log('1111111')


        // 방 ID 인거 같고

        // console.log(this.mySessionId)
        // console.log(this.myUserName)
        this.OV = new OpenVidu();
        // --- Init a session ---
        this.session = this.OV.initSession();
        this.mySessionId = this.$route.params.conferenceid;
        console.log('세션!!!!!!!!')
        console.log(this.$route.params)
        console.log(this.mySessionId)
        this.myUserName = this.$store.state.id
        this.myUserNick = this.$store.state.userData.nickname
            // --- Specify the actions when events take place in the session ---

        // On every new Stream received...
        this.session.on('streamCreated', ({ stream }) => {
            const subscriber = this.session.subscribe(stream);
            this.subscribers.push(subscriber);
        });

        // On every Stream destroyed...
        this.session.on('streamDestroyed', ({ stream }) => {
            const index = this.subscribers.indexOf(stream.streamManager, 0);
            if (index >= 0) {
                this.subscribers.splice(index, 1);
            }
        });


        // On every asynchronous exception...
        this.session.on('exception', ({ exception }) => {
            console.warn(exception);
        });
        this.session.on('signal:game', (event) => {
            console.log(event);
        });


        axios.defaults.headers.common["Authorization"] = `Bearer ${this.$store.state.accessToken}`;
        // --- Connect to the session with a valid user token ---
        console.log('room확인')
            // axios.get(`${SERVER_URL}/conferences/${this.$route.params.roomid}`)
            //     .then((res) => {
            //         console.log(res.status)
            //         if (res.status == 200) {
            //             this.canJoin = true;
            //         } else {
            //             this.canJoin = false;
            //         }
            //         if (!this.canJoin)
            //             return;
            //     })
            //     .catch(() => {
            //         this.$router.push({ name: 'MainPage' })
            //         this.canJoin = false;
            //     });

        // 'getToken' method is simulating what your server-side should do.
        // 'token' parameter should be retrieved and returned by your own backend
        this.getToken(this.mySessionId)
            .then(token => {
                this.session.connect(token, { participantPublicId: this.myUserName })
                    .then(() => {

                        // --- Get your own camera stream with the desired properties ---
                        console.log(this.myUserName)
                        let publisher = this.OV.initPublisher(undefined, {
                            audioSource: undefined, // The source of audio. If undefined default microphone
                            videoSource: undefined, // The source of video. If undefined default webcam
                            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                            publishVideo: true, // Whether you want to start publishing with your video enabled or not
                            resolution: '640x480', // The resolution of your video
                            frameRate: 30, // The frame rate of your video
                            insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
                            mirror: false // Whether to mirror your local video or not
                        });
                        console.log('!!!!!!!!!!!!!!')
                        console.log(publisher)

                        // this.mainStreamManager = publisher;
                        this.publisher = publisher;
                        console.log('durldurdlurdlurdlul')

                        console.log(this.mainStreamManager)
                        console.log(this.publisher)

                        // --- Publish your stream ---

                        this.session.publish(this.publisher);
                    })
                    .catch(error => {
                        console.log('There was an error connecting to the session:', error.code, error.message);
                    });
            });

        window.addEventListener('beforeunload', this.leaveSession)

    },
    methods: {
        // leaveSession() {
        //     // --- Leave the session by calling 'disconnect' method over the Session object ---
        //     console.log('leavesesion')
        //     if (this.session) this.session.disconnect();

        //     this.session = undefined;
        //     this.mainStreamManager = undefined;
        //     this.publisher = undefined;
        //     this.subscribers = [];
        //     this.OV = undefined;

        //     window.removeEventListener('beforeunload', this.leaveSession);
        // },
        updateMainVideoStreamManager(stream) {
            if (this.mainStreamManager === stream) return;
            this.mainStreamManager = stream;
        },

        /**
         * --------------------------
         * SERVER-SIDE RESPONSIBILITY
         * --------------------------
         * These methods retrieve the mandatory user token from OpenVidu Server.
         * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
         * the API REST, openvidu-java-client or openvidu-node-client):
         *   1) Initialize a Session in OpenVidu Server   (POST /openvidu/api/sessions)
         *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
         *   3) The Connection.token must be consumed in Session.connect() method
         */

        getToken(mySessionId) {
            return this.createSession(mySessionId)
                .then(sessionId => this.createToken(sessionId));
        },

        // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
        createSession(sessionId) {
            console.log('세션 확인')
            console.log(sessionId)
            return new Promise((resolve, reject) => {
                console.log(this.$store.state.conferenceid)
                axios
                    .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
                        customSessionId: String(sessionId),
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
                            resolve();
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

        // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
        createToken(sessionId) {
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
    },
}