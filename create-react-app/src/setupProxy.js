const { createProxyMiddleware } = require('http-proxy-middleware')

module.exports = function (app) {
  app.use(
    createProxyMiddleware('/v1_0', {
      target: 'http://localhost:8080',
      secure: false,
      changeOrigin: true,
      pathRewrite: {
        "^/v1_0": ""
      }
    })
  )
}