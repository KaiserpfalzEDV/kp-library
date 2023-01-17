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

import com.fasterxml.jackson.annotation.JsonInclude;
import de.kaiserpfalzedv.commons.core.resources.HasName;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>Book -- The JPA implementation for {@link de.kaiserpfalzedv.office.library.model.Book}.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Schema(
        title = "Book",
        description = "A traditional book."
)
@Jacksonized
@Entity
@DiscriminatorValue("BOOK")
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Book extends Medium implements de.kaiserpfalzedv.office.library.model.Book {
    @Schema(
            title = "Kind",
            description = "The type of the medium.",
            enumeration = {Book.KIND},
            example = Book.KIND,
            required = true
    )
    @Override
    @Size(min = 3, max = 100, message = "The length of the string must be between 3 and 100 characters long.")
    @Pattern(regexp = HasName.VALID_NAME_PATTERN, message = HasName.VALID_NAME_PATTERN_MSG)
    @Transient
    public String getKind() {
        return de.kaiserpfalzedv.office.library.model.Book.KIND;
    }
}
