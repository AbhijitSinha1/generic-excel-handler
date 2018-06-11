package com.abhijit.geh;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abhijit.geh.GenericExcelHandler;
import com.abhijit.geh.marker.IExcelObject;
import com.abhijit.geh.pojo.Student;
import com.abhijit.geh.pojo.Teacher;
import com.abhijit.jrg.RandomGenerator;

public class ExcelWriteTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelWriteTest.class);
	private static final String excelPath = "/home/abhijits/Work/project/generic_excel_reader/school_excel_file.xls";

	@SuppressWarnings("unchecked")
	public static <T extends IExcelObject> void main(String[] args) throws IOException {

		LOGGER.info("starting application...");

		Map<String, List<T>> outStudents = new HashMap<>();
		List<T> students = (List<T>) generateStudets(RandomGenerator.number(10, 100), Student.class);
		List<T> teachers = (List<T>) generateTeachers(RandomGenerator.number(10, 20), Teacher.class);

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
		teacher.setGrade(RandomGenerator.number(4, 10));
		teacher.setName(RandomGenerator.string(RandomGenerator.number(5, 10)));
		teacher.setSchool(join(RandomGenerator.strings(RandomGenerator.number(1, 3)), (a, b) -> a + " " + b));
		teacher.setSection(RandomGenerator.character('A', 'B', 'C', 'D', 'E', 'F', 'G'));
		teacher.setSubject(RandomGenerator.string(RandomGenerator.number(5, 10)));
		return (T) teacher;
	}

	@SuppressWarnings("unchecked")
	private static <T extends IExcelObject> T generateStudent(Class<T> clz) {
		Student student = new Student();
		int grade = RandomGenerator.number(4, 10);
		int age = grade + 5;
		student.setGrade(grade);
		student.setAge(age);

		student.setName(RandomGenerator.string(RandomGenerator.number(5, 10)));
		student.setFatherName(RandomGenerator.string(RandomGenerator.number(5, 10)));

		student.setSchool(join(RandomGenerator.strings(RandomGenerator.number(1, 3)), (a, b) -> a + " " + b));
		student.setSection(RandomGenerator.character('A', 'B', 'C', 'D'));

		student.setHeight(RandomGenerator.number(3, 5));
		student.setWeight(RandomGenerator.number(30, 50));
		return (T) student;
	}

	private static <T> T join(List<T> ts, BinaryOperator<T> accumulator) {
		return ts.stream()
		    .reduce(accumulator)
		    .get();
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
