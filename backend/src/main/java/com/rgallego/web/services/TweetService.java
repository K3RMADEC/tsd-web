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

    @Autowired
    private TweetRepository tweetRepository;

    public Flux<TweetDocument> getTweetsByDate(Long from, Long to) {
        return tweetRepository.findAllByDate(from, to);
    }
}
