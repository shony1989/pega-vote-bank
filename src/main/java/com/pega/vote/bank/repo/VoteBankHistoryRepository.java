package com.pega.vote.bank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pega.vote.bank.domain.VoteBankHistory;
import com.pega.vote.bank.model.VoteCountResult;

public interface VoteBankHistoryRepository extends JpaRepository<VoteBankHistory, Long> {
	
	
	/**
	 * Returns the 1st 3 votes per each year
	 * @param year
	 * @return projection result of aggregate query
	 */
	@Query(value = "SELECT voted_for as country, COUNT(*) as count "+
            "FROM pega_vote_bank_history "+
            "WHERE year = :year " +
            "GROUP BY voted_for " +
            "ORDER BY count DESC " +
            "LIMIT 3", nativeQuery = true)
	List<VoteCountResult> retrieveVoteSummaryResultByYear(@Param("year") int year);
	
	/**
	 * * Returns the 1st 3 votes per each year and countryFrom
	 * @param year
	 * @param country
	 * @return projection result of aggregate query
	 */
	@Query(value = "SELECT voted_for as country, COUNT(*) as count "+
            "FROM pega_vote_bank_history "+
            "WHERE year = :year AND country_from = :country " +
            "GROUP BY voted_for " +
            "ORDER BY count DESC " +
            "LIMIT 3", nativeQuery = true)
	List<VoteCountResult> retrieveVoteSummaryResultByYearAndCountry(@Param("year") int year,@Param("country") String country);

}
