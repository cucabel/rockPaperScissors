package com.rockPaperScissors.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static com.rockPaperScissors.domain.Winners.*;

@SpringBootTest
public class GameShould {
	
	private final String nickName = "Isabel";
	
	@Test
	public void set_the_result_to_true_when_the_player_symbol_wins_against_the_enemy_symble() {
		Player player = new Player(nickName);
		Game game = new Game();
		game.setPlayerSymbol(PAPER.toString());
		game.setEnemySymbol(ROCK.toString());
		game.setPlayer(player);
		
		game.play();
		
		assertTrue(game.isResult());
	}
	
	@Test
	public void do_not_set_the_result_to_true_when_the_player_symbol_loses_against_the_enemy_symble() {
		Player player = new Player(nickName);
		Game game = new Game();
		game.setPlayerSymbol(ROCK.toString());
		game.setEnemySymbol(PAPER.toString());
		game.setPlayer(player);
		
		game.play();
		
		assertFalse(game.isResult());
	}

}
