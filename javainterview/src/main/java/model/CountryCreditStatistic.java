package model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class CountryCreditStatistic {
	private final String country;
	private final String creditRating;
	private final String currency;
	private final BigDecimal averageAmount;

}
