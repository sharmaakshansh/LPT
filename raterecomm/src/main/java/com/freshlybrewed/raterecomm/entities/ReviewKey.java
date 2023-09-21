package com.freshlybrewed.raterecomm.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Embeddable
public class ReviewKey implements Serializable {
    
	
    @Column(name = "mentorId")
	private Mentor mentor;

	
	@Column(name = "userId")
    private User user;

	public Mentor getMentor() {
		return mentor;
	}

	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    // Constructors, getters, setters, equals, and hashCode methods

    // ...
}

