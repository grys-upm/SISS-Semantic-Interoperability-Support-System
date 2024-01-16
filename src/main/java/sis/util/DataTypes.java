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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * @proyect Research
 * 
 * @author Mario San Emeterio
 * @date 2023
 */


public class DataTypes {
	private static final List<SimpleDateFormat> PARAMS_DATE_FORMAT;
	
	static {
		// IMPORTANT!! order: more to less specific
		PARAMS_DATE_FORMAT = new ArrayList<SimpleDateFormat> (
						Arrays.asList(new SimpleDateFormat [] {			
								new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
								new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),								
								new SimpleDateFormat("yyyy-MM-dd HH:mm"),
								new SimpleDateFormat("yyyy-MM-dd HH"),								
								new SimpleDateFormat("yyyy-MM-dd")						
							}
						));
	}
	
	
	   public static java.util.Date dGetTimestamp(String sDate) throws Exception {
	        if (sDate == null) {
	            return null;
	        }
	        	        
	        // tratamiento de la fecha
	        java.util.Date dResult = null;

	        for (int i=0;i<PARAMS_DATE_FORMAT.size();i++) {
	            try {
	                dResult = ((SimpleDateFormat)PARAMS_DATE_FORMAT.get(i)).parse(sDate, new ParsePosition(0));
	            } catch (Exception e) {
	                // No hacemos nada... simplemente este no es su formato
	            }
	            if (dResult != null) {
	                break;
	            }
	        }

	        return dResult;
	    }

	   public static boolean bIsTimestamp(String sDate) {	        
	        try {
				return dGetTimestamp(sDate)!=null;
			} catch (Exception e) {
				return false;
			}
	    }

	   public static String stringDateToTimestamp(String sDate) throws Exception {
	    	SimpleDateFormat dateDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    	//SimpleDateFormat dateDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	
	        //dateDF.setTimeZone(TimeZone.getTimeZone("UTC"));
	        
	    	java.util.Date dDate = DataTypes.dGetTimestamp(sDate);
	    		    	
	    	if (dDate!= null)
	    		return dateDF.format(dDate);
	    	
	    	return null;
	    	
	    }	   

	   
	   public static String epochSecondToStringDate(long nEpoch) {
		   return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new java.util.Date (nEpoch*1000));
	   }
	   
	   
	   public static long stringDateToEpochSecond(String sDate) {
	    	SimpleDateFormat dateDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    	
	    	java.util.Date dDate = dateDF.parse(sDate, new ParsePosition(0));
//	    		    	System.out.println("fecha: "+dDate);
	    	if (dDate!= null) {
//	    		System.out.println("second: "+dDate.toInstant().getEpochSecond());
	    		return dDate.toInstant().getEpochSecond();
	    	}
	    	return 0;	    	
	    }	   
	   
	    
	    /**
	     * calcula la diferencia en horas entre dos fechas
	     * si estas distan más de un día devuelve Interger.MAX_VALUE
	     * @param cCalPrev
	     * @param cCalCurrent
	     * @return
	     * @throws Exception
	     */
	    static public int nComputeDiffHour(Calendar cCal1, Calendar cCal2) throws Exception{
			int nHour = Integer.MAX_VALUE;

	    	Calendar cCalPrev, cCalCurrent;
	    	if (cCal1.compareTo(cCal2)<0){
	    		cCalPrev = cCal1;
	    		cCalCurrent = cCal2;
	    	}else{
	    		cCalPrev = cCal2;
	    		cCalCurrent = cCal1;
	    	}
			/* un día de diferencia */
			if ( (
					(cCalPrev.get(Calendar.YEAR) == cCalCurrent.get(Calendar.YEAR)) &&
					(cCalPrev.get(Calendar.DAY_OF_YEAR) +1 == cCalCurrent.get(Calendar.DAY_OF_YEAR))
				  ) ||
				  (
					cCalPrev.get(Calendar.YEAR)+1 == cCalCurrent.get(Calendar.YEAR) &&
					cCalPrev.get(Calendar.MONTH)==Calendar.DECEMBER &&
					cCalPrev.get(Calendar.DAY_OF_MONTH) == 31 &&
					cCalCurrent.get(Calendar.MONDAY) == Calendar.JANUARY &&
					cCalCurrent.get(Calendar.DAY_OF_MONTH) == 1
				  )
				){
				nHour = 24 -cCalPrev.get(Calendar.HOUR_OF_DAY) + cCalCurrent.get(Calendar.HOUR_OF_DAY); 
			}else
			/* mismo dia */
			if (	
					(cCalPrev.get(Calendar.YEAR) == cCalCurrent.get(Calendar.YEAR)) &&
					(cCalPrev.get(Calendar.DAY_OF_YEAR) == cCalCurrent.get(Calendar.DAY_OF_YEAR))
				){
				nHour = cCalCurrent.get(Calendar.HOUR_OF_DAY) - cCalPrev.get(Calendar.HOUR_OF_DAY);
			}
			return nHour;
	    }
	   
	    
		public static void main(String[] args)  throws Exception{
	    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    	Calendar cCal1 = Calendar.getInstance(Locale.FRANCE);
	    	cCal1.set(2014, 12, 31, 16, 0);
	    	Calendar cCal2 = Calendar.getInstance(Locale.FRANCE);
	    	cCal2.set(2015, 1, 1, 15, 0);
	    	System.out.println(sdf.format(cCal1.getTime()) + " :: " +sdf.format(cCal1.getTime()) + " => " + DataTypes.nComputeDiffHour(cCal1, cCal2) );
	    	
	    	cCal1.set(2014, 12, 31, 16, 0);
	    	cCal2.set(2015, 2, 1, 15, 0);
	    	System.out.println(sdf.format(cCal1.getTime()) + " :: " +sdf.format(cCal1.getTime()) + " => " + DataTypes.nComputeDiffHour(cCal1, cCal2) );
	    	
	    	cCal1.set(2015, 1, 2, 16, 0);
	    	cCal2.set(2015, 1, 1, 15, 0);
	    	System.out.println(sdf.format(cCal1.getTime()) + " :: " +sdf.format(cCal1.getTime()) + " => " + DataTypes.nComputeDiffHour(cCal1, cCal2) );
	    	
	    	/***** TimeZone ****/ 
	    	System.out.println("\n tz:: " + TimeZone.getDefault().getDisplayName() + "\n" + TimeZone.getDefault().getID());
	    	
	    	System.out.println("\n tzs:: " +  new ArrayList<String>(Arrays.asList(TimeZone.getAvailableIDs())).toString().replaceAll(",","\n\t")
	    			);
	    	
	    	TimeZone oTZ = TimeZone.getTimeZone("Europe/Paris");
	    	System.out.println(oTZ.getID());
	    	
	        
	    	long epoch = DataTypes.stringDateToEpochSecond("2020-06-10T10:34:14Z");
	    	System.out.println("\nepoch: " + epoch);
	    	System.out.println("\n" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new java.util.Date (epoch*1000)));
	    	System.out.println("\n" + DataTypes.epochSecondToStringDate(epoch));
	    	

	    	System.out.println("\n" + Long.MAX_VALUE +"\n" + DataTypes.epochSecondToStringDate(Long.MAX_VALUE/10000));
	    	
	    }
}
