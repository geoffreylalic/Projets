import React from 'react';

class Forms extends React.Component {
    constructor() {
        super();
        this.state = {
            firstName:"",
            lastName:""
        }
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(evt){
        this.setState({
            [evt.target.name] : evt.target.value
        })
    }

    render() {
        return (
            <div>
                <h1>
                    Forms
                </h1>
                <form>
                    <input
                        type="text"
                        name="firstName"
                        placeholder="Firstname"
                        onChange={this.handleChange}
                    />

                    <input
                        type="text"
                        name="lastName"
                        placeholder="Lastname"
                        onChange={this.handleChange}
                    />
                </form>
            <h1>{this.state.firstName} {this.state.lastName}</h1>
            </div>)
    }
}

export default Forms