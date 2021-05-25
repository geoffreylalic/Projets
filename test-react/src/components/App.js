import React from 'react';
import Profil from './Profil';
import Message from './Message';
import twitterData from '../TwitterData';
import ClickEv from './ClickEv';
import Loading from './Loading';
import FetchAPI from './FetchAPI'
import Forms from './Forms'
import './App.css';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            answer: 'states',
            isLoggedIn: true,
            isLoaded: false
        }
    }


    componentDidMount() {
        setTimeout(() => {
            this.setState({ isLoaded: true })
        }, 5000);
    }
    render() {
        let word;
        if (this.state.isLoggedIn) {
            word = 'on'
        } else {
            word = 'off'
        }
        //affichage des twitts
        const data = twitterData.map(twit =>
            <div>
                <Profil profil={twit.profil}
                    key={twit.key}
                />
                <Message message={twit.message}
                    key={twit.key}
                />
                <p> states : {this.state.answer}</p>
                <hr></hr>
            </div>
        )

        return (
            <div>
                <p>loggedIn : {word}</p>
                {data}
                <ClickEv />
                <Loading isLoaded={this.state.isLoaded} />
                <FetchAPI />
                <Forms />
            </div>
        );
    }



}

export default App;