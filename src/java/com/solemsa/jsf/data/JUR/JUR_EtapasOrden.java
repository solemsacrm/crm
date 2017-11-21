package com.solemsa.jsf.data.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_EtapaDetalle;
import com.solemsa.hibernate.entities.JUR.JUR_Etapas;
import com.solemsa.hibernate.entities.JUR.JUR_Fechas;
import com.solemsa.hibernate.entities.JUR.JUR_Notificaciones;
import com.solemsa.hibernate.entities.JUR.JUR_Tareas;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Date;
import org.joda.time.Days;
import org.joda.time.LocalDate;

public class JUR_EtapasOrden
{
    public static String getNextEtapa(JUR_Casos caso)
    {
        String siguiente="";
        switch(caso.getJuzgado())
        {
            case "Civil":siguiente=getEtapaGeneral(caso.getJUR_EtapasLength(),caso.getPapelCliente());break;
            case "Laboral":siguiente=getEtapaGeneral(caso.getJUR_EtapasLength(),caso.getPapelCliente());break;
            case "Menor Civil":siguiente=getEtapaGeneral(caso.getJUR_EtapasLength(),caso.getPapelCliente());break;
            case "Oralidad Familiar":siguiente=getEtapaOralidad(caso);break;
            case "Oralidad Mercantil":siguiente=getEtapaOralidad(caso);break;
            case "Penal":siguiente=getEtapaPenal(caso.getJUR_EtapasLength(),caso.getPapelCliente());break;
        }
        return siguiente;
    }
    
    private static String getEtapaGeneral(int etapaActual,String papelCliente)
    {
        String e;
        switch(etapaActual)
        {
            case 0:e=papelCliente.equals("Demandante")?"Demanda":"Contestación";break;
            case 1:e=papelCliente.equals("Demandante")?"Juzgados":"Ofrecimiento de Pruebas";break;
            case 2:e=papelCliente.equals("Demandante")?"Ofrecimiento de Pruebas":"Desahogo de Pruebas";break;
            case 3:e=papelCliente.equals("Demandante")?"Desahogo de Preubas":"Alegatos";break;
            case 4:e=papelCliente.equals("Demandante")?"Alegatos":"Sentencia o Laudo";break;
            case 5:e=papelCliente.equals("Demandante")?"Sentencia o Laudo":"Apelación";break;
            case 6:e=papelCliente.equals("Demandante")?"Apelación":"Amparo";break;
            case 7:e=papelCliente.equals("Demandante")?"Amparo":"Revisión";break;
            case 8:e=papelCliente.equals("Demandante")?"Revisión":"";break;
            default:e="";break;
        }
        return e;
    }
    
    private static String getEtapaPenal(int etapaActual,String papelCliente)
    {
        String e;
        switch(etapaActual)
        {
            case 0:e=papelCliente.equals("Denunciante")?"Denuncia":"Contestación";break;
            case 1:e="Investigación";break;
            case 2:e="Aportación de Pruebas";break;
            case 3:e="Vinculación al Proceso";break;
            case 4:e="Alegatos de Apertura";break;
            case 5:e="Ofrecimiento de Pruebas";break;
            case 6:e="Desahogo de Pruebas";break;
            case 7:e="Alegatos de Clausura";break;
            case 8:e="Sentencia o Laudo";break;
            case 9:e="Apelación";break;
            case 10:e="Amparo";break;
            case 11:e="Revisión";break;
            default:e="";break;
        }
        return e;
    }
    
