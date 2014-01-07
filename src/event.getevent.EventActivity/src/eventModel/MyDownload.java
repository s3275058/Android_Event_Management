package eventModel;


import java.net.*;
import java.io.*;
 
public class MyDownload
{
  public static void main(String[] args)
  {
     try
     {
        /*
         * Get a connection to the URL and start up
         * a buffered reader.
         */
        long startTime = System.currentTimeMillis();
        URL url = new URL("http://mekong.rmit.edu.vn/~s3275058/MAD/upload/");
        url.openConnection();
        InputStream reader = url.openStream();
        FileOutputStream writer = new FileOutputStream("C:/mura-newest.zip");
        byte[] buffer = new byte[153600];
        int totalBytesRead = 0;
        int bytesRead = 0;
 
        System.out.println("Reading ZIP file 150KB blocks at a time.\n");
 
        while ((bytesRead = reader.read(buffer)) > 0)
        {  
           writer.write(buffer, 0, bytesRead);
           buffer = new byte[153600];
           totalBytesRead += bytesRead;
        }
 
        long endTime = System.currentTimeMillis();
 
        System.out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
        writer.close();
        reader.close();
     }
     catch (MalformedURLException e)
     {
        e.printStackTrace();
     }
     catch (IOException e)
     {
        e.printStackTrace();
     }
 
  }
 
}