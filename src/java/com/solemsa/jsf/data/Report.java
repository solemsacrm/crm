package com.solemsa.jsf.data;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Report
{
    File Report;
    
    public Report(String report)
    {
        Report=new File(report);
    }
    
    public void exportReport(String fileName,String logo,List records)
    {
        try{
            Map<String,Object> parametros=null;
            if(logo!=null && !logo.isEmpty())
            {
                parametros=new HashMap<>();
                parametros.put("LogoPicture",new FileInputStream(new File(logo)));
            }
            JasperPrint jp=JasperFillManager.fillReport(Report.getPath(),parametros,new JRBeanCollectionDataSource(records));
            HttpServletResponse response=(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition","attachment; fileName="+fileName+".pdf");
            ServletOutputStream stream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp,stream);
            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch(Exception e)
        {
            System.out.println("exportReport EXCEPTION: "+e);
            e.printStackTrace();
        }
    }
}
