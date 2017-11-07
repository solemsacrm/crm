package com.solemsa.jsf.DAOs.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Clientes;
import com.solemsa.hibernate.entities.JUR.JUR_Notificaciones;
import com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos;
import com.solemsa.jsf.data.CommonActions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class daoJUR_Landing {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoJUR_Landing(SessionFactory Factory){
        try{
            this.Factory=Factory;
            System.out.println("FACTORY CONSTRUCTION");
            
            Session=this.Factory.getCurrentSession();
            ca=new CommonActions();
            System.out.println("SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
        }
    }
    
    public List loadClienteesList()
    {
        Session=Factory.getCurrentSession();
        System.out.println("CURRENT SESSION GOTTEN");
        Session.beginTransaction();
        System.out.println("load TRANSACTION STARTED");
        Criteria c=Session.createCriteria(JUR_Clientes.class);
        System.out.println("CRITERIA CREATED");
        c.add(Restrictions.and(Restrictions.eq("ClienteJUR",1)));
        ProjectionList pl=Projections.projectionList();
        pl.add(Projections.id());
        pl.add(Projections.property("Nombre"));
        c.setProjection(pl);
        System.out.println("CRITERIA SET");
        List lista=c.list();
        Session.getTransaction().commit();
        System.out.println("SESSION SUCCESSFULY COMMITED");
        try{
            Session.close();
            System.out.println("CURRENT SESSION CLOSED");
        }catch(Exception e){System.out.println("SESSION WAS ALREDY CLOSED: "+e);}
        return lista;
    }
    
    public List loadRecursosHumanosList()
    {
        System.out.println("loadRecursosHumanosList METHOD REACHED");
        List lista=null;
        try
        {
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("load TRANSACTION STARTED");
            Criteria c=Session.createCriteria(JUR_RecursosHumanos.class);
            System.out.println("CRITERIA CREATED");
            ProjectionList pl=Projections.projectionList();
            pl.add(Projections.id());
            pl.add(Projections.property("Nombre"));
            pl.add(Projections.property("Apellidos"));
            c.setProjection(pl);
            System.out.println("CRITERIA SET");
            lista=c.list();
            Session.getTransaction().commit();
            System.out.println("SESSION SUCCESSFULY COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("loadRecursosHumanosList EXCEPTION: "+e);
            e.printStackTrace();
            if(lista!=null)
                lista=ca.newArrayList();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadRecursosHumanosList METHOD PERFORMED");
        return lista;
    }

    public List<JUR_Notificaciones> getJUR_NotificacionesList(long id, boolean gerente)
    {
        List<JUR_Notificaciones> lista=null;
        try
        {			
            System.out.println("getJUR_NotificacionesList METHOD REACHED as: "+(gerente?"gerente":"empleado"));
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            if(gerente)
                lista=Session.createCriteria(JUR_Notificaciones.class).add(Restrictions.eq("ParaGerente",true)).list();
            else
            {
                
                Criteria alcanceCriteria=Session.createCriteria(JUR_Notificaciones.class).createCriteria("JUR_Alcance").add(Restrictions.eq("JUR_RecursoHumano.__id_JUR_RecursoHumano",id));
                Criteria tareaCriteria=Session.createCriteria(JUR_Notificaciones.class).createCriteria("JUR_Tarea").add(Restrictions.eq("JUR_RecursoHumano.__id_JUR_RecursoHumano",id));
                lista=alcanceCriteria.list();
                lista.addAll(tareaCriteria.list());
            }
            sortNotificaciones(lista,id,gerente);
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("getJUR_NotificacionesList EXCEPTION: "+e);
            e.printStackTrace();
            if(lista!=null)
                lista=null;
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        return lista;
    }
    
    private void sortNotificaciones(List<JUR_Notificaciones> lista,long id,boolean gerente)
    {
        int n=lista.size();
        System.out.println("get QUERY SUCCESSFUL: "+n+" items in list");
        if(n>0)
        {
            if(n<2)
                checkForUniqueNotificacion(lista,id,gerente);
            else
            {
                n--;//se reduce porque dentro del for se toma el valor (i+1)
                JUR_Notificaciones tmp1,tmp2;
                java.sql.Date cd=ca.getCurrentDate();
                boolean f1,f2;
                for(int i=0;i<n;i++)
                {
                    tmp1=lista.get(i);
                    tmp2=lista.get(i+1);
                    //System.out.println("i: "+i+", n: "+n);
                    f1=tmp1.getFechaFin()!=null;
                    if(f1)
                    {
                        f1=tmp1.getFechaFin().compareTo(cd)<0;
                        if(tmp1.getJUR_AlcanceLength()==null)
                            tmp1.setJUR_AlcanceLength(tmp1.getJUR_Alcance().size());
                        //System.out.println("\tT1) Asunto: "+tmp1.getAsunto()+" Fecha: "+tmp1.getFechaFin()+" Length: "+(tmp1.getJUR_AlcanceLength())+" isParaGerente: "+tmp1.isParaGerente());
                        if(f1)
                        {
                            if(tmp1.getJUR_Tarea()==null)
                            {
                                if(cd.compareTo(ca.stringToDate(ca.parseDate(ca.addWorkDaysToDate(tmp1.getFechaFin(),10),"-"),"/"))>=0)
                                {
                                    if(gerente)
                                    {
                                        if(tmp1.getJUR_AlcanceLength()<1)
                                        {
                                            Session.delete(tmp1);
                                            tmp1.setJUR_AlcanceLength(tmp1.getJUR_AlcanceLength()-1);
                                        }
                                    }
                                    else
                                    {
                                        if(tmp1.getJUR_AlcanceLength()<2&&!tmp1.isParaGerente())
                                            Session.delete(tmp1);
                                        else deleteAlcance(tmp1,id);
                                        tmp1.setJUR_AlcanceLength(tmp1.getJUR_AlcanceLength()-1);
                                    }
                                }
                            }
                            lista.remove(i);
                            n--;
                            i=(i>=0)?(i-1):i;
                            //System.out.println("\tT1 removed by fecha");
                        }
                        else if(tmp1.getJUR_AlcanceLength()<1&&tmp1.isParaGerente()&&!gerente)
                        {
                            lista.remove(i);
                            n--;
                            i=(i>=0)?(i-1):i;
                            f1=true;
                            //System.out.println("\tT1 removed by length");
                        }
                    }
                    f2=tmp2.getFechaFin()!=null;
                    if(f2)
                    {
                        if(tmp2.getJUR_AlcanceLength()==null)
                            tmp2.setJUR_AlcanceLength(tmp2.getJUR_Alcance().size());
                        f2=tmp2.getFechaFin().compareTo(cd)<0;
                        //System.out.println("\tT2) Asunto: "+tmp2.getAsunto()+" Fecha: "+tmp2.getFechaFin()+" Length: "+(tmp2.getJUR_AlcanceLength())+" isParaGerente: "+tmp2.isParaGerente());
                        if(f2)
                        {
                            if(tmp2.getJUR_Tarea()==null)
                            {
                                if(cd.compareTo(ca.stringToDate(ca.parseDate(ca.addWorkDaysToDate(tmp2.getFechaFin(),10),"-"),"/"))>=0)
                                {
                                    if(gerente)
                                    {
                                        if(tmp2.getJUR_AlcanceLength()<1)
                                        {
                                            Session.delete(tmp2);
                                            tmp2.setJUR_AlcanceLength(tmp2.getJUR_AlcanceLength()-1);
                                        }
                                    }
                                    else
                                    {
                                        if(tmp2.getJUR_AlcanceLength()<2&&!tmp2.isParaGerente())
                                            Session.delete(tmp2);
                                        else deleteAlcance(tmp2,id);
                                        tmp2.setJUR_AlcanceLength(tmp2.getJUR_AlcanceLength()-1);
                                    }
                                }
                            }
                            lista.remove(i+1);
                            i=(i>=0)?(i-1):i;
                            n--;
                            //System.out.println("\tT2 removed by fecha");
                        }
                        else if(tmp2.getJUR_AlcanceLength()<1&&tmp2.isParaGerente()&&!gerente)
                        {
                            lista.remove(i+1);
                            i=(i>=0)?(i-1):i;
                            n--;
                            f2=true;
                            //System.out.println("\tT2 removed by length");
                        }
                    }
                    if(!(f1||f2))
                    {
                        //String sout="\tT1 and T2 were switched";
                        if(tmp1.getFechaFin()==null)
                        {
                            if(tmp2.getFechaFin()!=null)
                            {
                                lista.set(i,tmp2);
                                lista.set(i+1,tmp1);
                            }
                            //else
                            //    sout="\tT1 and T2 weren't switched";
                        }
                        else if(tmp2.getFechaFin()==null)
                        {
                            lista.set(i,tmp2);
                            lista.set(i+1,tmp1);
                        }
                        else if(tmp1.getFechaFin().compareTo(tmp2.getFechaFin())>0)
                        {
                            lista.set(i,tmp2);
                            lista.set(i+1,tmp1);
                        }
                        /*else
                            sout="\tT1 and T2 weren't switched";
                        System.out.println(sout);
                        */
                    }
                }
            }
        }
    }
    
    private void checkForUniqueNotificacion(List<JUR_Notificaciones> lista, long id, boolean gerente)
    {
        System.out.println("\tcheckForUniqueNotificacion METHOD REACHED");
        JUR_Notificaciones tmp1=lista.get(0);
        if(tmp1.getJUR_Tarea()!=null)
        {
            if(tmp1.isParaGerente()&&!gerente)
            {
                if(ca.getCurrentDate().compareTo(ca.stringToDate(ca.parseDate(ca.addWorkDaysToDate(tmp1.getFechaFin(),10),"-"),"/"))>=0)
                    Session.delete(tmp1);
                lista.remove(0);
            }
        }
        else
        {
            System.out.println("\ttmp1.getJUR_Alcance()!=null: "+tmp1.getJUR_Alcance()!=null);
            if(tmp1.getJUR_Alcance()!=null)
            {
                tmp1.setJUR_AlcanceLength(tmp1.getJUR_Alcance().size());
                System.out.println("size: "+tmp1.getJUR_AlcanceLength());
                int i;
                for(i=0;i<tmp1.getJUR_AlcanceLength();i++)
                {
                    if(tmp1.getJUR_Alcance().get(i).getJUR_RecursoHumano().getId_JUR_RecursoHumano()==id)
                    {
                        Session.delete(tmp1.getJUR_Alcance().get(i));
                        lista.remove(0);
                        break;
                    }
                }
                if(i>=tmp1.getJUR_AlcanceLength()&&tmp1.isParaGerente()&&!gerente)
                {
                    if(ca.getCurrentDate().compareTo(ca.stringToDate(ca.parseDate(ca.addWorkDaysToDate(tmp1.getFechaFin(),10),"-"),"/"))>=0)
                        Session.delete(tmp1);
                    lista.remove(0);
                }
            }
            else if(!tmp1.isParaGerente())
            {
                Session.delete(tmp1);
                lista.remove(0);
            }
            else if(ca.getCurrentDate().compareTo(ca.stringToDate(ca.parseDate(ca.addWorkDaysToDate(tmp1.getFechaFin(),10),"-"),"/"))>=0)
            {
                Session.delete(tmp1);
                lista.remove(0);
            }
        }
        System.out.println("\tcheckForUniqueNotificacion METHOD PERFORMED");
    }
    
    public void changePrioridad(JUR_Notificaciones tmp,boolean prioridad)
    {
        System.out.println("changePrioridad METHOD REACHED with: "+tmp.getId_JUR_Notificacion());
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            tmp.setPrioridad(prioridad);
            Session.update(tmp);
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("changePrioridad EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("changePrioridad METHOD PERFORMED");
    }
    
    private void deleteAlcance(JUR_Notificaciones tmp,long id)
    {
        com.solemsa.hibernate.entities.JUR.JUR_NotificacionAlcance tmp3;
        List<com.solemsa.hibernate.entities.JUR.JUR_NotificacionAlcance> tmpA=tmp.getJUR_Alcance();
        //tmp.setJUR_AlcanceLength(tmpA.size());
        for(int j=0;j<tmp.getJUR_AlcanceLength();j++)
        {
            tmp3=tmpA.get(j);
            if(tmp3.getJUR_RecursoHumano().getId_JUR_RecursoHumano()==id)
            {
                Session.delete(tmp3);
                //tmp.setJUR_AlcanceLength(tmp.getJUR_AlcanceLength()-1);
                break;
            }
        }
    }
    
    private List<JUR_Notificaciones> lookForPruebasNotificacion(List<JUR_Notificaciones>l)
    {
        int n=l.size(),m=0;
        JUR_Notificaciones tmp;
        List<Long>casos=ca.newArrayList();
        List<Boolean>init=ca.newArrayList();
        List<JUR_Notificaciones>nl=ca.newArrayList();
        boolean f=false;
        for(int i=0;i<n;i++)
        {
            tmp=l.get(i);
            long idC=tmp.getJUR_Caso()!=null?tmp.getJUR_Caso().getId_JUR_Caso():0;
            if(idC>0)
            {
                f=false;
                m=nl.size();
                for(int j=0;j<m&&!f;j++)
                {
                    f=idC==nl.get(i).getJUR_Caso().getId_JUR_Caso();
                }
                if(!(tmp.isPruebas()||f))
                {
                    JUR_Notificaciones nt=new JUR_Notificaciones();
                    nt.setJUR_Caso(tmp.getJUR_Caso());
                    nt.setFechaFin(tmp.getJUR_Caso().getPurebasFecha());
                    nt.setFechaFinString(ca.parseDate(nt.getFechaFin()+"","-"));
                    nt.setFechaInicioString(ca.subtractWorkDaysToDate(nt.getFechaFin(),10));
                    nt.setFechaInicio(ca.stringToDate(nt.getFechaInicioString(),"/"));
                    nt.setPruebas(true);
                    //int days=Days.daysBetween(nt.getFechaInicio().toLocalDate(),nt.getFechaFin().toLocalDate()).getDays();
                    nt.setAsunto(0+" días restantes.");
                    nt.setDescripcion("Faltan "+0+" días para prubas del caso "+nt.getJUR_Caso().getNombre());
                    nl.add(nt);
                }
            }
        }
        return nl;
    }
    
    public void hideNotification(List<JUR_Notificaciones> l,String idS)
    {
        System.out.println("hideNotification METHOD REACHED");
        try
        {
            long id=ca.parseStringLong(idS);
            JUR_Notificaciones tmp=null;
            int n=l.size();
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            for(int i=0;i<n;i++)
            {
                tmp=l.get(i);
                if(tmp.getId_JUR_Notificacion()==id)
                {
                    tmp.setOculto(true);
                    Session.update(tmp);
                    Session.getTransaction().commit();
                    break;
                }
            }
            
        }
        catch(Exception e)
        {
            System.out.println("hideNotification EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("hideNotification METHOD PERFORMED");
    }
    
}
