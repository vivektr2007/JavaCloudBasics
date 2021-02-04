package com.pixel.section.service;

import java.util.List;

import com.pixel.aws.model.AddFormModel;
import com.pixel.section.model.MasterSectionModel;
import com.pixel.section.model.SubSectionModel;
import com.pixel.section.model.ViewSectionModel;

public interface SectionService {

	public List<MasterSectionModel> getMasterSection();
	
	public List<SubSectionModel> getSubSectionById(String masterid);

	public void saveSubSectionData(AddFormModel addFormModel);
	
	public List<ViewSectionModel> getMasterSectionDetails(String masterid);
	
}
