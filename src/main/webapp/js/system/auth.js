$(function() {
	//初始化用户树
	initUserTree(); //把用户的数据显示到页面
	//初始化用户组树
	initGroupTree();//把用户组的数据显示到页面
	//初始化菜单树
	initMenuTree();// 把菜单的数据显示到页面
	$("#userForm").validate({
		rules : {
			"userName" : "required",
			"chName" : "required"
		}
	});
	$("#groupForm").validate({
		rules : {
			"groupName" : "required"
		}
	});
	$("#menuForm").validate({
		rules : {
			"menuName" : "required"
		}
	});
	$("#actionForm").validate({
		rules : {
			"actionName" : "required",
			"actionUrl" : "required"
		}
	});
});

/**
 * 鼠标在菜单间移动时样式的切换
 */
function move(element) {
	$(".rMenuLiMove").addClass("rMenuLi");
	$(".rMenuLiMove").removeClass("rMenuLiMove");
	
	$(element).addClass("rMenuLiMove");
	$(element).removeClass("rMenuLi");
}

/**
 * 鼠标在弹出层上方时，解除鼠标按下的事件
 */
function divOver() {
	$("body").unbind("mousedown", mousedown);
}

/**
 * 单击鼠标事件：
 * 在页面任意地方单击鼠标，关闭右键弹出的菜单
 */
function mousedown() {
	$("#rMenu").css({
		"visibility" : "hidden"
	});
	$("#groupMenu").css({
		"visibility" : "hidden"
	});
	$("#menuMenu").css({
		"visibility" : "hidden"
	});
}

/**
 * 鼠标划出右键菜单层时，去除“鼠标经过菜单时”的样式。
 */
function divOut() {
	$("body").bind("mousedown", mousedown);
	$(".rMenuLiMove").addClass("rMenuLi");
	$(".rMenuLiMove").removeClass("rMenuLiMove");
}

/**
 * 右击时定位右键菜单展示的位置并显示
 */
function rightClick(event,rMenuId) {
	$("#" + rMenuId).css({
		"top" : event.clientY + "px",
		"left" : event.clientX + "px",
		"visibility" : "visible"
	});
}


/**
 * 清空用户维护界面里的内容
 */
function clearUser() {
	$("#userId").val("");
	$("#userName").val("");
	$("#chName").val("");
}

/**
 * 清空用户组维护界面里的内容
 */
function clearGroup() {
	$("#groupId").val("");
	$("#groupName").val("");
}
/**
 * 清空菜单维护界面里的内容
 */
function clearMenu() {
	$("#menuId").val("");
	$("#menuName").val("");
	$("#url").val("");
}
/**
 * 清空动作维护界面里的内容
 */
function clearAction() {
	$("#actionId").val("");
	$("#actionName").val("");
	$("#actionUrl").val("");
	$("#httpMethod").val("");
}
/**
 * 初始化用户树
 */
function initUserTree() {
	common.ajax({//默认get请求
		url : $("#basePath").val() + "/users",
		success : function(data) {
			var setting = {
				view : {
					dblClickExpand : true,//定义双击展开
					showLine : true,
					selectedMulti : false
				},
				data : {
					simpleData : {
						enable : true//enable设置为true就是为了变成树的结构  而不是平行的关系
					},
					key : {
						name : "chName"//name : "chName" 是为了让User里面的chName显示到树结构的页面
					}
				},
				callback : {//回调函数
					onClick : function(event, treeId, treeNode) {
					  selectUser();//单击用户节点的时候，根据用户的外键group_id 就是用户组的主键
					},
					onRightClick : userRightClick//右击用户节点
				}
			};
			data.push({id:0,chName:"用户",open:true});//加个根节点id值为0
			$.fn.zTree.init($("#user"), setting, data);//初始化  就把数据显示到了页面
		}
	});
 }
/**
 * 在用户树上右击显示右键菜单同时选中节点
 */
function userRightClick(event, treeId, treeNode) {
	if(!treeNode) {
		return;
	}
	$.fn.zTree.getZTreeObj(treeId).selectNode(treeNode);//选择这个节点
	rightClick(event,"rMenu");//右击
	if(treeNode.id === 0) {//treeNode.id==0  就是根节点
		$(".disabled").hide();
	} else {//否则是子节点
		$(".disabled").show();
		selectUser();//在选中用户对应用户组
	} 
 }
/**
 * 选中用户后显示用户对应的用户组
 */
