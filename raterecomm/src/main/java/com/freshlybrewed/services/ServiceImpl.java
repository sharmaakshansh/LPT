package com.freshlybrewed.services;
import com.freshlybrewed.raterecomm.entities.Review;
import com.freshlybrewed.raterecomm.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.freshlybrewed.raterecomm.dao.MentorDao;
import com.freshlybrewed.raterecomm.dao.RecommendationRepository;
import com.freshlybrewed.raterecomm.dao.ReviewDao;
import com.freshlybrewed.raterecomm.dao.UserDao;
import com.freshlybrewed.raterecomm.entities.Mentor;
import com.freshlybrewed.raterecomm.entities.Recommendation;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceImpl implements MyService {

    private final MentorDao mentorDao;
    
    private RecommendationRepository recommendationRepository;

    private final UserDao userDao;
    private final ReviewDao reviewDao;
    
    @Autowired
    public ServiceImpl (MentorDao mentorDao, UserDao userDao, ReviewDao reviewDao) {
        this.mentorDao = mentorDao;
		this.userDao = userDao;
		this.reviewDao = reviewDao;
		
    }
    
    
    
    

    public Mentor saveMentor(Mentor mentor) {
        // Implement the logic to save a mentor to the database
        return mentorDao.save(mentor);
    }

    @Override
    @Transactional
    public ResponseEntity<String> rateMentor(Long mentorId, Long userId, int rating) {
        // Fetch the mentor by ID from the repository
        Optional<Mentor> optionalMentor = mentorDao.findById(mentorId);

        if (optionalMentor.isEmpty()) {
            // Mentor not found, return a 404 Not Found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mentor not found with ID: " + mentorId);
        }

        Mentor mentor = optionalMentor.get();

        // Fetch the user by ID from the repository (assuming you have a userDao)
        Optional<User> optionalUser = userDao.findById(userId);

        if (optionalUser.isEmpty()) {
            // User not found, return a 404 Not Found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }

        // Calculate the new overall rating for the mentor
        int currentRating = mentor.getOverallRating();
        int numberOfRatings = mentor.getNumberOfRatings();
        int newRating = ((currentRating * numberOfRatings) + rating) / (numberOfRatings + 1);

        // Update the mentor's rating and increment the number of ratings
        mentor.setOverallRating(newRating);
        mentor.setNumberOfRatings(numberOfRatings + 1);

        // Save the updated mentor data
        mentorDao.save(mentor);

        // Return a success response
        return ResponseEntity.ok("Mentor rated successfully.");
    }

    

    
    

        @Override
        public ResponseEntity<String> reviewMentor(Long mentorId, Long userId, String review) {
            // Check if the mentor and user exist in the database
            Mentor mentor = mentorDao.findById(mentorId).orElse(null);
            User user = userDao.findById(userId).orElse(null);

            if (mentor == null || user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mentor or User not found.");
            }

            // Validate the review text (maximum 50 words)
            if (review == null || review.isEmpty()) {
                return ResponseEntity.badRequest().body("Review text cannot be empty.");
            }

            if (review.split("\\s+").length > 50) {
                return ResponseEntity.badRequest().body("Review text should not exceed 50 words.");
            }
             
            // Create a new review and save it
            Review newReview = new Review(mentor, review);
            reviewDao.save(newReview);

            return ResponseEntity.ok("Review submitted successfully.");
        }
    

    @Override
    @Transactional
    public List<Mentor> getMentorsWithReviewsByRating(int rating, Long userId) {
        // Fetch mentors by rating using the mentorRepository
        List<Mentor> mentorsWithRating = mentorDao.findByOverallRating(rating);

        // Iterate through mentors and fetch reviews for the user
        for (Mentor mentor : mentorsWithRating) {
            List<Review> reviews = reviewDao.findByMentorMentorId(mentor.getmentorId());
            mentor.setReviews(reviews);
        }

        return mentorsWithRating;
    }


    @Override
    @Transactional
    public void recommendStudent(Long mentorId, String recommendationText) {
        // Fetch the mentor by ID from the mentorRatingDao
        Optional<Mentor> optionalMentor = mentorDao.findById(mentorId);

        if (optionalMentor.isPresent()) {
            Mentor mentor = optionalMentor.get();

            // Create a new Recommendation entity
            Recommendation recommendation = new Recommendation();
            recommendation.setRecommenderName(mentor.getMentorName()); // Assuming mentor's name as recommender
            recommendation.setRecommendationText(recommendationText);

            // Generate a unique identifier (e.g., UUID) for the recommendation
            String uniqueIdentifier = generateUniqueIdentifier(); // Implement this method

            // Create a shareable link using the unique identifier
            String shareableLink = generateShareableLink(uniqueIdentifier); // Implement this method

            // Store the unique identifier and recommendation in the database
            recommendation.setUniqueIdentifier(uniqueIdentifier);
            recommendationRepository.save(recommendation);

            // Add the recommendation to the mentor's list of recommendations
            mentor.getRecommendations().add(recommendation);

            // Save the updated mentor data with the new recommendation
            mentorDao.save(mentor);
        }
    }

    private String generateUniqueIdentifier() {
        // Implement the logic to generate a unique identifier (e.g., UUID)
        // You can use UUID.randomUUID() for generating a UUID
        // Replace this with your actual implementation
        return UUID.randomUUID().toString();
    }

    private String generateShareableLink(String uniqueIdentifier) {
        // Define the base URL where the recommendations can be accessed
        String baseUrl = "https://example.com/recommendations";

        // Construct the complete URL with the unique identifier as a path parameter
        return baseUrl + "/" + uniqueIdentifier;
    }

    
    @Override
    @Transactional
    public void submitRecommendation(Long mentorId, String recommenderName, String studentName, String recommendationText) {
        // Fetch the mentor by ID from the mentorRatingDao
        Optional<Mentor> optionalMentor = mentorDao.findById(mentorId);

        if (optionalMentor.isPresent()) {
            Mentor mentor = optionalMentor.get();

            // Create a new Recommendation entity
            Recommendation recommendation = new Recommendation();
            recommendation.setRecommenderName(recommenderName);
            recommendation.setStudentName(studentName);
            recommendation.setRecommendationText(recommendationText);

            // Add the recommendation to the mentor's list of recommendations
            mentor.getRecommendations().add(recommendation);

            // Save the updated mentor data with the new recommendation
            mentorDao.save(mentor);

            // Save the recommendation using the recommendationRepository
            recommendationRepository.save(recommendation);
        }
        // You can choose to log a message or take other action if the mentor is not found,
        // but no exception is thrown in this version.
    }

    @Override
    public Recommendation getRecommendationById(Long recommendationId) {
        // Fetch the recommendation by ID from the recommendationRepository
        Optional<Recommendation> optionalRecommendation = recommendationRepository.findById(recommendationId);

        // If the recommendation is found, return it; otherwise, return null
        return optionalRecommendation.orElse(null);
    }
    
    @Override
    public void createMentor(Mentor mentor) {
        // Ensure that the mentorName is not null or empty before saving
        if (mentor.getMentorName() != null && !mentor.getMentorName().isEmpty()) {
            // Set the initial overall rating and number of ratings
            mentor.setOverallRating(0); // Assuming initial rating is 0
            mentor.setNumberOfRatings(0); // Initially, no ratings

            // Save the mentor to the database
            mentorDao.save(mentor);
        } else {
            throw new IllegalArgumentException("Mentor name is required.");
        }
    }
    
    @Override
    public List<Mentor> getAllMentors() {
        return mentorDao.findAll();
    }
    
    @Override
    public void createUser(User user) {
    	
        userDao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
	
	}
    

