/*
 * Copyright (c) 2024 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package de.kaiserpfalzedv.office.library.jpa.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import de.kaiserpfalzedv.office.library.domain.users.User;

/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-05-18
 */
public class UserModelConverterTest {
    private static UUID IDP_NAME = UUID.randomUUID();
    private static UserJPA DEFAULT_JPA = UserJPA.builder()
            .idpName(IDP_NAME.toString())
            .nameSpace("LIBRARY")
            .name("klenkes74")
            .created(OffsetDateTime.now())
            .modified(OffsetDateTime.now())
            .build();

    private static User DEFAULT_MODEL = new User.InventoryUser(IDP_NAME.toString(), "LIBRARY", "klenkes74", OffsetDateTime.now(), OffsetDateTime.now());

    private final UserModelConverter sut = new UserModelConverter();

    @Test
    void shouldConvertFromJPAWhenIdIsSet() {
        UserJPA orig = DEFAULT_JPA.toBuilder()
                .id(1L)
                .build();
        User result = sut.convertFromJPA(orig);

        assertEquals(orig.getIdpName(), result.idpUser());
        assertEquals(orig.nameSpace(), result.nameSpace());
        assertEquals(orig.name(), result.name());
        assertEquals(orig.created(), result.created());
        assertEquals(orig.modified(), result.modified());
    }

    @Test
    void shouldConvertFromJPAWhenIdIsNotDefined() {
        UserJPA orig = DEFAULT_JPA;
        User result = sut.convertFromJPA(orig);

        assertEquals(orig.getIdpName(), result.idpUser());
        assertEquals(orig.nameSpace(), result.nameSpace());
        assertEquals(orig.name(), result.name());
        assertEquals(orig.created(), result.created());
        assertEquals(orig.modified(), result.modified());
    }

    @Test
    void shouldConvertFromModel() {
        User orig = DEFAULT_MODEL;
        UserJPA result = sut.convertFromModel(orig);

        assertEquals(orig.idpUser(), result.getIdpName());
        assertEquals(orig.nameSpace(), result.nameSpace());
        assertEquals(orig.name(), result.name());
        assertEquals(orig.created(), result.created());
        assertEquals(orig.modified(), result.modified());
    }
}
