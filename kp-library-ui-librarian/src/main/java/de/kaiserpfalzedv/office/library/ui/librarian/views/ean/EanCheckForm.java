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

package de.kaiserpfalzedv.office.library.ui.librarian.views.ean;

import com.github.appreciated.card.Card;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.quarkus.annotation.UIScoped;
import de.kaiserpfalzedv.commons.external.eansearch.model.EanData;
import de.kaiserpfalzedv.commons.vaadin.mvp.nodata.BasicForm;
import de.kaiserpfalzedv.commons.vaadin.users.FrontendUser;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * <p>EanCheckForm -- .</p>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-21
 */
@UIScoped
@Slf4j
public class EanCheckForm extends BasicForm {
    private VerticalLayout cards;

    private TextField ean;

    @Inject
    public EanCheckForm(@NotNull FrontendUser user) {
        super(user);

        cards = new VerticalLayout();
        cards.setSpacing(true);
        cards.setPadding(true);
        cards.setAlignItems(FlexComponent.Alignment.START);
        cards.setVisible(false);

        ean = new TextField(getTranslation("form.eancheck.ean.caption"));
        ean.setTooltipText(getTranslation("form.eancheck.ean.help"));
        ean.setAutofocus(true);

        add(ean, cards);
    }


    String getEan() {
        return ean.getValue();
    }

    void clear() {
        ean.clear();
    }

    void addEan(@NotNull final Collection<EanData> data) {
        cards.setVisible(true);

        for(EanData d : data) {
            Card card = new Card();
            card.setAlignItems(FlexComponent.Alignment.START);
            card.add(new H2(d.getEan()));
            card.add(new Paragraph(d.getName()));
            card.add(new Paragraph("Category: " + d.getCategoryName() + " (" + d.getCategoryId() + ")"));

            cards.add(card);
        }
    }
}
