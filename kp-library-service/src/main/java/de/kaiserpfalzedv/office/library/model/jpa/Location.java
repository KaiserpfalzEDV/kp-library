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

package de.kaiserpfalzedv.office.library.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Location -- the JPA implementation of {@link de.kaiserpfalzedv.office.library.model.Location}.</p>
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
        schema = About.DB_SCHEMA,
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
public class Location extends BaseNamedResource implements de.kaiserpfalzedv.office.library.model.Location {
    @Getter
    @Column(name = "LOCATION_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Type locationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "PART_OF",
            referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "LOCATIONS_PARTOF_FK")
    )
    private Location partOf;

    @OneToMany(
            mappedBy = "partOf",
            orphanRemoval = true
    )
    @Builder.Default
    private Set<Location> subLocations = new HashSet<>();

    @OneToMany(
            mappedBy = "location",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Asset> assets;

    @Transient
    @JsonIgnore
    @Override
    public Optional<de.kaiserpfalzedv.office.library.model.Location> getPartOf() {
        return Optional.ofNullable(partOf);
    }

    @Transient
    @JsonIgnore
    @Override
    public Set<de.kaiserpfalzedv.office.library.model.Location> getSubLocations() {
        return subLocations.stream()
                .map(d -> (de.kaiserpfalzedv.office.library.model.Location) d)
                .collect(Collectors.toUnmodifiableSet());
    }
}
