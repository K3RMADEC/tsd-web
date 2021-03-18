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
import java.util.Date;
import java.util.stream.Collectors;

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
    private String matchingRules;

    /**
     * Constructor for parsing TweetData object to TweetDocument
     * @param tweetData
     */
    public TweetDocument(TweetData tweetData) {
        if(tweetData.getData() != null) {
            this.text = tweetData.getData().getText();
            this.authorId = tweetData.getData().getAuthorId();
            this.tweetId = tweetData.getData().getTweetId();
            // Fill and format timestamp and date fields
            if (tweetData.getData().getCreatedAt() != null) {
                // Format string date to miliseconds
                SimpleDateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                // Format date to readable string
                SimpleDateFormat outputSdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
                try {
                    Date date = inputSdf.parse(tweetData.getData().getCreatedAt());
                    this.timestamp = date.getTime();
                    this.date = outputSdf.format(date);
                } catch (ParseException e) {
                    log.error("Error parsing tweet date - {}", e.getCause());
                }
            }
        }
        // Fill and format matchingRules (join the Tags only)
        this.matchingRules = tweetData.getMatchingRules().stream()
                .map(Rule::getTag)
                .collect(Collectors.joining(", "));
    }
}