function selectUser() {
	//获得user下的所有的节点
	var nodes = $.fn.zTree.getZTreeObj("user").getSelectedNodes();
	//获得group整个树
	var groupTree = $.fn.zTree.getZTreeObj("group");
	//先清空原本选中的选项
	var checkedNodes = groupTree.getCheckedNodes(true);//获得被选中的用户组节点
	if (checkedNodes.length > 0) {//如果用户组的节点有被选中的就清空
		groupTree.checkNode(checkedNodes[0], false);
	}
	common.ajax({
		url : $("#basePath").val() + "/users/" + nodes[0].id,
		success : function(data) {
			//如果当前选中的用户有用户组，则选中这个用户组
			if(data.groupId) {
				groupTree.checkNode(groupTree.getNodeByParam("id", data.groupId),true);
			}
		}
	});
}
/**
 * 初始化新增用户界面
 */
function initAddUser() {
	mousedown();
	clearUser();
	$("#userTitle").html("&nbsp;&nbsp;新增用户");
	$("#cover").show();
	$("#userMaintain").show();
}

/**
 * 初始化修改用户界面，根据用户的id查询的记录放到修改页面上了
 */
function initModifyUser() {
	//把右边菜单隐藏起来了
	mousedown();
	//获得用户下的所有节点
	var nodes = $.fn.zTree.getZTreeObj("user").getSelectedNodes();
	common.ajax({
		url : $("#basePath").val() + "/users/" + nodes[0].id,
		success : function(data) {
			//清空用户维护界面里的内容
			clearUser();
			//把修改后的内容放上页面
			$("#userId").val(data.id);
			$("#userName").val(data.name);
			$("#chName").val(data.chName);
			$("#userTitle").html("&nbsp;&nbsp;修改用户");
			$("#cover").show();
			$("#userMaintain").show();
		}
	});
}

/**
 * 保存用户，如果主键存在则修改，不存在则新增
 */
function saveUser() {
	if($("#userForm").valid()) {
		if ($("#userId").val()) {//用户id存在就是修改
			common.ajax({
				url : $("#basePath").val() + "/users/" + $("#userId").val(),
				type : "POST",
				success : function(data) {
					if (data.code === common.pageCode.MODIFY_SUCCESS) {
						initUserTree();//初始化树
						closeUser();//关闭用户的维护页面
					}
					common.showMessage(data.msg);
				},
				data : {
					"name" : $("#userName").val(),
					"chName" : $("#chName").val(),
					"_method" : "PUT" //put提交 ，在页面相当于是<input name="_method"/>
				}
			});
		} else {//用户id不存在就是新增
			common.ajax({
				url : $("#basePath").val() + "/users",
				type : "POST",
				success : function(data) {
					if (data.code === common.pageCode.ADD_SUCCESS) {
						initUserTree();//初始化树
						closeUser();//关闭用户的维护页面
					}
					common.showMessage(data.msg);
				},
				data : {
					"name" : $("#userName").val(),
					"chName" : $("#chName").val(),
					"password" : $("#userName").val()
				}
			});
		}
	}
}



/**
 * 删除用户
 */
function removeUser() {
	mousedown();
	var nodes = $.fn.zTree.getZTreeObj("user").getSelectedNodes();
	if (confirm("确定要删除用户【" + nodes[0].chName + "】吗？")) {
		common.ajax({
			url : $("#basePath").val() + "/users/"+ nodes[0].id,
			type : "POST",
			success : function(data) {
				if (data.code === common.pageCode.REMOVE_SUCCESS) {
					initUserTree();
				}
				common.showMessage(data.msg);
			},
			data : {
				"_method" : "DELETE"
			}
		});
	}
}

/**
 * 重置密码  就是把原来的密码改成和name的名字相同
 */
function resetPassword() {
	mousedown();
	var nodes = $.fn.zTree.getZTreeObj("user").getSelectedNodes();
	common.ajax({
		url : $("#basePath").val() + "/users/" + nodes[0].id,
		type : "POST",
		success : function(data) {
			if(data.code === common.pageCode.MODIFY_SUCCESS) {
				common.showMessage("重置密码成功！");
			} else {
				common.showMessage(data.msg);
			}
		},
		data : {
			"_method" : "PUT",
			"password" : nodes[0].name
		}
	});
}

/**
 * 为用户分配用户组
 */
