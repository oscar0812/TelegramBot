package com.bittle.telegram.text;

import com.bittle.telegram.WebCrawler;
import org.jsoup.Jsoup;

public class AZLyrics {

    protected class Song {
        private String artist = "";
        private String song_name = "";
        private String lyrics = "";

        public Song(String a, String s, String l) {
            artist = capFirstLetter(a);
            song_name = capFirstLetter(s);
            lyrics = capFirstLetter(l);
        }

        public void setArtist(String a) {
            artist = "";
        }

        public void setLyrics(String l) {
            lyrics = l;
        }

        public void setSongName(String song) {
            song_name = song;
        }

        public String getArtist() {
            return artist;
        }

        public String getLyrics() {
            return lyrics;
        }
    }

    private Song currentSong;

    public AZLyrics(String o_artist, String o_songName) {
        final String BASE_URL = "http://www.azlyrics.com/lyrics/";
        String artist = o_artist.toLowerCase().replaceAll("\\s+", "");
        String songName = o_songName.toLowerCase().replaceAll("\\s+", "");
        String site = BASE_URL + artist + "/" + songName + ".html";
        try {
            WebCrawler crawler = new WebCrawler(site);
            String html = crawler.getHtml();

            String lyrics = extractLyricsFromHtml(html);

            if (lyrics.equals("error"))
                throw new Exception();
            else
                currentSong = new Song(o_artist, o_songName, lyrics);

        } catch (Exception ignored) {
        }
    }

    private String extractLyricsFromHtml(String html) {
        String str = "# search";
        try {
            html = html.substring(0, html.indexOf("MxM banner")) + "-->";
            html = html.trim().replaceAll("<br>", " enter_here ");
            String lyrics = Jsoup.parse(html).text();
            if (lyrics.toLowerCase().contains(str)) {
                lyrics = " " + lyrics.substring(lyrics.indexOf("enter_here")) + " ";
            }

            lyrics = lyrics.replaceAll(" enter_here ", "\n").replaceAll("enter_here", "\n");

            return lyrics.trim();
        } catch (Exception e) {
            return "error";
        }
    }

    public String getArtist() {
        return currentSong.artist;
    }

    public String getLyrics() {
        return currentSong.lyrics;
    }

    public String getSongName() {
        return currentSong.song_name;
    }

    private String capFirstLetter(String str) {
        str = str.toLowerCase().trim();
        return (str.charAt(0) + "").toUpperCase() + str.substring(1);
    }
}