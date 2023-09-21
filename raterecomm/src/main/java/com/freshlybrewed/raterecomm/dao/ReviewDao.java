package com.freshlybrewed.raterecomm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshlybrewed.raterecomm.entities.Review;



public interface ReviewDao extends JpaRepository<Review, Long> {
    List<Review> findByMentorMentorId(Long mentorId);
}
