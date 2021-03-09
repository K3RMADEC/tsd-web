package com.rgallego.web.kafka.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet implements Serializable {

    /**
     * 2021-01-19T17:06:41.000Z
     */
    @JsonProperty("created_at")
    private String createdAt;
    private String text;
    @JsonProperty("author_id")
    private String authorId;
    @JsonProperty("id")
    private String tweetId;
}
