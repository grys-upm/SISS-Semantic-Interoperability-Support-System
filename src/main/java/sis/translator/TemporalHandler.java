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

package sis.translator;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class TemporalHandler {
	
    private static final Logger log = Logger.getLogger(TemporalHandler.class);	
	private static final List<SimpleDateFormat> PARAMS_DATE_FORMAT;
	static {
		// IMPORTANT!! order: more to less specific
		PARAMS_DATE_FORMAT = new ArrayList<SimpleDateFormat> (
						Arrays.asList(new SimpleDateFormat [] {	
								new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
								new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),								
								new SimpleDateFormat("yyyy-MM-dd HH:mm"),
								new SimpleDateFormat("yyyy-MM-dd HH"),								
								new SimpleDateFormat("yyyy-MM-dd")
							}
						));
	}
	
	
	
	public static HashMap<String, String> getTimeDimension(HashMap<String, Object> map){
		
		HashMap<String, String> temporalInfo = new HashMap<String, String>();
		
		getTimeDimensionAsDate(map, temporalInfo);
		getTimeDimensionAsEpoch(map, temporalInfo);
		
		if(temporalInfo.isEmpty() || temporalInfo.size()<1) {
			log.debug("----> No temporal information available on the data");
			return null;
		}else {
			for(Map.Entry<String, String> set :temporalInfo.entrySet()) map.remove(set.getKey());
		}
		
		return temporalInfo;
		
	}
	
	public static void getTimeDimensionAsEpoch(HashMap<String, Object> map, HashMap<String, String> temporalInfoSource) {
		
		for (Map.Entry<String, Object> set :
			map.entrySet()) {
			try {
				if(set.getValue().toString().replace("\"", "").length()==13 && !set.getValue().toString().replace("\"", "").contains(".") && !set.getValue().toString().replace("\"", "").contains(",")) {
					Instant.ofEpochMilli(Long.parseLong(set.getValue().toString().replace("\"", "")));
					temporalInfoSource.put(set.getKey(),set.getValue().toString().replace("\"", ""));
					log.debug("----> Temporal information in Epoch timestamp format has been found. Resolution: milliseconds");
				}
			}catch(Exception e){
				// No hacemos nada... simplemente este no es su formato
			}
			try {
				if(set.getValue().toString().replace("\"", "").length()==10 && !set.getValue().toString().replace("\"", "").contains(".") && !set.getValue().toString().replace("\"", "").contains(",")) {
					Instant.ofEpochSecond(Long.parseLong(set.getValue().toString().replace("\"", "")));
					temporalInfoSource.put(set.getKey(),set.getValue().toString().replace("\"", ""));
					log.debug("----> Temporal information in Epoch timestamp format has been found. Resolution: seconds");
				}
			}catch(Exception e){
				// No hacemos nada... simplemente este no es su formato
			}
			
		}
		
	}

	public static void getTimeDimensionAsDate(HashMap<String, Object> map, HashMap<String, String> temporalInfoSource) {
		
		for (Map.Entry<String, Object> set :
			map.entrySet()) {
			
			java.util.Date dResult = null;
			
	        for (int i=0;i<PARAMS_DATE_FORMAT.size();i++) {
	            try {
	                dResult = ((SimpleDateFormat)PARAMS_DATE_FORMAT.get(i)).parse(set.getValue().toString().replace("\"", ""), new ParsePosition(0));
	            } catch (Exception e) {
	                // No hacemos nada... simplemente este no es su formato
	            }
	            if (dResult != null) {
//	            	temporalInfoSource.put(set.getKey(),dResult.toString()); //For save specific date format
	            	temporalInfoSource.put(set.getKey(),set.getValue().toString().replace("\"", ""));
	            	log.debug("----> Temporal information in "+PARAMS_DATE_FORMAT.get(i).toPattern()+" format has been found.");
	                break;
	            }
	        }
	        
		}
	}
	

}
