package com.rgallego.web.repositories;

import com.rgallego.web.documents.TweetDocument;
import com.rgallego.web.kafka.bean.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Flux<TweetDocument> findAllByDate(Long startDate, Long endDate) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("timestamp").gte(startDate),
                        Criteria.where("timestamp").lte(endDate)
                )
        );
        return template.find(query, TweetDocument.class);
    }


    //todo paginacion
//    Sort sort = Sort.by("id").descending();
//        if (StringUtils.isNotBlank(request.getField()) && StringUtils.isNotBlank(request.getOrder())) {
//        sort = FebsConstant.ORDER_ASC.equals(request.getOrder()) ?
//                Sort.by(request.getField()).ascending() :
//                Sort.by(request.getField()).descending();
//    }
//    Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), sort);
//        return template.find(query.with(pageable), clazz);
}
