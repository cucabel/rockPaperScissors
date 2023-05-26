package com.rockPaperScissors.service;

import java.util.Optional;

import com.rockPaperScissors.domain.Player;

public interface IPlayerService {

	Player create(Player player);

	Optional<Player> getBy(long playerId);

}
