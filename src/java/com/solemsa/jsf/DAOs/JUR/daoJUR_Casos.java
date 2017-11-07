package com.solemsa.jsf.DAOs.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_Clientes;
import com.solemsa.hibernate.entities.JUR.JUR_Notificaciones;
import com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos;
import com.solemsa.hibernate.entities.JUR.JUR_Tareas;
import com.solemsa.jsf.data.CommonActions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import static com.solemsa.jsf.data.JUR.JUR_EtapasOrden.notificacionTareaNueva;

public class daoJUR_Casos {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoJUR_Casos(SessionFactory Factory){
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
        //c.add(Restrictions.and(Restrictions.eq("ClienteJUR",1),Restrictions.lt("Estatus",3)));
        c.add(Restrictions.and(Restrictions.eq("ClienteJUR",1)));
        c.addOrder(Order.asc("Nombre"));
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

    public List<JUR_Casos> getJUR_CasosList()
    {
        List<JUR_Casos> lista=null;
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
            try{lista=Session.createCriteria(JUR_Casos.class).addOrder(Order.desc("__id_JUR_Caso")).list();}catch(Exception e){
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
    
    public JUR_Casos getJUR_Caso(long id,long jur,boolean load)
    {
        System.out.println("getJUR_Caso METHOD REACHED");
        JUR_Casos cfg=null;
        if(id>0)
        {
            try
            {
                Session=Factory.getCurrentSession();
                Session.beginTransaction();
                if(load)
                    cfg=(JUR_Casos)Session.load(JUR_Casos.class,id);
                else
                    cfg=(JUR_Casos)Session.get(JUR_Casos.class,id);
                if(cfg.getJUR_Etapas()==null)
                    cfg.setJUR_Etapas(ca.newArrayList());
                int n=cfg.getJUR_Etapas().size();
                System.out.println("JUR_Etapas LIST INITIALIZED WITH: "+n);
                cfg.setJUR_EtapasLength(n);
                if(cfg.getJUR_Tareas()==null)
                    cfg.setJUR_Tareas(ca.newArrayList());
                n=cfg.getJUR_Tareas().size();
                cfg.setJUR_TareasLength(n);
                Session.getTransaction().commit();
                if(jur>0)
                    for(int i=0;i<n;i++)
                    {
                        JUR_Tareas tmpT=cfg.getJUR_Tareas().get(i);
                        if(jur!=tmpT.getJUR_RecursoHumano().getId_JUR_RecursoHumano())
                        {
                            cfg.getJUR_Tareas().remove(i);
                            i--;
                            n--;
                        }
                        else
                        {
                            tmpT.setFechaAsignacionString(ca.parseDate(tmpT.getFechaAsignacion()+"","-"));
                            tmpT.setFechaEntregaString(ca.parseDate(tmpT.getFechaEntrega()+"","-"));
                        }
                    }
                else
                    for(int i=0;i<n;i++)
                    {
                        JUR_Tareas tmpT=cfg.getJUR_Tareas().get(i);
                        tmpT.setFechaAsignacionString(ca.parseDate(tmpT.getFechaAsignacion()+"","-"));
                        tmpT.setFechaEntregaString(ca.parseDate(tmpT.getFechaEntrega()+"","-"));
                    }
                System.out.println("JUR_Tareas LIST INITIALIZED WITH: "+n);
                if(cfg.getCliente()==null)
                    cfg.setCliente(new JUR_Clientes());
            }
            catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
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
            cfg=new JUR_Casos();            
            cfg.setCliente(new JUR_Clientes());
            cfg.setJUR_Etapas(ca.newArrayList());
        }
        return cfg;
    }
    
    public JUR_Casos newJUR_Caso(JUR_Casos model,long jur)
    {
        System.out.println("newJUR_Caso METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            model.setZz_FechaCreacion(ca.getCurrentTimestamp());
            model.setZz_FechaModificacion(model.getZz_FechaCreacion());
            if(model.getCliente().getId_Clientes()<1)
                model.setCliente(null);
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED");
            Session.save(model);
            System.out.println("new cliente SAVE SUCCESSFUL");
            Session.getTransaction().commit();
            if(model.getCliente()==null)
                model.setCliente(new JUR_Clientes());
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
    
    public void deleteJUR_Caso(JUR_Casos record)
    {
        try {				
            long id=record.getId_JUR_Caso();
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
    
    public void deleteMultipleJUR_Casos(JUR_Casos[] records)
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
    
    public JUR_Casos modifyJUR_Caso(JUR_Casos source){
        JUR_Casos target=null;
        try {						
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            if(source.getCliente().getId_Clientes()<1)
                source.setCliente(null);
            /*target=setNewValues(source,new JUR_Casos());
            if(target.getCliente()==null)
                target.setCliente(new JUR_Clientes());*/
            Session.update(source);
            if(source.getCliente()==null)
                source.setCliente(new JUR_Clientes());
            System.out.println("material"+source.getId_JUR_Caso()+" SUCCESSFULLY MODIFIED");
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
    
    private JUR_Casos setNewValues(JUR_Casos source,JUR_Casos target){
        if(target==null)
            target=(JUR_Casos) Session.get(JUR_Casos.class,source.getId_JUR_Caso());
        else if(target.getId_JUR_Caso()<1)
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
        target.setJuzgado(source.getJuzgado()==null?null:source.getJuzgado().trim());
        System.out.println("Juzgado MODIFY SUCCESSFUL TO "+target.getJuzgado());
        target.setRama(source.getRama()==null?null:source.getRama().trim());
        System.out.println("Rama MODIFY SUCCESSFUL TO "+target.getRama());
        target.setNoExpediente(source.getNoExpediente()==null?null:source.getNoExpediente().trim());
        System.out.println("NoExpediente MODIFY SUCCESSFUL TO "+target.getNoExpediente());
        target.setCosto(source.getCosto());
        System.out.println("Costo MODIFY SUCCESSFUL TO "+target.getCosto());
        target.setEtapaActual(source.getEtapaActual());
        System.out.println("EtapaActual MODIFY SUCCESSFUL TO "+target.getEtapaActual());
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
    
    public void setEtapaActualNombre(JUR_Casos model)
    {
        switch(model.getEtapaActual())
        {
            
        }
    }
    
    public JUR_Tareas setJUR_TareaRelationships(String[] arr,JUR_Casos model)
    {
        long id=ca.parseStringLong(arr[1]);
        int i,n=model.getJUR_TareasLength();
        JUR_Tareas record=null;
        for (i=0;i<n;i++)
        {
            record=model.getJUR_Tareas().get(i);
            if(record.getId_JUR_Tarea()==id)
                break;
        }
        if(i>=n)
        {
            record=new JUR_Tareas();
            record.setJUR_Caso(model);
        }
        System.out.println("arr[4]: "+arr[4]);
        id=ca.parseStringLong(arr[4]);
        if(id>0)
        {
            n=model.getJUR_EtapasLength();
            for(i=0;i<n;i++)
            {
                record.setJUR_Etapa(model.getJUR_Etapas().get(i));
                if(record.getJUR_Etapa().getId_JUR_Etapa()==id)
                    break;
            }
            if(i<n)
            {
                System.out.println("arr[5]: "+arr[5]);
                id=ca.parseStringLong(arr[5]);
                if(id>0)
                {
                    JUR_RecursosHumanos l=null;
                    try
                    {
                        Session=Factory.getCurrentSession();
                        Session.beginTransaction();
                        l=(JUR_RecursosHumanos)Session.load(JUR_RecursosHumanos.class,id);
                        if(l.getJUR_Tareas()==null)
                            l.setJUR_Tareas(ca.newArrayList());
                        else id=l.getJUR_Tareas().size();
                        l.getJUR_Tareas().add(record);
                        Session.getTransaction().commit();
                    }catch(Exception e)
                    {
                        System.out.println("setJUR_TareaRelationships EXCEPTION: "+e);
                        e.printStackTrace();
                        if(l!=null)
                            l=null;
                    }
                    finally
                    {
                        if(Session.isOpen())
                            Session.close();
                        if(l==null)
                        {
                            daoJUR_RecursosHumanos drh=new daoJUR_RecursosHumanos(Factory);
                            l=drh.getJUR_RecursoHumano(id);
                        }
                        record.setJUR_RecursoHumano(l);
                    }
                }
            }
            else record.setJUR_Etapa(null);
        }
        
        System.out.println("\trecord: "+record);
        return record;
    }
    
    public long modifyJUR_Tareas(JUR_Tareas record,String[] arr,String usu)
    {
        System.out.println("modifyJUR_EtapaDetalle METHOD REACHED");
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            record.setFechaAsignacionString(arr[2].isEmpty()?null:arr[2]);
            record.setFechaAsignacion(ca.stringToDate(arr[2],"/"));
            record.setFechaEntregaString(arr[3]);
            record.setFechaEntrega(ca.stringToDate(arr[3],"/"));
            record.setAsunto(arr[6]);
            record.setDescripcion(arr[7]);
            record.setZz_UsuarioModificacion(usu);
            record.setZz_FechaModificacion(ca.getCurrentTimestamp());
            record.getJUR_RecursoHumano().setZz_FechaModificacion(record.getZz_FechaModificacion());
            record.getJUR_RecursoHumano().setZz_UsuarioModificacion(usu);
            if(record.getId_JUR_Tarea()>0)
            {
                JUR_Tareas record2=(JUR_Tareas)Session.load(JUR_Tareas.class,record.getId_JUR_Tarea());
                int n=record2.getJUR_Notificaciones().size();
                Session.getTransaction().commit();
                Session=Factory.getCurrentSession();
                Session.beginTransaction();
                record.setJUR_Caso(record2.getJUR_Caso());
                for(int i=0;i<n;i++)
                {
                    JUR_Notificaciones noti=record2.getJUR_Notificaciones().get(i);
                    if(!noti.isParaGerente()&&noti.getId_JUR_Notificacion()>0)
                    {
                        noti.setFechaInicio(record.getFechaAsignacion());
                        noti.setFechaFin(record.getFechaEntrega());
                        noti.setZz_UsuarioModificacion(usu);
                        noti.setZz_FechaModificacion(record.getZz_FechaModificacion());
                        Session.update(noti);
                        break;
                    }
                }
                Session.update(record);
            }
            else
            {
                record.setZz_FechaCreacion(record.getZz_FechaModificacion());
                record.setZz_UsuarioCreacion(usu);
                Session.save(record);
                Session.save(notificacionTareaNueva(record));//método estático de JUR_EtapasOrden.java
            }
            Session.update(record.getJUR_RecursoHumano());
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("modifyJUR_EtapaDetalle EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("modifyJUR_EtapaDetalle METHOD PERFORMED");
        return record.getId_JUR_Tarea();
    }
    
    public void deleteJUR_Tarea(long id)
    {
        System.out.println("deleteJUR_Tarea METHOD REACHED");
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            Session.delete((JUR_Tareas)Session.load(JUR_Tareas.class,id));
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("deleteJUR_Tarea EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("deleteJUR_Tarea METHOD PERFORMED");
    }
    
    private JUR_Casos cloneModel(JUR_Casos model)
    {
        return setNewValues(model,new JUR_Casos());
    }
    
}
