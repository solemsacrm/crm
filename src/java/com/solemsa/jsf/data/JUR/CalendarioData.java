package com.solemsa.jsf.data.JUR;

public class CalendarioData
{
    public static int getNumberOfDaysInMonth(int month,int year)
    {
        while(month>12)
        {
            month-=12;
        }
        switch(month)
        {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: month=31;break;
            case 4: case 6: case 9: case 11: month=30;break;
            case 2: if(((float)year)%4!=0)month=28;else month=29;break;
        }
        return month;
    }
}
