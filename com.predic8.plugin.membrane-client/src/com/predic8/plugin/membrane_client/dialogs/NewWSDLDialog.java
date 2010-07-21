package com.predic8.plugin.membrane_client.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.predic8.membrane.client.core.controller.ServiceParamsManager;
import com.predic8.membrane.client.core.model.ServiceParams;
import com.predic8.membrane.client.core.util.SOAModelUtil;
import com.predic8.plugin.membrane_client.ImageKeys;
import com.predic8.plugin.membrane_client.MembraneClientUIPlugin;
import com.predic8.plugin.membrane_client.ui.PluginUtil;

public class NewWSDLDialog extends Dialog {

	private Text textURL;

	private Composite bComposite;

	private Button btLocation;
	
	private Button btFile;
	
	private Text textFilePath;
	
	private Button btBrowse;
	
	public NewWSDLDialog(Shell shell) {
		super(shell);
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setSize(520, 160);
		shell.setText("Add New WSDL");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		bComposite = createBaseAreaComposite(parent);

		WSDLSourceSelection sListener = new WSDLSourceSelection();
		
		btLocation = new Button(bComposite, SWT.RADIO);
		btLocation.addSelectionListener(sListener);
		btLocation.setSelection(true);
		
		createLabel(70).setText("Location URL: ");

		textURL = PluginUtil.createText(bComposite, 320);

		createLabel(30).setText(" ");
		
		
		btFile = new Button(bComposite, SWT.RADIO);
		btFile.addSelectionListener(sListener);
		createLabel(70).setText("File path: ");
		
		textFilePath = PluginUtil.createText(bComposite, 320);
		textFilePath.setEnabled(false);
		
		
		btBrowse = createFileBrowser();
		
		
		return bComposite;
	}

	private Button createFileBrowser() {
		Button bt = new Button(bComposite, SWT.PUSH); 
		bt.setImage(MembraneClientUIPlugin.getDefault().getImageRegistry().getDescriptor(ImageKeys.IMAGE_FOLDER).createImage());
		GridData g = new GridData();
		g.heightHint = 20;
		g.widthHint = 20;
		bt.setLayoutData(g);
		bt.setEnabled(false);
		bt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textFilePath.setText(openFileDialog());
			}
		});
		
		return bt;
	}

	private Label createLabel(int width) {
		Label label = new Label(bComposite, SWT.NONE);
		GridData gData = new GridData();
		//gData.heightHint = 22;
		gData.widthHint = width;
		label.setLayoutData(gData);
		return label;
	}

	private Composite createBaseAreaComposite(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.marginLeft = 20;
		layout.marginRight = 20;
		layout.marginTop = 20;
		layout.marginBottom = 20;
		composite.setLayout(layout);
		return composite;
	}

	@Override
	protected void okPressed() {
		try {
			ServiceParamsManager.getInstance().addNewServiceParams(getServiceParams(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.okPressed();
	}

	private ServiceParams getServiceParams() {
		if (btLocation.getSelection()) {
			return new ServiceParams(textURL.getText().trim(), SOAModelUtil.getDefinitions(textURL.getText().trim()));
		}
		return new ServiceParams(textFilePath.getText().trim(), SOAModelUtil.getDefinitions("file:"+textFilePath.getText().trim()));
	}

	private class WSDLSourceSelection extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			onWSDLSourceSelection();
		}
	}

	private void enableWidgets(boolean enabled) {
		textURL.setEnabled(enabled);
		textFilePath.setEnabled(!enabled);
		btBrowse.setEnabled(!enabled);
	}

	private void onWSDLSourceSelection() {
		Display.getCurrent().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (btLocation.getSelection()) {
					enableWidgets(true);
				} else {
					enableWidgets(false);
				}
			}
		});
	}
	
	private String openFileDialog() {
		FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
		dialog.setText("Open");
		dialog.setFilterExtensions(new String[] { "*.*" }); //"*.*",
		String selected = dialog.open();
		return selected;
	}
	
}