package com.chenghao.poi.mapper;

import com.chenghao.poi.domain.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExcelMapper {

    void inputExcel(Map<String, Object> ginsengMap);

    List<Emp> getAll();

    List<Emp> getTenDataEmp();

    void updateNameByEmpsBatch(@Param("emps") List<Emp> emps, @Param("name") String name);
}