function assignGroup() {
	//获取用户选中的节点
	var userNodes = $.fn.zTree.getZTreeObj("user").getSelectedNodes();
	//获取用户组选中的节点
	var groupNodes = $.fn.zTree.getZTreeObj("group").getCheckedNodes();
	if (userNodes.length <= 0) {
		common.showMessage("未选中用户！");
		return;
	}
	if (userNodes[0].id == '0') {
		common.showMessage("不能为根节点分配用户组！");
		return;
	}
	if (groupNodes.length <= 0) {
		common.showMessage("未选择用户组！");
		return;
	}
	common.ajax({
		url : $("#basePath").val() + "/users/" + userNodes[0].id,
		type : "POST",
		success : function(data) {
			if(data.code === common.pageCode.MODIFY_SUCCESS) {
				common.showMessage("分配用户组成功！");
			} else {
				common.showMessage(data.msg);
			}
		},
		data : {
			"groupId" : groupNodes[0].id,
			"_method" : "PUT"
		}
	});
}

/**
 * 初始化用户组树
 */
function initGroupTree() {
	common.ajax({
		url : $("#basePath").val() + "/groups",
		success : function(data) {
			var setting = {
				check : {
					enable : true,
					chkStyle : "radio"
				},
				view : {
					dblClickExpand : true,// 定义双击展开
					showLine : true,
					selectedMulti : false
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback : {
					onClick : function(event, treeId, treeNode) {
						selectGroup();
					},
					onRightClick : groupRightClick
				}
			};
			data.push({id:0,name:"用户组",open:true,nocheck:true});
			$.fn.zTree.init($("#group"), setting, data);
		}
	});
}

/**
 * 在用户组树上右击显示右键菜单同时选中节点
 */
function groupRightClick(event, treeId, treeNode) {
	if(!treeNode) {
		return;
	}
	if(treeNode.id === 0) {
		$(".disabled").hide();
	} else {
		$(".disabled").show();
		selectGroup();
	}
	rightClick(event,"groupMenu");
	$.fn.zTree.getZTreeObj(treeId).selectNode(treeNode);
}

/**
 * 初始化新增用户组界面
 */
function initAddGroup() {
	mousedown();
	clearGroup();
	$("#groupTitle").html("&nbsp;&nbsp;新增用户组");
	$("#cover").show();
	$("#groupMaintain").show();
}

/**
 * 初始化修改用户组界面
 */
function initModifyGroup() {
	mousedown();
	var nodes = $.fn.zTree.getZTreeObj("group").getSelectedNodes();
	common.ajax({
		url : $("#basePath").val() + "/groups/" + nodes[0].id,
		success : function(data) {
			clearGroup();
			$("#groupId").val(data.id);
			$("#groupName").val(data.name);
			$("#groupTitle").html("&nbsp;&nbsp;修改用户组");
			$("#cover").show();
			$("#groupMaintain").show();
		}
	});
}

/**
 * 保存用户组，如果主键存在则修改，不存在则新增
 */
function saveGroup() {
	if($("#groupForm").valid()) {
		if ($("#groupId").val()) {
			common.ajax({
				url : $("#basePath").val() + "/groups/" + $("#groupId").val(),
				type : "POST",
				success : function(data) {
					if (data.code === common.pageCode.MODIFY_SUCCESS) {
						initGroupTree();
						closeGroup();
					}
					common.showMessage(data.msg);
				},
				data : {
					"name" : $("#groupName").val(),
					"_method" : "PUT"
				}
			});
		} else {
			common.ajax({
				url : $("#basePath").val() + "/groups",
				type : "POST",
				success : function(data) {
					if (data.code === common.pageCode.ADD_SUCCESS) {
						initGroupTree();
						closeGroup();
					}
					common.showMessage(data.msg);
				},
				data : {
					"name" : $("#groupName").val()
				}
			});
		}
	}
}
/**
 * 删除用户组
 */
function removeGroup() {
	mousedown();
	var nodes = $.fn.zTree.getZTreeObj("group").getSelectedNodes();
	if (confirm("确定要删除用户组【" + nodes[0].name + "】吗？")) {
		common.ajax({
			url : $("#basePath").val() + "/groups/" + nodes[0].id,
			type : "POST",
			success : function(data) {
				if (data.code === common.pageCode.REMOVE_SUCCESS) {
					initGroupTree();
				}
				common.showMessage(data.msg);
			},
			data : {
				"_method" : "DELETE"
			}
		});
	}
}
/**
 * 
 * 初始化菜单树
 * 
 */
function initMenuTree() {
	//初始化菜单树
	common.ajax({
		url : $("#basePath").val() + "/menus",
		success : function(data) {
			var setting = {
				edit : {//编辑
					enable : true,//可以拖动
					showRemoveBtn : false,
					showRenameBtn : false,
					drag : {
						isCopy : false//设置不复制功能
					}
				},
				check : {
					enable : true//使用默认单选框
				},
				view : {
					dblClickExpand : true,// 定义双击展开
					showLine : true,
					selectedMulti : false
				},
				data : {
					simpleData : {
						enable : true,//为true,就是可以变为树的结构 ,而不是平行的结构
						pIdKey : "comboParentId",//comboParentId挂在comboId下面
						idKey : "comboId"//节点的主健
					}
				},
				callback : {
					beforeDrop : beforeDrop,
					beforeDrag : beforeDrag,
					onRightClick : menuRightClick
				}
			};
			data.push({comboId:common.menuPrefix.PREFIX_MENU + "0",name:"菜单",open:true,nocheck:true});
			$.fn.zTree.init($("#menu"), setting, data);
		}
	});
}
/**
 * 在菜单树上右击显示右键菜单同时选中节点
 */
function menuRightClick(event, treeId, treeNode) {
	if(!treeNode) {
		return;
	}
	rightClick(event,"menuMenu");
	$.fn.zTree.getZTreeObj(treeId).selectNode(treeNode);

	$(".rMenuLi").show();
	// 如果是动作节点，不显示【新增菜单】、【新增动作】
	if(treeNode.comboId.indexOf(common.menuPrefix.PREFIX_ACTION) == 0) {
		$(".menuClass").hide();
	}
	// 如果是根节点，不显示【修改】、【删除】
	if(treeNode.comboId === common.menuPrefix.PREFIX_MENU + "0") {
		$(".disabled").hide();
	}
	
}

/**
 * 初始化新增菜单界面
 */
function initAddMenu() {
	mousedown();
	clearMenu();
	$("#menuTitle").html("&nbsp;&nbsp;新增菜单");
	$("#cover").show();
	$("#menuMaintain").show();
}

/**
 * 初始化新增动作界面
 */
function initAddAction() {
	mousedown();
	clearAction();
	$("#actionTitle").html("&nbsp;&nbsp;新增动作");
	$("#cover").show();
	$("#actionMaintain").show();
}

/**
 * 菜单树上的修改按钮，点击时判断当前修改的是菜单还是动作
 */
function modifyOfMenu() {
	var nodes = $.fn.zTree.getZTreeObj("menu").getSelectedNodes();
	// 如果选中的是动作节点
	if(nodes[0].comboId.indexOf(common.menuPrefix.PREFIX_ACTION) == 0) {
		initModifyAction();
	} else if(nodes[0].comboId.indexOf(common.menuPrefix.PREFIX_MENU) == 0) {
		// 如果选中的是菜单节点
		initModifyMenu();
	} else {
		common.showMessage("选中了错误的节点！");
	}
}

/**
 * 初始化修改菜单界面
 */
function initModifyMenu() {
	mousedown();
	var nodes = $.fn.zTree.getZTreeObj("menu").getSelectedNodes();
	common.ajax({
		url : $("#basePath").val() + "/menus/" + nodes[0].id,
		success : function(data) {
			clearMenu();
			$("#menuId").val(data.id);
			$("#menuName").val(data.name);
			$("#url").val(data.url);
			$("#menuTitle").html("&nbsp;&nbsp;修改菜单");
			$("#cover").show();
			$("#menuMaintain").show();
		}
	});
}

/**
 * 初始化修改动作界面
 */
function initModifyAction() {
	mousedown();
	var nodes = $.fn.zTree.getZTreeObj("menu").getSelectedNodes();
	common.ajax({
		url : $("#basePath").val() + "/actions/" + nodes[0].id,
		success : function(data) {
			clearMenu();
			$("#actionId").val(data.id);
			$("#actionName").val(data.name);
			$("#actionUrl").val(data.url);
			$("#httpMethod").val(data.method);
			$("#actionTitle").html("&nbsp;&nbsp;修改动作");
			$("#cover").show();
			$("#actionMaintain").show();
		}
	});
}

/**
 * 关闭用户维护界面
 */
function closeUser() {
	$("#cover").hide();
	$("#userMaintain").hide();
}

/**
 * 关闭用户组维护界面
 */
function closeGroup() {
	$("#cover").hide();
	$("#groupMaintain").hide();
}

/**
 * 关闭菜单维护界面
 */
function closeMenu() {
	$("#cover").hide();
	$("#menuMaintain").hide();
}

/**
 * 关闭动作维护界面
 */
function closeAction() {
	$("#cover").hide();
	$("#actionMaintain").hide();
}

/**
 * 保存菜单，如果主键存在则修改，不存在则新增
 */
function saveMenu() {
	if($("#menuForm").valid()) {
		if ($("#menuId").val()) {
			common.ajax({
				url : $("#basePath").val() + "/menus/" + $("#menuId").val(),
				type : "POST",
				success : function(data) {
					if (data.code === common.pageCode.MODIFY_SUCCESS) {
						initMenuTree();
						closeMenu();
					}
					common.showMessage(data.msg);
				},
				data : {
					"name" : $("#menuName").val(),
					"url" : $("#url").val(),
					"_method" : "PUT"
				}
			});
		} else {
			//右击当前节点
			var nodes = $.fn.zTree.getZTreeObj("menu").getSelectedNodes();
			var parentId = nodes[0].id;//当前节点
			common.ajax({
				url : $("#basePath").val() + "/menus",
				type : "POST",
				success : function(data) {
					if (data.code === common.pageCode.ADD_SUCCESS) {
						initMenuTree();
						closeMenu();
					}
					common.showMessage(data.msg);
				},
				data : {
					"name" : $("#menuName").val(),
					"url" : $("#url").val(),
					"parentId" : parentId
				}
			});
		}
	}
}


/**
 * 保存动作，如果动作存在则修改，不存在则新增
 */
function saveAction() {
	if($("#actionForm").valid()) {
		if ($("#actionId").val()) {
			common.ajax({
				url : $("#basePath").val() + "/actions/" + $("#actionId").val(),
				type : "POST",
				success : function(data) {
					if (data.code === common.pageCode.MODIFY_SUCCESS) {
						initMenuTree();
						closeAction();
					}
					common.showMessage(data.msg);
				},
				data : {
					"name" : $("#actionName").val(),
					"url" : $("#actionUrl").val(),
					"method" : $("#httpMethod").val(),
					"_method" : "PUT"
				}
			});
		} else {
			var nodes = $.fn.zTree.getZTreeObj("menu").getSelectedNodes();
			var parentId = nodes[0].id;
			common.ajax({
				url : $("#basePath").val() + "/actions",
				type : "POST",
				success : function(data) {
					if (data.code === common.pageCode.ADD_SUCCESS) {
						initMenuTree();
						closeAction();
					}
					common.showMessage(data.msg);
				},
				data : {
					"name" : $("#actionName").val(),
					"url" : $("#actionUrl").val(),
					"method" : $("#httpMethod").val(),
					"menuId" : parentId
				}
			});
		}
	}
}

/**
 * 菜单树上的删除按钮，点击时判断当前删除的是菜单还是动作
 */
function removeOfMenu() {
	var nodes = $.fn.zTree.getZTreeObj("menu").getSelectedNodes();
	// 如果选中的是动作节点
	if(nodes[0].comboId.indexOf(common.menuPrefix.PREFIX_ACTION) == 0) {
		removeAction();
	} else if(nodes[0].comboId.indexOf(common.menuPrefix.PREFIX_MENU) == 0) {
		// 如果选中的是菜单节点
		removeMenu();
	} else {
		common.showMessage("选中了错误的节点！");
	}
}

/**
 * 删除菜单
 */
function removeMenu() {
	mousedown();
	var nodes = $.fn.zTree.getZTreeObj("menu").getSelectedNodes();
	if (confirm("将会连同菜单下的动作一起删除，确定要删除菜单【" + nodes[0].name + "】吗？")) {
		common.ajax({
			url : $("#basePath").val() + "/menus/" + nodes[0].id,
			type : "POST",
			success : function(data) {
				if (data.code === common.pageCode.REMOVE_SUCCESS) {
					initMenuTree();
				}
				common.showMessage(data.msg);
			},
			data : {
				"_method" : "DELETE"
			}
		});
	}
}

/**
 * 删除动作
 */
function removeAction() {
	mousedown();
	var nodes = $.fn.zTree.getZTreeObj("menu").getSelectedNodes();
	if (confirm("确定要删除动作【" + nodes[0].name + "】吗？")) {
		common.ajax({
			url : $("#basePath").val() + "/actions/" + nodes[0].id,
			type : "POST",
			success : function(data) {
				if (data.code === common.pageCode.REMOVE_SUCCESS) {
					initMenuTree();
				}
				common.showMessage(data.msg);
			},
			data : {
				"_method" : "DELETE"
			}
		});
	}
}

/**
 * 节点被拖拽之前的事件回调函数，
 * 返回false可以阻止拖拽
 */
function beforeDrag(treeId, treeNodes) {
	//动作节点不参与排序
	if(treeNodes[0].comboId.indexOf(common.menuPrefix.PREFIX_ACTION) == 0) {
		return false;
	}
}

/**
 * 节点拖拽操作结束之前事件：
 * 将拖拽后的顺序提交，修改数据库中的顺序
 */
function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
	common.ajax({
		url : $("#basePath").val() + "/menus/" + treeNodes[0].id + "/" + targetNode.id + "/" + moveType,
		type : "POST",
		success : function(data) {
			initMenuTree();
			common.showMessage(data.msg);
		},
		data : {
			"_method" : "PUT"
		}
	});
	return true;
}
/**
 * 选中用户组后显示用户组对应的菜单
 */
