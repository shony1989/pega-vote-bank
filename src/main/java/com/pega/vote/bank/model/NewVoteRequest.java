package com.pega.vote.bank.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewVoteRequest {
	
	@NotEmpty(message = "countryFrom cannot be empty")
	@Pattern(regexp = "^[A-Za-z]+$" , message = "country name not valid")
	private String countryFrom;
	
	@NotEmpty(message = "votedFor cannot be empty")
	@Pattern(regexp = "^[A-Za-z]+$", message = "country name not valid")
	private String votedFor;

}
