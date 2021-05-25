import React from 'react';

class Loading extends React.Component {
    constructor() {
        super();
        this.state = {};
    }
    render() {
        return(
            this.props.isLoaded ? <h1> La page est chargée</h1> : <h1>Loading...</h1>
            )
        }
        
}

export default Loading