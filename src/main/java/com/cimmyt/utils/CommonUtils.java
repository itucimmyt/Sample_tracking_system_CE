/*
Copyright 2013 International Maize and Wheat Improvement Center
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.cimmyt.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.cimmyt.bean.ItemSampleBean;
import com.cimmyt.bean.LocationBean;
import com.cimmyt.bean.SeasonBean;
import com.cimmyt.constants.ShipmentStatus;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.Season;
import com.cimmyt.model.bean.Status;

/**
 * @author CIMMYT
 * Class generic to thins commons 
 */
public class CommonUtils {

	/**
	 * Method that load the samples in the study it will be save in DB
	 * @param platename
	 * @param plateloc
	 * @param beanSample
	 * @param index
	 * @return
	 */
	public static SampleDetail getSampleDetdail(String platename,String plateloc, ItemSampleBean beanSample
			, int index, LabStudy beanLabStudy){
		SampleDetail sampleDetail = new SampleDetail();
		sampleDetail.setLabstudyid(beanLabStudy);
		sampleDetail.setPlatename(platename);
		sampleDetail.setPlateloc(plateloc);
		if(beanSample != null){
			if(beanSample.getGid() != null)
				sampleDetail.setBreedergid(beanSample.getGid());
			if(beanSample.getEntryNo() != null)
				sampleDetail.setEntryNo(beanSample.getEntryNo());
			if (beanSample.getPlantNo() != null)
				sampleDetail.setNplanta(beanSample.getPlantNo());
			if (beanSample.getAcc() != null	&& !beanSample.getAcc().toString().equals("") &&
					beanSample.getAcc().toString().length() > 255)
				sampleDetail.setNval(beanSample.getAcc().substring(0, 254));
			else
				sampleDetail.setNval(beanSample.getAcc().toString());
			if(beanSample.getComment() != null && !beanSample.getComment().toString().equals("")
					&& beanSample.getComment().toString().length() < 255)
				sampleDetail.setPriority(beanSample.getComment().toString());
			if (beanSample.getSpecie() != null && !beanSample.getSpecie().toString().equals("")
					&& beanSample.getSpecie().toString().length() < 99){
				sampleDetail.setSpecie(beanSample.getSpecie().toString());
			}
			if (beanSample.getSampleID() > 0){
				sampleDetail.setSamplegid(beanSample.getSampleID());
			}else{
				sampleDetail.setSamplegid(null);
			}

			sampleDetail.setLocationid(getLocationCatalog(beanSample.getLocationidBean()));
			sampleDetail.setSeasonid(getSeaconCatalog(beanSample.getSeasonidBean()));
		}
		sampleDetail.setSelforsend(ShipmentStatus.NO_SELECTED.getId());
		if (beanSample != null && beanSample.getStudysampleid() != null)
			sampleDetail.setStudysampleid(beanSample.getStudysampleid());
		else
		sampleDetail.setStudysampleid(null);
		sampleDetail.setControltype(StrUtils.getTypeOfControl(index));
		return sampleDetail;
	}
	/**
	 * Method to change object LocationBean to Location Catalog
	 * @param locationBean
	 * @return
	 */
	private static LocationCatalog getLocationCatalog(LocationBean locationBean){
		LocationCatalog locationCatalog = locationBean.getLocationBean(locationBean);
		return locationCatalog;
	}
	/**
	 * Method to change object SeasonBean to Season
	 * @param bean
	 * @return
	 */
	private static Season getSeaconCatalog (SeasonBean bean){
		Season season = bean.getSeason(bean);
		return season;
	}
	/**
	 * Method that copy the attributes of sample detail
	 * @param Sample detail
	 */
	public static SampleDetail copySampleDetail (SampleDetail detail){
		SampleDetail detailCopy = new SampleDetail();
		if (detail != null){
			if (detail.getBreedergid() != null )  
			detailCopy.setBreedergid(detail.getBreedergid());
			if (detail.getLabstudyid()!= null && detail.getLabstudyid().getProject() != null && 
					detail.getLabstudyid().getProject().getProjectid() != null)
				detailCopy.setLabstudyid(detail.getLabstudyid());
			if (detail.getLocationid() != null && detail.getLocationid().getLocationid() != null);
			detailCopy.setLocationid(detail.getLocationid());
			if (detail.getSeasonid() != null && detail.getSeasonid().getSeasonid() != null)
				detailCopy.setSeasonid(detail.getSeasonid());
			if (detail.getNplanta() != null)
				detailCopy.setNplanta(detail.getNplanta());
			if (detail.getSamplegid() != null)
				detailCopy.setSamplegid(detail.getSamplegid());
			if (detail.getStudysampleid() != null )				
			detailCopy.setStudysampleid(detail.getStudysampleid());
			return detailCopy;
		}
		return null;
	}
	/**
	 * Method that return a status by default of studies 
	 */
	public static Status getStatusNew(){
		Status status = new Status();
		status.setIdStatus(ConstantsDNA.ID_STATUS);
		status.setStatusDescription(ConstantsDNA.DESCRIPTION_STATUS);
		return status;
	}	
	
	public static void marshallXMLInConsole(Object obj){
		
		
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(obj, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
