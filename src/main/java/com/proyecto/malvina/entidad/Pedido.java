package com.proyecto.malvina.entidad;
import com.proyecto.malvina.enums.Estado;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;
@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String id;
	
	@ManyToMany
	private List<Producto> productos;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Temporal(TemporalType.DATE)
	private Date fechabaja;

	private Double precioTotal;
	
	@ManyToOne
	private Usuario usuario;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	@ElementCollection
	private List<Integer>cantidad;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Integer> getCantidad() {
		return cantidad;
	}

	public void setCantidad(List<Integer> cantidad) {
		this.cantidad = cantidad;
	}
	
	
	

}
