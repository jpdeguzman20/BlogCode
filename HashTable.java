import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class HashTable {
	private Video[] video_inventory;
	private int inventory_size;

	/**
	 * Constructor that initializes the inventory size and allocates the inventory array
	 *
	 * @param inventory_size		the number of movies that we have in our inventory
	 */
	public HashTable (int inventory_size) {
		this.inventory_size = inventory_size;
		video_inventory = new Video[inventory_size];
	}

	/**
	 * Hash function for String type keys
	 *
	 * @param title 		the key that we use for lookup
	 * @return hash_code	integer value to be used in indexing the movie
	 */
	public int hashFunction (String title) {
		String make_hash = new String(title);

		// Be careful here. After a little debugging, I found out that the hashCode method can return a negative 
		// value. If it does, you'll get a NullPointerException, which means that you're trying to assign a movie to
		// an index in the array that does not exist. Use the absolute value method in the Math library to fix this.
		int hash_code = abs(make_hash.hashCode()) % inventory_size; 

		return hash_code;
	}

	/**
	 * Retrieve a movie based on key 
	 *
	 * @param title 				key to help us look for the movie we want
	 * @return copies_available 	when calling get, user expects the number of copies available to be returned
	 */
	public int get (String title) {
		int hash_code = hashFunction(title);

		// When you try to put movie A into a bucket that already has another movie, movie B, this code makes it 
		// so that you try to put movie A into the next available bucket over. Movie B remains in the same bucket
		// it was first put in. This technique is called open-address hashing. Remember, this technique is just 
		// one of the ways to handle collisions like this. 
		while (video_inventory[hash_code] != null && !(title.equals(video_inventory[hash_code].title))) {
			hash_code = hash_code + 1;
		}

		return (video_inventory[hash_code] != null) ? video_inventory[hash_code].copies_available : -1;
	}

	/**
	 * Function to put the movie data and key into the table
	 *
	 * @param title 		key for the new movie to be added
	 * @param new_movie 	newly added movie to the hash table
	 */
	public void put (String title, int copies_available) {
		int hash_code = hashFunction(title);
		
		while (video_inventory[hash_code] != null && !(title.equals(video_inventory[hash_code].title))) {
			hash_code = hash_code + 1;
		}

		video_inventory[hash_code] = new Video(title, copies_available);
	}

	/**
	 * Function to display the contents of our hash table
	 */
	public void displayInfo() {
		for (int i = 0; i < inventory_size; i++) {
			if (video_inventory[i] == null) {
				System.out.println("Index " + i + " is null");
				System.out.println();
			} else {
				System.out.print("Index " + i + ":");
				video_inventory[i].display();
			}
		}
	}

	public static void main (String[] args) {
		HashTable inventory_hash_table = new HashTable(31);
		inventory_hash_table.put("Deadpool", 0);
		inventory_hash_table.put("Zootopia", 4);
		inventory_hash_table.put("Captain America: Civil War", 2);
		inventory_hash_table.put("Batman v Superman: Dawn of Justice", 20);
		inventory_hash_table.put("The Nice Guys", 9);
		inventory_hash_table.put("The Jungle Book", 8);
		inventory_hash_table.put("Star Wars: The Force Awakens", 1);
		inventory_hash_table.put("Ex Machina", 5);
		inventory_hash_table.put("Inside Out", 6);
		inventory_hash_table.put("The Martian", 3);
		inventory_hash_table.put("Mad Max: Fury Road", 7);
		inventory_hash_table.put("The Revenant", 2);
		inventory_hash_table.put("Finding Dory", 4);
		inventory_hash_table.put("Ghostbusters", 10);
		inventory_hash_table.put("X-Mean: Apocalypse", 5);

		inventory_hash_table.displayInfo();

		Scanner reader = new Scanner(System.in);
		System.out.print("Please enter a movie title: ");
		String find_movie_title = reader.next();

		System.out.println("Amount available: " + inventory_hash_table.get(find_movie_title));
	} 
}


class Video {
	public String title;
	public int copies_available;

	/**
	 * This is the constructor for our video class.
	 * <p>
	 * Remember that our manager only wants to know two things about the movies we're going to be 
     * looking at: 1. the title of the movie and 2. the amount of copies the store has in stock, both of 
     * which we have given to our video class below. Since our Video class is internal, I'm opting to 
     * not use getters and setters here. 
     * <p>
	 *
	 * @param title 				The title of the movie that we want information for
	 * @param copies_available		The number of copies available in the store
	 */
	Video (String title, int copies_available) {
		this.title = title;
		this.copies_available = copies_available;
	}

	void display() {
		System.out.println("Movie title: " + title);
		System.out.println("Copies available: " + copies_available);
		System.out.println();
	}
}





