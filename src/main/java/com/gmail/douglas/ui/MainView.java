package com.gmail.douglas.ui;

import static com.gmail.douglas.ui.utils.BakeryConst.ICON_DASHBOARD;
import static com.gmail.douglas.ui.utils.BakeryConst.ICON_LOGOUT;
import static com.gmail.douglas.ui.utils.BakeryConst.ICON_PRODUCTS;
import static com.gmail.douglas.ui.utils.BakeryConst.ICON_STOREFRONT;
import static com.gmail.douglas.ui.utils.BakeryConst.ICON_USERS;
import static com.gmail.douglas.ui.utils.BakeryConst.PAGE_DASHBOARD;
import static com.gmail.douglas.ui.utils.BakeryConst.PAGE_DEFAULT;
import static com.gmail.douglas.ui.utils.BakeryConst.PAGE_LOGOUT;
import static com.gmail.douglas.ui.utils.BakeryConst.PAGE_PRODUCTS;
import static com.gmail.douglas.ui.utils.BakeryConst.PAGE_STOREFRONT;
import static com.gmail.douglas.ui.utils.BakeryConst.PAGE_USERS;
import static com.gmail.douglas.ui.utils.BakeryConst.TITLE_DASHBOARD;
import static com.gmail.douglas.ui.utils.BakeryConst.TITLE_LOGOUT;
import static com.gmail.douglas.ui.utils.BakeryConst.TITLE_PRODUCTS;
import static com.gmail.douglas.ui.utils.BakeryConst.TITLE_STOREFRONT;
import static com.gmail.douglas.ui.utils.BakeryConst.TITLE_USERS;
import static com.gmail.douglas.ui.utils.BakeryConst.VIEWPORT;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.gmail.douglas.app.security.SecurityUtils;
import com.gmail.douglas.ui.components.AppNavigation;
import com.gmail.douglas.ui.components.BakeryCookieConsent;
import com.gmail.douglas.ui.entities.PageInfo;
import com.gmail.douglas.ui.exceptions.AccessDeniedException;
import com.gmail.douglas.ui.views.HasConfirmation;
import com.gmail.douglas.ui.views.admin.products.ProductsView;
import com.gmail.douglas.ui.views.admin.users.UsersView;
import org.springframework.beans.factory.annotation.Autowired;

@Tag("main-view")
@HtmlImport("src/main-view.html")

@PageTitle("My Starter Project")
@Viewport(VIEWPORT)
public class MainView extends PolymerTemplate<TemplateModel>
		implements RouterLayout, BeforeEnterObserver {

	@Id("appNavigation")
	private AppNavigation appNavigation;

	private final ConfirmDialog confirmDialog;

	@Autowired
	public MainView() {
		this.confirmDialog = new ConfirmDialog();
		confirmDialog.setCancelable(true);
		confirmDialog.setConfirmButtonTheme("raised tertiary error");
		confirmDialog.setCancelButtonTheme("raised tertiary");

		List<PageInfo> pages = new ArrayList<>();

		pages.add(new PageInfo(PAGE_STOREFRONT, ICON_STOREFRONT, TITLE_STOREFRONT));
		pages.add(new PageInfo(PAGE_DASHBOARD, ICON_DASHBOARD, TITLE_DASHBOARD));
		if (SecurityUtils.isAccessGranted(UsersView.class)) {
			pages.add(new PageInfo(PAGE_USERS, ICON_USERS, TITLE_USERS));
		}
		if (SecurityUtils.isAccessGranted(ProductsView.class)) {
			pages.add(new PageInfo(PAGE_PRODUCTS, ICON_PRODUCTS, TITLE_PRODUCTS));
		}
		pages.add(new PageInfo(PAGE_LOGOUT, ICON_LOGOUT, TITLE_LOGOUT));

		appNavigation.init(pages, PAGE_DEFAULT, PAGE_LOGOUT);

		getElement().appendChild(confirmDialog.getElement());
		getElement().appendChild(new BakeryCookieConsent().getElement());
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
			event.rerouteToError(AccessDeniedException.class);
		}
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		if (content != null) {
			getElement().appendChild(content.getElement());
		}

		this.confirmDialog.setOpened(false);
		if (content instanceof HasConfirmation) {
			((HasConfirmation) content).setConfirmDialog(this.confirmDialog);
		}
	}
}
