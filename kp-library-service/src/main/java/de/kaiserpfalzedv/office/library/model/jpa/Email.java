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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * <p>Email -- The JPA implementation for {@link de.kaiserpfalzedv.office.library.api.Email}</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Schema(
        title = "Email",
        description = "The email address."
)
@Jacksonized
@Embeddable
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Email implements de.kaiserpfalzedv.office.library.api.Email {
    @Schema(
            title = "LocalPart",
            description = "The local part of the email.",
            minLength = 1,
            maxLength = 64
    )
    @Size(min = 1, max = 64)
    @NotEmpty
    @Column(name = "EMAIL_LOCAL_PART", length = 65, nullable = false)
    private String localPart;

    @Schema(
            title = "Domain",
            description = "The domain part of the email.",
            minLength = 2,
            maxLength = 1000
    )
    @Size(min = 2, max = 1000)
    @NotEmpty
    @Column(name = "EMAIL_DOMAIN", length = 1000, nullable = false)
    private String domain;

    /**
     * @return the string representation of this email address.
     */
    @JsonIgnore
    @Transient
    @Override
    public String getMailAddress() {
        return localPart + "@" + domain;
    }
}
