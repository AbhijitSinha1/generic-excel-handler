package com.abhijit.geh.excel;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abhijit.geh.GenericExcelHandler;
import com.abhijit.geh.PropertiesReader;
import com.abhijit.geh.enums.FileFormat;
import com.abhijit.geh.marker.IExcelObject;
import com.abhijit.geh.pojo.Student;
import com.abhijit.geh.pojo.Teacher;

public class ExcelReadTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReadTest.class);

	public static <T extends IExcelObject> void main(String[] args) throws IOException {
		String xlsPath = PropertiesReader.instance().read("filepath") + "school_excel_file.xls";
		String xlsxPath = PropertiesReader.instance().read("filepath") + "school_excel_file.xlsx";
		LOGGER.info("starting application...");
		read(xlsPath, FileFormat.XLS);
		read(xlsxPath, FileFormat.XLSX);
	}

	private static void read(String path, FileFormat format) throws IOException {
		GenericExcelHandler geh = GenericExcelHandler.of(path, format);
		List<Student> students = geh.read("Students", Student.class);
		List<Teacher> teachers = geh.read("Teachers", Teacher.class);
		LOGGER.info("read {} student and {} teacher information", students.size(), teachers.size());
	}

}
