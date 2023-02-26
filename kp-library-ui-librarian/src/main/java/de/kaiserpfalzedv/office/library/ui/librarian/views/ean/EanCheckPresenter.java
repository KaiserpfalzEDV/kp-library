package de.kaiserpfalzedv.office.library.ui.librarian.views.ean;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.quarkus.annotation.UIScoped;
import de.kaiserpfalzedv.commons.external.dnb.client.DnbLookupClient;
import de.kaiserpfalzedv.commons.external.dnb.model.Book;
import de.kaiserpfalzedv.commons.vaadin.mvp.nodata.BasicPresenterImpl;
import de.kaiserpfalzedv.commons.vaadin.users.FrontendUser;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>EanCheckPresenter -- .</p>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-21
 */
@UIScoped
public class EanCheckPresenter extends BasicPresenterImpl {
    /**
     * The lookup for the Deutsche National Bibliothek.
     */
    private final DnbLookupClient client;

    @Inject
    public EanCheckPresenter(
            @RestClient @NotNull final DnbLookupClient client,
            @NotNull final FrontendUser user
    ) {
        super(user);

        this.client = client;
    }

    @Override
    public void execute() {
        String ean = form().getEan();
        form().clear();

        List<Book> data = client.lookup(ean);

        form().addEan(data);
        Notification.show("Execute of '" + this.getClass().getSimpleName() + "'.");
    }

    @Override
    public void reset() {
        Notification.show("Reset of '" + this.getClass().getSimpleName() + "'.");
    }

    @Override
    public void close() {
        Notification.show("Close of '" + this.getClass().getSimpleName() + "'.");
    }

    EanCheckForm form() {
        return (EanCheckForm) form;
    }

    EanCheckView view() {
        return (EanCheckView) view;
    }
}
