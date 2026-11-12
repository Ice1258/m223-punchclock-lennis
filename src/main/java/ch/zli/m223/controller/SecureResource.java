package ch.zli.m223.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@Path("/secure")
@SecurityScheme(securitySchemeName = "BearerAuth", type = SecuritySchemeType.HTTP,
        scheme = "bearer", bearerFormat = "JWT")
@SecurityRequirement(name = "BearerAuth")
public class SecureResource {

    @GET
    @Path("/data")
    @RolesAllowed("Admin")
    public Response getSecureData() {
        return Response.ok("This is protected data.").build();
    }
}
