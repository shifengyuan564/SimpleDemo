require('./app.css');
var jsonFile = require("./app.json");


import React from 'react';
import ReactDOM from 'react-dom';


class Layout extends React.Component {

    render() {
        return (
            <h2>
                React is setup and running !
                <span className="greet-text">{jsonFile.greetText}</span>
            </h2>
        );
    }
}

const app = document.getElementById('app');
ReactDOM.render(<Layout/>, app);