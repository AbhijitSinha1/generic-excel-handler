# Generic Excel Reader/Writer

## Usage
We start by first defining the `Student` and `Teacher` class, where the Student class is as follows:

```java
public class Student implements IExcelObject {

	@GenericExcelColumn("Student Name")
	private String name;

	@GenericExcelColumn("Father's Name")
	private String fatherName;

	@GenericExcelColumn(value = "Student Age")
	private int age;

	@GenericExcelColumn("Student Height")
	private double height;

	@GenericExcelColumn("Student Weight")
	private double weight;

	@GenericExcelColumn("School")
	private String school;

	@GenericExcelColumn("Grade")
	private int grade;

	@GenericExcelColumn("section")
	private char section;

	// ------------------------------------------
	// getter and setter methods
}
```
Similarly Teacher class is:

```java
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

	// ------------------------------------------
	// getter and setter methods
}
```
### Excel Write
```java
Map<String, List<T>> map = new HashMap<>();
map.put("Students", fetchStudents());
map.put("Teachers", fetchTeachers());
GenericExcelHandler.write(map, "/path/to/excel/file.xls");
```
This will create an excel file with 2 sheets named `Students` and `Teachers` and populate the sheets with the corresponding data.

### Excel Read
```java
GenericExcelHandler geh = GenericExcelHandler.of("/path/to/excel/file.xls");
List<Student> students = geh.read("Students", Student.class);
List<Teacher> teachers = geh.read("Teachers", Teacher.class);
```
This will read the excel file which contains sheets named `Students` and `Teachers` and create a list of corresponding objects.
