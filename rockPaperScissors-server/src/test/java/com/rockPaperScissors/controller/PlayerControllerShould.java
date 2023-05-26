package com.rockPaperScissors.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.rockPaperScissors.domain.Player;
import com.rockPaperScissors.service.IPlayerService;
import com.rockPaperScissors.service.impl.PlayerServiceImpl;

@SpringBootTest
public class PlayerControllerShould {
	
	private PlayerController playerController;
	private IPlayerService playerService;
	private final long existingPlayerId = 1L;
	private final long nonExistingPlayerId = 2L;
	private final String nickName = "Isabel";
	
	@BeforeEach
	public void setUp() {
		playerService = mock(PlayerServiceImpl.class);
		playerController = new PlayerController(playerService);
	}
	
	@Test
	public void create_a_player() {
		Player player = new Player(nickName);
		Player mockedPlayer = new Player(nickName);
		mockedPlayer.setId(existingPlayerId);
		when(playerService.create(player)).thenReturn(mockedPlayer);
		
		Player returnedPlayer = playerController.create(player);
		
		verify(playerService).create(player);
		assertTrue(returnedPlayer.equals(mockedPlayer));
	}
	
	@Test
	public void get_a_player_by_id() {
		Player player = new Player(nickName);
		player.setId(existingPlayerId);
		Optional<Player> mockedOptionalPlayer = Optional.of(player);
		when(playerService.getBy(existingPlayerId)).thenReturn(mockedOptionalPlayer);	
		
		Player returnedPlayer = playerController.getById(existingPlayerId);
		
		verify(playerService).getBy(existingPlayerId);
		assertTrue(returnedPlayer.equals(player));
	}
	
	@Test
	public void throw_a_response_status_not_found_exception_when_a_player_does_not_exist_when_getting_a_player() {
		when(playerService.getBy(nonExistingPlayerId)).thenThrow(new ResponseStatusException(NOT_FOUND));	
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> playerController.getById(nonExistingPlayerId));
		
		verify(playerService).getBy(nonExistingPlayerId);
		assertTrue(exception.getMessage().contains(NOT_FOUND.toString()));
	}

}
