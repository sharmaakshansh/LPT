package com.freshlybrewed.raterecomm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.freshlybrewed.raterecomm.entities.Mentor;
import com.freshlybrewed.raterecomm.entities.Recommendation;
import com.freshlybrewed.raterecomm.entities.User;
import com.freshlybrewed.services.MyService;


import java.util.List;

@RestController
@RequestMapping("/api/mentor")
public class MyController {

    private final MyService myService;

    @Autowired
    public MyController(MyService myService) {
        this.myService = myService;
    }

    // Endpoint to rate a mentor
    @PostMapping("/{mentorId}/rate")
    public ResponseEntity<String> rateMentor(
        @PathVariable Long mentorId,
        @RequestParam Long userId,
        @RequestParam int rating
    ) {
        myService.rateMentor( mentorId,userId, rating);
        return ResponseEntity.ok("Mentor rated successfully.");
    }

 //@PostMapping("/{mentorId}/review")
    public ResponseEntity<String> reviewMentor(
            @PathVariable Long mentorId,
            @RequestParam Long userId,
            @RequestBody String review) {

        ResponseEntity<String> response = myService.reviewMentor(mentorId, userId, review);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Review submitted successfully.");
        } else {
            // Handle the case where the review was not successfully submitted
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Review submission failed.");
        }
    }



    @GetMapping("/mentorsbyrating")
    public ResponseEntity<List<Mentor>> getMentorsByRating(
            @RequestParam int rating,
            @RequestParam Long userId) {

        List<Mentor> mentorsWithReviews = myService.getMentorsWithReviewsByRating(rating, userId);

        if (mentorsWithReviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(mentorsWithReviews);
        }
    }


    // Endpoint to recommend a student
    @PostMapping("/{mentorId}/recommend")
    public ResponseEntity<String> recommendStudent(@PathVariable Long mentorId, @RequestBody String recommendation) {
        myService.recommendStudent(mentorId, recommendation);
        return ResponseEntity.ok("Student recommended successfully.");
    }
    
 // Endpoint to submit a recommendation letter for a student
    @PostMapping("/{mentorId}/recommendation")
    public ResponseEntity<String> submitRecommendation(@PathVariable Long mentorId,
                                                       @RequestParam("recommenderName") String recommenderName,
                                                       @RequestParam("studentName") String studentName,
                                                       @RequestParam("recommendationText") String recommendationText) {
        // Call the service method to submit a recommendation letter
        myService.submitRecommendation(mentorId, recommenderName, studentName, recommendationText);
        return ResponseEntity.ok("Recommendation submitted successfully.");
    }

    // Endpoint to retrieve a specific recommendation letter by ID
    @GetMapping("/{recommendationId}")
    public ResponseEntity<Recommendation> getRecommendationById(@PathVariable Long recommendationId) {
        // Call the service method to retrieve a recommendation by ID
        Recommendation recommendation = myService.getRecommendationById(recommendationId);
        if (recommendation != null) {
            return ResponseEntity.ok(recommendation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/mentors")
    public ResponseEntity<String> createMentor(@RequestBody Mentor mentor) {
        myService.createMentor(mentor);
        return ResponseEntity.ok("Mentor details saved successfully.");
    }
    @GetMapping("/mentors")
    public ResponseEntity<List<Mentor>> getAllMentors() {
        List<Mentor> mentors = myService.getAllMentors();
        return ResponseEntity.ok(mentors);
    }

    
    
    
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        myService.createUser(user);
        return ResponseEntity.ok("User created successfully.");
    }

    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = myService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
