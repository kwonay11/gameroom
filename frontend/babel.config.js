const removeConsolePlugin = ['@vue/cli-plugin-babel/preset']
if(process.env.NODE_ENV === 'production') {
  removeConsolePlugin.push("transform-remove-console")
}

module.exports = {
  presets: [
    '@vue/app'
  ],
  plugins: removeConsolePlugin
}
