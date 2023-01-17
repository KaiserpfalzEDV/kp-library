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
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>User -- The JPA implementation for {@link de.kaiserpfalzedv.office.library.model.User}.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Schema(
        title = "User",
        description = "A user of the library management system. Librarians don't need to be users."
)
@Entity
@Table(
        name = "USERS",
        schema = "IAM",
        uniqueConstraints = {
                @UniqueConstraint(name = "USER_NAME_UK", columnNames = {"NAMESPACE","NAME"}),
                @UniqueConstraint(name = "USER_EMAIL_UK", columnNames = {"EMAIL_LOCAL_PART", "EMAIL_DOMAIN"})
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
public class User extends BaseNamedResource implements de.kaiserpfalzedv.office.library.model.User {
    @Embedded
    @NotNull
    private Email email;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
    @Builder.Default
    private Set<AssetBorrow> currentBorrows = new HashSet<>();
}
