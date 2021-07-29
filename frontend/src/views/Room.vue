<template>
	<div id="main-container" >
		<div id="join" v-if="!session">
			<div id="img-div"><img src="resources/images/openvidu_grey_bg_transp_cropped.png" /></div>
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>Join a video session</h1>
				<div class="form-group">
					<p>
						<label>Participant</label>
						<input v-model="myUserName" class="form-control" type="text" required>
					</p>
					<p>
						<label>Session</label>
						<input v-model="mySessionId" class="form-control" type="text" required>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="joinSession()">Join!</button>
					</p>
				</div>
			</div>
		</div>

		<div  v-if="session">
			<div class='participation'>
				<div id="video-container" class="">
					<user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
					<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
				</div>
			</div>
			<!-- <div id="video-container" class="col-md-2">
				<user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
			</div> -->
			<div id="main-video" class="col-md-6">
				<user-video :stream-manager="mainStreamManager"/>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/UserVideo';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	name: 'App',

	components: {
		UserVideo,
	},

	data () {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],

			mySessionId: 'SessionA',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),
		}
	},

	methods: {
		joinSession () {
			// --- Get an OpenVidu object ---
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

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

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
		},

		leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager (stream) {
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
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
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
	}
}
</script>

<style>

.participation {
  margin: 5vh 2.5vw;
  width: 95vw;
	/* height: 22vh; */
	/* text-align: justify; */
  border: 3px solid #ffa500;
	display: flex;
	flex-direction: row;
	/* align-items: center;
	justify-content: space-around;   */


}



#main-container {
	padding-bottom: 80px;
}

/*vertical-center {
	position: relative;
	top: 30%;
	left: 50%;
	transform: translate(-50%, -50%);
}*/

.horizontal-center {
	margin: 0 auto;
}

.form-control {
	color: #0088aa;
	font-weight: bold;
}

.form-control:focus {
	border-color: #0088aa;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(0, 136, 170, 0.6);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(0, 136, 170, 0.6);
}

input.btn {
	font-weight: bold;
}

.btn {
	font-weight: bold !important;
}

.btn-success {
	background-color: #06d362 !important;
	border-color: #06d362;
}

.btn-success:hover {
	background-color: #1abd61 !important;
	border-color: #1abd61;
}



a:hover .demo-logo {
	-webkit-filter: brightness(0.7);
	filter: brightness(0.7);
}

#join-dialog {
	margin-left: auto;
    margin-right: auto;
    max-width: 70%;
}

#join-dialog h1 {
	color: #4d4d4d;
	font-weight: bold;
	text-align: center;
}

#img-div {
	text-align: center;
	margin-top: 3em;
	margin-bottom: 3em;
	/*position: relative;
	top: 20%;
	left: 50%;
	transform: translate(-50%, -50%);*/
}

#img-div img {
	height: 15%;
}

#join-dialog label {
	color: #0088aa;
}

#join-dialog input.btn {
	margin-top: 15px;
}


#buttonLeaveSession {
	float: right;
	margin-top: 20px;
}

#video-container video {
	position: relative;
	float: left;
	width: 30%;
	cursor: pointer;
	margin: 0 2%;
}

#video-container video + div {
	float: left;
	width: 50%;
	position: relative;
	margin-left: -50%;
}

#video-container p {
	display: inline-block;
	background: #f8f8f8;
	padding-left: 5px;
	padding-right: 5px;
	color: #777777;
	font-weight: bold;
	border-bottom-right-radius: 4px;
}

video {
	width: 100%;
	height: auto;
}

#main-video p {
	position: absolute;
	display: inline-block;
	background: #f8f8f8;
	padding-left: 5px;
	padding-right: 5px;
	font-size: 22px;
	color: #777777;
	font-weight: bold;
	border-bottom-right-radius: 4px;
}



#session #video-container img {
	position: relative;
	float: left;
	width: 50%;
	cursor: pointer;
	object-fit: cover;
	height: 180px;
}


/* xs ans md screen resolutions*/
/* 
@media screen and (max-width: 991px) and (orientation: portrait) {
	#join-dialog {
		max-width: inherit;
	}
	#img-div img {
		height: 10%;
	}
	#img-div {
		margin-top: 2em;
		margin-bottom: 2em;
	}
	.container-fluid>.navbar-collapse, .container-fluid>.navbar-header, .container>.navbar-collapse, .container>.navbar-header {
		margin-right: 0;
		margin-left: 0;
	}
	.navbar-header i.fa {
		font-size: 30px;
	}
	.navbar-header a.nav-icon {
		padding: 7px 3px 7px 3px;
	}
}

@media only screen and (max-height: 767px) and (orientation: landscape) {
	#img-div {
		margin-top: 1em;
		margin-bottom: 1em;
	}
	#join-dialog {
		max-width: inherit;
	}
} */
</style>

