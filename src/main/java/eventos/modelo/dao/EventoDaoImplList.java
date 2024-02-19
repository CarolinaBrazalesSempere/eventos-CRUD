package eventos.modelo.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import eventos.modelo.javabean.Evento;


@Repository 
public class EventoDaoImplList implements EventoDao {
	
	private List<Evento> lista;
	private TipoDao tdao;
	private static int idAuto;
	
	static {
		idAuto = 0;
	}
	
	public EventoDaoImplList() throws ParseException {
		lista = new ArrayList<>();
		cargarLista();
	}
	
	public void cargarLista() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		tdao = new TipoDaoImplList();
		lista.add(new Evento(1, 
				"Festival de les Arts", 
				"Festival de música indie en València", 
				dateFormat.parse("10/06/2024"), 
				2, 
				"Ciutat de les Arts", 
				"Activo", 
				"Sí", 
				10000, 
				1000, 
				45, 
				tdao.findById(1)));
		
		lista.add(new Evento(2, 
				"Concierto de Els Manel", 
				"Actuación musical del grupo catalán Manel", 
				dateFormat.parse("15/10/2023"), 
				1, 
				"Sala Moon",
				"Activo",
				"Sí",
				1000,
				100,
				35.50,
				tdao.findById(1)));
		
		lista.add(new Evento(3,
				"La obra de Artemisa Gentilleschi",
				"Exposición de cuadros de época barroca de la artista italiana Artemisa Gentilleschi",
				dateFormat.parse("25/4/2024"),
				7,
				"Museu de Belles Arts de València",
				"Activo",
				"Sí",
				800,
				50,
				25.50,
				tdao.findById(5)));
		
		lista.add(new Evento(4, 
            "Subida al Benicadell",
            "Excursión de senderismo en la Vall d'Albaida",
            dateFormat.parse("24/07/2024"), 
            1, 
            "Agres, Comunidad Valenciana",
            "Cancelado", 
            "Sí", 
            50, 
            10, 
            10.5, 
            tdao.findById(2)));
		
		
		idAuto = 4; //Para asignarle un id nuevo al evento que se cree
		
	}

	/**
	 * Busca y devuelve un objeto de tipo Evento por su identificador en la lista.
	 *
	 * @param idEvento El id del evento que buscamos.
	 * @return El objeto de tipo Evento con el id especificado, o null si no se encuentra en la lista.
	 */
	
	@Override
	public Evento findById(int idEvento) {
		for(int i =0; i < lista.size(); i++) {
			if(lista.get(i).getIdEvento() == idEvento) {
				return lista.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Recupera una lista de todos los objetos de tipo Evento en la lista.
	 *
	 * @return Una lista que contiene todos los objetos de tipo Evento en la lista.
	 */
	@Override
	public List<Evento> findAll() {
		return lista;
	}
	
	/**
	 * Añade un objeto de tipo Evento que le pasemos como argumento a la lista
	 * 
	 * @param evento un objeto de tipo Evento que queremos insertar en la lista
	 * @return 1, número entero si lo ha añadadido con éxito y 
	 * 0 si no lo añade porque dicho objeto Evento ya existe en la lista
	 */
	@Override
	public int insert(Evento evento) {
		if(!lista.contains(evento)) {
			evento.setIdEvento(++idAuto);
			lista.add(evento);
			return 1;
		}
		return 0;
	}
	
	/**
	 * Elimina un objeto Evento de la lista según su id.
	 *
	 * @param idEvento El identificador del evento que se quiere eliminar.
	 * @return 1 si el evento se encontró y se eliminó con éxito, 
	 * o 0 si el evento no se encontró en la lista y no se eliminó.
	 */
	@Override
	public int delete(int idEvento) {
		Evento evento = findById(idEvento); // creamos una variable de  tipo Evento para que al buscar por ID nos elimine todo el objeto con findById
		
		return lista.remove(evento) ? 1 : 0;
	}
	
	/**
	 * Actualiza un objeto Evento en la lista.
	 *
	 * Este método busca el objeto Evento especificado en la lista y, si lo encuentra,
	 * lo reemplaza con el nuevo objeto Evento proporcionado. Si el objeto Evento no
	 * se encuentra en la lista, no se realiza ninguna actualización.
	 *
	 * @param evento El objeto Evento que se desea actualizar en la lista.
	 * @return 0 si la actualización se realizó con éxito, o -1 si el objeto Evento
	 *         no se encontró en la lista y no se realizó ninguna actualización.
	 */

	@Override
	public int update(Evento evento) {
		int ePos = lista.indexOf(evento);
		if(ePos == -1) {
			return 0;
		}
		lista.set(ePos, evento);//método encargado de reemplazar un objeto por otro, es un método de List
		return 1;
	}

	/**
	 * Recupera una lista de eventos activos.
	 *
	 * @return Una lista de objetos Evento con la propiedad "estado" establecida como "Activo".
	 */
	
	@Override
	public List<Evento> findByActive() {
		return lista
				.stream()
				.filter(eventos -> eventos.getEstado().equals("Activo"))
				.toList();
	}

}
