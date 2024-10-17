package com.ias.dynamodb.infraestructure.drivenadapters.dynamoadapter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Configuration
public class DynamoDbConfig {

    @Value("${aws.access-key-id}")
    private String accessKeyId;
    @Value("${aws.secret-access-key}")
    private String secretAccessKey;
    @Value("${aws.region}")
    private String region;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient(){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId,secretAccessKey);
        return DynamoDbAsyncClient.builder()
                .region(Region.of(this.region))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbAsyncClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
    }
}
