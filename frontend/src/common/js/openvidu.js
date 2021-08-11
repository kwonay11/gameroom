import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';

axios.defaults.headers.post['Content-Type'] = 'application/json';
const SERVER_URL = process.env.VUE_APP_SERVER_URL

// const Openjs = {
//     OV: undefined,
//     session: undefined,
//     mainStreamManager: undefined,
//     publisher: undefined,
//     subscribers: [],

//     mySessionId: null,
//     myUserName: '',
//     myUserNick: '',
//     canJoin: null,

//     chattings: '',
//     // chatting_user: '',
//     chat: [],
//     chatHeight: "33vh"
// };


    this.mySessionId = this.$route.params.roomid
        
    this.myUserName = this.$store.state.id
    this.myUserNick = this.$store.state.userData.nickname
    this.OV = new OpenVidu();
    // --- Init a session ---
    this.session = this.OV.initSession();

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
    axios.defaults.headers.common["Authorization"] = `Bearer ${this.$store.state.accessToken}`;
    // --- Connect to the session with a valid user token ---
    console.log('room확인')
    axios.get(`${SERVER_URL}/conferences/${this.$route.params.roomid}`)
    .then((res) => {
        console.log(res.status)
        if(res.status == 200) {
            this.canJoin = true;
        }
        else {
            this.canJoin = false;
        }
        if(!this.canJoin)
    return;
    })
    .catch(() => {
            this.$router.push({ name: 'MainPage' })
    this.canJoin = false;
    });

    // 'getToken' method is simulating what your server-side should do.
    // 'token' parameter should be retrieved and returned by your own backend
    this.getToken(this.mySessionId)
    .then(token => {
        console.log(token)
        console.log('토큰')
        this.session.connect(token, { clientData: this.myUserNick })
            .then(() => {

            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true,     // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true,     // Whether you want to start publishing with your video enabled or not
                resolution: '640x480',  // The resolution of your video
                frameRate: 30,         // The frame rate of your video
                insertMode: 'APPEND',   // How the video is inserted in the target element 'video-container'
                mirror: false          // Whether to mirror your local video or not
            });
            console.log( publisher, this.session, this.subscribers)
            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
            })
            .catch(error => {
            console.log('There was an error connecting to the session:', error.code, error.message);
            });
    });

    window.addEventListener('beforeunload', this.leaveSession)

    // 내가 입력한 채팅
    this.session.on('signal:my-chat', (event) => {
        console.log('여기')
        console.log(event)
        // this.chatting_user = event.from.data["clientData"]
        console.log('내가 입력한 내용')
        console.log(event.data); // Message
        const content = event.data.slice(1, -1) // Message
        console.log('입력한 사람')
        console.log(event.from.data); // Message
        const chatting_user = event.from.data.slice(15,-2)
        console.log(chatting_user)
        

        
        this.chat.push({
            user : chatting_user,
            text : content
        });


    });

export default OpenVidu