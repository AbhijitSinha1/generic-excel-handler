package com.abhijit.geh.pojo;

import com.abhijit.geh.annotations.GenericExcelColumn;
import com.abhijit.geh.marker.IExcelObject;

public class Teacher implements IExcelObject {

	@GenericExcelColumn("Teacher Name")
	private String name;

	@GenericExcelColumn("School")
	private String school;

	@GenericExcelColumn("Grade")
	private int grade;

	@GenericExcelColumn("Section")
	private char section;

	@GenericExcelColumn("Subject")
	private String subject;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
