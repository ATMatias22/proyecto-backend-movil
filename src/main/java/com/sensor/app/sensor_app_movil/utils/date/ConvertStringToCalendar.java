package com.sensor.app.sensor_app_movil.utils.date;

import com.sensor.app.sensor_app_movil.utils.date.constants.DateConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class ConvertStringToCalendar {

    private ConvertStringToCalendar(){

    }
    public static Optional<Calendar> toCalendar(String strDate) {
        Optional<Calendar> optionalCalendar = Optional.empty();
        try {
            DateFormat formatter = new SimpleDateFormat(DateConstants.FORMAT_DATE);
            Date date  = (Date) formatter.parse(strDate);;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            optionalCalendar = Optional.ofNullable(calendar);
        } catch (ParseException e) {
            System.err.println("ParseException: " + e.getMessage());
        }
        return optionalCalendar;
    }


    public static Calendar getCalendar(String date){
        //se creó este AtomicRefence para que pueda editar esta variable dentro de la lambda optionalDate.ifPresent
        AtomicReference<Calendar> calendar = new AtomicReference<>();

        Optional<Calendar> optionalDate = ConvertStringToCalendar.toCalendar(date);

        //en caso de que exista un Calendar en optionalDate entonces me setea el valor del Calendar dentro del optional
        //adentro del AtomicReference gracias a que se puede pasar un consumer podemos usar esta sintaxis

        optionalDate.ifPresent(calendar::set);
        return calendar.get();
    }

    public static Calendar getCalendarThatCanBeNull(String date){
        AtomicReference<Calendar> calendar = new AtomicReference<>();
        if (date != null) {
            Optional<Calendar> optionalDate = ConvertStringToCalendar.toCalendar(date);
            optionalDate.ifPresent(calendar::set);
        }
        return calendar.get();
    }

}