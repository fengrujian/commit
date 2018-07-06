package org.imooc.controller.system;

import org.imooc.constant.DicTypeConst;
import org.imooc.dto.UserDto;
import org.imooc.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private DicService dicService;
    /**
     * http动作
     * @param model
     * @return
     */
	@RequestMapping
	public String index(Model model,@RequestParam("name") String name) {
		UserDto userDto = new UserDto();
		userDto.setName(name);
		model.addAttribute("user", userDto);
		model.addAttribute("httpMethodList", dicService.getListByType(DicTypeConst.HTTP_METHOD));
		return "/system/auth";
	}
		
	
}