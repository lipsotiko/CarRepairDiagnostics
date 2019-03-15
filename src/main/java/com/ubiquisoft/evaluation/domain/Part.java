package com.ubiquisoft.evaluation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Part {

	private String inventoryId;

	private PartType type;

	private ConditionType condition;

	public boolean isInWorkingCondition() {
		switch (this.condition) {
			case NEW:
			case GOOD:
			case WORN:
				return true;
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return "Part{" +
				       "inventoryId='" + inventoryId + '\'' +
				       ", type=" + type +
				       ", condition=" + condition +
				       '}';
	}

}
