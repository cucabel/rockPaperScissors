package com.rockPaperScissors.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;


import static com.rockPaperScissors.domain.Winners.*;
import com.rockPaperScissors.domain.Game;
import com.rockPaperScissors.service.IGameService;
import com.rockPaperScissors.service.impl.GameServiceImpl;
import com.rockPaperScissors.util.PlayerNotFoundException;

@SpringBootTest
public class GameControllerShould {
	
	private GameController gameController;
	private IGameService gameService;
	private Game game;
	private final long existingPlayerId = 1L;
	private final long nonExistingPlayerId = 2L;
	
	@BeforeEach
	public void setUp() {
		gameService = mock(GameServiceImpl.class);
		gameController = new GameController(gameService);
		game = new Game();
		game.setPlayerSymbol(PAPER.toString());
		game.setEnemySymbol(ROCK.toString());
	}
	
	@Test
	public void create_a_game() throws PlayerNotFoundException {
		boolean mockedResult = true;
		when(gameService.create(game, existingPlayerId)).thenReturn(mockedResult);
		
		boolean returnedResult = gameController.create(game, existingPlayerId);
		
		verify(gameService).create(game, existingPlayerId);
		assertTrue(returnedResult == mockedResult);
	}
	
	@Test
	public void throw_a_response_status_not_found_exception_when_a_player_does_not_exist_when_creating_a_game() throws PlayerNotFoundException {
		when(gameService.create(game, nonExistingPlayerId)).thenThrow(new ResponseStatusException(NOT_FOUND));
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> gameController.create(game, nonExistingPlayerId));

		verify(gameService).create(game, nonExistingPlayerId);
		assertTrue(exception.getMessage().contains(NOT_FOUND.toString()));
	}

}
