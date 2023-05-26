package com.rockPaperScissors.service;

import com.rockPaperScissors.domain.Game;
import com.rockPaperScissors.util.PlayerNotFoundException;

public interface IGameService {

	public boolean create(Game game, Long playerId) throws PlayerNotFoundException;

}
