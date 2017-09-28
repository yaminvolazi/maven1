package maven1.utils;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;

public class Proxy {
	String IP;
	String Port;
	String username;
	String Passwd;
	String path;
	boolean status;

	public Proxy() {
	}

	public Proxy(String iP, String port, String username, String passwd) {
		this.IP = iP;
		this.Port = port;
		this.username = username;
		this.Passwd = passwd;
		this.status = true;
	}

	public void generateZip() {
		try {
			File manifest = new File("Resources/proxyFolder/manifest.json");
			File bg = new File("Resources/proxyFolder/background.js");

			File directory = new File("Resources/proxyFolder/proxies");
			if (!directory.exists()) {
				directory.mkdir();
			}
			FileOutputStream fos = new FileOutputStream("Resources/proxyFolder/proxies/extension_" + this.IP + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			File tmpfile = new File(directory.getAbsolutePath() + "/" + bg.getName());
			Files.copy(bg, tmpfile);
			Files.copy(manifest, new File(directory.getAbsolutePath() + "/" + manifest.getName()));

			String content = IOUtils.toString(new FileInputStream(tmpfile), StandardCharsets.UTF_8);
			content = content.replaceAll("%ip%", this.IP);
			content = content.replaceAll("%port%", this.Port);
			content = content.replaceAll("%username%", this.username);
			content = content.replaceAll("%password%", this.Passwd);
			IOUtils.write(content, new FileOutputStream(tmpfile), StandardCharsets.UTF_8);

			String file1Name = "manifest.json";
			String file2Name = "background.js";

			addToZipFile(file1Name, zos, directory.getAbsolutePath());
			addToZipFile(file2Name, zos, directory.getAbsolutePath());
			zos.close();
			fos.close();
			this.path = ("Resources/proxyFolder/proxies/extension_" + this.IP + ".zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return this.IP + ":" + this.Port + ":" + this.username + ":" + this.Passwd;
	}

	private static void addToZipFile(String fileName, ZipOutputStream zos, String path)
    throws FileNotFoundException, IOException
  {
    File file = new File(path + "/" + fileName);
    FileInputStream fis = new FileInputStream(file);
    ZipEntry zipEntry = new ZipEntry(fileName);
    zos.putNextEntry(zipEntry);
    
    byte[] bytes = new byte['a'];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zos.write(bytes, 0, length);
    }
    
    zos.closeEntry();
    fis.close();
  }

	public String getIP() {
		return this.IP;
	}

	public void setIP(String iP) {
		this.IP = iP;
	}

	public String getPort() {
		return this.Port;
	}

	public void setPort(String port) {
		this.Port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return this.Passwd;
	}

	public void setPasswd(String passwd) {
		this.Passwd = passwd;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
