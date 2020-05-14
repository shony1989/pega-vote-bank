package com.pega.vote.bank.service;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pega.vote.bank.domain.VoteBankHistory;
import com.pega.vote.bank.exception.SameCountryVoteException;
import com.pega.vote.bank.model.NewVoteRequest;
import com.pega.vote.bank.model.VoteCountResult;
import com.pega.vote.bank.model.VoteSummaryResult;
import com.pega.vote.bank.repo.VoteBankHistoryRepository;

import javassist.NotFoundException;

@SpringBootTest
public class VoteBankServiceIntegrationTest {

	@Autowired
	private VoteBankHistoryRepository voteBankRepository;
	@Autowired
	private VoteBankService voteBankService;

	@Test
	public void testVoteAggregatePerYearRepo() {
		List<VoteCountResult> result = voteBankRepository.retrieveVoteSummaryResultByYear(2020);

		assertEquals("Poland", result.get(0).getCountry());
		assertEquals("Russia", result.get(1).getCountry());
		assertEquals("Belgium", result.get(2).getCountry());

	}

	@Test
	public void testVoteAggregatePerYearAndCountryRepo() {
		List<VoteCountResult> result = voteBankRepository.retrieveVoteSummaryResultByYearAndCountry(2020,
				"Netherlands");

		assertEquals("Poland", result.get(0).getCountry());
		assertEquals("Russia", result.get(1).getCountry());
		assertEquals("Belgium", result.get(2).getCountry());

	}

	@Test
	public void testSaveNewVoteRepo() {
		VoteBankHistory entity = voteBankRepository
				.save(VoteBankHistory.builder().countryFrom("Russia").votedFor("Poland").year(2019).build());
		assertTrue(entity.getId() != null);

	}

	@Test
	public void testSaveNewVoteService() throws SameCountryVoteException {
		VoteBankHistory entity = voteBankService.saveVote(2019, new NewVoteRequest("Belgium", "France"));
		assertTrue(entity.getId() != null);

	}
	
	@Test
	public void testSaveNewVoteServiceExceptionWithSameCountry(){
		assertThrows(SameCountryVoteException.class, () -> {
			voteBankService.saveVote(2019, new NewVoteRequest("Belgium", "Belgium"));
		});

	}

	@Test
	public void getVoteAggregatePerYear() throws NotFoundException {
		VoteSummaryResult result = voteBankService.getVoteResultPerYear(2020);
		assertEquals("Poland", result.getFirst());
		assertEquals("Russia", result.getSecond());
		assertEquals("Belgium", result.getThird());

	}

	@Test
	public void testVoteAggregatePerYearAndCountry() throws NotFoundException {
		VoteSummaryResult result = voteBankService.getVoteResultPerYearAndCountry(2020, "Netherlands");
		assertEquals("Poland", result.getFirst());
		assertEquals("Russia", result.getSecond());
		assertEquals("Belgium", result.getThird());

	}

	@Test
	public void getVoteAggregatePerYearException() {
		assertThrows(NotFoundException.class, () -> {
			voteBankService.getVoteResultPerYear(2016);
		});

	}
	
	@Test
	public void getVoteAggregatePerYearAndCountryException() {
		assertThrows(NotFoundException.class, () -> {
			voteBankService.getVoteResultPerYearAndCountry(2016, "Poland");
		});

	}

}
