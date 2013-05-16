package com.totalchange.bunman.ui.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.totalchange.bunman.CatalogueFactory.Location;
import com.totalchange.bunman.Song;
import com.totalchange.bunman.ui.BunmanPresenter;
import com.totalchange.bunman.ui.BunmanView;
import javax.swing.JTextArea;

public class BunmanFrame extends JFrame implements BunmanView {
    private static final long serialVersionUID = 1L;

    private BunmanPresenter presenter;

    private SongTableModel backupTableModel;
    private SongTableModel libraryTableModel;

    /**
     * Create the application.
     */
    public BunmanFrame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 596, 386);

        this.backupTableModel = new SongTableModel();

        this.libraryTableModel = new SongTableModel();

        JSplitPane mainPane = new JSplitPane();
        mainPane.setResizeWeight(0.8);
        mainPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        getContentPane().add(mainPane, BorderLayout.CENTER);

        JSplitPane cataloguesPane = new JSplitPane();
        cataloguesPane.setResizeWeight(0.5);
        mainPane.setLeftComponent(cataloguesPane);

        JPanel backupPanel = new JPanel();
        cataloguesPane.setLeftComponent(backupPanel);
        backupPanel.setLayout(new BorderLayout(0, 0));

        JLabel backupLabel = new JLabel(
                Messages.getString("BunmanFrame.backupLabel.text")); //$NON-NLS-1$
        backupPanel.add(backupLabel, BorderLayout.NORTH);

        JScrollPane backupScrollPane = new JScrollPane();
        backupPanel.add(backupScrollPane, BorderLayout.CENTER);
        JTable backupTable = new JTable(backupTableModel);
        backupScrollPane.setViewportView(backupTable);

        JPanel libraryPanel = new JPanel();
        cataloguesPane.setRightComponent(libraryPanel);
        libraryPanel.setLayout(new BorderLayout(0, 0));

        JLabel libraryLabel = new JLabel(
                Messages.getString("BunmanFrame.libraryLabel.text")); //$NON-NLS-1$
        libraryPanel.add(libraryLabel, BorderLayout.NORTH);

        JScrollPane libraryScrollPane = new JScrollPane();
        libraryPanel.add(libraryScrollPane, BorderLayout.CENTER);
        JTable libraryTable = new JTable(libraryTableModel);
        libraryScrollPane.setViewportView(libraryTable);

        JTextArea textArea = new JTextArea();
        mainPane.setRightComponent(textArea);
        textArea.setEditable(false);
        textArea.setText("");
        
        
    }

    public void setPresenter(BunmanPresenter presenter) {
        this.presenter = presenter;
    }

    public void showLocationChooser(List<Location> defaultBackupLocations,
            List<Location> defaultLibraryLocations) {
        // TODO Auto-generated method stub
        presenter.scanLocations(null, null);
    }

    public void addBackupSong(Song song) {
        backupTableModel.addSong(song);
    }

    public void addLibrarySong(Song song) {
        libraryTableModel.addSong(song);
    }

    public void warn(String msg) {
        // TODO Auto-generated method stub

    }

    public void showInProgress() {
        // TODO Auto-generated method stub

    }

    public void hideInProgress() {
        // TODO Auto-generated method stub

    }

    public void setInProgress(int percentComplete, String msg) {
        // TODO Auto-generated method stub

    }

    public void showListToSync(List<Song> toSync) {
        // TODO Auto-generated method stub

    }
}
