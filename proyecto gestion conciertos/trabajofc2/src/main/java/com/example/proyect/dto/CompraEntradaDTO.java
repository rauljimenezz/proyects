package com.example.proyect.dto;

import java.math.BigDecimal;

public class CompraEntradaDTO {
	private Long conciertoId;
	private Long usuarioId;
	private BigDecimal precio;

	public Long getConciertoId() {
		return conciertoId;
	}

	public void setConciertoId(Long conciertoId) {
		this.conciertoId = conciertoId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
}