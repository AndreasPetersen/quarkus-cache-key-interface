package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.cache.CompositeCacheKey;
import javax.inject.Inject;
import io.quarkus.cache.CacheName;
import io.quarkus.cache.Cache;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/hello")
public class GreetingResource {
    @RestClient
    MyRestClient myRestClient;

    @Inject
    @CacheName("my-cache")
    Cache myCache;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String a = "a";
        String b = "b";
        String c = "c";
        CompositeCacheKey compositeCacheKey = new CompositeCacheKey(a, b);

        myRestClient.hello(a, b, c);

        return myCache.get(compositeCacheKey, key -> myRestClient.hello(c)).await().indefinitely();
    }
}