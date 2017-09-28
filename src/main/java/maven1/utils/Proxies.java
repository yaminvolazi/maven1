package maven1.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Proxies
{
  public static List<Proxy> list = new ArrayList();
  static String myProxiesPath = "Resources/proxy";
  
  public static void doGenerateAllZip() {
    BufferedReader br = null;
    try
    {
      br = new BufferedReader(new FileReader(myProxiesPath));
      String sCurrentLine; while ((sCurrentLine = br.readLine()) != null) {
        String[] tmp = sCurrentLine.split(":");
        
        Proxy p = new Proxy(tmp[0], tmp[1], tmp[2], tmp[3]);
        p.generateZip();
        list.add(p);
        System.out.println(p.getIP() + "zip created");
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}