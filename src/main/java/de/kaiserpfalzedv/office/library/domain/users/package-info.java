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

/**
 * User Model -- a model for referencing users within the library.
 * 
 * <p>The user model is not used for securing transactions within the library model.
 * It is used to be able to borrow media or record who did changes.</p>
 * 
 * <p>Security needs to be defined at the application level and will use the appropriate security integration offered by the frameworks used.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-18
 */
package de.kaiserpfalzedv.office.library.domain.users;