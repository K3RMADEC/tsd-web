package com.rgallego.web.documents;

import com.rgallego.web.kafka.bean.Rule;
import com.rgallego.web.kafka.bean.TweetData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
@Document(collection = "tweets")
public class TweetDocument {

    private String date;
    private String text;
    @Indexed(name="author_index")
    private String authorId;
    @Id
    private String tweetId;
    @Indexed
    private Long timestamp;
    private List<Rule> matchingRules;

    /**
     * Constructor for parsing TweetData object to TweetDocument
     * @param tweetData
     */
    public TweetDocument(TweetData tweetData) {
        if(tweetData.getData() != null) {
            date = tweetData.getData().getCreatedAt();
            text = tweetData.getData().getText();
            authorId = tweetData.getData().getAuthorId();
            tweetId = tweetData.getData().getTweetId();
            if(date != null) {
                // Format string date to miliseconds
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                try {
                    timestamp = sdf.parse(date).getTime();
                } catch (ParseException e) {
                    log.error("Error parsing tweet date - {}", e.getCause());
                }
            }
        }
        matchingRules = tweetData.getMatchingRules();
    }
}
