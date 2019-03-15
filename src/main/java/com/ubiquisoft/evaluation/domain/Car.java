package com.ubiquisoft.evaluation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ubiquisoft.evaluation.domain.PartType.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		final int REQUIRED_ENGINE = 1;
		final int REQUIRED_ELECTRICAL = 1;
		final int REQUIRED_FUEL_FILTER = 1;
		final int REQUIRED_OIL_FILTER = 1;
		final int REQUIRED_TIRES = 4;

		Map<PartType, Integer> missingParts = new HashMap<>();
		missingParts.put(ENGINE, REQUIRED_ENGINE - this.partTypeCount(ENGINE));
		missingParts.put(ELECTRICAL, REQUIRED_ELECTRICAL - this.partTypeCount(ELECTRICAL));
		missingParts.put(FUEL_FILTER, REQUIRED_FUEL_FILTER - this.partTypeCount(FUEL_FILTER));
		missingParts.put(OIL_FILTER, REQUIRED_OIL_FILTER - this.partTypeCount(OIL_FILTER));
		missingParts.put(TIRE, REQUIRED_TIRES - this.partTypeCount(TIRE));
		return missingParts;
	}

	private Integer partTypeCount(PartType partType) {
		int count = 0;
		for(Part part : this.parts) {
			if(part.getType().equals(partType)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}
}
