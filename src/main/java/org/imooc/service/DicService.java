package org.imooc.service;

import java.util.List;

import org.imooc.bean.Dic;
import org.imooc.dto.DicDto;

public interface DicService {
    /**
     * 根据类型获取字典表列�?
     * @param type 类型
     * @return 字典表列�?
     */
    public List<Dic> getListByType(String type);

	public List<DicDto>  searchByPage(DicDto dicDto);

	public boolean add(DicDto dicDto);

	public DicDto getById(Long id);

	public boolean modify(DicDto dicDto);

	public boolean remove(int id);


}