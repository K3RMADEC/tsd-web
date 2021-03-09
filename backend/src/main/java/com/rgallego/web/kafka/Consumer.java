package com.rgallego.web.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgallego.web.documents.TweetDocument;
import com.rgallego.web.kafka.bean.TweetData;
import com.rgallego.web.repositories.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
class Consumer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TweetRepository tweetRepository;

    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) throws JsonProcessingException, ParseException {
        log.debug("New message: {}-{}[{}] \"{}\"\n", topics.get(0), partitions.get(0), offsets.get(0), message);
        // Parse string JSON to Object
        TweetData tweetData = mapper.readValue(message, TweetData.class);
        tweetRepository.save(new TweetDocument(tweetData)).block();
    }

}
