package de.kaiserpfalzedv.office.library.ui.librarian.views.asset;

import com.vaadin.quarkus.annotation.UIScoped;
import de.kaiserpfalzedv.commons.vaadin.mvp.data.BasicDataPresenterImpl;
import de.kaiserpfalzedv.commons.vaadin.users.FrontendUser;
import de.kaiserpfalzedv.office.library.model.client.Asset;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * <p>AssetPresenter -- .</p>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-21
 */
@UIScoped
public class AssetPresenter extends BasicDataPresenterImpl<Asset> {
    @Inject
    public AssetPresenter(@NotNull final FrontendUser user) {
        super(user);
    }

    @Override
    public void loadId(@NotNull UUID id) {

    }

    @Override
    public void save() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void execute() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void close() {

    }
}
