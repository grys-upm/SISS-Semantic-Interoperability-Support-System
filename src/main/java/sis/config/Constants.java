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

package sis.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
	/* services params*/
	public static final String SRV_PARAM_LIMIT = "limit";
	
	public static final String OPEN_WEATHER_API_KEY = "Generate you own key at: https://openweathermap.org/api";
	
	public static final String SOURCE_JSON = "sourceJSON";	
	public static final String TARGET_JSON = "targetJSON";
	
	public static final String SRV_PARAM_ENTITY_NAMES = "entityNames";
	public static final String SRV_PARAM_COLLAR_IDS = "collarIDs";
	public static final String SRV_PARAM_SERVICES = "services";
	public static final String SRV_PARAM_PROVIDERS = "providers";
	public static final String SRV_PARAM_DEVICES = "devices";
	public static final String SRV_PARAM_TYPES = "types";	
	public static final String SRV_PARAM_ALTITUDE = "altitude";
	
	public static final String SRV_PARAM_OBSERVED_PROPERTIES = "obs_properties";
	
	public static final String SRV_PARAM_CSV = "csv";
	
	public static final String SRV_PARAM_START_TIME = "start_time";
	public static final String SRV_PARAM_END_TIME = "end_time";
	
	public static final String SRV_PARAM_VEHICLE_ID ="vehiclesId";
	
	//special => anomalies
	public static final String SRV_PARAM_ANOMALIES = "anomalies";
	public static final String SRV_PARAM_ALARM="alarm";
	
	//special => FROM CLAUSE 
	public static final String SRV_PARAM_MEASUREMENTS = "measurements";

	//special => ORDER CLAUSE 
	public static final String SRV_PARAM_ORDER = "order";
	public static final String SRV_PARAM_ORDER_ASC = "DESC";
	public static final String SRV_PARAM_ORDER_DESC = "ASC";

	public static final List<String> SRV_LIST_ORDER;

	
	//special => result (region)
	public static final String SRV_PARAM_REGION_RESULTS = "results";
	
	// comma separated value list
	public static final List<String> SRV_LIST_PARAMS;	
	
	// well-formed predicate
	public static final List<String> SRV_LIST_PREDICATES;
	
	// valid collar anomalies
	public static final List<String> SRV_LIST_COLLAR_ANOMALIES;
	

	// valid REGION results
	public static final List<String> SRV_LIST_REGION_RESULTS;

	
	// Proximity + georaptor: centroid + radio => 
	public static final String SRV_PARAM_CENTROID_LONGITUDE ="centr_long";
	public static final String SRV_PARAM_CENTROID_LATITUDE ="centr_lat";
	public static final String SRV_PARAM_RADIUS ="radius";
	
	
	static {
		SRV_LIST_PARAMS = new ArrayList<String> (Arrays.asList(
				new String[] {
				SRV_PARAM_ENTITY_NAMES, SRV_PARAM_SERVICES, SRV_PARAM_PROVIDERS, SRV_PARAM_DEVICES,
				SRV_PARAM_TYPES, SRV_PARAM_MEASUREMENTS, SRV_PARAM_OBSERVED_PROPERTIES,
				SRV_PARAM_VEHICLE_ID, SRV_PARAM_COLLAR_IDS
				}
				));

		SRV_LIST_ORDER = new ArrayList<String> (Arrays.asList(
				new String[] {
				SRV_PARAM_ORDER_ASC, SRV_PARAM_ORDER_DESC
				}
				));
		
		SRV_LIST_COLLAR_ANOMALIES = new ArrayList<String> (Arrays.asList(
				new String[] {
				"activity","distance","location","position","temperature"
				}
				));

		SRV_LIST_REGION_RESULTS = new ArrayList<String> (Arrays.asList(
				new String[] {
						"Weed", "DeadPlant", "WaterStress"
				}
				));
		
		SRV_LIST_PREDICATES = new ArrayList<String> (Arrays.asList(
				new String[] {
				SRV_PARAM_ANOMALIES, SRV_PARAM_REGION_RESULTS
				}
				));

	}
	
	/** (IDB) INFLUX DB tags and fields **/
	public static final int IDB_GEOHASH_PRECISION = 12;	
	public static final int IDB_QUERY_GEOHASH_PROXIMITY_PRECISION = 7;
	public static final int IDB_QUERY_GEORAPTOR_MIN = 3;
	public static final int IDB_QUERY_GEORAPTOR_MAX = 5;
	
	/* factor de precisión para distancias cortas */
	/* menores que 100 metros*/
	public static final int IDB_QUERY_GEOHASH_PROXIMITY_FACTOR_100 = 2; 
	public static final int IDB_QUERY_GEOHASH_GEORAPTOR_MAX_100 = 2;
	/* menores que 500 metros*/
	public static final int IDB_QUERY_GEOHASH_PROXIMITY_FACTOR_500 = 0;
	public static final int IDB_QUERY_GEOHASH_GEORAPTOR_MAX_500 = 1;
	
	/* common */
	public static final String IDB_SERVICE = "service";
	public static final String IDB_PROVIDER = "provider";
	public static final String IDB_TYPE = "type";
	public static final String IDB_ENTITY_NAME = "entityName";
	public static final String IDB_SCENARIO = "scenario";
	public static final String IDB_SEQUENCE_NUMBER = "sequenceNumber";
	public static final String IDB_TIME = "time";
	
	/* location */
	public static final String IDB_LOCATION_LONGITUDE ="longitude";
	public static final String IDB_LOCATION_LATITUDE ="latitude";
	public static final String IDB_LOCATION_ALTITUDE ="altitude";
	public static final String IDB_LOCATION_GEOHASH ="geohash";
	
	/* OM */
	public static final String IDB_OM_VALUE = "value";
	public static final String IDB_OM_UOM = "uom";
	
	/* Collar */
	public static final String IDB_COLLAR_MEASUREMENT = "collar";
		
	public static final String IDB_COLLAR_TEMPERATURE = "temperature";
	public static final String IDB_COLLAR_RESOURCEALARM = "resourceAlarm";
	public static final String IDB_COLLAR_ANOMALY_ACTIVITY = "activityAnomaly";
	public static final String IDB_COLLAR_ANOMALY_DISTANCE = "distanceAnomaly";
	public static final String IDB_COLLAR_ANOMALY_LOCATION = "locationAnomaly";
	public static final String IDB_COLLAR_ANOMALY_POSITION = "positionAnomaly";
	public static final String IDB_COLLAR_ANOMALY_TEMPERATURE = "temperatureAnomaly";
	
	public static final String IDB_COLLAR_ACC_X = "accX";
	public static final String IDB_COLLAR_ACC_Y = "accY";
	public static final String IDB_COLLAR_ACC_Z = "accZ";
	
	/* vehicle */
	public static final String IDB_VEHICLE_MEASUREMENT = "vehicle";
	
	public static final String IDB_VEHICLE_ID = "vehicleId";
	
	public static final String IDB_VEHICLE_ORIENTATION_ROLL = "roll";
	public static final String IDB_VEHICLE_ORIENTATION_PITCH = "pitch";
	public static final String IDB_VEHICLE_ORIENTATION_YAW = "yaw";
	
	public static final String IDB_VEHICLE_BATTERY_CAPACITY = "batteryCapacity";
	public static final String IDB_VEHICLE_BATTERY_PERCENTAGE = "batteryPercentage";
	
	public static final String IDB_VEHICLE_LINEAR_SPEED = "linearSpeed";
	
	
	/* Region */
	public static final String IDB_REGION_MEASUREMENT = "region";
	
	public static final String IDB_REGION_OBSERVED_PROPERTY = "observedProperty";
	public static final String IDB_REGION_OBSERVED_RESULT = "result";
	public static final String IDB_REGION_GEO_NUMPOINTS = "geo_numPoint";
	public static final String IDB_REGION_GEO_COORDINATES = "geo_coordinates";
	
	
	
	
	/** (MySQL) MySQL TABLES AND COLUMNS **/		
	/* common - location */
	public static final String MySQL_LOCATION_LONGITUDE ="longitude";
	public static final String MySQL_LOCATION_LATITUDE ="latitude";
	public static final String MySQL_LOCATION_ALTITUDE ="altitude";
	public static final String MySQL_TIME = "lastUpdate";
	
	/* OM */
	public static final String MySQL_OM_TABLE = "lastMeasurement";
	public static final String MySQL_OM_OBSERVED_PROPERTY = "observedProperty";
	public static final String MySQL_OM_TYPE = "resourceType";
	public static final String MySQL_OM_ENTITY_NAME = "resourceId";
	public static final String MySQL_OM_VALUE = "lastValue";
	public static final String MySQL_OM_UOM = "uom";

	// OM colummns
	public static final List<String> MySQL_LIST_OM_COLUMNS;
	static {
		 MySQL_LIST_OM_COLUMNS = new ArrayList<String> (Arrays.asList(
				new String[] {
						MySQL_OM_ENTITY_NAME, MySQL_OM_OBSERVED_PROPERTY, MySQL_OM_TYPE,
						MySQL_LOCATION_LONGITUDE, MySQL_LOCATION_ALTITUDE, MySQL_LOCATION_LATITUDE,
						MySQL_TIME, MySQL_OM_VALUE, MySQL_OM_UOM
				}
			));
	}
	
	/* Collar */
	public static final String MySQL_COLLAR_TABLE = "lastCollar";
	public static final String MySQL_COLLAR_ID = "collarId";
	public static final String MySQL_COLLAR_TEMPERATURE = "temperature";
	public static final String MySQL_COLLAR_RESOURCEALARM = "resourceAlarm";
	public static final String MySQL_COLLAR_ANOMALY_LOCATION = "locationAnomaly";
	public static final String MySQL_COLLAR_ANOMALY_TEMPERATURE = "temperatureAnomaly";
	public static final String MySQL_COLLAR_ANOMALY_DISTANCE ="distanceAnomaly";
	public static final String MySQL_COLLAR_ANOMALY_POSITION = "positionAnomaly";	

	// COLLAR colummns
	public static final List<String> MySQL_LIST_COLLAR_COLUMNS;
	static {
		 MySQL_LIST_COLLAR_COLUMNS = new ArrayList<String> (Arrays.asList(
				new String[] {
						MySQL_COLLAR_ID,
						MySQL_LOCATION_LONGITUDE, MySQL_LOCATION_ALTITUDE, MySQL_LOCATION_LATITUDE,
						MySQL_COLLAR_TEMPERATURE, MySQL_COLLAR_RESOURCEALARM,
						MySQL_COLLAR_ANOMALY_LOCATION, MySQL_COLLAR_ANOMALY_TEMPERATURE, MySQL_COLLAR_ANOMALY_DISTANCE, MySQL_COLLAR_ANOMALY_POSITION, 
						MySQL_TIME,
				}
			));
	}

}
