package com.rgallego.web.controllers;

import com.rgallego.web.bean.TweetRequest;
import com.rgallego.web.documents.TweetDocument;
import com.rgallego.web.services.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tweets")
@Slf4j
@CrossOrigin(origins = "*")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping("/getByDate")
    public Flux<TweetDocument> getByDate(@RequestBody TweetRequest request) {
        log.info("Get tweets by date Request");
        return tweetService.getTweetsByDate(request);
    }

    @PostMapping("/countByDate")
    public Mono<Long> countByDate(@RequestBody TweetRequest request) {
        log.info("Count tweets by date Request");
        return tweetService.countAllTweetsByDate(request);
    }

    @PostMapping("/getGeoByDate")
    public Flux<TweetDocument> getGeoByDate(@RequestBody TweetRequest request) {
        log.info("Get geo tweets by date Request");
        return tweetService.getGeoTweetsByDate(request);
    }
}
