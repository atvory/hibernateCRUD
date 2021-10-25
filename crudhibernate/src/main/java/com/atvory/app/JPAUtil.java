package com.atvory.app;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
	private static EntityManagerFactory emf;
	
	//crea la emf si no existe
	public static EntityManagerFactory getEntityManagerFactory() {
		//comprueba si exite el emf, patron singleton
		if(emf==null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return emf;
	}
	
	//cierra la emf si existe
	public static void shutdown() {
		if(emf!=null) {
			emf.close();
		}

	}
}
