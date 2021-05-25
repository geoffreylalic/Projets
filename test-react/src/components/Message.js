import React from 'react';

class Message extends React.Component{
    render(){
        return (
            <h2>
                Message :{this.props.message}

            </h2>
        )
    }
}

export default Message;