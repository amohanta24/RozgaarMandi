package com.rozgaarmandi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rozgaarmandi.Models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{

}
