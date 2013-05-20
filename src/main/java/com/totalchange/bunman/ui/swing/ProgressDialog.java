/*
 * Copyright 2013 Ralph Jones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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

import com.totalchange.bunman.ui.BunmanPresenter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ProgressDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private BunmanPresenter presenter;
    private JProgressBar progressBar;
    private JLabel progressLabel;

    public ProgressDialog(JFrame owner) {
        super(owner);
        setAlwaysOnTop(true);
        setBounds(100, 100, 423, 132);
        setLocationRelativeTo(owner);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presenter.cancelInProgress();
            }
        });

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
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                presenter.cancelInProgress();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        getRootPane().setDefaultButton(cancelButton);
    }

    void setPresenter(BunmanPresenter presenter) {
        this.presenter = presenter;
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
