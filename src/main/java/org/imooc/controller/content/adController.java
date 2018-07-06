package org.imooc.controller.content;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.UserDto;
import org.imooc.service.AdService;
import org.imooc.service.impl.AdServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ad")
public class adController { //手机号》ation》service>dao》mysql》
	@Autowired
	private AdService adService;
	//定义全局变量
	private int currentPage_;//页码
	 
	private String title_;//标题  模糊查询
	/**
	 * 查询初始化
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String init(Model model) {  //修改 删除  初始化 都进行查询
		AdDto adDto = new AdDto();
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}
	/**
	 * 修改或者删除完自动跳到这个接口在按条件查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/del_update", method = RequestMethod.GET)
	public String del_update(Model model) {  //修改 删除  初始化 都进行查询
		AdDto adDto = new AdDto();
		if(currentPage_>=1 || (title_!=null || title_!="")){
			adDto.getPage().setCurrentPage(currentPage_);
			adDto.setTitle(title_);
		}	
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}
	
	/**
	 *   返回新增 修改返回时查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/gobackser", method = RequestMethod.GET)
	public String gobackser(Model model,@RequestParam("currentPage") int currentPage,HttpServletRequest request,@RequestParam("title") String title){
		AdDto adDto = new AdDto();
		adDto.getPage().setCurrentPage(currentPage);
		adDto.setTitle(title);
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/search" ,method=RequestMethod.POST)
	public String search(Model model,@RequestParam("currentPage") int currentPage,@RequestParam("title") String title, HttpServletRequest request) throws UnsupportedEncodingException {
		AdDto adDto = new AdDto();
		adDto.getPage().setCurrentPage(currentPage);
		adDto.setTitle(title);
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
//		model.addAttribute("title", title);//数据回显
		return "/content/adList";
	}
	/**
	 * 新增页初始化
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/addInit")
	public String addInit(Model model,HttpServletRequest request,@RequestParam("currentPage") int currentPage,@RequestParam("title") String title) throws UnsupportedEncodingException {
		AdDto adDto = new AdDto();
		adDto.getPage().setCurrentPage(Integer.valueOf(currentPage));
		adDto.setTitle(title);
		model.addAttribute("searchParam", adDto);
	    return "/content/adAdd";
	}
	//��ӹ������
	@RequestMapping("/add")
	public String add(@ModelAttribute AdDto adDto,Model model,@RequestParam("currentPage") int currentPage,@RequestParam("title_like") String title_like) { 
		if(adService.add(adDto)){
			adDto.getPage().setCurrentPage(currentPage);
			adDto.setTitle(title_like);
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
		}else{
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
		}
		return "/content/adAdd";
	}
	/**
	 * 修改页面初始化
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(Model model, @RequestParam("id") Long id,
			@RequestParam("currentPage") int currentPage,@RequestParam("title") String title, HttpServletRequest request) throws UnsupportedEncodingException {		     
		    AdDto adDto = new AdDto();
			adDto.getPage().setCurrentPage(currentPage);//当前页码
			adDto.setTitle(title);
		    model.addAttribute("searchParam", adDto);
		    model.addAttribute("modifyObj", adService.getById(id));
		return "/content/adModify";
	}
	/**
	 * 修改
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/modify")
	public String modify(Model model, AdDto adDto,@RequestParam("currentPage") int currentPage,
			HttpServletRequest request,@RequestParam("title_like") String title_like) throws UnsupportedEncodingException {
		    model.addAttribute("modifyObj", adDto);
		    currentPage_ = currentPage;//页码赋值
		    title_ = title_like; //模糊查询 标题
		 if (adService.modify(adDto)) {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS);
			return "redirect:/ad/del_update";
		} else {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL);
		}
		return "/content/adModify";
	}
	/**
	 * 删除操作  根据id删除
	 * @param model
	 * @param adDto
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/remove")
	public String remove(Model model, @RequestParam("currentPage") int currentPage,@RequestParam("title") String title, AdDto adDto,HttpServletRequest request) throws UnsupportedEncodingException {
//		 String current_Page = request.getParameter("currentPage");
//		 String title =new String(request.getParameter("title").getBytes("ISO8859-1"),"UTF-8");//传入标题
		if(adService.remove(adDto.getId())){ //删除成功  就返回true
			currentPage_ = currentPage;
			title_ = title;
			model.addAttribute("searchParam", adDto);
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS);
		}else{//false
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL);
		}
		return  "redirect:/ad/del_update";
	}
	
	
}