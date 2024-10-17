package com.ias.dynamodb.infraestructure.drivenadapters.dynamoadapter.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamoDbBean
public class AddressEntity {
    private String city;
    private String state;
    private String country;
}
