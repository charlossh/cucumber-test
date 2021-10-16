package converter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.Locale;

/* This class receives a date in unix format 
 * and returns it converted into YYYY/mm/dd format plus the month name in spanish
 * to use these data to search for the checkin & checkout dates in Calendar
 */
public class UnixTime {
    
    public static LinkedHashMap<String, String> getConvertedDate (long unix_time) {
    
        LinkedHashMap<String, String> resultsMap = new LinkedHashMap<String, String>();
        LocalDate convertedDate = new Timestamp (unix_time * 1000).toLocalDateTime().toLocalDate();
        
        String year = Integer.toString(convertedDate.getYear());
        String month = Integer.toString(convertedDate.getMonthValue());
        String day = Integer.toString(convertedDate.getDayOfMonth());
        String fecha = year + "/" + month + "/" + day;
        // date in field "fecha" returned to search for a date in calendar (Example: 2022/02/25) 
        resultsMap.put("fecha", fecha);
        
        Locale spanishLocale = new Locale("es", "ES");
        String monthName = convertedDate.getMonth().getDisplayName(TextStyle.FULL, spanishLocale);
        String mes = monthName.substring(0,1).toUpperCase() + monthName.substring(1) + " " + year;
        // Month name is returned in spanish with first letter capitalized and followed by the 4 digit year
        // to match the month format in calendar to search for the desired month
        resultsMap.put("mes", mes);
        
        return resultsMap;
        
    }
}
