package com.abhijit.geh.pojo;

import com.abhijit.geh.annotations.GenericExcelColumn;
import com.abhijit.geh.marker.IExcelObject;

public class Student implements IExcelObject {

	@GenericExcelColumn("Student Name")
	private String name;
	
	@GenericExcelColumn("Father's Name")
	private String fatherName;

	@GenericExcelColumn(value = "Student Age")
	private int age;

	@GenericExcelColumn("Student Height (ft)")
	private double height;

	@GenericExcelColumn("Student Weight (kg)")
	private double weight;

	@GenericExcelColumn("School")
	private String school;

	@GenericExcelColumn("Grade")
	private int grade;

	@GenericExcelColumn("section")
	private char section;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public char getSection() {
		return section;
	}

	public void setSection(char section) {
		this.section = section;
	}
}
