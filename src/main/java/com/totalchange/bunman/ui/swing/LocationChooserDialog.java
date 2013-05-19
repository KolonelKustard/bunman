package com.totalchange.bunman.ui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.totalchange.bunman.CatalogueFactory;

class LocationChooserDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();

    private File backupRoot = null;
    private File libraryRoot = null;

    /**
     * Create the dialog.
     */
    public LocationChooserDialog(JFrame owner,
            final List<CatalogueFactory.Location> backupLocations,
            final List<CatalogueFactory.Location> libraryLocations) {
        super(owner, true);
        
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel backupPanel = new JPanel();
        backupPanel.setBorder(new TitledBorder(null, "Backup Location",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(backupPanel);
        backupPanel.setLayout(new BorderLayout(0, 0));

        JTextArea backupTextArea = new JTextArea();
        backupTextArea.setMinimumSize(new Dimension(400, 0));
        backupTextArea.setEditable(false);
        backupTextArea.setLineWrap(true);
        backupTextArea.setWrapStyleWord(true);
        backupTextArea.setOpaque(false);
        backupTextArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
        backupTextArea
                .setText("Choose the location of your Brennan JB7 backup. Hopefully the location has already been detected and you can choose from the list already provided. If it's not there then you'll need to click the Browse... button and find the directory containing all the backup files yourself.");
        backupPanel.add(backupTextArea, BorderLayout.NORTH);

        JPanel backupSelectPanel = new JPanel();
        backupPanel.add(backupSelectPanel, BorderLayout.CENTER);
        backupSelectPanel.setLayout(new BorderLayout(0, 0));

        final JComboBox backupComboBox = new JComboBox();
        backupComboBox.setPreferredSize(new Dimension(400, 20));
        for (CatalogueFactory.Location location : backupLocations) {
            backupComboBox.addItem(location.getName() + " - "
                    + location.getRoot());
        }
        backupSelectPanel.add(backupComboBox, BorderLayout.CENTER);

        JButton backupBrowseButton = new JButton("Browse...");
        backupBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File dir = chooseDirectory();
                if (dir != null) {
                    backupComboBox.addItem(dir.getAbsolutePath());
                }
            }
        });
        backupSelectPanel.add(backupBrowseButton, BorderLayout.EAST);

        JPanel libraryPanel = new JPanel();
        libraryPanel.setBorder(new TitledBorder(null, "Library Location",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(libraryPanel);
        libraryPanel.setLayout(new BorderLayout(0, 0));

        JTextArea libraryTextArea = new JTextArea();
        libraryTextArea.setMinimumSize(new Dimension(400, 0));
        libraryTextArea
                .setText("Choose the location of your computers music library. Hopefully the location has already been detected and you can choose from the list already provided. If it's not there then you'll need to click the Browse... button and find the directory containing your music library files yourself.");
        libraryTextArea.setOpaque(false);
        libraryTextArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
        libraryTextArea.setLineWrap(true);
        libraryTextArea.setWrapStyleWord(true);
        libraryTextArea.setEditable(false);
        libraryPanel.add(libraryTextArea, BorderLayout.NORTH);

        JPanel librarySelectPanel = new JPanel();
        libraryPanel.add(librarySelectPanel, BorderLayout.CENTER);
        librarySelectPanel.setLayout(new BorderLayout(0, 0));

        final JComboBox libraryComboBox = new JComboBox();
        libraryComboBox.setPreferredSize(new Dimension(400, 20));
        for (CatalogueFactory.Location location : libraryLocations) {
            libraryComboBox.addItem(location.getName() + " - "
                    + location.getRoot());
        }
        librarySelectPanel.add(libraryComboBox, BorderLayout.CENTER);

        JButton libraryBrowseButton = new JButton("Browse...");
        libraryBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File dir = chooseDirectory();
                if (dir != null) {
                    libraryComboBox.addItem(dir.getAbsolutePath());
                }
            }
        });
        librarySelectPanel.add(libraryBrowseButton, BorderLayout.EAST);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (backupComboBox.getSelectedIndex() <= -1) {
                    backupRoot = null;
                } else if (backupComboBox.getSelectedIndex() < backupLocations
                        .size()) {
                    backupRoot = backupLocations.get(
                            backupComboBox.getSelectedIndex()).getRoot();
                } else {
                    backupRoot = new File(backupComboBox.getSelectedItem()
                            .toString());
                }

                if (libraryComboBox.getSelectedIndex() <= -1) {
                    libraryRoot = null;
                } else if (libraryComboBox.getSelectedIndex() < libraryLocations
                        .size()) {
                    libraryRoot = libraryLocations.get(
                            libraryComboBox.getSelectedIndex()).getRoot();
                } else {
                    libraryRoot = new File(libraryComboBox.getSelectedItem()
                            .toString());
                }

                setVisible(false);
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        this.pack();
        this.setLocationRelativeTo(owner);
    }

    private File chooseDirectory() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        String usersDir = System.getProperty("user.home");
        if (usersDir != null) {
            fc.setCurrentDirectory(new File(usersDir));
        }

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }

    File getBackupRoot() {
        return backupRoot;
    }

    File getLibraryRoot() {
        return libraryRoot;
    }
}
