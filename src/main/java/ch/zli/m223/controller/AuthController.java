package ch.zli.m223.controller;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.LoginDto;
import ch.zli.m223.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Tag(name = "Auth", description = "Login and token generation")
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@NotNull @Valid LoginDto dto) {
        return authService.login(dto.getUsername(), dto.getPassword());
    }
}
