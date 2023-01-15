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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.kaiserpfalzedv.office.library.model.AssetBorrow;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * <p>Asset -- The client implementation of {@link de.kaiserpfalzedv.office.library.model.Asset}.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Asset extends BaseResource implements de.kaiserpfalzedv.office.library.model.Asset {
    private Medium medium;
    private int counter;

    private Location location;
    private ZonedDateTime acquirementDate;

    @Nullable
    private AssetBorrow currentBorrow;

    @JsonIgnore
    @Override
    public Optional<AssetBorrow> getCurrentBorrow() {
        return Optional.ofNullable(currentBorrow);
    }
}
