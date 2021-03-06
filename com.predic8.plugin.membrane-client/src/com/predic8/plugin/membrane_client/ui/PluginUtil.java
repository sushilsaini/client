/* Copyright 2010 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package com.predic8.plugin.membrane_client.ui;

import java.util.Map;
import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.predic8.membrane.core.Constants;
import com.predic8.plugin.membrane_client.MembraneClientUIPlugin;
import com.predic8.schema.restriction.BaseRestriction;

public class PluginUtil {

	public static Composite createComposite(Composite parent, int col) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = col;
		layout.marginTop = 10;
		layout.marginLeft = 10;
		layout.marginBottom = 10;
		layout.marginRight = 10;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		return composite;
	}

	public static Text createText(Composite comp, int width) {
		Text text = new Text(comp, SWT.BORDER);
		GridData gData = new GridData();
		gData.heightHint = 14;
		gData.widthHint = width;
		text.setLayoutData(gData);
		return text;
	}

	public static Text createPasswordText(Composite comp, int width) {
		Text text = new Text(comp, SWT.PASSWORD | SWT.BORDER);
		GridData gData = new GridData();
		gData.heightHint = 14;
		gData.widthHint = width;
		text.setLayoutData(gData);
		return text;
	}
	
	public static Text createText(Composite comp, int width, int height) {
		Text text = new Text(comp, SWT.BORDER);
		text.setLayoutData(LayoutUtil.createGridData(width, height));
		return text;
	}
	
	public static Text createText(Composite comp, int width, int height, BaseRestriction restriction) {
		if (restriction == null)
			return createText(comp, width, height);
		
		Text text = new Text(comp, SWT.BORDER);
		GridData gData = new GridData();
		gData.heightHint = height;
		gData.widthHint = width;
		text.setLayoutData(gData);
		return text;
	}
	
	public static Combo createCombo(Composite comp, int width, int height) {
		Combo combo = new Combo(comp, SWT.DROP_DOWN | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gData = new GridData();
		gData.heightHint = height;
		gData.widthHint = width;
		combo.setLayoutData(gData);
		return combo;
	}
	
	public static Button createCheckButton(Composite parent, int w, int h) {
		Button bt = new Button(parent, SWT.CHECK);
		GridData chk = new GridData();
		chk.widthHint = w;
		chk.heightHint = h;
		bt.setLayoutData(chk);
		return bt;
	}
	
	public static Label createLabel(Composite parent, int width) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(LayoutUtil.createGridData(width));
		return label;
	}
	
	public static Label createLabel(Composite parent, int width, int height) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(LayoutUtil.createGridData(width, height));
		return label;
	}
	
	public static Image createImage(String path, String key) {
		if (MembraneClientUIPlugin.getDefault() == null)
			return ImageDescriptor.createFromFile(MembraneClientUIPlugin.class,path).createImage();
	
		return MembraneClientUIPlugin.getDefault().getImageRegistry().getDescriptor(key).createImage();
	}
	
	public static IViewPart showView(String viewId) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			page.showView(viewId);
			return page.findView(viewId);
		} catch (PartInitException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to find view. View ID may be not correct: " + viewId);
		}
	}
	
	public static IViewPart getView(String viewId) {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(viewId);
	}
	
	
	public static void closeView(String viewId) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView(viewId);
		if (page.isPartVisible(view)) {
			page.hideView(view);
		}
	}
	
	public static String getMapContent(Map<String, String> map) {
		StringBuffer buf = new StringBuffer();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			buf.append(key);
			buf.append(": ");
			buf.append(map.get(key));
			buf.append(Constants.CRLF);
		}
		return buf.toString();
	}
	
	public static Group createGroup(Composite composite, String title, int col, int margin) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(title);
		GridLayout layout = new GridLayout();
		layout.marginTop = margin;
		layout.marginLeft = margin;
		layout.marginBottom = margin;
		layout.marginRight = margin;
		
		layout.numColumns = col;
		layout.verticalSpacing = 5;
		group.setLayout(layout);
		return group;
	}
	
}
