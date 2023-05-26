package com.rockPaperScissors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rockPaperScissors.domain.Player;

public interface IPlayerRepository extends JpaRepository<Player, Long>{

}
