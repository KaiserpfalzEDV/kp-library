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

package de.kaiserpfalzedv.office.library.model;

import de.kaiserpfalzedv.commons.core.resources.HasId;
import de.kaiserpfalzedv.commons.core.resources.HasNameSpace;
import de.kaiserpfalzedv.office.library.api.Email;
import de.kaiserpfalzedv.office.library.api.HasDisplayName;
import de.kaiserpfalzedv.office.library.api.HasRecord;

/**
 * <p>User -- A user of the library.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface User extends HasId, HasRecord, HasDisplayName, HasNameSpace {
    String getIdpUser();

    Email getEmail();
}
