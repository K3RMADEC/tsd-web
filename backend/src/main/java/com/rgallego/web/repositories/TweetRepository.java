package com.rgallego.web.repositories;

import com.rgallego.web.bean.TweetRequest;
import com.rgallego.web.documents.TweetDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TweetRepository {

    @Autowired
    ReactiveMongoTemplate template;


    public Mono<TweetDocument> save(TweetDocument tweet) {
        return template.save(tweet);
    }

    public Flux<TweetDocument> findByDate(TweetRequest request) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("timestamp").gte(request.getFrom()),
                        Criteria.where("timestamp").lte(request.getTo())
                )
        );
        if (request.getRules() != null) {
            query.addCriteria(Criteria.where("matchingRules").all(request.getRules()));
        }
        query.with(PageRequest.of(request.getPage(), request.getItemsPerPage(), Sort.by("timestamp").descending()));
        return template.find(query, TweetDocument.class);
    }

    public Mono<Long> countAllByDate(TweetRequest request) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("timestamp").gte(request.getFrom()),
                        Criteria.where("timestamp").lte(request.getTo())
                )
        );
        if (request.getRules() != null) {
            query.addCriteria(Criteria.where("matchingRules").all(request.getRules()));
        }
        return template.count(query, TweetDocument.class);
    }

    public Flux<TweetDocument> findAllByDateAndCoordinatesExists(TweetRequest request) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("timestamp").gte(request.getFrom()),
                        Criteria.where("timestamp").lte(request.getTo()),
                        Criteria.where("coordinates").exists(true)
                )
        );
        if (request.getRules() != null) {
            query.addCriteria(Criteria.where("matchingRules").all(request.getRules()));
        }
        return template.find(query, TweetDocument.class);
    }
}
