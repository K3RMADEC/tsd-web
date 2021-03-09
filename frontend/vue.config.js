module.exports = {
  devServer: {
   port: 8090,
   proxy: {
     '/api': {
       target: 'http://localhost:8080',
       ws: true,
       changeOrigin: true
     }
   }
  },

  transpileDependencies: ['vuetify'],

  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: false,
    },
  },
  outputDir: 'target/dist',
  assetsDir: 'static'
}