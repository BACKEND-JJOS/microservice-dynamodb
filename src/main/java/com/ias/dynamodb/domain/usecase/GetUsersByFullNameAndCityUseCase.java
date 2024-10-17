package com.ias.dynamodb.domain.usecase;

import com.ias.dynamodb.domain.model.User;
import com.ias.dynamodb.domain.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class GetUsersByFullNameAndCityUseCase {
    private final UserRepository userRepository;

    public Flux<User> execute(String fullName, String city){
        return userRepository.findByFullNameAndCity(fullName, city);
    }
}
