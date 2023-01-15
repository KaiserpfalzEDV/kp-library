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

package de.kaiserpfalzedv.office.library.model.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * <p>AssetBorrow -- </p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class AssetBorrow implements de.kaiserpfalzedv.office.library.model.AssetBorrow {
    @ToString.Include
    @EqualsAndHashCode.Include
    /** The id of this resource. */
    private UUID id;

    @ToString.Include
    @EqualsAndHashCode.Include
    /** The version of this data set. */
    private Long version;

    private User user;

    private Asset asset;

    private OffsetDateTime borrowTime;

    private OffsetDateTime latestReturnTime;
}
