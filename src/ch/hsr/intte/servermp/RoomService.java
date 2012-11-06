package ch.hsr.intte.servermp;

import java.util.Collection;
import java.util.Set;

import ch.hsr.intte.servermp.model.Room;

public class RoomService {

	private Set<Room> rooms;
	
	public RoomService() {
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
