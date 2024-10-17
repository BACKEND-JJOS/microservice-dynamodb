package com.ias.dynamodb.infraestructure.entrypoints;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    private static final String USER_API = "api/user";
    @Bean
    public RouterFunction<ServerResponse> routerFunctions(Handler handler){
        return route(GET(USER_API), handler::listenGETAllUsers)
                .and(route(POST(USER_API), handler::listenPOSTCreateNewUser)
                        .and(route(PUT(USER_API),handler::listentPUTUpdateUser))
                        .and(route(GET(USER_API+"/filter-full-name-city"),handler::listentGETUserFilteringByFullNameAndCity))
                        .and(route(GET(USER_API+"/address/{userId}"),handler::listenGETUserAddressByUserId))
                        .and(route(GET(USER_API+"/{id}"),handler::listenGETUserById)));
    }
}
