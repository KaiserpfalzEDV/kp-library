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
import de.kaiserpfalzedv.office.library.model.client.Asset;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.quarkus.oidc.token.propagation.reactive.AccessTokenRequestReactiveFilter;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

/**
 * <p>AssetClient -- Client for accessing the assets of a library.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@RegisterRestClient
@RegisterProvider(value = AccessTokenRequestReactiveFilter.class, priority = 5000)
@RegisterProvider(value = ResponseErrorMapper.class, priority = 10)
@Path("/api/assets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AssetClient {
    String CACHE_NAME = "library-assets";
    long CACHE_LOCK_TIMEOUT = 10L;

    @Timed("library.asset.create.time")
    @Counted("library.asset.create.count")
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
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    Asset create(final Asset data);

    @Timed("library.asset.list-page.time")
    @Counted("library.asset.list-page.count")
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
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    List<Asset> index(
        @QueryParam("start") long start,
        @QueryParam("size") long size
    );

    @Timed("library.asset.list.time")
    @Counted("library.asset.list.count")
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
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    List<Asset> index();

    @Timed("library.asset.retrieve-by-id.time")
    @Counted("library.asset.retrieve-by-id.count")
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
    @Path("/{id}")
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    Asset retrieve(@PathParam("id") final UUID id);

    @Timed("library.asset.retrieve-by-name.time")
    @Counted("library.asset.retrieve-by-name.count")
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
    @Path("/{namespace}/{name}")
    @CacheResult(cacheName = CACHE_NAME, lockTimeout = CACHE_LOCK_TIMEOUT)
    Asset retrieve(
            @PathParam("namespace") final String nameSpace,
            @PathParam("name") final String name
    );

    @Timed("library.asset.update.time")
    @Counted("library.asset.update.count")
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
    @Path("/{id}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void update(@PathParam("id") final UUID id, final Asset asset);

    @Timed("library.asset.delete.time")
    @Counted("library.asset.delete.count")
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
    @Path("/{id}")
    @CacheInvalidate(cacheName = CACHE_NAME)
    void delete(@PathParam("id") final UUID id);
}
