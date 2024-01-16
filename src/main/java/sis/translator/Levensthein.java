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

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Levensthein {

    private static final Logger log = Logger.getLogger(Levensthein.class);
    
	public static List<String> quivalencesThroughLevensthein(HashMap<String, Object> source, HashMap<String, Object> target, List<String> mapping) {
		
		log.debug("Calculating semantic content equivalence between properties via Levensthein");
		
		List<String> coincidencesKeysSource = new ArrayList<String>(); List<String> coincidencesKeysTarget = new ArrayList<String>();
		int levenstheinMatches = 0; int distance = 0;
//		String response =("| Source property | Target property | Levensthein distance |\n");
		
		for (Map.Entry<String, Object> setS : source.entrySet()) {
			for (Map.Entry<String, Object> setT : target.entrySet()) {
				distance = calculate(setS.getValue().toString().replace("\"", ""), setT.getValue().toString().replace("\"", ""));

				if(distance <= ((setS.getValue().toString().replace("\"", "").length()+setT.getValue().toString().replace("\"", "").length())*0.15)) {
					levenstheinMatches++;
					coincidencesKeysSource.add(setS.getKey()); coincidencesKeysTarget.add(setT.getKey());
					mapping.add("Source property: ("+setS.toString()+") is equivalent to target property: ("+setT.toString()+")");
//					response+=("| "+setS.toString()+" | "+setT.toString()+" | "+distance+" |\n");
					
				}
			}
		}
		
		for(String e: coincidencesKeysSource) source.remove(e);
		
		for(String e: coincidencesKeysTarget) target.remove(e);
		
//		response+=("| Total number of equivalent properties: "+levenstheinMatches+" |");
		log.debug("Levensthein matches: "+levenstheinMatches);
		return mapping;
	}
	
   private static int calculate(String x, String y) {
	    int[][] dp = new int[x.length() + 1][y.length() + 1];

	    for (int i = 0; i <= x.length(); i++) {
	        for (int j = 0; j <= y.length(); j++) {
	            if (i == 0) {
	                dp[i][j] = j;
	            }
	            else if (j == 0) {
	                dp[i][j] = i;
	            }
	            else {
	                dp[i][j] = min(dp[i - 1][j - 1] 
	                 + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)), 
	                  dp[i - 1][j] + 1, 
	                  dp[i][j - 1] + 1);
	            }
	        }
	    }

	    return dp[x.length()][y.length()];
	}

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
          .min().orElse(Integer.MAX_VALUE);
    }
	
}
