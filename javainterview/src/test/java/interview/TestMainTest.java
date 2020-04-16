package interview;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.AccountDetail;
import model.CountryCreditStatistic;

public class TestMainTest {

	private TestMain main;

	@Test
	public void testReadFile_shouldSucceed() throws FileNotFoundException {
		// given
		String path = "src/test/resources/FILE.DAT.txt";

		main = new TestMain();

		// when
		List<AccountDetail> readFile = main.readFile(path);

		// then
		assertEquals(expectedAccountDetails().get(0).toString(), readFile.get(0).toString());

	}
	
	@Test(expected = FileNotFoundException.class)
	public void testReadFile_shouldFail() throws FileNotFoundException {
		// given
		String path = "src/test/resources/random/FILE.DAT.txt";

		main = new TestMain();

		// when
		main.readFile(path);
	}

	@Test
	public void testCalculateAmountInEur_ShouldSucceed() {
		// given
		main = new TestMain();
		AccountDetail accountDetail = AccountDetail.builder().companyCode("2300").account("9917319").city("")
				.country("USA").creditRating("AAA+").currency("CHF").amount(new BigDecimal("9223372036854775809.456"))
				.build();

		// when
		AccountDetail calculateAmountInEur = main.calculateAmountInEur(accountDetail);

		// then
		AccountDetail expectedAccountDetail = AccountDetail.builder().companyCode("2300").account("9917319").city("")
				.country("USA").creditRating("AAA+").currency("EUR")
				.amount(new BigDecimal("7515203535629271329.5447488")).build();
		assertEquals(expectedAccountDetail.toString(), calculateAmountInEur.toString());

	}

	@Test
	public void testCalculateAverageAmountInEur_shouldSucceed() throws FileNotFoundException {
		// given
		String path = "src/test/resources/FILE.DAT.txt";
		main = new TestMain();

		// when
		List<CountryCreditStatistic> calculateAverageAmountInEur = main.calculateAverageAmountInEur(path);

		// then
		assertEquals(expectedcalculateAverageAmountInEur().get(0).toString(),
				calculateAverageAmountInEur.get(0).toString());

	}
	
	@Test
	public void testCalculateAverageAmountInEur_shouldReplaceCountryNameWithCityName() throws FileNotFoundException {
		// given
		String path = "src/test/resources/FILE.DAT.txt";
		main = new TestMain();

		// when
		List<CountryCreditStatistic> calculateAverageAmountInEur = main.calculateAverageAmountInEur(path);

		// then
		assertEquals(expectedcalculateAverageAmountInEur().get(2).toString(),
				calculateAverageAmountInEur.get(2).toString());

	}

	private List<CountryCreditStatistic> expectedcalculateAverageAmountInEur() {

		List<CountryCreditStatistic> countryCreditStatistics = new ArrayList<CountryCreditStatistic>();
		countryCreditStatistics.add(CountryCreditStatistic.builder().country("NOR").creditRating("A").currency("EUR")
				.averageAmount(new BigDecimal("885652691.45580")).build());	
		
		countryCreditStatistics.add(CountryCreditStatistic.builder().country("NOR").creditRating("B").currency("EUR")
				.averageAmount(new BigDecimal("442586667.01723")).build());
				
		countryCreditStatistics.add(CountryCreditStatistic.builder().country("London").creditRating("-").currency("EUR")
				.averageAmount(new BigDecimal("643254.77832")).build());
		return countryCreditStatistics;
	}

	private List<AccountDetail> expectedAccountDetails() {
		List<AccountDetail> details = new ArrayList<AccountDetail>();
		details.add(AccountDetail.builder().companyCode("2300").account("9917319").city("").country("USA")
				.creditRating("AAA+").currency("CHF").amount(new BigDecimal("9223372036854775809.456")).build());
		details.add(AccountDetail.builder().companyCode("2301").account("7213447").city("New York").country("USA")
				.creditRating("BBB").currency("GBP").amount(new BigDecimal("-9223372036854775809.456")).build());
		details.add(AccountDetail.builder().companyCode("2302").account("4535851").city("").country("London")
				.creditRating("A").currency("GBP").amount(new BigDecimal("456.85014")).build());
		details.add(AccountDetail.builder().companyCode("2303").account("2954026").city("Atlanta").country("USA")
				.creditRating("AAA+").currency("CHF").amount(new BigDecimal("65484231.44")).build());
		details.add(AccountDetail.builder().companyCode("2304").account("896137").city("Bristol").country("UK")
				.creditRating("A").currency("GBP").amount(new BigDecimal("312139226.6")).build());

		return details;
	}
}
