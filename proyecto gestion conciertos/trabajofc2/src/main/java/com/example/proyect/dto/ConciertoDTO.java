package com.example.proyect.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConciertoDTO {
	private Long id;
	private String nombre;
	private String descripcion;
	private String lugar;
	private int capacidad;
	private LocalDate fecha;
	private String organizadorNombre;
	private String estadoNombre;
	private BigDecimal precioEntrada;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getOrganizadorNombre() {
		return organizadorNombre;
	}

	public void setOrganizadorNombre(String organizadorNombre) {
		this.organizadorNombre = organizadorNombre;
	}

	public String getEstadoNombre() {
		return estadoNombre;
	}

	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}
	
	public BigDecimal getPrecioEntrada() {
		return precioEntrada;
	}

	public void setPrecioEntrada(BigDecimal precioEntrada) {
		this.precioEntrada = precioEntrada;
	}
}