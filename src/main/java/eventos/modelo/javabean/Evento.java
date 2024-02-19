package eventos.modelo.javabean;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Evento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	private int idEvento;
	private String nombre;
	private String descripcion;
	private Date fechaInicio;
	private int duracion;
	private String direccion;
	private String estado;
	private String destacado;
	private int aforoMaximo;
	private int minimoAsistencia;
	private double precio;
	private Tipo tipo;
	
	

}


