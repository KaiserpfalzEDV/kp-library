package de.kaiserpfalzedv.office.library.ui.librarian.views.asset;

import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.UIScoped;
import de.kaiserpfalzedv.commons.vaadin.mvp.data.BasicDataViewImpl;
import de.kaiserpfalzedv.commons.vaadin.nav.MainLayout;
import de.kaiserpfalzedv.office.library.model.client.Asset;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

/**
 * <p>AssetsView -- .</p>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-21
 */
@UIScoped
@Route(value = "/asset/:id", layout = MainLayout.class)
@Slf4j
public class AssetsView extends BasicDataViewImpl<Asset> {
    @Inject
    public AssetsView(AssetPresenter presenter, AssetDataForm form) {
        super(presenter, form);
    }

    @Override
    protected void updateView() {

    }
}
