package com.solemsa.jsf.DAOs.rex;

import com.solemsa.hibernate.entities.REX.REX_Contrataciones;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class daoREX_Contraciones {
    
    private SessionFactory Factory;
    private Session Session;
    
    public daoREX_Contraciones(SessionFactory Factory){
        try{
            this.Factory=Factory;
            System.out.println("FACTORY CONSTRUCTION");
            
            Session=this.Factory.getCurrentSession();
            System.out.println("SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
        }
    }
    
    public List loadREX_ContratacionesList()
    {
        Session=Factory.getCurrentSession();
        System.out.println("CURRENT SESSION GOTTEN");
        Session.beginTransaction();
        System.out.println("load TRANSACTION STARTED");
        Criteria c=Session.createCriteria(REX_Contrataciones.class);
        System.out.println("CRITERIA CREATED");
        c.add(Restrictions.gt("REX_ObraREX",0));
        ProjectionList pl=Projections.projectionList();
        pl.add(Projections.id());
        pl.add(Projections.property("Nombre"));
        c.setProjection(pl);
        System.out.println("CRITERIA SET");
        List lista=c.list();
        System.out.println("c.list() LIST: "+lista);
        Session.getTransaction().commit();
        System.out.println("SESSION SUCCESSFULY COMMITED");
        try{
            Session.close();
            System.out.println("CURRENT SESSION CLOSED");
        }catch(Exception e){System.out.println("SESSION WAS ALREDY CLOSED: "+e);}
        return lista;
    }

    public List<REX_Contrataciones> getREX_ContratacionesList()
    {
        List<REX_Contrataciones> lista=null;
        try
        {			
            System.out.println("getREX_ContratacionesList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            // query students
            String qry="FROM REX_Contrataciones ORDER BY Nombre ASC";
            try
            {
                lista=Session.createQuery(qry).list();
                System.out.println("get QUERY SUCCESSFUL: "+lista.size()+" items in list");
            }
            catch(Exception e)
            {
                if(lista!=null)
                    lista=null;
                System.out.println("get REX_Contrataciones List EXCEPTION: "+e);
                System.out.println("lista SET TO NULL");
            }
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
    
    public REX_Contrataciones getREX_Contratacion(long id)
    {
        System.out.println("getREX_Contratacion METHOD REACHED: "+id);
        REX_Contrataciones cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(REX_Contrataciones)Session.get(REX_Contrataciones.class,id);
            System.out.println("REQUESTED rex_obra FETCHED");
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
            /*if(cfg.get_REX_Obra()==null)
                cfg.set_REX_Obra(new REX_Obras());*/
            try
            {
                Session.close();
                System.out.println("get REX_Contratacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            System.out.println("LIST LOAD SESSION CLOSED");
        }
        else
        {
            cfg=new REX_Contrataciones();
            //cfg.set_REX_Obra(new REX_Obras());
        }
        return cfg;
    }
    
    public String newREX_Contratacion(REX_Contrataciones model)
    {
        Exception ex=null;
        System.out.println("newREX_Contratacion METHOD REACHED");
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
            System.out.println("new rex_obra SAVE SUCCESSFUL");
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
            System.out.println("new REX_Contratacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        if(ex!=null)
            return "obras";
        return null;
    }
    
    public void deleteREX_Contratacion(REX_Contrataciones record)
    {
        try {				
            long id=record.getId_REX_Contratacion();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            //REX_Contrataciones target=(REX_Contrataciones) Session.get(REX_Contrataciones.class, id);
            //System.out.println("student "+id+" SUCCESSFULLY RETRIEVED");
            // delete the student
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
            System.out.println("delete REX_Contratacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleREX_Contrataciones(REX_Contrataciones[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple REX_Contratacion TRANSACTION STARTED");
            //delete student id
            //Session.createQuery("delete from Student where id=2").executeUpdate();
            //System.out.println("student "+id+" records SUCCESSFULLY DELETED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple REX_Contratacion TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple REX_Contratacion EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple REX_Contratacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public REX_Contrataciones modifyREX_Contratacion(REX_Contrataciones source){
        REX_Contrataciones target=null;
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            target=setNewValues(source,null);
            System.out.println("obra"+target.getId_REX_Contratacion()+" SUCCESSFULLY MODIFIED");
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
                System.out.println("modify REX_Contratacion SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private REX_Contrataciones setNewValues(REX_Contrataciones source,REX_Contrataciones target){
        if(target==null)
            target=(REX_Contrataciones) Session.get(REX_Contrataciones.class,source.getId_REX_Contratacion());
        //target.set_REX_Obra(new REX_Obras());
        System.out.println("modify "+target.getId_REX_Contratacion()+" SUCCESSFULLY RETRIEVED");
        /*target.get_REX_Obra().setId_REX_Obra(source.get_REX_Obra().getId_REX_Obra());
        System.out.println("id REX_Obra MODIFY SUCCESSFUL TO "+target.get_REX_Obra());*/
        target.setNombre(source.getNombre()==null?null:source.getNombre().trim());
        System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getNombre());
        target.setEdad(source.getEdad());
        System.out.println("Edad MODIFY SUCCESSFUL TO "+target.getEdad());
        target.setFechaContratacion(source.getFechaContratacion());
        System.out.println("Fecha Contratación MODIFY SUCCESSFUL TO "+target.getFechaContratacion());
        target.setDiasContratacion(source.getDiasContratacion());
        System.out.println("Días Contratación MODIFY SUCCESSFUL TO "+target.getDiasContratacion());
        target.setIFE(source.getIFE()==null?null:source.getIFE().trim());
        System.out.println("IFE MODIFY SUCCESSFUL TO "+target.getIFE());
        target.setNumSeguro(source.getNumSeguro()==null?null:source.getNumSeguro().trim());
        System.out.println("NumSeguro MODIFY SUCCESSFUL TO "+target.getNumSeguro());
        target.setNotas(source.getNotas()==null?null:source.getNotas().trim());
        System.out.println("Notas MODIFY SUCCESSFUL TO "+target.getNotas());
        target.setSueldo(source.getSueldo());
        System.out.println("Sueldo MODIFY SUCCESSFUL TO "+target.getSueldo());
        target.setSeguro(source.isSeguro());
        System.out.println("Sueldo MODIFY SUCCESSFUL TO "+target.getSueldo());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        java.util.Date date= new java.util.Date();
        Timestamp d=new Timestamp(date.getTime());
        target.setZz_FechaModificacion(d);
        System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
