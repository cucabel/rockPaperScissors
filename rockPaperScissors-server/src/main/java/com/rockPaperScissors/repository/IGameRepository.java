package com.rockPaperScissors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rockPaperScissors.domain.Game;

public interface IGameRepository extends JpaRepository<Game, Long>{

}
