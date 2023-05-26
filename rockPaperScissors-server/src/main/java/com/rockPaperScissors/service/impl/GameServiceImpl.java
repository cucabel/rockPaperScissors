package com.rockPaperScissors.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rockPaperScissors.domain.Game;
import com.rockPaperScissors.domain.Player;
import com.rockPaperScissors.repository.IGameRepository;
import com.rockPaperScissors.repository.IPlayerRepository;
import com.rockPaperScissors.service.IGameService;
import com.rockPaperScissors.util.PlayerNotFoundException;

@Service(value = "gameService")
public class GameServiceImpl implements IGameService {
	
	private IGameRepository gameRepository;
	private IPlayerRepository playerRepository;
	
	@Autowired
	public GameServiceImpl(IGameRepository gameRepository, IPlayerRepository playerRepository) {
		this.gameRepository = gameRepository;
		this.playerRepository = playerRepository;
	}

	@Override
	public boolean create(Game game, Long playerId) throws PlayerNotFoundException {
		Player player = playerRepository.findById(playerId).orElseThrow(PlayerNotFoundException::new);
		Game newGame = new Game(game.getPlayerSymbol(), game.getEnemySymbol(), player);
		gameRepository.save(newGame);
		return newGame.isResult();
	}

}
