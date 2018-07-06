package org.imooc.service;

import java.util.List;

import org.imooc.dto.AdDto;

public interface AdService {
	/**
	 * �������  �����ɹ�Ϊtrue ʧ��Ϊfalse
	 * @param adDto
	 * @return
	 */
	boolean add(AdDto adDto);

	List<AdDto> searchByPage(AdDto adDto);

	boolean modify(AdDto adDto);

	AdDto getById(Long id);

	boolean remove(long id);

	
	
	
}