    private static String getEtapaOralidad(JUR_Casos caso)
    {
        String e;
        int etapaActual=caso.getJUR_EtapasLength();
        if(etapaActual<1)
            e="Audiencia de Conciliación";
        else
        {
            String papelCliente=caso.getPapelCliente();
            switch(etapaActual)
            {
                case 1:e="Audiencia Preliminar"+(papelCliente.equals("Demandante")?" (Demanda)":" (Contestación)");break;
                case 2:e=papelCliente.equals("Demandante")?"Juzgados":"Audiencia de Juicio (Ofrecimiento de Pruebas)";break;
                case 3:e=papelCliente.equals("Demandante")?"Audiencia de Juicio (Ofrecimiento de Pruebas)":"Audiencia de Juicio (Desahogo de Pruebas)";break;
                case 4:e=papelCliente.equals("Demandante")?"Audiencia de Juicio (Desahogo de Preubas)":"Alegatos";break;
                case 5:e=papelCliente.equals("Demandante")?"Alegatos":"Sentencia o Laudo";break;
                case 6:e=papelCliente.equals("Demandante")?"Sentencia o Laudo":"Apelación";break;
                case 7:e=papelCliente.equals("Demandante")?"Apelación":"Amparo";break;
                case 8:e=papelCliente.equals("Demandante")?"Amparo":"Revisión";break;
                case 9:e=papelCliente.equals("Demandante")?"Revisión":"";break;
                default:e="";break;
            }
        }
        return e;
    }
    
    public static JUR_EtapaDetalle setDefaultPruebasDetalle(JUR_Etapas etapa,java.util.List<JUR_Fechas>lista,String usu,CommonActions ca)
    {
        System.out.println("setDefaultPruebasDetalle METHOD REACHED");
        JUR_EtapaDetalle tmp=new JUR_EtapaDetalle();
        tmp.setJUR_Etapa(etapa);
        tmp.setFecha(etapa.getJUR_Caso().getPurebasFecha());
        tmp.setFechaString(ca.parseDate(tmp.getFecha().toString(),"-"));
        tmp.setDuracion(30);
        tmp.setUnidadTiempo("Días");
        tmp.setAsuntosTratados("Inicio del periodo para preubas");
        tmp.setZz_FechaCreacion(ca.getCurrentTimestamp());
        tmp.setZz_FechaModificacion(tmp.getZz_FechaCreacion());
        tmp.setZz_UsuarioCreacion(usu);
        tmp.setZz_UsuarioModificacion(usu);
        JUR_Notificaciones tmpN=new JUR_Notificaciones();
        tmpN.setFechaInicio(tmp.getFecha());
        tmpN.setFechaInicioString(tmp.getFechaString());
        tmpN.setFechaFinString(ca.parseDate(addWorkDaysToDate(tmp.getFecha(),30,lista,ca),"-"));
        tmpN.setFechaFin(ca.stringToDate(tmpN.getFechaFinString(),"/"));
        tmpN.setAsunto("Periodo de pruebas de "+etapa.getJUR_Caso().getNombre());
        tmpN.setDescripcion("Fecha límite para pruebas: "+tmpN.getFechaFinString());
        tmpN.setPrioridad(false);
        tmpN.setParaGerente(true);
        tmpN.setJUR_Caso(etapa.getJUR_Caso());
        tmpN.setZz_FechaCreacion(tmp.getZz_FechaCreacion());
        tmpN.setZz_FechaModificacion(tmp.getZz_FechaCreacion());
        tmpN.setZz_UsuarioCreacion(usu);
        tmpN.setZz_UsuarioModificacion(usu);
        tmp.setNotas("Se cuentan con 30 días hábiles para llevar a cabo el ofreciomiento y desahogo de preubas empezando desde el "+tmp.getFechaString()+" hasta "+tmpN.getFechaFinString());
        tmpN.setJUR_EtapaDetalle(tmp);
        tmp.setJUR_Notificacion(tmpN);
        tmp.setDefaultRowNumber(etapa.getJUR_EtapaDetalleLength()+1);
        System.out.println("setDefaultPruebasDetalle METHOD PERFORMED");
        return tmp;
    }
    
