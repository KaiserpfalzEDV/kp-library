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
import de.kaiserpfalzedv.commons.core.resources.HasId;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * <p>BorrowHistoryEntry -- The JPA implementation of
 * {@link de.kaiserpfalzedv.office.library.model.BorrowHistoryEntry}.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Schema(
        title = "History Entry",
        description = "The asset borrow history. Current borrows are not listed here."
)
@Jacksonized
@Entity
@Table(name = "BORROWHISTORY", schema = About.DB_SCHEMA)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class BorrowHistoryEntry implements de.kaiserpfalzedv.office.library.model.BorrowHistoryEntry {
    @Schema(
            title = "ID",
            description = "The technical ID of this resource",
            pattern = HasId.VALID_UUID_PATTERN,
            example = HasId.VALID_UUID_EXAMPLE,
            minLength = HasId.VALID_UUID_LENGTH,
            maxLength = HasId.VALID_UUID_LENGTH,
            required = true
    )
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(
            name = "ID",
            length = 36,
            nullable = false,
            updatable = false,
            unique = true
    )
    @ToString.Include
    @EqualsAndHashCode.Include
    protected UUID id;

    @Schema(
            title = "Version",
            description = "Version of this resource (for optimistic locking)",
            minimum = "0",
            example = "3442",
            required = true
    )
    @Min(0)
    @NotNull
    @Version
    @Column(
            name = "VERSION",
            nullable = false
    )
    @ToString.Include
    @EqualsAndHashCode.Include
    protected Integer version;

    @Schema(
            title = "User ID",
            description = "The technical ID of the user",
            pattern = HasId.VALID_UUID_PATTERN,
            example = HasId.VALID_UUID_EXAMPLE,
            minLength = HasId.VALID_UUID_LENGTH,
            maxLength = HasId.VALID_UUID_LENGTH,
            required = true
    )
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(
            name = "USER_ID",
            length = 36,
            nullable = false,
            updatable = false,
            unique = true
    )
    @ToString.Include
    @NotNull
    private UUID user;

    @Schema(
            title = "Asset ID",
            description = "The technical ID of the asset",
            pattern = HasId.VALID_UUID_PATTERN,
            example = HasId.VALID_UUID_EXAMPLE,
            minLength = HasId.VALID_UUID_LENGTH,
            maxLength = HasId.VALID_UUID_LENGTH,
            required = true
    )
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(
            name = "ASSET_ID",
            length = 36,
            nullable = false,
            updatable = false,
            unique = true
    )
    @ToString.Include
    @NotNull
    private UUID asset;

    @Schema(
            title = "Borrow Time",
            description = "The timestamp of the borrow of the asset.",
            pattern = "^(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))(T[0-9][0-9]:[0-9][0-9](:[0-9][0-9])?(\\.[0-9]+)?(([+-][0-9][0-9]:[0-9][0-9])|Z)?)?)?",
            example = "2023-01-16T01:23:45.789Z"
    )
    @Column(name = "BORROW_TIME", nullable = false, updatable = false)
    @NotNull
    private OffsetDateTime borrowDate;
    @Schema(
            title = "Return Time",
            description = "The timestamp of the return of the asset.",
            pattern = "^(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))(T[0-9][0-9]:[0-9][0-9](:[0-9][0-9])?(\\.[0-9]+)?(([+-][0-9][0-9]:[0-9][0-9])|Z)?)?)?",
            example = "2023-01-16T01:23:45.789Z"
    )
    @Column(name = "RETURN_TIME", nullable = false, updatable = false)
    @NotNull
    private OffsetDateTime returnDate;
}
