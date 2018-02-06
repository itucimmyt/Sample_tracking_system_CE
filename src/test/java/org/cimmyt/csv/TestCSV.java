package org.cimmyt.csv;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zkoss.util.media.AMedia;

import com.cimmyt.bean.FileSampleCSVBean;
import com.cimmyt.csv.FileManagerCSV;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class TestCSV {

	@Autowired
	private FileManagerCSV serviceManagerFileCSV;
	
	private File file90= null;
	private File fileControl = null;
	private File filehistorical = null;
	private File fileIncorectPath = null;
	private File fileReturnEmpty = null;

	@Before
	public void initResources (){
			 try {
				  file90 = new File("src/test/resources/testfile/test90.csv");
				  fileControl = new File("src/test/resources/testfile/Test2 testcontrol.csv");
				  filehistorical = new File("src/test/resources/testfile/test-historical-information.csv");
				  fileIncorectPath = new File("src/test/resources/testfile/test-historical-information1.csv");
				  fileReturnEmpty =  new File("src/test/resources/testfile/test90_and_return.csv");
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	}	

	@Test
	public void test1ValidateSimpleFile() throws FileNotFoundException{
		FileSampleCSVBean fileSampleCsvBean = serviceManagerFileCSV.getFileSampleCSV(new AMedia(file90, null, null), 1, true);
		Assert.assertEquals(fileSampleCsvBean.getListRowCSV().size(), 89);
		Assert.assertEquals(fileSampleCsvBean.getListHeaders().size(), 8);
	}


	@Test
	public void test2ValidatewithoutSizeColums() throws FileNotFoundException{
		FileSampleCSVBean fileSampleCsvBean = serviceManagerFileCSV.getFileSampleCSV(new AMedia(file90, null, null), 1, false);
		Assert.assertEquals(fileSampleCsvBean.getListRowCSV().size(), 89);
		Assert.assertEquals(fileSampleCsvBean.getListHeaders().size(), 8);
	}

	@Test
	public void test3ValidatewithControl() throws FileNotFoundException{
		FileSampleCSVBean fileSampleCsvBean = serviceManagerFileCSV.getFileSampleCSV(new AMedia(fileControl, null, null), 2, true);
		Assert.assertEquals(fileSampleCsvBean.getListRowCSV().size(), 1);
		Assert.assertEquals(fileSampleCsvBean.getListHeaders().size(), 12);
	}

	@Test
	public void historicalInformation() throws FileNotFoundException{
		FileSampleCSVBean fileSampleCsvBean = serviceManagerFileCSV.getFileSampleCSV(new AMedia(filehistorical, null, null), 1, true);
		Assert.assertEquals(fileSampleCsvBean.getListRowCSV().size(), 95);
		Assert.assertEquals(fileSampleCsvBean.getListHeaders().size(), 9);
	}

	@Test
	public void optionNotAvaliableForReadFile() throws FileNotFoundException{
		FileSampleCSVBean fileSampleCsvBean = serviceManagerFileCSV.getFileSampleCSV(new AMedia(filehistorical, null, null), 3, true);
		Assert.assertNull(fileSampleCsvBean);
	}

	@Test(expected = NullPointerException.class)
	public void nullFile() throws FileNotFoundException{
		@SuppressWarnings("unused")
		FileSampleCSVBean fileSampleCsvBean = serviceManagerFileCSV.getFileSampleCSV(null, 3, true);
	}
	@Test(expected = FileNotFoundException.class)
	public void incorrectPath () throws FileNotFoundException{
		@SuppressWarnings("unused")
		FileSampleCSVBean fileSampleCsvBean = serviceManagerFileCSV.getFileSampleCSV(new AMedia(fileIncorectPath,null,null), 1, true);
	}
	@Test
	public void fileWithReturnEmpty () throws FileNotFoundException{
		@SuppressWarnings("unused")
		FileSampleCSVBean fileSampleCsvBean = serviceManagerFileCSV.getFileSampleCSV(new AMedia(fileReturnEmpty,null,null), 1, true);
	}
}
