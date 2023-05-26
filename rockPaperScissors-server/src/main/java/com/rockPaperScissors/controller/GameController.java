package com.rockPaperScissors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rockPaperScissors.domain.Game;
import com.rockPaperScissors.service.IGameService;
import com.rockPaperScissors.util.PlayerNotFoundException;

@CrossOrigin
@RestController
@RequestMapping(value = "players/{playerId}/games")
public class GameController {
	
	private IGameService gameService;

	@Autowired
	public GameController(IGameService gameService) {
		this.gameService = gameService;
	}
	
	@PostMapping
	public boolean create(@RequestBody Game game, @PathVariable long playerId) {
		try {
			return gameService.create(game, playerId);
		} catch (PlayerNotFoundException e) {
			throw new ResponseStatusException(NOT_FOUND);
		}
	}

}
