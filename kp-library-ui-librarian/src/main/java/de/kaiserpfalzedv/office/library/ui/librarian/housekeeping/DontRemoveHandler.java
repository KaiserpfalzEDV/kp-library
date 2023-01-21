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

package de.kaiserpfalzedv.office.library.ui.librarian.housekeeping;

import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import de.kaiserpfalzedv.commons.rest.LoggingFilter;
import de.kaiserpfalzedv.commons.rest.workflow.WorkflowFilter;
import de.kaiserpfalzedv.commons.rest.workflow.WorkflowProvider;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * <p>DontRemoveHandler -- .</p>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-21
 */
@SuppressWarnings("unused")
@Slf4j
public class DontRemoveHandler {
    private AccessAnnotationChecker accessAnnotationChecker = new AccessAnnotationChecker();
    private LoggingFilter loggingFilter = new LoggingFilter();
    private WorkflowProvider workflowProvider = new WorkflowProvider();
    private WorkflowFilter workflowFilter = new WorkflowFilter(workflowProvider);

    @PostConstruct
    public void init() {
        log.debug("Unremoved some of the infrastructure services: {}",
                (Object) new Object[] { accessAnnotationChecker, loggingFilter, workflowFilter, workflowProvider });
    }
}
