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

package sis.userInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MappingAdapter {

	public static JTable fromRESTMappingToGUIMapping(String mapping) {
		String[] columnNames = { "Subject", "Predicate", "Object" };
		if(mapping.contains("Error")) {
			return null;
		}
		String[] equivalences = mapping.split(",");
		int n = equivalences.length;
		
		JTable mappings = new JTable(new DefaultTableModel(columnNames, 0));
		
		for(String s: equivalences) {
			int subjectInit = s.indexOf("(", 0)+1; int subjectEnd = s.indexOf(")");
			int objectInit = s.indexOf("(", subjectEnd)+1; int objectEnd = s.indexOf(")", objectInit);
			
			String subject = s.substring(subjectInit, subjectEnd);
			String predicate = "";
			String object = "";
			
			DefaultTableModel model = (DefaultTableModel) mappings.getModel();
			
			if(s.contains("spatial marks")) {
				predicate = "owl:hasKey";
				int objectLatLongInit = s.indexOf("(", objectEnd)+1; int objectLatLongEnd = s.length()-2;
				object = s.substring(objectLatLongInit, objectLatLongEnd);
				model.addRow(new Object[]{subject, predicate, object});
				subject = s.substring(objectInit, objectEnd);
			}else if(s.contains("spatial mark")) {
				predicate = "owl:equivalentProperty";
				object = s.substring(objectInit, s.length()-2);
			}else if(s.contains("equivalent")) {
				predicate = "owl:equivalentProperty";
				if(s.contains("equivalent to target time")) predicate = "owl:equivalentClass";
				object = s.substring(objectInit, objectEnd);
			}			
			
			model.addRow(new Object[]{subject, predicate, object});
		}
		
		return mappings;
	}
	
	public static JTable getRemainingProperties(HashMap<String, Object> remainingProperties) {
		String[] columnNames = { "Property", "Value"};
		JTable restOfProperties = new JTable(new DefaultTableModel(columnNames, 0));
		DefaultTableModel model = (DefaultTableModel) restOfProperties.getModel();
		
		for (Map.Entry<String, Object> set : remainingProperties.entrySet()) {
			model.addRow(new Object[]{set.getKey(), set.getValue().toString()});
		}
		
		return restOfProperties;
	}
	
	public static ArrayList<String> getCoordinates(String mapping) {
		
		ArrayList<String> coordinates = new ArrayList<String>();
		
		if(mapping.contains("Error")) {
			return null;
		}
		
		String[] equivalences = mapping.split(",");
		
		for(String s: equivalences) {
			
			int subjectInit = s.indexOf("(", 0)+1; int subjectEnd = s.indexOf(")");
			int objectInit = s.indexOf("(", subjectEnd)+1; int objectEnd = s.indexOf(")", objectInit);
			
			String subject = s.substring(subjectInit, subjectEnd);
			
			if(s.contains("spatial marks")) {
				
				int objectLatLongInit = s.indexOf("(", objectEnd)+1; int objectLatLongEnd = s.length()-2;
				String object = s.substring(objectLatLongInit, objectLatLongEnd);
				System.out.println("Spatial: "+object+", "+subject);
				if(isLatLong(subject)) {
					coordinates.add(subject);
				}
				if(isLatLong(object)) {
					coordinates.add(object);
				}
				subject = s.substring(objectInit, objectEnd);
				System.out.println("Spatial1: "+object+", "+subject);
				if(isLatLong(subject)) {
					coordinates.add(subject);
				}
				
			}else if(s.contains("spatial mark")){
				String object = s.substring(objectInit, s.length()-2);
				System.out.println("Spatial2: "+object+", "+subject);
				if(isLatLong(subject)) {
					coordinates.add(subject);
				}
			}
		}
		return coordinates;
	}
	
	private static boolean isLatLong(String object) {
		
		if(object.contains("latitude") ||
				object.contains("longitude") ||
				object.contains("coordinates") ||
				object.equalsIgnoreCase("lng") ||
				object.equalsIgnoreCase("lat") ||
				object.equalsIgnoreCase("lon")
				) {
			return true;
		}
		
		return false;
	}
	
	public static JTable getPrefixes() {
		String[] prefixColumns = { "Predicate prefix", "URL"};
		JTable prefixes = new JTable(new DefaultTableModel(prefixColumns, 0));
		DefaultTableModel model = (DefaultTableModel) prefixes.getModel();
		
		model.addRow(new Object[]{"PREFIX owl:", "https://www.w3.org/2002/07/owl#"});
		
		return prefixes;
	}
}
