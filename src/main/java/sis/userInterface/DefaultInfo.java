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

public class DefaultInfo {

	public static final String JSONINPUT = "{\r\n"
			+ "  \"sensorData\": {\r\n"
			+ "    \"resourceId\": \"afc_node_0100_0\",\r\n"
			+ "    \"resourceType\": \"soil\",\r\n"
			+ "    \"resourceUrn\": \"urn:afc:AS03:cropsManagement:RISE:soil:afc_node_0100_0\",\r\n"
			+ "    \"latitude\": 57.9202195,\r\n"
			+ "    \"longitude\": 16.4001396,\r\n"
			+ "    \"altitude\": 0,\r\n"
			+ "    \"preprocessing\": false,\r\n"
			+ "    \"pythonScript\": \"\",\r\n"
			+ "    \"resultTime\": 1697469662,\r\n"
			+ "    \"observations\": [\r\n"
			+ "      {\r\n"
			+ "        \"observedProperty\": \"battery\",\r\n"
			+ "        \"uom\": \"https://qudt.org/vocab/unit/V\",\r\n"
			+ "        \"min_value\": 0,\r\n"
			+ "        \"max_value\": 0,\r\n"
			+ "        \"accuracy\": 0.0001\r\n"
			+ "      }\r\n"
			+ "    ],\r\n"
			+ "    \"supportedProtocol\": \"MQTT\",\r\n"
			+ "    \"hardwareVersion\": \"0100\",\r\n"
			+ "    \"softwareVersion\": \"\",\r\n"
			+ "    \"firmwareVersion\": \"Rev.23\"\r\n"
			+ "  },\r\n"
			+ "  \"multiVariableObservationData\": {\r\n"
			+ "    \"resourceId\": \"urn:afc:AS03:cropsManagement:RISE:soil:afc_node_0100_0\",\r\n"
			+ "    \"sequenceNumber\": 57898,\r\n"
			+ "    \"observations\": [\r\n"
			+ "      {\r\n"
			+ "        \"observedProperty\": \"battery\",\r\n"
			+ "        \"resultTime\": 1697469662,\r\n"
			+ "        \"result\": {\r\n"
			+ "          \"value\": 0.05210000000000001\r\n"
			+ "        }\r\n"
			+ "      }\r\n"
			+ "    ]\r\n"
			+ "  }\r\n"
			+ "}";
	
	public static final String JSONOUTPUT = "{\r\n"
			+ "  \"@graph\": [\r\n"
			+ "    {\r\n"
			+ "      \"@id\": \"https://www.w3id.org/afarcloud/pCoord?lat=57.9202195&amp;long=16.4001396\",\r\n"
			+ "      \"type\": \"Point\",\r\n"
			+ "      \"asWKT\": \"POINT(16.4001396 57.9202195)\"\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "      \"@id\": \"https://www.w3id.org/afarcloud/poi?lat=57.9202195&amp;long=16.4001396\",\r\n"
			+ "      \"type\": \"Feature\",\r\n"
			+ "      \"hasGeometry\": \"https://www.w3id.org/afarcloud/pCoord?lat=57.9202195&amp;long=16.4001396\"\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "      \"@id\": \"urn:afc:AS03:cropsManagement:RISE:soil:afc_node_0100_0\",\r\n"
			+ "      \"type\": [\r\n"
			+ "        \"SoilSensor\",\r\n"
			+ "        \"AfarcloudSensors\"\r\n"
			+ "      ]\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "      \"@id\": \"urn:afc:AS03:cropsManagement:RISE:soil:afc_node_0100_0:battery-1583311439683\",\r\n"
			+ "      \"type\": \"Observation\",\r\n"
			+ "      \"hasFeatureOfInterest\": \"https://www.w3id.org/afarcloud/poi?lat=57.9202195&amp;long=16.4001396\",\r\n"
			+ "      \"hasResult\": \"urn:afc:AS03:cropsManagement:RISE:soil:afc_node_0100_0:battery-1583311439683/q1\",\r\n"
			+ "      \"madeBySensor\": \"urn:afc:AS03:cropsManagement:RISE:soil:afc_node_0100_0\",\r\n"
			+ "      \"observedProperty\": \"https://www.w3id.org/afarcloud/battery\",\r\n"
			+ "      \"resultTime\": \"2023-10-16T17:21:02Z\"\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "      \"@id\": \"urn:afc:AS03:cropsManagement:RISE:soil:afc_node_0100_0:battery-1583311439683/q1\",\r\n"
			+ "      \"type\": \"QuantityValue\",\r\n"
			+ "      \"identifier\": \"afc_node_0100_0/battery-1583311439683/q1\",\r\n"
			+ "      \"numericValue\": \"0.05210000000000001\",\r\n"
			+ "      \"unit\": \"https://qudt.org/vocab/unit/V\"\r\n"
			+ "    }\r\n"
			+ "  ]\r\n"
			+ "}";
}
