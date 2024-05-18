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

package de.kaiserpfalzedv.office.library.domain.inventory;

import java.time.ZonedDateTime;
import java.util.Optional;

import de.kaiserpfalzedv.office.library.domain.HasDisplayName;
import de.kaiserpfalzedv.office.library.domain.HasSignature;
import de.kaiserpfalzedv.office.library.domain.borrowing.AssetBorrow;
import de.kaiserpfalzedv.office.library.domain.locations.HasLocation;
import de.kaiserpfalzedv.office.library.domain.locations.Location;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * <p>Asset -- A single asset of the library.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface Asset extends HasSignature, HasAcquisitionDate, HasLocation, HasDisplayName {

    record AssetImpl(Medium medium, int counter, Location location, ZonedDateTime acquisitionDate, AssetBorrow borrow) implements Asset {
        @Override
        public Optional<AssetBorrow> currentBorrow() {
            return Optional.ofNullable(borrow);
        }}

    @NotNull
    Medium medium();

    @Min(0)
    int counter();

    Optional<AssetBorrow> currentBorrow();

    default String signature() {
        return medium().signature() + counterPostfix();
    }

    default String name() {
        return medium().name();
    }

    default String getName() {
        return medium().getName();
    }

    /**
     * @return The display name consisting of the medium name and the counter.
     */
    @Override
    default String getDisplayName() {
        return getName() + counterPostfix();
    }

    @Override
    default String getShortName() {
        if (getDisplayName().length() <= SHORT_NAME_SIZE) {
            return getDisplayName();
        } else {
            return getName().substring(0, calculateShortNameAbbreviatedLength()) + SHORT_NAME_CUTTING_SYMBOLS + counterPostfix();
        }
    }

    private int calculateShortNameAbbreviatedLength() {
        return SHORT_NAME_SIZE - SHORT_NAME_CUTTING_SYMBOLS.length() - counterPostfix().length();
    }

    @NotNull
    private String counterPostfix() {
        return counter() == 0 ? "" : " (" + counter() + ")";
    }
}