     public static JUR_EtapaDetalle setDefaultContestacionDetalle(JUR_Etapas etapa,java.util.List<JUR_Fechas>lista,String usu,CommonActions ca)
    {
        System.out.println("setDefaultContestacionDetalle METHOD REACHED");
        JUR_EtapaDetalle tmp=new JUR_EtapaDetalle();
        tmp.setJUR_Etapa(etapa);
        tmp.setFecha(etapa.getJUR_Caso().getContestacionFecha());
        tmp.setFechaString(ca.parseDate(tmp.getFecha().toString(),"-"));
        tmp.setDuracion(9);
        tmp.setUnidadTiempo("Días");
        tmp.setAsuntosTratados("Inicio del periodo para contestar la demanda");
        tmp.setZz_FechaCreacion(ca.getCurrentTimestamp());
        tmp.setZz_FechaModificacion(tmp.getZz_FechaCreacion());
        tmp.setZz_UsuarioCreacion(usu);
        tmp.setZz_UsuarioModificacion(usu);
        JUR_Notificaciones tmpN=new JUR_Notificaciones();
        tmpN.setFechaInicio(tmp.getFecha());
        tmpN.setFechaInicioString(tmp.getFechaString());
        tmpN.setFechaFinString(ca.parseDate(addWorkDaysToDate(tmp.getFecha(),10,lista,ca),"-"));
        tmpN.setFechaFin(ca.stringToDate(tmpN.getFechaFinString(),"/"));
        tmpN.setAsunto("Contestación del caso "+etapa.getJUR_Caso().getNombre());
        tmpN.setDescripcion("Fecha límite para contestar demanda: "+tmpN.getFechaFinString());
        tmpN.setPrioridad(false);
        tmpN.setParaGerente(true);
        tmpN.setJUR_Caso(etapa.getJUR_Caso());
        tmpN.setZz_FechaCreacion(tmp.getZz_FechaCreacion());
        tmpN.setZz_FechaModificacion(tmp.getZz_FechaCreacion());
        tmpN.setZz_UsuarioCreacion(usu);
        tmpN.setZz_UsuarioModificacion(usu);
        tmp.setNotas("Se cuentan con 9 días hábiles para contestar la demanda, empezando desde el "+tmp.getFechaString()+" hasta "+tmpN.getFechaFinString());
        tmpN.setJUR_EtapaDetalle(tmp);
        tmp.setJUR_Notificacion(tmpN);
        tmp.setDefaultRowNumber(etapa.getJUR_EtapaDetalleLength()+1);
        System.out.println("setDefaultContestacionDetalle METHOD PERFORMED");
        return tmp;
    }
    
    public static JUR_Notificaciones notificacionTareaNueva(JUR_Tareas record)
    {
        JUR_Notificaciones noti=new JUR_Notificaciones();
        noti.setFechaInicio(record.getFechaAsignacion());
        noti.setFechaFin(record.getFechaEntrega());
        noti.setJUR_Caso(record.getJUR_Caso());
        noti.setJUR_Tarea(record);
        noti.setJUR_Etapa(record.getJUR_Etapa());
        noti.setAsunto("Nueva tarea asignada para el caso: "+noti.getJUR_Caso().getNombre());
        noti.setDescripcion(record.getAsunto());
        noti.setZz_FechaCreacion(record.getZz_FechaCreacion());
        noti.setZz_FechaModificacion(record.getZz_FechaCreacion());
        noti.setZz_UsuarioModificacion(record.getZz_UsuarioModificacion());
        noti.setZz_UsuarioCreacion(noti.getZz_UsuarioModificacion());
        return noti;
    }
    
    public static JUR_Notificaciones notificacionTareaRegresada(JUR_Notificaciones tmp,JUR_Tareas model,java.util.List<JUR_Fechas>lista,String usu,CommonActions ca)
    {
        tmp.setParaGerente(false);
        tmp.setJUR_Tarea(model);
        tmp.setPrioridad(false);
        tmp.setZz_UsuarioModificacion(usu);
        tmp.setZz_FechaModificacion(ca.getCurrentTimestamp());
        tmp.setZz_UsuarioCreacion(usu);
        tmp.setZz_FechaCreacion(tmp.getZz_FechaModificacion());
        tmp.setFechaInicio(ca.getCurrentDate());
        tmp.setFechaFin(ca.stringToDate(ca.parseDate(addWorkDaysToDate(tmp.getFechaInicio(),10,lista,ca),"-"),"/"));
        tmp.setAsunto("Tarea incompleta o incorrecta");
        tmp.setDescripcion("El jefe de área ha vuelto a marcar como \"Pendiente\" la tarea: "+model.getAsunto()+'\n'+(model.getJUR_Caso().getNombre()!=null?("Caso: "+model.getJUR_Caso().getNombre()):"")+(model.getJUR_Etapa()!=null?('\n'+"Etapa: "+model.getJUR_Etapa().getNombre()):"")+" Regresada: "+model.getZz_FechaModificacion());
        return tmp;
    }
    
