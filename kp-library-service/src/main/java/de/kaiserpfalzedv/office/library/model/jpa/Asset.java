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
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>Asset -- The JPA implementation of {@link de.kaiserpfalzedv.office.library.model.Asset}.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Schema(
        title = "Asset",
        description = "The single asset that can be borrowed."
)
@Entity
@Table(
        name = "ASSETS",
        schema = About.DB_SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = "ASSETS_MEDIUM_UK", columnNames = {"MEDIUM_ID", "COUNTER"})
        }
)
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Asset extends BaseResource implements de.kaiserpfalzedv.office.library.model.Asset {
    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(
            name = "MEDIUM_ID", nullable = false,
            referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "ASSETS_MEDIUMS_FK")
    )
    private Medium medium;
    @Column(name = "COUNTER", nullable = false)
    private int counter;


    @ManyToOne(optional = false)
    @JoinColumn(
            name = "LOCATION_ID", nullable = false,
            referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "ASSETS_LOCATIONS_FK")
    )
    private Location location;

    @Column(name = "ACQUIREMENT_DATE", nullable = false)
    private ZonedDateTime acquirementDate;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "asset")
    private AssetBorrow currentBorrow;


    @JsonIgnore
    @Transient
    @Override
    public Optional<UUID> getCurrentBorrow() {
        if (currentBorrow == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(currentBorrow.getId());
    }
}
