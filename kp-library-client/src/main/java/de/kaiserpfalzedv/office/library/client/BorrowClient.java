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

import de.kaiserpfalzedv.office.library.model.client.AssetBorrow;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.quarkus.oidc.token.propagation.reactive.AccessTokenRequestReactiveFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;

/**
 * <p>BorrowClient -- The client to borrow media.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@RegisterRestClient(configKey = "borrow-api")
@RegisterProvider(value = AccessTokenRequestReactiveFilter.class, priority = 5000)
@Path("/api/borrows")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"librarian","admin"})
public interface BorrowClient {
    String CACHE_NAME = "library-borrows";
    long CACHE_LOCK_TIMEOUT = 10L;

    @POST
    @Path("/{asset}/{user}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void borrowAsset(
            @PathParam("asset") final UUID asset,
            @PathParam("user") final UUID user,
            @QueryParam("duration") @DefaultValue("P14d") final String duration
    );

    @GET
    @Path("/asset/{asset}")
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    AssetBorrow retrieveByAsset(@PathParam("asset") final UUID asset);

    @GET
    @Path("/user/{user}")
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    Set<AssetBorrow> retrieveByUser(@PathParam("user") final UUID user);

    @PUT
    @Path("/{borrow}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void prolongueAsset(
            @PathParam("borrow") final UUID borrow,
            final Duration duration
    );

    @DELETE
    @Path("/{borrow}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void returnAsset(@PathParam("borrow") final UUID borrow);
}
