const fs = require('fs');

module.exports = {
  /* 배포할때 사용함 */
  configureWebpack: {
    
  },
    devServer: {
      // proxy:{
      //   '/openvidu': {
      //     target: "https://i5c104.p.ssafy.io:4443",
      //     changeOrigin: true,
      //     "secure":false
      //   }
    
    host: "0.0.0.0",
    hot: true,
    disableHostCheck: true
  },
  transpileDependencies:[
    'vuetify'
  ],
}
