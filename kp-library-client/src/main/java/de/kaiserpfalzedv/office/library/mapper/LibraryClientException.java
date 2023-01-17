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

package de.kaiserpfalzedv.office.library.mapper;

import de.kaiserpfalzedv.commons.core.api.BaseSystemException;
import lombok.Getter;

/**
 * <p>LibraryClientException -- The base runtime exception for the whole library client</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-17
 */
public abstract class LibraryClientException extends BaseSystemException {
    @Getter
    private final int code;

    public LibraryClientException(final int code, final String message) {
        super(message);

        this.code = code;
    }

    public LibraryClientException(final int code, final String message, Throwable cause) {
        super(message, cause);

        this.code = code;
    }
}
