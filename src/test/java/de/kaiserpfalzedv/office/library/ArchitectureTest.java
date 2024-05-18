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
package de.kaiserpfalzedv.office.library;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-05-18
 */
@AnalyzeClasses(
    packages="de.kaiserpfalzedv.office.library",
    importOptions = ImportOption.DoNotIncludeTests.class
)
public class ArchitectureTest {
    @ArchTest
    static final ArchRule onienArchitectureIsRespected = Architectures.onionArchitecture()
        .domainModels("de.kaiserpfalzedv.office.library.domain..")
        .domainServices("de.kaiserpfalzedv.office.library.domain..")
        
        .applicationServices("de.kaiserpfalzedv.office.library.application..")
        
        .adapter("jpa", "de.kaiserpfalzedv.office.library.jpa..")
        .adapter("api", "de.kaiserpfalzedv.office.library.api..")
        .adapter("ui", "de.kaiserpfalzedv.office.library.ui..")

        .withOptionalLayers(true)
        .ensureAllClassesAreContainedInArchitectureIgnoring("de.kaiserpfalzedv.office.library");
}
