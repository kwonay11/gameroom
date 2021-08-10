<template>
  <div>
      <div id='chat-area'>
               <div v-for="val in chat" v-bind:key="val.id">
               <div v-if="val.user === $store.state.id" class="mychat">
                  {{ val.user }} : {{ val.text }}
               </div>
              
               <div v-else class="otherchat">
                  {{ val.user }} : {{ val.text }}
               </div>
            
               </div>
         
            </div>
       
            <div class='chat_input'>
               <!-- <textarea v-model='chattings' @keyup.enter='sendMessage' placeholder="채팅" type="text" class="message-input"></textarea> -->
               <input v-model='chattings' @keyup.enter='sendMessage' placeholder="input message.." type="text" class="message-input"/>
               <button :disabled="!chattings" @click='sendMessage' type="submit" class="message-submit">Send</button>

            </div>

  </div>
</template>

<script>
export default {
    name:'Chatting',
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

}
</script>

<style>

</style>