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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>ISSN -- International Standard Serial Number</p>
 *
 * <p>An International Standard Serial Number (ISSN) is an eight-digit serial number used to uniquely identify a serial
 * publication, such as a magazine. The ISSN is especially helpful in distinguishing between serials with the same
 * title. ISSNs are used in ordering, cataloging, interlibrary loans, and other practices in connection with serial
 * literature.</p>
 *
 * <p>The ISSN system was first drafted as an International Organization for Standardization (ISO) international
 * standard in 1971 and published as ISO 3297 in 1975. ISO subcommittee TC 46/SC 9 is responsible for maintaining the
 * standard.</p>
 *
 * <p>When a serial with the same content is published in more than one media type, a different ISSN is assigned to each
 * media type. For example, many serials are published both in print and electronic media. The ISSN system refers to
 * these types as print ISSN (p-ISSN) and electronic ISSN (e-ISSN). Consequently, as defined in ISO 3297:2007, every
 * serial in the ISSN system is also assigned a linking ISSN (ISSN-L), typically the same as the ISSN assigned to the
 * serial in its first published medium, which links together all ISSNs assigned to the serial in every medium.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-15
 */
public interface ISSN extends Serializable {
    /**
     * @return The ISSN of the publication.
     */
    @Size(min = 9, max = 9)
    @Pattern(regexp = "^[0-9]{4}-[0-9]{3}[0-9xX]$")
    String getISSN();

    @Min(100000)
    @Max(999999)
    int getNumeric();
}
