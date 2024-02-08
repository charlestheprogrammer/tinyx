package com.epita.tinyx.gateway.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Provider
@PreMatching
public class GatewayFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {
        if (!shouldRedirect(reqContext)) {
            return;
        }
        System.out.println("Redirecting to " + portToRedirect(reqContext));
        try {
            String port = portToRedirect(reqContext);
            String host = hostToRedirect(reqContext);
            String newPath = reqContext.getUriInfo().getPath().replaceFirst("/" + reqContext.getUriInfo().getPath().split("/")[1], "");
            System.out.println("Redirecting to " + host + ":" + port + newPath);
            reqContext.abortWith(Response.temporaryRedirect(new URI("http://" + host + ":" + port + newPath)).build());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean shouldRedirect(ContainerRequestContext reqContext) {
        System.out.println(reqContext.getUriInfo().getPath());
        return reqContext.getUriInfo().getPath().startsWith("/repo-") || reqContext.getUriInfo().getPath().startsWith("/srvc-");
    }

    private String hostToRedirect(ContainerRequestContext reqContext) {
        String microservice = reqContext.getUriInfo().getPath().split("/")[1].split("-")[1];
        
        return "tinyx-" + microservice + ".tinyx.svc.cluster.local";
    }

    private String portToRedirect(ContainerRequestContext reqContext) {
        String microservice = reqContext.getUriInfo().getPath().split("/")[1];
        return switch (microservice) {
            case "repo-post" -> "9001";
            case "repo-social" -> "9002";
            case "srvc-home-timeline" -> "9003";
            case "srvc-search" -> "9004";
            case "srvc-user-timeline" -> "9005";
            default -> "9000";
        };
    }
}