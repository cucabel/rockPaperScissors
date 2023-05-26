import axios from "axios";

class PlayerService {

  addPlayer(newPlayer) {
    return axios.post(`${process.env.REACT_APP_API_URL}/players`, newPlayer);
  }

  getPlayerById(playerId) {
    return axios.get(`${process.env.REACT_APP_API_URL}/players/${playerId}`);
  }

}

let playerService = new PlayerService();

export default playerService;
