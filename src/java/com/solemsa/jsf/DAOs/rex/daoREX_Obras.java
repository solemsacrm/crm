package com.solemsa.jsf.DAOs.rex;

import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.REX.REX_Avances;
import com.solemsa.hibernate.entities.REX.REX_Obras;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class daoREX_Obras {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoREX_Obras(SessionFactory Factory){
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
    
    public List loadClientesList()
    {
        Session=Factory.getCurrentSession();
        System.out.println("CURRENT SESSION GOTTEN");
        Session.beginTransaction();
        System.out.println("load TRANSACTION STARTED");
        Criteria c=Session.createCriteria(Clientes.class);
        System.out.println("CRITERIA CREATED");
        c.add(Restrictions.gt("ClienteREX",0));
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

    public List<REX_Obras> getREX_ObrasList()
    {
        List<REX_Obras> lista=null;
        try
        {			
            System.out.println("getREX_ObrasList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            // query students
            String qry="FROM REX_Obras ORDER BY Nombre ASC";
            try
            {
                lista=Session.createQuery(qry).list();
                System.out.println("get QUERY SUCCESSFUL: "+lista.size()+" items in list");
            }
            catch(Exception e)
            {
                if(lista!=null)
                    lista=null;
                System.out.println("get REX_Obras List EXCEPTION: "+e);
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
    
    public REX_Obras getREX_Obra(long id,boolean load)
    {
        System.out.println("getREX_Obra METHOD REACHED: "+id);
        REX_Obras cfg=null;
        if(id>0)
        {
            try
            {
                Session=Factory.getCurrentSession();
                Session.beginTransaction();
                System.out.println("get TRANSACTION STARTED");
                if(load)
                    cfg=(REX_Obras)Session.load(REX_Obras.class,id);
                else
                    cfg=(REX_Obras)Session.get(REX_Obras.class,id);
                int n=cfg.getREX_Avances().size();
                System.out.println("\tREX_Avance LIST INITIALIZED with: "+n);
                Session.getTransaction().commit();
                if(cfg.getFechaInicio()!=null)
                    cfg.setFechaInicioString(ca.parseDate(cfg.getFechaInicio().toString(),"-"));
                if(cfg.getFechaFin()!=null)
                    cfg.setFechaFinString(ca.parseDate(cfg.getFechaFin().toString(),"-"));
                System.out.println("get TRANSACTION COMMITED");
                if(cfg.get_Cliente()==null)
                    cfg.set_Cliente(new Clientes());
                for(int i=0;i<n;i++)
                {
                    REX_Avances tmp=cfg.getREX_Avances().get(i);
                    if(tmp.getFecha()!=null)
                        tmp.setFechaString(ca.parseDate(tmp.getFecha().toString(),"-"));
                }
                cfg.setREX_AvanceLenght(n);
            }
            catch(Exception e)
            {
                System.out.println("getREX_Obra EXCEPTION: "+e);
                e.printStackTrace();
            }
            finally
            {
                if(Session.isOpen())
                    Session.close();
            }
        }
        else
        {
            cfg=new REX_Obras();
            cfg.set_Cliente(new Clientes());
            cfg.setREX_Avances(ca.newArrayList());
            cfg.getREX_Avances().add(new REX_Avances());
            cfg.setREX_AvanceLenght(1);
        }
        return cfg;
    }
    
    public String newREX_Obra(REX_Obras model)
    {
        Exception ex=null;
        System.out.println("newREX_Obra METHOD REACHED");
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
            System.out.println("new REX_Obra SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        if(ex!=null)
            return "obras";
        return null;
    }
    
    public void deleteREX_Obra(REX_Obras record)
    {
        try {				
            long id=record.getId_REX_Obra();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            //REX_Obras target=(REX_Obras) Session.get(REX_Obras.class, id);
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
            System.out.println("delete REX_Obra SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleREX_Obras(REX_Obras[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple REX_Obra TRANSACTION STARTED");
            //delete student id
            //Session.createQuery("delete from Student where id=2").executeUpdate();
            //System.out.println("student "+id+" records SUCCESSFULLY DELETED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple REX_Obra TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple REX_Obra EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple REX_Obra SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public REX_Obras modifyREX_Obra(REX_Obras source,String usu)
    {
        System.out.println("modifyREX_Obra METHOD REACHED");
        try
        {
            source.setZz_FechaModificacion(ca.getCurrentTimestamp());
            source.setZz_UsuarioModificacion(usu);
            source.setFechaInicio(ca.stringToDate(source.getFechaInicioString(),"/"));
            source.setFechaFin(ca.stringToDate(source.getFechaFinString(),"/"));
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            if(source.get_Cliente().getId_Clientes()<1)
                source.set_Cliente(null);
            if(source.getId_REX_Obra()<1)
            {
                source.setZz_FechaCreacion(source.getZz_FechaModificacion());
                source.setZz_UsuarioCreacion(usu);
                Session.save(source);
            }
            else Session.update(source);
            for(int i=0;i<source.getREX_AvanceLenght();i++)
            {
                REX_Avances tmp=source.getREX_Avances().get(i);
                tmp.setZz_FechaModificacion(source.getZz_FechaModificacion());
                tmp.setZz_UsuarioModificacion(usu);
                if(tmp.getId_REX_Avance()>0)
                    Session.update(tmp);
                else
                {
                    tmp.setZz_FechaCreacion(tmp.getZz_FechaModificacion());
                    tmp.setZz_UsuarioCreacion(usu);
                    tmp.set_REX_Obra(source);
                    Session.save(tmp);
                }
            }
            Session.getTransaction().commit();
            if(source.get_Cliente()==null)
                source.set_Cliente(new Clientes());
        }
        catch(Exception e){
            System.out.println("modifyREX_Obra EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("modifyREX_Obra METHOD PERFORMED");
        return source;
    }
    
    private REX_Obras setNewValues(REX_Obras source,REX_Obras target){
        if(target==null)
            target=(REX_Obras)Session.get(REX_Obras.class,source.getId_REX_Obra());
        target.set_Cliente(new Clientes());
        System.out.println("modify "+target.getId_REX_Obra()+" SUCCESSFULLY RETRIEVED");
        target.get_Cliente().setId_Clientes(source.get_Cliente().getId_Clientes());
        System.out.println("id Cliente MODIFY SUCCESSFUL TO "+target.get_Cliente());
        target.setNombre(source.getNombre()==null?null:source.getNombre().trim());
        System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getNombre());
        target.setEstatus(source.getEstatus());
        System.out.println("Estus MODIFY SUCCESSFUL TO "+target.getEstatus());
        target.setFechaInicio(source.getFechaInicio());
        System.out.println("Fecha Inicio MODIFY SUCCESSFUL TO "+target.getFechaInicio());
        target.setFechaFin(source.getFechaFin());
        System.out.println("Fecha Fin MODIFY SUCCESSFUL TO "+target.getFechaFin());
        target.setDireccion1(source.getDireccion1()==null?null:source.getDireccion1().trim());
        System.out.println("Dirección 1 MODIFY SUCCESSFUL TO "+target.getDireccion1());
        target.setDireccion2(source.getDireccion2()==null?null:source.getDireccion2().trim());
        System.out.println("Dirección 2 MODIFY SUCCESSFUL TO "+target.getDireccion2());
        target.setDescripcion(source.getDescripcion()==null?null:source.getDescripcion().trim());
        System.out.println("Descripción MODIFY SUCCESSFUL TO "+target.getDescripcion());
        target.setCiudad(source.getCiudad()==null?null:source.getCiudad().trim());
        System.out.println("Ciudad MODIFY SUCCESSFUL TO "+target.getCiudad());
        target.setEstado(source.getEstado()==null?null:source.getEstado().trim());
        System.out.println("Estado MODIFY SUCCESSFUL TO "+target.getEstado());
        target.setPais(source.getPais()==null?null:source.getPais().trim());
        System.out.println("Pais MODIFY SUCCESSFUL TO "+target.getPais());
        target.setCodigoPostal(source.getCodigoPostal()==null?null:source.getCodigoPostal().trim());
        System.out.println("Código Postal MODIFY SUCCESSFUL TO "+target.getCodigoPostal());
        target.setMonto(source.getMonto());
        System.out.println("Monto MODIFY SUCCESSFUL TO "+target.getMonto());
        target.setMoneda(source.getMoneda()==null?null:source.getMoneda().trim());
        System.out.println("Monto MODIFY SUCCESSFUL TO "+target.getMoneda());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        Timestamp d=ca.getCurrentTimestamp();
        target.setZz_FechaModificacion(d);
        //target.setREX_Avances(modifyAvancesList(source.getREX_Avances(),d,source.getZz_UsuarioModificacion()));
        System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public List<REX_Avances> modifyAvancesList(List<REX_Avances> source,Timestamp d,String u)
    {
        System.out.println("modifyAvancesList METHOD REACHED");
        List<REX_Avances> target=ca.newArrayList();
        int n=source.size();
        for(int i=0;i<n;i++)
        {
            REX_Avances tmp=new REX_Avances(),sourceTmp=source.get(i);
            tmp.setDescripcion(sourceTmp.getDescripcion()==null?null:sourceTmp.getDescripcion());
            System.out.println("Descripicón["+i+"] MODIFY SUCCESSFUL TO "+tmp.getDescripcion());
            tmp.setEtapa(sourceTmp.getEtapa()==null?null:sourceTmp.getEtapa());
            System.out.println("Etapa["+i+"] MODIFY SUCCESSFUL TO "+tmp.getEtapa());
            tmp.setPorcentaje(sourceTmp.getPorcentaje());
            System.out.println("Porcentaje["+i+"] MODIFY SUCCESSFUL TO "+tmp.getPorcentaje());
            tmp.setFecha(sourceTmp.getFecha());
            System.out.println("Fecha["+i+"] MODIFY SUCCESSFUL TO "+tmp.getFecha());
            tmp.setZz_FechaModificacion(d);
            tmp.setZz_UsuarioModificacion(u);
            if(sourceTmp.getId_REX_Avance()<1)
            {
                tmp.setZz_FechaCreacion(d);
                tmp.setZz_UsuarioCreacion(u);
            }
            target.add(tmp);
        }
        System.out.println("Avances LIST SUCCESSFULLY MODIFIED");
        return target;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
