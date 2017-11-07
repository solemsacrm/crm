package com.solemsa.jsf.DAOs.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_Etapas;
import com.solemsa.hibernate.entities.JUR.JUR_Notificaciones;
import com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos;
import com.solemsa.hibernate.entities.JUR.JUR_Tareas;
import com.solemsa.jsf.data.CommonActions;
import static com.solemsa.jsf.data.JUR.JUR_EtapasOrden.notificacionTareaNueva;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class daoJUR_RecursosHumanos {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoJUR_RecursosHumanos(SessionFactory Factory){
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

    public List<JUR_RecursosHumanos> getJUR_RecursosHumanosList()
    {
        List<JUR_RecursosHumanos> lista=null;
        try
        {			
            System.out.println("getJUR_RecursosHumanosList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            // query students
            //String qry="FROM JUR_RecursosHumanos ORDER BY Puesto ASC";
            try{
                Criteria c=Session.createCriteria(JUR_RecursosHumanos.class);
                c.addOrder(Order.asc("Nombre")).addOrder(Order.asc("Apellidos"));
                System.out.println("CRITERIA CREATED");
                lista=c.list();
            }
            catch(Exception e)
            {
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
    
    public JUR_RecursosHumanos getJUR_RecursoHumano(long id)
    {
        System.out.println("getJUR_RecursoHumano METHOD REACHED");
        JUR_RecursosHumanos cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(JUR_RecursosHumanos)Session.get(JUR_RecursosHumanos.class,id);
            System.out.println("REQUESTED cliente FETCHED");
            if(cfg.getJUR_Tareas()==null)
                cfg.setJUR_Tareas(ca.newArrayList());
            int n=cfg.getJUR_Tareas().size();
            cfg.setJUR_TareasLength(n);
            System.out.println("JUR_Tareas LIST INITIALIZED WITH: "+n);
            Session.getTransaction().commit();
            for(int i=0;i<n;i++)
            {
                JUR_Tareas tt=cfg.getJUR_Tareas().get(i);
                if(tt.getFechaAsignacion()!=null)
                    tt.setFechaAsignacionString(ca.parseDate(tt.getFechaAsignacion().toString(),"-"));
                if(tt.getFechaEntrega()!=null)
                    tt.setFechaEntregaString(ca.parseDate(tt.getFechaEntrega().toString(),"-"));
            }
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
            cfg=new JUR_RecursosHumanos();            
            cfg.setJUR_Tareas(ca.newArrayList());
        }
        return cfg;
    }
    
    public List<JUR_Casos> loadJUR_CasosList()
    {
        System.out.println("loadJUR_CasosList METHOD REACHED");
        List<JUR_Casos> l=null;
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            Criteria c=Session.createCriteria(JUR_Casos.class);
            c.add(Restrictions.lt("EtapaActual",2));
            l=c.list();
            System.out.println("LIST LOADED WITH: "+l.size());
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            if(l!=null)
                l=null;
            System.out.println("loadJUR_CasosList EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadJUR_CasosList METHOD PERFORMED");
        return l;
    } 
    
    public JUR_RecursosHumanos newJUR_RecursoHumano(JUR_RecursosHumanos model)
    {
        System.out.println("newJUR_RecursoHumano METHOD REACHED");
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
            try
            {
                Session.close();
                System.out.println("new JUR_RecursoHumano SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return cloneModel(model);
    }
    
    public void deleteJUR_RecursoHumano(JUR_RecursosHumanos record)
    {
        try {				
            long id=record.getId_JUR_RecursoHumano();
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
            System.out.println("delete JUR_RecursoHumano SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleJUR_RecursosHumanos(JUR_RecursosHumanos[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple JUR_RecursoHumano TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            Session.getTransaction().commit();
            System.out.println("delete multiple JUR_RecursoHumano TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple JUR_RecursoHumano EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple JUR_RecursoHumano SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public JUR_RecursosHumanos modifyJUR_RecursoHumano(JUR_RecursosHumanos source){
        JUR_RecursosHumanos target=null;
        try {						
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            target=setNewValues(source,new JUR_RecursosHumanos());
            System.out.println("material"+target.getId_JUR_RecursoHumano()+" SUCCESSFULLY MODIFIED");
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
                System.out.println("modify JUR_RecursoHumano SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target;
    }
    
    private JUR_RecursosHumanos setNewValues(JUR_RecursosHumanos source,JUR_RecursosHumanos target){
        if(target==null)
            target=(JUR_RecursosHumanos) Session.get(JUR_RecursosHumanos.class,source.getId_JUR_RecursoHumano());
        else if(target.getId_JUR_RecursoHumano()<1)
        {
            target.setZz_FechaCreacion(source.getZz_FechaCreacion());
            target.setZz_UsuarioCreacion(source.getZz_UsuarioCreacion());
        }
        target.setNombre(source.getNombre()==null?null:source.getNombre().trim());
        System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getNombre());
        target.setApellidos(source.getApellidos()==null?null:source.getApellidos().trim());
        System.out.println("Apellidos MODIFY SUCCESSFUL TO "+target.getApellidos());
        target.setPuesto(source.getPuesto()==null?null:source.getPuesto().trim());
        System.out.println("Puesto MODIFY SUCCESSFUL TO "+target.getPuesto());
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
    
    private JUR_RecursosHumanos cloneModel(JUR_RecursosHumanos model)
    {
        return setNewValues(model,new JUR_RecursosHumanos());
    }
    
    public JUR_Tareas setJUR_TareaRelationships(String[] arr,JUR_RecursosHumanos model,List<JUR_Casos> Casos,com.solemsa.jsf.data.ValueLists Etapas)
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
            record.setJUR_RecursoHumano(model);
        }
        id=ca.parseStringLong(arr[4]);
        if(id>0)
        {
            n=Casos.size();
            for(i=0;i<n;i++)
            {
                record.setJUR_Caso(Casos.get(i));
                if(record.getJUR_Caso().getId_JUR_Caso()==id)
                    break;
            }
            if(i<n)
            {
                id=ca.parseStringLong(arr[5]);
                if(id>0 )
                {
                    List<JUR_Etapas>l=null;
                    try
                    {
                        Session=Factory.getCurrentSession();
                        Session.beginTransaction();
                        JUR_Casos tmp=(JUR_Casos)Session.load(JUR_Casos.class,record.getJUR_Caso().getId_JUR_Caso());
                        n=tmp.getJUR_Etapas().size();
                        l=tmp.getJUR_Etapas();
                        Session.getTransaction().commit();
                    }catch(Exception e)
                    {
                        System.out.println("setJUR_TareaRelationships EXCEPTION: "+e);
                        e.printStackTrace();
                        n=0;
                    }
                    finally
                    {
                        if(Session.isOpen())
                            Session.close();
                    }
                    for(i=0;i<n;i++)
                    {
                        record.setJUR_Etapa(l.get(i));
                        if(record.getJUR_Etapa().getId_JUR_Etapa()==id)
                            break;
                    }
                    if(i>=n)
                        record.setJUR_Etapa(null);
                }
            }
            else record.setJUR_Caso(null);
            
        }
        
        return record;
    }
    
    public long modifyJUR_Tareas(JUR_Tareas record,String[] arr,String usu)
    {
        System.out.println("modifyJUR_EtapaDetalle METHOD REACHED with: "+record.getId_JUR_Tarea());
        try
        {
            Session=Factory.getCurrentSession();
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
            Session.beginTransaction();
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
    
    public List<JUR_Etapas> loadEtapasByCaso(long caso)
    {
        System.out.println("loadEtapasByCaso METHOD REACHED");
        List<JUR_Etapas> l=null;
        JUR_Casos tmp=null;
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            tmp=(JUR_Casos)Session.load(JUR_Casos.class,caso);
            int n=tmp.getJUR_Etapas().size();
            System.out.println("etapas LIST LOADED WITH: "+n+" records");
            l=tmp.getJUR_Etapas();
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("loadEtapasByCaso EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadEtapasByCaso METHOD PERFORMED");
        return l;
    }
    
    public JUR_Etapas cloneEtapa(JUR_Etapas source)
    {
        JUR_Etapas target=new JUR_Etapas();
        target.setId_JUR_Etapa(source.getId_JUR_Etapa());
        target.setDescripcion(source.getDescripcion());
        target.setEtapaActualNombre(source.getEtapaActualNombre());
        target.setJUR_Caso(source.getJUR_Caso());
        target.setJUR_EtapaDetalle(source.getJUR_EtapaDetalle());
        target.setJUR_EtapaDetalleLength(source.getJUR_EtapaDetalleLength());
        target.setNombre(source.getNombre());
        target.setZz_FechaCreacion(source.getZz_FechaCreacion());
        target.setZz_FechaModificacion(source.getZz_FechaModificacion());
        target.setZz_UsuarioCreacion(source.getZz_UsuarioCreacion());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        return target;
    }
    
    public JUR_Casos cloneCaso(JUR_Casos source)
    {
        JUR_Casos target=new JUR_Casos();
        target.setId_JUR_Caso(source.getId_JUR_Caso());
        target.setZz_FechaCreacion(source.getZz_FechaCreacion());
        target.setZz_UsuarioCreacion(source.getZz_UsuarioCreacion());
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
    
}
