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
import java.util.Map;

import org.apache.log4j.Logger;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.io.GeohashUtils;
import org.locationtech.spatial4j.shape.Point;

public class SpatialHandler {

    private static final Logger log = Logger.getLogger(SpatialHandler.class);	
	
	public static HashMap<String, String> getSpatialDimension(HashMap<String, Object> map) {
		
		HashMap<String, String> spatialInfo = new HashMap<String, String>();
		
//		getSpatialDimensionAsGeohash(map, spatialInfo);
		getSpatialDimensionAsLatLong(map, spatialInfo);
		getSpatialDimensionAsWKT(map, spatialInfo);
		
		if(spatialInfo.isEmpty() || spatialInfo.size()<1) {
			log.debug("-----> No spatial information available on the data");
			return null;
		}else {
			for(Map.Entry<String, String> set :spatialInfo.entrySet()) map.remove(set.getKey());
		}
		
		return spatialInfo;
	}
	
	public static void getSpatialDimensionAsGeohash(HashMap<String, Object> map, HashMap<String, String> spatialInfo) {
		
		for (Map.Entry<String, Object> set :
			map.entrySet()) {
			System.out.println("extracting geohash");
			Point latLong = GeohashUtils.decode(set.getValue().toString().replace("\"", ""), SpatialContext.GEO);
			System.out.println("geohash extracted");
			if(!latLong.isEmpty() ) {
				System.out.println("geohash exists");
				spatialInfo.put(set.getKey(), set.getValue().toString());
				log.debug("-----> Spatial information has been found in Geohash format.");
				System.out.println("Geohash as point: "+latLong.toString());
			}
			
		}
	}
	
	public static void getSpatialDimensionAsLatLong(HashMap<String, Object> map, HashMap<String, String> spatialInfo) {
		
		for (Map.Entry<String, Object> set :
			map.entrySet()) {
			if(set.getKey().contains("latitude") ||
					set.getKey().contains("longitude") ||
					set.getKey().contains("coordinates") ||
					set.getKey().equalsIgnoreCase("lng") ||
					set.getKey().equalsIgnoreCase("lat") ||
					set.getKey().equalsIgnoreCase("lon")
					) {
				
				spatialInfo.put(set.getKey(),set.getValue().toString());
				log.debug("-----> Spatial information has been found in latitude, longitude format.");
				
			}
		}
	}
	
	public static void getSpatialDimensionAsWKT(HashMap<String, Object> map, HashMap<String, String> spatialInfo) {
		
		for (Map.Entry<String, Object> set :
			map.entrySet()) {
			
			if (set.getValue().toString().contains("MULTIPOINT") || 
				set.getValue().toString().contains("MULTILINESTRING") ||
				set.getValue().toString().contains("MULTIPOLYGON") ||
				set.getValue().toString().contains("POLYGON") ||
				set.getValue().toString().contains("LINESTRING") ||
				set.getValue().toString().contains("POINT")) {
				
				spatialInfo.put(set.getKey(),set.getValue().toString());
				log.debug("-----> Spatial information has been found in AsWKT format.");
			}
		}
	}
	
	public static void getMappingBetweenSpatialInfo(HashMap<String, String> spatialInfoSource, HashMap<String, String> spatialInfoTarget) {
		
	}
	
}
