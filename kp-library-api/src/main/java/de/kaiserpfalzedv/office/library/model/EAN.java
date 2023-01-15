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

/**
 * <p>EAN -- This medium has an EAN assigned.</p>
 *
 * <p>The International Article Number (also known as European Article Number or EAN) is a standard describing a barcode
 * symbology and numbering system used in global trade to identify a specific retail product type, in a specific
 * packaging configuration, from a specific manufacturer. The standard has been subsumed in the Global Trade Item Number
 * standard from the GS1 organization; the same numbers can be referred to as GTINs and can be encoded in other barcode
 * symbologies defined by GS1. EAN barcodes are used worldwide for lookup at retail point of sale, but can also be used
 * as numbers for other purposes such as wholesale ordering or accounting. These barcodes only represent the digits 0–9,
 * unlike some other barcode symbologies which can represent additional characters.</p>
 *
 * <p>The most commonly used EAN standard is the thirteen-digit EAN-13, a superset of the original 12-digit Universal
 * Product Code (UPC-A) standard developed in 1970 by George J. Laurer. An EAN-13 number includes a 3-digit GS1 prefix
 * (indicating country of registration or special type of product). A prefix with a first digit of "0" indicates a
 * 12-digit UPC-A code follows. A prefix with first two digits of "45" or "49" indicates a Japanese Article Number (JAN)
 * follows.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface EAN {
    /**
     * @return The EAN number of this medium.
     */
    @NotNull
    String getEAN();
}
