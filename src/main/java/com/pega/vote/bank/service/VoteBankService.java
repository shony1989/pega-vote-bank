package com.pega.vote.bank.service;

import com.pega.vote.bank.domain.VoteBankHistory;
import com.pega.vote.bank.exception.SameCountryVoteException;
import com.pega.vote.bank.model.NewVoteRequest;
import com.pega.vote.bank.model.VoteSummaryResult;

import javassist.NotFoundException;

public interface VoteBankService {

	/**
	 * method to save new vote
	 * @param year
	 * @param newVoteRequest
	 * @return the saved entity
	 */
	public VoteBankHistory saveVote(int year, NewVoteRequest newVoteRequest) throws SameCountryVoteException;

	/**
	 * method to get vote aggregate for a year
	 * @param year
	 * @return aggregate result for year
	 * @throws NotFoundException
	 */
	public VoteSummaryResult getVoteResultPerYear(int year) throws NotFoundException;

	/**
	 * method to get vote aggregate for year and country
	 * @param year
	 * @param country
	 * @return aggregate result for year and country
	 * @throws NotFoundException
	 */
	public VoteSummaryResult getVoteResultPerYearAndCountry(int year, String country) throws NotFoundException;

}