function selectGroup() {
	var nodes = $.fn.zTree.getZTreeObj("group").getSelectedNodes();
	if (nodes.length > 0 && nodes[0].id != 0) {
		common.ajax({
			url : $("#basePath").val() + "/groups/" + nodes[0].id + "/menus",
			success : function(data) {// data包括 menuDtoList和actionDtoList集合
				var menuTree = $.fn.zTree.getZTreeObj("menu");
				menuTree.checkAllNodes(false);//清空所有的菜单节点和动作节点
				//将菜单树上,用户组对应的,菜单节点勾选上
				for (var i = 0; i < data.menuDtoList.length; i++) {
					//因为菜单树是一颗混合树,需要用组合ID(带前缀的ID)来选中对应的节点
					menuTree.checkNode(menuTree.getNodeByParam("comboId", common.menuPrefix.PREFIX_MENU + data.menuDtoList[i].id), true);
//					groupTree.checkNode(groupTree.getNodeByParam("id", data.groupId),true);
				}
				//将菜单树上,用户组对应的 ,动作节点勾选上
				for (var i = 0; i < data.actionDtoList.length; i++) {
					// 因为菜单树是一颗混合树,需要用组合ID(带前缀的ID)来选中对应的节点
					menuTree.checkNode(menuTree.getNodeByParam("comboId", common.menuPrefix.PREFIX_ACTION + data.actionDtoList[i].id), true);
				}
			}
		});
	}
}

