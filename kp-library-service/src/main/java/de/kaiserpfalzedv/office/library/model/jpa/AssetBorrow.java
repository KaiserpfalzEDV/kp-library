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
import de.kaiserpfalzedv.commons.api.resources.HasId;
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
 * <p>AssetBorrow -- </p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
@Schema(
        title = "AssetBorrow",
        description = "A current borrow of an asset."
)
@Jacksonized
@Entity
@Table(
        name = "BORROWS",
        schema = About.DB_SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = "BORROWS_UK", columnNames = {"ASSET_ID","USER_ID"})
        }
)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class AssetBorrow implements de.kaiserpfalzedv.office.library.model.AssetBorrow {
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

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "USER_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "BORROWS_USERS_FK")
    )
    private User user;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(
            name = "ASSET_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "BORROWS_ASSETS_FK")
    )
    private Asset asset;

    @Schema(
            title = "Borrow Time",
            description = "The timestamp of the borrow.",
            pattern = "^(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))(T[0-9][0-9]:[0-9][0-9](:[0-9][0-9])?(\\.[0-9]+)?(([+-][0-9][0-9]:[0-9][0-9])|Z)?)?)?",
            example = "2023-01-16T01:23:45.789Z"
    )
    @Column(name = "BORROW_TIME", nullable = false, updatable = false)
    @NotNull
    private OffsetDateTime borrowTime;

    @Schema(
            title = "Latest Return",
            description = "The latest return timestamp.",
            pattern = "^(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))(T[0-9][0-9]:[0-9][0-9](:[0-9][0-9])?(\\.[0-9]+)?(([+-][0-9][0-9]:[0-9][0-9])|Z)?)?)?",
            example = "2023-01-16T01:23:45.789Z"
    )
    @Column(name = "LATEST_RETURN", nullable = false)
    @NotNull
    private OffsetDateTime latestReturnTime;
}
