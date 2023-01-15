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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>ISBN -- International Standard Book Number</p>
 *
 * <p>The International Standard Book Number (ISBN) is a numeric commercial book identifier that is intended to be
 * unique. Publishers purchase ISBNs from an affiliate of the International ISBN Agency.</p>
 *
 * <p>An ISBN is assigned to each separate edition and variation (except reprintings) of a publication. For example,
 * an e-book, a paperback and a hardcover edition of the same book will each have a different ISBN. The ISBN is ten
 * digits long if assigned before 2007, and thirteen digits long if assigned on or after 1 January 2007. The method
 * of assigning an ISBN is nation-specific and varies between countries, often depending on how large the publishing
 * industry is within a country.</p>
 *
 * <p>The initial ISBN identification format was devised in 1967, based upon the 9-digit Standard Book Numbering (SBN)
 * created in 1966. The 10-digit ISBN format was developed by the International Organization for Standardization (ISO)
 * and was published in 1970 as international standard ISO 2108 (the 9-digit SBN code can be converted to a 10-digit
 * ISBN by prefixing it with a zero).</p>
 *
 * <p>Privately published books sometimes appear without an ISBN. The International ISBN Agency sometimes assigns such
 * books ISBNs on its own initiative.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface ISBN extends EAN, Serializable {
    /**
     * The valid ISBN of a book.
     * @return The ISBN of the book.
     */
    @NotNull
    String getIsbn13();

    /**
     * If the book still has a ISBN-10 code this method will return it.
     * @return The ISBN-10 code (if existing).
     */
    @NotNull
    default String getIsbn10() {
        return getIsbn13().substring(4);
    }

    @Override
    default String getEAN() {
        return getIsbn13();
    }
}
