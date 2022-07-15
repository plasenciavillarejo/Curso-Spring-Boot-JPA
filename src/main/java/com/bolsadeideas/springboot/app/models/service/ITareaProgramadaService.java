package com.bolsadeideas.springboot.app.models.service;

import com.bolsadeideas.springboot.app.models.entity.Shedlock;

public interface ITareaProgramadaService {
	
	public Shedlock buscarTareaBBDD();

	public void borrarEntidad(String name);

	
}
