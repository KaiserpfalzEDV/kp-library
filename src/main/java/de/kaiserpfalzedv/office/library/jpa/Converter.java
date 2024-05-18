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
package de.kaiserpfalzedv.office.library.jpa;



import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-05-18
 */
@RequiredArgsConstructor
@ToString(includeFieldNames = true)
@Slf4j
public abstract class Converter<M, J> {
    protected final Function<M, J> fromModel;
    protected final Function<J, M> fromJPA;

    public final J convertFromModel(final M model) {
        log.trace("Converting model to jpa. input={}", model);

        return fromModel.apply(model);
    }

    public final M convertFromJPA(final J jpa) {
        log.trace("Converting jpa to model. input={}", jpa);

        return fromJPA.apply(jpa);
    }

    public final List<J> createFromModel(final Collection<M> models) {
        return models.stream().map(this::convertFromModel).collect(Collectors.toList());
    }

    public final List<M> createFromPA(final Collection<J> jpas) {
        return jpas.stream().map(this::convertFromJPA).collect(Collectors.toList());
    }
}
