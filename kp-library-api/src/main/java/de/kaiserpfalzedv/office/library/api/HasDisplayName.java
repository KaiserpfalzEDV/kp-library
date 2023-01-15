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

package de.kaiserpfalzedv.office.library.api;

import de.kaiserpfalzedv.commons.core.resources.HasName;

/**
 * HasDisplayName -- Human readable names.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface HasDisplayName extends HasName {
    /**
     * Returns a user displayable name. Defaults to {@link HasName#getName()}.
     *
     * @return a name that can be displayed to a human user.
     */
    default String getDisplayName() {
        return getName();
    }


    /**
     * Returns a user displayable name of maximum 20 characters length. Defaults to {@link HasName#getName()} .
     *
     * @return a name of maximum 20 characters of length.
     */
    default String getShortName() {
        if (getName().length() > 20) {
            return getName().substring(0, 17) + "...";
        } else {
            return getName();
        }
    }
}
