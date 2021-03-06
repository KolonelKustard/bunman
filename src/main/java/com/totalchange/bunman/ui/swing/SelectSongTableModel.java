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

import java.util.ArrayList;
import java.util.List;

import com.totalchange.bunman.Song;

class SelectSongTableModel extends SongTableModel {
    private List<Boolean> checked;

    SelectSongTableModel(List<Song> songs) {
        super(songs);

        checked = new ArrayList<Boolean>(songs.size());
        for (int num = 0; num < songs.size(); num++) {
            checked.add(true);
        }
    }

    @Override
    public int getColumnCount() {
        return super.getColumnCount() + 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "";
        } else {
            return super.getColumnName(columnIndex - 1);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Boolean.class;
        } else {
            return super.getColumnClass(columnIndex - 1);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return true;
        } else {
            return super.isCellEditable(rowIndex, columnIndex - 1);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return checked.get(rowIndex);
        } else {
            return super.getValueAt(rowIndex, columnIndex - 1);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            if (aValue instanceof Boolean) {
                checked.set(rowIndex, (Boolean) aValue);
            }
        } else {
            super.setValueAt(aValue, rowIndex, columnIndex - 1);
        }
    }

    @Override
    void addSong(Song song) {
        checked.add(true);
        super.addSong(song);
    }

    List<Song> getSelectedSongs() {
        List<Song> sel = new ArrayList<Song>(songs.size());
        for (int num = 0; num < checked.size(); num++) {
            if (checked.get(num)) {
                sel.add(songs.get(num));
            }
        }
        return sel;
    }
}
