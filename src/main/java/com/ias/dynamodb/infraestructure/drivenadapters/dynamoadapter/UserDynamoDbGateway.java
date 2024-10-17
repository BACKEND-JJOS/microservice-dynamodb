package com.ias.dynamodb.infraestructure.drivenadapters.dynamoadapter;

import com.ias.dynamodb.infraestructure.drivenadapters.dynamoadapter.entity.UserEntity;
import com.ias.dynamodb.infraestructure.drivenadapters.dynamoadapter.entity.AddressEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;


@Repository
public class UserDynamoDbGateway {

    private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;
    private final DynamoDbAsyncTable<UserEntity> userEntityDynamoDbAsyncTable;

    public UserDynamoDbGateway(DynamoDbEnhancedAsyncClient asyncClient) {
        this.enhancedAsyncClient = asyncClient;
        this.userEntityDynamoDbAsyncTable = enhancedAsyncClient.table(UserEntity.TABLE_NAME, TableSchema.fromBean(UserEntity.class));
    }

    public Mono<UserEntity> save(UserEntity userEntity) {
        return Mono.fromFuture(() -> userEntityDynamoDbAsyncTable.putItem(userEntity)).thenReturn(userEntity);
    }

    public Flux<UserEntity> findAll(){
        return  Flux.from(userEntityDynamoDbAsyncTable.scan().items());
    }

    public Mono<UserEntity> findById(String userId) {
        return Mono.fromFuture(() -> userEntityDynamoDbAsyncTable.getItem(getKeyBuild(userId)))
                .doOnError(throwable -> {
                    System.out.println("Error: ********* " +  throwable);
                });
    }

    public Mono<UserEntity> update(UserEntity userEntity) {
        return Mono.fromFuture(() -> userEntityDynamoDbAsyncTable.updateItem(userEntity)).thenReturn(userEntity);
    }

    public Mono<AddressEntity> getUserAddress(String userId) {
        return findById(userId)
                .map(UserEntity::getAddress);
    }

    public Flux<UserEntity> findByFullNameAndCity(String fullName, String city) {
        return Flux.from(userEntityDynamoDbAsyncTable
                .scan(
                        request -> request.filterExpression(
                                Expression.builder()
                                        .expression("fullName = :fullName and contains(addressEntity.city, :city) ")
                                        .expressionValues(Map.of(
                                                ":fullName", AttributeValue.builder().s(fullName).build(),
                                                ":city", AttributeValue.builder().s(city).build()
                                        ))
                                        .build())
                )).flatMapIterable(Page::items);
    }

    private Key getKeyBuild(String userId) {
        return Key.builder().partitionValue(userId).build();
    }
}
