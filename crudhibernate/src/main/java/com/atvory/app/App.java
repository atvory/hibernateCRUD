package com.atvory.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.atvory.pojo.Bird;
import com.atvory.pojo.User;

public class App {

	public static void main(String[] args) {
		
		
		int opcion = 0;
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		Bird bird;
		int id;
		
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		
		while(opcion!=5) {
			
			System.out.println("1. CREATE bird");
			System.out.println("2. SEARCH bird");
			System.out.println("3. UPDATE bird");
			System.out.println("4. DELETE bird");
			System.out.println("5. EXIT");
			System.out.println("SELECT OPTION:");
			
			try {
				opcion = Integer.parseInt(br.readLine());
			}catch(IOException | NumberFormatException ex) {
				System.out.println(ex.getMessage());
			}
			
			
			switch(opcion) {
					case 1:
						try {
							System.out.println("CREATE bird");
							bird = new Bird();
							
							System.out.println("Type the bird name");
							String name = br.readLine();
							
							System.out.println("Type family name for the bird");
							String family = br.readLine();
							System.out.println(" ");
							
							bird.setName(name);
							bird.setFamily(family);
							
							em.getTransaction().begin();
							em.persist(bird);
							em.getTransaction().commit();
							
							System.out.println();
							System.out.println("New bird added to the database");
							System.out.println();
							
							
						}catch(IOException | NumberFormatException ex) {
							System.out.println("An error has occurred");
							System.out.println(ex.getMessage());
						}
						
						break;
						
					case 2:
						try {
							System.out.println("SEARCH bird");
							System.out.println("Type Id for the bird you're lookin for...");
							id = Integer.parseInt(br.readLine());
							bird = new Bird();
							bird = em.find(Bird.class, id);
							
							if(bird != null) {
								System.out.println("The bird with that Id is:");
								System.out.println("name: "+bird.getName()+", family: "+bird.getFamily());
								System.out.println();
							}else {
								System.out.println();
								System.out.println("There is no bird in DB with that Id");
								System.out.println("This is the bird's list available");
								List<Bird> birdsList = new ArrayList<>();
								
								Query query = em.createQuery("from birds order by id");
								birdsList = query.getResultList();
								if(birdsList != null) {
									birdsList.forEach(System.out::println);
								}else {
									System.out.println("The table 'birds' is empty");
								}
								System.out.println();
							}
							
						} catch (IOException | NumberFormatException ex) {
							System.out.println("An error has occurred");
							System.out.println(ex.getMessage());
						}
						
						
						break;
						
					case 3:
						try {
							System.out.println("UPDATE bird");
							System.out.println("Type Id for the bird you're lookin for update...");
							id = Integer.parseInt(br.readLine());
							bird = new Bird();
							bird = em.find(Bird.class,id);
							if(bird != null) {
								System.out.println("bird founded");
								System.out.println("Select data for update:");
								System.out.println("1. update name");
								System.out.println("2. update family");
								System.out.println("0. exit UPDATE bird");
								System.out.println("type an option:");
								int option = Integer.parseInt(br.readLine());
								if(option != 0) {
									
									if(option == 1) {
										System.out.println("type de new bird name:");
										String name = br.readLine();
										bird.setName(name);
										
									}else if(option == 2) {
										System.out.println("type de new bird family:");
										String family = br.readLine();
										bird.setFamily(family);
										
									}else {
										System.out.println("option out of range");
									}
									
								}else {
									System.out.println("object not updated");
									break;
								}
							}
							
						} catch (IOException | NumberFormatException ex) {
							System.out.println("An error has occurred");
							System.out.println(ex.getMessage());
						}
						break;
					case 4:
						try {
							System.out.println("DELETE bird");
							System.out.println("Type Id for the bird you're lookin for delete...");
							id = Integer.parseInt(br.readLine());
							bird = new Bird();
							bird = em.find(Bird.class,id);
							if(bird != null) {
								System.out.println("bird founded");
								System.out.println("Are you sure you want to delete this data?");
								System.out.println("N / y");
								
								String option = br.readLine();
								
								if(option==null || option == "N" || option == "n") {
									System.out.println("bird not deleted...");
									
								}else if(option=="y"|| option == "Y") {
									em.remove(bird);
									System.out.println("bird deleted...");
								}
								System.out.println();
							}
							
						} catch (IOException | NumberFormatException ex) {
							System.out.println("An error has occurred");
							System.out.println(ex.getMessage());
						}
						
						break;
					case 5:
						em.close();
						JPAUtil.shutdown();
						break;
			
			}	
		}
	}

}
