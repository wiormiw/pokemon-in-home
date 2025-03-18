package com.wiormiw.controller;

import com.wiormiw.repository.FriendsRepository;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/friends")
public class FriendsController {
    @Inject
    FriendsRepository friendsRepository;

    @POST
    @Path("/add/{friendId}")
    @Authenticated
    public Response addFriend(@PathParam("friendId") Long friendId, @Context SecurityContext ctx) {
        Long userId = Long.valueOf(ctx.getUserPrincipal().getName());
        friendsRepository.addFriend(userId, friendId);
        return Response.ok().build();
    }
}
