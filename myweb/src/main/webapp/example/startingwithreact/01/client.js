import React from "/js/react/react.js";
import ReactDOM from "/js/react/react-dom";

class Layout extends React.Component {

    render() {
        return (
            <h2>React is setup and running !</h2>
        );
    }
}

const app = document.getElementById('app');
ReactDOM.render(<Layout/>, app);