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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import sis.translator.getSTMappingFunctions.GetSpatialMappingFunctions;
import sis.translator.getSTMappingFunctions.GetTemporalMappingFunctions;
import sis.translator.openWeatherStractor.OpenWeatherExtractor;

public class Mapping {

    private static final Logger log = Logger.getLogger(Decomposer.class);
    private static HashMap<String, Object> mapIn = new HashMap<String, Object>();
    private static HashMap<String, Object> mapOut = new HashMap<String, Object>();
    
	public static String[] traceMapping(String json1, String json2) {
		
		log.debug("Starting with the process to generate the mapping");
		
//		HashMap<String, Object> mapIn = new HashMap<String, Object>();
		HashMap<String, String> temporalInfoSource, spatialInfoSource;
		
//		HashMap<String, Object> mapOut = new HashMap<String, Object>();
		HashMap<String, String> temporalInfoTarget, spatialInfoTarget;
		
		List<String> mapping = new ArrayList<String>();
		
		try {
			mapIn = (HashMap<String, Object>) Decomposer.decompose(json1);
		}catch (Exception e) {
			String[] response = {"Error parsing JSON: "+e, ""};
			return response;
		}
		temporalInfoSource = TemporalHandler.getTimeDimension(mapIn);
		spatialInfoSource = SpatialHandler.getSpatialDimension(mapIn);
		
		try {
			mapOut = (HashMap<String, Object>) Decomposer.decompose(json2);
		}catch (Exception e) {
			String[] response = {"Error parsing JSON: "+e, ""};
			return response;
		}
		temporalInfoTarget = TemporalHandler.getTimeDimension(mapOut);
		spatialInfoTarget = SpatialHandler.getSpatialDimension(mapOut);
		
		
		mapping = Levensthein.quivalencesThroughLevensthein(mapIn, mapOut, mapping);
		if(temporalInfoSource != null && temporalInfoTarget != null) GetTemporalMappingFunctions.getMappingBetweenTimes(temporalInfoSource, temporalInfoTarget, mapping);
		if(spatialInfoSource != null && spatialInfoTarget != null) GetSpatialMappingFunctions.getMappingBetweenSpatialMarks(spatialInfoSource, spatialInfoTarget, mapping);
		
		String semanticInfo = "";
		
		if(spatialInfoSource != null && spatialInfoTarget != null) {
			if(temporalInfoSource != null && temporalInfoTarget != null) {
				//Have temporal and Geopositiong info. Quering OpenAPI...
				double latitude = -1000; double longitude = -1000;
				Long epoch = (long) 0;
				
				epoch = GetTemporalMappingFunctions.extractRecentEpoch(temporalInfoSource, temporalInfoTarget);
				
				latitude = GetSpatialMappingFunctions.getLatitude(spatialInfoSource, spatialInfoTarget);
				longitude = GetSpatialMappingFunctions.getLongitude(spatialInfoSource, spatialInfoTarget);
				
				if(latitude != -1000 && longitude !=-1000 && epoch > 0) {
					semanticInfo = "Semantic Information for parameters:\nLatitude = "
					+latitude+"\nLongitude = "+longitude+"\nEpoch timestamp = "+epoch+"\n\n"
					+OpenWeatherExtractor.getInfoFromOpenWeather(latitude, longitude, epoch);
					
				}else {
					log.debug("Spatial and/or Temporal information is not valid");
					semanticInfo = "Spatial and/or Temporal information is not valid: \nLatitude = "+latitude+"\nLongitude = "+longitude+"\nEpoch = "+epoch;
				}
			}else {
				//Only have Geopositiong info. Quering OpenAPI...
				System.out.println("We have only spatial info");
			}
		}
		
		System.out.println("Source Data model:");
		for (Map.Entry<String, Object> set : mapIn.entrySet()) {
			System.out.println(set.getKey()+" : "+set.getValue());
		}
		System.out.println("Target data model:");
		for (Map.Entry<String, Object> set : mapOut.entrySet()) {
			System.out.println(set.getKey()+" : "+set.getValue());
		}
		
		String[] response = {mapping.toString(), semanticInfo};
		
		return response;
	}
	
	
	public static HashMap<String, Object> getSourceMap(){
		return mapIn;
	}
	
	public static HashMap<String, Object> getTargetMap(){
		return mapOut;
	}
	
}
