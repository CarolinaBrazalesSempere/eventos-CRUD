package eventos.modelo.controlador;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.TipoDao;
import eventos.modelo.javabean.Evento;



@Controller
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired
	private EventoDao edao;
	
	@Autowired
	private TipoDao tdao;
	
	/**
	 * Controlador que maneja las solicitudes GET a la ruta "/alta".
	 *
	 * Este método crea un modelo con un objeto Evento y una lista de tipos de eventos
	 * y después redirige a la vista "loginForm".
	 *
	 * @param model El modelo de datos que se utilizará en la vista.
	 * @return La vista "loginForm" que mostrará el formulario de alta de eventos.
	 */
	
	@GetMapping("/alta")
	public String loginForm(Model model) {
		model.addAttribute("evento", new Evento());
		model.addAttribute("tipo", tdao.findAll());
		
		return "loginForm";
	}
	
	/**
	 * Controlador que maneja las solicitudes POST a la ruta "/alta".
	 *
	 * Este método se encarga de procesar un formulario enviado por el usuario para dar de alta un evento. 
	 * La información del evento se recibe a través del objeto "evento" y se almacena en la base de datos. 
	 * 
	 * Luego, mostramos un mensaje flash al objeto "ratt" para informar al usuario
	 * sobre el resultado de la operación. 
	 * 
	 * Finalmente, redirigimos al usuario a la página de inicio ("/").
	 *
	 * @param evento El objeto Evento que contiene los datos del evento para dar de alta.
	 * @param ratt El objeto RedirectAttributes para enviar mensajes flash.
	 * @return Redirige al usuario a la página de inicio con un mensaje flash.
	 */
	
	//Dar de alta un nuevo evento: Create
	@PostMapping("/alta")
	public String processForm(Evento evento, RedirectAttributes ratt) {
		
		if (edao.insert(evento) == 1) {// se puede omitir el if...else y usar solo el método insert
			ratt.addFlashAttribute("mensaje", "Alta realizada con éxito");
			
		}else
			ratt.addFlashAttribute("mensaje", "Algo ha ido mal");
		
		return "redirect:/";
		
	}
	
	/**
	 * Controlador que maneja las solicitudes GET para ver los detalles de un evento.
	 *
	 * Este método recibe como parámetro el id de un evento ("idEvento") a través de path variable
	 * y utiliza el objeto "edao" para buscar y recuperar la información detallada de ese evento. 
	 * Finalmente se añaden los datos del evento al modelo para que puedan ser mostrados en la vista "detalle".
	 * 
	 * @param idEvento El id del evento que queremos ver.
	 * @param model El modelo de datos que se utilizará en la vista.
	 * @return La vista "detalle" que muestra los detalles del evento.
	 */
	
	//Mostrar en un HTML los datos del evento: Read
	@GetMapping("/detalle/{id}")
    public String verEventos(@PathVariable("id") int idEvento, Model model) {
		Evento evento = edao.findById(idEvento);
		
			model.addAttribute("evento", evento);
			return "detalle";
		}
		
	/**
	 * Controlador que maneja las solicitudes GET para eliminar un evento.
	 *
	 * Este método recibe como parámetro el "idEvento" a través de path variable.
	 * Utiliza el objeto "edao" para  eliminar el evento con el id proporcionado.
	 * Se muestra un mensaje flash con "ratt" para informar al usuario sobre el resultado de la operación.
	 * Finalmente, se redirige al usuario a la página de inicio ("/").
	 * 
	 * @param idEvento El identificador único del evento que se desea eliminar.
	 * @param ratt El objeto RedirectAttributes utilizado para enviar mensajes flash.
	 * @return Redirige al usuario a la página de inicio con un mensaje flash.
	 */
	
	//Borrar evento: Delete
	@GetMapping("/eliminar/{id}")//el id procede del id asignado a cada evento en la home con el href
	public String eliminarEvento(@PathVariable("id") int idEvento, RedirectAttributes ratt) {
		
		if (edao.delete(idEvento) == 1)
	        ratt.addFlashAttribute("mensaje", "evento eliminado");
		else
	        ratt.addFlashAttribute("mensaje", "evento no eliminado");
		
		return "redirect:/"; // ponemos redirect porque con forward elimina los estilos
		
	}
	
	
	/**
	 * Controlador que maneja las solicitudes GET para editar un evento existente.
	 *
	 * Este método recibe como parámetro un "idEvento" a través de path variable.
	 * 
	 * Con "edao" se busca y se recupera la información del evento que queremos editar.
	 * 
	 * Luego, prepara un modelo de datos con un objeto Evento y una lista de tipos de eventos que se
	 * utilizan en la vista "editarForm". Si el evento existe, agrega sus datos al modelo y redirige
	 * al usuario a la vista "editarForm" para permitir la edición. 
	 * 
	 * Si el evento no existe, mostramos un
	 * mensaje de error y redirigimos al usuario a la página de inicio ("/").
	 * 
	 * @param idEvento El id del evento para editar.
	 * @param model El modelo de datos que se utilizará en la vista.
	 * @return La vista "editarForm" para editar el evento si existe, o redirección a la página de inicio
	 * con un mensaje de error si el evento no existe.
	 */
	
	//Modificar un evento: Update
	@GetMapping("/editar/{id}")
	public String editarEvento(@PathVariable("id") int idEvento, Model model) {
		model.addAttribute("evento", new Evento());// se puede omitir
		model.addAttribute("tipo", tdao.findAll());
		Evento evento = edao.findById(idEvento);
		
		if (evento != null) {
			model.addAttribute("evento", evento);
			return "editarForm";
		}
		else {
			model.addAttribute("mensaje", "Este cliente no existe");
			return "forward:/";
		}
		
	}
	
	/**
	 * Controlador que maneja las solicitudes POST para procesar la edición de un evento existente.
	 *
	 * Este método recibe como parámetro un "idEvento" a través de path variable y 
	 * un objeto "evento" que contiene los datos editados del evento. 
	 *
	 *  
	 * Después de realizar la actualización, mostramos un mensaje flash al objeto "ratt" para informar 
	 * al usuario sobre el resultado de la operación. 
	 * 
	 * Finalmente, se redirige al usuario a la página de inicio ("/").
	 * 
	 * @param idEvento El identificador único del evento que se está editando.
	 * @param evento El objeto "Evento" con los datos editados del evento.
	 * @param ratt El objeto RedirectAttributes utilizado para enviar mensajes flash.
	 * @return Redirige al usuario a la página de inicio con un mensaje flash 
	 * que indica si la edición se realizó con éxito o no.
	 */
	
	@PostMapping("/editar/{id}")
	public String actualizarEvento(@PathVariable("id") int idEvento, Evento evento,
			RedirectAttributes ratt) {
		
		evento.setIdEvento(idEvento);//Establecer el identificador en el objeto Evento con el valor proporcionado en la variable idEvento
		
		if (edao.update(evento) == 1)
			ratt.addFlashAttribute("mensaje", "se ha modficado el evento " + evento.getNombre());
		
		else
			ratt.addFlashAttribute("mensaje", "No se ha modificado el evento");
		
		return "redirect:/";
	}
	
	/**
	 * Controlador que maneja las peticiones GET para cancelar un evento
	 * 
	 * Recibimos por path variable el id del evento, lo buscamos en la lista y si el evento existe
	 * le cambiamos el estado a cancelado
	 * 
	 * 
	 * @param idEvento que queremos cancelar
	 * @param ratt mensaje para informar al usuario del éxito o no de la operación
	 * @return / para redirigir al usuario a la página de inicio
	 */
	
	
	@GetMapping("/cancelar/{id}")
	public String cancelarEvento(@PathVariable("id") int idEvento, RedirectAttributes ratt) {
		Evento evento = edao.findById(idEvento);
		if (evento != null) {
	        evento.setEstado("Cancelado"); // Establece el estado del evento como "Cancelado" 
	        ratt.addFlashAttribute("mensaje", "se ha cancelado el evento " + evento.getNombre());
	        System.out.println(evento);
	    } else {
	        ratt.addFlashAttribute("mensaje", "No se ha encontrado el evento");
	    }

	    return "redirect:/";
	}
	
	/**
	 * Controlador que maneja las peticiones GET para reactivar un evento
	 * 
	 * Recibimos por path variable el id del evento, lo buscamos en la lista y si el evento existe
	 * le cambiamos el estado a activo
	 * 
	 * 
	 * @param idEvento que queremos reactivar
	 * @param ratt mensaje para informar al usuario del éxito o no de la operación
	 * @return / para redirigir al usuario a la página de inicio
	 */
	
	@GetMapping("/activar/{id}")
	public String reactivarEvento(@PathVariable("id") int idEvento, RedirectAttributes ratt) {
		Evento evento = edao.findById(idEvento);
		if (evento != null) {
	        evento.setEstado("Activo"); // Establece el estado del evento como "Activo" 
	        ratt.addFlashAttribute("mensaje", "se ha reactivado el evento " + evento.getNombre());
	        System.out.println(evento);
	    } else {
	        ratt.addFlashAttribute("mensaje", "No se ha encontrado el evento");
	    }

	    return "redirect:/";
	}
	
	
	//Para formatear la fecha que nos introducen en el form
	@InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
