package com.rgallego.web.services;

import com.rgallego.web.bean.TweetRequest;
import com.rgallego.web.documents.TweetDocument;
import com.rgallego.web.repositories.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TweetService {

    private final long oneDayInMilisec = ((24 * 60 * 60) - 1) * 1000; // ((24h x 60min x 60sec) - 1sec) x 1000 miliseconds

    @Autowired
    private TweetRepository tweetRepository;

    public Flux<TweetDocument> getTweetsByDate(TweetRequest request) {
        // Add 23:59:59 to the final date for including this day in the filter.
        request.setTo(request.getTo() + oneDayInMilisec);
        log.debug("getTweetsByDate Request: {}", request.toString());
        return tweetRepository.findByDate(request);
    }

    public Mono<Long> countAllTweetsByDate(TweetRequest request) {
        // Add 23:59:59 to the final date for including this day in the filter.
        request.setTo(request.getTo() + oneDayInMilisec);
        return tweetRepository.countAllByDate(request);
    }

    public Flux<TweetDocument> getGeoTweetsByDate(TweetRequest request) {
        // Add 23:59:59 to the final date for including this day in the filter.
        request.setTo(request.getTo() + oneDayInMilisec);
        return tweetRepository.findAllByDateAndCoordinatesExists(request);
    }
}
