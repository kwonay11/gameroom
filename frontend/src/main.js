import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import store from './store'
import axios from 'axios'

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.use(axios)
new Vue({
    vuetify,
    router,
    store,
    render: h => h(App)
}).$mount('#app', '#pp')