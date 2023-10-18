package inputData;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CSVDataTest {

	@Test
	void test() {
		CSVData test = new CSVData("activity_2001397372.csv");
		System.out.println(test);
	}

}
