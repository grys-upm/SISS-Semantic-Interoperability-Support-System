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

package sis.util;

import java.util.ArrayList;
import java.util.ResourceBundle;


public class GlobalParameters {
	public static final String AFC_SCENARIO = "afc.scenario";
	public static final String AFC_SQUEMAS = "afc.schemas";
	
	//JSONLD Imput Folder
	public static final String JsonldLocation="translator.imputFolder";
	public static final String AIM_Context = "aim.context";
	
	//DS:: DATASOURCE - INFLUX DB
	public static final String DS_IDB_SERVER = "datasource.influxDB.accessPoint";
	public static final String DS_IDB_PORT = "datasource.influxDB.port";
	public static final String DS_IDB_USER = "datasource.influxDB.username";
	public static final String DS_IDB_PASSWORD = "datasource.influxDB.password";

	//DS:: DATASOURCE - MySQL
	public static final String DS_MySQL_SERVER = "datasource.MySQL.accessPoint";
	public static final String DS_MySQL_PORT = "datasource.MySQL.port";
	public static final String DS_MySQL_USER = "datasource.MySQL.username";
	public static final String DS_MySQL_PASSWORD = "datasource.MySQL.password";
	public static final String DS_MySQL_DRIVER = "datasource.MySQL.driver";
	
	public static final String DS_MySQL_DBNAME = "datasource.MySQL.DBName";
	
	//PREFIX measurement/tables
	public static final String PREFIX_OBSERVATION = "datastore.prefix.observation";
	public static final String PREFIX_COLLAR = "datastore.prefix.collar";
	public static final String PREFIX_REGION = "datastore.prefix.region";
	public static final String PREFIX_VEHICLE = "datastore.prefix.vehicle";
	public final ArrayList<String> LIST_PREFIX;
	
	public static final String PREFIX_AGGREGATION = "datastore.prefix.aggregation";
	public final ArrayList<String> LIST_PREFIX_TO_EXCLUDE;
	
	
	//RES SERVER
	public static final String RS_RESPONSE_MAX_OBSERVATIONS = "rs.response.observations.max";
	public static final String RS_RESPONSE_MAX_OBSERVATIONS_BY_UID = "rs.response.observations.max_by_uid";
	public static final String RS_RESPONSE_MAX_PROXIMITY_RADIO = "rs.response.proximity.radio.max";
	public static final String RS_RESPONSE_SELECT_OBSERVATION = "rs.response.select.observation";
	public static final String RS_RESPONSE_SELECT_COLLAR = "rs.response.select.collar";
	public static final String RS_RESPONSE_SELECT_VEHICLE = "rs.response.select.vehicle";
	public static final String RS_RESPONSE_SELECT_REGION = "rs.response.select.region";
	
	
	static private GlobalParameters oInstance = null;
	
	private ResourceBundle oResourceBundle = null;
	
	/* maven proyect: default under main/resources */ 
	private final String sFileProperties = "DEMETER.AIM2AFC.config.Translator_Params";
	
	/**
	 * Constructor privado, ya que la clase va a implementar el patrón singlenton para
	 * no tener en memoria varios GlobalParams
	 * @throws Exception 
	 */
	private GlobalParameters() throws Exception {
		super();

		try{
			oResourceBundle = ResourceBundle.getBundle(sFileProperties);
			LIST_PREFIX = new ArrayList<String>();
			LIST_PREFIX.add(getParameter(PREFIX_COLLAR));
			LIST_PREFIX.add(getParameter(PREFIX_OBSERVATION));
			LIST_PREFIX.add(getParameter(PREFIX_REGION));			
			LIST_PREFIX.add(getParameter(PREFIX_VEHICLE));
			
			LIST_PREFIX_TO_EXCLUDE = new ArrayList<String>();
			LIST_PREFIX_TO_EXCLUDE.add(getParameter(PREFIX_AGGREGATION));
			
		}catch (Exception ex){
			System.err.println(
					"$ERROR$DBM$LOAD_PROPERTIES:: filename '" + this.sFileProperties + "'\n " +
					"There must be in the CLASSPATH" +
					ex.getMessage()
					);
			throw(ex);
		}
	}
	
	/**
	 * singlenton pattern
	 */
	
	public static synchronized  GlobalParameters getInstance(){
		if (oInstance == null){
			try {
				oInstance = new GlobalParameters();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return oInstance;		
	}
	
	
	public String getParameter (String sParam){
		try {
			return oResourceBundle.getString(sParam);
		}catch (Exception  e) {
			return "";
		}
	}

	public int getIntObservations (String sParam, int nDefValue){
		int nReturn;
		try {
			nReturn = Integer.parseInt(oResourceBundle.getString(sParam));
		}catch (Exception  e) {
			nReturn = nDefValue;
		}
		
		return nReturn;
	}

	/** **********************
	 * Main
	 * ************************* */
	 public static void main(String[] args){
		 System.out.println("\n" + GlobalParameters.getInstance().getParameter("uno"));
	 
	 }
}
