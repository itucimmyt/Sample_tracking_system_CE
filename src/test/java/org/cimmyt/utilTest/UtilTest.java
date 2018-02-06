package org.cimmyt.utilTest;

import java.util.ArrayList;
import java.util.Collection;

import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.LoadType;
import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Project;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.Season;
import com.cimmyt.model.bean.Status;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.Tissue;

public class UtilTest {

	public static StudyTemplate getStudyTemplate(){
		StudyTemplate bean = new StudyTemplate();
		bean.setStudytemplateid(9);
		return bean;
	}
	public static Project getProject(int id){
		Project bean = new Project();
		bean.setProjectid(id);
		return bean;
	}

	
	public static Investigator getInvestigator(int id){
		Investigator bean = new Investigator();
		bean.setInvestigatorid(id);
		return bean;
	}
	
	public static Organism getOrganism(){
		Organism bean = new Organism();
		bean.setOrganismid(1);
		return bean;
	}

	public static Tissue getTissue(){
		Tissue bean = new Tissue();
		bean.setIdtissue(1);
		return bean;
	}

	public static LocationCatalog getLocationCatalog(int id){
		LocationCatalog bean = new LocationCatalog();
		bean.setLocationid(id);
		return bean;
	}

	public static Season getSeason(int id){
		Season bean = new Season();
		bean.setSeasonid(id);
		return bean;
	}
	public static Status getStatus(){
		Status bean = new Status();
		bean.setIdStatus("N");
		return bean;
	}
	public static LoadType getLoadType(){
		LoadType bean = new LoadType();
		bean.setIdLoadType(1);
		return bean;
	}

	public static Collection<SampleDetail> generateSamplesOf96SamplesByRow(int plateNumber, LabStudy labstudyid){
		Collection<SampleDetail> sampleDetailCollection = new ArrayList<SampleDetail>();
		int breedergid = 1;
		for (int _plateNumber =1; _plateNumber <= plateNumber; _plateNumber++){
			for (int column = 1; column <= 12 ; column++ ){
					for (int row = 1 ; row <= 8 ; row++){
						sampleDetailCollection.add(generateSampleDetail(labstudyid, breedergid, breedergid, "A", labstudyid.getPrefix()+_plateNumber, getLetterRow(row)+column, "N", "MAIZE",
								String.valueOf(breedergid), "", getLocationCatalog(2), getSeason(2)));
						breedergid ++;
					}
				}
		}
		return sampleDetailCollection;
	}

	private static String getLetterRow(int row){
		switch (row){
		case 1:
			return "A";
		case 2 :
			return "B";
		case 3 :
			return "C";
		case 4 :
			return "D";
		case 5 :
			return "E";
		case 6 :
			return "F";
		case 7 :
			return "G";
		case 8 :
			return "H";
		}
		return "";
	}
	

	private static SampleDetail generateSampleDetail(LabStudy labstudyid, Integer breedergid, 
			Integer samplegid, String nval, String platename, String plateloc, String selforsend
			,String specie, String priority, String controltype, 
			LocationCatalog locationid, Season seasonid){
		SampleDetail detail = new SampleDetail();
		detail.setLabstudyid(labstudyid);
		detail.setBreedergid(breedergid);
		detail.setSamplegid(samplegid);
		detail.setEntryNo(1);
		detail.setNplanta(1);
		detail.setNval(nval);
		detail.setPlatename(platename);
		detail.setPlateloc(plateloc);
		detail.setSelforsend(selforsend);
		detail.setLastmovdate(null);
		detail.setSpecie(specie);
		detail.setPriority(priority);
		detail.setControltype(controltype);
		detail.setLocationid(locationid);
		detail.setSeasonid(seasonid);
		return detail;
	}
}
