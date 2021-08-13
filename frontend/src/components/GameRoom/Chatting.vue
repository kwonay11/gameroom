<template>
  <div>
      <div id='chat-area'>
         <div v-for="val in chat" v-bind:key="val.id">
            <div v-if="val.user === myUserNick" class="mychat">
               {{ val.text }}
            </div>
            
            <div v-else class="otherchat">
               {{ val.user }} : {{ val.text }}
            </div>
         
         </div>
   
      </div>
       
      <div class='chat_input'>
         <input v-model='chattings' @keyup.enter='sendMessage' placeholder="input message.." type="text" class="message_input"/>
         <button :disabled="!chattings" @click='sendMessage' type="submit" class="message_submit">Send</button>

      </div>

  </div>
</template>

<script>

export default {
    name:'Chatting',
    data() {
       return {
         chattings: '',
         // chatting_user: '',
         chat: [],
         // chatHeight: "30vh",
         myUserNick: null,
       }
    },
    props:{
       session: Object,
    },
    watch: {

      chat() {
         setTimeout(() => {
         var chatDiv = document.getElementById("chat-area");
         chatDiv.scrollTo({
            // document.body.scrollTop = document.body.scrollHeight;
            top: chatDiv.scrollHeight - chatDiv.clientHeight,
            behavior: 'smooth'
         })
         }, 50);
      },
   },
   created: function () {
        this.myUserNick = this.$store.state.userData.nickname

      //  방에 들어와 있는 모든 사람이 받는거
        this.session.on('signal:my-chat', (event) => {
            console.log('여기')
            console.log(event)
                // this.chatting_user = event.from.data["clientData"]
            console.log('내가 입력한 내용')
            console.log(event.data); // Message
            const content = event.data.slice(1, -1) // Message
            console.log('입력한 사람')
            console.log(event.from.data); // Message
            const chatting_user = event.from.data.slice(15, -2)
            console.log(chatting_user)



            this.chat.push({
                user: chatting_user,
                text: content
            });


        });
   },
       methods: {
        sendMessage() {
            // post 같은 느낌 = signal
            this.session.signal({
                    data: JSON.stringify(this.chattings),
                    type: 'my-chat'
                })
                .then(() => {
                    this.chattings = '';
                    console.log('Message success');
                })
                .catch(error => {
                    console.log(error);
                })
        },
      },

}
</script>

<style>
.chat_input {
   margin-top: 6px;
   border: 3px solid rgb(255, 255, 255);
   border-radius:20px;
   flex: 0 1 40px;
   width: 100%;
   background: rgba(0, 0, 0, 0.3);
   padding: 15px;
   position: relative;
}
#chat-area {
   padding:5px;
   overflow-y: scroll;
   border: 3px solid rgb(255, 255, 255);
   border-radius:20px;
   align-items: center;
   height: 39.5vh;
   background: rgba(0, 0, 0, 0.5);
   color:white;
 
}
#chat-area::-webkit-scrollbar {
  width: 8px; 
  height: 8px;
 
}
#chat-area::-webkit-scrollbar-track {
   background: #3f3150;
   border-radius: 20px;
}
#chat-area::-webkit-scrollbar-corner {
   background: #3f3150;
   border-radius: 20px;
  
}
#chat-area::-webkit-scrollbar-thumb {
   background:  #b0a2c8;
   border-radius: 20px;
}
#chat-area::-webkit-scrollbar-button {
  background-color: #dccbe0;
 border-radius: 20px;
}
.mychat{
   position: relative;
   float: right;
   display: block;
   color: #fff;
   text-align: right;
   background: linear-gradient(120deg, #df80bf, #56136b);
   border-radius: 10px 10px 0 10px;
   margin-bottom:10px;
   margin-left:30px;
   margin-right: 10px;
   padding:5px;
   /* 한줄씩 나오는거 */
   clear: both;

}
/* 말풍선 */
.mychat:after{
   content: '';
	position: absolute;
	bottom: 0;
	left: 97%;
	width: 0;
	height: 0;
	border: 5px solid transparent;
	border-top-color: #56136b;
	border-bottom: 0;
	border-right: 0;
	margin-left: -2.5px;
	margin-bottom: -5px;
}


.otherchat{
   position: relative;
   float: left;
   display: block;
   color: #fff;
   text-align: left;
   background: linear-gradient(120deg, #4f2fc2,#24a89d);
   border-radius: 10px 10px 10px 0px;
   margin-bottom:10px;
   margin-left:8px;
   margin-right: 30px;
   padding:5px;
   clear: both;


}
/* 말풍선 */
.otherchat:after{
   content: '';
	position: absolute;
	bottom: 0;
	left: 3%;
	width: 0;
	height: 0;
	border: 5px solid transparent;
	border-top-color: #4f2fc2;
	border-bottom: 0;
	border-left: 0;
	margin-left: -2.5px;
	margin-bottom: -5px;
}
.message_input{
   background: none;
  outline: none !important;
  resize: none;
  color: white;
  font-size: 20px;
  height: 25px;
  margin: 0;
  padding-right: 10px;
  width: 300px;
}
.message_submit{
   position: absolute;
  z-index: 1;
  right: 20px;
  color: #fff;
  border: none;
  background: #401d77;
  font-size: 15px;
  text-transform: uppercase;
  line-height: 1;
  padding: 6px 10px;
  border-radius: 50px;
  outline: none !important;
  transition: background 0.1s ease;

}
.message_submit:hover {
  background: #8647eb;
}



</style>

