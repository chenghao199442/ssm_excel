package com.chenghao.poi.service.impl;

import com.chenghao.poi.domain.Emp;
import com.chenghao.poi.mapper.ExcelMapper;
import com.chenghao.poi.service.ExcelService;
import com.chenghao.poi.util.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ExcelMapper excelMapper;

    @Override
    public String inputExcel(InputStream is, String originalFilename) {
        Map<String, Object> ginsengMap = new HashMap<>();
        List<ArrayList<Object>> list;
        if (originalFilename.endsWith(".xls")) {
            list = Excel.readExcel2003(is);
        } else {
            list = Excel.readExcel2007(is);
        }
        int j = list.size();
        for (int i = 0; i < j - 1; i++) {
            List<Object> row = list.get(i);
            ginsengMap.put("name", row.get(0).toString());
            ginsengMap.put("sex", row.get(1).toString());
            ginsengMap.put("email", row.get(2).toString());
            ginsengMap.put("dept_id", row.get(3).toString());
            excelMapper.inputExcel(ginsengMap);
        }
        return "01";
    }

    @Override
    public List<Emp> outputExcel() {
        return excelMapper.getAll();
    }

    @Override
    public List<Emp> getAll() {
        return excelMapper.getAll();
    }

    @Override
    public List<Emp> getTenDataEmp() {
        return excelMapper.getTenDataEmp();
    }

    @Override
    public void updateNameByEmpsBatch(List<Emp> tenDataEmp, String name) {
        excelMapper.updateNameByEmpsBatch(tenDataEmp, name);
    }
}
