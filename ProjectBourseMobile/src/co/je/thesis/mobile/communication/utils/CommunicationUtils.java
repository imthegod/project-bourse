package co.je.thesis.mobile.communication.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;

public class CommunicationUtils {

	public static String getEntityAsString(HttpEntity httpResponseEntity) {

		String result = "";

		try {

			InputStream inputStream = httpResponseEntity.getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String line = bufferedReader.readLine();

			while (line != null) {

				result += line;
				line = bufferedReader.readLine();
			}

		} catch (IllegalStateException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return result;
	}
}