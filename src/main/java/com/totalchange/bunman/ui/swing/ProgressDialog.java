package com.totalchange.bunman.ui.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

class ProgressDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JProgressBar progressBar;
    private JLabel progressLabel;

    public ProgressDialog(JFrame owner) {
        super(owner);

        setBounds(100, 100, 423, 132);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(2, 1, 0, 0));

        progressLabel = new JLabel("");
        contentPanel.add(progressLabel);

        progressBar = new JProgressBar();
        contentPanel.add(progressBar);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        getRootPane().setDefaultButton(cancelButton);
    }

    void setProgress(int percentComplete, String msg) {
        if (percentComplete != progressBar.getValue()) {
            progressBar.setValue(percentComplete);
        }

        if (msg != null && !msg.equals(progressLabel.getText())) {
            progressLabel.setText(msg);
        }
    }
}
