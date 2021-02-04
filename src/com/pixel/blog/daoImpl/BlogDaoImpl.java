package com.pixel.blog.daoImpl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pixel.aws.model.AddFormDetailModel;
import com.pixel.aws.model.AddFormModel;
import com.pixel.aws.model.BlogCommentBean;
import com.pixel.aws.model.BlogDescripitionModel;
import com.pixel.aws.model.CodeModel;
import com.pixel.aws.model.ContentLVModel;
import com.pixel.aws.model.LoginBean;
import com.pixel.aws.model.MenuBean;
import com.pixel.aws.model.QuestionOfTheDayBean;
import com.pixel.aws.model.YouTubeModel;
import com.pixel.blog.dao.BlogDao;
import com.pixel.blog.mapper.BlogCommentRowMapper;
import com.pixel.blog.mapper.BlogDescRowMapper;
import com.pixel.blog.mapper.CodeInfoRowMapper;
import com.pixel.blog.mapper.ContentDetailRowMapper;
import com.pixel.blog.mapper.ContentLVRowMapper;
import com.pixel.blog.mapper.ContentRowMapper;
import com.pixel.blog.mapper.InterviewQuestionMapper;
import com.pixel.blog.mapper.LatestContentRowMapper;
import com.pixel.blog.mapper.LoginBeanRowMapper;
import com.pixel.blog.mapper.MenuDescRowMapper;
import com.pixel.blog.mapper.QuestionOfTheDay;
import com.pixel.blog.mapper.TagRowMapper;
import com.pixel.blog.mapper.YouTubeRowMapper;
import com.pixel.section.model.InterviewQuestionsBean;
import com.pixel.utils.CommonIFace;
import com.pixel.utils.CommonUtils;

@Repository
public class BlogDaoImpl implements BlogDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<BlogDescripitionModel> getBlogDescriptionList() {
		List<BlogDescripitionModel> list = jdbcTemplate.query(BLOG_DESC_QUERY, new BlogDescRowMapper());

