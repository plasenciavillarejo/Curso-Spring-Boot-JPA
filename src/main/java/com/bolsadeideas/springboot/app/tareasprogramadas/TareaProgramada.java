package com.bolsadeideas.springboot.app.tareasprogramadas;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.app.models.entity.Shedlock;
import com.bolsadeideas.springboot.app.models.service.ITareaProgramadaService;

import net.javacrumbs.shedlock.core.SchedulerLock;

@Component
public class TareaProgramada {
	
	@Autowired
	private ITareaProgramadaService tareaProgramada;
	
	@SuppressWarnings("unused")
	@Scheduled(cron = "* * * * * *")
	/* 1.- Name = Se le da un nombre al SchedulerLock ya que es único, con esto nos bastará para evitar que se ejecute
			más de una ejecución con el mismo nombre.
	   
	*/
	@SchedulerLock(name = "Ejecucion_Tarea_Programada", 
    lockAtLeastForString = "PT5M", lockAtMostForString = "PT14M")
	public void ejecutarTarea() {
		System.out.println("Ejecutando tarea crontab ");
		
		Shedlock validarTareaExistente = null;
		
		Shedlock borrarEntidad = null;
		try {
			validarTareaExistente = tareaProgramada.buscarTareaBBDD();
			
			if(validarTareaExistente != null) {
				tareaProgramada.borrarEntidad(validarTareaExistente.getName());
				
				if(validarTareaExistente  == null) {
					System.out.println("Se ha borrado correctamente la tarea.");
				}else {
					System.out.println("Ha fallado al borrar la tarea");
				}
			}
			
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finalizando la tarea");
		
		
		
	}
	
}
