package com.chenghao.poi.service;

import com.chenghao.poi.domain.Emp;

import java.io.InputStream;
import java.util.List;

public interface ExcelService {

    String inputExcel(InputStream is, String originalFilename);

    List<Emp> outputExcel();

    List<Emp> getAll();

    List<Emp> getTenDataEmp();

    void updateNameByEmpsBatch(List<Emp> tenDataEmp, String name);
}
