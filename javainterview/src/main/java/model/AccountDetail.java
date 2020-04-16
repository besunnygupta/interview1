package model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDetail {
	private String companyCode;	
	private String account;
	private String city;
	private String country;
	private String creditRating;
	private String currency;
	private BigDecimal amount;
}
