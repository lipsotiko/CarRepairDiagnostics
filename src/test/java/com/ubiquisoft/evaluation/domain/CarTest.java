package com.ubiquisoft.evaluation.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ubiquisoft.evaluation.domain.PartType.*;
import static org.assertj.core.api.Assertions.*;


class CarTest {

    private List<Part> parts;

    @BeforeEach
    public void setUp() {
        parts = new ArrayList<>();
    }

    @Test
    void car_is_missing_part_type_of_ENGINE() {
        Car car = Car.builder().parts(Collections.emptyList()).build();
        assertThat(car.getMissingPartsMap().get(ENGINE)).isEqualTo(1);
    }

    @Test
    void car_is_missing_part_type_of_ELECTRICAL() {
        Car car = Car.builder().parts(Collections.emptyList()).build();
        assertThat(car.getMissingPartsMap().get(ELECTRICAL)).isEqualTo(1);
    }

    @Test
    void car_is_missing_part_type_of_FUEL_FILTER() {
        Car car = Car.builder().parts(Collections.emptyList()).build();
        assertThat(car.getMissingPartsMap().get(FUEL_FILTER)).isEqualTo(1);
    }

    @Test
    void car_is_missing_part_type_of_OIL_FILTER() {
        Car car = Car.builder().parts(Collections.emptyList()).build();
        assertThat(car.getMissingPartsMap().get(OIL_FILTER)).isEqualTo(1);
    }

    @Test
    void car_is_not_missing_part_type_of_ENGINE() {
        parts.add(Part.builder().type(ENGINE).build());

        Car car = Car.builder().parts(parts).build();
        assertThat(car.getMissingPartsMap().get(ENGINE)).isEqualTo(0);
    }

    @Test
    void car_is_not_missing_part_type_of_ELECTRICAL() {
        parts.add(Part.builder().type(ELECTRICAL).build());

        Car car = Car.builder().parts(parts).build();
        assertThat(car.getMissingPartsMap().get(ELECTRICAL)).isEqualTo(0);
    }

    @Test
    void car_is_not_missing_part_type_of_FUEL_FILTER() {
        parts.add(Part.builder().type(FUEL_FILTER).build());

        Car car = Car.builder().parts(parts).build();
        assertThat(car.getMissingPartsMap().get(FUEL_FILTER)).isEqualTo(0);
    }

    @Test
    void car_is_not_missing_part_type_of_OIL_FILTER() {
        parts.add(Part.builder().type(OIL_FILTER).build());

        Car car = Car.builder().parts(parts).build();
        assertThat(car.getMissingPartsMap().get(OIL_FILTER)).isEqualTo(0);
    }

    @Test
    void car_is_missing_four_part_type_of_TIRE() {
        Car car = Car.builder().parts(Collections.emptyList()).build();
        assertThat(car.getMissingPartsMap().get(TIRE)).isEqualTo(4);
    }

    @Test
    void car_is_missing_three_part_type_of_TIRE() {
        parts.add(Part.builder().type(TIRE).build());

        Car car = Car.builder().parts(parts).build();
        assertThat(car.getMissingPartsMap().get(TIRE)).isEqualTo(3);
    }

    @Test
    void car_is_missing_two_part_type_of_TIRE() {
        parts.add(Part.builder().type(TIRE).build());
        parts.add(Part.builder().type(TIRE).build());

        Car car = Car.builder().parts(parts).build();
        assertThat(car.getMissingPartsMap().get(TIRE)).isEqualTo(2);
    }

    @Test
    void car_is_missing_one_part_type_of_TIRE() {
        parts.add(Part.builder().type(TIRE).build());
        parts.add(Part.builder().type(TIRE).build());
        parts.add(Part.builder().type(TIRE).build());

        Car car = Car.builder().parts(parts).build();
        assertThat(car.getMissingPartsMap().get(TIRE)).isEqualTo(1);
    }

    @Test
    void car_is_missing_no_part_type_of_TIRE() {
        parts.add(Part.builder().type(TIRE).build());
        parts.add(Part.builder().type(TIRE).build());
        parts.add(Part.builder().type(TIRE).build());
        parts.add(Part.builder().type(TIRE).build());

        Car car = Car.builder().parts(parts).build();
        assertThat(car.getMissingPartsMap().get(TIRE)).isEqualTo(0);
    }
}
