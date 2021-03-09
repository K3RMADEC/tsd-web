package com.rgallego.web.repositories;

import com.rgallego.web.documents.UserDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserRepository {

    @Autowired
    ReactiveMongoTemplate template;


    public Mono<UserDocument> findByUsername(String userName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(userName));
        return template.findOne(query, UserDocument.class);
    }

    public void saveUser(UserDocument userDocument) {
        template.insert(userDocument).subscribe();
    }

    public void removeUser(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        template.remove(query, UserDocument.class).block();
    }

    public Flux<UserDocument> findAll() {
        return template.findAll(UserDocument.class);
    }
}
