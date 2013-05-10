package com.totalchange.bunman.jd7;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueSongListener;
import com.totalchange.bunman.Song;
import com.totalchange.bunman.cddb.CddbQuerier;
import com.totalchange.bunman.cddb.CddbResult;
import com.totalchange.bunman.util.AbstractSong;

public class Jd7CatalogueTests extends EasyMockSupport {
    private Song makeUnthanksSong(final int track, final String title) {
        return new AbstractSong() {
            public String getArtist() {
                return "Unthanks";
            }

            public String getAlbum() {
                return "Last";
            }

            public String getTitle() {
                return title;
            }

            public String getGenre() {
                return "Folk";
            }

            public int getYear() {
                return 2003;
            }

            public int getTrack() {
                return track;
            }

            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(new byte[] {});
            }
        };
    }

    private Song make10ccSong(final int track, final String title) {
        return new AbstractSong() {
            public String getArtist() {
                return "10cc";
            }

            public String getAlbum() {
                return "The Very Best Of 10cc";
            }

            public String getTitle() {
                return title;
            }

            public String getGenre() {
                return "Rock";
            }

            public int getYear() {
                return 1997;
            }

            public int getTrack() {
                return track;
            }

            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(new byte[] {});
            }
        };
    }

    @Test
    public void testListAllSongs() throws IOException {
        IdnFileAlbumCache mockCache = createMock(IdnFileAlbumCache.class);
        CddbQuerier mockQuerier = createMock(CddbQuerier.class);
        Catalogue catalogue = new Jd7Catalogue(mockCache, mockQuerier,
                new File("./src/test/jd7"));

        expect(mockCache.getFileFromCache("920BA70B", "Unthanks   Last"))
                .andReturn(null);
        mockCache.putFileIntoCache(eq("920BA70B"), eq("Unthanks   Last"),
                anyObject(IdnFileAlbum.class));
        mockQuerier.query(eq("920BA70B"), isA(CddbQuerier.Listener.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            public Object answer() throws Throwable {
                CddbQuerier.Listener listener = (CddbQuerier.Listener) getCurrentArguments()[1];
                List<CddbResult> cddbResults = new ArrayList<CddbResult>();
                cddbResults.add(new CddbResult() {
                    public String getDiscId() {
                        return "920ba70b";
                    }

                    public String getCategory() {
                        return "misc";
                    }

                    public String getTitle() {
                        return "Stephanie Dawn / Shine Jesus Shine";
                    }

                    public String getGenre() {
                        return "Rock";
                    }

                    public int getYear() {
                        return 1997;
                    }

                    public String[] getTrackNames() {
                        return new String[] { "All My Heart",
                                "Who Has Believed", "Willing to Wait",
                                "Sing Me A Lullaby", "Shine, Jesus, Shine",
                                "Everyone Is Differently Abled",
                                "Come Into My Life", "Operator",
                                "Say the Name", "Blessed Are You", "This Day" };
                    }

                    public String[] getExtendedTrackData() {
                        return new String[] {};
                    }

                    public String getExtendedData() {
                        return "";
                    }
                });

                cddbResults.add(new CddbResult() {
                    public String getDiscId() {
                        return "920ba70b";
                    }

                    public String getCategory() {
                        return "newage";
                    }

                    public String getTitle() {
                        return "Agnus Dei / Angelos";
                    }

                    public String getGenre() {
                        return "New Age";
                    }

                    public int getYear() {
                        return 2003;
                    }

                    public String[] getTrackNames() {
                        return new String[] { "MICHAEL", "ANAEL", "RADKIEL",
                                "URIEL", "ZADKIEL", "RAPHAEL", "EZECHIEL",
                                "METATRON", "NATHANAEL", "GABRIEL",
                                "GRIEF (For Hilde)" };
                    }

                    public String[] getExtendedTrackData() {
                        return new String[] {};
                    }

                    public String getExtendedData() {
                        return "";
                    }
                });

                cddbResults.add(new CddbResult() {
                    public String getDiscId() {
                        return "920ba70b";
                    }

                    public String getCategory() {
                        return "folk";
                    }

                    public String getTitle() {
                        return "Unthanks / Last";
                    }

                    public String getGenre() {
                        return "Folk";
                    }

                    public int getYear() {
                        return 2011;
                    }

                    public String[] getTrackNames() {
                        return new String[] { "Gan to the Kye",
                                "The Gallowgate Lad", "Queen of Hearts",
                                "Last", "Give Away Your Heart",
                                "No One Knows I'm Gone",
                                "My Laddie Sits Ower Late Up",
                                "Canny Hobbie Elliot", "Starless",
                                "Close the Coalhouse Door", "Last (reprise)" };
                    }

                    public String[] getExtendedTrackData() {
                        return new String[] {};
                    }

                    public String getExtendedData() {
                        return "";
                    }
                });

                cddbResults.add(new CddbResult() {
                    public String getDiscId() {
                        return "920ba70b";
                    }

                    public String getCategory() {
                        return "rock";
                    }

                    public String getTitle() {
                        return "????? / KING";
                    }

                    public String getGenre() {
                        return "Rock";
                    }

                    public int getYear() {
                        return 2003;
                    }

                    public String[] getTrackNames() {
                        return new String[] { "Baby ????", "WANTED",
                                "????????", "HB-2B-2H", "??", "????????",
                                "????", "?????", "?????", "????????", "??" };
                    }

                    public String[] getExtendedTrackData() {
                        return new String[] {};
                    }

                    public String getExtendedData() {
                        return "";
                    }
                });

                cddbResults.add(new CddbResult() {
                    public String getDiscId() {
                        return "920ba70b";
                    }

                    public String getCategory() {
                        return "data";
                    }

                    public String getTitle() {
                        return "Unthanks / Last";
                    }

                    public String getGenre() {
                        return "Folk";
                    }

                    public int getYear() {
                        return 2011;
                    }

                    public String[] getTrackNames() {
                        return new String[] { "Gan to the Kye",
                                "The Gallowgate Lad", "Queen of Hearts",
                                "Last", "Give Away Your Heart",
                                "No One Knows I'm Gone",
                                "My Laddie Sits Ower Late Up",
                                "Canny Hobbie Elliot", "Starless",
                                "Close the Coalhouse Door", "Last (reprise)" };
                    }

                    public String[] getExtendedTrackData() {
                        return new String[] {};
                    }

                    public String getExtendedData() {
                        return "";
                    }
                });

                listener.response(cddbResults);
                return null;
            }
        });
        mockQuerier.close();

        final List<Song> songs = new ArrayList<Song>();
        final List<String> skipped = new ArrayList<String>();

        replayAll();
        catalogue.listAllSongs(new CatalogueSongListener() {
            public void yetAnotherSong(Song song) {
                songs.add(song);
            }

            public void skippedSomething(String msg) {
                skipped.add(msg);
            }
        });
        verifyAll();

        List<Song> expectedSongs = new ArrayList<Song>();
        expectedSongs.add(make10ccSong(1, "Donna"));
        expectedSongs.add(make10ccSong(2, "Rubber Bullets"));
        expectedSongs.add(make10ccSong(3, "The Dean And I"));
        expectedSongs.add(make10ccSong(4, "The Wall Street Shuffle"));
        expectedSongs.add(make10ccSong(5, "Silly Love"));
        expectedSongs.add(make10ccSong(6, "Life Is A Minestrone"));
        expectedSongs.add(make10ccSong(7, "Une Nuit A Paris, Pt. 1: One "
                + "Night In Paris\\Pt. 2: The Same N"));
        expectedSongs.add(make10ccSong(8, "I'm Not In Love"));
        expectedSongs.add(make10ccSong(9, "Art For Art's Sake"));
        expectedSongs.add(make10ccSong(10, "I'm Mandy, Fly Me"));
        expectedSongs.add(make10ccSong(11, "The Things We Do For Love"));
        expectedSongs.add(make10ccSong(12, "Good Morning Judge"));
        expectedSongs.add(make10ccSong(13, "Dreadlock Holiday"));
        expectedSongs.add(make10ccSong(14, "People In Love"));
        expectedSongs.add(make10ccSong(15, "Under Your Thumb"));
        expectedSongs.add(make10ccSong(16, "Wedding Bells"));
        expectedSongs.add(make10ccSong(17, "Cry"));
        expectedSongs.add(make10ccSong(18, "Neanderthal Man"));

        expectedSongs.add(makeUnthanksSong(1, "Gan to the Kye"));
        expectedSongs.add(makeUnthanksSong(2, "The Gallowgate Lad"));
        expectedSongs.add(makeUnthanksSong(3, "Queen of Hearts"));
        expectedSongs.add(makeUnthanksSong(4, "Last"));
        expectedSongs.add(makeUnthanksSong(5, "Give Away Your Heart"));
        expectedSongs.add(makeUnthanksSong(6, "No One Knows I'm Gone"));
        expectedSongs.add(makeUnthanksSong(7, "My Laddie Sits Ower Late Up"));
        expectedSongs.add(makeUnthanksSong(8, "Canny Hobbie Elliot"));
        expectedSongs.add(makeUnthanksSong(9, "Starless"));
        expectedSongs.add(makeUnthanksSong(10, "Close the Coalhouse Door"));
        expectedSongs.add(makeUnthanksSong(11, "Last (reprise)"));

        Collections.sort(songs);

        assertEquals(new ArrayList<String>(), skipped);
        assertEquals(expectedSongs, songs);
    }
}
