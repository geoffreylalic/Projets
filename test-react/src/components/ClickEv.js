import React from 'react'

class ClickEv extends React.Component {
    constructor(){
        super();
        this.state = {
            isCliked : false,
            display : ''
        }
    }

    click() {
        if(this.state.isCliked ===  true){
            this.state.isCliked = false
            this.state.display = 'switch to false'
        }else{
            this.state.isCliked = true
            this.state.display = 'switch to true'
        }
        
    }
    render() {
        
        return (
            <div>
                <button onClick={this.click}>
                    click me
                </button>
                <p>{this.state.display}</p>
            </div>

        )
    }
}



export default ClickEv;