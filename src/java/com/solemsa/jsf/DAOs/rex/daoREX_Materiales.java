package com.solemsa.jsf.DAOs.rex;

import com.solemsa.hibernate.entities.REX.REX_Materiales;
import com.solemsa.hibernate.entities.REX.REX_Proveedores;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class daoREX_Materiales {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoREX_Materiales(SessionFactory Factory){
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
    
    public List loadProveedoresList(String tipo)
    {
        Session=Factory.getCurrentSession();
        System.out.println("CURRENT SESSION GOTTEN");
        Session.beginTransaction();
        System.out.println("load TRANSACTION STARTED");
        Criteria c=Session.createCriteria(REX_Proveedores.class);
        System.out.println("CRITERIA CREATED");
        c.add(Restrictions.eq("Tipo",tipo));
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
        System.out.println("lista: "+lista);
        return lista;
    }

    public List<REX_Materiales> getREX_MaterialesList()
    {
        List<REX_Materiales> lista=null;
        try
        {			
            System.out.println("getREX_MaterialesList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            // query students
            //String qry="FROM REX_Materiales ORDER BY Nombre ASC";
            try{lista=Session.createCriteria(REX_Materiales.class).addOrder(Order.asc("Nombre")).list();}catch(Exception e){
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
    
    public REX_Materiales getREX_Material(long id)
    {
        System.out.println("getREX_Material METHOD REACHED");
        REX_Materiales cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(REX_Materiales)Session.get(REX_Materiales.class,id);
            cfg.getREX_Oferta().size();//Forces lazy fetches to initialize LAB
            System.out.println("REQUESTED cliente FETCHED");
            Session.getTransaction().commit();
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
            cfg=new REX_Materiales();
            cfg.setREX_Oferta(ca.newArrayList());
        }
        return cfg;
    }
    
    public String newREX_Material(REX_Materiales model)
    {
        Exception ex=null;
        System.out.println("newREX_Material METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Timestamp d=ca.getCurrentTimestamp();
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
            System.out.println("new REX_Material SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        if(ex!=null)
            return "materiales";
        return null;
    }
    
    public void deleteREX_Material(REX_Materiales record)
    {
        try {				
            long id=record.getId_REX_Material();
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
            System.out.println("delete REX_Material SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleREX_Materiales(REX_Materiales[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple REX_Material TRANSACTION STARTED");
            //delete student id
            //Session.createQuery("delete from Student where id=2").executeUpdate();
            //System.out.println("student "+id+" records SUCCESSFULLY DELETED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple REX_Material TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple REX_Material EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple REX_Material SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public REX_Materiales modifyREX_Material(REX_Materiales source){
        REX_Materiales target=null;
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            target=setNewValues(source,null);
            System.out.println("material"+target.getId_REX_Material()+" SUCCESSFULLY MODIFIED");
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
                System.out.println("modify REX_Material SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private REX_Materiales setNewValues(REX_Materiales source,REX_Materiales target){
        if(target==null)
            target=(REX_Materiales) Session.get(REX_Materiales.class,source.getId_REX_Material());
        target.setNombre(source.getNombre()==null?null:source.getNombre().trim());
        System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getNombre());
        target.setNombre(source.getNombre());
        target.setTipo(source.getTipo()==null?null:source.getTipo().trim());
        System.out.println("Tipo MODIFY SUCCESSFUL TO "+target.getTipo());
        target.setUnidad(source.getUnidad()==null?null:source.getUnidad().trim());
        System.out.println("Unidad MODIFY SUCCESSFUL TO "+target.getUnidad());
        target.setFoto(source.getFoto()==null?null:source.getFoto().trim());
        System.out.println("Foto MODIFY SUCCESSFUL TO "+target.getFoto());
        target.setNotas(source.getNotas()==null?null:source.getNotas().trim());
        System.out.println("Notas MODIFY SUCCESSFUL TO "+target.getNotas());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        Timestamp d=ca.getCurrentTimestamp();
        target.setZz_FechaModificacion(d);
        System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
