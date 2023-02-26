package de.kaiserpfalzedv.office.library.ui.librarian.views.ean;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.quarkus.annotation.UIScoped;
import de.kaiserpfalzedv.commons.vaadin.mvp.nodata.BasicViewImpl;
import de.kaiserpfalzedv.commons.vaadin.nav.AppNavRoute;
import de.kaiserpfalzedv.commons.vaadin.nav.MainLayout;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * <p>EanCheckView -- .</p>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-21
 */
@UIScoped
@AppNavRoute(i18n = "EanCheckView", icon = "las la-barcode")
@Route(value = "/ean-check", layout = MainLayout.class)
@RouteAlias(value = "/", layout = MainLayout.class)
@RolesAllowed({"admin"})
public class EanCheckView extends BasicViewImpl {

    @Inject
    public EanCheckView(@NotNull final EanCheckPresenter presenter, @NotNull final EanCheckForm form) {
        super(presenter, form);

        add(form);
    }

    @Override
    protected void updateView() {
        removeAll();

        add(form);
    }
}
