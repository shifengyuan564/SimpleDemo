var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: __dirname + '/src/main/webapp/example/Tutsplus/reactdeepdive/app.jsx',

    output: {
        path: __dirname + '/build',
        filename: '[name].bundle.js'
    },

    module: {
        loaders: [
            {
                test: /\.css$/,
                loader: 'style-loader!' + 'css-loader?sourceMap'
            },
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                query: {
                    presets: ['react']
                }
            },
            {
                test: /\.json$/,
                loader: "json-loader"
            }

        ]
    },

    plugins: [
        new HtmlWebpackPlugin()
    ],

    devtool: 'source-map'

};