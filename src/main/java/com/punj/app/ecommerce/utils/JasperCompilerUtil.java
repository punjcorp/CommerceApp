/**
 * 
 */
package com.punj.app.ecommerce.utils;

import java.io.File;
import java.io.FilenameFilter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * @author amit
 *
 */
public class JasperCompilerUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File file = new File("/home/amit/repos/CommerceApp/src/main/resources/reports");
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});

		File[] jasperFiles = null;

		for (String directoryName : directories) {

			File dirName = new File(file.getPath() + "/" + directoryName);

			jasperFiles = dirName.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".jrxml");
				}
			});

			if (jasperFiles != null && jasperFiles.length > 0) {
				for (File jasperFile : jasperFiles) {
					String fileNameStr = jasperFile.getName();
					int pos = fileNameStr.lastIndexOf(".");
					if (pos > 0) {
						fileNameStr = fileNameStr.substring(0, pos);
					}
					
					try {
						JasperCompileManager.compileReportToFile(jasperFile.getAbsolutePath(),
								jasperFile.getParent() + "/" + fileNameStr + ".jasper");
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

}
