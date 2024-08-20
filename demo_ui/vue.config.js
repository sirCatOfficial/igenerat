const { defineConfig } = require('@vue/cli-service')
const webpack = require('webpack');
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    port: 8081,
    // 下方配置无效，已屏蔽
    /* proxy: {
      '/api': {
        target: "http://192.168.111.101:8081",
        ws: true,
        changeOrigin: true,
        pathRewrite: { '^/api': '' }
      }
    } */
    // '/api': {
    //   target: 'http://localhost:80',
    //   changeOrigin: true,
    //   pathRewrite: {
    //       '/api': '/'
    //   }
    // },
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        ws: true, //代理websockets
        changeOrigin: true,
        pathRewrite: {
          '^/api': '',
        },
      }
    }
  },
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        "windows.jQuery": "jquery"
      })
    ],
  }
})
