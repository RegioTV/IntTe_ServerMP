package ch.hsr.intte.servermp.service;

import ch.hsr.intte.servermp.model.Room;

public class RoomService extends AbstractService<Room> {

	private static final String ROOM_FILE = "rooms";

	private static RoomService instance;

	public static RoomService getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new RoomService(ROOM_FILE);
	}

	private RoomService(String filename) {
		super(filename);
	}

	@Override
	String getId(Room room) {
		return room.getName();
	}

	@Override
	String convert(Room room) {
		return room.getName();
	}

	@Override
	Room convert(String line) {
		return new Room(line);
	}

}
