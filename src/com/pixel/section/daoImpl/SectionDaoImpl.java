package com.pixel.section.daoImpl;

import java.sql.Types;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pixel.aws.model.AddFormDetailModel;
import com.pixel.aws.model.AddFormModel;
import com.pixel.section.dao.SectionDao;
import com.pixel.section.mapper.MasterSectionRowMapper;
import com.pixel.section.mapper.SubSectionRowMapper;
import com.pixel.section.mapper.ViewSectionRowMapper;
import com.pixel.section.model.MasterSectionModel;
import com.pixel.section.model.SubSectionModel;
import com.pixel.section.model.ViewSectionModel;

@Repository
public class SectionDaoImpl implements SectionDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<MasterSectionModel> getMasterSection(){
		List<MasterSectionModel> list = jdbcTemplate.query(SELECT_MASTER_SECTION, new MasterSectionRowMapper());
		return list;
	}

	public List<SubSectionModel> getSubSectionById(String masterid) {
		Object args[] = { masterid };
		List<SubSectionModel> list  = null;
		try {
			list = jdbcTemplate.query(SELECT_SUB_SECTION_BY_ID,args, new SubSectionRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void saveSubSectionData(AddFormModel addFormModel) {

		Integer countent_id = selectMaxContentId();

		Object[] params1 = new Object[] {countent_id, addFormModel.getMenuId(), addFormModel.getTitleId(),addFormModel.getTags(),
					addFormModel.getPrevId(), addFormModel.getNextId(), new Date()};

		int[] types1 = new int[] { Types.INTEGER, Types.INTEGER,Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP};

		jdbcTemplate.update(INSERT_SUB_SECTION_DETAIL, params1, types1);
		List<AddFormDetailModel> addFormDetailModelList = addFormModel.getAddFormDetailModel();
		insertAddDetail(addFormDetailModelList, countent_id);
	}
	
	public int selectMaxContentId() {
		return jdbcTemplate.queryForObject(SELECT_MAX_CONTENT_ID, Integer.class);
	}
	
	private void insertAddDetail(List<AddFormDetailModel> addFormDetailModelList, Integer countent_id) {
		Iterator<AddFormDetailModel> itr = addFormDetailModelList.iterator();

		while (itr.hasNext()) {
			AddFormDetailModel model = itr.next();
			Object[] params2 = new Object[] { countent_id, model.getLongDescId(), model.getAddMoreType(),
					model.getOrderId(), new Date() };
			int[] types2 = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP };
			jdbcTemplate.update(INSERT_SUB_SECTION_DESC, params2, types2);

		}
	}

	public List<ViewSectionModel> getMasterSectionDetails(String masterid) {
		Object args[] = { masterid };
		List<ViewSectionModel> list  = null;
		try {
			String SELECT_MASTERSECTION_DETAILS = SELECT_MASTERSECTION_DETAILS_1 +SELECT_MASTERSECTION_DETAILS_2+SELECT_MASTERSECTION_DETAILS_3;
			list = jdbcTemplate.query(SELECT_MASTERSECTION_DETAILS,args, new ViewSectionRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
