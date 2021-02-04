package com.pixel.blog.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.aws.model.AddFormDetailModel;
import com.pixel.aws.model.AddFormModel;
import com.pixel.aws.model.BlogCommentBean;
import com.pixel.aws.model.BlogDescripitionModel;
import com.pixel.aws.model.CodeModel;
import com.pixel.aws.model.ContentLVModel;
import com.pixel.aws.model.LoginBean;
import com.pixel.aws.model.QuestionOfTheDayBean;
import com.pixel.aws.model.YouTubeModel;
import com.pixel.blog.dao.BlogDao;
import com.pixel.blog.service.BlogService;
import com.pixel.section.model.InterviewQuestionsBean;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogDao blogDao;

	public List<BlogDescripitionModel> getBlogDescList() {

		return blogDao.getBlogDescriptionList();
	}

	public List<AddFormModel> getContentByMenuId(int menuId) {

		return blogDao.getContentByMenuId(menuId);
	}

	public AddFormModel getTopicDetailByTopicId(int topicId) {
		return blogDao.getTopicDetailByTopicId(topicId);
	}

	public void insertAddForm(AddFormModel addFormModel) {
		blogDao.insertAddForm(addFormModel);
	}

	public int selectMaxContentId() {
		return blogDao.selectMaxContentId();
	}

	public void insertMenuForm(Map<String, String> parameters) {
		blogDao.insertMenuForm(parameters);
	}

	public Map<String, String> getParentMenues() {
		return blogDao.getParentMenues();
	}

	public Map<String, String> getSubMenues(String parent) {
		return blogDao.getSubMenues(parent);
	}

	public List<AddFormModel> getContentByMenuIdWithLimit(int menuId, int pageNo, String searchKeywork) {
		int length = 15;
		int startIndex = (pageNo - 1) * length;
		//int endIndex = startIndex + (length);
		int endIndex = length;
		
		return blogDao.getContentByMenuIdWithLimit(menuId, startIndex, endIndex,searchKeywork);

	}

	public int rowCount(int menuId, String searchKeywork) {
		return blogDao.rowCount(menuId, searchKeywork);
	}

	public String checkLogin(Map<String, String> parameters) {
		return blogDao.checkLogin(parameters);
	}

	public List<AddFormModel> getLatestContent(int limit, int parentMenuId) {
		return blogDao.getLatestContent(limit,parentMenuId);
	}

	public void deleteBlogContent(String[] deleteArr, String type) {
		blogDao.deleteBlogContent(deleteArr, type);

	}

	public boolean isExistsEmail(String emailId) {
		return blogDao.isExistsEmail(emailId);
	}

	public void subscribe(String emailId) {
		blogDao.subscribe(emailId);
	}

	public List<AddFormDetailModel> getTopicDetailForTopic(Integer topicId) {
		return blogDao.getTopicDetailForTopic(topicId);
	}

	public void updateContentDesc(AddFormModel addFormModel) {
		blogDao.updateContentDesc(addFormModel);
	}

	public void updateContentLike(int contentId) {
		blogDao.updateContentLike(contentId);
	}

	public List<ContentLVModel> getContentLike(int contentId) {
		return blogDao.getContentLike(contentId);
	}

	public List<AddFormModel> getSearchContent(String searchKeyword) {
		return blogDao.getSearchContent(searchKeyword);
	}

	public List<CodeModel> getCodeList() {
		return blogDao.getCodeList();
	}

	public List<BlogCommentBean> getBlogCommentList(int topicId) {
		return blogDao.getBlogCommentList(topicId);
	}

	public void submitComment(Map<String, Object> params) {
		blogDao.submitComment(params);
	}

	public void submitReply(Map<String, Object> params) {
		blogDao.submitReply(params);
	}

	public boolean userLogin(Map<String, String> params, HttpServletRequest request) {
		List<LoginBean> list = blogDao.userLogin(params);
		if(list != null && list.size() > 0){
			request.getSession().setAttribute("loggerInUserName", list.get(0).getUserName());
			request.getSession().setAttribute("loggerInUserId", list.get(0).getUserId());
			
			return true;
		}
		return false;
	}

	public int submitLike(Map<String, Object> params) {
		return blogDao.submitLike(params);
	}

	public List<YouTubeModel> getYouTubeList(int contentId) {
		return blogDao.getYouTubeList(contentId);
	}

	public void addYouTubeList(int contentId, List<String> youtubeList) {
		blogDao.addYouTubeList(contentId, youtubeList);
	}

	public boolean checkUserIdExists(String userId) {
		return blogDao.checkUserIdExists(userId);
	}

	public void registerUser(Map<String, String> params) {
		blogDao.registerUser(params);
	}

	public int getBlogCount() {
		return blogDao.getBlogCount();
	}

	public void insertIPdetails(String ipAddress) {
		blogDao.insertIPdetails(ipAddress);
	}

	public int getVisitCount() {
		return blogDao.getVisitCount();
	}

	public QuestionOfTheDayBean getQuestionOfTheDay() {
	
		return blogDao.getQuestionOfTheDay();
	}

	@Override
	public Map<String, List<InterviewQuestionsBean>> getInterviewQuestions() {
		
		return blogDao.getInterviewQuestions();
	}
	
	public void insertInterviewQuestionDetails(InterviewQuestionsBean addFormModel) {
		blogDao.insertInterviewQuestionDetails(addFormModel);
	}

	@Override
	public InterviewQuestionsBean getInterViewQuestionDetailById(int topicId) {
		
		return blogDao.getInterViewQuestionDetailById(topicId);
	}

	@Override
	public void getContentTitleIdMap(ServletContext context) {
		
		context.setAttribute("contentTitleMap", blogDao.getContentTitleIdMap());
	}

	@Override
	public void getMenuTitleIdMap(ServletContext context) {
		
		context.setAttribute("menuTitleMap", blogDao.getMenuTitleIdMap());
	}
}
