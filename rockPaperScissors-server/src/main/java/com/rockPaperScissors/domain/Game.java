package com.rockPaperScissors.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "Game")
@JsonIgnoreProperties(value = "player")
public class Game {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "game_id")
	private long id;
	private String playerSymbol;
	private String enemySymbol;
	private boolean result;
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	
	public Game() {};

	public Game(String playerSymbol, String enemySymbol, Player player) {
		this.playerSymbol = playerSymbol;
		this.enemySymbol = enemySymbol;
		this.player = player;
		play();
	}

	public void play() {
		String loser = Winners.valueOf(playerSymbol.toUpperCase()).getLooser();
		if (this.enemySymbol.toLowerCase().equals(loser)) {
			this.result = true;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlayerSymbol() {
		return playerSymbol;
	}

	public void setPlayerSymbol(String playerSymbol) {
		this.playerSymbol = playerSymbol;
	}

	public String getEnemySymbol() {
		return enemySymbol;
	}

	public void setEnemySymbol(String enemySymbol) {
		this.enemySymbol = enemySymbol;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
