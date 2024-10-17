package com.ias.dynamodb.domain.usecase;

import com.ias.dynamodb.domain.model.Address;
import com.ias.dynamodb.domain.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class GetUserAddressByIdUserUseCase {
    private final UserRepository userRepository;

    public Mono<Address> execute(String userId){
        return userRepository.getUserAddress(userId);
    }
}
