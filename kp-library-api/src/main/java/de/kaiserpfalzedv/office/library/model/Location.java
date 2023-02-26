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

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import de.kaiserpfalzedv.commons.api.resources.HasId;
import de.kaiserpfalzedv.commons.api.resources.HasNameSpace;
import de.kaiserpfalzedv.office.library.api.HasDisplayName;
import de.kaiserpfalzedv.office.library.api.HasRecord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * <p>Location -- A location for a resource.</p>
 *
 * <p>The location is a recursive data type. It has a pointer to the enclosing location and the directly enclosed
 * locations. The whole interface defaults to a virtual location with neither enclosed nor enclosing locations.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface Location extends HasId, HasNameSpace, HasDisplayName, HasRecord {
    /**
     * @return The type of the location. Defaults to {@link  Type#VIRTUAL}.
     */
    default Type getLocationType() {
        return Type.VIRTUAL;
    }

    /**
     * A location is normally part of another location. So a shelf is sitting in a room which is part of a building
     * which may be part of a campus.
     *
     * @return the enclosing location of this location. Defaults to {@link Optional#empty()}.
     */
    default Optional<Location> getPartOf() {
        return Optional.empty();
    }

    /**
     * A location can be the enclosure for other locations. This will create a tree.
     *
     * @return the directly enclosed locations. Defaults to {@link Collections#emptySet()}.
     */
    default Set<Location> getSubLocations() {
        return Collections.emptySet();
    }


    @Schema(
            title = "Library Location Type",
            description = "Media can be stored in different locations. This is the type of the location.",
            example = "SHELF",
            defaultValue = "SHELF",
            enumeration = {"SHELF", "ROOM", "BUILDING", "CAMPUS", "VIRTUAL"},
            required = true
    )
    @RequiredArgsConstructor
    @Getter
    enum Type {
        @JsonEnumDefaultValue
        SHELF("library.location.type.shelf"),
        ROOM("library.location.type.room"),
        BUILDING("library.location.type.building"),
        CAMPUS("library.location.type.campus"),
        VIRTUAL("library.location.type.virtual");

        @NotNull
        final String i18nKey;
    }
}
