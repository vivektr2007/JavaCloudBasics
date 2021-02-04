package com.pixel.section.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.aws.model.AddFormModel;
import com.pixel.section.dao.SectionDao;
import com.pixel.section.model.MasterSectionModel;
import com.pixel.section.model.SubSectionModel;
import com.pixel.section.model.ViewSectionModel;
import com.pixel.section.service.SectionService;

@Service
public class SectionServiceImpl implements SectionService {
	
	@Autowired
	SectionDao sectionDao;

	public List<MasterSectionModel> getMasterSection() {
		return sectionDao.getMasterSection();
	}

	public List<SubSectionModel> getSubSectionById(String masterid) {
		return sectionDao.getSubSectionById(masterid);
	}

	public void saveSubSectionData(AddFormModel addFormModel) {
		sectionDao.saveSubSectionData(addFormModel);
	}

	public List<ViewSectionModel> getMasterSectionDetails(String masterid) {
		return sectionDao.getMasterSectionDetails(masterid);
	}

}
