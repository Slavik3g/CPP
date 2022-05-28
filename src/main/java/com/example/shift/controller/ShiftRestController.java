//http://localhost:8080/?Date=123
package com.example.shift.controller;

import com.example.shift.caches.HashCalculation;
import com.example.shift.counter.CountAtomic;
import com.example.shift.exeption.IncorrectDataExeption;
import com.example.shift.logic.CalculatorLogic;
import com.example.shift.entity.ShiftData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.*;

@RestController
public class ShiftRestController {

    private static final Logger logger = LoggerFactory.getLogger(ShiftRestController.class);

    @Autowired
    private HashCalculation shiftingHashMap;
    @Autowired
    private HashCalculation unShiftingHashMap;


    @Autowired
    private final CalculatorLogic calculatorLogic = new CalculatorLogic();


    @Autowired
    public void setHashCalculation(HashCalculation shiftingHashMap, HashCalculation unShiftingHashMap) {
        this.shiftingHashMap = shiftingHashMap;
        this.unShiftingHashMap = unShiftingHashMap;
    }

    @GetMapping("/Shift")
    public HashMap<String, String> doGetShifting(@RequestParam(value = "DataForShifting") String dataForShift) throws IncorrectDataExeption {
        new Thread(CountAtomic::increment).start();
        logger.info("Getting params...");
        if (dataForShift.isEmpty()) {
            logger.error("Wrong arguments order, argument is null");
            throw new IncorrectDataExeption("Wrong arguments order, argument is null! ");
        }
        ShiftData receivedData = new ShiftData(calculatorLogic.reposutory.findAll().size(), dataForShift);
        String result;

        if (shiftingHashMap.isContain(receivedData)) {
            logger.info("Get from hashMap");
            result = shiftingHashMap.getParameters(receivedData);
        } else {
            result = calculatorLogic.Codding(receivedData);
            logger.info("Codding success");
            logger.info("Add to hashMap");
            calculatorLogic.reposutory.save(receivedData);
            shiftingHashMap.addToMap(receivedData, result);
        }
        return new HashMap<>() {{
            put("Shift", dataForShift);
            put("UnShift", result);
        }};
    }

    @GetMapping("/UnShift")
    public HashMap<String, String> doGetUnShifting(@RequestParam(value = "DataForUnShifting") String dataForUnShifting) throws IncorrectDataExeption {
        new Thread(CountAtomic::increment).start();
        logger.info("Getting params...");
        if (dataForUnShifting.isEmpty()) {
            logger.error("Wrong arguments order, argument is null");
            throw new IncorrectDataExeption("Wrong arguments order, argument is null");
        }

        ShiftData receivedData = new ShiftData(calculatorLogic.reposutory.findAll().size(), dataForUnShifting);
        String result;

        if (unShiftingHashMap.isContain(receivedData)) {
            logger.info("Get from hashMap");
            result = unShiftingHashMap.getParameters(receivedData);
        } else {
            result = calculatorLogic.UnCodding(receivedData);
            logger.info("UnCodding success");
            unShiftingHashMap.addToMap(receivedData, result);
            calculatorLogic.reposutory.save(receivedData);
            logger.info("Add to hashMap");
        }

        return new HashMap<>() {{
            put("Shift", dataForUnShifting);
            put("UnShift", result);
        }};
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> doPost(@RequestBody List<ShiftData> bodyList) {

        List<String> resultList = new LinkedList<>();

        bodyList.forEach((currentElement) -> {
            try {
                resultList.add(calculatorLogic.Codding(currentElement));
            }catch (Throwable e){
                logger.error("Argument is null");
            }
        });


        logger.info("Successfully postMapping");

        Map<String, Object> mapa = new HashMap<>();

        mapa.put("AvergeRezult", resultList
                .stream()
                .mapToInt(String::length)
                .average()
                .orElseThrow(NoSuchElementException::new));

        mapa.put("MaxRezult", resultList
                .stream()
                .mapToInt(String::length)
                .max()
                .orElseThrow(NoSuchElementException::new));

        mapa.put("MinRezult", resultList
                .stream()
                .mapToInt(String::length)
                .min()
                .orElseThrow(NoSuchElementException::new));

        mapa.put("SumRezult", resultList
                .stream()
                .mapToInt(String::length)
                .sum());

        mapa.put("Amount", resultList
                .stream()
                .mapToInt(String::length)
                .count());

        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    @GetMapping("/counter")
    public String getCounter() {
        logger.info("Visited RequestCounterController");
        return CountAtomic.value() + " запросов было выполнено";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<String> errorEntryParams(MethodArgumentTypeMismatchException e) {
        logger.error("Entry wrong params");
        return new ResponseEntity<>("ERROR 400: BAD REQUEST", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> errorServer(RuntimeException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>("ERROR 500", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
