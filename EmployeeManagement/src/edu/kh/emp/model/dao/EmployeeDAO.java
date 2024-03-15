package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {

	// JDBC 객체 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs; // select문으로 조회시 가져올 ResultSet
	
	private Properties prop;
	
	public EmployeeDAO() {

		// properties 이용해서 query.xml 읽어온 거 저장
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("query.xml")); 
			// inputStream을 통해 query.xml의 entry (key와 value)값 읽어서 prop에 저장
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
	
	/** 전체 사원 정보 조회 DAO
	 * @param conn
	 * @return list 
	 */
	public List<Employee> selectAll(Connection conn) throws Exception{
		
		// 결과 저장용 변수 선언
		List<Employee> list = new ArrayList<Employee>();
		
		try {
			String sql = prop.getProperty("selectAll");
			
			// Statement 객체 생성 (placeholder가 없어서 Statement만 생성하면 됨)
			stmt = conn.createStatement();
			
			// SQL을 수행 후 결과(ResultSet) 반환 받음
			rs = stmt.executeQuery(sql);
			
			// 조회 결과를 얻어와 한 행씩 접근하여
			// Employee 객체 생성 후 컬럼 값 담기
			// -> List 담기
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				// EMP_ID 컬럼은 문자열 컬럼이지만
				// 저장된 값들은 모두 숫자형태
				// -> DB에서 자동으로 형변환 진행해서 얻어옴
						// 전화번호 숫자형태로 얻어오면 앞에 0 붙은 거 날아감
				
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
				list.add(emp); // List 담기
				
			} // while문 종료
			
		}finally {
			
			// 사용한 JDBC 객체 자원 반환
			close(rs);
			close(stmt);
			
		}
		return list;
	}


	/** 사원 정보 추가 DAO
	 * @param conn
	 * @param emp
	 * @return result (1/0)
	 */
	public int insertEmployee(Connection conn, Employee emp) throws Exception{

		int result = 0;
		
		try {
			
			// SQL 작성
			String sql = prop.getProperty("insertEmployee");
			// INSERT INTO EMPLOYEE VALUES(?,?,?,?,?,?,?,?,?,?,?, SYSDATE, NULL, DEFAULT)
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? (위치홀더)에 알맞은 값 대입
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			
			result = pstmt.executeUpdate();
			
			
		}finally {
			close(pstmt);
		}
		
		
		
		
		
		return result;
	}



	public Employee selectEmpId(Connection conn, int empID) throws Exception{
		
		Employee emp = null;

		try {
			String sql = prop.getProperty("selectEmpId");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empID);
			
			
			// stmt - ? (위치홀더 없을 때)
			// pstmt - ? (위치홀더 있을 때)
			// executeQuery() - Select -> ResultSet
			// executeUpdate() - DML (Update/Delete/Insert) -> int
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empID, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
			}

			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return emp;
	}



	/** 사번이 일치하는 사원 정보 수정 DAO
	 * @param conn
	 * @param emp
	 * @return
	 */
	public int updateEmployee(Connection conn, Employee emp) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result = pstmt.executeUpdate();
			
			
		}finally {
			
			close(pstmt);
			
			
		}
		
		
		
		return result;
	}



	/** 사원이 일치하는 사원 정보 삭제 DAO
	 * @param conn
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(Connection conn, int empId) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteEmployee");
			// DELETE FROM EMPLOYEE WHERE EMP_ID = ?
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		
		
		return result;
	}



	public List<Employee> selectDeptEmp(Connection conn, String dept) throws Exception{
		
		List<Employee> empList = new ArrayList<Employee>();
		
		
		try {
			String sql = prop.getProperty("selectDeptEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dept);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email,phone, departmentTitle, jobName, salary);
				empList.add(emp);
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return empList;
	}



	public List<Employee> selectSalaryEmp(Connection conn, int salaryInput) throws Exception{
		
		List<Employee> empList = new ArrayList<Employee>();
		
		try {
		String sql = prop.getProperty("selectSalaryEmp");
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, salaryInput);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int empId = rs.getInt("EMP_ID");
			String empName = rs.getString("EMP_NAME");
			String empNo = rs.getString("EMP_NO");
			String email = rs.getString("EMAIL");
			String phone = rs.getString("PHONE");
			String departmentTitle = rs.getString("DEPT_TITLE");
			String jobName = rs.getString("JOB_NAME");
			int salary = rs.getInt("SALARY");
			
			Employee emp = new Employee(empId, empName, empNo, email,phone, departmentTitle, jobName, salary);
			empList.add(emp);
			
		}
		
		}finally {
			close(rs);
			close(pstmt);
			
			
		}
		
		
		return empList;
	}



	public LinkedHashMap<String, Integer> selectDeptTotalSalary(Connection conn) throws Exception{
		
		LinkedHashMap<String, Integer> deptMap = new LinkedHashMap<String, Integer>();
		
		try {
			
			String sql = prop.getProperty("selectDeptTotalSalary");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String deptCode = rs.getString("DEPT_CODE");
				int salary = rs.getInt("SUMSAL");
				
				deptMap.put(deptCode, salary);
				
			}
			
			
		}finally {
			close(rs);
			close(stmt);
			
			
		}
		
		
		return deptMap;
	}



	public Employee selectEmpNo(Connection conn, String empNoInput) throws Exception {
		Employee emp = null;
		
		try {
			String sql = prop.getProperty("selectEmpNo");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, empNoInput);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			int empId = rs.getInt("EMP_ID");
			String empName = rs.getString("EMP_NAME");
			String email = rs.getString("EMAIL");
			String phone = rs.getString("PHONE");
			String departmentTitle = rs.getString("DEPT_TITLE");
			String jobName = rs.getString("JOB_NAME");
			int salary = rs.getInt("SALARY");
			
			emp = new Employee(empId,empName, empNoInput, email,phone, departmentTitle, jobName, salary);
			}
		}finally {
			
			close(rs);
			close(pstmt);
		}
		
		
		
		
		return emp;
	}



	public LinkedHashMap<String, Double> selectJobAvgSalary(Connection conn) throws SQLException {
		
		LinkedHashMap<String, Double> salAvgMap = new LinkedHashMap<String, Double>();
		
		try {
			String sql = prop.getProperty("selectJobAvgSalary");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String jobName = rs.getString("JOB_NAME");
				double salAvg = rs.getDouble("SALARY");
				
				salAvgMap.put(jobName, salAvg);

			}
			
		} finally {
			close(rs);
			close(stmt);
			
			
		}
		
		return salAvgMap;
	}



	
}
