package com.solemsa.jsf.DAOs.SEG;

import com.solemsa.hibernate.entities.SEG.SEG_Cotizaciones;
import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.jsf.data.CommonActions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class daoSEG_Cotizaciones {
    
    private SessionFactory Factory;
    private Session Session;
    private CommonActions ca;
    
    public daoSEG_Cotizaciones(SessionFactory Factory){
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
        Criteria c=Session.createCriteria(Clientes.class);
        System.out.println("CRITERIA CREATED");
        c.add(Restrictions.and(Restrictions.eq("ClienteSEG",1),Restrictions.lt("Estatus",3)));
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

    public List<SEG_Cotizaciones> getSEG_CotizacionesList()
    {
        List<SEG_Cotizaciones> lista=null;
        try
        {			
            System.out.println("getSEG_CotizacionesList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            // query students
            //String qry="FROM SEG_Cotizaciones ORDER BY Descripcion ASC";
            try{lista=Session.createCriteria(SEG_Cotizaciones.class).addOrder(Order.asc("Estatus")).list();}catch(Exception e){
                if(lista!=null)
                    lista=null;
                System.out.println("lista SET TO NULL: "+e);
            }
            System.out.println("QUERY RESULT: "+lista);
            System.out.println("get QUERY SUCCESSFUL: "+lista.size()+" items in list");
            //commit transaction
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("EXCEPCIÓN DE CONSULTA: "+e.toString());
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
    
    public SEG_Cotizaciones getSEG_Cotizacion(long id)
    {
        System.out.println("getSEG_Cotizacion METHOD REACHED");
        SEG_Cotizaciones cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(SEG_Cotizaciones)Session.get(SEG_Cotizaciones.class,id);
            System.out.println("REQUESTED cliente FETCHED");
            Session.getTransaction().commit();
            if(cfg.getFecha()!=null)
                cfg.setFechaString(ca.parseDate(cfg.getFecha().toString(),"-"));
            if(cfg.getCliente()==null)
                cfg.setCliente(new Clientes());
            System.out.println("get TRANSACTION COMMITED");
            System.out.println("LIST LOAD SESSION CLOSED");
            try
            {
                Session.close();
                System.out.println("LIST LOAD SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        else
        {
            cfg=new SEG_Cotizaciones();
            cfg.setCliente(new Clientes());
            cfg.setTipo("Evento");
            cfg.setEstatus(1);
            cfg.setNoElementos(1);
            cfg.setRenta(2);
        }
        return cfg;
    }
    
    public SEG_Cotizaciones newSEG_Cotizacion(SEG_Cotizaciones model)
    {
        System.out.println("newSEG_Cotizacion METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            model.setZz_FechaCreacion(ca.getCurrentTimestamp());
            model.setZz_FechaModificacion(model.getZz_FechaCreacion());
            model.setFecha(ca.stringToDate(model.getFechaString(),"/"));
            if(model.getCliente().getId_Clientes()<1)
                model.setCliente(null);
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED");
            Session.save(model);
            System.out.println("new cliente SAVE SUCCESSFUL");
            Session.getTransaction().commit();
            if(model.getCliente()==null)
                model.setCliente(new Clientes());
            System.out.println("new TRANSACTION COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("new EXCEPTION: "+e.toString());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("new SEG_Cotizacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return model;
    }
    
    public void deleteSEG_Cotizacion(SEG_Cotizaciones record)
    {
        try {				
            long id=record.getId_SEG_Cotizacion();
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
            System.out.println("delete SEG_Cotizacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleSEG_Cotizaciones(SEG_Cotizaciones[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple SEG_Cotizacion TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            Session.getTransaction().commit();
            System.out.println("delete multiple SEG_Cotizacion TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple SEG_Cotizacion EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple SEG_Cotizacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public SEG_Cotizaciones modifySEG_Cotizacion(SEG_Cotizaciones source){
        SEG_Cotizaciones target=null;
        try {						
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            source.setFecha(ca.stringToDate(source.getFechaString(),"/"));
            if(source.getCliente().getId_Clientes()<1)
                source.setCliente(null);
            target=setNewValues(source,new SEG_Cotizaciones());
            if(target.getCliente()==null)
                target.setCliente(new Clientes());
            System.out.println("material"+target.getId_SEG_Cotizacion()+" SUCCESSFULLY MODIFIED");
            Session.update(source);
            Session.getTransaction().commit();
            System.out.println("modify TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("modify EXCEPTION: "+e);
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("modify SEG_Cotizacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target;
    }
    
    private SEG_Cotizaciones setNewValues(SEG_Cotizaciones source,SEG_Cotizaciones target){
        if(target==null)
            target=(SEG_Cotizaciones) Session.get(SEG_Cotizaciones.class,source.getId_SEG_Cotizacion());
        else if(target.getId_SEG_Cotizacion()<1)
        {
            target.setZz_FechaCreacion(source.getZz_FechaCreacion());
            target.setZz_UsuarioCreacion(source.getZz_UsuarioCreacion());
        }
        target.setCliente(source.getCliente());
        System.out.println("Cliente MODIFY SUCCESSFUL TO "+target.getCliente().getId_Clientes());
        target.setNombre(source.getNombre()==null?null:source.getNombre().trim());
        System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getNombre());
        target.setTipo(source.getTipo()==null?null:source.getTipo().trim());
        System.out.println("Tipo MODIFY SUCCESSFUL TO "+target.getTipo());
        target.setDescripcion(source.getDescripcion()==null?null:source.getDescripcion().trim());
        System.out.println("Descripcion MODIFY SUCCESSFUL TO "+target.getDescripcion());
        target.setRenta(source.getRenta());
        System.out.println("Renta MODIFY SUCCESSFUL TO "+target.getRenta());
        target.setEstatus(source.getEstatus());
        System.out.println("Estatus MODIFY SUCCESSFUL TO "+target.getEstatus());
        target.setCosto(source.getCosto());
        System.out.println("Costo MODIFY SUCCESSFUL TO "+target.getCosto());
        target.setPrecio(source.getPrecio());
        System.out.println("Precio MODIFY SUCCESSFUL TO "+target.getPrecio());
        target.setNoElementos(source.getNoElementos());
        System.out.println("NoElementos MODIFY SUCCESSFUL TO "+target.getNoElementos());
        target.setFecha(source.getFecha());
        System.out.println("Fecha MODIFY SUCCESSFUL TO "+target.getFecha());
        target.setFechaString(source.getFechaString());
        System.out.println("FechaString MODIFY SUCCESSFUL TO "+target.getFechaString());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        target.setZz_FechaModificacion(ca.getCurrentTimestamp());
        System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
