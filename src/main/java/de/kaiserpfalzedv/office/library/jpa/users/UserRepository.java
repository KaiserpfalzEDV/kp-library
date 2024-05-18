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

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.kaiserpfalzedv.office.library.domain.users.User;


/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-05-05
 */
public interface UserRepository extends JpaRepository<UserJPA, Long>, de.kaiserpfalzedv.office.library.domain.users.UserRepository {
    default User save(final User user) {
        UserJPA jpa = new UserModelConverter().convertFromModel(user);

        jpa = insertIdFromExistingJPA(user, jpa);
        jpa = saveAndFlush(jpa);

        return new UserModelConverter().convertFromJPA(jpa);
    }

    default UserJPA insertIdFromExistingJPA(final User user, UserJPA jpa) {
        UserJPA orig = findByIdpName(user.idpUser());

        if (orig != null) {
            jpa = jpa.toBuilder().id(orig.getId()).build();
        }

        return jpa;
    }

    default Optional<User> find(final String idpName) {
        return Optional.ofNullable(new UserModelConverter().convertFromJPA(findByIdpName(idpName)));
    }

    default List<User> all() {
        return new UserModelConverter().createFromPA(findAll());
    }
    
    default void delete(final String idpName) {
        UserJPA user = findByIdpName(idpName);
        if (user != null) {
            delete(user);
        }
    }

    UserJPA findByIdpName(final String idpName);
}
