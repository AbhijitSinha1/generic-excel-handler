package com.abhijit.geh;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abhijit.geh.GenericExcelHandler;
import com.abhijit.geh.marker.IExcelObject;
import com.abhijit.geh.pojo.Student;
import com.abhijit.geh.pojo.Teacher;

public class ExcelReadTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReadTest.class);
	private static final String excelPath = "/home/abhijits/Work/project/generic_excel_reader/school_excel_file.xls";

	public static <T extends IExcelObject> void main(String[] args) throws IOException {

		LOGGER.info("starting application...");
		GenericExcelHandler ger = GenericExcelHandler.of(excelPath);
		List<Student> students = ger.read("Students", Student.class);
		List<Teacher> teachers = ger.read("Teachers", Teacher.class);
		LOGGER.info("read {} student and {} teacher information", students.size(), teachers.size());
	}

}
