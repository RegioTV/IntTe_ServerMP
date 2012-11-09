package ch.hsr.intte.servermp.model;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import ch.hsr.intte.servermp.service.RoomService;

public class RoomConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		String roomId = value;
		// TODO: need Roooooommmsss :)
//		Room room = RoomService.getInstance().findById(roomId);
		Room room = new Room(roomId);
		return room;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		String roomId = ((Room)value).getName();
		return roomId;
	}

}
