module.exports = {
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
