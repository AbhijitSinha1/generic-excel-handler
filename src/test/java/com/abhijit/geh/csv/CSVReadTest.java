package com.abhijit.geh.csv;

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

public class CSVReadTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVReadTest.class);

	public static <T extends IExcelObject> void main(String[] args) throws IOException {
		String csvPath = PropertiesReader.instance().read("filepath") + "school_excel_file.csv";
		LOGGER.info("starting application...");
		read(csvPath);
	}

	private static void read(String path) throws IOException {
		GenericExcelHandler geh = GenericExcelHandler.of(path, FileFormat.CSV);
		List<Student> students = geh.read("Students", Student.class);
		List<Teacher> teachers = geh.read("Teachers", Teacher.class);
		LOGGER.info("read {} student and {} teacher information", students.size(), teachers.size());
	}

}
