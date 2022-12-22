package com.sparta.week5project.DAO.impl;

import com.sparta.week5project.DTO.DepartmentDTO;
import com.sparta.week5project.entities.Department;
import com.sparta.week5project.mappers.impl.DepartmentMapperImpl;
import com.sparta.week5project.mappers.impl.DeptManagerMapperImpl;
import com.sparta.week5project.repositories.DepartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.Optional;

@SpringBootTest
public class DepartmentDAOTest {
    @Autowired
    private DepartmentDAO departmentDAO;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapperImpl departmentMapper;


    @Test
    void testFindByDept_No() {
        DepartmentDTO result = departmentDAO.findByDept_No("d009").get();
        System.out.println(result);
        Assertions.assertEquals(result.getDeptName(), "Customer Service");
    }

    @Test
    @Commit
    void testSaveMethod_ShouldSave() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDeptName("MyDepartment");
        departmentDTO.setId("d013");
        departmentDAO.save(departmentDTO);
        Optional<DepartmentDTO> savedDepartment = departmentDAO.findByDept_No("d013");
        DepartmentDTO result = savedDepartment.get();

        Assertions.assertEquals(result.getDeptName(), "MyDepartment");
    }

    @Test
    @Commit
    void testDeleteByIdMethod_ShouldDelete() {
        if (departmentRepository.findByIdNumber("d013").isEmpty()) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setDeptName("MyDepartment");
            departmentDTO.setId("d013");
            departmentDAO.save(departmentDTO);
        }
        Optional<DepartmentDTO> result = departmentDAO.findByDept_No("d013");
        if (result.isPresent()) {
            departmentDAO.deleteById("d013");
        }
        Optional<Department> deleted = departmentRepository.findById("d013");

        Assertions.assertTrue(deleted.isEmpty());
    }

    @Test
    void testUpdate() {
        Optional<Department> departmentOptional = departmentRepository.findByIdNumber("d014");
        if (departmentOptional.isPresent()) {
            DepartmentDTO departmentNew = new DepartmentDTO();
            departmentNew.setDeptName("NewDepartment");
            departmentDAO.update(departmentNew, "d014");
        }
        Optional<Department> result = departmentRepository.findById("d014");
        Assertions.assertEquals(result.get().getDeptName(), "NewDepartment");
    }


    @Test
    void testConstructor() {
        DepartmentDAO departmentDAO1 = new DepartmentDAO();
        Assertions.assertNotNull(departmentDAO1);
    }

    @Test
    void departmentDAOTest() {
        DepartmentDAO departmentDAO1 = new DepartmentDAO(departmentMapper, departmentRepository);
        Assertions.assertNotNull(departmentDAO1);
    }
}

