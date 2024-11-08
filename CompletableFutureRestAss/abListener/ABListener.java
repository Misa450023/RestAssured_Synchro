package abListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestListener;
import org.testng.ITestResult;

import io.restassured.response.Response;

public class ABListener implements ITestListener{
	
	
private static final String SUCCESS_FILE = "rest_assured_success_responses.txt";
private static final String FAILURE_FILE = "rest_assured_failure_responses.txt";
public static Response resp;
public static List<Response>resps=new ArrayList<>();



	@Override
	public void onTestSuccess(ITestResult result) {
		
		
        if (resp != null) {
            writeResponseToFile(resp, SUCCESS_FILE);
        }
        if(resps !=null) {
        	resps.stream().forEach(r->writeResponseToFile(r, SUCCESS_FILE));
        }
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
        if (resp !=null) {
            writeResponseToFile(resp,FAILURE_FILE);
        }
        if(resps !=null) {
        	resps.stream().forEach(r->writeResponseToFile(r, FAILURE_FILE));
        }
	}
		
    public void writeResponseToFile(Response response, String fileName) {
        try {
        	String path="C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\PaypalApi\\myReports\\";
            File file = new File(path+
            String.valueOf(System.currentTimeMillis())+fileName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("Response at: " + System.currentTimeMillis());
            writer.newLine();
            writer.write("Status Code: " + response.getStatusCode());
            writer.newLine();
            writer.write("Response Body: " + response.asString());
            writer.newLine();
            writer.write("--------------------------------------------------");
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }	
	


}
