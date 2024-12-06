/**  
* Megan Mojeiko - mmojeiko  
* Last Updated: Dec 2, 2024  
*/
package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.ImageIcon;

public class CDManagerGUI extends JFrame {
    private JTextField albumField, artistField, genreField;
    private JTextField releaseDateField, lastListenedField;
    private JButton addButton, displayButton, sortButton, editButton, recButton;
    private JList<CD> cdList;
    private DefaultListModel<CD> listModel;
    private List<CD> cdCollection;
    private PriorityQueue<CD> pq;		// priority queue

    public CDManagerGUI() {
        // frame
        setTitle("CD Manager");
        setSize(700, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); 	// centers window
        
        // icon top left
        ImageIcon logo = new ImageIcon(".//res//iconVector.png");;
        setIconImage(logo.getImage());

        // panel for form's inputs
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Album Name:"));
        albumField = new JTextField(20);
        formPanel.add(albumField);

        formPanel.add(new JLabel("Artist:"));
        artistField = new JTextField(20);
        formPanel.add(artistField);

        formPanel.add(new JLabel("Genre:"));
        genreField = new JTextField(20);
        formPanel.add(genreField);

        formPanel.add(new JLabel("Release Date (YYYY-MM-DD):"));
        releaseDateField = new JTextField(10);
        formPanel.add(releaseDateField);

        formPanel.add(new JLabel("Last Listened Date (YYYY-MM-DD):"));
        lastListenedField = new JTextField(10);
        formPanel.add(lastListenedField);

        // adding form panel to the frame
        add(formPanel, BorderLayout.NORTH);

        // button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        // add button
        addButton = new JButton("Add CD");
        // display button
       // displayButton = new JButton("Display CDs");
        // sort CDs button
        sortButton = new JButton("Sort CDs");
        // adding button panels
        buttonPanel.add(addButton);
        //buttonPanel.add(displayButton);
        buttonPanel.add(sortButton);
        // combo box options
        String[] sortOptions = {"By Last Listened Date", "By Release Date", "By Artist", "By Genre"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        buttonPanel.add(sortComboBox);
        // edit button
        editButton = new JButton("Update Selected CD");
        buttonPanel.add(editButton);
        
        recButton = new JButton("Recommend a CD");
        buttonPanel.add(recButton);
        
        // adding button panel to the frame
        add(buttonPanel, BorderLayout.CENTER);

        // CD list panel
        listModel = new DefaultListModel<>();
        cdList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(cdList); // scroll bar
        JPanel listPanel = new JPanel(new BorderLayout());
        // title for CD collection
        JLabel listTitle = new JLabel("CD Collection:", SwingConstants.CENTER);
        listTitle.setFont(new Font("Arial", Font.BOLD, 16));
        listPanel.add(listTitle, BorderLayout.NORTH);
        listPanel.add(listScrollPane, BorderLayout.CENTER);
        
        

        // adding list to the frame
        add(listPanel, BorderLayout.SOUTH);

        // initializing collection and priority queue
        cdCollection = new ArrayList<>();
        pq = new PriorityQueue<>(new LastListenedComparator());

        
        // button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCD();
            }
        });
        
        // edit button actions
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editCD();
            }
        });

        /**
        // display button actions
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCDs();
            }
        });
        **/

        sortButton.addActionListener(e -> {
            String selectedOption = (String) sortComboBox.getSelectedItem();
            sortCDs(selectedOption);
        });
        
        // recommend button actions
        recButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recCD();
            }
        });
    }
    
    // METHODS

    // add CD method
    public void addCD() {
        try {
            String album = albumField.getText();
            String artist = artistField.getText();
            String genre = genreField.getText();
            String releaseDateStr = releaseDateField.getText().trim();
            String lastListenedStr = lastListenedField.getText().trim();

            // validating fields
            if (album.isEmpty() || artist.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Album, Artist, and Genre cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // validating date format
            if (!isValidDate(releaseDateStr) || !isValidDate(lastListenedStr)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // parsing dates
            LocalDate releaseDate = LocalDate.parse(releaseDateStr);
            LocalDate lastListened = LocalDate.parse(lastListenedStr);
            LocalDate today = LocalDate.now();
            

            // check for future release date
            if (releaseDate.isAfter(today)) {
                JOptionPane.showMessageDialog(this, "Release date cannot be in the future. Please enter a valid date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // check for future last listened date
            if (lastListened.isAfter(today)) {
                JOptionPane.showMessageDialog(this, "Last listened date cannot be in the future. Please enter a valid date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // creating and adding the CD
            CD cd = new CD(album, artist, genre, releaseDate, lastListened);
            cdCollection.add(cd);
            pq.offer(cd); // adding to priority queue
            JOptionPane.showMessageDialog(this, "CD added successfully!"); // pop up for "CD added successfully"

        } catch (Exception e) {
            // for unexpected errors
            JOptionPane.showMessageDialog(this, "An unexpected error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        displayCDs();
    }
    
    // checking for valid date method
    private boolean isValidDate(String dateStr) {
        try {
            // tries to parse the date
            LocalDate.parse(dateStr);
            return true;
        } catch (Exception e) {
            // if parsing fails, date is invalid
            return false;
        }
    }
    
    // display CDs method
    private void displayCDs() {
        listModel.clear();
        for (CD cd : cdCollection) {
            listModel.addElement(cd);
        }
    }

    // sort CDs method
    private void sortCDs(String criteria) {
        List<CD> sortedCDs = new ArrayList<>(cdCollection);

        switch (criteria) {
            case "By Last Listened Date":
                Collections.sort(sortedCDs, new LastListenedComparator());
                break;
            case "By Release Date":
                sortedCDs.sort(Comparator.comparing(CD::getReleaseDate));
                break;
            case "By Artist":
                sortedCDs.sort(Comparator.comparing(CD::getArtist));
                break;
            case "By Genre":
                sortedCDs.sort(Comparator.comparing(CD::getGenre));
                break;
        }

        listModel.clear();
        for (CD cd : sortedCDs) {
            listModel.addElement(cd);
        }
    }
    
    // edit CD method
    private void editCD() {
        // getting CD from the list
        int selectedIndex = cdList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "No CD selected for editing.");
            return;
        }

        // getting CD object from the list model
        CD selectedCD = listModel.get(selectedIndex);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // asking user to confirm edits
        int response = JOptionPane.showConfirmDialog(this, 
            "Update selected CD with what's in the textboxes?",
            "Edit CD",
            JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            try {
                // updating the CD's data
                String updatedAlbum = albumField.getText();
                String updatedArtist = artistField.getText();
                String updatedGenre = genreField.getText();
                LocalDate updatedReleaseDate = LocalDate.parse(releaseDateField.getText(), formatter);
                LocalDate updatedLastListened = LocalDate.parse(lastListenedField.getText(), formatter);

                selectedCD.setAlbumName(updatedAlbum);
                selectedCD.setArtist(updatedArtist);
                selectedCD.setGenre(updatedGenre);
                selectedCD.setReleaseDate(updatedReleaseDate);
                selectedCD.setLastListenedDate(updatedLastListened);

                // updating the list model
                listModel.set(selectedIndex, selectedCD);

                JOptionPane.showMessageDialog(this, "CD updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your dates.");
            }
        }
    }
    
    // recommend CD method
    private void recCD() {
        if (pq.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No CDs in the collection. Please add some CDs first.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            CD recommendedCD = pq.peek(); // gets the top CD without removing it
            String message = "Give this a listen: '" + recommendedCD.getAlbumName() + "' by " + recommendedCD.getArtist();
            JOptionPane.showMessageDialog(this, message);
        }
    }

    public List<CD> getCdCollection() {
        return cdCollection;
    }
    
    // MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CDManagerGUI().setVisible(true);
            }
        });
    }
}