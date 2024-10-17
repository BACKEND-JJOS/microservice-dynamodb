package com.ias.dynamodb.domain.usecase;

import com.ias.dynamodb.domain.model.User;
import com.ias.dynamodb.domain.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public Mono<User> execute(User user){
        return Mono.just(user)
                .map(this::assingId)
                .flatMap(userRepository::create);
    }

    private User assingId(User user) {
        return user.toBuilder()
                .userId(UUID.randomUUID().toString())
                .build();
    }
}
