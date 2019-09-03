package abdullah.mockito_sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import controller.MainFrontContoller;
import utilities.Utility;

/**
 * @author muhammadabdullah The requirements are as follows: 
 * 		   1. A FrontController will receive a param. If it is null or any required field
 *         missing, it should throw exception. Null pointer and Illegal
 *         Arguments exception respectively. 
 *         2. The param will be sent to DB to check if data exists. If it exists, the corresponding data will be
 *         return back. 
 *         3. If it doesn't exist in DB, the param will be sent in
 *         a Web Request to check if data exists. If it exists, the
 *         corresponding data will be return back. 
 *         4. At the end, if it doesn't
 *         exist on the Web even, the request should throw error. 
 *         5. The function should return back the DATA in case there's no error
 */
@RunWith(MockitoJUnitRunner.class)
public class AppTest {

	@InjectMocks
	MainFrontContoller mainFrontController;

	@Spy
	Utility utilities;

	private String param1 = "test param 1";
	private String param2 = "test param 2";
	private String param3 = "test param 3";

	private String data1 = "test data 1";
	private String data2 = "test data 2";

	
	@Before
	public void setUp() {

		Mockito.when(utilities.dbRequest(param1)).thenReturn(data1);
		Mockito.when(utilities.dbRequest(param2)).thenReturn(null);
		Mockito.when(utilities.dbRequest(param3)).thenReturn(null);

		Mockito.when(utilities.webRequest(param2)).thenReturn(data2);
		Mockito.when(utilities.webRequest(param3)).thenReturn(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void TestCase1A() {

		mainFrontController.invokingFunctionToGetData(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestCase1B() {

		mainFrontController.invokingFunctionToGetData("");
	}



	@Test
	public void TestCase2() {

		assertEquals(data1, mainFrontController.invokingFunctionToGetData(param1));
		assertNotEquals(data2, mainFrontController.invokingFunctionToGetData(param1));
		
		assertEquals(data2, mainFrontController.invokingFunctionToGetData(param2));
		assertNotEquals(data1, mainFrontController.invokingFunctionToGetData(param2));

		Mockito.verify(utilities, Mockito.times(2)).dbRequest(param1);
		Mockito.verify(utilities, Mockito.times(2)).dbRequest(param2);
		
		Mockito.verify(utilities, Mockito.times(0)).webRequest(param1);
		Mockito.verify(utilities, Mockito.times(2)).webRequest(param2);
		

	}
	
	@Test(expected = Error.class)
	public void TestCase3A() {
		mainFrontController.invokingFunctionToGetData(param3);

	}
	
	
	@Test
	public void TestCase3B() {
		try {
			mainFrontController.invokingFunctionToGetData(param3);
		}
		catch (Error e) {
			
		}
		Mockito.verify(utilities).dbRequest(param3);
		Mockito.verify(utilities).webRequest(param3);
	}
	
	

}
