package ch.hsr.intte.servermp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import ch.hsr.intte.servermp.model.Room;

public class RoomManager {

	private Set<Room> rooms;
	
	public RoomManager() {
		initialize();
	}

	private void initialize() {
		try {
			rooms = new TreeSet<Room>();
			File file = new File("room-list");
			file.createNewFile();

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				rooms.add(new Room(line));
				line = reader.readLine();
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isRoomnameUnique(String name) {
		return false;
	}
	
	public Room createRoom(String name) {
		return null;
	}
	
	public boolean deleteRoom(String name) {
		return false;
	}
	
	public Room getRoom(String room) {
		return null;
	}
	
	public Collection<Room> getAllRooms() {
		return null;
	}
}
