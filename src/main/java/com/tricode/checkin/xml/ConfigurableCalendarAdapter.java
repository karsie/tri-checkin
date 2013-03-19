package com.tricode.checkin.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConfigurableCalendarAdapter extends XmlAdapter<String, Calendar> {

    private final DateFormat df;

    public ConfigurableCalendarAdapter(String format) {
        df = new SimpleDateFormat(format);
    }

    public Calendar unmarshal(String date) throws Exception {
        final Date utilDate = df.parse(date);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);

        return cal;
    }

    public String marshal(Calendar date) throws Exception {
        return df.format(date.getTime());
    }

}
