package com.abhijit.geh.csv;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abhijit.geh.GenericExcelHandler;
import com.abhijit.geh.PropertiesReader;
import com.abhijit.geh.enums.FileFormat;
import com.abhijit.geh.marker.IExcelObject;
import com.abhijit.geh.pojo.PojoFactory;
import com.abhijit.jrg.RandomGenerator;

public class CSVWriteTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVWriteTest.class);

	@SuppressWarnings("unchecked")
	public static <T extends IExcelObject> void main(String[] args) throws IOException {
		String csvPath = PropertiesReader.instance().read("filepath") + "school_excel_file.csv";

		LOGGER.info("starting application...");

		Map<String, List<T>> outStudents = new HashMap<>();
		List<T> students = (List<T>) PojoFactory.getStudents(RandomGenerator.number(10, 100));
		List<T> teachers = (List<T>) PojoFactory.getTeachers(RandomGenerator.number(10, 100));

		outStudents.put("Students", students);
		outStudents.put("Teachers", teachers);

		LOGGER.info("writing {} student and {} teacher information", students.size(), teachers.size());

		extracted(outStudents, csvPath);
	}

	private static <T extends IExcelObject> void extracted(Map<String, List<T>> outStudents, String path)
			throws IOException {
		GenericExcelHandler.write(outStudents, path, FileFormat.CSV);
	}

}
