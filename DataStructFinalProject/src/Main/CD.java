/**  
* Megan Mojeiko - mmojeiko  
* Last Updated: Dec 2, 2024  
*/
package Main;
import java.time.LocalDate;

public class CD {
    private String albumName;
    private String artist;
    private String genre;
    private LocalDate releaseDate;
    private LocalDate lastListenedDate;

    public CD(String albumName, String artist, String genre, LocalDate releaseDate, LocalDate lastListenedDate) {
        this.albumName = albumName;
        this.artist = artist;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.lastListenedDate = lastListenedDate;
    }

    // getters and setters
    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public LocalDate getLastListenedDate() {
        return lastListenedDate;
    }

    public void setLastListenedDate(LocalDate lastListenedDate) {
        this.lastListenedDate = lastListenedDate;
    }
    
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public String toString() {
        return "Album: " + albumName + ", Artist: " + artist + ", Genre: " + genre + ", Release Date: " + releaseDate + ", Last Listened: " + lastListenedDate;
    }
}
