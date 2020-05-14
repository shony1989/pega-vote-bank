package com.pega.vote.bank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pega.vote.bank.domain.VoteBankHistory;
import com.pega.vote.bank.exception.SameCountryVoteException;
import com.pega.vote.bank.model.NewVoteRequest;
import com.pega.vote.bank.model.VoteCountResult;
import com.pega.vote.bank.model.VoteSummaryResult;
import com.pega.vote.bank.repo.VoteBankHistoryRepository;
import com.pega.vote.bank.service.VoteBankService;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteBankServiceImpl implements VoteBankService {

	private final VoteBankHistoryRepository voteBankHistoryRepo;

	@Override
	public VoteBankHistory saveVote(int year, NewVoteRequest newVoteRequest) throws SameCountryVoteException {
		if(newVoteRequest.getCountryFrom().equalsIgnoreCase(newVoteRequest.getVotedFor())) {
			throw new SameCountryVoteException("Cant vote for your own country : " + newVoteRequest.getVotedFor());
		}
		return voteBankHistoryRepo.save(VoteBankHistory.builder().countryFrom(newVoteRequest.getCountryFrom())
				.votedFor(newVoteRequest.getVotedFor()).year(year).build());

	}

	//this method does not consider if vote count aggregate is same for more than 1 country or a tie position
	@Override
	public VoteSummaryResult getVoteResultPerYear(int year) throws NotFoundException {
		List<VoteCountResult> resultList = voteBankHistoryRepo.retrieveVoteSummaryResultByYear(year);

		if (resultList.isEmpty()) {
			throw new NotFoundException("no result found for year: " + year);
		}

		return VoteSummaryResult.builder().first(resultList.get(0).getCountry())
				.second(1 >= resultList.size() ? null : resultList.get(1).getCountry())
				.third(2 >= resultList.size() ? null : resultList.get(2).getCountry()).build();
	}

	//this method does not consider if vote count aggregate is same for more than 1 country or a tie position
	//the below method returns the 1st 3 counts ,it can be same number of votes for each country
	@Override
	public VoteSummaryResult getVoteResultPerYearAndCountry(int year, String country) throws NotFoundException {
		List<VoteCountResult> resultList = voteBankHistoryRepo.retrieveVoteSummaryResultByYearAndCountry(year, country);

		if (resultList.isEmpty()) {
			throw new NotFoundException("no result found for year: " + year + "and country: " + country);
		}

		return VoteSummaryResult.builder().first(resultList.get(0).getCountry())
				.second(1 >= resultList.size() ? null : resultList.get(1).getCountry())
				.third(2 >= resultList.size() ? null : resultList.get(2).getCountry()).build();
	}

}
