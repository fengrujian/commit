package org.imooc.controller.content;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.imooc.bean.Dic;
import org.imooc.constant.DicTypeConst;
import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.DicDto;
import org.imooc.dto.UserDto;
import org.imooc.service.AdService;
import org.imooc.service.DicService;
import org.imooc.service.impl.AdServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dic")
public class dicController { //手机号》ation》service>dao》mysql》
	@Autowired
	private AdService adService;
	
	@Autowired
	private DicService dicService;
	
	//定义全局变量
	private int currentPage_;//页码
	 
	private String name_;//名称 模糊查询
	
	
	
	/**
	 * 查询初始化
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String init(Model model) {  //修改 删除  初始化 都进行查询
		DicDto dicDto = new DicDto();
		model.addAttribute("list", dicService.searchByPage(dicDto));
		model.addAttribute("searchParam", dicDto);
		return "/content/dicList";
	}
	/**
	 * 修改或者删除完自动跳到这个接口在按条件查询
	 * @param model
	 * @return
	 */
//	@RequestMapping(value="/del_update", method = RequestMethod.GET)
//	public String del_update(Model model) {  //修改 删除  初始化 都进行查询
//		AdDto adDto = new AdDto();
//		if(currentPage_>=1 || (name_!=null || name_!="")){
//			adDto.getPage().setCurrentPage(currentPage_);
//			adDto.setname(name_);
//		}	
//		model.addAttribute("list", adService.searchByPage(adDto));
//		model.addAttribute("searchParam", adDto);
//		return "/content/dicList";
//	}
//	
	/**
	 *   返回新增 修改返回时查询
	 * @throws UnsupportedEncodingException 
	 */
//	@RequestMapping(value="/gobackser", method = RequestMethod.GET)
//	public String gobackser(Model model,@RequestParam("currentPage") int currentPage,HttpServletRequest request,@RequestParam("name") String name){
//		AdDto adDto = new AdDto();
//		adDto.getPage().setCurrentPage(currentPage);
//		adDto.setname(name);
//		model.addAttribute("list", adService.searchByPage(adDto));
//		model.addAttribute("searchParam", adDto);
//		return "/content/dicList";
//	}
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/search" ,method=RequestMethod.POST)
	public String search(Model model,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, HttpServletRequest request) throws UnsupportedEncodingException {
		DicDto dicDto = new DicDto();
		dicDto.getPage().setCurrentPage(currentPage);
		dicDto.setName(name);
		model.addAttribute("list", dicService.searchByPage(dicDto));
		model.addAttribute("searchParam", dicDto);
		return "/content/dicList";
	}
	/**
	 * 新增页初始化
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/addInit")
	public String addInit(Model model,HttpServletRequest request,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name) throws UnsupportedEncodingException {
		DicDto dicDto = new DicDto();
		dicDto.getPage().setCurrentPage(currentPage);
		dicDto.setName(name);
		model.addAttribute("searchParam", dicDto);
	    return "/content/dicAdd";
	}
	
	@RequestMapping("/add")
	public String add(DicDto dicDto,Model model,@RequestParam("currentPage") int currentPage,@RequestParam("name_like") String name_like) { 
		DicDto dicDto1 = new DicDto();
		dicDto1.getPage().setCurrentPage(currentPage);
		dicDto1.setName(name_like);
		model.addAttribute("searchParam", dicDto1);	
		if(dicDto!=null){
			 if(dicDto.getName()!="" && dicDto.getWeight()!=null && dicDto.getCode()!="" && dicDto.getType()!=""){
				   if(!dicDto.getName().equals(dicDto.getCode())){
					     model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.name_and_code);
						 return "/content/dicAdd";
				   }
				   List<Dic> list = dicService.getListByType(dicDto.getType()); 
				   if(dicDto.getType().equals("category")){
						if(list.size() >= DicTypeConst.CATEGORY_NUM){
							 model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.CATEGORY_NUM);
							 return "/content/dicAdd";
						}
					}else if(dicDto.getType().equals("city")){
                          if(list.size() >= DicTypeConst.CITY_NUM){
                        	  model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.CITY_NUM);
                        	  return "/content/dicAdd";
                          }
					}
				   		
				  if(dicService.add(dicDto)){
						model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
					}else{
						model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
					}
			 }else{
				 model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.data_null);
			 }
		}else{
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.data_null);
		}
	  return "/content/dicAdd";
	}
	/**
	 * 修改页面初始化
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(Model model, @RequestParam("id") Long id,
			@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, HttpServletRequest request) throws UnsupportedEncodingException {		     
		    DicDto dicDto = new DicDto();
		    dicDto.getPage().setCurrentPage(currentPage);//当前页码
		    dicDto.setName(name);
		    model.addAttribute("searchParam", dicDto);
		    model.addAttribute("modifyObj", dicService.getById(id));
		   return "/content/dicModify";
	}
	/**
	 * 修改
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/modify")
	public String modify(Model model,RedirectAttributes attr, DicDto dicDto,@RequestParam("currentPage") int currentPage,
			HttpServletRequest request,@RequestParam("name_like") String name_like) throws UnsupportedEncodingException {
		    model.addAttribute("modifyObj", dicDto);
		    currentPage_ = currentPage;//页码赋值
		    name_ = name_like; //模糊查询  
		    DicDto dicDto1 = new DicDto();
		    dicDto1.getPage().setCurrentPage(currentPage);
		    dicDto1.setName(name_like);
		    model.addAttribute("searchParam", dicDto1);
		    
		  if(!dicDto.getName().equals(dicDto.getCode())){
			     model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.name_and_code);
				 return "/content/dicModify";
		  }
		    
		 if (dicService.modify(dicDto)) {
			 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS.getMsg());
		} else {
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL.getMsg());
		}
		 return "redirect:/dic/del_update";
	}
	
	
	@RequestMapping(value="/del_update",method=RequestMethod.GET)
	public String del_update(ModelMap map,Model model,HttpServletRequest request,@RequestParam("pageCode") String pageCode) throws UnsupportedEncodingException {  //修改 删除  初始化 都进行查询
		PageCodeEnum.update_msg.setMsg(pageCode);
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.update_msg);
		String str = search(model, currentPage_, name_, request);
		return str;
	}
	/**
	 * 删除操作  根据id删除
	 * @param model
	 * @param adDto
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/remove")
	public String remove(Model model,RedirectAttributes attr ,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, DicDto dicDto,HttpServletRequest request) throws UnsupportedEncodingException {
		currentPage_ = currentPage;
		name_ = name;
		if(dicService.remove(dicDto.getId())){ //删除成功  就返回true
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS.getMsg());
		}else{//false
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL.getMsg());
		}
		return  "redirect:/dic/del_update";
	}
	
	
}