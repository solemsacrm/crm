package com.solemsa.jsf.DAOs.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Tareas;
import com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos;
import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_Etapas;
import com.solemsa.hibernate.entities.JUR.JUR_Fechas;
import com.solemsa.hibernate.entities.JUR.JUR_Notificaciones;
import com.solemsa.jsf.data.CommonActions;
import static com.solemsa.jsf.data.JUR.JUR_EtapasOrden.notificacionTareaFinalizada;
import com.solemsa.jsf.data.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import static com.solemsa.jsf.data.JUR.JUR_EtapasOrden.notificacionTareaRegresada;
import static com.solemsa.jsf.data.JUR.JUR_EtapasOrden.notificacionTareaNueva;

public class daoJUR_Tareas {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoJUR_Tareas(SessionFactory Factory){
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
    
    public List loadJUR_CasosList()
    {
        System.out.println("loadJUR_CasosList METHOD PERFORMED");
        List lista=null;
        try
        {
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("load TRANSACTION STARTED");
            Criteria c=Session.createCriteria(JUR_Casos.class);
            System.out.println("CRITERIA CREATED");
            c.add(Restrictions.lt("EtapaActual",3));
            ProjectionList pl=Projections.projectionList();
            pl.add(Projections.id());
            pl.add(Projections.property("Nombre"));
            c.setProjection(pl);
            System.out.println("CRITERIA SET");
            lista=c.list();
            Session.getTransaction().commit();
            System.out.println("SESSION SUCCESSFULY COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("loadJUR_CasosList EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadJUR_CasosList METHOD PERFORMED");
        return lista;
    }
    
    public List loadJUR_EtapasList(long id)
    {
        System.out.println("loadJUR_EtapasList METHOD PERFORMED");
        List lista=null;
        try
        {
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("load TRANSACTION STARTED");
            Criteria c=Session.createCriteria(JUR_Etapas.class);
            System.out.println("CRITERIA CREATED");
            c.add(Restrictions.lt("JUR_Caso.__id_JUR_Caso",id));
            ProjectionList pl=Projections.projectionList();
            pl.add(Projections.id());
            pl.add(Projections.property("Nombre"));
            c.setProjection(pl);
            System.out.println("CRITERIA SET");
            lista=c.list();
            Session.getTransaction().commit();
            System.out.println("SESSION SUCCESSFULY COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("loadJUR_EtapasList EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadJUR_EtapasList METHOD PERFORMED");
        return lista;
    }
    
    public void loadJUR_CasoCliente(JUR_Tareas tarea)
    {
        System.out.println("loadJUR_CasoCliente METHOD REACHED");
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            JUR_Casos tmp=(JUR_Casos)Session.load(JUR_Casos.class,tarea.getJUR_Caso().getId_JUR_Caso());
            tmp.getCliente();
            tarea.setJUR_Caso(tmp);
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("loadJUR_CasoCliente EXEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadJUR_CasoCliente METHOD REACHED");
    }
    
    public List<JUR_Etapas> loadJUR_EtapasList(JUR_Casos caso)
    {
        System.out.println("loadJUR_EtapasList METHOD REACHED with: "+caso.getId_JUR_Caso());
        List lista=null;
        try
        {
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("load TRANSACTION STARTED");
            caso=(JUR_Casos)Session.load(JUR_Casos.class,caso.getId_JUR_Caso());
            int n=caso.getJUR_Etapas().size();
            if(n>0)
                lista=caso.getJUR_Etapas();
            else
                lista=ca.newArrayList();
            Session.getTransaction().commit();
            System.out.println("SESSION SUCCESSFULY COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("loadJUR_EtapasList EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadJUR_EtapasList METHOD PERFORMED");
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

    public List<JUR_Tareas> getJUR_TareasList(Usuario usuario)
    {
        System.out.println("getJUR_TareasList METHOD REACHED");
        List<JUR_Tareas> lista=null;	
        try
        {
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            if(usuario.isGerente())
                lista=Session.createCriteria(JUR_Tareas.class).addOrder(Order.asc("Finalizado")).addOrder(Order.desc("FechaEntrega")).list();
            else
                lista=Session.createCriteria(JUR_Tareas.class).add(Restrictions.eq("JUR_RecursoHumano.__id_JUR_RecursoHumano",(long)usuario.getJur())).addOrder(Order.asc("Finalizado")).addOrder(Order.desc("FechaEntrega")).list();
            int n=lista.size();
            System.out.println("get QUERY SUCCESSFUL: "+n+" items in list");
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
            for(int i=0;i<n;i++)
            {
                JUR_Tareas tmp=lista.get(i);
                if(tmp.getFechaAsignacion()!=null)
                    tmp.setFechaAsignacionString(ca.parseDate(tmp.getFechaAsignacion()+"","-"));
                if(tmp.getFechaEntrega()!=null)
                    tmp.setFechaEntregaString(ca.parseDate(tmp.getFechaEntrega()+"","-"));
            }
        }
        catch(Exception e)
        {
            System.out.println("getJUR_TareasList EXCEPTION: "+e);
            e.printStackTrace();
            if(lista!=null)
                lista=null;
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("getJUR_TareasList METHOD PERFORMED");
        return lista;
    }
    
    public void setJUR_TareaRelaciones(JUR_Tareas model)
    {
        if(model.getJUR_Caso()==null)
            model.setJUR_Caso(new JUR_Casos());
        if(model.getJUR_Etapa()==null)
            model.setJUR_Etapa(new JUR_Etapas());
        if(model.getJUR_RecursoHumano()==null)
            model.setJUR_RecursoHumano(new JUR_RecursosHumanos());
    }
    
    public JUR_Tareas getJUR_Tarea(long id,boolean load)
    {
        System.out.println("getJUR_Tarea METHOD REACHED");
        JUR_Tareas cfg=null;
        if(id>0)
        {
            try
            {
                System.out.println("ID RECEIVED: "+id);
                Session=Factory.getCurrentSession();
                System.out.println("CURRENT SESSION GOTTEN");
                Session.beginTransaction();
                System.out.println("get TRANSACTION STARTED");
                if(load)
                    cfg=(JUR_Tareas)Session.load(JUR_Tareas.class,id);
                else
                    cfg=(JUR_Tareas)Session.get(JUR_Tareas.class,id);
                System.out.println("REQUESTED cliente FETCHED");
                /*if(cfg.getJUR_Caso()==null)
                    cfg.setJUR_Caso(new JUR_Casos());
                if(cfg.getJUR_Etapa()==null)
                    cfg.setJUR_Etapa(new JUR_Etapas());
                if(cfg.getJUR_RecursoHumano()==null)
                    cfg.setJUR_RecursoHumano(new JUR_RecursosHumanos());*/
                if(cfg.getFechaAsignacion()!=null)
                    cfg.setFechaAsignacionString(ca.parseDate(cfg.getFechaAsignacion()+"","-"));
                if(cfg.getFechaEntrega()!=null)
                    cfg.setFechaEntregaString(ca.parseDate(cfg.getFechaEntrega()+"","-"));
                Session.getTransaction().commit();
                System.out.println("get TRANSACTION COMMITED");
            }
            catch(Exception e)
            {
                System.out.println("getJUR_Tarea EXCEPTION: "+e);
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
            cfg=new JUR_Tareas();            
            cfg.setJUR_Caso(new JUR_Casos());
            cfg.setJUR_Etapa(new JUR_Etapas());
            cfg.setJUR_RecursoHumano(new JUR_RecursosHumanos());
            cfg.setJUR_Notificaciones(ca.newArrayList());
        }
        return cfg;
    }
    
    public void newJUR_Tarea(JUR_Tareas model,String usu,boolean gerente)
    {
        System.out.println("newJUR_Tarea METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            model.setZz_FechaCreacion(ca.getCurrentTimestamp());
            model.setZz_FechaModificacion(model.getZz_FechaCreacion());
            model.setZz_UsuarioCreacion(usu);
            model.setZz_UsuarioModificacion(usu);
            if(model.getJUR_Caso().getId_JUR_Caso()<1)
                model.setJUR_Caso(null);
            if(model.getJUR_Etapa().getId_JUR_Etapa()<1)
                model.setJUR_Etapa(null);
            if(model.getJUR_RecursoHumano().getId_JUR_RecursoHumano()<1)
                model.setJUR_RecursoHumano(null);
            if(model.getFechaAsignacionString()!=null)
                model.setFechaAsignacion(ca.stringToDate(model.getFechaAsignacionString(),"/"));
            if(model.getFechaEntregaString()!=null)
                model.setFechaEntrega(ca.stringToDate(model.getFechaEntregaString(),"/"));
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED\t"+model.getJUR_Caso()+"\n\t"+model.getJUR_Etapa());
            Session.save(model);
            setJUR_NotificacionForEstatus(model,usu,gerente,true);
            System.out.println("new cliente SAVE SUCCESSFUL");
            Session.getTransaction().commit();
            if(model.getJUR_Caso()==null)
                model.setJUR_Caso(new JUR_Casos());
            if(model.getJUR_Etapa()==null)
                model.setJUR_Etapa(new JUR_Etapas());
            if(model.getJUR_RecursoHumano()==null)
                model.setJUR_RecursoHumano(new JUR_RecursosHumanos());
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
    }
    
    private void setJUR_NotificacionForEstatus(JUR_Tareas model,String usu,boolean gerente, boolean newTarea)
    {
        int n=model.getJUR_Notificaciones().size();
        if(model.isFinalizado())
        {
            
            if(!gerente)
            {  
                String[] cd=(ca.getCurrentDate()+"").split("-");
                List<JUR_Fechas>fechas=getJUR_Fechas(Integer.parseInt(cd[1]),Integer.parseInt(cd[0]));
                if(n<1)
                    Session.save(notificacionTareaFinalizada(new JUR_Notificaciones(),model,fechas,usu,ca));//método estático de JUR_EtapasOrden.java
                else if(n<2)
                    Session.update(notificacionTareaFinalizada(model.getJUR_Notificaciones().get(0),model,fechas,usu,ca));//método estático de JUR_EtapasOrden.java
                else
                {
                    JUR_Notificaciones tmp=null;
                    for(int i=0;i<n;i++)
                    {
                        tmp=model.getJUR_Notificaciones().get(i);
                        if(tmp.isParaGerente())
                            Session.update(notificacionTareaFinalizada(tmp,model,fechas,usu,ca));//método estático de JUR_EtapasOrden.java
                        else Session.delete(tmp);
                    }
                }
            }
        }
        else
        {
            if(gerente)
            {
                if(newTarea)
                    Session.save(notificacionTareaNueva(model));//método estático de JUR_EtapasOrden.java
                else
                {
                    String[] cd=(ca.getCurrentDate()+"").split("-");
                    List<JUR_Fechas>fechas=getJUR_Fechas(Integer.parseInt(cd[1]),Integer.parseInt(cd[0]));
                    if(n<1)
                        Session.save(notificacionTareaRegresada(new JUR_Notificaciones(),model,fechas,usu,ca));//método estático de JUR_EtapasOrden.java
                    else if(n<2)
                        Session.update(notificacionTareaRegresada(model.getJUR_Notificaciones().get(0),model,fechas,usu,ca));//método estático de JUR_EtapasOrden.java
                    else
                    {
                        JUR_Notificaciones tmp=null;
                        for(int i=0;i<n;i++)
                        {
                            tmp=model.getJUR_Notificaciones().get(i);
                            if(tmp.isParaGerente())
                                Session.delete(tmp);
                            else
                                Session.update(notificacionTareaRegresada(tmp,model,fechas,usu,ca));//método estático de JUR_EtapasOrden.java
                        }
                    }
                }
            }
            else if(n>0)
            {
                JUR_Notificaciones tmp=null;
                for(int i=0;i<n;i++)
                {
                    tmp=model.getJUR_Notificaciones().get(i);
                    if(tmp.isParaGerente())
                        Session.delete(tmp);
                }
            }
        }
    }
    
    public void deleteJUR_Tarea(JUR_Tareas record)
    {
        try {				
            long id=record.getId_JUR_Tarea();
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
            System.out.println("delete JUR_Tarea SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleJUR_Tareas(JUR_Tareas[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple JUR_Tarea TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            Session.getTransaction().commit();
            System.out.println("delete multiple JUR_Tarea TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple JUR_Tarea EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
    }
    
    public void modifyJUR_Tarea(JUR_Tareas source,String usu,boolean gerente){
        try
        {						
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            if(source.getJUR_Caso().getId_JUR_Caso()<1)
                source.setJUR_Caso(null);
            if(source.getJUR_Etapa().getId_JUR_Etapa()<1)
                source.setJUR_Etapa(null);
            if(source.getJUR_RecursoHumano().getId_JUR_RecursoHumano()<1)
                source.setJUR_RecursoHumano(null);
            if(source.getFechaAsignacionString()!=null)
                source.setFechaAsignacion(ca.stringToDate(source.getFechaAsignacionString(),"/"));
            if(source.getFechaEntregaString()!=null)
                source.setFechaEntrega(ca.stringToDate(source.getFechaEntregaString(),"/"));
            Session.update(source);
            setJUR_NotificacionForEstatus(source,usu,gerente,false);
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
    }
    
    private JUR_Tareas setNewValues(JUR_Tareas source,JUR_Tareas target){
        if(target==null)
            target=(JUR_Tareas) Session.get(JUR_Tareas.class,source.getId_JUR_Tarea());
        else if(target.getId_JUR_Tarea()<1)
        {
            target.setId_JUR_Tarea(source.getId_JUR_Tarea());
            target.setZz_FechaCreacion(source.getZz_FechaCreacion());
            target.setZz_UsuarioCreacion(source.getZz_UsuarioCreacion());
        }
        if(target.getJUR_Caso()==null)
            target.setJUR_Caso(new JUR_Casos());
        if(target.getJUR_Etapa()==null)
            target.setJUR_Etapa(new JUR_Etapas());
        if(target.getJUR_RecursoHumano()==null)
            target.setJUR_RecursoHumano(new JUR_RecursosHumanos());
        target.getJUR_Caso().setId_JUR_Caso(source.getJUR_Caso().getId_JUR_Caso()); 
        System.out.println("JUR_Caso MODIFY SUCCESSFUL TO "+target.getJUR_Caso().getId_JUR_Caso());
        target.getJUR_Etapa().setId_JUR_Etapa(source.getJUR_Etapa().getId_JUR_Etapa());
        System.out.println("JUR_Etapa MODIFY SUCCESSFUL TO "+target.getJUR_Etapa().getId_JUR_Etapa());
        target.getJUR_RecursoHumano().setId_JUR_RecursoHumano(source.getJUR_RecursoHumano().getId_JUR_RecursoHumano());
        System.out.println("JUR_RecursoHumano MODIFY SUCCESSFUL TO "+target.getJUR_RecursoHumano().getId_JUR_RecursoHumano());
        target.setAsunto(source.getAsunto()==null?null:source.getAsunto().trim());
        System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getAsunto());
        target.setDescripcion(source.getDescripcion()==null?null:source.getDescripcion().trim());
        System.out.println("Tipo MODIFY SUCCESSFUL TO "+target.getDescripcion());
        target.setDescripcion(source.getDescripcion()==null?null:source.getDescripcion().trim());
        System.out.println("Descripcion MODIFY SUCCESSFUL TO "+target.getDescripcion());
        target.setNotas(source.getNotas()==null?null:source.getNotas().trim());
        System.out.println("Juzgado MODIFY SUCCESSFUL TO "+target.getNotas());
        target.setFechaAsignacionString(source.getFechaAsignacionString()==null?null:source.getFechaAsignacionString().trim());
        System.out.println("FechaAsignacionString MODIFY SUCCESSFUL TO "+target.getFechaAsignacionString());
        target.setFechaEntregaString(source.getFechaEntregaString()==null?null:source.getFechaEntregaString().trim());
        System.out.println("FechaEntregaString MODIFY SUCCESSFUL TO "+target.getFechaEntregaString());
        target.setHoras(source.getHoras());
        System.out.println("Costo MODIFY SUCCESSFUL TO "+target.getHoras());
        target.setFechaEntrega(source.getFechaEntrega());
        System.out.println("EtapaActual MODIFY SUCCESSFUL TO "+target.getFechaEntrega());
        target.setFechaEntrega(source.getFechaAsignacion());
        System.out.println("EtapaActual MODIFY SUCCESSFUL TO "+target.getFechaAsignacion());
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
    
    public JUR_Tareas cloneModel(JUR_Tareas model)
    {
        return setNewValues(model,new JUR_Tareas());
    }
    
    private List<JUR_Fechas> getJUR_Fechas(int month,int year)
    {
        System.out.println("getJUR_Fechas METHOD REACHED");
        List<JUR_Fechas> lista=null;
        try
        {
            //Session=Factory.getCurrentSession();
            //Session.beginTransaction();
            String minDateF,maxDateI,minDateI,maxDateF;
            if(month>1)
            {
                month--;
                minDateI="01/"+(month<10?"0":"")+month+"/"+year;
                month++;
            }
            else minDateI="01/12/"+(year-1);
            minDateF="01/"+(month<10?"0":"")+month+"/"+year;
            if(month<10)
            {
                month+=3;
                maxDateI="01/"+(month<10?"0":"")+month+"/"+year;
                if(month<12)
                {
                    month++;
                    maxDateF="01/"+(month<10?"0":"")+month+"/"+year;
                }
                else maxDateF="01/01/"+(year+1);
            }
            else
            {
                year++;
                month=1+month-10;
                maxDateI="01/"+month+"/"+year;
                maxDateF="01/"+(month+1)+"/"+year;
            }
            System.out.println("\t"+minDateI+" - "+maxDateI);
            lista=Session.createCriteria(JUR_Fechas.class).add(Restrictions
                    .or(
                        Restrictions.between("FechaInicio",ca.stringToDate(minDateI,"/"),ca.stringToDate(maxDateI,"/")),
                        Restrictions.between("FechaFin",ca.stringToDate(minDateF,"/"),ca.stringToDate(maxDateF,"/"))
                    )).addOrder(Order.asc("FechaInicio")).list();
            //Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("getJUR_Fechas EXCEPTION: "+e);
            e.printStackTrace();
            lista=ca.newArrayList();
        }
        /*finally
        {
            if(Session.isOpen())
                Session.close();
        }*/
        System.out.println("getJUR_Fechas METHOD PERFORMED");
        return lista;
    }
    
}