		return list;
	}

	public List<MenuBean> getMenuDetail() {
		List<MenuBean> list = jdbcTemplate.query(MENU_DESC_QUERY, new MenuDescRowMapper());

		return list;
	}
	
	public Map<String, Integer> getContentTitleIdMap(){
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from content_desc");

		Map<String, Integer> returnMap = new HashMap<>();
		for(Map<String, Object> tmpMap : list) {
			returnMap.put(String.valueOf(tmpMap.get("header")).replaceAll(" ", "-").replaceAll("/", "-").
					replaceAll("\\?", "-").replaceAll("\\.", "-").replaceAll("\\,", "-"), Integer.parseInt(String.valueOf(tmpMap.get("content_id"))));
		}
		
		return returnMap;
	}

	public Map<String, Integer> getMenuTitleIdMap(){
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from menu_detail");

		Map<String, Integer> returnMap = new HashMap<>();
		for(Map<String, Object> tmpMap : list) {
			returnMap.put(String.valueOf(tmpMap.get("menu_desc")).replaceAll(" ", "-").replaceAll("/", "-").
					replaceAll("\\?", "-").replaceAll("\\.", "-").replaceAll("\\,", "-"), Integer.parseInt(String.valueOf(tmpMap.get("menu_id"))));
		}
		
		return returnMap;
	}
	
	
	public List<AddFormModel> getContentByMenuId(int menuId) {
		Object args[] = { menuId };
		List<AddFormModel> list = null;
		try {
			list = jdbcTemplate.query(CONTENT_BY_MENU_ID_QUERY, args, new LatestContentRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public AddFormModel getTopicDetailByTopicId(int topicId) {

		Object args[] = { topicId };
		List<AddFormModel> list = null;
		try {
			list = jdbcTemplate.query(CONTENT_BY_CONTENT_ID_QUERY, args, new ContentRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		AddFormModel model = list.get(0);
		model.setAddFormDetailModel(getTopicDetailForTopic(model.getContentId()));

		return model;
	}

	public List<AddFormDetailModel> getTopicDetailForTopic(Integer topicId) {

		List<AddFormDetailModel> list = null;

		Object args[] = { topicId };
		try {
			list = jdbcTemplate.query(CONTENT_DETAIL_BY_CONTENT_ID_QUERY, args, new ContentDetailRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<AddFormDetailModel> getIngterviewQuestionAnsDetailsById(Integer topicId) {

		List<AddFormDetailModel> list = null;

		Object args[] = { topicId };
		try {
			list = jdbcTemplate.query(INGTERVIEW_QUESTION_ANS_DETAIL_BY_ID, args, new ContentDetailRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public void insertAddForm(AddFormModel addFormModel) {

		Integer countent_id = selectMaxContentId();

		Object[] params1 = new Object[] { countent_id, addFormModel.getTitleId(), addFormModel.getShortDescId(),
				addFormModel.getMenuId(), addFormModel.getPrevId(), addFormModel.getNextId(), new Date(), addFormModel.getTags()};

		int[] types1 = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR };

		jdbcTemplate.update(INSERT_CONTENT_DESC, params1, types1);

		List<AddFormDetailModel> addFormDetailModelList = addFormModel.getAddFormDetailModel();

		insertAddDetail(addFormDetailModelList, countent_id);

	}
	
	
	public void insertInterviewQuestionDetails(InterviewQuestionsBean addFormModel) {

		Integer countent_id = selectMaxInterviewContentId();

		Object[] params1 = new Object[] { countent_id, addFormModel.getCompanyName(), addFormModel.getInterviewQuestion(), addFormModel.getShortAnswer()};

		int[] types1 = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

		jdbcTemplate.update(INSERT_INTERVIEW_QUES_DETAIL, params1, types1);

		List<AddFormDetailModel> addFormDetailModelList = addFormModel.getAnswerDetails();

		insertInterviewQuesAnsDetail(addFormDetailModelList, countent_id);

	}

	private void insertAddDetail(List<AddFormDetailModel> addFormDetailModelList, Integer countent_id) {

		Iterator<AddFormDetailModel> itr = addFormDetailModelList.iterator();

		while (itr.hasNext()) {

			AddFormDetailModel model = itr.next();

			Object[] params2 = new Object[] { countent_id, model.getLongDescId(), model.getAddMoreType(),
					model.getOrderId(), new Date() };

			int[] types2 = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP };

			jdbcTemplate.update(INSERT_CONTENT_DETAIL, params2, types2);

		}
	}
	
	private void insertInterviewQuesAnsDetail(List<AddFormDetailModel> addFormDetailModelList, Integer countent_id) {

		Iterator<AddFormDetailModel> itr = addFormDetailModelList.iterator();

		while (itr.hasNext()) {

			AddFormDetailModel model = itr.next();

			Object[] params2 = new Object[] { countent_id, model.getLongDescId(), model.getAddMoreType(),
					model.getOrderId(), new Date() };

			int[] types2 = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP };

			jdbcTemplate.update(INSERT_INTERVIEW_ANS_DETAIL, params2, types2);

		}
	}

	public int selectMaxContentId() {

		return jdbcTemplate.queryForObject(SELECT_MAX_CONTENT_ID,Integer.class);

	}

	public void insertMenuForm(Map<String, String> parameters) {
		Object[] params1 = new Object[] { parameters.get("MENU_DESC"), parameters.get("PARENT"),
				parameters.get("TARGET"), parameters.get("IS_VISIBLE") };

		int[] types1 = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };

		jdbcTemplate.update(INSERT_MENU_DESC, params1, types1);
	}

	public Map<String, String> getParentMenues() {

		Map<String, String> result = new HashMap<String, String>();
		List<Map<String, Object>> data = jdbcTemplate.queryForList(PARENT_MENU_QUERY);
		Iterator<Map<String, Object>> itr = data.iterator();
		while (itr.hasNext()) {
			Map<String, Object> map = itr.next();
			result.put(String.valueOf(map.get("parent")), String.valueOf(map.get("parent")));

		}

		return result;
	}

	public Map<String, String> getSubMenues(String parent) {
		Object args[] = { parent };
		Map<String, String> result = new HashMap<String, String>();
		List<Map<String, Object>> data = jdbcTemplate.queryForList(SUB_MENU_QUERY, args);
		Iterator<Map<String, Object>> itr = data.iterator();
		while (itr.hasNext()) {
			Map<String, Object> map = itr.next();
			result.put(String.valueOf(map.get("menu_id")), String.valueOf(map.get("menu_desc")));

		}

		return result;
	}

	public List<AddFormModel> getContentByMenuIdWithLimit(int menuId, int startIndex, int endIndex, String searchKeyword) {
		Object args[] = { startIndex, endIndex };
		List<AddFormModel> list = null;
		try {
			String query = CONTENT_BY_MENU_ID_QUERY_LIMIT;
			if( menuId != 0){
				query = query.replace("$$", " and a.menu_id="+menuId);
			}
			if( searchKeyword != null && !"".equals(searchKeyword)){
				query = query.replace("##", " where (a.header like '%"+searchKeyword+"%' or a.tags like '%"+searchKeyword+"%')");
			}
			if(query.indexOf("$$") !=-1){
				query = query.replace("$$", "");
			}
			if(query.indexOf("##") !=-1){
				query = query.replace("##", "");
			}
			list = jdbcTemplate.query(query, args, new LatestContentRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int rowCount(int menuId, String searchKeyword) {
		int rowCount = 0;
		try {
			
			String whereCondition = "";
			
			String query = ROW_COUNT_QUERY;
			if( menuId != 0){
				whereCondition = whereCondition + " and menu_id="+menuId;
			}
			if( searchKeyword != null && !"".equals(searchKeyword)){
				whereCondition = whereCondition + " and (header like '%"+searchKeyword+"%' or tags like '%"+searchKeyword+"%')";
			}
			if(!"".equals(whereCondition)){
				whereCondition = whereCondition.substring(4);
				whereCondition = " where " + whereCondition;
				query = query + whereCondition;
			}
			rowCount = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	public String checkLogin(Map<String, String> parameters) {
		Object args[] = { parameters.get("EMAIL_ID"), parameters.get("PASSWORD") };
		String name = "";
		try {
			name = jdbcTemplate.queryForObject(CHECK_LOGIN_QUERY, args, String.class);
		} catch (Exception e) {

		}
		return name;
	}

	public List<AddFormModel> getLatestContent(int limit,int parentMenuId) {
		List<AddFormModel> list = null;
		try {
			if(parentMenuId == -1){
				Object args1[] = { limit };
				list = jdbcTemplate.query(CONTENT_BY_LATEST_QUERY, args1, new LatestContentRowMapper());
			}else{
				Object args2[] = { parentMenuId, limit };
				list = jdbcTemplate.query(CONTENT_BY_LATEST_QUERY_USING_PARENT_MENU_ID, args2, new LatestContentRowMapper());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void deleteBlogContent(String[] deleteArr, String type) {
		String in = "";
		String whereCondition = "";
		for (int i = 0; i < deleteArr.length; i++) {
			in = in + "," + deleteArr[i];
		}
		if (in.length() > 0) {
			in = in.substring(1);
			whereCondition = " where content_id in (" + in + ")";
		}
		
		try {

			if (type.equalsIgnoreCase("CONTENT_DESC")) {
				jdbcTemplate.update(CONTENT_DELETE_QUERY + whereCondition);
			} else if (type.equalsIgnoreCase("CONTENT_DETAIL")) {
				jdbcTemplate.update(CONTENT_DETAIL_DELETE_QUERY + whereCondition);
			} else if (type.equalsIgnoreCase("ALL")) {
				jdbcTemplate.update(CONTENT_DELETE_QUERY + whereCondition);
				jdbcTemplate.update(CONTENT_DETAIL_DELETE_QUERY + whereCondition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isExistsEmail(String emailId) {
		boolean flag = false;
		Object args[] = { emailId };
		int name = 0;
		try {
			name = jdbcTemplate.queryForObject(SUBSCRIBER_EMAIL_CHECK, args, Integer.class);
		} catch (Exception e) {

		}
		if (name > 0) {
			flag = true;
		}

		return flag;
	}

	public void subscribe(String emailId) {
		Object[] params1 = new Object[] { emailId };
		int[] types1 = new int[] { Types.VARCHAR };
		jdbcTemplate.update(SUBSCRIBE_EMAIL, params1, types1);
	}

	public void updateContentDesc(AddFormModel addFormModel) {

		Object[] params1 = new Object[] { addFormModel.getTitleId(), addFormModel.getShortDescId(),
				addFormModel.getMenuId(), addFormModel.getTags(),addFormModel.getContentId()};

		int[] types1 = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER,Types.VARCHAR, Types.INTEGER };

		jdbcTemplate.update(UPDATE_CONTENT_DESC, params1, types1);

		String deleteArr[] = { "" + addFormModel.getContentId() };
		// Delete Content Details
		deleteBlogContent(deleteArr, "CONTENT_DETAIL");

		List<AddFormDetailModel> addFormDetailModelList = addFormModel.getAddFormDetailModel();

		insertAddDetail(addFormDetailModelList, addFormModel.getContentId());
	}

	public synchronized void updateContentLike(int contentId) {
		
		List<ContentLVModel> list = getContentLike(contentId);
				
		if (list!=null && list.size()>0) {
			Object[] params1 = new Object[] { list.get(0).getLikeCount()+1, contentId };
			int[] types1 = new int[] { Types.INTEGER, Types.INTEGER };

			jdbcTemplate.update(UPDATE_CONTENT_LIKE, params1, types1);
			
		} else {
			
			Object[] params1 = new Object[] { contentId,1,1 };
			int[] types1 = new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER };

			jdbcTemplate.update(INSERT_CONTENT_LIKE, params1, types1);
		}
	}

	public List<ContentLVModel> getContentLike(int contentId) {
		Object args[] = { contentId };
		List<ContentLVModel> list = jdbcTemplate.query(GET_CONTENT_LIKE, args, new ContentLVRowMapper());
		return list;
	}

	public List<AddFormModel> getSearchContent(String searchKeyword) {
		List<AddFormModel> list = null;
	  
		try {
			list = jdbcTemplate.query(CONTENT_BY_SEARCH_QUERY,new String[]{"%"+searchKeyword+"%"},new LatestContentRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getTagList() {
		return jdbcTemplate.query(UNIQUE_TAGS_LIST,new TagRowMapper());
	}

	public List<CodeModel> getCodeList() {
		List<CodeModel> list = jdbcTemplate.query(CODE_LIST, new CodeInfoRowMapper());
		return list;
	}
	
	public List<BlogCommentBean> getBlogCommentList(int topicId){
		List<BlogCommentBean> list = jdbcTemplate.query(BLOG_COMMENT_QUERY,new Object[]{topicId}, new BlogCommentRowMapper(jdbcTemplate));
		
		return list;
	}

	public void submitComment(Map<String, Object> params) {
		
		Object[] params1 = new Object[] { (Integer)params.get("topicId"),(String)params.get("comment"),(String)params.get("loggerInUserId") };
		int[] types1 = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR };
		jdbcTemplate.update(BLOG_COMMENT_SUBMIT_QUERY, params1, types1);
	}

	public void submitReply(Map<String, Object> params) {
		
		Object[] params1 = new Object[] { (Integer)params.get("commentId"),(String)params.get("reply"),(String)params.get("loggerInUserId") };
		int[] types1 = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR };
		jdbcTemplate.update(BLOG_COMMENT_REPLY_SUBMIT_QUERY, params1, types1);
	}

	public List<LoginBean> userLogin(Map<String, String> params) {
		Object []args = new Object[]{params.get("userId"), params.get("password"), params.get("admin")};
		List<LoginBean> list = jdbcTemplate.query(CHECK_USER_LOGIN_QUERY, args, new LoginBeanRowMapper());
		
		return list;
	}

	public int submitLike(Map<String, Object> params) {
		
		Object args[] = {params.get("loggerInUserId"), (Integer)params.get("contentId")};
		
		int userLikeCount = jdbcTemplate.queryForObject(COUNT_LIKE_FOR_USER_QUERY,args, Integer.class );
		if(userLikeCount == 0){
			Object[] params1 = new Object[] { params.get("loggerInUserId"),(Integer)params.get("contentId")};
			int[] types1 = new int[] { Types.VARCHAR, Types.INTEGER};
			jdbcTemplate.update(SUBMIT_BLOG_LIKE_QUERY, params1, types1);
		}
		
		updateContentLike((Integer)params.get("contentId"));
		
		return 0;
	}

	public boolean checkUserIdExists(String userId) {
		
		List<Map<String, Object>> value = jdbcTemplate.queryForList(CHECK_USER_ID_EXISTS_QUERY, userId);
		if(value != null && value.size() > 0){
			return true;
		}
		
		return false;
	}

	public void registerUser(Map<String, String> params) {
		jdbcTemplate.execute("insert into login_detail(first_name, last_name, email_id, password, is_admin) values('"+params.get("firstName")+"'"
				+ ",'"+params.get("lastName")+"','"+params.get("userName")+"','"+params.get("password")+"','N')");
	}

	public int getBlogCount() {
		
		int count = jdbcTemplate.queryForObject(BLOG_COUNT_QUERY,Integer.class);
		
		return count;
	}	
	
	public List<YouTubeModel> getYouTubeList(int contentId) {
		List<YouTubeModel> list = null;
		Object args[] = { contentId };
		try {
			list = jdbcTemplate.query(GET_YOUTUBE_LIST, args, new YouTubeRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void addYouTubeList(int contentId,List<String> youtubeCodeList) {
		
		try {
			//Delete
			Object args[] = { contentId };
			jdbcTemplate.update(DELETE_YOUTUBE_LIST, args);
			
			//Insert
			int size = youtubeCodeList.size();
			for(int i=0;i< size;i++){
				
				String youtubeCode = youtubeCodeList.get(i);
			
				Object[] params1 = new Object[] { contentId, CommonIFace.YOUTUBE_URL_EMBED+youtubeCode ,youtubeCode};
				int[] types1 = new int[] { Types.INTEGER, Types.VARCHAR,Types.VARCHAR};

				jdbcTemplate.update(INSERT_YOUTUBE_LIST, params1, types1);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void insertIPdetails(String ipAddress) {
		List<Map<String, Object>> list = null;
		Object args[] = { ipAddress };
		try {
			list = jdbcTemplate.queryForList(CHECK_IP_EXISTS_QUERY, args);
			if(list == null || list.size() == 0){
				
				Object[] params1 = new Object[] { ipAddress};
				int[] types1 = new int[] { Types.VARCHAR};
				jdbcTemplate.update(INSERT_IP_QUERY, params1, types1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getVisitCount() {
		
		return jdbcTemplate.queryForObject(IP_COUNT_QUERY, Integer.class);
	}

	public QuestionOfTheDayBean getQuestionOfTheDay() {
		
		List<QuestionOfTheDayBean> list = jdbcTemplate.query(QUESTION_OFTHE_DAY_QRY, new QuestionOfTheDay());
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, List<InterviewQuestionsBean>> getInterviewQuestions() {
		
		Map<String, List<InterviewQuestionsBean>> map = new LinkedHashMap<String, List<InterviewQuestionsBean>>();
		
		List<Map<String, Object>> data = jdbcTemplate.queryForList(INTERVIEW_QUESTIONS_QRY);
		for(Map<String, Object> tmpMap : data) {
			
			String companyName = String.valueOf(tmpMap.get("company_name"));
			String interviewQuestion = String.valueOf(tmpMap.get("interview_question"));
			String answer = String.valueOf(tmpMap.get("answer"));
			
			int pk = Integer.parseInt(String.valueOf(tmpMap.get("pk")));
			
			InterviewQuestionsBean bean = new InterviewQuestionsBean(); 
			bean.setShortAnswer(answer);
			bean.setCompanyName(companyName);
			bean.setInterviewQuestion(interviewQuestion);
			bean.setPk(pk);
			bean.setCreatedDate(CommonUtils.getDisplayDate((Date)tmpMap.get("created_date")));
			
			if(map.containsKey(companyName)) {
				map.get(companyName).add(bean);
			} else {
				ArrayList<InterviewQuestionsBean> tmpList = new ArrayList<InterviewQuestionsBean>();
				tmpList.add(bean);
				
				map.put(companyName, tmpList);
			}
		}
		
		return map;
	}

	@Override
	public int selectMaxInterviewContentId() {
		
		return jdbcTemplate.queryForObject(SELECT_MAX_INTERVIEW_QUESTION_ID,Integer.class);
	}

	@Override
	public InterviewQuestionsBean getInterViewQuestionDetailById(int topicId) {

		Object args[] = { topicId };
		List<InterviewQuestionsBean> list = null;
		try {
			list = jdbcTemplate.query(INGTERVIEW_QUESTION_DETAIL_BY_ID, args, new InterviewQuestionMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		InterviewQuestionsBean model = list.get(0);
		model.setAnswerDetails(getIngterviewQuestionAnsDetailsById(topicId));

		return model;
	}
	
}