package com.freshlybrewed.raterecomm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recommenderName;

    @Column(nullable = false)
    private String studentName; // Add a field for studentName

    @Column(nullable = false)
    private String recommendationText;
    
    @Column(nullable = false)
    private String uniqueIdentifier;

    // Constructors, getters, and setters

    public Recommendation() {
    }

    public Recommendation(String recommenderName, String studentName, String recommendationText,String uniqueIdentifier) {
        this.recommenderName = recommenderName;
        this.studentName = studentName;
        this.recommendationText = recommendationText;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName; // Add the setter for studentName
    }

    public String getRecommendationText() {
        return recommendationText;
    }

    public void setRecommendationText(String recommendationText) {
        this.recommendationText = recommendationText;
    }
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier; // Add the setter for uniqueIdentifier
    }
}
