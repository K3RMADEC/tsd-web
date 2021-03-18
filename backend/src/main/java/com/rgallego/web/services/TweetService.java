package com.rgallego.web.services;

import com.rgallego.web.documents.TweetDocument;
import com.rgallego.web.repositories.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class TweetService {

  private final long oneDayInMilisec = ((24 * 60 * 60) - 1) * 1000; // ((24h x 60min x 60sec) - 1sec) x 1000 miliseconds

  @Autowired
  private TweetRepository tweetRepository;

  public Flux<TweetDocument> getTweetsByDate(Long from, Long to) {
    to += oneDayInMilisec;
    return tweetRepository.findAllByDate(from, to);
  }
}
