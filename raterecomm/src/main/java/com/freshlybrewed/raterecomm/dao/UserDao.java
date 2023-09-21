package com.freshlybrewed.raterecomm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshlybrewed.raterecomm.entities.User;

public interface UserDao extends JpaRepository< User, Long > {

}
