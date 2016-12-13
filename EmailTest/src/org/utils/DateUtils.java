package org.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Date Utils
 */
public class DateUtils {

    public static final String YYYY_MM_DD = "dd-MM-yyyy";

    public static final String HH_MM_SS = "HH:mm:ss";

    public static final String HH_MM = "HH:mm";

    public static final String YYYY_MM_DD_HH_MM_SS = "dd-MM-yyyy HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM = "dd-MM-yyyy HH:mm";

    public static final String YYMMDDHHMMSS = "yyMMddHHmmss";

    public static final String MMDDHHMMSS = "MMddHHmmss";

    public static final String YYYY = "yyyy";

    /**
     * Return Current Date String
     *
     * @param pattern : Date format
     * @return : String
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static String getDate(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static Date getToday() {
        return format(getDate(DateUtils.YYYY_MM_DD));
    }

    /**
     * Return Date String
     *
     * @param date : Date
     * @return : String
     */
    public static String format(Date date) {
        return format(date, "dd-MM-yyyy HH:mm:ss");
    }

    public static String convertDateToString(Date date) {
        String result = format(date);
        String hhmmss = format(date, HH_MM_SS);
        if (hhmmss.equalsIgnoreCase("00:00:00")) {
            result = format(date, YYYY_MM_DD);
        }
        return result;
    }

    /**
     * Return Date String
     *
     * @param date : Date
     * @return : String
     */
    public static String formatShortDate(Date date) {
        if (date == null) {
            return null;
        }
        return format(date, "dd-MM-yyyy");
    }

