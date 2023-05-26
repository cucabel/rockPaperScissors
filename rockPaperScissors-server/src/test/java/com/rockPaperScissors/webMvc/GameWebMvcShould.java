package com.rockPaperScissors.webMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockPaperScissors.domain.Game;
import com.rockPaperScissors.domain.Player;
import static com.rockPaperScissors.domain.Winners.*;

@SpringBootTest
public class GameWebMvcShould {
	
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	ObjectMapper objectMapper;
	Game game;
	private final long existingPlayerId = 1L;
	private final long nonExistingPlayerId = 6L;
	private final String nickName = "Isabel";

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		objectMapper = new ObjectMapper();
		createPlayer();
		game = new Game();
		game.setPlayerSymbol(PAPER.toString());
		game.setEnemySymbol(ROCK.toString());
	}
	
	@Test
	public void create_a_game() throws Exception {
		String jsonRequest = objectMapper.writeValueAsString(game);
		
		MvcResult result = mockMvc
				.perform(post("/players/{playerId}/games", existingPlayerId).content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andReturn();
		String resultContent = result.getResponse().getContentAsString();
		boolean gameResult = objectMapper.readValue(resultContent, Boolean.class);
		
		assertTrue(gameResult);
	}
	
	@Test
	public void throw_a_not_found_response_status_exception_when_the_url_is_not_the_correct_one_when_creating_a_game() throws Exception {
		String jsonRequest = objectMapper.writeValueAsString(game);
		
		mockMvc.perform(post("/players/{playerId}/candies", existingPlayerId).content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void throw_a_bad_request_response_status_exception_when_the_web_request_does_not_have_a_body_when_creating_a_game() throws Exception {
		mockMvc.perform(post("/players/{playerId}/games", existingPlayerId))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void throw_a_not_found_response_status_exception_when_a_player_does_not_exist_when_creating_a_game() throws Exception {
		String jsonRequest = objectMapper.writeValueAsString(game);
		
		mockMvc.perform(post("/players/{playerId}/games", nonExistingPlayerId).content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	public Player createPlayer() throws Exception {
		Player player = new Player(nickName);
		String jsonRequest = objectMapper.writeValueAsString(player);
		
		MvcResult result = mockMvc
				.perform(post("/players").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Player returnedPlayer = objectMapper.readValue(resultContent, Player.class);
		return returnedPlayer;
	}
}
