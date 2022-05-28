package com.example.shift;

import com.example.shift.logic.CalculatorLogic;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class ShiftApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShiftApplication.class, args);
	}

}
