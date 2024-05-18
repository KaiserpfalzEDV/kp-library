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

package de.kaiserpfalzedv.office.library.domain;

import de.kaiserpfalzedv.commons.api.resources.HasName;

/**
 * HasDisplayName -- Human readable names.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface HasDisplayName extends HasName {
    final int SHORT_NAME_SIZE = 20;
    final String SHORT_NAME_CUTTING_SYMBOLS = "...";

    /**
     * Returns a user displayable name. Defaults to {@link HasName#getName()}.
     *
     * @return a name that can be displayed to a human user.
     */
    default String getDisplayName() {
        return getName();
    }

    String name();

    default String getName() {
        return name();
    }


    /**
     * Returns a user displayable name of maximum 20 characters length. Defaults to {@link HasName#getName()} .
     *
     * @return a name of maximum {@value SHORT_NAME_SIZE} characters of length.
     */
    default String getShortName() {
        if (getName().length() > SHORT_NAME_SIZE) {
            return getName().substring(0, SHORT_NAME_SIZE - SHORT_NAME_CUTTING_SYMBOLS.length()) + SHORT_NAME_CUTTING_SYMBOLS;
        } else {
            return getName();
        }
    }
}
