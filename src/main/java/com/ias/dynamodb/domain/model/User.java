package com.ias.dynamodb.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class User {
    private String userId;
    private String fullName;
    private String lastName;
    private String contactNumber;
    private Address address;
    private String createdTimeStamp;
}
