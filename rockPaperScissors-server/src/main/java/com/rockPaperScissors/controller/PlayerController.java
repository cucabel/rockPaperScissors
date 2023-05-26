package com.rockPaperScissors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rockPaperScissors.domain.Player;
import com.rockPaperScissors.service.IPlayerService;

@CrossOrigin
@RestController
@RequestMapping(value = "/players")
public class PlayerController {
	
	private IPlayerService playerService;
	
	@Autowired
	public PlayerController(IPlayerService playerService) {
		this.playerService = playerService;
	}

	@PostMapping
	Player create(@RequestBody Player player) {
		return playerService.create(player);
	}
	
	@GetMapping("/{playerId}")
	Player getById(@PathVariable long playerId) {
		return playerService
				.getBy(playerId)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
	}

}
