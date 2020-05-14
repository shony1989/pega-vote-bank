package com.pega.vote.bank.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pega.vote.bank.error.RestResponseEntityExceptionHandler;
import com.pega.vote.bank.model.NewVoteRequest;
import com.pega.vote.bank.service.VoteBankService;

@ExtendWith(SpringExtension.class)
public class VoteBankControllerTest {

	protected MockMvc mvc;

	@Mock
	private VoteBankService voteBankService;

	@InjectMocks
	private VoteBankController controller;

	@BeforeEach
	public void setUp() {
		this.mvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	@Test
	public void testSaveVoteSuccess() throws JsonProcessingException, Exception {

		NewVoteRequest request = new NewVoteRequest("Netherlands", "Poland");
		mvc.perform(MockMvcRequestBuilders.post("/votes/{year}", 2019).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(mapToJson(request))).andExpect(status().isCreated());

		verify(voteBankService, times(1)).saveVote(eq(2019), any(NewVoteRequest.class));

	}

	@Test
	public void testSaveVoteFailureWrongCountryAndYear() throws JsonProcessingException, Exception {

		NewVoteRequest request = new NewVoteRequest("Netherlands1234", "Poland12345");
		mvc.perform(MockMvcRequestBuilders.post("/votes/{year}", 2019).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(mapToJson(request))).andExpect(status().is4xxClientError());

		mvc.perform(MockMvcRequestBuilders.post("/votes/{year}", "xxx").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(mapToJson(request))).andExpect(status().is4xxClientError());

	}

	@Test
	public void testGetAgrregateResultPerYear() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/votes/{year}", 2019).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(voteBankService, times(1)).getVoteResultPerYear(2019);

		mvc.perform(MockMvcRequestBuilders.get("/votes/{year}", "xxxx").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
		
		mvc.perform(MockMvcRequestBuilders.get("/votes/{year}", 201).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is4xxClientError());

	}

	@Test
	public void testGetAgrregateResultPerYearAndCountry() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/votes/{year}/{country}", 2019, "Netherlands")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(voteBankService, times(1)).getVoteResultPerYearAndCountry(2019, "Netherlands");

		mvc.perform(MockMvcRequestBuilders.get("/votes/{year}/{country}", "xxxx", "Netherlands")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
		
		mvc.perform(MockMvcRequestBuilders.get("/votes/{year}/{country}", 201, "Netherlands")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());

		mvc.perform(MockMvcRequestBuilders.get("/votes/{year}/{country}", 2020, "Netherlands123")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
		

	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

}
