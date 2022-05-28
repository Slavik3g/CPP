package com.example.shift.test;

import com.example.shift.exeption.IncorrectDataExeption;
import com.example.shift.logic.CalculatorLogic;
import com.example.shift.entity.ShiftData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorLogicTest {
    private final CalculatorLogic calculatorLogic = new CalculatorLogic();
    @Test
    void testPerformCalculation() {
        ShiftData data = new ShiftData();
        data.setData("123");
        String expected = "234";
        String result = calculatorLogic.Codding(data);
        assertEquals(expected, result);
    }

}
