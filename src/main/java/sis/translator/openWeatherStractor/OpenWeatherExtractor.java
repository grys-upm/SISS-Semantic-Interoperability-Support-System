/* Copyright 2018-2021 Universidad Politécnica de Madrid (UPM).
 *
 * Authors:
 *    Mario San Emeterio de la Parte
 *    Vicente Hernández Díaz
 *    José-Fernan Martínez Ortega
 *    Néstor Lucas Martínez
 *
 * This software is distributed under a dual-license scheme:
 *
 * - For academic uses: Licensed under GNU Affero General Public License as
 *                      published by the Free Software Foundation, either
 *                      version 3 of the License, or (at your option) any
 *                      later version.
 *
 * - For any other use: Licensed under the Apache License, Version 2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * You can get a copy of the license terms in licenses/LICENSE.
 *
 */

package sis.translator.openWeatherStractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.ctc.wstx.ent.IntEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OpenWeatherExtractor {

    private static final Logger log = Logger.getLogger(OpenWeatherExtractor.class);	
	private static final String OpenApiWeatherUrl = "https://api.openweathermap.org/data/2.5/weather?";
//	lat={lat}&lon={lon}&appid={API key}
	
	public static String getInfoFromOpenWeather(double latitude, double longitude, Long epoch) {
		String query = OpenApiWeatherUrl+"lat="+latitude+"&lon="+longitude+"&dt="+epoch+"&appid="+sis.config.Constants.OPEN_WEATHER_API_KEY;
		String response = "";
		try {
			response=sendGET(query);
			if(!response.isEmpty() && response!=null) {
				JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
				JsonObject mainWeatherInfo = jsonObject.getAsJsonObject("main");
				double minTemp = Double.parseDouble(mainWeatherInfo.get("temp_min").toString())-273.15;
				double maxTemp = Double.parseDouble(mainWeatherInfo.get("temp_max").toString())-273.15;
				ObjectMapper mapper = new ObjectMapper();
				Object json = mapper.readValue(response, Object.class);
				String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
				if(minTemp != 0 && maxTemp != 0) {  log.debug(indented); System.out.println(indented); return indented;}
				
				System.out.println("Temporature range: "+minTemp+", "+maxTemp);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	private static String sendGET(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
//		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			return response.toString();
		} else {
			System.out.println("GET request did not work.");
			return null;
		}

	}
	
	
//	Test API
	public static void main(String[] args) {
		double latitude = 40.38967908470179; double longitude = -3.6289961869430716;
		
		String response = getInfoFromOpenWeather(latitude, longitude, (long) 1697024122);
		System.out.println(response);
		JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
		JsonObject mainWeatherInfo = jsonObject.getAsJsonObject("main");
		double minTemp = Double.parseDouble(mainWeatherInfo.get("temp_min").toString())-273.15;
		double maxTemp = Double.parseDouble(mainWeatherInfo.get("temp_max").toString())-273.15;
		
		System.out.println("Temporature range: "+minTemp+", "+maxTemp);
	}
	
}
