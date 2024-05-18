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

package de.kaiserpfalzedv.office.library.jpa.users;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.kaiserpfalzedv.office.library.jpa.BaseNamedResourceJPA;
import de.kaiserpfalzedv.office.library.jpa.borrowing.AssetBorrowJPA;
import de.kaiserpfalzedv.office.library.jpa.borrowing.BorrowHistoryEntryJPA;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * <p>User -- The JPA implementation for {@link User}.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Entity
@Table(
        name = "USERS",
        schema = "IAM",
        uniqueConstraints = {
                @UniqueConstraint(name = "USER_NAME_UK", columnNames = {"NAMESPACE","NAME"}),
                @UniqueConstraint(name = "USER_IDP_NAME_UK", columnNames = {"IDP_NAME"})
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
public class UserJPA extends BaseNamedResourceJPA {

    @Column(name="IDP_NAME", length = 200, nullable = false, unique = true)
    private String idpName;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
    @Builder.Default
    private Set<AssetBorrowJPA> currentBorrows = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    @Builder.Default
    private Set<BorrowHistoryEntryJPA> borrowHistory = new HashSet<>();
}
