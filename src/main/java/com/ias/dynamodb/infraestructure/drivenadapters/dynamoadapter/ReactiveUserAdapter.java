package com.ias.dynamodb.infraestructure.drivenadapters.dynamoadapter;

import com.ias.dynamodb.domain.model.Address;
import com.ias.dynamodb.domain.model.User;
import com.ias.dynamodb.infraestructure.drivenadapters.dynamoadapter.entity.UserEntity;
import com.ias.dynamodb.domain.model.gateways.UserRepository;
import lombok.AllArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@AllArgsConstructor
public class ReactiveUserAdapter implements UserRepository {
    private final UserDynamoDbGateway userGateway;
    private final ObjectMapper reactiveMapper;

    public Mono<User> create(User user) {
        UserEntity userEntity = reactiveMapper.map(user, UserEntity.class);
        return userGateway.save(userEntity)
                .thenReturn(user);
    }

    public Flux<User> findAll() {
        return userGateway.findAll()
                .map(userEntity -> reactiveMapper.map(userEntity, User.class))
                .doOnNext(Objects::requireNonNull);
    }

    public Mono<User> findById(String userId) {
        return userGateway.findById(userId)
                .map(userEntity -> reactiveMapper.map(userEntity, User.class))
                .doOnError(e -> {
                    // Aquí puedes registrar información sobre el error
                    System.err.println("Error buscando usuario con ID: " + userId + " e: " + e);
                    e.printStackTrace(); // O usa un logger para un mejor manejo
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .doOnSuccess(Objects::requireNonNull);
    }

    public Mono<User> updateUser(User user) {
        UserEntity userEntity = reactiveMapper.map(user, UserEntity.class);
        return userGateway.update(userEntity)
                .thenReturn(user);
    }

    public Mono<Address> getUserAddress(String userId) {
        return userGateway.getUserAddress(userId)
                .map(addressEntity -> reactiveMapper.map(addressEntity, Address.class));
    }

    @Override
    public Flux<User> findByFullNameAndCity(String fullName, String city) {
        return userGateway.findByFullNameAndCity(fullName, city)
                .map(userEntity -> reactiveMapper.map(userEntity, User.class))
                .doOnNext(Objects::requireNonNull);
    }
}
