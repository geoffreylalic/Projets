import React from 'react'

class ClickEv extends React.Component {
    constructor() {
        super()
        this.state = {
            nbClick: 0
        }
        this.clickHandle = this.clickHandle.bind(this)
    }

    clickHandle() {
        this.setState(prevState => {
            return{
                nbClick: prevState.nbClick + 1
            }
        });
    }
    render() {
        return (
            <div>
                <button
                    onClick={this.clickHandle}
                >
                    click me
                </button>
                <p>
                    {this.state.nbClick}
                </p>
            </div>)
    }
}



export default ClickEv;