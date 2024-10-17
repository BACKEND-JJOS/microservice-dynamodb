package com.ias.dynamodb.infraestructure.entrypoints;

import com.ias.dynamodb.domain.model.User;
import com.ias.dynamodb.domain.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final CreateUserUseCase createUserUseCase;
    private final FindAllUserUseCase findAllUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateExistingUserUseCase updateExistingUserUseCase;
    private final GetUserAddressByIdUserUseCase getUserAddressByIdUserUseCase;
    private final GetUsersByFullNameAndCityUseCase getUsersByFullNameAndCityUseCase;


    public Mono<ServerResponse> listenGETUserById(ServerRequest serverRequest) {
        return getUserByIdUseCase.execute(serverRequest.pathVariable("id"))
                .flatMap(user -> ServerResponse.ok().bodyValue(user) )
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue("The user not exist"));

    }

    public Mono<ServerResponse> listenPOSTCreateNewUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
                .flatMap(userEntity -> createUserUseCase.execute(userEntity)
                        .flatMap(userCreated -> ServerResponse.ok().bodyValue(userCreated))
                        .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error : " + e.getMessage()))
                );
    }

    public Mono<ServerResponse> listenGETAllUsers(ServerRequest serverRequest) {
        return  findAllUserUseCase.execute()
                .collectList()
                .flatMap(userEntities -> ServerResponse.ok().bodyValue(userEntities));
    }

    public Mono<ServerResponse> listentPUTUpdateUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
                .flatMap(user -> updateExistingUserUseCase.execute(user)
                        .flatMap(userUpdate -> ServerResponse.ok().bodyValue(userUpdate))
                );
    }

    public Mono<ServerResponse> listenGETUserAddressByUserId(ServerRequest serverRequest){
        return getUserAddressByIdUserUseCase.execute(serverRequest.pathVariable("userId"))
                .flatMap(addressEntity -> ServerResponse.ok().bodyValue(addressEntity));
    }

    public Mono<ServerResponse> listentGETUserFilteringByFullNameAndCity(ServerRequest serverRequest) {
        return  getUsersByFullNameAndCityUseCase.execute(
                        serverRequest.queryParam("fullName").orElse(""),
                        serverRequest.queryParam("city").orElse("")
                        )
                        .collectList()
                        .flatMap(userFiltering -> ServerResponse.ok().bodyValue(userFiltering))
                        .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error : " + e.getMessage()));
    }
}
