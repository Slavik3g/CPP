package com.example.shift.logic;

import com.example.shift.Reposutory;
import com.example.shift.entity.ShiftData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class CalculatorLogic {

    public CalculatorLogic() {}

    @Autowired
    public Reposutory reposutory;

    private static final Logger logger = LogManager.getLogger(CalculatorLogic.class);

    public String Codding(ShiftData text) {
        String result;
        logger.info("Codding start");
        String str = text.getData();
        int key = 1;
        char[] Shiftstr = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            Shiftstr[i] = (char) ((int) (str.charAt(i) + key));
        }
        result = String.valueOf(Shiftstr);
        return result;
    }

    public String UnCodding(ShiftData text) {
        String result;
        logger.info("UnCodding start");
        int key = 1;
        char[] Shiftstr = new char[text.getData().length()];
        for (int i = 0; i < text.getData().length(); i++) {
            Shiftstr[i] = (char) ((int) (text.getData().charAt(i) - key));
        }
        result = String.valueOf(Shiftstr);
        return result;
    }

}
