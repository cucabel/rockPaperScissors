package com.rockPaperScissors.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rockPaperScissors.domain.Player;
import com.rockPaperScissors.repository.IPlayerRepository;
import com.rockPaperScissors.service.IPlayerService;

@Service(value = "playerService")
public class PlayerServiceImpl implements IPlayerService{
	
	private IPlayerRepository playerRepository;
	
	@Autowired
	public PlayerServiceImpl(IPlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	public Player create(Player player) {
		return playerRepository.save(player);
	}

	@Override
	public Optional<Player> getBy(long playerId) {
		Optional<Player> player = playerRepository.findById(playerId);
		return player;
	}

}
