package com.pega.vote.bank.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pega.vote.bank.exception.InputNotValidException;
import com.pega.vote.bank.exception.SameCountryVoteException;
import com.pega.vote.bank.model.NewVoteRequest;
import com.pega.vote.bank.model.VoteSummaryResult;
import com.pega.vote.bank.service.VoteBankService;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor
public class VoteBankController {

	private final VoteBankService voteBankService;

	/**
	 * saves the result of vote request
	 * There is no year and country validation as of now
	 * @param year
	 * @param newVoteRequest
	 * @return status code
	 * @throws InputNotValidException 
	 * @throws SameCountryVoteException 
	 */
	@PostMapping("/{year}")
	public ResponseEntity<Void> saveVote(@PathVariable("year") int year,
			@RequestBody @Valid NewVoteRequest newVoteRequest) throws InputNotValidException, SameCountryVoteException {
		checkYearValidation(year);
		voteBankService.saveVote(year, newVoteRequest);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * get the aggregate result per year
	 * @param year
	 * @return the 1st 3 position of result
	 * @throws NotFoundException
	 * @throws InputNotValidException 
	 */
	@GetMapping("/{year}")
	public ResponseEntity<VoteSummaryResult> getVoteAggregatePerYear(@PathVariable("year") int year)
			throws NotFoundException, InputNotValidException {
		checkYearValidation(year);
		return new ResponseEntity<VoteSummaryResult>(voteBankService.getVoteResultPerYear(year), HttpStatus.OK);
	}

	/**
	 * get aggregate result per year and country
	 * Method does not do country validation
	 * @param year
	 * @param country
	 * @return the 1st 3 position of result
	 * @throws NotFoundException
	 * @throws InputNotValidException 
	 */
	@GetMapping("/{year}/{country}")
	public ResponseEntity<VoteSummaryResult> getVoteAggregatePerYearAndCountry(@PathVariable("year") int year,
			@PathVariable("country") String country) throws NotFoundException, InputNotValidException {
		
		checkCountryValidation(country);
		checkYearValidation(year);
		return new ResponseEntity<VoteSummaryResult>(voteBankService.getVoteResultPerYearAndCountry(year, country),
				HttpStatus.OK);
	}
	
	/**
	 * simple validation for a year , checking 4 numerical digits 
	 * @param year
	 * @throws InputNotValidException
	 */
	private void checkYearValidation(int year) throws InputNotValidException {
		if(!String.valueOf(year).matches("[0-9]{4}$")) {
			throw new InputNotValidException("input year not valid : " + year);
		}
	}
	
	/**
	 * simple validation checking country is consisting only valid alphabets
	 * @param country
	 * @throws InputNotValidException
	 */
	private void checkCountryValidation(String country) throws InputNotValidException {
		if(!country.matches("^[A-Za-z]+$")) {
			throw new InputNotValidException("input country not valid : " + country);
		}
	}

}
