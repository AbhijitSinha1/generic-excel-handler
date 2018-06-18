package com.abhijit.geh;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abhijit.geh.marker.IExcelObject;
import com.abhijit.geh.pojo.Student;
import com.abhijit.geh.pojo.Teacher;

public class ExcelReadTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReadTest.class);
	private static final String xlsPath = "/home/abhijits/Work/project/generic_excel_reader/school_excel_file.xls";
	private static final String xlsxPath = "/home/abhijits/Work/project/generic_excel_reader/school_excel_file.xlsx";

	public static <T extends IExcelObject> void main(String[] args) throws IOException {
		LOGGER.info("starting application...");
		read(xlsPath);
		read(xlsxPath);
	}

	private static void read(String path) throws IOException {
		GenericExcelHandler geh = GenericExcelHandler.of(path);
		List<Student> students = geh.read("Students", Student.class);
		List<Teacher> teachers = geh.read("Teachers", Teacher.class);
		LOGGER.info("read {} student and {} teacher information", students.size(), teachers.size());
	}

}
