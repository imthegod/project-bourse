package co.je.thesis.test.firstStateLoader.BaseStockLoader.data;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

import co.je.thesis.src.firstStateLoader.BaseStockLoader.data.BaseStockDataRetriever;

public class TestBaseStockDataRetriever {
	
	private String getFileExtension(File file) {
		
		String fileName = file.getName();
		String[] splitArray = fileName.split("\\.");
		int length = splitArray.length;
		String fileExtension = splitArray[length - 1];
		
		return fileExtension;
	}
	
	@Test
	public void testLoadStockBaseExcelFiles() {
		
		BaseStockDataRetriever fileLoader = new BaseStockDataRetriever();
		File[] loadedFiles = fileLoader.loadStockBaseExcelFiles();
		
		for (int i = 0; i < loadedFiles.length; i++) {
			
			File file = loadedFiles[i];
			String fileExtension = getFileExtension(file);
			
			assertEquals(fileExtension, "xls");
		}
	}
}
