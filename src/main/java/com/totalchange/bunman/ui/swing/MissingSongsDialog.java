package com.totalchange.bunman.ui.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.totalchange.bunman.Song;

public class MissingSongsDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private SongTableModel songTableModel;

    /**
     * Create the dialog.
     */
    public MissingSongsDialog(JFrame owner, List<Song> songs) {
        super(owner, true);

        setBounds(100, 100, 450, 300);

        JPanel contentPanel = new JPanel();
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        this.songTableModel = new SongTableModel(songs);
        JTable table = new JTable(songTableModel);
        table.setAutoCreateRowSorter(true);
        scrollPane.setViewportView(table);

        JTextArea textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        contentPanel.add(textArea, BorderLayout.NORTH);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }
}
