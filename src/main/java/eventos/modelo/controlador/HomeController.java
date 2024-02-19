package eventos.modelo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.TipoDao;
import eventos.modelo.javabean.Evento;

@Controller
public class HomeController {
	@Autowired
	private EventoDao edao;
	
	
	/**
	 * Controlador que maneja las peticiones GET del usuario para la home
	 * 
	 * Devolvemos la lista de todos los eventos activos
	 * @param model
	 * @return el html encargado de mostrar la vista "home"
	 */
	
	@GetMapping("/")
	public String getHome(Model model) {
		model.addAttribute("evento", edao.findAll());
		
		return "home";
	}

}
