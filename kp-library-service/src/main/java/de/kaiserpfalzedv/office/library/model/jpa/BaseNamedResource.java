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

import de.kaiserpfalzedv.commons.core.resources.HasId;
import de.kaiserpfalzedv.commons.core.resources.HasName;
import de.kaiserpfalzedv.commons.core.resources.HasNameSpace;
import de.kaiserpfalzedv.office.library.api.HasDisplayName;
import de.kaiserpfalzedv.office.library.api.HasRecord;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>BaseNamedResource -- A base resource with namespace and name information for the kp-library client.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class BaseNamedResource extends BaseResource implements HasId, HasRecord, HasNameSpace, HasDisplayName {
    @Schema(
            title = "NameSpace",
            description = "The namespace of the resource",
            required = true,
            defaultValue = "DEFAULT",
            pattern = HasName.VALID_NAME_PATTERN,
            example = HasName.VALID_NAME_EXAMPLE,
            minLength = HasName.VALID_NAME_MIN_LENGTH,
            maxLength = HasName.VALID_NAME_MAX_LENGTH
    )
    @Size(
            min = HasName.VALID_NAME_MIN_LENGTH,
            max = HasName.VALID_NAME_MAX_LENGTH,
            message = HasName.VALID_NAME_LENGTH_MSG
    )
    @Pattern(regexp = HasName.VALID_NAME_PATTERN, message = HasName.VALID_NAME_PATTERN_MSG)
    @NotEmpty
    @Column(name = "NAMESPACE", length = 100, nullable = false)
    @ToString.Include
    @Builder.Default
    /** The namespace of this resource. */
    private String nameSpace = "DEFAULT";

    @Schema(
            title = "Name",
            description = "The name of the resource. It needs to be unique within the namespace.",
            required = true,
            pattern = HasName.VALID_NAME_PATTERN,
            example = HasName.VALID_NAME_EXAMPLE,
            minLength = HasName.VALID_NAME_MIN_LENGTH,
            maxLength = HasName.VALID_NAME_MAX_LENGTH
    )
    @Size(
            min = HasName.VALID_NAME_MIN_LENGTH,
            max = HasName.VALID_NAME_MAX_LENGTH,
            message = HasName.VALID_NAME_LENGTH_MSG
    )
    @Pattern(regexp = HasName.VALID_NAME_PATTERN, message = HasName.VALID_NAME_PATTERN_MSG)
    @NotEmpty
    @Column(name = "NAME", length = 100, nullable = false)
    @ToString.Include
    /** The name of this resource. */
    private String name;
}
