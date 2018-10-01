package com.gmail.douglas.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import com.gmail.douglas.ui.views.orderedit.OrderItemsEditor;

public class NewEditorEvent extends ComponentEvent<OrderItemsEditor> {

	public NewEditorEvent(OrderItemsEditor component) {
		super(component, false);
	}
}