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

import sis.util.DataTypes;

public class GetTemporalMappingFunctions {

	
	public static void getMappingBetweenTimes(HashMap<String, String> temporalInfoSource, HashMap<String, String> temporalInfoTarget, List<String> mapping) {
		
		for (Map.Entry<String, String> setS : temporalInfoSource.entrySet()) {
			for (Map.Entry<String, String> setT : temporalInfoTarget.entrySet()) {
				Long epochT; Long epochS;
				try {
					epochT = Long.parseLong(setT.getValue());
				}catch (Exception e) {
					// No hacemos nada, simplemente no es un epoch
					epochT =DataTypes.stringDateToEpochSecond(setT.getValue());
				}
				try {
					epochS = Long.parseLong(setS.getValue());
				}catch (Exception e) {
					// No hacemos nada, simplemente no es un epoch
					epochS =DataTypes.stringDateToEpochSecond(setS.getValue());
				}
				
				if(epochS.equals(epochT)) mapping.add("Source time: ("+setS.toString()+" = "+epochS+") is equivalent to target time: ("+setT.toString()+" = "+epochT+")");
				
			}
		}
		
	}
	
	public static Long extractEpoch(HashMap<String, String> temporalInfo) {
		Long epoch = (long) 0;
		for (Map.Entry<String, String> setT : temporalInfo.entrySet()) {
			Long actualEpoch;
			try {
				actualEpoch = Long.parseLong(setT.getValue());
			}catch (Exception e) {
				// No hacemos nada, simplemente no es un epoch
				actualEpoch =DataTypes.stringDateToEpochSecond(setT.getValue());
			}
			
			if(actualEpoch>epoch) {
				epoch = actualEpoch;
			}
			
		}
		return epoch;
	}
	
	public static Long extractRecentEpoch(HashMap<String, String> temporalInfoSource, HashMap<String, String> temporalInfoTarget) {
		Long epoch = (long) 0;
		
		for (Map.Entry<String, String> setS : temporalInfoSource.entrySet()) {
			for (Map.Entry<String, String> setT : temporalInfoTarget.entrySet()) {
				Long epochT; Long epochS;
				try {
					epochT = Long.parseLong(setT.getValue());
				}catch (Exception e) {
					// No hacemos nada, simplemente no es un epoch
					epochT =DataTypes.stringDateToEpochSecond(setT.getValue());
				}
				try {
					epochS = Long.parseLong(setS.getValue());
				}catch (Exception e) {
					// No hacemos nada, simplemente no es un epoch
					epochS =DataTypes.stringDateToEpochSecond(setS.getValue());
				}
				
				if(epochT>epoch) {
					epoch = epochT;
				}
				if(epochS>epoch) {
					epoch = epochS;
				}
			}
			
		}
		
		return epoch;
	}
	
	
	public static void main(String[] args) {
		String test1 = "hola";
		String test2 = "1234567890";
		
		Long long1 = Long.parseLong(test1);
		System.out.println(long1);
		
		Long long2 = Long.parseLong(test2);
		System.out.println(long2);
		
		
	}

}
