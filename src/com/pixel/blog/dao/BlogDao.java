package com.pixel.blog.dao;

import java.util.List;
import java.util.Map;

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
import com.pixel.section.model.InterviewQuestionsBean;

public interface BlogDao {

	String BLOG_DESC_QUERY = "select * from blog_def";
	public List<BlogDescripitionModel> getBlogDescriptionList();

	String MENU_DESC_QUERY = "select * from menu_detail where is_visible = 'Y'";
	public List<MenuBean> getMenuDetail();

	public Map<String, Integer> getContentTitleIdMap();
	
	String CONTENT_BY_MENU_ID_QUERY = "select a.content_id, a.header,a.short_desc,a.menu_id,a.creation_date,a.next,a.prev,a.tags, b.parent,"
			+ " b.menu_desc,ifnull(c.like_count,0) as like_count FROM content_desc"
			+ " a INNER JOIN  menu_detail b ON a.menu_id = b.menu_id and a.menu_id=? LEFT OUTER JOIN content_lv c	ON a.content_id=c.content_id"
			+ "  order by a.creation_date desc ";
	public List<AddFormModel> getContentByMenuId(int menuId);

	public Map<String, Integer> getMenuTitleIdMap();
	
	String CONTENT_BY_CONTENT_ID_QUERY = "select a.content_id , a.header,  a.short_desc,  a.menu_id ,a.creation_date,  a.next,  a.prev ,a.tags,"
			+ " b.parent, b.menu_desc from content_desc a , menu_detail b where a.menu_id=b.menu_id and content_id=? ";
	public AddFormModel getTopicDetailByTopicId(int topicId);
	
	String CONTENT_DETAIL_BY_CONTENT_ID_QUERY = "select content_id , long_desc,  content_type,  order_id ,content_unique_id from content_detail"
			+ " where content_id=? order by order_id asc";
	public List<AddFormDetailModel> getTopicDetailForTopic(Integer topicId);

	String INSERT_CONTENT_DETAIL = "INSERT INTO content_detail(content_id,long_desc,content_type,order_id,creation_date) values(?,?,?,?,?)";
	String INSERT_CONTENT_DESC = "INSERT INTO content_desc ( content_id, header,  short_desc, menu_id ,  next,  prev, creation_date,tags)"
			+ " values(?, ?,?,?,?,?,?,?)";
	public void insertAddForm(AddFormModel addFormModel);

	String INSERT_INTERVIEW_ANS_DETAIL = "INSERT INTO interview_questions_ans_details(content_id,long_desc,content_type,order_id,creation_date) values(?,?,?,?,?)";
	String INSERT_INTERVIEW_QUES_DETAIL = "INSERT INTO interview_questions ( pk, company_name,  interview_question, answer)"
			+ " values(?,?,?,?)";
	public void insertInterviewQuestionDetails(InterviewQuestionsBean addFormModel);
	
	String SELECT_MAX_CONTENT_ID = "SELECT MAX(content_id)+1 FROM content_desc";
	public int selectMaxContentId();
	
	String SELECT_MAX_INTERVIEW_QUESTION_ID = "SELECT MAX(pk)+1 FROM interview_questions";
	public int selectMaxInterviewContentId();
	
	
	String INSERT_MENU_DESC = "INSERT INTO menu_detail ( menu_desc, parent,  target, is_visible) values(?, ?,?,?)";

	public void insertMenuForm(Map<String, String> parameters);
	
	String PARENT_MENU_QUERY = "select distinct parent from menu_detail where is_visible = 'Y' order by parent_order_id,child_order_id";
	public Map<String, String> getParentMenues();
	
	String SUB_MENU_QUERY = "select menu_id, menu_desc from menu_detail where parent = ? order by parent_order_id,child_order_id";
	public Map<String, String> getSubMenues(String parent);
	
	String CONTENT_BY_MENU_ID_QUERY_LIMIT = "select a.content_id, a.header,a.short_desc,a.menu_id,a.creation_date,a.next,a.prev,a.tags,"
			+ " b.parent, b.menu_desc,ifnull(c.like_count,0) as like_count FROM content_desc a INNER JOIN  menu_detail b ON a.menu_id ="
			+ " b.menu_id $$ LEFT OUTER JOIN content_lv c	ON a.content_id=c.content_id ## order by a.creation_date desc limit ? , ?";
	public List<AddFormModel> getContentByMenuIdWithLimit(int menuId, int startIndex, int endIndex, String searchKeyword);
	
	
	String ROW_COUNT_QUERY = "select count(*) from content_desc ";
	public int rowCount(int menuId, String searchKeywork);
	
	String CHECK_LOGIN_QUERY = "select concat(first_name, ' ' , last_name) as user_name from login_detail where email_id=? and password = ? and"
			+ " status='A'";
	public String checkLogin(Map<String, String> parameters);

	String CONTENT_BY_LATEST_QUERY = "select a.content_id,(select count(*) from blog_like_details bld"
			+ " where bld.topic_id = a.content_id) as like_count, a.header,a.short_desc,a.menu_id,"
			+ "a.creation_date,a.next,a.prev,a.tags, b.parent, "
			+ "b.menu_desc FROM content_desc a INNER JOIN  menu_detail b ON a.menu_id = b.menu_id"
			+ " order by a.creation_date desc limit ?";
	
	String CONTENT_BY_LATEST_QUERY_USING_PARENT_MENU_ID = "select a.content_id,(select count(*) from blog_like_details bld"
			+ " where bld.topic_id = a.content_id) as like_count, a.header,a.short_desc,a.menu_id,"
			+ "a.creation_date,a.next,a.prev,a.tags, b.parent, "
			+ "b.menu_desc FROM content_desc a INNER JOIN  menu_detail b ON a.menu_id = b.menu_id and b.parent_order_id=?"
			+ " order by a.creation_date desc limit ?";
	
