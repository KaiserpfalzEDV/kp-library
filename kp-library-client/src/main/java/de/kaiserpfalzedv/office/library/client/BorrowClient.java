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

import de.kaiserpfalzedv.office.library.mapper.LibraryClientException;
import de.kaiserpfalzedv.office.library.mapper.ResponseErrorMapper;
import de.kaiserpfalzedv.office.library.model.client.AssetBorrow;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.quarkus.oidc.token.propagation.reactive.AccessTokenRequestReactiveFilter;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
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
@RegisterRestClient
@RegisterProvider(value = AccessTokenRequestReactiveFilter.class, priority = 5000)
@RegisterProvider(value = ResponseErrorMapper.class, priority = 10)
@Path("/api/borrows")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"librarian","admin"})
public interface BorrowClient {
    String CACHE_NAME = "library-borrows";
    long CACHE_LOCK_TIMEOUT = 10L;

    String DEFAULT_DURATION = "P14d";

    /**
     * <p>Borrows the given asset to the user. The duration defaults to {@value #DEFAULT_DURATION} in format defined by
     * <a href="https://en.wikipedia.org/wiki/ISO_8601#Durations">ISO-8601</a>.</p>
     *
     * @param asset The id of the asset to borrow.
     * @param user The borrowing user.
     * @param duration The duration until the asset needs to be returned.
     */
    @Timed("library.borrow.borrow.time")
    @Counted("library.borrow.borrow.count")
    @Retry(
            delay = 2000,
            maxDuration = 6000,
            retryOn = {LibraryClientException.class}
    )
    @CircuitBreaker(
            failOn = LibraryClientException.class,
            requestVolumeThreshold = 5
    )
    @POST
    @Path("/{asset}/{user}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void borrowAsset(
            @PathParam("asset") final UUID asset,
            @PathParam("user") final UUID user,
            @QueryParam("duration") @DefaultValue(DEFAULT_DURATION) final String duration
    );

    @Timed("library.borrow.retrieve-by-asset.time")
    @Counted("library.borrow.retrieve-by-asset.count")
    @Retry(
            delay = 2000,
            maxDuration = 6000,
            retryOn = {LibraryClientException.class}
    )
    @CircuitBreaker(
            failOn = LibraryClientException.class,
            requestVolumeThreshold = 5
    )
    @GET
    @Path("/asset/{asset}")
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    AssetBorrow retrieveByAsset(@PathParam("asset") final UUID asset);

    @Timed("library.borrow.retrieve-by-user.time")
    @Counted("library.borrow.retrieve-by-user.count")
    @Retry(
            delay = 2000,
            maxDuration = 6000,
            retryOn = {LibraryClientException.class}
    )
    @CircuitBreaker(
            failOn = LibraryClientException.class,
            requestVolumeThreshold = 5
    )
    @GET
    @Path("/user/{user}")
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    Set<AssetBorrow> retrieveByUser(@PathParam("user") final UUID user);

    @Timed("library.borrow.extension.time")
    @Counted("library.borrow.extension.count")
    @Retry(
            delay = 2000,
            maxDuration = 6000,
            retryOn = {LibraryClientException.class}
    )
    @CircuitBreaker(
            failOn = LibraryClientException.class,
            requestVolumeThreshold = 5
    )
    @PUT
    @Path("/{borrow}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void extendLoan(
            @PathParam("borrow") final UUID borrow,
            final Duration duration
    );

    @Timed("library.borrow.return.time")
    @Counted("library.borrow.return.count")
    @Retry(
            delay = 2000,
            maxDuration = 6000,
            retryOn = {LibraryClientException.class}
    )
    @CircuitBreaker(
            failOn = LibraryClientException.class,
            requestVolumeThreshold = 5
    )
    @DELETE
    @Path("/{borrow}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void returnAsset(@PathParam("borrow") final UUID borrow);
}
