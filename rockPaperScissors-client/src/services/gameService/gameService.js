import axios from "axios";

class GameService {

  addGame(playerId, newGame) {
    return axios.post(`${process.env.REACT_APP_API_URL}/players/${playerId}/games`, newGame);
  }

}

let gameService = new GameService();

export default gameService;
