package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static com.ubiquisoft.evaluation.domain.ConditionType.DAMAGED;
import static com.ubiquisoft.evaluation.domain.PartType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarDiagnosticEngineTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private CarDiagnosticEngine carDiagnosticEngine;

    @Mock
    Car car;

    @Mock
    Part part;

    private List<Part> partList;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        carDiagnosticEngine = new CarDiagnosticEngine();

        partList = new ArrayList<>();
        partList.add(part);
    }

    @Test
    void car_is_missing_year() {
        when(car.getYear()).thenReturn(null);
        carDiagnosticEngine.executeDiagnostics(car);
        assertThat(outContent.toString()).contains("Missing Year Detected");
    }

    @Test
    void car_is_not_missing_year() {
        when(car.getYear()).thenReturn("2018");
        carDiagnosticEngine.executeDiagnostics(car);
        assertThat(outContent.toString()).doesNotContain("Missing Year Detected");
    }

    @Test
    void car_is_missing_make() {
        when(car.getYear()).thenReturn("2018");
        when(car.getMake()).thenReturn(null);
        carDiagnosticEngine.executeDiagnostics(car);
        assertThat(outContent.toString()).contains("Missing Make Detected");
    }

    @Test
    void car_is_not_missing_make() {
        when(car.getYear()).thenReturn("2018");
        when(car.getMake()).thenReturn("Ferrari");
        carDiagnosticEngine.executeDiagnostics(car);
        assertThat(outContent.toString()).doesNotContain("Missing Make Detected");
    }

    @Test
    void car_is_missing_model() {
        when(car.getYear()).thenReturn("2018");
        when(car.getMake()).thenReturn("Ferrari");
        when(car.getModel()).thenReturn(null);
        carDiagnosticEngine.executeDiagnostics(car);
        assertThat(outContent.toString()).contains("Missing Model Detected");
    }

    @Test
    void car_is_not_missing_model() {
        when(car.getYear()).thenReturn("2018");
        when(car.getMake()).thenReturn("Ferrari");
        when(car.getModel()).thenReturn("Enzo");
        carDiagnosticEngine.executeDiagnostics(car);
        assertThat(outContent.toString()).doesNotContain("Missing Model Detected");
    }

    @Test
    void car_has_missing_parts() {
        validYearMakeModel();

        when(car.getMissingPartsMap()).thenReturn(mockMissingParts());
        carDiagnosticEngine.executeDiagnostics(car);
        assertThat(outContent.toString()).contains("Missing Part(s) Detected: ENGINE - Count: 1");
    }

    @Test
    void car_has_no_missing_parts() {
        validYearMakeModel();

        when(car.getMissingPartsMap()).thenReturn(mockNoMissingParts());
        carDiagnosticEngine.executeDiagnostics(car);
        assertThat(outContent.toString()).doesNotContain("Missing Part(s) Detected");
    }

    @Test
    void car_has_parts_that_are_not_in_working_condition() {
        validYearMakeModel();
        when(part.isInWorkingCondition()).thenReturn(false);
        when(part.getCondition()).thenReturn(DAMAGED);
        when(part.getType()).thenReturn(ENGINE);
        when(car.getParts()).thenReturn(partList);

        carDiagnosticEngine.executeDiagnostics(car);

        assertThat(outContent.toString()).contains("Damaged Part Detected: ENGINE - Condition: DAMAGED");
    }

    @Test
    void car_has_parts_that_are_in_working_condition() {
        validYearMakeModel();
        when(part.isInWorkingCondition()).thenReturn(true);
        when(car.getParts()).thenReturn(partList);

        carDiagnosticEngine.executeDiagnostics(car);

        assertThat(outContent.toString()).doesNotContain("Damaged Part Detected");
        assertThat(outContent.toString()).contains("Car data validation has succeeded!");
    }

    private Map<PartType, Integer> mockMissingParts() {
        Map<PartType, Integer> missingParts = new HashMap<>();
        missingParts.put(ENGINE, 1);
        return missingParts;
    }

    private Map<PartType, Integer> mockNoMissingParts() {
        Map<PartType, Integer> missingParts = new HashMap<>();
        missingParts.put(ENGINE, 0);
        return missingParts;
    }

    private void validYearMakeModel() {
        when(car.getModel()).thenReturn("A");
        when(car.getYear()).thenReturn("B");
        when(car.getMake()).thenReturn("C");
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}