    /**
     * Return Date String
     *
     * @param date    : Date
     * @param pattern : Date Format
     * @return : String
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "null";
        }
        if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = "dd-MM-yyyy HH:mm:ss";
        }
        return new java.text.SimpleDateFormat(pattern).format(date);
    }

    /**
     * Return Date
     *
     * @param date : Date String
     * @return : Date
     */
    public static Date format(String date) {
        if (StringUtils.isNotEmpty(date)) {
            if (date.length() == 5) {
                return format(date, HH_MM);
            } else if (date.length() == 8) {
                return format(date, HH_MM_SS);
            } else if (date.length() == 10) {
                return format(date, YYYY_MM_DD);
            } else if (date.length() == 16) {
                return format(date, YYYY_MM_DD_HH_MM);
            } else if (date.length() == 19) {
                return format(date, YYYY_MM_DD_HH_MM_SS);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Return Date
     *
     * @param date : Date String
     * @return : Date
     */
    public static Date formatNoTime(String date) {
        return format(date, "dd-MM-yyyy");
    }


    public static Date formatToDate(String date) {
        if (StringUtils.isNotEmpty(date)) {
            return format(date, "dd-MM-yyyy");
        }
        return null;
    }


    public static String formatStringDate(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, "dd-MM-yyyy");
    }

    public static String formatStringTime(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, "dd-MM-yyyy HH:mm");
    }

    /**
     * Return Date
     *
     * @param date : Date String
     * @return : Date
     */
    public static Date formatTime(String date) {
        return format(date, "hh:mm:ss");
    }

    /**
     * Return Date
     *
     * @param date : Date String
     * @return : Date
     */
    public static Date formatTimeNoSs(String date) {
        return format(date, "HH:mm");
    }

    /**
     * Return Date
     *
     * @param date : Date String
     * @return : Date
     */
    public static Date formatWithHHmm(String date) {
        return format(date, "dd-MM-yyyy HH:mm");
    }

    /**
     * Return Date
     *
     * @param date    : Date String
     * @param pattern : Date Format
     * @return : Date
     */
    public static Date format(String date, String pattern) {
        if (pattern == null || pattern.equals("") || pattern.equals("null") || StringUtils.isBlank(pattern)) {
            pattern = "dd-MM-yyyy HH:mm:ss";
        }
        if (date == null || date.equals("") || date.equals("null")) {
            return new Date();
        }
        Date d = null;
        try {
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
            format.setLenient(false);
            d = format.parse(date);
        } catch (ParseException pe) {
        }
        return d;
    }

    public static Date formatWithoutEmptyString(String date, String pattern) {
        if (pattern == null || pattern.equals("") || pattern.equals("null") || StringUtils.isBlank(pattern)) {
            pattern = "dd-MM-yyyy HH:mm:ss";
        }
        if (StringUtils.isBlank(date)) {
            return null;
        }
        Date d = null;
        try {
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
            format.setLenient(false);
            d = format.parse(date);
        } catch (ParseException pe) {
        }
        return d;
    }

    /**
     * Get Date List By From And To And Week List
     *
     * @param dateFrom
     * @param dateTo
     * @param weekList : 1 - 7 : Mon - Sun
     * @return
     */
    public static List getDateList(Date dateFrom, Date dateTo, List<Integer> weekList) {
        List dateList = new ArrayList();
        Calendar start = Calendar.getInstance();
        start.setTime(dateFrom);
        Calendar end = Calendar.getInstance();
        end.setTime(dateTo);
        while (start.compareTo(end) <= 0) {
            int w = start.get(Calendar.DAY_OF_WEEK);
            if (w == 1) {
                w = 7;
            } else {
                w = w - 1;
            }
            if (weekList.contains(w)) {
                dateList.add(format(start.getTime(), "dd-MM-yyyy"));
            }
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
        }
        return dateList;
    }

    /**
     * Get Date List
     *
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public static List getDateList(Date dateFrom, Date dateTo) {
        List dateList = new ArrayList();
        Calendar start = Calendar.getInstance();
        start.setTime(dateFrom);
        Calendar end = Calendar.getInstance();
        end.setTime(dateTo);
        while (start.compareTo(end) <= 0) {
            dateList.add(format(start.getTime(), "dd-MM-yyyy"));
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
        }
        return dateList;
    }

    public static List getYearMonthList(Date dateFrom, Date dateTo) {
        List dateList = new ArrayList();
        Calendar start = Calendar.getInstance();
        start.setTime(dateFrom);
        Calendar end = Calendar.getInstance();
        end.setTime(dateTo);
        while (start.compareTo(end) <= 0) {
            dateList.add(format(start.getTime(), "yyyyMM"));
            start.set(Calendar.MONTH, start.get(Calendar.MONTH) + 1);
        }
        return dateList;
    }

    public static List getYearMonthNameList(Date dateFrom, Date dateTo) {
        List dateList = new ArrayList();
        Calendar start = Calendar.getInstance();
        start.setTime(dateFrom);
        Calendar end = Calendar.getInstance();
        end.setTime(dateTo);
        while (start.compareTo(end) <= 0) {
            dateList.add(format(start.getTime(), "MMM-yy"));
            start.set(Calendar.MONTH, start.get(Calendar.MONTH) + 1);
        }
        return dateList;
    }

    public static int getYear(Date date) {
        return getCalendar(date).get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        return getCalendar(date).get(Calendar.MONTH);
    }

    public static int getDay(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getDiffYear(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
                || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))
                ) {
            diff--;
        }
        return diff;
    }

    public static int[] getDiffYearMonthDay(Date first, Date last) {
        int[] monthDay = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int increment = 0, year, month, day;
        int[] ageDiffArr = new int[3];

        Calendar fromDate = getCalendar(first);
        Calendar toDate = getCalendar(last);

        if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
            increment = monthDay[fromDate.get(Calendar.MONTH)];
        }

        boolean isLeapYear = (fromDate.get(Calendar.YEAR) % 4 == 0 && (fromDate.get(Calendar.YEAR) % 100 != 0) || (fromDate.get(Calendar.YEAR) % 400 == 0));

        if (increment == -1) {
            if (isLeapYear) {
                increment = 29;
            } else {
                increment = 28;
            }
        }

        //Calculate day
        if (increment != 0) {
            day = toDate.get(Calendar.DAY_OF_MONTH) + increment - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }

        //calculate month
        if (fromDate.get(Calendar.MONTH) + increment > toDate.get(Calendar.MONTH)) {
            month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

        //calculate year
        year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);

        //set result
        ageDiffArr[0] = day;
        ageDiffArr[1] = month;
        ageDiffArr[2] = year;

        return ageDiffArr;
    }

    public static String getDiffYMDString(String first, String last) {
        String result = "";
        if (StringUtils.isNotEmpty(first) && StringUtils.isNotEmpty(last)) {
            int[] age = getDiffYearMonthDay(formatToDate(first), formatToDate(last));
            int year = age[2], month = age[1], day = age[0];
            if (year > 0) {
                if (year > 1) {
                    result = year + " years ";
                } else {
                    result = year + " year ";
                }
            }
            double months = month;
            if (day > 15) {
                if (month > 0) {
                    result += (months + 0.5) + " months ";
                } else {
                    result += "0.5 month ";
                }
            } else {
                if (month > 0) {
                    result += month + " months ";
                }
            }
        }
        return result;
    }

    /**
     * Get current month date list
     *
     * @param currentDate
     * @return
     */
    public static List getDateList(Date currentDate) {
        Date dateFrom = getMonthFirstDay(currentDate);
        Date dateTo = getMonthLastDay(currentDate);
        return getDateList(dateFrom, dateTo);
    }

    /**
     * Get Week By Date //1-7 : Mon - Sun
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static int dayForWeek(Date date) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    public static int weekForMonth(Date date) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    public static Date getDateForNextMonth(Date date) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        int weekForMonth = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month + 1);
        c.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekForMonth);
        c.set(Calendar.DAY_OF_WEEK, dayForWeek);
        Date nextDay = c.getTime();
        Date monthFirstDay = getMonthFirstDay(nextDay);
        Date monthLastDay = getMonthLastDay(nextDay);
        if (nextDay.getTime() >= monthFirstDay.getTime() && nextDay.getTime() <= monthLastDay.getTime()) {
            return nextDay;
        } else {
            return null;
        }
    }

    /**
     * Get Month First Day
     *
     * @param month : yyyyMM
     * @return
     */
    public static Date getMonthFirstDay(String month) {
        Date firstDay = format(month + "01", "yyyyMMdd");
        return firstDay;
    }

    public static Date getMonthFirstDay(Date date) {
        String month = format(date, "yyyyMM");
        return getMonthFirstDay(month);
    }

    /**
     * Get Month Last Day
     *
     * @param month : yyyyMM
     * @return
     */
    public static Date getMonthLastDay(String month) {
        Date lastDay = org.apache.commons.lang3.time.DateUtils.addDays(org.apache.commons.lang3.time.DateUtils.addMonths(getMonthFirstDay(month), 1), -1);
        return lastDay;
    }

    public static Date getMonthLastDay(Date date) {
        String month = format(date, "yyyyMM");
        return getMonthLastDay(month);
    }

    /**
     * Get Quarter End Day
     *
     * @param date : yyyyMMdd
     * @return
     */
    public static Date getQuarterEndDay(Date date) {
        Calendar currentDate = getCalendar(date);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                currentDate.set(Calendar.MONTH, 2);
                currentDate.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                currentDate.set(Calendar.MONTH, 5);
                currentDate.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                currentDate.set(Calendar.MONTH, 8);
                currentDate.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                currentDate.set(Calendar.MONTH, 11);
                currentDate.set(Calendar.DATE, 31);
            }
            now = new java.text.SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(format(currentDate.getTime(), YYYY_MM_DD_HH_MM_SS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * Get Quarter Start Day
     *
     * @param date : yyyyMMdd
     * @return
     */
    public static Date getQuarterStartDay(Date date) {
        Calendar currentDate = getCalendar(date);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int nextYear = currentDate.get(Calendar.YEAR) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                currentDate.set(Calendar.MONTH, 3);
                currentDate.set(Calendar.DATE, 1);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                currentDate.set(Calendar.MONTH, 6);
                currentDate.set(Calendar.DATE, 1);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                currentDate.set(Calendar.MONTH, 9);
                currentDate.set(Calendar.DATE, 1);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                currentDate.set(Calendar.MONTH, 0);
                currentDate.set(Calendar.DATE, 1);
                currentDate.set(Calendar.YEAR, nextYear);
            }
            now = new java.text.SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(format(currentDate.getTime(), YYYY_MM_DD_HH_MM_SS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }


    /**
     * Get Hours By Start Date And End Date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static BigDecimal getHoursByDateRange(Date startDate, Date endDate) {
        long betweenMinutes = ((endDate.getTime() - startDate.getTime()) / 1000) / 60;
        BigDecimal hours = new BigDecimal(betweenMinutes / 60);
        return hours;
    }

    public static boolean isWeekday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        } else {
            return true;
        }
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static long getDateDiffInDay(Date date1, Date date2) {
        return getDateDiff(date1, date2, TimeUnit.DAYS);
    }

    public static boolean isEqual(Date date1, Date date2, String pattern) {
        boolean result = false;
        if (date1 != null && date2 != null
                && format(date1, pattern).equalsIgnoreCase(format(date2, pattern))
                ) {
            result = true;
        }
        return result;
    }

    public static Date getDateWithoutTime(Date date) {
        if (date == null) {
            return null;
        }
        return format(format(date, YYYY_MM_DD), YYYY_MM_DD);
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
