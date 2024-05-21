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

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.kaiserpfalzedv.office.library.domain.locations.Location;

/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-05-18
 */
@Repository
public interface LocationRepository extends JpaRepository<LocationJPA, Long>, de.kaiserpfalzedv.office.library.domain.locations.LocationRepository {

    default Location save(final Location location) {
        LocationJPA jpa = new LocationModelConverter().convertFromModel(location);

        jpa = insertIdsFromExistingJPA(location, jpa);
        jpa = saveAndFlush(jpa);

        return new LocationModelConverter().convertFromJPA(jpa);
    }

    default LocationJPA insertIdsFromExistingJPA(final Location location, LocationJPA jpa) {
        LocationJPA orig = findByNameSpaceAndName(location.nameSpace(), location.name());

        if (orig != null) {
            LocationJPA partOf = null;
            if (jpa.getPartOf().isPresent()) {
                partOf = jpa.getPartOf().get();
            }

            Set<LocationJPA> subLocations = location.subLocations().stream().map(l -> insertIdsFromExistingJPA(l, new LocationModelConverter().convertFromModel(l))).collect(Collectors.toSet());
            
            jpa.getSubLocations().clear();
            jpa.getSubLocations().addAll(subLocations);
        }

        return jpa;
    }

    LocationJPA findByNameSpaceAndName(final String nameSpace, final String name);
}