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

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;

/**
 * <p>Medium -- JPA implementation for {@link de.kaiserpfalzedv.office.library.model.Medium}.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Entity
@Table(
        name = "MEDIUMS",
        schema = About.DB_SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = "MEDIUMS_NAME_UK", columnNames = {"NAMESPACE","NAME"}),
                @UniqueConstraint(name = "MEDIUMS_EAN_UK", columnNames = {"EAN"})
        }
)
@DiscriminatorColumn
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class Medium extends BaseNamedResource implements de.kaiserpfalzedv.office.library.model.Medium {
    @Schema(
            title = "EAN -- International Article Number",
            description = "The EAN13 number of the medium.",
            example = "978-5-12345-678-9",
            minLength = 13,
            maxLength = 16,
            required = true
    )
    @Column(name = "EAN", length = 16, nullable = false, unique = true)
    @ToString.Include
    private String ean;
}
