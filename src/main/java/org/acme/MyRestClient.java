package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheName;
import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;



@RegisterRestClient
public interface MyRestClient {
    @GET
    @Path("/hello")
    String hello(
        @QueryParam("c") String c);

    @CacheResult(cacheName = "my-cache")
    default String hello(
        @CacheKey @QueryParam("a") String a, 
        @CacheKey @QueryParam("b") String b,
        @QueryParam("c") String c) {
        return hello(c);
    }
}