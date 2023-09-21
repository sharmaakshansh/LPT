package com.freshlybrewed.raterecomm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "mentor")
public class Mentor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentorId;

    @Column(nullable = false)
    private String mentorName;

    @Column(nullable = false)
    private int overallRating;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_rating_id")
    private List<Review> reviews;
    
    @Column(nullable = false) // Add this field
    private int numberOfRatings;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_rating_id")
    private List<Recommendation> recommendations;

    public Mentor() {
        this.reviews = new ArrayList<>();
        this.recommendations = new ArrayList<>();
    }

    // Getters and setters for all fields

    public Long getmentorId() {
        return mentorId;
    }

    public Mentor(Long mentorId, String mentorName) {
		super();
		this.mentorId = mentorId;
		this.mentorName = mentorName;
	}

	public void setmentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }
    
    public int getNumberOfRatings() { // Add this getter method
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) { // Add this setter method
        this.numberOfRatings = numberOfRatings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
