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
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Decomposer {

    private static final Logger log = Logger.getLogger(Decomposer.class);
    public static AtomicInteger sameNameProperty;
    public static AtomicInteger arraySequenceProperties;
    
	public static Map<String,Object> decompose(String json)throws JsonSyntaxException{
		sameNameProperty = new AtomicInteger(0);
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		Map<String, Object> mapData = new HashMap<String, Object>();
		
		log.debug("Starting with the decomposition of the JSON into key-value pairs");
		return parseJSONObjectToMap(jsonObject, mapData);
		
	}
	
	
	
	public static Map<String,Object> parseJSONObjectToMap(JsonObject jsonObject, Map<String, Object> mapData){
	    
	    Iterator<String> keysItr = jsonObject.keySet().iterator();
        while(keysItr.hasNext()) {
        	String key = keysItr.next();
            Object value = jsonObject.get(key);
            if(value instanceof JsonArray) {
            	parseJSONArrayToList(key, (JsonArray) value, mapData);
            }else if(value instanceof JsonObject) {
            	parseJSONObjectToMap((JsonObject) value, mapData);
            }else {
            	if(mapData.containsKey(key)) {
            		key = key+"_PwSN_"+sameNameProperty.getAndIncrement();
            	}
            	
                mapData.put(key, value);
            }
        }
	    return mapData;
	}

	public static void parseJSONArrayToList(String key, JsonArray array, Map<String, Object> mapData) {
		arraySequenceProperties =  new AtomicInteger(0);
	    for(int i = 0; i < array.size(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JsonArray) {
	            parseJSONArrayToList(key, (JsonArray) value, mapData);
	        }else if(value instanceof JsonObject) {
	        	parseJSONObjectToMap((JsonObject) value, mapData);
	        }else {
                mapData.put(key+"_ArrayItem_"+arraySequenceProperties.getAndIncrement(), value);
            }
	    }
	}
	

}
