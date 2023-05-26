import React, { Component } from 'react';
import { Routes, Route } from 'react-router-dom';

import HomePage from './pages/home/HomePage';
import GamePage from './pages/playerGame/GamePage';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="App">
        <Routes>
          <Route exact path="/" element={ <HomePage /> }/>
          <Route exact path="/players/:playerId" element={ <GamePage /> }/>
        </Routes>
      </div>
    );
  }
}

export default App;
