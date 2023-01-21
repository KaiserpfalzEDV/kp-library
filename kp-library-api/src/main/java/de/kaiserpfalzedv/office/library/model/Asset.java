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

import de.kaiserpfalzedv.commons.api.resources.HasId;
import de.kaiserpfalzedv.commons.api.resources.HasName;
import de.kaiserpfalzedv.office.library.api.HasAcquirementDate;
import de.kaiserpfalzedv.office.library.api.HasDisplayName;
import de.kaiserpfalzedv.office.library.api.HasLocation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>Asset -- A single asset of the library.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface Asset extends HasId, HasAcquirementDate, HasLocation, HasName, HasDisplayName {
    @NotNull
    Medium getMedium();

    @Min(0)
    int getCounter();

    Optional<UUID> getCurrentBorrow();

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
        if (getDisplayName().length() <= 20) {
            return getDisplayName();
        } else {
            return getName().substring(0, 17 - createCounterPostfix().length()) + "..." + createCounterPostfix();
        }
    }

    @NotNull
    private String createCounterPostfix() {
        return getCounter() == 0 ? "" : " (" + getCounter() + ")";
    }
}
