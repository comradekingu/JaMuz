/*
 * Copyright (C) 2011 phramusca ( https://github.com/phramusca/JaMuz/ )
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jamuz.gui;

import jamuz.process.sync.Device;
import jamuz.Jamuz;
import jamuz.process.merge.StatSource;
import jamuz.utils.Popup;
import javax.swing.DefaultComboBoxModel;
import org.apache.commons.io.FilenameUtils;
import jamuz.utils.Inter;
import jamuz.utils.Swing;

/**
 * JDialog extension to add/modify Stat source
 * @author phramusca ( https://github.com/phramusca/JaMuz/ )
 */
public class DialogStatSource extends javax.swing.JDialog {

	private final StatSource statSource;
	
	
	/** Creates new form StatSourceGUI
	 * @param parent
	 * @param modal
	 * @param statSource  
	 */
    public DialogStatSource(java.awt.Frame parent, boolean modal, StatSource statSource) {
        super(parent, modal);
        initComponents();
		this.statSource = statSource;
		//Fill the Playlist Combobox
		//TODO: Do the same for every combobox in JaMuz (avoid for loop + include whole object with included id)
		jComboBoxDevice.setModel(new DefaultComboBoxModel(Jamuz.getMachine().getDevices().toArray()));
		DefaultComboBoxModel<Device> comboModelDevice = (DefaultComboBoxModel<Device>) jComboBoxDevice.getModel();
		comboModelDevice.insertElementAt(Jamuz.getMachine().getDevice(0), 0);  //Default none hard coded device
		if(statSource.getIdDevice()>0) {
			jComboBoxDevice.setSelectedItem(statSource.getDevice());
		}
		else {
			jComboBoxDevice.setSelectedIndex(0);
		}
		
        jComboBoxType.setSelectedIndex(statSource.getIdStatement() - 1);
        
		if(!this.statSource.getSource().getName().equals("")) {
			jTextName.setText(this.statSource.getSource().getName());
		}
		jTextLocation.setText(statSource.getSource().getLocation());
		jTextRootPath.setText(statSource.getSource().getRootPath());
		jCheckBoxSelected.setSelected(statSource.isIsSelected());
		
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelName = new javax.swing.JLabel();
        jTextName = new javax.swing.JTextField();
        jLabelType = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox();
        jLabelLocation = new javax.swing.JLabel();
        jTextLocation = new javax.swing.JTextField();
        jLabelRootPath = new javax.swing.JLabel();
        jTextRootPath = new javax.swing.JTextField();
        jButtonSelect = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jLabelDevice = new javax.swing.JLabel();
        jComboBoxDevice = new javax.swing.JComboBox<>();
        jCheckBoxSelected = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("jamuz/Bundle"); // NOI18N
        setTitle(bundle.getString("DialogStatSource.title")); // NOI18N
        setModal(true);

        jLabelName.setText(bundle.getString("Label.Name")); // NOI18N

        jTextName.setText("jTextField1"); // NOI18N

        jLabelType.setText(bundle.getString("Label.Type")); // NOI18N

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Guayadeque (Linux)", "Kodi (Linux/Windows)", "MediaMonkey (Windows)", "Mixxx (Linux/Windows)", "MyTunes (Android)" }));
        jComboBoxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTypeActionPerformed(evt);
            }
        });

        jLabelLocation.setText(bundle.getString("Label.Location")); // NOI18N

        jTextLocation.setText("jTextField1"); // NOI18N

        jLabelRootPath.setText(bundle.getString("Label.RootPath")); // NOI18N

        jTextRootPath.setText("jTextField1"); // NOI18N

        jButtonSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jamuz/ressources/folder_explore.png"))); // NOI18N
        jButtonSelect.setText(bundle.getString("Button.Select")); // NOI18N
        jButtonSelect.setEnabled(false);
        jButtonSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectActionPerformed(evt);
            }
        });

        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jamuz/ressources/cancel.png"))); // NOI18N
        jButtonCancel.setText(bundle.getString("Button.Cancel")); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jamuz/ressources/accept.png"))); // NOI18N
        jButtonSave.setText(bundle.getString("Button.Save")); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jLabelDevice.setText(bundle.getString("PlaylistOrderGUI.jLabelDevice.text")); // NOI18N

        jComboBoxDevice.setModel(new DefaultComboBoxModel<Device>());

        jCheckBoxSelected.setText(bundle.getString("DialogStatSource.jCheckBoxSelected.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRootPath)
                    .addComponent(jLabelLocation, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelType, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelDevice, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextName)
                    .addComponent(jTextLocation)
                    .addComponent(jComboBoxType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBoxSelected)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 702, Short.MAX_VALUE)
                                .addComponent(jButtonCancel))
                            .addComponent(jTextRootPath))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonSelect)
                            .addComponent(jButtonSave, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jComboBoxDevice, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelType)
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLocation)
                    .addComponent(jTextLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRootPath)
                    .addComponent(jTextRootPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSelect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDevice)
                    .addComponent(jComboBoxDevice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonSave)
                    .addComponent(jCheckBoxSelected))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
		String name = jTextName.getText();
		String location = jTextLocation.getText();
		String rootPath = jTextRootPath.getText();

		//TODO: This check could be part of StatSource's check() method ...
		if(name.equals("") || location.equals("") || rootPath.equals("")) {  //NOI18N
			Popup.warning(Inter.get("Error.AllFieldsMustBeSet")); //NOI18N
		}
		else {
			this.statSource.getSource().setName(name);
			this.statSource.getSource().setLocation(location);
			//Check if rootPath ends with a separator and add it if missing
			// FilenameUtils.normalize(rootPath); cannot be applied because of some cases:
			// - mixxx on Windows: rootPath is not on current machine format
			// - MediaMonkey: rootPath is not a valid path (starts with ":")
			int idLastSeparator=FilenameUtils.indexOfLastSeparator(rootPath);
			if(idLastSeparator+1<rootPath.length() && idLastSeparator>-1) {
				String separator=rootPath.substring(idLastSeparator, idLastSeparator+1);
				rootPath=rootPath+separator;
			}
			this.statSource.getSource().setRootPath(rootPath);
			this.statSource.setIdStatement(jComboBoxType.getSelectedIndex()+1);
			this.statSource.setIsSelected(jCheckBoxSelected.isSelected());
			Device device = (Device)jComboBoxDevice.getSelectedItem();
			this.statSource.setIdDevice(device.getId());
			//TODO: Cannot use check() as type is not initialized at this stage ...
//			if(this.myStatSource.check()) {
				if(Jamuz.getDb().setStatSource(this.statSource)) {
					this.dispose();
					DialogOptions.displayStatSources();
				}
				else {
					Popup.warning(Inter.get("Error.Saving")); //NOI18N
				}
//			}
		}
	}//GEN-LAST:event_jButtonSaveActionPerformed

	private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
		this.dispose();
	}//GEN-LAST:event_jButtonCancelActionPerformed

	private void jButtonSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectActionPerformed
		//Disabled 
        //TODO: Read database (first need to make sure that database exists and is readable)
        //to read root path. Change button name to "Auto"
        // To determine root path:
        //Ex, pour Kodi: SELECT strPath FROM path ORDER BY length(strPath) ASC LIMIT 1
        
        //Par contre, utiliser le code ci-dessous pour chercher un dossier
        //(voir aussi si FTP ...)
		String selectedFolder=Swing.selectFolder(jTextRootPath.getText(), Inter.get("Label.RootPath"));
		if(!selectedFolder.equals("")) {  //NOI18N
			jTextRootPath.setText(selectedFolder); 
		}
	}//GEN-LAST:event_jButtonSelectActionPerformed

    private void jComboBoxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTypeActionPerformed
        if(this.statSource.getSource().getName().equals("")) {
			jTextName.setText(jComboBoxType.getSelectedItem().toString());
		}
    }//GEN-LAST:event_jComboBoxTypeActionPerformed

    /**
	 * Open the GUI
	 * @param myStatSource 
	 */
    public static void main(final StatSource myStatSource) {
        java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
            public void run() {

                DialogStatSource dialog = new DialogStatSource(new javax.swing.JFrame(), true, myStatSource);
				//Center the dialog
				dialog.setLocationRelativeTo(dialog.getParent());
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSelect;
    private javax.swing.JCheckBox jCheckBoxSelected;
    private javax.swing.JComboBox<Device> jComboBoxDevice;
    private javax.swing.JComboBox jComboBoxType;
    private javax.swing.JLabel jLabelDevice;
    private javax.swing.JLabel jLabelLocation;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelRootPath;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JTextField jTextLocation;
    private javax.swing.JTextField jTextName;
    private javax.swing.JTextField jTextRootPath;
    // End of variables declaration//GEN-END:variables

}
