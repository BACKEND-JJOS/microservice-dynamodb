package com.ias.dynamodb.domain.usecase;

import com.ias.dynamodb.domain.model.User;
import com.ias.dynamodb.domain.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UpdateExistingUserUseCase {
    private  final UserRepository userRepository;

    public Mono<User> execute(User userEntity){
        return userRepository.updateUser(userEntity);
    }
}
