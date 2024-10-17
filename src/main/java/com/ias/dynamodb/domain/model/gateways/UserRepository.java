package com.ias.dynamodb.domain.model.gateways;

import com.ias.dynamodb.domain.model.Address;
import com.ias.dynamodb.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User>  create(User user);

    Flux<User> findAll();

    Mono<User> findById(String userId);

    Mono<User> updateUser(User user);

    Mono<Address> getUserAddress(String userId);

    Flux<User> findByFullNameAndCity(String fullName, String city);
}
