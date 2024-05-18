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

package de.kaiserpfalzedv.office.library.jpa.locations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import de.kaiserpfalzedv.office.library.domain.locations.Location;
import de.kaiserpfalzedv.office.library.jpa.AboutJPA;
import de.kaiserpfalzedv.office.library.jpa.BaseNamedResourceJPA;
import de.kaiserpfalzedv.office.library.jpa.inventory.AssetJPA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * <p>Location -- the JPA implementation of {@link Location}.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Schema(
        title = "Location",
        description = "The location is a tree structure from campus via building, room to the shelf. Alternatively it could be a virtual location."
)
@Jacksonized
@Entity
@Table(
        name = "LOCATIONS",
        schema = AboutJPA.DB_SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = "LOCATIONS_NAME_UK", columnNames = {"NAMESPACE","NAME"})
        }
)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class LocationJPA extends BaseNamedResourceJPA {
    @Getter
    @Column(name = "LOCATION_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Location.Type locationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "PART_OF",
            referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "LOCATIONS_PARTOF_FK")
    )
    private LocationJPA partOf;

    @OneToMany(
            mappedBy = "partOf",
            orphanRemoval = true
    )
    @Builder.Default
    private Set<LocationJPA> subLocations = new HashSet<>();

    @OneToMany(
            mappedBy = "location",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<AssetJPA> assets;

    @Transient
    @JsonIgnore
    public Optional<LocationJPA> getPartOf() {
        return Optional.ofNullable(partOf);
    }

    @Transient
    @JsonIgnore
    public Set<LocationJPA> getSubLocations() {
        return Collections.unmodifiableSet(subLocations);
    }
}
