package org.iii.www.MyJasperReport;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JasperPrintFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
    	
    	InputStream jrxmlInput;
		try {
			String uri =App.class.getResource("data.jasper").getPath();
			jrxmlInput = new FileInputStream(new File(uri));
			HashMap<String, Object> parameters = new HashMap<String, Object>();//傳參數給報表
			//DataBeanList DataBeanList = new DataBeanList();
		      ArrayList<DataBean> dataList = new ArrayList();
		      DataBean d1 =new DataBean();
		      d1.setUuid("0");
		      d1.setAddress("修改0");
		      dataList.add(d1);
		      JRBeanCollectionDataSource beanColDataSource =
		      new JRBeanCollectionDataSource(dataList);
		      
			System.out.println(App.class.getResource("").getPath());
			File newFile =new File(App.class.getResource("").getPath()+"test.pdf");
			FileOutputStream out =new FileOutputStream(newFile);
			try {
				//直接轉成檔案
				JasperPrint jasperPrint =JasperFillManager.fillReport(uri, parameters,beanColDataSource);
//				JasperExportManager.exportReportToPdfFile(jasperPrint,newFile.getPath());
				
			
				//io流派
				JasperRunManager.runReportToPdfStream(jrxmlInput, out, parameters, beanColDataSource);
				       

				out.flush();
				out.close();
				  System.out.println( "export ok!" );
				 extractPrintImage("C:\\Java\\MyJasperReport\\src\\main\\java\\org\\iii\\www\\MyJasperReport\\ht.png",jasperPrint);
			} catch (JRException e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
//        JasperDesign design= JRXmlLoader.load(jrxmlInput);
//        Calendar.getInstance().getTime();
//        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        
      
       
        	
        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private static void extractPrintImage (String filePath, JasperPrint print){      
        File file = new File(filePath);  
        OutputStream ouputStream= null;    
        try{   
           ouputStream= new FileOutputStream(file);     
           DefaultJasperReportsContext.getInstance();   
           JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());      
    
           BufferedImage rendered_image = null;      
           rendered_image = (BufferedImage)printManager.printPageToImage(print, 0,1.6f); 
           ImageIO.write(rendered_image, "png", ouputStream);     
    
        }catch(Exception e){ e.printStackTrace();  }    }
}
