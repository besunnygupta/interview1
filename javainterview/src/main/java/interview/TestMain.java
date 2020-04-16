package interview;
import static model.Currency.CHF;
import static model.Currency.EUR;
import static model.Currency.GBP;
import static model.Currency.USD;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import model.AccountDetail;
import model.CountryCreditStatistic;

public class TestMain {

	static Map<String, Double> conversionRatesToEUR = new HashMap<>();
	static {
		conversionRatesToEUR.put(GBP.name(), 1.2251);
		conversionRatesToEUR.put(CHF.name(), 0.8148);
		conversionRatesToEUR.put(USD.name(), 0.7407);
		conversionRatesToEUR.put(EUR.name(), 1.0);
	}

	public static void main(String[] args) throws FileNotFoundException {
		TestMain test = new TestMain();
		List<CountryCreditStatistic> calculateAverageAmountInEur = test.calculateAverageAmountInEur(
				args[0]);

		calculateAverageAmountInEur.stream().forEach(System.out::println);
	}

	public List<CountryCreditStatistic> calculateAverageAmountInEur(String filePath) throws FileNotFoundException {

		List<AccountDetail> details = readFile(filePath);

		Map<List<String>, List<AccountDetail>> groupByCountryAndCredit = details.stream()
				.map(this::calculateAmountInEur)
				.collect(Collectors.groupingBy(acc -> Arrays.asList(
						StringUtils.isBlank(Objects.toString(acc.getCountry(), "")) ? acc.getCity() : acc.getCountry(),
						acc.getCreditRating())));

		List<CountryCreditStatistic> finalResult = new ArrayList<>();

		for (Map.Entry<List<String>, List<AccountDetail>> entry : groupByCountryAndCredit.entrySet()) {

			List<AccountDetail> accountDetails = entry.getValue();
			
			System.out.println(entry.getKey().get(0) +  " " + entry.getKey().get(1) + " " + accountDetails.size() );

			BigDecimal average = accountDetails.stream().map(AccountDetail::getAmount).reduce((a, b) -> a.add(b)).get()
					.divide(BigDecimal.valueOf(accountDetails.size()), 5, RoundingMode.HALF_UP);

			finalResult.add(CountryCreditStatistic.builder().country(entry.getKey().get(0))
					.creditRating(entry.getKey().get(1)).averageAmount(average).currency(EUR.name()).build());

		}

		return finalResult;
	}

	AccountDetail calculateAmountInEur(AccountDetail accDetails) {
		BigDecimal amountInEur = BigDecimal.valueOf(conversionRatesToEUR.get(accDetails.getCurrency()))
				.multiply(accDetails.getAmount());

		accDetails.setAmount(amountInEur);
		accDetails.setCurrency(EUR.name());
		return accDetails;
	}

	List<AccountDetail> readFile(String filePath) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(filePath));

		List<AccountDetail> details = new ArrayList<AccountDetail>();

		scan.nextLine();
		while (scan.hasNext()) {
			String curLine = scan.nextLine();
			String[] splitted = curLine.split("\t");

			details.add(AccountDetail.builder().companyCode(splitted[0].trim()).account(splitted[1].trim())
					.city(splitted[2].trim()).country(splitted[3].trim()).creditRating(splitted[4].toUpperCase().trim())
					.currency(splitted[5].trim()).amount(new BigDecimal(splitted[6].trim())).build());

		}

		scan.close();
		return details;
	}
	
}