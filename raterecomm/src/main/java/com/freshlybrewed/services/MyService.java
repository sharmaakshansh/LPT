package com.freshlybrewed.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.freshlybrewed.raterecomm.entities.Mentor;
import com.freshlybrewed.raterecomm.entities.Recommendation;
import com.freshlybrewed.raterecomm.entities.User;

public interface MyService {

    // Rate a mentor out of 5 stars and update the overall rating
    public ResponseEntity<String> rateMentor(Long mentorId, Long userId, int rating);

    // Submit a review for a mentor (limited to 50 words)
    ResponseEntity<String> reviewMentor(Long mentorId, Long userId, String reviewText);

    

    // Recommend a student and generate a shareable link for the letter of recommendation
    void recommendStudent(Long mentorId, String recommendationText);

	void submitRecommendation(Long mentorId, String recommenderName, String studentName, String recommendationText);

	Recommendation getRecommendationById(Long recommendationId);
	 
	 Mentor saveMentor(Mentor mentor);
	 
	 void createMentor(Mentor mentor);
	 List<Mentor> getAllMentors();

	public List<Mentor> getMentorsWithReviewsByRating(int rating, Long userId);
	void createUser(User user);
	List<User> getAllUsers();
}
