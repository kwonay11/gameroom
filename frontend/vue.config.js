module.exports = {
  /* 배포할때 사용함 */
  configureWebpack: {
    
  },
    devServer: {
    host: "0.0.0.0",
    hot: true,
    disableHostCheck: true
  },
  transpileDependencies: [
    'vuetify'
  ]
}
