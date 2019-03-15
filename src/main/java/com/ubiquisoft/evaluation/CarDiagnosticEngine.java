package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Map;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {

		if(car.getYear() == null) {
			System.out.println("Missing Year Detected");
			return;
		}

		if(car.getMake() == null) {
			System.out.println("Missing Make Detected");
			return;
		}

		if(car.getModel() == null) {
			System.out.println("Missing Model Detected");
			return;
		}

		for (Map.Entry<PartType, Integer> entry : car.getMissingPartsMap().entrySet()) {
			PartType missingPart = entry.getKey();
			Integer count = entry.getValue();
			if (count > 0) {
				printMissingPart(missingPart, count);
				return;
			}
		}

		for (Part part : car.getParts()) {
			if (!part.isInWorkingCondition()) {
				printDamagedPart(part.getType(), part.getCondition());
				return;
			}
		}

		System.out.println("Car data validation has succeeded!");

	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
