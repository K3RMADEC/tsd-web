package com.rgallego.web.documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "users")
public class UserDocument implements Serializable {

    @Id
    private String username;

    private String password;

    private String role;
}
