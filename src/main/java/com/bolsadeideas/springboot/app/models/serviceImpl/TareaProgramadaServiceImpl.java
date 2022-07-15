package com.bolsadeideas.springboot.app.models.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.ITareaProgramadaDao;
import com.bolsadeideas.springboot.app.models.entity.Shedlock;
import com.bolsadeideas.springboot.app.models.service.ITareaProgramadaService;

@Service
public class TareaProgramadaServiceImpl implements ITareaProgramadaService{

	@Autowired
	private ITareaProgramadaDao tareaProgramada;
	
	@Override
	@Transactional(readOnly = true)
	public Shedlock buscarTareaBBDD() {
		return tareaProgramada.buscarTareaBBDD();
	}

	@Override
	@Transactional
	public void borrarEntidad(String name) {
		tareaProgramada.borrarEntidad(name);
	}

}