/**
 * 为用户组分配菜单
 */
function assignMenu() {
	var groupNodes = $.fn.zTree.getZTreeObj("group").getSelectedNodes();
	var menuNodes = $.fn.zTree.getZTreeObj("menu").getCheckedNodes();
	var name = $("#name").val();
	if (groupNodes.length <= 0) {
		common.showMessage("未选中用户组！");
		return;
	}
	if (groupNodes[0].id == '0') {
		common.showMessage("不能为根节点分配菜单！");
		return;
	}
	var param = {};
	for (var i = 0; i < menuNodes.length; i++) {
		//将勾选的菜单节点分成两组：因为在同一颗树上有两种节点：菜单节点，动作菜点
		if(menuNodes[i].comboId.indexOf(common.menuPrefix.PREFIX_MENU) == 0) {
			// 注意:这里如果用i来做下标，将会出现跳号，actionIdList也一样会跳号(因为在单次循环中，i不是单属于某一种节点)：
			// 要么修改这里，让下标是从0开始顺序递增：用两个变量来分别记录menuIdList、actionIdList的下标（可以自己试改一下）
			// 要么修改后台，我这里是修改后台
			param["menuIdList[" + i + "]"] = menuNodes[i].id;//一级菜单和二级菜单的id
		} else if(menuNodes[i].comboId.indexOf(common.menuPrefix.PREFIX_ACTION) == 0) {
			param["actionIdList[" + i + "]"] = menuNodes[i].id;//动作的id
		} else {
			common.showMessage("选中了错误的节点！");
		}
		//param = {"menuIdList":[{0},{1}],"actionIdList":[{2},{3}]}
	}
	common.ajax({
		url : $("#basePath").val() + "/groups/" + groupNodes[0].id + "/menus"+"?name="+name,
		type: "POST",
		success : function(data) {
			common.showMessage(data.msg);
		},
		data : param
	});
	
}