package com.rockPaperScissors.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.rockPaperScissors.domain.Game;
import com.rockPaperScissors.domain.Player;
import com.rockPaperScissors.repository.IGameRepository;
import com.rockPaperScissors.repository.IPlayerRepository;
import com.rockPaperScissors.util.PlayerNotFoundException;
import static com.rockPaperScissors.domain.Winners.*;

@SpringBootTest
public class GameServiceImplShould {
	
	private GameServiceImpl gameService;
	private IGameRepository gameRepository;
	private IPlayerRepository playerRepository;
	ArgumentCaptor<Game> gameCaptor;
	Game game;
	private final long existingPlayerId = 1L;
	private final long nonExistingPlayerId = 2L;
	private final String nickName = "Isabel";
	
	@BeforeEach
	public void setUp() {
		gameRepository = mock(IGameRepository.class);
		playerRepository = mock(IPlayerRepository.class);
		gameService = new GameServiceImpl(gameRepository, playerRepository);
		gameCaptor = ArgumentCaptor.forClass(Game.class);
		game = new Game();
		game.setPlayerSymbol(PAPER.toString());
		game.setEnemySymbol(ROCK.toString());
	}
	
	@Test
	public void create_a_game() throws PlayerNotFoundException {
		Player player = new Player(nickName);
		player.setId(existingPlayerId);
		Optional<Player> mockedOptionalPlayer = Optional.of(player);
		Game mockedGame = new Game();
		mockedGame.setId(1L);
		mockedGame.setPlayerSymbol(PAPER.toString());
		mockedGame.setEnemySymbol(ROCK.toString());
		mockedGame.setResult(true);
		mockedGame.setPlayer(mockedOptionalPlayer.get());
		when(playerRepository.findById(existingPlayerId)).thenReturn(mockedOptionalPlayer);
		when(gameRepository.save(gameCaptor.capture())).thenReturn(mockedGame);
		
		boolean returnedResult = gameService.create(game, existingPlayerId);
		Game gameCaptorValue = gameCaptor.getValue();
		
		verify(playerRepository).findById(existingPlayerId);
		verify(gameRepository).save(gameCaptorValue);
		assertTrue(returnedResult);
		assertTrue(gameCaptorValue.getPlayerSymbol().equals(PAPER.toString()));
		assertTrue(gameCaptorValue.getEnemySymbol().equals(ROCK.toString()));
	}
	
	@Test
	public void throw_a_response_status_not_found_exception_when_a_player_does_not_exist_when_creating_a_game() {
		when(playerRepository.findById(nonExistingPlayerId)).thenThrow(new ResponseStatusException(NOT_FOUND));	
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> gameService.create(game, nonExistingPlayerId));
		
		verify(playerRepository).findById(nonExistingPlayerId);
		assertTrue(exception.getMessage().contains(NOT_FOUND.toString()));
	}

}
