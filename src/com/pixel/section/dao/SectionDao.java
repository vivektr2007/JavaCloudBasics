package com.pixel.section.dao;

import java.util.List;

import com.pixel.aws.model.AddFormModel;
import com.pixel.section.model.MasterSectionModel;
import com.pixel.section.model.SubSectionModel;
import com.pixel.section.model.ViewSectionModel;

public interface SectionDao {
	
	String SELECT_MASTER_SECTION = "select * from tblmastersection";
	public List<MasterSectionModel> getMasterSection();
	
	String SELECT_SUB_SECTION_BY_ID = "select * from tblsubsection where masterid=? order by orderid";
	public List<SubSectionModel> getSubSectionById(String masterid);
	
	String INSERT_SUB_SECTION_DETAIL = "insert into tblsubsectiondata(content_id,subsectionid,title,tags,prev, next, created_date) values(?,?,?,?,?,?,?)";
	String INSERT_SUB_SECTION_DESC = "insert into tblsubsectiondetail(content_id, long_desc, content_type, order_id, creation_date) values(?,?,?,?,?)";
	public void saveSubSectionData(AddFormModel addFormModel);
	
	String SELECT_MAX_CONTENT_ID = "SELECT MAX(content_id)+1 FROM tblsubsectiondata";
	
	
	String SELECT_MASTERSECTION_DETAILS_1 = "SELECT c.submasterdesc,a.content_id, a.long_desc,a.content_type,a.order_id,a.creation_date,b.subsectionid,b.title,b.tags,b.prev,b.next FROM tblsubsectiondetail a";
	String SELECT_MASTERSECTION_DETAILS_2 = " LEFT OUTER JOIN tblsubsectiondata b ON a.content_id=b.content_id LEFT OUTER JOIN tblsubsection c ON c.submasterid=b.subsectionid WHERE c.submasterid in (select d.masterid from tblmastersection d";
	String SELECT_MASTERSECTION_DETAILS_3 = " where c.masterid=d.masterid and d.masterid= ?) order by a.content_id,a.creation_date";
	public List<ViewSectionModel> getMasterSectionDetails(String masterid);
}
