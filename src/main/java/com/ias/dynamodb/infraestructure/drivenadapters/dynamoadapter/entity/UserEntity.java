package com.ias.dynamodb.infraestructure.drivenadapters.dynamoadapter.entity;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
@DynamoDbBean
public class UserEntity {

    public static final String TABLE_NAME = "user";

    private String userId;
    private String fullName;
    private String lastName;
    private String contactNumber;
    private AddressEntity address;
    private String createdTimeStamp;

    @DynamoDbPartitionKey
    public String getUserId(){ return  this.userId; }
}
