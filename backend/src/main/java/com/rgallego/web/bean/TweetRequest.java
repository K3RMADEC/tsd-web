package com.rgallego.web.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TweetRequest implements Serializable {

    private Long from;
    private Long to;
    private List<String> rules;
    private Integer page;
    private Integer itemsPerPage;
}
