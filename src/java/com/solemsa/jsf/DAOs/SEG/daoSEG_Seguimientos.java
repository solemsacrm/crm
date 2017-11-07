package com.solemsa.jsf.DAOs.SEG;

import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.SEG.SEG_Seguimientos;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class daoSEG_Seguimientos {
    
    private SessionFactory Factory;
    private Session Session;
    private CommonActions ca;
    
    public daoSEG_Seguimientos(SessionFactory Factory){
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
    
    public daoSEG_Seguimientos(Session Session){
        try{
            this.Session=Session;
            System.out.println("FACTORY CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
        }
    }

    public List<SEG_Seguimientos> getSEG_SeguimientosList(long cliente)
    {
        List<SEG_Seguimientos> lista=null;
        try
        {			
            System.out.println("getSEG_SeguimientosList METHOD REACHED");
            Session = Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            try{
                lista=Session.createCriteria(SEG_Seguimientos.class).add(Restrictions.eq("Cliente.__id_Clientes",cliente)).addOrder(Order.desc("Fecha")).list();
            }catch(Exception e){
                if(lista!=null)
                    lista=null;
                System.out.println("lista SET TO NULL: "+e);
            }
            if(lista==null)
            {
                lista=ca.newArrayList();
            }
            int n=lista.size();
            for(int i=0;i<n;i++)
                lista.get(i).setFechaString(ca.parseDate(lista.get(i).getFecha().toString(),"-"));
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED with "+n+" elements");
        }
        catch(Exception e)
        {
            System.out.println("EXCEPCIÓN DE CONSULTA: "+e);
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("LIST LOAD SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return lista;
    }
    
    public SEG_Seguimientos getSEG_Seguimiento(long id)
    {
        System.out.println("getSEG_Seguimiento METHOD REACHED: "+id);
        SEG_Seguimientos cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(SEG_Seguimientos)Session.get(SEG_Seguimientos.class,id);
            System.out.println("REQUESTED cliente FETCHED");
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
            if(cfg.getCliente()==null)
                cfg.setCliente(new Clientes());
            try
            {
                Session.close();
                System.out.println("get SEG_Seguimiento SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            System.out.println("LIST LOAD SESSION CLOSED");
        }
        else
        {
            cfg=new SEG_Seguimientos();
            cfg.setCliente(new Clientes());
        }
        return cfg;
    }
    
    public String newSEG_Seguimiento(SEG_Seguimientos model)
    {
        Exception ex=null;
        System.out.println("newSEG_Seguimiento METHOD REACHED");
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
            System.out.println("new SEG_Seguimiento SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        if(ex!=null)
            return "clientes";
        return null;
    }
    
    public void deleteSEG_Seguimiento(SEG_Seguimientos record)
    {
        try {				
            long id=record.getId_SEG_Seguimiento();
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
            System.out.println("delete SEG_Seguimiento SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleSEG_Seguimientos(SEG_Seguimientos[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple SEG_Seguimiento TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple SEG_Seguimiento TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple SEG_Seguimiento EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple SEG_Seguimiento SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public SEG_Seguimientos modifySEG_Seguimiento(SEG_Seguimientos source){
        SEG_Seguimientos target=null;
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            target=setNewValues(source,null,false);
            System.out.println("seguimiento "+target.getId_SEG_Seguimiento()+" SUCCESSFULLY MODIFIED");
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
                System.out.println("modify SEG_Seguimiento SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private SEG_Seguimientos setNewValues(SEG_Seguimientos source,SEG_Seguimientos target,boolean SameModificationDate){
        if(target==null)
            target=(SEG_Seguimientos) Session.get(SEG_Seguimientos.class,source.getId_SEG_Seguimiento());
        target.setCliente(new Clientes());
        //System.out.println("modify "+target.getId_SEG_Seguimiento()+" SUCCESSFULLY RETRIEVED");
        target.setCliente(source.getCliente());
        //System.out.println("id Cliente MODIFY SUCCESSFUL TO "+target.get_Cliente());
        target.setFecha(source.getFecha());
        //System.out.println("Fecha MODIFY SUCCESSFUL TO "+target.getFecha());
        target.setFechaString(source.getFechaString());
        target.setDescripcion(source.getDescripcion()==null?null:source.getDescripcion().trim());
        //System.out.println("Descripción MODIFY SUCCESSFUL TO "+target.getDescripcion());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        //System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        if(SameModificationDate)
        {
            target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
            target.setId_SEG_Seguimiento(source.getId_SEG_Seguimiento());//for deletion from ClienteCVS to work correctly
        }
        else
            target.setZz_FechaModificacion(ca.getCurrentTimestamp());
        //System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public void saveFromCliente(List<SEG_Seguimientos>l1,List<SEG_Seguimientos>l2,String u, ClientesMSC model)
    {
        System.out.println("saveFromCliente METHOD REACHED");
        if(Session.isOpen())
            Session.getTransaction().commit();
        Session=Factory.getCurrentSession();
        Session.beginTransaction();
        Timestamp d=ca.getCurrentTimestamp();
        model.setZz_FechaModificacion(d);
        model.setZz_UsuarioModificacion(u);
        Session.update(model);
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
                SEG_Seguimientos t1=l1.get(i);
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
            SEG_Seguimientos ins[]=new SEG_Seguimientos[m],upd[]=new SEG_Seguimientos[n],del[]=new SEG_Seguimientos[n];
            for(int i=0;i<m;i++)
            {
                SEG_Seguimientos t1=l1.get(i);
                System.out.println("\tt1) "+t1.getId_SEG_Seguimiento());
                int j;
                if(t1.getId_SEG_Seguimiento()==0)
                        ins[i]=t1;
                else
                    for(j=0;j<n;j++)
                    {
                        SEG_Seguimientos t2=l2.get(j);
                        System.out.println("\tt2) "+t2.getId_SEG_Seguimiento());
                        if(t1.getId_SEG_Seguimiento()==t2.getId_SEG_Seguimiento())
                            upd[j]=t1;
                        else
                            del[j]=t2;
                    }
            }
            for(int i=0;i<n;i++)
            {
                SEG_Seguimientos t1=upd[i];
                System.out.println("upd["+i+"]="+(t1==null?t1:t1.getId_SEG_Seguimiento()));
                if(t1==null)
                {
                    t1=del[i];
                    System.out.println("del["+i+"]="+(t1==null?t1:t1.getId_SEG_Seguimiento()));
                    if(t1!=null)
                        Session.delete(t1);
                }
                else if(t1.getId_SEG_Seguimiento()>0)
                {
                    t1.setFecha(ca.stringToDate(t1.getFechaString(),"/"));
                    t1.setZz_FechaModificacion(d);
                    t1.setZz_UsuarioModificacion(u);
                    Session.update(t1);
                }
            }
            for(int i=--m;i>=0;i--)
            {
                SEG_Seguimientos t1=ins[i];
                System.out.println("ins["+i+"]="+(t1==null?t1:t1.getId_SEG_Seguimiento()));
                if(t1!=null)
                {
                    t1.setFecha(ca.stringToDate(t1.getFechaString(),"/"));
                    t1.setZz_FechaCreacion(d);
                    t1.setZz_FechaModificacion(d);
                    t1.setZz_UsuarioCreacion(u);
                    t1.setZz_UsuarioModificacion(u);
                    Session.save(t1);
                }
            }
        }
        Session.getTransaction().commit();
        try{
            Session.close();
        }catch(Exception e)
        {
            System.out.println("SESSION CLOSING EXCEPTION: "+e);
        }
        System.out.println("saveFromCliente METHOD SUCCESSFUL");
    }
    
    public SEG_Seguimientos clone(SEG_Seguimientos s)
    {
        return setNewValues(s,new SEG_Seguimientos(),true);
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
