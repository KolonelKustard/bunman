package com.totalchange.bunman.ui.impl;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.totalchange.bunman.Song;

final class SongTableModel implements TableModel {
    private static final int COL_ARTIST = 0;
    private static final int COL_ALBUM = 1;
    private static final int COL_TRACK = 2;
    private static final int COL_TITLE = 3;

    private static final Class<?>[] COLUMN_TYPES = new Class[] { String.class,
            String.class, Integer.class, String.class };

    private List<Song> songs;
    private List<TableModelListener> listeners = new ArrayList<TableModelListener>();

    SongTableModel() {
        this.songs = new ArrayList<Song>();
    }

    SongTableModel(List<Song> songs) {
        this.songs = songs;
    }

    public int getRowCount() {
        return songs.size();
    }

    public int getColumnCount() {
        return COLUMN_TYPES.length;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case COL_ARTIST:
            return Messages.getString("SongTableModel.columnName.artist");
        case COL_ALBUM:
            return Messages.getString("SongTableModel.columnName.album");
        case COL_TRACK:
            return Messages.getString("SongTableModel.columnName.track");
        case COL_TITLE:
            return Messages.getString("SongTableModel.columnName.title");
        default:
            return "???";
        }
    }

    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case COL_ARTIST:
            return songs.get(rowIndex).getArtist();
        case COL_ALBUM:
            return songs.get(rowIndex).getAlbum();
        case COL_TRACK:
            return songs.get(rowIndex).getTrack();
        case COL_TITLE:
            return songs.get(rowIndex).getTitle();
        default:
            return "???";
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // Not implemented
    }

    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    void addSong(Song song) {
        songs.add(song);
        TableModelEvent ev = new TableModelEvent(this, songs.size() - 1);
        for (TableModelListener listener : listeners) {
            listener.tableChanged(ev);
        }
    }
}
