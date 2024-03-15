package edu.kh.emp.model.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

public class EmployeeService {

	private EmployeeDAO dao = new EmployeeDAO();

	/** 전체 사원 정보 조회 서비스
	 * @return list 
	 */
	public List<Employee> selectAll() throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		List<Employee> list = dao.selectAll(conn); // Connection을 통해 Statement나 PreparedStatement 만들어주기 때문에 
													// 반드시 conn 넘겨주기
		close(conn);
		
		return list;
	}

	
	/** 사원 정보 추가 서비스
	 * @param emp
	 * @return result (1/0)
	 */
	public int insertEmployee(Employee emp) throws Exception{

		Connection conn = getConnection();
		
		int result = dao.insertEmployee(conn,emp);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}


	/** 사번이 일치하는 사원 정보 조회 서비스
	 * @param empID
	 * @return
	 * @throws Exception
	 */
	public Employee selectEmpId(int empID) throws Exception {

		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpId(conn, empID);
		
		close(conn);
		
		return emp;
	}


	/** 사번이 일치하는 사원 정보 수정 서비스
	 * @param emp
	 * @return result
	 */
	public int updateEmployee(Employee emp) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.updateEmployee(conn,emp);
		
		if(result >0)commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}


	/** 사번이 일치하는 사원 정보 삭제 서비스
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(int empId) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.deleteEmployee(conn, empId);
		
		if(result > 0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		
		return result;
	}


	public List<Employee> selectDeptEmp(String dept) throws Exception{
		
		Connection conn = getConnection();
		
		List<Employee> empList = dao.selectDeptEmp(conn, dept);
		
		close(conn);
		
		return empList;
	}


	public List<Employee> selectSalaryEmp(int salaryInput) throws Exception{

		Connection conn = getConnection();
		
		List<Employee> empList = dao.selectSalaryEmp(conn, salaryInput);
		
		close(conn);
		
		return empList;
	}


	public LinkedHashMap<String, Integer> selectDeptTotalSalary() throws Exception {

		Connection conn = getConnection();
		
		LinkedHashMap<String, Integer> deptMap = dao.selectDeptTotalSalary(conn);
		
		close(conn);
		
		return deptMap;
	}


	public Employee selectEmpNo(String empNoInput) throws Exception {
		
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpNo(conn, empNoInput);
		
		close(conn);
		
		
		return emp;
	}


	public LinkedHashMap<String, Double> selectJobAvgSalary() throws Exception{
		
		Connection conn = getConnection();
		
		LinkedHashMap<String, Double> salAvgMap = dao.selectJobAvgSalary(conn);
		
		close(conn);
		
		return salAvgMap;
	}
	
	
}
