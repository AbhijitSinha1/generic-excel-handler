package com.abhijit.geh;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abhijit.geh.GenericExcelHandler;
import com.abhijit.geh.marker.IExcelObject;
import com.abhijit.geh.pojo.Student;
import com.abhijit.geh.pojo.Teacher;
import com.abhijit.geh.utils.RandomUtil;

public class ExcelWriteTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelWriteTest.class);
	private static final String excelPath = "/home/abhijits/Work/project/generic_excel_reader/school_excel_file.xls";

	@SuppressWarnings("unchecked")
	public static <T extends IExcelObject> void main(String[] args) throws IOException {

		LOGGER.info("starting application...");

		Map<String, List<T>> outStudents = new HashMap<>();
		List<T> students = (List<T>) generateStudets(RandomUtil.randomInteger(10, 100), Student.class);
		List<T> teachers = (List<T>) generateTeachers(RandomUtil.randomInteger(10, 20), Teacher.class);

		outStudents.put("Students", students);
		outStudents.put("Teachers", teachers);

		LOGGER.info("writing {} student and {} teacher information", students.size(), teachers.size());

		GenericExcelHandler.write(outStudents, excelPath);
	}

	// private helper methods
	// ------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private static <T extends IExcelObject> T generateTeacher(Class<T> clz) {
		Teacher teacher = new Teacher();
		teacher.setGrade(RandomUtil.randomInteger(4, 10));
		teacher.setName(RandomUtil.randomString(RandomUtil.randomInteger(5, 10)));
		teacher.setSchool(RandomUtil.randomWords(RandomUtil.randomInteger(1, 3)));
		teacher.setSection(RandomUtil.randomCharacter("ABCDEFG"));
		teacher.setSubject(RandomUtil.randomString(RandomUtil.randomInteger(5, 10)));
		return (T) teacher;
	}

	@SuppressWarnings("unchecked")
	private static <T extends IExcelObject> T generateStudent(Class<T> clz) {
		Student student = new Student();
		int grade = RandomUtil.randomInteger(4, 10);
		int age = grade + 5;
		student.setGrade(grade);
		student.setAge(age);

		student.setName(RandomUtil.randomString(RandomUtil.randomInteger(5, 10)));
		student.setFatherName(RandomUtil.randomString(RandomUtil.randomInteger(5, 10)));
		
		student.setSchool(RandomUtil.randomWords(RandomUtil.randomInteger(1, 3)));
		student.setSection(RandomUtil.randomCharacter("ABCD"));

		student.setHeight(RandomUtil.randomDouble(3, 5));
		student.setWeight(RandomUtil.randomDouble(30, 50));
		return (T) student;
	}

	private static <T extends IExcelObject> List<T> generateTeachers(int i, Class<T> clz) {
		return IntStream.range(0, i)
		    .boxed()
		    .map(index -> generateTeacher(clz))
		    .collect(Collectors.toList());
	}

	private static <T extends IExcelObject> List<T> generateStudets(int i, Class<T> clz) {
		return IntStream.range(0, i)
		    .boxed()
		    .map(index -> generateStudent(clz))
		    .collect(Collectors.toList());
	}

}
