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



import de.kaiserpfalzedv.office.library.domain.users.User;
import de.kaiserpfalzedv.office.library.jpa.Converter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-05-18
 */
@ToString(includeFieldNames = true)
@Slf4j
public class UserModelConverter extends Converter<User, UserJPA> {
    public UserModelConverter() {
        super(UserModelConverter::convertToJPA, UserModelConverter::convertToModel);
    }

    private static UserJPA convertToJPA(final User model) {
        log.trace("User converting to JPA port. user={}", model);

        if (model == null) {
            return null;
        }

        return UserJPA.builder()

            .idpName(model.idpUser())
            .nameSpace(model.nameSpace())
            .name(model.name())
            .created(model.created())
            .modified(model.modified())
            
            .build();
    }

    private static User convertToModel(final UserJPA jpa) {
        log.trace("User converting from JPA port. user={}", jpa);

        if (jpa == null) {
            return null;
        }

        return new User.InventoryUser(
            jpa.getIdpName(), 
            jpa.getNameSpace(), jpa.getName(), 
            jpa.getCreated(), jpa.getModified()
        );
    }
    
}
