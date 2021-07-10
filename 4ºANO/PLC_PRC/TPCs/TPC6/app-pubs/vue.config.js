module.exports = {
  devServer: {
    proxy: {
      "/api-server": {
        target: "http://localhost:7007",

        secure: true,

        changeOrigin: true,

        pathRewrite: {
          "^/api-server": "",
        },
      },
    },
  },

  transpileDependencies: ["vuetify"],
};
