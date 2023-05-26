package com.rockPaperScissors.webMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockPaperScissors.domain.Player;

@SpringBootTest
public class PlayerWebMvcShould {
	
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	ObjectMapper objectMapper;
	private final long existingPlayerId = 1L;
	private final long nonExistingPlayerId = 4L;
	private final String nickName = "Isabel";
	
	@BeforeEach
	public void setUp() throws JsonProcessingException {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		objectMapper = new ObjectMapper();
	}
	
	@Test
	public void create_a_player() throws Exception {
		String jsonRequest = getJsonRequest();
		
		MvcResult result = mockMvc
				.perform(post("/players").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andReturn();
		Player returnedPlayer = getResult(result);
		
		assertTrue(returnedPlayer.getNickName().equals(nickName));
	}
	
	@Test
	public void throw_a_not_found_response_status_exception_when_the_url_is_not_the_correct_one_when_creating_a_player() throws Exception {
		String jsonRequest = getJsonRequest();

		mockMvc.perform(post("/").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void throw_a_bad_request_response_status_exception_when_the_web_request_does_not_have_a_body_when_creating_a_player() throws Exception {
		mockMvc.perform(post("/players"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void get_a_player_by_id() throws Exception {
		String jsonRequest = getJsonRequest();

		mockMvc.perform(post("/players").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE));
		MvcResult result = mockMvc
				.perform(get("/players/{playerId}", existingPlayerId))
				.andExpect(status().isOk())
				.andReturn();
		Player returnedPlayer = getResult(result);
		
		assertTrue(returnedPlayer.getNickName().equals(nickName));
	}

	@Test
	public void throw_a_not_found_response_status_exception_when_the_url_is_not_the_correct_one_when_getting_a_player() throws Exception {
		String jsonRequest = getJsonRequest();

		mockMvc.perform(post("/players").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE));
		mockMvc.perform(get("/{playerId}", existingPlayerId))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void throw_a_not_found_response_status_exception_when_a_player_does_not_exist_when_getting_a_player() throws Exception {
		mockMvc.perform(get("/players/{playerId}", nonExistingPlayerId))
				.andExpect(status().isNotFound());
	}
	

	public String getJsonRequest() throws JsonProcessingException {
		Player player = new Player();
		player.setNickName(nickName);
		
		return objectMapper.writeValueAsString(player);
	}
	
	public Player getResult(MvcResult result) throws Exception {
		String resultContent = result.getResponse().getContentAsString();
		return objectMapper.readValue(resultContent, Player.class);
	}

}
