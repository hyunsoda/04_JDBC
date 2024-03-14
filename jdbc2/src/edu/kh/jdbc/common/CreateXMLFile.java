package edu.kh.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	public static void main(String[] args) {

		// XML (eXtensible Markup Language) : 단순화된 데이터 기술 형식
		// 데이터를 어떻게 표현하고 전송하겠다 -- DB정보, SQL 쿼리등을 저장
		
		// XML에 저장되는 데이터 형식 Key : Value 형식이다.
		// -> Key, Value 모두 String(문자열) 형식이다.
		
		// XML 파일을 읽고, 쓰기 위한 IO 관련 클래스 필요
		
		// * Properties 컬렉션 객체 *
		// - Map 후손 클래스
		// - Key, Value 모두 String (문자열)
		// - XML 파일을 읽고, 쓰는데 특화된 메서드 제공
		
		
		// IO관련 Exception 발생할 수 있기 때문에 try로 시작
		try {
			
			Scanner sc = new Scanner(System.in);
			
			// Properties 객체 생성
			Properties prop = new Properties();
			
			System.out.print("생성할 파일 이름 : ");
			String fileName = sc.next();
			
			// FileOutputStream 생성
			FileOutputStream fos = new FileOutputStream(fileName + ".xml"); // 현재 프로젝트 안에 생성됨
			
			// Properties 객체를 이용해서 XML 파일 생성
			prop.storeToXML(fos, fileName+".xml file!!!");
			
			System.out.println(fileName + ".xml 파일 생성 완료");
			// 데이터를 불러다 쓰려면 안에 doctype, properties, entry등이 있어야 한다
			
			
			
			
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
