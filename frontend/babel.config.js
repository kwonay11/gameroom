module.exports = (api) => {
  var env = api.cache(() => process.env.NODE_ENV);
  var plugins = [];
  // Change to 'production' when configs are working
  if (env === 'development') {
    plugins.push(['transform-remove-console', { exclude: ['error', 'warn'] }]);
  }
  return {
    presets: ['@vue/cli-plugin-babel/preset'],
    // plugins,
    // Doesn't work even when always on?
    plugins: [['transform-remove-console', { exclude: ['error', 'warn'] }]]
  };
};