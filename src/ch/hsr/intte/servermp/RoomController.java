package ch.hsr.intte.servermp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class RoomController {
	
	public RoomController() {
	}

	private String logout() {
		System.out.println("Session invalidiert");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(
				false);
		session.invalidate();
		return "lobby.xhtml";
	}

	public Integer getCount() {
//		System.out.println(users == null);
//		return users.size();
		return null;
	}
}