    public static JUR_Notificaciones notificacionTareaFinalizada(JUR_Notificaciones tmp,JUR_Tareas model,java.util.List<JUR_Fechas>lista,String usu,CommonActions ca)
    {
        tmp.setParaGerente(true);
        tmp.setJUR_Tarea(model);
        tmp.setPrioridad(false);
        tmp.setFechaInicio(ca.getCurrentDate());
        tmp.setFechaFin(ca.stringToDate(ca.parseDate(addWorkDaysToDate(tmp.getFechaInicio(),10,lista,ca),"-"),"/"));
        tmp.setZz_FechaCreacion(ca.getCurrentTimestamp());
        tmp.setZz_FechaModificacion(tmp.getZz_FechaCreacion());
        tmp.setZz_UsuarioCreacion(usu);
        tmp.setZz_UsuarioModificacion(usu);
        tmp.setAsunto(model.getJUR_RecursoHumano().getNombre()+" "+model.getJUR_RecursoHumano().getApellidos()+" ha marcado como finalizada una tarea.");
        tmp.setDescripcion((model.getJUR_Caso().getNombre()!=null?("Caso: "+model.getJUR_Caso().getNombre()):"")+(model.getJUR_Etapa()!=null?('\n'+"Etapa: "+model.getJUR_Etapa().getNombre()):"")+'\n'+"Tarea: "+model.getAsunto()+'\n'+"Finalizada: "+model.getZz_FechaModificacion());
        return tmp;
    }
    
