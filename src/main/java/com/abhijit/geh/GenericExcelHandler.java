package com.abhijit.geh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abhijit.geh.annotations.GenericExcelColumn;
import com.abhijit.geh.enums.FileFormat;
import com.abhijit.geh.marker.IExcelObject;
import com.abhijit.geh.utils.ClassCast;

public class GenericExcelHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericExcelHandler.class);

	private Workbook workbook;

	// constructors
	// --------------------------------------------------------------------------------------------
	private GenericExcelHandler(String path, FileFormat format) throws IOException {
		this(new File(path), format);
	}

	private GenericExcelHandler(File file, FileFormat format) throws IOException {
		this(new FileInputStream(file), format);
	}

	private GenericExcelHandler(InputStream inputStream, FileFormat format) throws IOException {
		if (format.equals(FileFormat.XLSX)) {
			workbook = new XSSFWorkbook(inputStream);
		} else {
			workbook = new HSSFWorkbook(inputStream);
		}

	}

	// read methods
	// ------------------------------------------------------------------------
	public <T extends IExcelObject> List<T> read(String sheetName, Class<T> clz) {
		Sheet sheet = workbook.getSheet(sheetName);
		Map<String, Integer> header = generateHeader(sheet);
		return generateData(sheet, header, clz);
	}

	// write methods
	// ------------------------------------------------------------------------
	public static <T extends IExcelObject> void write(Map<String, List<T>> map, String path, FileFormat format)
			throws IOException {
		GenericExcelHandler.write(map, new File(path), format);
	}

	public static <T extends IExcelObject> void write(Map<String, List<T>> map, File file, FileFormat format)
			throws IOException {
		GenericExcelHandler.write(map, new FileOutputStream(file), format);
	}

	public static <T extends IExcelObject> void write(Map<String, List<T>> map, OutputStream outputStream,
			FileFormat format) throws IOException {
		switch (format) {
		case XLS: {
			writeToWorkbook(new XSSFWorkbook(), map, outputStream);
		}
			break;
		case XLSX: {
			writeToWorkbook(new HSSFWorkbook(), map, outputStream);
		}
			break;
		case CSV: {
			if (map.isEmpty()) {
				throw new IllegalArgumentException("No data sent");
			}
			Set<String> keys = map.keySet();
			String key = keys.iterator().next();
			
		}
			break;
		default: {
		}
			break;
		}

		if (format.equals(FileFormat.XLSX)) {
		} else {
		}
	}

	// factory methods
	// ------------------------------------------------------------------------
	public static GenericExcelHandler of(String filepath, FileFormat format) throws IOException {
		return new GenericExcelHandler(new File(filepath), format);
	}

	public static GenericExcelHandler of(File file, FileFormat format) throws IOException {
		return new GenericExcelHandler(new FileInputStream(file), format);
	}

	public static GenericExcelHandler of(InputStream inputStream, FileFormat format) throws IOException {
		return new GenericExcelHandler(inputStream, format);
	}

	// writer methods
	// ------------------------------------------------------------------------
	private static <T extends IExcelObject> void writeToWorkbook(Workbook workbook, Map<String, List<T>> map,
			OutputStream outputStream) throws IOException {
		for (String sheetName : map.keySet()) {
			LOGGER.debug("creating sheet: {}", sheetName);
			Sheet sheet = workbook.createSheet(sheetName);
			populateSheet(sheet, map.get(sheetName));
		}
		workbook.write(outputStream);
		workbook.close();
	}

	private static <T extends IExcelObject> void writeToFile(List<T> list, OutputStream outputStream) {

	}

	// private helper methods
	// ------------------------------------------------------------------------
	private static <T extends IExcelObject> void populateSheet(Sheet sheet, List<T> list) {
		Map<String, String> header = generateHeader(list.get(0));

		LOGGER.debug("adding header row: {}", header);
		Row headerRow = sheet.createRow(0);

		populateRow(headerRow, header);

		IntStream.range(0, list.size()).forEach(index -> {
			Row row = sheet.createRow(index + 1);
			try {
				populateRow(row, list.get(index), header);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchFieldException e) {
				LOGGER.error("could not populate row: {}", e);
			}
		});
	}

	private static void populateRow(Row headerRow, Map<String, String> header) {
		int cellIndex = 0;
		for (String colHeader : header.keySet()) {
			Cell cell = headerRow.createCell(cellIndex);
			cell.setCellValue(colHeader);
			cellIndex++;
		}
	}

	private static <T extends IExcelObject> Map<String, String> generateHeader(T t) {
		Map<String, String> header = new HashMap<>();

		for (Field field : t.getClass().getDeclaredFields()) {
			if (!field.isAnnotationPresent(GenericExcelColumn.class)) {
				LOGGER.debug("GenericExcelColumn annotation not found {}", field.getName());
				continue;
			}
			GenericExcelColumn annotation = field.getDeclaredAnnotation(GenericExcelColumn.class);
			header.put(annotation.value(), field.getName());
			LOGGER.debug("{}: {}", annotation.value(), field.getName());
		}
		return header;
	}

	private static <T extends IExcelObject> void populateRow(Row row, T t, Map<String, String> header)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException {

		int cellIndex = 0;
		for (String colHeader : header.keySet()) {
			String fieldName = header.get(colHeader);
			Field field = t.getClass().getDeclaredField(fieldName);

			Class<?> type = field.getType();

			String getterMethodName = generateMethodName(fieldName, "get");
			Method getterMethod = t.getClass().getMethod(getterMethodName);

			Object value = getterMethod.invoke(t);
			Cell cell = row.createCell(cellIndex);

			if (type.getName().equals("int") || type.equals(Integer.class)) {
				LOGGER.debug("{}: int", fieldName);
				cell.setCellValue((Integer) value);
			} else if (type.getName().equals("double") || type.equals(Double.class) || type.getName().equals("float")
					|| type.equals(Float.class)) {
				LOGGER.debug("{}: double", fieldName);
				cell.setCellValue((Double) value);
			} else if (type.getName().equals("boolean") || type.equals(Boolean.class)) {
				LOGGER.debug("{}: boolean", fieldName);
				cell.setCellValue((Boolean) value);
			} else if (type.equals(Date.class)) {
				LOGGER.debug("{}: date", fieldName);
				cell.setCellValue((Date) value);
			} else if (type.equals(Calendar.class)) {
				LOGGER.debug("{}: calendar", fieldName);
				cell.setCellValue((Calendar) value);
			} else {
				LOGGER.debug("{}: string", fieldName);
				cell.setCellValue(value.toString());
			}

			cellIndex++;
		}
	}

	private static String generateMethodName(String field, String prefix) {
		return prefix + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	private <T extends IExcelObject> List<T> generateData(Sheet sheet, Map<String, Integer> header, Class<T> clz) {
		return (List<T>) IntStream.rangeClosed(1, sheet.getLastRowNum()).boxed()
				.map(rowNumber -> sheet.getRow(rowNumber)).map(row -> {
					try {
						return generatePojo(row, header, clz);
					} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
							| IllegalArgumentException | InvocationTargetException e) {
						LOGGER.error("error: {}", e);
						return null;
					}
				}).filter(Objects::nonNull).collect(Collectors.toList());

	}

	private <T extends IExcelObject> T generatePojo(Row row, Map<String, Integer> header, Class<T> clz)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException {
		Map<String, Object> mapping = new HashMap<>();
		for (String colName : header.keySet()) {
			Integer colIndex = header.get(colName);
			Cell cell = row.getCell(colIndex);
			switch (cell.getCellTypeEnum()) {
			case _NONE: {
			}
				break;
			case BLANK: {
				mapping.put(colName, "");
			}
				break;
			case BOOLEAN: {
				mapping.put(colName, cell.getBooleanCellValue());
			}
				break;
			case ERROR: {
				mapping.put(colName, cell.getErrorCellValue());
			}
				break;
			case FORMULA: {
				mapping.put(colName, cell.getCellFormula());
			}
				break;
			case NUMERIC: {
				mapping.put(colName, cell.getNumericCellValue());
			}
				break;
			case STRING: {
				mapping.put(colName, cell.getStringCellValue());
			}
				break;
			}
		}
		T t = clz.newInstance();
		for (Field field : clz.getDeclaredFields()) {
			GenericExcelColumn annotation = field.getDeclaredAnnotation(GenericExcelColumn.class);
			String name = annotation.value();
			Object value = mapping.get(name);
			String fieldName = field.getName();
			String setterMethodName = generateMethodName(fieldName, "set");
			String getterMethodName = generateMethodName(fieldName, "get");
			Method getterMethod = clz.getDeclaredMethod(getterMethodName);
			Class<?> returnType = getterMethod.getReturnType();
			Method setterMethod = clz.getDeclaredMethod(setterMethodName, returnType);
			setterMethod.invoke(t, ClassCast.cast(value, returnType));
		}
		return t;
	}

	private Map<String, Integer> generateHeader(Sheet sheet) {
		Row headerRow = sheet.getRow(0);
		Map<String, Integer> header = new HashMap<>();
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			Cell cell = headerRow.getCell(i);
			String colName = cell.getStringCellValue();
			header.put(colName, i);
		}

		return header;
	}

}
