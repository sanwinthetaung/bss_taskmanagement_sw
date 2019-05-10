package com.bss.tm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import com.bss.tm.model.Task;

@Mapper
public interface TaskMapper {
	
	//@Select("SELECT * FROM TASK WHERE ISDELETED = 0")
	public List<Task> findAll();
	
	
//	@Select("SELECT * FROM TASK WHERE ISDELETED = 0 AND ID = #{ID}")
	public Task selectTaskById(Long id);
	
//	@Insert("INSERT INTO TASK (TITLE, STATUS, TASKlIST_ID, CREATED_DATE) VALUES (#{title}, #{status}, #{taskList_id}, now())")
//	@SelectKey("SELECT LAST_INSERT_ID() as id")
	public int insertTask(Task task);
    public int updateTask(Task task);
    public void deleteTaskLogical(Long id);
    public void deleteTaskPhysical(Long id);
}