    public static String addWorkDaysToDate(java.sql.Date date,long workdays,java.util.List<JUR_Fechas> datesToSkip,CommonActions ca)
    {
        int i=0,n=datesToSkip.size(),indexes[]=new int[n],iC=0;
        System.out.println("n: "+n);
        for(int j=0;j<n;j++)
        {
            JUR_Fechas tmpJF1=datesToSkip.get(j);
            System.out.println("j: "+j);
            for(int k=0;k<n;k++)
            {
                System.out.println("-------------k:"+k+"---------------");
                int c;
                for(c=0;c<iC;c++)
                    if(k==indexes[c]||j==indexes[c])
                        break;
                System.out.println("c: "+c);
                System.out.println("iC: "+iC);
                if(c>=iC||iC<1)
                {
                    JUR_Fechas tmpJF2=datesToSkip.get(k);
                    if(k!=j)
                    {
                        switch(ca.checkDatesInPeriods(tmpJF1.getFechaInicio(),tmpJF1.getFechaFin(),tmpJF2.getFechaInicio(),tmpJF2.getFechaFin()))
                        {
                            case -2:tmpJF2.setFechaFin(tmpJF1.getFechaFin());indexes[iC++]=j;break;
                            case -1:tmpJF2.setFechaInicio(tmpJF1.getFechaInicio());indexes[iC++]=j;break;
                            case 1: indexes[iC++]=k;break;
                            case 12:indexes[iC++]=j;break;
                            case 21:indexes[iC++]=k;break;
                            default:break;
                        }
                    }
                }
            }
            System.out.println("-------------j:"+j+"---------------");
        }
        if(iC>0)
            filterDatesToSkip(indexes,iC,datesToSkip);
        n=datesToSkip.size();
        System.out.println("DATES("+n+")");
        java.time.LocalDate tmp=date.toLocalDate();
        //workdays++;
        do
        {
            int d=tmp.getDayOfWeek().getValue(),sd=0;
            if(n>0)
            {
                JUR_Fechas td=datesToSkip.get(0);
                System.out.println("tmp: "+tmp+" / "+td.getFechaInicio()+" - "+td.getFechaFin()+"\n\t"+workdays);
                Date dateT=Date.valueOf(tmp.toString());
                iC=td.getFechaInicio().compareTo(dateT);
                if(iC<=0)
                {
                    if(td.getFechaFin()!=null)
                    {
                        iC=td.getFechaFin().compareTo(dateT);
                        System.out.println("iC1: "+iC);
                        if(iC>0)
                        {
                            System.out.println("n2: "+n);
                            iC=1+Days.daysBetween(new LocalDate(dateT),new LocalDate(td.getFechaFin())).getDays();
                            System.out.println("DAYS BETWEEN: "+iC);
                            if(i<workdays)
                                if(i+iC>workdays)
                                {
                                    iC=(i+iC)-(int)workdays;
                                    i=(int)workdays;
                                    n=0;
                                }
                        }
                        else 
                        {
                            if(iC==0&&i<=workdays)
                                i--;
                            iC=1;
                        }
                    }
                    else 
                    {
                        if(iC==0&&i<=workdays)
                            i--;
                        iC=1;
                    }
                    datesToSkip.remove(0);
                    n--;
                }
                else
                {
                    System.out.println("in break if");
                    if(i>workdays&&d<6)
                        break;
                    iC=1;
                    sd=d+iC;
                }
            }
            else 
            {
                iC=1;
                System.out.println("tmp: "+tmp+"\n\t"+workdays);
            }
            if(sd<1)
                sd=d+iC;
            System.out.println("\t"+i+") iC: "+iC+", d: "+d);
            if(sd>5)
            {
                if(sd>7)
                {
                    sd=(iC-7*(iC/7));
                    System.out.println("\tsd1: "+sd);
                    sd+=d;
                }
                else System.out.println("\tsd2: "+sd);
            }
            else sd=d;
            System.out.println("\td: "+d+", sd: "+sd);
            switch(sd)
            {
                case 5: if(i<=workdays)iC+=3;break;
                case 6: iC+=2;break;
                case 7: iC+=1;break;
            }
            tmp=tmp.plusDays(iC);
            System.out.println("\tiC:"+iC);
            i++;
        }while(i<workdays||n>0);
        String newDate=tmp.toString();
        return newDate;
    }
    
    private static void filterDatesToSkip(int[] n, int l,java.util.List<JUR_Fechas>lista)
    {
        int nn[]=new int[l],i;
        for(i=0;i<l;i++)
            nn[i]=n[i];
        int aux;
        for(i=0;i<l-1;i++)
            for(int j=i+1;j<l;j++)
                if(nn[j]<nn[i])
                {
                    aux=nn[i];
                    nn[i]=nn[j];
                    nn[j]=aux;
                }
        n=null;
        /*System.out.println("INDEXES");
        for(i=0;i<l;i++)
            System.out.println("\t"+nn[i]);
        aux=lista.size();
        System.out.println("aux: "+aux);*/
        for(i=0;i<l;i++)
            lista.remove(nn[i]-i);
        aux=lista.size()-1;
        System.out.println("aux: "+aux);
        for(i=0;i<aux;i++)
        {
            int j=i+1;
            JUR_Fechas t1=lista.get(i),
                   t2=lista.get(j);
            if(t1.getFechaInicio()==null)
            {
                if(t1.getFechaFin()==null)
                {
                    lista.remove(i--);
                    aux--;
                }
                else
                {
                    t1.setFechaInicio(t1.getFechaFin());
                    t1.setFechaFin(null);
                }
            }
            if(t2.getFechaInicio()==null)
            {
                if(t2.getFechaFin()==null)
                {
                    lista.remove(j);
                    aux--;
                    i--;
                }
                else
                {
                    t2.setFechaInicio(t2.getFechaFin());
                    t2.setFechaFin(null);
                }
            }
            if(t1.getFechaInicio()!=null&&t2.getFechaInicio()!=null)
                if(t2.getFechaInicio().compareTo(t1.getFechaInicio())<0)
                {
                    lista.set(i,t2);
                    lista.set(j,t1);
                }
        }
    }
    
}
