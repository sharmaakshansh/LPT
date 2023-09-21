package com.freshlybrewed.raterecomm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshlybrewed.raterecomm.entities.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    // Add any custom query methods here if needed
}
