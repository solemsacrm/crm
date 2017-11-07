package com.solemsa.jsf.DAOs.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Fechas;
import com.solemsa.jsf.data.CommonActions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class daoCalendario {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoCalendario(SessionFactory Factory){
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
    
    public List<JUR_Fechas> getJUR_Fechas(int displayedYear)
    {
        System.out.println("getJUR_Fechas METHOD REACHED");
        List<JUR_Fechas> lista=null;
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            lista=Session.createCriteria(JUR_Fechas.class).add(Restrictions.or(Restrictions.between("FechaInicio",ca.stringToDate("01/01/"+displayedYear,"/"),ca.stringToDate("31/12/"+displayedYear,"/")),Restrictions.isNull("FechaInicio"))).addOrder(Order.asc("FechaInicio")).list();
            int n=lista.size();
            for(int i=0;i<n;i++)
            {
                JUR_Fechas tmp=lista.get(i);
                if(tmp.getFechaInicio()!=null)
                    tmp.setFechaInicioString(ca.parseDate(tmp.getFechaInicio()+"","-"));
                else
                    tmp.setFechaInicioString("");
                tmp.setTipo(tmp.getFechaFin()==null);
                if(tmp.isTipo())
                    tmp.setFechaFinString("");
                else tmp.setFechaFinString(ca.parseDate(tmp.getFechaFin()+"","-"));
            }
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("getJUR_Fechas EXCEPTION: "+e);
            e.printStackTrace();
            if(lista!=null)
                lista=null;
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
            if(lista==null)
                lista=ca.newArrayList();
        }
        System.out.println("getJUR_Fechas METHOD PERFORMED");
        return lista;
    }
    
    public List<JUR_Fechas> reloadJUR_Tareas(List<JUR_Fechas>originalList, int displayedYear)
    {
        System.out.println("reloadJUR_Tareas METHOD REACHED");
        List<JUR_Fechas> lista=null;
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            int n=originalList.size();
            System.out.println("\tn: "+n);
            for(int i=0;i<n;i++)
            {
                JUR_Fechas tmp=(JUR_Fechas)Session.load(JUR_Fechas.class,originalList.get(i).getId_JUR_Fecha());
                if(tmp.getFechaInicio()!=null)
                    tmp.setFechaInicioString(ca.parseDate(tmp.getFechaInicio()+"","-"));
                else
                    tmp.setFechaInicioString("");
                tmp.setTipo(tmp.getFechaFin()==null);
                if(tmp.isTipo())
                    tmp.setFechaFinString("");
                else tmp.setFechaFinString(ca.parseDate(tmp.getFechaFin()+"","-"));
                lista.add(tmp);
            }
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("reloadJUR_Tareas EXCEPTION: "+e);
            //e.printStackTrace();
            if(lista!=null)
                lista=null;
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
            if(lista==null)
                lista=getJUR_Fechas(displayedYear);
        }
        System.out.println("reloadJUR_Tareas METHOD PERFORMED");
        return lista;
    }
    
    public JUR_Fechas loadJUR_Fecha(JUR_Fechas model)
    {
        System.out.println("loadJUR_Fecha METHOD REACHED with: "+model.getId_JUR_Fecha());
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            model=(JUR_Fechas)Session.load(JUR_Fechas.class,model.getId_JUR_Fecha());
            if(model.getFechaInicio()!=null)
                model.setFechaInicioString(ca.parseDate(model.getFechaInicio()+"","-"));
            else
                model.setFechaInicioString("");
            if(model.getFechaFin()!=null)
                model.setFechaFinString(ca.parseDate(model.getFechaFin()+"","-"));
            else
                model.setFechaFinString("");
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("loadJUR_Fecha EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadJUR_Fecha METHOD PERFORMED");
        return model;
    }
    
    public void saveJUR_Fecha(JUR_Fechas model,String usu)
    {
        System.out.println("saveJUR_Fecha METHOD REACHED with: "+model.getId_JUR_Fecha());
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            model.setZz_FechaModificacion(ca.getCurrentTimestamp());
            model.setZz_UsuarioModificacion(usu);
            if(model.getFechaInicioString().equals(""))
                model.setFechaInicio(null);
            else
                model.setFechaInicio(ca.stringToDate(model.getFechaInicioString(),"/"));
            if(model.getFechaFinString().equals(""))
                model.setFechaFin(null);
            else
                model.setFechaFin(ca.stringToDate(model.getFechaFinString(),"/"));
            if(model.getId_JUR_Fecha()<1)
            {
                model.setZz_UsuarioCreacion(usu);
                model.setZz_FechaCreacion(model.getZz_FechaModificacion());
                Session.save(model);
            }
            else Session.update(model);
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("saveJUR_Fecha EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("saveJUR_Fecha METHOD REACHED PERFORMED");
    }
    
    public void deleteJUR_Fecha(JUR_Fechas model)
    {
        System.out.println("deleteJUR_Fecha METHOD REACHED with: "+model.getId_JUR_Fecha());
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            Session.delete(model);
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("deleteJUR_Fecha EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("deleteJUR_Fecha METHOD PERFORMED");
    }
    
}
