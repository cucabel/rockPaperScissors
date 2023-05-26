package com.rockPaperScissors.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rockPaperScissors.domain.Player;
import com.rockPaperScissors.repository.IPlayerRepository;

@SpringBootTest
public class PlayerServiceImplShould {
	
	private PlayerServiceImpl playerService;
	private IPlayerRepository playerRepository;
	Player player;
	private final long existingPlayerId = 1L;
	private final String nickName = "Isabel";
	
	@BeforeEach
	public void setUp() {
		playerRepository = mock(IPlayerRepository.class);
		playerService = new PlayerServiceImpl(playerRepository);
		player = new Player(nickName);
	}
	
	@Test
	public void create_a_player() {
		Player mockedPlayer = new Player(nickName);
		mockedPlayer.setId(existingPlayerId);

		when(playerRepository.save(player)).thenReturn(mockedPlayer);
		
		Player returnedPlayer = playerService.create(player);
		
		verify(playerRepository).save(player);
		assertTrue(returnedPlayer.equals(mockedPlayer));
	}

	@Test
	public void get_a_player_by_id() {
		player.setId(existingPlayerId);
		Optional<Player> mockedOptionalPlayer = Optional.of(player);
		when(playerRepository.findById(existingPlayerId)).thenReturn(mockedOptionalPlayer);
		
		Optional<Player> returnedOptionalPlayer = playerService.getBy(existingPlayerId);
		
		verify(playerRepository).findById(existingPlayerId);
		assertTrue(returnedOptionalPlayer.equals(mockedOptionalPlayer));
	}
}
