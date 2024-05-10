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

package de.kaiserpfalzedv.office.library.model;

import java.util.Optional;

import de.kaiserpfalzedv.commons.api.resources.HasId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * <p>Asset -- A single asset of the library.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface Asset extends HasId, HasAcquirementDate, HasLocation, HasDisplayName {
    @NotNull
    Medium getMedium();

    @Min(0)
    int getCounter();

    Optional<Long> getCurrentBorrow();

    default String getName() {
        return getMedium().getName();
    }

    /**
     * @return The display name consisting of the medium name and the counter.
     */
    @Override
    default String getDisplayName() {
        return getName() + createCounterPostfix();
    }

    @Override
    default String getShortName() {
        if (getDisplayName().length() <= SHORT_NAME_SIZE) {
            return getDisplayName();
        } else {
            return getName().substring(0, calculateShortNameAbbreviatedLength()) + SHORT_NAME_CUTTING_SYMBOLS + createCounterPostfix();
        }
    }

    private int calculateShortNameAbbreviatedLength() {
        return SHORT_NAME_SIZE - SHORT_NAME_CUTTING_SYMBOLS.length() - createCounterPostfix().length();
    }

    @NotNull
    private String createCounterPostfix() {
        return getCounter() == 0 ? "" : " (" + getCounter() + ")";
    }
}
