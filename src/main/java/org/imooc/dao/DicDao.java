package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Dic;

public interface DicDao {
    List<Dic> select(Dic dic);

	List<Dic> searchByPage(Dic condition);

	int insertdic(Dic dic);

	Dic selectById(Long id);

	int update(Dic dic);

	int remove(int id);
	
	
	
}