package com.pega.vote.bank.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pega_vote_bank_history" , indexes = {@Index(name = "i_votes_country", columnList = "country_from,year"),
		@Index(name = "i_votes_year", columnList = "year")})
public class VoteBankHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "country_from", nullable = false)
	private String countryFrom;

	@Column(name = "voted_for", nullable = false)
	private String votedFor;
	
	@Column(name = "year", nullable = false)
	private int year;


}
