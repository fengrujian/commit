package org.imooc.controller.system;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.MemberDto;
import org.imooc.dto.OrdersDto;
import org.imooc.dto.UserDto;
import org.imooc.service.AdService;
import org.imooc.service.MemberService;
import org.imooc.service.OrdersService;
import org.imooc.service.impl.AdServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
public class memberController { 
	@Autowired
	private AdService adService;
	//定义全局变量
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrdersService ordersService;
	
	private int currentPage_;//页码
	 
	private String name_;//名称 模糊查询
	/**
	 * 查询初始化
	 */

	@RequestMapping(method = RequestMethod.GET)
	public String init(Model model) {  //修改 删除  初始化 都进行查询
		MemberDto memberDto = new MemberDto();
		model.addAttribute("list", memberService.searchByPage(memberDto));
		model.addAttribute("searchParam", memberDto);
		return "/content/memberList";
	}
	
	/**
	 * 修改或者删除完自动跳到这个接口在按条件查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/del_update", method = RequestMethod.GET)
	public String del_update(Model model,@RequestParam("pageCode") String pageCode) {  //修改 删除  初始化 都进行查询
		PageCodeEnum.update_msg.setMsg(pageCode);
		MemberDto memberDto = new MemberDto();
		if(currentPage_>=1 || (name_!=null || name_!="")){
			memberDto.getPage().setCurrentPage(currentPage_);
			memberDto.setName(name_);
		}	
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.update_msg);
		model.addAttribute("list", memberService.searchByPage(memberDto));
		model.addAttribute("searchParam", memberDto);
		return "/content/memberList";
	}
//	
//	/**
//	 *   返回新增 修改返回时查询
//	 * @throws UnsupportedEncodingException 
//	 */
//	@RequestMapping(value="/gobackser", method = RequestMethod.GET)
//	public String gobackser(Model model,@RequestParam("currentPage") int currentPage,HttpServletRequest request,@RequestParam("name") String name){
//		AdDto adDto = new AdDto();
//		adDto.getPage().setCurrentPage(currentPage);
//		adDto.setname(name);
//		model.addAttribute("list", adService.searchByPage(adDto));
//		model.addAttribute("searchParam", adDto);
//		return "/content/memberList";
//	}
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/search" ,method=RequestMethod.POST)
	public String search(Model model,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, HttpServletRequest request) throws UnsupportedEncodingException {
		MemberDto memberDto = new MemberDto();
		memberDto.getPage().setCurrentPage(currentPage);
		memberDto.setName(name);
		model.addAttribute("list", memberService.searchByPage(memberDto));
		model.addAttribute("searchParam", memberDto);
		return "/content/memberList";
	}
//	/**
//	 * 新增页初始化
//	 * @throws UnsupportedEncodingException 
//	 */
//	@RequestMapping("/memberdInit")
//	public String addInit(Model model,HttpServletRequest request,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name) throws UnsupportedEncodingException {
//		AdDto adDto = new AdDto();
//		adDto.getPage().setCurrentPage(Integer.valueOf(currentPage));
//		adDto.setname(name);
//		model.addAttribute("searchParam", adDto);
//	    return "/content/memberAdd";
//	}
//	//��ӹ������
//	@RequestMapping("/memberd")
//	public String add(@ModelAttribute AdDto adDto,Model model,@RequestParam("currentPage") int currentPage,@RequestParam("name_like") String name_like) { 
//		if(adService.add(adDto)){
//			adDto.getPage().setCurrentPage(currentPage);
//			adDto.setname(name_like);
//			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
//		}else{
//			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
//		}
//		return "/content/memberAdd";
//	}
//	/**
//	 * 修改页面初始化
//	 * @throws UnsupportedEncodingException 
//	 */
//	@RequestMapping("/modifyInit")
//	public String modifyInit(Model model, @RequestParam("id") Long id,
//			@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, HttpServletRequest request) throws UnsupportedEncodingException {		     
//		    MemberDto memberDto = new MemberDto();
//		    memberDto.getPage().setCurrentPage(currentPage);//当前页码
//		    memberDto.setName(name);
//		    model.addAttribute("searchParam", memberDto);
//		    model.addAttribute("modifyObj", memberService.getById(id));
//		    return "/content/memberModify";
//	}
	
//	/**
//	 * 修改
//	 * @throws UnsupportedEncodingException 
//	 */
//	@RequestMapping("/modify")
//	public String modify(Model model, AdDto adDto,@RequestParam("currentPage") int currentPage,
//			HttpServletRequest request,@RequestParam("name_like") String name_like) throws UnsupportedEncodingException {
//		    model.addAttribute("modifyObj", adDto);
//		    currentPage_ = currentPage;//页码赋值
//		    name_ = name_like; //模糊查询 标题
//		 if (adService.modify(adDto)) {
//			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS);
//			return "redirect:/member/del_update";
//		} else {
//			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL);
//		}
//		return "/content/memberModify";
//	}
	/**
	 * 删除操作  根据id删除
	 * @param model
	 * @param adDto
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/remove")
	public String remove(Model model,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, RedirectAttributes attr, MemberDto memberDto,HttpServletRequest request) throws UnsupportedEncodingException {
		 currentPage_ = currentPage;
		 name_ = name;
		 List<OrdersDto> OrdersDtolist = ordersService.getListByMemberId(memberDto.getId());
		 if(OrdersDtolist.size()>0){
			 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_not_member.getMsg());
			 return  "redirect:/member/del_update";
		 }
		 if(memberService.remove(memberDto.getId())){ //删除成功  就返回true
			 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS.getMsg());
		}else{//false
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL.getMsg());
		}
		return  "redirect:/member/del_update";
	}
	
	
}