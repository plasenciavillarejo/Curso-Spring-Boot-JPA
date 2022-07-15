package com.bolsadeideas.springboot.app.tareasprogramadas;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.core.SchedulerLock;

@Component
public class TareaProgramada {

	@Scheduled(cron = "* * * * * *")
	/* 1.- Name = Se le da un nombre al SchedulerLock ya que es único, con esto nos bastará para evitar que se ejecute
			más de una ejecución con el mismo nombre.
	   
	*/
	@SchedulerLock(name = "TaskScheduler_scheduledTask", 
    lockAtLeastForString = "PT5M", lockAtMostForString = "PT14M")
	public void ejecutarTarea() {
		System.out.println("Ejecutando tarea crontab ");
	}
	
}
