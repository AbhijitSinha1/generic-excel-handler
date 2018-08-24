package com.abhijit.geh.pojo;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.abhijit.jrg.RandomGenerator;

public class PojoFactory {
	private PojoFactory() {
	}

	public static List<Student> getStudents(Integer count) {
		return IntStream.range(0, count).boxed().map(index -> generateStudent()).collect(Collectors.toList());
	}

	public static List<Teacher> getTeachers(Integer count) {
		return IntStream.range(0, count).boxed().map(index -> generateTeacher()).collect(Collectors.toList());
	}

	// private functions
	// ------------------------------------------------------------------------

	private static <T> T join(List<T> ts, BinaryOperator<T> accumulator) {
		return ts.stream().reduce(accumulator).get();
	}

	private static Teacher generateTeacher() {
		Teacher teacher = new Teacher();
		teacher.setGrade(RandomGenerator.number(4, 10));
		teacher.setName(RandomGenerator.string(RandomGenerator.number(5, 10)));
		teacher.setSchool(join(RandomGenerator.strings(RandomGenerator.number(1, 3)), (a, b) -> a + " " + b));
		teacher.setSection(RandomGenerator.character('A', 'B', 'C', 'D', 'E', 'F', 'G'));
		teacher.setSubject(RandomGenerator.string(RandomGenerator.number(5, 10)));
		return teacher;
	}

	private static Student generateStudent() {
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
		return student;
	}

}