	public List<AddFormModel> getLatestContent(int limit,int parentMenuId);
	
	String CONTENT_DELETE_QUERY = "DELETE FROM content_desc";
	String CONTENT_DETAIL_DELETE_QUERY = "DELETE FROM content_detail";
	public void deleteBlogContent(String[] deleteArr, String type);
	
	String SUBSCRIBER_EMAIL_CHECK = "select count(1) from subscriber_list where email_id = ?";
	public boolean isExistsEmail(String emailId);
	
	String SUBSCRIBE_EMAIL = "insert into subscriber_list(email_id) values(?)";
	public void subscribe(String emailId);
	
	String UPDATE_CONTENT_DESC ="update content_desc a set a.header =?,  a.short_desc =?,  a.menu_id =?, a.tags =? where content_id= ?";
	public void updateContentDesc(AddFormModel addFormModel);
	
	String UPDATE_CONTENT_LIKE ="update content_lv a set a.like_count =? where content_id= ?";
	public void updateContentLike(int contentId);
	
	String INSERT_CONTENT_LIKE ="INSERT INTO content_lv(content_id,like_count,view_count) values(?,?,?)";
	
	String GET_CONTENT_LIKE ="SELECT content_id, like_count,view_count from content_lv where content_id= ?";
	public List<ContentLVModel> getContentLike(int contentId);
	
	String CONTENT_BY_SEARCH_QUERY = "select a.content_id, a.header,a.short_desc,a.menu_id,a.creation_date,a.next,a.prev,a.tags, b.parent,"
			+ " b.menu_desc,ifnull(c.like_count,0) as like_count FROM content_desc a INNER JOIN  menu_detail b ON a.menu_id = b.menu_id"
			+ " LEFT OUTER JOIN content_lv c ON a.content_id=c.content_id where a.header like ? order by a.creation_date desc";
	public List<AddFormModel> getSearchContent(String searchKeyword);
	
	String UNIQUE_TAGS_LIST ="select distinct tags from content_desc";
	public List<String> getTagList(); 
	
	String CODE_LIST = "select code_id,code_info,code_value,code_desc,del_yn from code_info";
	public List<CodeModel> getCodeList();

	String BLOG_COMMENT_QUERY = "select * from blog_comment_detail where topic_id=?";
	public List<BlogCommentBean> getBlogCommentList(int topicId);

	String BLOG_COMMENT_SUBMIT_QUERY = "insert into blog_comment_detail(topic_id, comment_desc, comment_by) values(?,?,?)";
	public void submitComment(Map<String, Object> params);

	String BLOG_COMMENT_REPLY_SUBMIT_QUERY = "insert into blog_comment_reply_detail(comment_id, reply_desc, reply_by) values(?,?,?)";
	public void submitReply(Map<String, Object> params);

	String CHECK_USER_LOGIN_QUERY = "select * from login_detail where email_id=? and password = ? and is_admin=?";
	public List<LoginBean> userLogin(Map<String, String> params);

	String SUBMIT_BLOG_LIKE_QUERY = "insert into blog_like_details(user_id, topic_id) values(?,?)";
	String COUNT_LIKE_FOR_USER_QUERY = "select count(*) from blog_like_details where user_id = ? and topic_id=?";
	String COUNT_LIKE_QUERY = "select count(*) from blog_like_details where topic_id=?";
	public int submitLike(Map<String, Object> params);

	String CHECK_USER_ID_EXISTS_QUERY = "select * from login_detail where email_id=?";
	public boolean checkUserIdExists(String userId);

	String REGISTER_USER_QUERY = "insert into login_detail(first_name, last_name, email_id, password) values(?,?,?,?)";
	public void registerUser(Map<String, String> params);
	
	String BLOG_COUNT_QUERY = "select count(*) from content_desc";
	public int getBlogCount();
	
	String GET_YOUTUBE_LIST = "select content_id, youtubeurl, youtubecode from youtube_content_info where content_id=?";
	String INSERT_YOUTUBE_LIST = "insert into youtube_content_info(content_id, youtubeurl,youtubecode) values(?,?,?)";
	String DELETE_YOUTUBE_LIST = "delete from youtube_content_info where content_id=?";
	public List<YouTubeModel> getYouTubeList(int contentId);
	public void addYouTubeList(int contentId, List<String> youtubeList);
	
	String CHECK_IP_EXISTS_QUERY = "select * from ip_tracking where ip_address=?";
	String INSERT_IP_QUERY = "insert into ip_tracking(ip_address, access_date) values(?,now())";
	public void insertIPdetails(String ipAddress);
	
	String IP_COUNT_QUERY = "select count(*) from ip_tracking";
	public int getVisitCount();
	
	String QUESTION_OFTHE_DAY_QRY = "select seq , heading , question_details , "
			+ "date_format(creation_date,'%d-%m-%Y') creation_date from question_of_the_day order by creation_date desc limit 1";
	public QuestionOfTheDayBean getQuestionOfTheDay();
	
	String INTERVIEW_QUESTIONS_QRY = "select * from interview_questions where del_yn = 'n' order by company_name, created_date";
	public Map<String, List<InterviewQuestionsBean>> getInterviewQuestions();
	
	String INGTERVIEW_QUESTION_DETAIL_BY_ID = "select * from interview_questions where pk=?";
	String INGTERVIEW_QUESTION_ANS_DETAIL_BY_ID = "select * from interview_questions_ans_details where content_id=? order by order_id";
	public InterviewQuestionsBean getInterViewQuestionDetailById(int topicId);
	
}
