/*
 * Copyright (c) 2023. Roland T. Lichti, Kaiserpfalz EDV-Service.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.kaiserpfalzedv.office.library.client;

import de.kaiserpfalzedv.office.library.model.client.Location;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.quarkus.oidc.token.propagation.reactive.AccessTokenRequestReactiveFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

/**
 * <p>LocationClient -- Client for accessing the locations of a library.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@RegisterRestClient(configKey = "location-api")
@RegisterProvider(value = AccessTokenRequestReactiveFilter.class, priority = 5000)
@Path("/api/locations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface LocationClient {
    String CACHE_NAME = "library-locations";
    long CACHE_LOCK_TIMEOUT = 10L;

    @POST
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    Location create(final Location data);

    @GET
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    List<Location> index(
        @QueryParam("start") long start,
        @QueryParam("size") long size
    );

    @GET
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    List<Location> index();

    @GET
    @Path("/{id}")
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    Location retrieve(@PathParam("id") final UUID id);

    @GET
    @Path("/{namespace}/{name}")
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    Location retrieve(
            @PathParam("namespace") final String nameSpace,
            @PathParam("name") final String name
    );

    @PUT
    @Path("/{id}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void update(@PathParam("id") final UUID id, final Location asset);

    @PUT
    @Path("/{id}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void delete(@PathParam("id") final UUID id);
}
