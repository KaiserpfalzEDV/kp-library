/*
 * Copyright (c) 2024 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package de.kaiserpfalzedv.office.library.jpa.locations;



import java.util.stream.Collectors;

import de.kaiserpfalzedv.office.library.domain.locations.Location;
import de.kaiserpfalzedv.office.library.jpa.Converter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-05-18
 */
@ToString(includeFieldNames = true)
@Slf4j
public class LocationModelConverter extends Converter<Location, LocationJPA> {
    public LocationModelConverter() {
        super(LocationModelConverter::convertToJPA, LocationModelConverter::convertToModel);
    }

    private static LocationJPA convertToJPA(final Location model) {
        log.trace("Location converting to JPA port. Location={}", model);

        if (model == null) {
            return null;
        }

        return LocationJPA.builder()
            .nameSpace(model.nameSpace()).name(model.name())
            .locationType(model.locationType())

            .partOf(model.partOf().isPresent() ? convertToJPA(model.partOf().get()) : null)
            .subLocations(model.getSubLocations().stream().map(LocationModelConverter::convertToJPA).collect((Collectors.toSet())))

            .created(model.created()).modified(model.modified())
            
            .build();
    }

    private static Location convertToModel(final LocationJPA jpa) {
        log.trace("Location converting from JPA port. Location={}", jpa);

        if (jpa == null) {
            return null;
        }

        return new Location.LocationImpl(
            jpa.getNameSpace(), jpa.getName(),
            jpa.getLocationType(),

            jpa.getPartOf().isPresent() ? convertToModel(jpa.getPartOf().get()) : null,
            jpa.getSubLocations().stream().map(LocationModelConverter::convertToModel).collect(Collectors.toSet()),

            jpa.getCreated(), jpa.getModified()
        );
    }
    
}
