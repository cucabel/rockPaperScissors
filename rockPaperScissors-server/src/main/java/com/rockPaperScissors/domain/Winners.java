package com.rockPaperScissors.domain;

public enum Winners {
	
	ROCK("scissors"),
	PAPER("rock"),
	SCISSORS("paper");
	
	private final String looser;

	private Winners(String looser) {
		this.looser = looser;
	}

	public String getLooser() {
		return looser;
	}

}
