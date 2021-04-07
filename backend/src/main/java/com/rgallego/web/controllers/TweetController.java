package com.rgallego.web.controllers;

import com.rgallego.web.documents.TweetDocument;
import com.rgallego.web.services.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/tweets")
@Slf4j
@CrossOrigin(origins = "*")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @GetMapping("/getByDate")
    public Flux<TweetDocument> getByDate(@RequestParam Long from,
                                         @RequestParam Long to) {
        log.info("Get tweets by date Request");
        return tweetService.getTweetsByDate(from, to);
    }

    @GetMapping("/getGeoByDate")
    public Flux<TweetDocument> getGeoByDate(@RequestParam Long from,
                                            @RequestParam Long to) {
        log.info("Get geo tweets by date Request");
        return tweetService.getGeoTweetsByDate(from, to);
    }
}
