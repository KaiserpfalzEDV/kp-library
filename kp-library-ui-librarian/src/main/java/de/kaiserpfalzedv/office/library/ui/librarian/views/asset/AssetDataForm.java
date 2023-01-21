package de.kaiserpfalzedv.office.library.ui.librarian.views.asset;

import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.quarkus.annotation.UIScoped;
import de.kaiserpfalzedv.commons.vaadin.mvp.data.BasicDataForm;
import de.kaiserpfalzedv.commons.vaadin.users.FrontendUser;
import de.kaiserpfalzedv.office.library.model.client.Asset;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * <p>AssetDataForm -- .</p>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-21
 */
@UIScoped
@Slf4j
public class AssetDataForm extends BasicDataForm<Asset> {

    @Inject
    public AssetDataForm(@NotNull FrontendUser user) {
        super(user, new BeanValidationBinder<>(Asset.class));
    }

    @Override
    protected void bind() {
        if (data == null) {
            data = Asset.builder().build();
        }

        binder.setBean(data);
    }
}
