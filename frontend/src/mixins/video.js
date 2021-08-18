import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import swal from 'sweetalert';
// import UserVideo from '@/components/UserVideo';

axios.defaults.headers.post['Content-Type'] = 'application/json';

// const OPENVIDU_SERVER_URL = "https://127.0.0.1:5443";  //로컬용
const OPENVIDU_SERVER_URL = "https://i5c104.p.ssafy.io"; // aws용 
// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
const SERVER_URL = process.env.VUE_APP_SERVER_URL


import { mapState } from 'vuex'
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

            refreshcheck: true,
            members: [],

        }
    },
    created: function() {
        console.log('1111111')
        window.addEventListener('beforeunload', this.beforeWindowUnload)


        // 방 ID 인거 같고
        this.mySessionId = this.$route.params.roomid
        this.myUserName = this.$store.state.id
        this.myUserNick = this.$store.state.userData.nickname
            // console.log(this.mySessionId)
            // console.log(this.myUserName)
        this.OV = new OpenVidu();
        // --- Init a session ---
        this.session = this.OV.initSession();
        
        console.log('세션!!!!!!!!')
        console.log(this.$route.params)
        console.log(this.mySessionId)
        this.myUserName = this.$store.state.id

        console.log('video.js에서 내 닉네임')
        console.log(this.myUserNick)
            // --- Specify the actions when events take place in the session ---
        axios.defaults.headers.common["Authorization"] = `Bearer ${this.$store.state.accessToken}`;
        // --- Connect to the session with a valid user token ---
        axios.get(`${SERVER_URL}/conferences/${this.$route.params.roomid}`)
            .then((res) => {
                console.log(res.status)
                if (res.status == 200) {
                    this.canJoin = true;
                } else {
                    this.canJoin = false;
                }
                if (!this.canJoin)
                    return;
            })
            .catch((err) => {
                console.log('입장에러')
                if (err.response.status == 404){
                    swal('방이 존재하지 않습니다.')
                } else if (err.response.status == 403){
                    swal('방에 인원이 꽉찼습니다.')
                } else if (err.response.status == 405){
                    swal('게임이 진행중입니다.')
                }
                
                this.$router.push({ name: 'MainPage' })
                this.canJoin = false;
            });
    
        // 방 ID 인거 같고
        this.mySessionId = this.$route.params.roomid
        this.myUserName = this.$store.state.id
        this.myUserNick = this.$store.state.userData.nickname
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
        console.log('video.js에서 내 닉네임')
        console.log(this.myUserNick)
            // --- Specify the actions when events take place in the session ---

        // On every new Stream received...
        this.session.on('streamCreated', ({ stream }) => {
            const subscriber = this.session.subscribe(stream);
            this.subscribers.push(subscriber);
            console.log('참가자들')
            console.log(this.subscribers)
            this.members.push(subscriber)
            console.log('멤버')
            console.log(this.members)

            // this.members.sort(function(a, b) {
            //     return a.stream.connection.connectionId < b.stream.connection.connectionId ? -1 : a.stream.connection.connectionId > b.stream.connection.connectionId ? 1 : 0
            // })
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

        console.log('room확인')


        // 'getToken' method is simulating what your server-side should do.
        // 'token' parameter should be retrieved and returned by your own backend
        this.getToken(this.mySessionId)
            .then(token => {
                this.session.connect(token, { clientData: this.myUserNick })
                    .then(() => {

                        // --- Get your own camera stream with the desired properties ---

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

                        // console.log(this.mainStreamManager)
                        console.log(this.publisher)
                        this.members.push(this.publisher)
                            // --- Publish your stream ---

                        // this.members.sort(function(a, b) {
                        //     return a.stream.connection.connectionId < b.stream.connection.connectionId ? -1 : a.stream.connection.connectionId > b.stream.connection.connectionId ? 1 : 0
                        // })

                        this.session.publish(this.publisher);
                    })
                    .catch(error => {
                        console.log('There was an error connecting to the session:', error.code, error.message);
                    });
            });

        window.addEventListener('unload', this.leaveSession)

    },
    methods: {
        leaveSession() {
            // --- Leave the session by calling 'disconnect' method over the Session object ---
            if (this.session) this.session.disconnect();

            this.session = undefined;
            this.mainStreamManager = undefined;
            this.publisher = undefined;
            this.subscribers = [];
            this.OV = undefined;

            window.removeEventListener('beforeunload', this.leaveSession);
        },

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
        beforeWindowUnload(){
            this.session.signal({
            
                data: JSON.stringify({
                    "roomId" : this.$route.params.roomid,
                    "JWT": this.$store.state.accessToken
                }),
                type: 'leave'
                })
                .then(() => {
                    console.log('leave success');
                    this.$router.push({ name: "MainPage" });
                    
                })
                .catch(error => {
                    console.log(error);
                })
        }
    },

    computed: mapState(['conferenceid', 'id']),

}