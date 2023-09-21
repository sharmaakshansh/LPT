package com.freshlybrewed.raterecomm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshlybrewed.raterecomm.entities.Mentor;

public interface MentorDao extends JpaRepository< Mentor , Long > {
	List<Mentor> findByOverallRating(int rating);
}
