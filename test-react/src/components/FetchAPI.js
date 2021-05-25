import React from 'react'

class FetchAPI extends React.Component {
    constructor() {
        super()
        this.state = {
            charactere: {}
        }
    }

    componentDidMount() {
        fetch("https://swapi.dev/api/people/1")
            .then(response => response.json())
            .then(data => this.setState({ charactere: data }))
    }

    render() {
        console.log(this.state.charactere)
        const text = this.state.charactere === null ? "en cours de transit" : this.state.charactere.name
        return (
            <div>
                <h1>
                    FetchAPI
                </h1>
                <p>
                    {text}
                </p>
            </div>
        )

    }
}

export default FetchAPI;