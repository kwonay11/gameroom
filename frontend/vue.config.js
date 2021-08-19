const fs = require('fs');
// const TerserPlugin = require('terser-webpack-plugin');

module.exports = {
  /* 배포할때 사용함 */
  // configureWebpack: config => {
  //    config.optimization = {
  //       minimize: true, 
  //       minimizer: [ 
  //         new TerserPlugin({
  //            terserOptions: {
  //               ecma: 6,
  //               compress: { drop_console: true },
  //                output: { comments: false }, 
  //               }, 
  //             }), 
  //           ], 
  //         } },


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
