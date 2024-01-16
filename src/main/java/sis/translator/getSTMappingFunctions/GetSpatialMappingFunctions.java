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

package sis.translator.getSTMappingFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetSpatialMappingFunctions {

	public static void getMappingBetweenSpatialMarks (HashMap<String, String> spatialInfoSource, HashMap<String, String> spatialInfoTarget, List<String> mapping) {
		int cont =0;String spatialCorrelation = "";
		for (Map.Entry<String, String> setS : spatialInfoSource.entrySet()) {
			for (Map.Entry<String, String> setT : spatialInfoTarget.entrySet()) {
				
				if(setS.getValue().contains(setT.getValue()) || setT.getValue().contains(setS.getValue())) {
					if(setS.getKey().contains("latitude") ||
							setS.getKey().contains("longitude") ||
							setS.getKey().contains("coordinates") ||
							setS.getKey().equalsIgnoreCase("lng") ||
							setS.getKey().equalsIgnoreCase("lat") ||
							setS.getKey().equalsIgnoreCase("lon")
							) {
						if(cont==1) {
							spatialCorrelation+=setS.toString()+") are equivalent to target spatial mark: ("+setT.toString()+")"; cont=0; 
//							System.out.println(spatialCorrelation);
							mapping.add(spatialCorrelation);
							spatialCorrelation="";
						}
						if(cont ==0)
							spatialCorrelation+="Source spatial marks: ("+setS.toString()+") and (";cont++; 
						
					}else if(setT.getKey().contains("latitude") ||
							setT.getKey().contains("longitude") ||
							setT.getKey().contains("coordinates") ||
							setT.getKey().equalsIgnoreCase("lng") ||
							setT.getKey().equalsIgnoreCase("lat") ||
							setT.getKey().equalsIgnoreCase("lon")
							) {
						if(cont==1) {
							spatialCorrelation+=setT.toString()+") are equivalent to target spatial mark: ("+setS.toString()+")"; cont=0;
//							System.out.println(spatialCorrelation);
							mapping.add(spatialCorrelation);
							spatialCorrelation="";
						}
						if(cont ==0)
							spatialCorrelation+="Source spatial marks: ("+setT.toString()+") and (";cont++;
						
					}else {
//						System.out.println("Source spatial mark: ("+setS.toString()+") is equivalent to target spatial mark: ("+setT.toString()+")");
						mapping.add("Source spatial mark: ("+setS.toString()+") is equivalent to target spatial mark: ("+setT.toString()+")");
					}

				}
			}
		}
		
	}
	
	
	public static double getLatitude(HashMap<String, String> spatialInfoSource, HashMap<String, String> spatialInfoTarget) {
		double latitude = -1000;
		for (Map.Entry<String, String> setS : spatialInfoSource.entrySet()) {
			for (Map.Entry<String, String> setT : spatialInfoTarget.entrySet()) {
				if(setS.toString().contains("latitude") || setS.toString().contains("lat")) {
					latitude = Double.parseDouble(setS.getValue());
				}
				if(setT.toString().contains("latitude") || setT.toString().contains("lat")) {
					latitude = Double.parseDouble(setT.getValue());
				}
				
			}
		}
		return latitude;
	}
	
	public static double getLongitude(HashMap<String, String> spatialInfoSource, HashMap<String, String> spatialInfoTarget) {
		double longitude = -1000;
		for (Map.Entry<String, String> setS : spatialInfoSource.entrySet()) {
			for (Map.Entry<String, String> setT : spatialInfoTarget.entrySet()) {
				if(setS.toString().contains("longitude") || setS.toString().contains("lng") || setS.toString().contains("lon")) {
					longitude = Double.parseDouble(setS.getValue());
				}
				if(setT.toString().contains("longitude") || setT.toString().contains("lng") || setT.toString().contains("lon")) {
					longitude = Double.parseDouble(setT.getValue());
				}
			}
		}
		return longitude;
	}
	
}
