import React from 'react';
import Profil from './Profil';
import Message from './Message';
import twitterData from '../TwitterData';
import ClickEv from './ClickEv';
import './App.css';

class App extends React.Component {
    constructor(){
        super();
        this.state = {
            answer : 'states',
            isLoggedIn: true
        }
    }
    render() {
        let word;
        if (this.state.isLoggedIn){
            word = 'on'
        }else{
            word = 'off'
        }
        const data = twitterData.map(twit =>
            <div>
                <Profil profil={twit.profil} />
                <Message message={twit.message} />
                <p> states : {this.state.answer}</p>
                <hr></hr>
            </div>
        )
        return (
            <div>
                <p>loggedIn : {word}</p>
                {data}
                <ClickEv />
            </div>
        );
    }



}

export default App;