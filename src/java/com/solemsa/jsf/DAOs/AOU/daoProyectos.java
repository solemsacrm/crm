package com.solemsa.jsf.DAOs.AOU;

import com.solemsa.hibernate.entities.AOU.OUT_Clientes;
import com.solemsa.hibernate.entities.AOU.OUT_Proyectos;
import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_Clientes;
import com.solemsa.jsf.data.CommonActions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class daoProyectos {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoProyectos(SessionFactory Factory){
        try{
            this.Factory = Factory;
            System.out.println("daoConfig FACTORY CONSTRUCTION");
            Session = Factory.getCurrentSession();
            ca=new CommonActions();
            System.out.println("daoConfig SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("daoConfig FACTORY FAILED "+e);
        }
    }

    public OUT_Proyectos getProyecto(long id,boolean load)
    {
        System.out.println("getConfig METHOD REACHED: "+id);
        OUT_Proyectos cfg=null;
        if(id>0)
        {
            Session = Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            if(load)
                cfg=(OUT_Proyectos)Session.load(OUT_Proyectos.class,id);
            else
                cfg=(OUT_Proyectos)Session.get(OUT_Proyectos.class,id);       
            System.out.println("REQUESTED config FETCHED");
            if(cfg.getCliente()==null)
                cfg.setCliente(new OUT_Clientes());
            Session.getTransaction().commit();
            
            System.out.println("get TRANSACTION COMMITED");
            if(Session.isOpen())
                Session.close();
        }
        else
        {
            cfg=new OUT_Proyectos();
            cfg.setCliente(new OUT_Clientes());
        }
        System.out.println("getCorreo() METHOD ENDED");
        return cfg;
    }
    
    public List<OUT_Proyectos> getOUT_ProyectosList()
    {
        List<OUT_Proyectos> lista=null;
        try
        {			
            System.out.println("getJUR_CasosList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            // query students
            //String qry="FROM JUR_Casos ORDER BY Descripcion ASC";
            try{lista=Session.createCriteria(OUT_Proyectos.class).addOrder(Order.desc("__id_OUT_Proyecto")).list();}catch(Exception e){
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
    
    public OUT_Proyectos newProyecto(OUT_Proyectos model)
    {
        System.out.println("newJUR_Caso METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            model.setZz_FechaCreacion(ca.getCurrentTimestamp());
            model.setZz_FechaModificacion(model.getZz_FechaCreacion());
            
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED");
            Session.save(model);
            System.out.println("new cliente SAVE SUCCESSFUL");
            Session.getTransaction().commit();
            System.out.println("new TRANSACTION COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("new EXCEPTION: "+e.toString());
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("newJUR_Caso METHOD PERFORMED");
        return model;
    }
    
    public List loadClienteesList()
    {
        List lista=null;
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("load TRANSACTION STARTED");
            Criteria c=Session.createCriteria(OUT_Clientes.class);
            System.out.println("CRITERIA CREATED");
            c.add(Restrictions.and(Restrictions.eq("ClienteOUT",1),Restrictions.lt("Estatus",3)));
            c.addOrder(Order.asc("Nombre"));
            ProjectionList pl=Projections.projectionList();
            pl.add(Projections.id());
            pl.add(Projections.property("Nombre"));
            c.setProjection(pl);
            System.out.println("CRITERIA SET");
            lista=c.list();
            Session.getTransaction().commit();
            System.out.println("CURRENT SESSION CLOSED");
        }
        catch(Exception e)
        {
            System.out.println("SESSION WAS ALREDY CLOSED: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        return lista;
    }

    private void setModuleProperties(Object module,String usu)
    {
        Session=Factory.getCurrentSession();
        Session.beginTransaction();
        String c[]=module.getClass().toString().split("[.]");
        System.out.println("setModuleProperties METHOD REACHED: "+c[c.length-1]);
//        switch(c[c.length-1])
//        {
//            case "Clientes":Clientes tmp=(Clientes)module;
//                            tmp.setZz_UsuarioModificacion(usu);
//                            tmp.setZz_FechaModificacion(ca.getCurrentTimestamp());
//                            Session.update(tmp);
//                            javax.faces.context.FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("logCard");
//                            break;
//        }
        Session.getTransaction().commit();
    }
    
    public void deleteProyecto(OUT_Proyectos record)
    {
        try {				
            long id=record.getId_OUT_Proyecto();
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
            System.out.println("delete JUR_Caso SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public OUT_Proyectos modifyProyecto(OUT_Proyectos source){
        OUT_Proyectos target=null;
        try {	
            source.setZz_FechaModificacion(ca.getCurrentTimestamp());
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            /*target=setNewValues(source,new JUR_Casos());
            if(target.getCliente()==null)
                target.setCliente(new JUR_Clientes());*/
            Session.update(source);
            Session.getTransaction().commit();
            System.out.println("modify TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("modify EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("modifyJUR_Caso METHOD PERFORMED");
        return source;
    }
    
    public void deleteMultipleProyectos(OUT_Proyectos[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple JUR_Caso TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            Session.getTransaction().commit();
            System.out.println("delete multiple JUR_Caso TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple JUR_Caso EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple JUR_Caso SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    private OUT_Proyectos setNewValues(OUT_Proyectos source,OUT_Proyectos target)
    {
        if(target==null)
            target=(OUT_Proyectos) Session.get(OUT_Proyectos.class,source.getId_OUT_Proyecto());
        System.out.println("modify "+target.getId_OUT_Proyecto()+" SUCCESSFULLY RETRIEVED");
        target.setNombre(source.getNombre()==null?null:source.getNombre().trim());
        System.out.println("Correo MODIFY SUCCESSFUL TO "+target.getNombre());
        target.setDescripcion(source.getDescripcion()==null?null:source.getDescripcion().trim());
        System.out.println("Notas MODIFY SUCCESSFUL TO "+target.getDescripcion());
        target.setEstatus(source.getEstatus()==null?null:source.getEstatus().trim());
        System.out.println("Tipo MODIFY SUCCESSFUL TO "+target.getEstatus());
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
