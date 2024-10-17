package com.ias.dynamodb.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private String city;
    private String state;
    private String country;
}
