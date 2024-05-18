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

package de.kaiserpfalzedv.office.library.jpa.inventory;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import de.kaiserpfalzedv.office.library.domain.inventory.Asset;
import de.kaiserpfalzedv.office.library.jpa.AboutJPA;
import de.kaiserpfalzedv.office.library.jpa.BaseNamedResourceJPA;
import de.kaiserpfalzedv.office.library.jpa.borrowing.AssetBorrowJPA;
import de.kaiserpfalzedv.office.library.jpa.locations.LocationJPA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * <p>Asset -- The JPA implementation of {@link Asset}.</p>
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
        schema = AboutJPA.DB_SCHEMA,
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
public class AssetJPA extends BaseNamedResourceJPA {
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
    private MediumJPA medium;
    @Column(name = "COUNTER", nullable = false)
    private int counter;


    @ManyToOne(optional = false)
    @JoinColumn(
            name = "LOCATION_ID", nullable = false,
            referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "ASSETS_LOCATIONS_FK")
    )
    private LocationJPA location;

    @Column(name = "ACQUISITION_DATE", nullable = false)
    private ZonedDateTime acquisitionDate;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "asset")
    private AssetBorrowJPA currentBorrow;


    @JsonIgnore
    @Transient
    public Optional<Long> getCurrentBorrow() {
        if (currentBorrow == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(currentBorrow.getId());
    }
}
