package com.solemsa.jsf.data;

import java.sql.Date;

public class CommonActions {
    
    public CommonActions(){
        
    }
    
    public String[] commitPortal(String p)
    {
        String[] arr=p.split("]");
        int n=arr.length-1;
        arr[0]=arr[0].replace("]","");//portal row number
        if(n>1)
        {
            arr[0]=arr[0].substring(1);
            System.out.println("(0): "+arr[0]);
            for(int i=1;i<n;i++)
            {
                arr[i]=arr[i].replace(",[","");
                //System.out.println("("+i+"): "+arr[i]);
            }
            arr[n]=arr[n].substring(2,arr[n].length());
            System.out.println("(n): "+arr[n]);
        }
        return arr;
    }
    
    public String[] parseCheckListValuesToStringArray(String p)
    {
        String[] arr=p.split(",");
        return arr;
    }
    
    public int[] parseCheckListValuesToIntegerArray(String p)
    {
        String[] arr=parseCheckListValuesToStringArray(p);
        int n=arr.length;
        int[] arrI=new int[n];
        for(int i=0;i<n;i++)
            arrI[i]=Integer.parseInt(arr[i]);
        return arrI;
    }
    
    public Date stringToDate(String d,String c)
    {
        if(d!=null && !d.isEmpty())
            return Date.valueOf(parseDate(d,c));
        else return null;
    }
    
    public String parseDate(String d,String c)
    {
        if(d!=null && !d.isEmpty() && !d.equals("null"))
        {
            String[]f=d.split(c);
            c=c.equals("-")?"/":"-";
            System.out.println("DATE: "+f[2]+c+f[1]+c+f[0]);
            return f[2]+c+f[1]+c+f[0];
        }
        else
            return null;
    }
    
