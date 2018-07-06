package org.imooc.controller.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.imooc.bean.BusinessList;
import org.imooc.bean.Dic;
import org.imooc.bean.Member;
import org.imooc.bean.Page;
import org.imooc.bean.record_order_createtime;
import org.imooc.constant.ApiCodeEnum;
import org.imooc.constant.DicTypeConst;
import org.imooc.constant.SessionKeyConst;
import org.imooc.dao.BusinessDao;
import org.imooc.dto.AdDto;
import org.imooc.dto.ApiCodeDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.dto.Collect_BusinessListDto;
import org.imooc.dto.CommentForSubmitDto;
import org.imooc.dto.CommentListDto;
import org.imooc.dto.OrderForBuyDto;
import org.imooc.dto.OrdersDto;
import org.imooc.errException.ZdException;
import org.imooc.service.AdService;
import org.imooc.service.BusinessService;
import org.imooc.service.CommentService;
import org.imooc.service.DicService;
import org.imooc.service.MemberService;
import org.imooc.service.OrdersService;
import org.imooc.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private AdService adService;

	@Resource
	private BusinessService businessService;

	@Resource
	private MemberService memberService;

	@Resource
	private DicService dicService;
	
	@Resource
	private OrdersService ordersService;
	
	@Resource
	private CommentService commentService;
 
	@Autowired
	private BusinessDao businessDao;
	
	private String token_key;
	
	@Value("${ad.number}")
	private int adNumber;

	@Value("${businessHome.number}")
	private int businessHomeNumber;

	@Value("${businessSearch.number}")
	private int businessSearchNumber;

	/**
	 * 首页 —— 广告（超值特惠） 广告
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value = "/homead", method = RequestMethod.GET)
	public List<AdDto> homead() throws JsonParseException, JsonMappingException, IOException {
		AdDto adDto = new AdDto();
		adDto.getPage().setPageNumber(adNumber);
		return adService.searchByPage(adDto);
	}
	/**
	 *  是根据城市关键字请求来查询商品
	 */
	@RequestMapping(value = "/homelist/{city}/{page.currentPage}", method = RequestMethod.GET)
	public BusinessListDto homelist(BusinessDto businessDto)
			throws JsonParseException, JsonMappingException, IOException {
		businessDto.getPage().setPageNumber(businessHomeNumber);
		return businessService.searchByPageForApi(businessDto);
	}
	/**
	 *  搜索结果页 - 搜索结果 - 三个参数
	 *  是根据城市/类别/关键字来请求
	 */
	@RequestMapping(value = "/search/{page.currentPage}/{city}/{category}/{keyword}", method = RequestMethod.GET)
	public BusinessListDto searchByKeyword(BusinessDto businessDto) {
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.searchByPageForApi(businessDto);
	}
	
	/**
	 * 搜索结果页 - 搜索结果 - 两个参数
	 * 是根据城市/类别  来请求
	 */
	@RequestMapping(value = "/search/{page.currentPage}/{city}/{category}", method = RequestMethod.GET)
	public BusinessListDto search(BusinessDto businessDto) {
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.searchByPageForApi(businessDto);
	}
	/**
	 * 查询收藏的商品数据
	 * @param businessDto
	 * @param username
	 * @return
	 */
	
	@RequestMapping(value = "/collect/{page.currentPage}/{username}", method = RequestMethod.GET)
	public Collect_BusinessListDto searchCollect(BusinessDto businessDto,@PathVariable("username")
	  Long username) {
		businessDto.getPage().setPageNumber(businessSearchNumber);
		Collect_BusinessListDto businesslist
		= businessService.CollectByPageForApi(businessDto,username);
		return businesslist;	
	}
	
	/**
	 * 详情页 - 商品信息
	 */
	@RequestMapping(value = "/detail/info/{id}", method = RequestMethod.GET)
	public BusinessDto detail(@PathVariable("id") Long id) {
		return businessService.getById(id);
	}

	/**
	 * 详情页 - 评论列表
	 */
	@RequestMapping(value = "/detail/comment/{currentPage}/{businessId}", method = RequestMethod.GET)
	public CommentListDto detail(@PathVariable("businessId") Long businessId,Page page) {
		return commentService.getListByBusinessId(businessId,page);
	}
	/**
	 *  订单列表
	 */
	@RequestMapping(value = "/orderlist/{username}", method = RequestMethod.GET)
	public List<OrdersDto> orderlist(@PathVariable("username") Long username) {
		//根据手机号取出会员ID
		Long memberId = memberService.getIdByPhone(username);
		return ordersService.getListByMemberId(memberId); //根据会员的主键来查询订单列表
	}
	/**
	 * 提交评论
	 */
	
	
	@RequestMapping(value = "/submitComment", method = RequestMethod.POST)
	public ApiCodeDto submitComment(CommentForSubmitDto dto) {
		ApiCodeDto result;
		Long phone = memberService.getPhone(dto.getToken());
		if (phone != null && phone.equals(dto.getUsername())) {	
			Long memberId = memberService.getIdByPhone(phone);
			OrdersDto ordersDto = ordersService.getById(dto.getId());
			if(ordersDto.getMemberId().equals(memberId)) {	
				boolean fal=commentService.add(dto);
				if(fal){
					businessDao.updateStar();
					result = new ApiCodeDto(ApiCodeEnum.SUCCESS);	
				}else{
					result = new ApiCodeDto(ApiCodeEnum.submitcommit_fail);	
				}
			} else {
				result = new ApiCodeDto(ApiCodeEnum.NO_AUTH);
			}
		} else {
			result = new ApiCodeDto(ApiCodeEnum.NOT_LOGGED);
		}
		return result;
	}
	
	
	
	/**
	 * 根据手机号下发短信验证码
	 */
	@RequestMapping(value = "/sms", method = RequestMethod.POST)
	public ApiCodeDto sms(@RequestParam("username") Long username) {
		ApiCodeDto dto = null;
		//  1、验证用户手机号是否存在（是否注册过）
		if (memberService.exists(username)) {
			// 2、生成6位随机数
			String code = String.valueOf(CommonUtil.random(6));
			// 3、保存手机号与对应的md5(6位随机数)（一般保存1分钟，1分钟后失效）
			if (memberService.saveCode(username, code)) { //保存到了一个map集合  Map<Long,String>
				//4、调用短信通道，将明文6位随机数发送到对应的手机上。
				if (memberService.sendCode(username, code)) {//打印到了控制台。模拟发验证码到手机上
					dto = new ApiCodeDto(ApiCodeEnum.SUCCESS.getErrno(), code);
				} else { 
					dto = new ApiCodeDto(ApiCodeEnum.SEND_FAIL);
				}   
			}
//			else { //已经报存过了
//				dto = new ApiCodeDto(ApiCodeEnum.REPEAT_REQUEST);   
//			}
		} else {//用户不存在
			dto = new ApiCodeDto(ApiCodeEnum.USER_NOT_EXISTS);
		}
		return dto;
	}
	/**
	 * 会员登录
	 */
	
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiCodeDto login(@RequestParam("username") Long username, 
			@RequestParam("password") String password,HttpServletRequest request) {
		ApiCodeDto dto;
		boolean bool = memberService.exists(username);
		if (bool) {
			String pass = memberService.getpassword(username);
			if (pass!=null && pass!="" &&  pass.equals(password)) {
				String token = CommonUtil.getUUID();
				memberService.saveToken(token, username);
				token_key = token;
				Member member = new Member();
				member.setPhone(username);
				dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
				dto.setToken(token);
			} else {
				dto = new ApiCodeDto(ApiCodeEnum.PASSWORD_ERROR);
			}
		} else {
			dto = new ApiCodeDto(ApiCodeEnum.USER_NOT_EXISTS);
		}
		return dto;
	}
	
	
	
	
	//退出登陆,把map集合里面的数据移除
	@RequestMapping(value = "/LoginOut", method = RequestMethod.POST)
	public ApiCodeDto LoginOut(){
		ApiCodeDto dto = new ApiCodeDto();
		if(token_key!=null && token_key!=""){
		  Long phone = memberService.getPhone(token_key);
		  if(phone!=0 && phone.toString().length()==11){
			  memberService.remove(token_key);//退出时 把map集合删除了
			  token_key = null;//把token变为空
			  return dto = new ApiCodeDto(ApiCodeEnum.loginOut_SUCCESS);
		  }else{
			  return dto = new ApiCodeDto(ApiCodeEnum.not_phone);  
		  }
		}else{
		  return dto = new ApiCodeDto(ApiCodeEnum.not_isLogin);//13
		}
	}
	//判断会员是否是登陆状态
	@RequestMapping(value = "/isLogin", method = RequestMethod.POST)
	public ApiCodeDto isLogin(){
		ApiCodeDto dto = new ApiCodeDto();
		if(token_key!=null && token_key!=""){//登陆状态
		  Long phone = memberService.getPhone(token_key); //根据token来取值，相当与sessionid
		  if(phone!=0 && phone.toString().length()==11){
			  return dto = new ApiCodeDto(ApiCodeEnum.isLogin.getErrno(),phone,token_key);//12
		  }else{
			  return dto = new ApiCodeDto(ApiCodeEnum.not_phone);  
		  }  
		}else{//不是登陆状态
		  return dto = new ApiCodeDto(ApiCodeEnum.not_isLogin);//13
		}
    }
	/**
	 * 会员注册
	 */
	
	
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public ApiCodeDto reg(@RequestParam("username") Long username,
	@RequestParam("password1") String password1, @RequestParam("password_re") String password_re,
	@RequestParam("name") String name) {
	ApiCodeDto dto = null;
    if(username!=0 && username.toString().length()==11 && CommonUtil.isNumeric(username.toString())){
	if(username!=0 && username!=null && password1!="" && password1!=null && password_re!="" &&
				password_re!=null && name!="" && name!=null){
				boolean fala1 =  memberService.exists(username);
				boolean fala2 =  memberService.exists2(name);
				if (!fala1 && !fala2) {
					if (password1.equals(password_re)) {
						int num = memberService.insertmember(name,username,password1);
					    if(num>0){
					    	dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
					    }
					} else {
						dto = new ApiCodeDto(ApiCodeEnum.Password_ERROR);
					}
				} else {
					dto = new ApiCodeDto(ApiCodeEnum.USER_EXISTS);
				}
			}else{
				dto = new ApiCodeDto(ApiCodeEnum.Input_ERROR);
			}
		}else{
			dto = new ApiCodeDto(ApiCodeEnum.not_phone);
		}
		return dto;
	}
	
	
	
	/**
	 *  会员购买下的单，新增加一条数据，重点
	 */
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ApiCodeDto order(OrderForBuyDto orderForBuyDto) {
		ApiCodeDto dto;
		Long phone = memberService.getPhone(orderForBuyDto.getToken());
		if (phone != null && phone.equals(orderForBuyDto.getUsername())) {
			Long memberId = memberService.getIdByPhone(phone);
			OrdersDto ordersDto = new OrdersDto();
			ordersDto.setNum(orderForBuyDto.getNum());
			ordersDto.setPrice(orderForBuyDto.getPrice());
			ordersDto.setBusinessId(orderForBuyDto.getId()); 
			ordersDto.setMemberId(memberId);
			ordersDto.setCreate_time(new Date());
			boolean add =ordersService.add(ordersDto);
			if(add){
				businessDao.updateNumber();
			}
			dto = new ApiCodeDto(ApiCodeEnum.BUY_SUCCESS);
		} else {
			dto = new ApiCodeDto(ApiCodeEnum.NOT_LOGGED);
		}
		return dto;
	}
	
	
	
	/**
	 * 会员收藏商品
	 */
	@RequestMapping(value="/collectbusiness" ,method=RequestMethod.POST)
	public ApiCodeDto collectbusiness(BusinessDto businessDto,@RequestParam("username") Long username){
		ApiCodeDto dto;
		if(username!=0 && username.toString().length()==11
		&& CommonUtil.isNumeric(username.toString())
		&& businessDto.getId()!=null){//手机号存在
			Long member_id = memberService.getIdByPhone(username);
			BusinessDto businessDto1 = businessService.getById(businessDto.getId());
			if(businessDto1!=null){
				if(businessDto1.getId().equals(businessDto.getId())){
					businessDto1.setMemberid(member_id);
					businessDto1.setMask(businessDto.getId());  
					//判断collect——business和business的mask和member_id是否相同
					boolean fale = businessService.getcollectBusinessById(businessDto1);			
					if(!fale){
						//收藏商品
						boolean fa = businessService.collectBusiness(businessDto1);											
						if(fa){
						   dto = new ApiCodeDto(ApiCodeEnum.business_collect_SUCCESS);
						}else{
						   dto = new ApiCodeDto(ApiCodeEnum.business_collect_FAIl);
						}
					}else{
						   dto = new ApiCodeDto(ApiCodeEnum.EXISTS_business);
					}	
				}else{
					 dto = new ApiCodeDto(ApiCodeEnum.business_not_MATH);
				}	
			}else{
				 dto = new ApiCodeDto(ApiCodeEnum.business_null);
			}
		}else{//值为空
			 dto = new ApiCodeDto(ApiCodeEnum.phone_business_null);
		}	
		return dto;
	}
	
	/**
	 * 判断该会员是否收藏商品了
	 */
	@RequestMapping(value="/isCollect",method=RequestMethod.POST)
	public ApiCodeDto isCollect(BusinessDto businessDto,@RequestParam("username") Long username){
		ApiCodeDto dto;
		Long member_id = memberService.getIdByPhone(username);
		businessDto.setMemberid(member_id);
		businessDto.setMask(businessDto.getId());
		boolean fale = businessService.getcollectBusinessById(businessDto);
		if(!fale){
		   return dto = new ApiCodeDto(ApiCodeEnum.NOT_EXISTS_business);
		}else{
		   return dto = new ApiCodeDto(ApiCodeEnum.EXISTS_business);
		}
	}
	/**
	 * 删除已经收藏的商品
	 */
	@RequestMapping(value="/deletecollectbusiness",method=RequestMethod.POST)
	public ApiCodeDto deletecollectbusiness(BusinessDto businessDto,@RequestParam("username") Long username){
		ApiCodeDto dto;
		Long member_id = memberService.getIdByPhone(username);
		businessDto.setMemberid(member_id);
		businessDto.setMask(businessDto.getId());
		boolean fale = businessService.delcollectBusinessById(businessDto);
		if(!fale){
		   return dto = new ApiCodeDto(ApiCodeEnum.delete_collectbusiness_FAIL);
		}else{
		   return dto = new ApiCodeDto(ApiCodeEnum.delete_collectbusiness_SUCCESS);
		}
	}
	
	@RequestMapping(value="/city",method=RequestMethod.GET)
	public List<Dic> getCity(){
		List<Dic> list=dicService.getListByType(DicTypeConst.CITY);
		return list;
	}
	
	@RequestMapping(value="/category",method=RequestMethod.GET)
	public List<Dic> getCategory(){
		List<Dic> list=dicService.getListByType(DicTypeConst.CATEGORY);
		return list;
	}
	
}