package com.solemsa.jsf.DAOs.rex;

import com.solemsa.hibernate.entities.REX.REX_Obras;
import com.solemsa.hibernate.entities.REX.REX_Avances;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class daoREX_Avances {
    
    private SessionFactory Factory;
    private Session Session;
    
    public daoREX_Avances(SessionFactory Factory){
        try{
            this.Factory=Factory;
            System.out.println("FACTORY CONSTRUCTION");
            
            Session=this.Factory.getCurrentSession();
            System.out.println("SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
        }
    }
    
    public daoREX_Avances(Session Session){
        try{
            this.Session=Session;
            System.out.println("FACTORY CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
        }
    }
    
    public REX_Avances getREX_Avance(long id)
    {
        System.out.println("getREX_Avance METHOD REACHED: "+id);
        REX_Avances cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(REX_Avances)Session.get(REX_Avances.class,id);
            System.out.println("REQUESTED cliente FETCHED");
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
            if(cfg.get_REX_Obra()==null)
                cfg.set_REX_Obra(new REX_Obras());
            try
            {
                Session.close();
                System.out.println("get REX_Avance SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            System.out.println("LIST LOAD SESSION CLOSED");
        }
        else
        {
            cfg=new REX_Avances();
            cfg.set_REX_Obra(new REX_Obras());
        }
        return cfg;
    }
    
    public String newREX_Avance(REX_Avances model)
    {
        Exception ex=null;
        System.out.println("newREX_Avance METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            java.util.Date date= new java.util.Date();
            Timestamp d=new Timestamp(date.getTime());
            model.setZz_FechaCreacion(d);
            model.setZz_FechaModificacion(d);
            // start a transaction
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED");
            // Saving Student
            Session.save(model);
            System.out.println("new cliente SAVE SUCCESSFUL");
            //commit transaction
            Session.getTransaction().commit();
            System.out.println("new TRANSACTION COMMITED");
        }
        catch(Exception e)
        {
            ex=e;
            System.out.println("new EXCEPTION: "+e.toString());
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("new REX_Avance SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        if(ex!=null)
            return "obras";
        return null;
    }
    
    public void deleteREX_Avance(REX_Avances record)
    {
        try {				
            long id=record.getId_REX_Avance();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            Session.delete(record);
            System.out.println("student "+id+" SUCCESSFULLY DELETED");
            // commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete REX_Avance SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleREX_Avances(REX_Avances[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple REX_Avance TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple REX_Avance TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple REX_Avance EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple REX_Avance SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public REX_Avances modifyREX_Avance(REX_Avances source){
        REX_Avances target=null;
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            target=setNewValues(source,null);
            System.out.println("obra"+target.getId_REX_Avance()+" SUCCESSFULLY MODIFIED");
            // commit the transaction
            Session.getTransaction().commit();
            System.out.println("modify TRANSACTION COMMITED");
            return target;
        }
        catch(Exception e){
            System.out.println("modify EXCEPTION: "+e);
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("modify REX_Avance SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private REX_Avances setNewValues(REX_Avances source,REX_Avances target){
        if(target==null)
            target=(REX_Avances) Session.get(REX_Avances.class,source.getId_REX_Avance());
        target.set_REX_Obra(new REX_Obras());
        //System.out.println("modify "+target.getId_REX_Avance()+" SUCCESSFULLY RETRIEVED");
        target.get_REX_Obra().setId_REX_Obra(source.get_REX_Obra().getId_REX_Obra());
        //System.out.println("id REX_Obra MODIFY SUCCESSFUL TO "+target.get_REX_Obra());
        target.setPorcentaje(source.getPorcentaje());
        //System.out.println("Porcentaje MODIFY SUCCESSFUL TO "+target.getPorcentaje());
        target.setFecha(source.getFecha());
        //System.out.println("Fecha MODIFY SUCCESSFUL TO "+target.getFecha());
        target.setDescripcion(source.getDescripcion()==null?null:source.getDescripcion().trim());
        //System.out.println("Descripción MODIFY SUCCESSFUL TO "+target.getDescripcion());
        target.setEtapa(source.getEtapa()==null?null:source.getEtapa().trim());
        //System.out.println("Etapa MODIFY SUCCESSFUL TO "+target.getEtapa());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        //System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        java.util.Date date= new java.util.Date();
        Timestamp d=new Timestamp(date.getTime());
        target.setZz_FechaModificacion(d);
        //System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public void saveFromREX_Obra(List<REX_Avances>l1,List<REX_Avances>l2,String u)
    {
        System.out.println("saveFromREX_Obra METHOD REACHED");
        java.util.Date date= new java.util.Date();
        Timestamp d=new Timestamp(date.getTime());
        int m=l1.size(),n=l2.size();
        System.out.println("        m: "+m+", n: "+n);
        if(m<1)
        {
            for(int i=0;i<n;i++)
                Session.delete(l2.get(i));
            System.out.println("ALL AVANCES DELETED SUCCESSFULLY");
        }
        else if(n<1)
        {
            for(int i=0;i<m;i++)
            {
                REX_Avances t1=l1.get(i);
                t1.setZz_FechaCreacion(d);
                t1.setZz_FechaModificacion(d);
                t1.setZz_UsuarioCreacion(u);
                t1.setZz_UsuarioModificacion(u);
                Session.save(t1);
            }
            System.out.println("NEW AVANCES INSERTED SUCCESSFULLY");
        }
        else
        {
            REX_Avances ins[]=new REX_Avances[m],upd[]=new REX_Avances[n],del[]=new REX_Avances[n];
            for(int i=0;i<m;i++)
            {
                REX_Avances t1=l1.get(i);
                int j;
                if(t1.getId_REX_Avance()==0)
                        ins[i]=t1;
                else
                    for(j=0;j<n;j++)
                    {
                        REX_Avances t2=l2.get(j);
                        if(t1.getId_REX_Avance()==t2.getId_REX_Avance())
                            upd[j]=t1;
                        else
                            del[j]=t2;
                    }
            }
            for(int i=0;i<n;i++)
            {
                REX_Avances t1=upd[i];
                if(t1==null)
                    Session.delete(del[i]);
                else
                {
                    t1.setZz_FechaModificacion(d);
                    t1.setZz_UsuarioModificacion(u);
                    Session.update(t1);
                }
            }
            for(int i=--m;i>=0;i--)
            {
                REX_Avances t1=ins[i];
                if(t1!=null)
                {
                    t1.setZz_FechaCreacion(d);
                    t1.setZz_FechaModificacion(d);
                    t1.setZz_UsuarioCreacion(u);
                    t1.setZz_UsuarioModificacion(u);
                    Session.save(t1);
                }
            }
        }
        System.out.println("saveFromREX_Obra METHOD SUCCESSFUL");
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