    public String encryptString(String s)
    {
        try{
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes("UTF-8"));
            byte[] hash = md.digest();
            s=javax.xml.bind.DatatypeConverter.printHexBinary(hash).toLowerCase();
        }catch(Exception e)
        {
            System.out.println("STRING ENCRYPTION EXCEPTION: "+e);
        }
        return s;
    }
    
    public java.sql.Timestamp getCurrentTimestamp()
    {
        java.util.Date date= new java.util.Date();
        return new java.sql.Timestamp(date.getTime());
    }
    
    public Date getCurrentDate()
    {
        return new Date(new java.util.Date().getTime());
    }
    
    public String getAreaName(int Area,boolean Directivo)
    {
        String a;
        switch(Area)
        {
            case 6: a="VEN"; break;
            case 5: a="REX"; break;
            case 4: a="SEG"; break;
            case 3: a="CNT"; break;
            case 2: a="JUR"; break;
            case 1: a="AOU"; break;
            default: a=Directivo?"DVO":"";break;
        }
        return a;
    }
    
    public String getFileExtension(javax.servlet.http.Part File)
    {
        String ext[]=File.getSubmittedFileName().split("[.]");
        int n=ext.length;
        return (n>1?ext[--n]:"");
    }
    
    public boolean uploadFileToServer(javax.servlet.http.Part File,String uploads,String filename)
    {
        try{
            java.nio.file.Path pt=java.nio.file.Paths.get(javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")+uploads+"/"+filename);
            if(!pt.toFile().exists())
                java.nio.file.Files.createDirectories(pt);
            try(java.io.InputStream input = File.getInputStream())
            {
                java.nio.file.Files.copy(input,pt,java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                return true;
            }
            catch (java.io.IOException e) {
                System.out.println("CA uploadFile METHOD COPY EXCEPTION: "+e);
                e.printStackTrace();
                return false;
            }
        }
        catch(Exception e)
        {
            System.out.println("CA uploadFile METHOD PATH EXCEPTION: "+e);
            e.printStackTrace();
            return false;
        }
    }
    
    public void exportReport(String fileName,String report,java.util.List records)
    {
        Report r=new Report(report);
        r.exportReport(fileName,report.split("resources/")[0]+"resources/img/REX.png",records);
    }
    
    public java.util.ArrayList newArrayList()
    {
        return new java.util.ArrayList();
    }
    
    public java.util.ArrayList newArrayList(int size)
    {
        return new java.util.ArrayList(size);
    }
    
    public int parseStringInt(String n)
    {
        int i;
        try{
            i=Integer.parseInt(n);
        }
        catch(Exception e)
        {
            System.out.println("parseStringInt EXCEPTION:"+e);
            i=0;
        }
        return i;
    }
    
    public long parseStringLong(String n)
    {
        long i;
        try
        {
            i=Long.parseLong(n);
        }
        catch(Exception e)
        {
            System.out.println("parseStringLong EXCEPTION:"+e);
            i=0;
        }
        return i;
    }
    
    public float parseStringFloat(String n)
    {
        float i;
        try
        {
            i=Float.parseFloat(n);
        }
        catch(Exception e)
        {
            System.out.println("parseStringFloat EXCEPTION:"+e);
            i=0;
        }
        return i;
    }
    
    public String addWorkDaysToDate(Date date, long workdays)
    {
        java.time.LocalDate tmp=date.toLocalDate();
        long i=0;
        while(++i<workdays)
        {
            if(tmp.getDayOfWeek().getValue()>5)
                workdays++;
            tmp=tmp.plusDays(1);
        }
        String newDate=tmp.toString();
        return newDate;
    }
    
    public String addNaturalDaysToDate(Date date, long days)
    {
        java.time.LocalDate tmp;
        String newDate;
        try{
            tmp=date.toLocalDate();
            tmp=tmp.plusDays(days);
            newDate=tmp.toString();
        }catch(Exception e)
        {
            System.out.println("addNaturalDaysToDate EXCEPTION: "+e);
            newDate="";
        }
        return newDate;
    }
    
    public String subtractWorkDaysToDate(Date date, long workdays)
    {
        java.time.LocalDate tmp=date.toLocalDate();
        long i=0;
        while(++i<workdays)
        {
            if(tmp.getDayOfWeek().getValue()>5)
                workdays++;
            tmp=tmp.minusDays(1);
        }
        String newDate=tmp.toString();
        return newDate;
    }
    
    public String substractNaturalDaysToDate(Date date, long days)
    {
        java.time.LocalDate tmp;
        String newDate;
        try{
            tmp=date.toLocalDate();
            tmp=tmp.minusDays(days);
            newDate=tmp.toString();
            System.out.println("NEW SMALLER DATE: "+newDate);
        }catch(Exception e)
        {
            System.out.println("addNaturalDaysToDate EXCEPTION: "+e);
            newDate="";
        }
        return newDate;
        
    }
    
    public int checkDatesInPeriods(Date InitialDate1,Date FinalDate1,Date InitialDate2,Date FinalDate2)
    {
        //Regresa 21 si f2 está dentro de f1
        //Regresa 11 si f1 está dentro de f2
        //Regresa 0 si f1 y f2 no se intersectan
        //Regresa 1 si f1 y f2 son la misma fecha
        //Regresa -1 si f1 empieza antes y termina dentro de f2
        //Regresa -2 si f1 empieza dentro y termina después de f2
        //System.out.println("\t1) "+InitialDate1+","+FinalDate1);
        //System.out.println("\t2) "+InitialDate2+","+FinalDate2);
        int f=0;
        if(InitialDate1==null)
        {
            if(FinalDate1!=null)
            {
                InitialDate1=FinalDate1;
                FinalDate1=null;
            }
        }
        if(InitialDate2==null)
        {
            if(FinalDate2!=null)
            {
                InitialDate2=FinalDate2;
                FinalDate2=null;
            }
        }
        if(InitialDate1==null||InitialDate2==null)
            f=0;
        else if(FinalDate2==null)
        {
            if(FinalDate1==null)
                if(InitialDate1==InitialDate2)
                    f=1;
                else f=0;
            else
            {
                f=checkDatesInPeriods(InitialDate2,FinalDate2,InitialDate1,FinalDate1);
                switch(f){
                    case -2:f=-1;break;
                    case -1:f=-2;break;
                    case 12:f=21;break;
                    case 21:f=12;break;
                }
            }
        }
        else
        {
            //si f2 es un perido
            if(FinalDate1!=null)
            {   
                //si f1 es un periodo
                if(InitialDate1.compareTo(InitialDate2)>=0)
                    //si f1 empieza despues que f2 empieza
                    if(InitialDate1.compareTo(FinalDate2)>0)
                        //si f1 empieza fuera de f2
                        f=0;
                    else
                        //si empieza dentro de f2
                        if(FinalDate1.compareTo(FinalDate2)<=0)
                            //si f1 termina dentro de f2
                            f=12;
                        else
                            //si f1 termina fuera de f2
                            f=-2;
                else
                    //si f1 empieza antes que f2
                    if(FinalDate1.compareTo(InitialDate2)<0)
                        //si f1 termina antes que f2 empieza
                        f=0;
                    else
                        //si f1 termina después que f2 empiece
                        if(FinalDate1.compareTo(FinalDate2)<=0)
                            //si f1 termina dentro de f2
                            f=-1;
                        else
                            //si f1 termina después de f2
                            f=21;
            }
            else
            {
                //si f1 es una fecha
                if(InitialDate1.compareTo(InitialDate2)>=0)
                    //si f1 sucede después que inicie f2
                    if(InitialDate1.compareTo(FinalDate2)<=0)
                        //si f1 sucede dentro de f2
                        f=12;
                    else
                        //si f1 sucede después que f2 termine
                        f=0;
                else //si f1 sucede antes que f2 inicie
                    f=0;
            }
        }
        return f;
    }
    
    public int[] ordenarArregloNumerico(int[] n)
    {
        int aux;
        for (int i = 0; i < n.length - 1; i++) {
            for (int x = i + 1; x < n.length; x++) {
                if (n[x] < n[i]) {
                    aux = n[i];
                    n[i] = n[x];
                    n[x] = aux;
                }
            }
        }
        return n;
    }
    
}