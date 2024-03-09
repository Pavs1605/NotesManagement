package com.app.notesManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class NotesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesManagementApplication.class, args);
	}

}
