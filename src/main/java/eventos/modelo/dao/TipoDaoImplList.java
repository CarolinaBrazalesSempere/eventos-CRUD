package eventos.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import eventos.modelo.javabean.Tipo;

@Repository 
public class TipoDaoImplList implements TipoDao {
	
	private List<Tipo> lista;
	private static int idAuto;
	
	static {
		idAuto = 0;
	}
	
	public TipoDaoImplList() {
		
		lista = new ArrayList<>();
		cargarLista();
			
	}
	
	public void cargarLista() {
		lista.add(new Tipo(1, "Concierto", "Festival de música indie, pop y rock"));
		lista.add(new Tipo(2, "Senderismo", "Rutas por la montaña"));
		lista.add(new Tipo(3, "Idiomas", "Tándems lingüísticos para mejorar diferentes lenguas"));
		lista.add(new Tipo(4, "Cumpleaños", "Celebraciones de fiestas para cumpleaños"));
		lista.add(new Tipo(5, "Exposiciones", "Visitas a museos, edificios y sitios históricos"));
		idAuto= 6;
		
	}
	
	/**
	 * Devuelve todos los objetos de tipo Tipo de la lista
	 * 
	 * @return una lista que contiene todos los objetos del tipo Tipo
	 * 
	 */
	
	@Override
	public List<Tipo> findAll() {
		return lista;
	}
	
	/**
	 * Encuentra un objeto del tipo Tipo por su id
	 * 
	 * @param idTipo, un entero que será el id del Tipo
	 * @return i, el objeto que le pasamos como argumento si lo ha encontrado o null si no lo ha encontrado
	 */
	
	@Override
	public Tipo findById(int idTipo) {
		for(int i = 0; i < lista.size(); i++) {
			if(lista.get(i).getIdTipo() == idTipo){
				return lista.get(i);
			}
		}
		return null;
	}

}
