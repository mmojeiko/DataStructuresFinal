/**  
* Megan Mojeiko - mmojeiko  
* Last Updated: Dec 2, 2024  
*/
package Main;
import java.util.Comparator;


public class LastListenedComparator implements Comparator<CD> {
    @Override
    public int compare(CD cd1, CD cd2) {
        return cd1.getLastListenedDate().compareTo(cd2.getLastListenedDate());
    }
}