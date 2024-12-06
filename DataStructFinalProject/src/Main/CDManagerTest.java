/**  
* Megan Mojeiko - mmojeiko  
* Last Updated: Dec 3, 2024  
*/
package Main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class CDManagerTest {
    
    private CD CD1;

    @BeforeEach
    public void setUp() {
        // CD simulation
        String album = "Album 1";
        String artist = "Artist 1";
        String genre = "Rock";
        LocalDate releaseDate = LocalDate.of(2023, 5, 20);
        LocalDate lastListened = LocalDate.of(2023, 12, 3);
        
        CD1 = new CD(album, artist, genre, releaseDate, lastListened);
    }

    @Test
    public void testAddCD() {
        assertEquals("Album: Album 1, Artist: Artist 1, Genre: Rock, Release Date: 2023-05-20, Last Listened: 2023-12-03", CD1.toString());
        
        CD1.setAlbumName("Greatest Hits");
        assertEquals("Greatest Hits", CD1.getAlbumName());
        assertEquals("Album: Greatest Hits, Artist: Artist 1, Genre: Rock, Release Date: 2023-05-20, Last Listened: 2023-12-03", CD1.toString());
    }

}