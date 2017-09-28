package maven1.utils;

import java.io.File;

public class LogWriter
{
  public static void write(String path, String msg)
  {
    try
    {
      String repo = "log_reports";
      File dir = new File(repo);
      if (!dir.isDirectory()) {
        dir.mkdir();
        dir.canWrite();dir.canExecute();dir.canRead();
      }
      File f = new File(dir.getAbsolutePath() + File.separator + path);
      if (!f.exists())
        f.createNewFile();
      java.io.FileWriter fw = new java.io.FileWriter(f.getAbsolutePath(), true);
      
      java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
      bw.write(msg + "\n");
      bw.close();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
