package com.example.InterviewDemo.service;

import com.example.InterviewDemo.dto.EmployeeDTO;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.pdf.PdfDocument;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfReportService {

    public byte[] generateEmployeeReport(List<EmployeeDTO> employees) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Employee Report").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));

            // Create a table with 6 columns
            Table table = new Table(6); // Adjust columns as needed
            table.setWidth(UnitValue.createPercentValue(100));
            table.addCell(new Cell().add(new Paragraph("ID")));
            table.addCell(new Cell().add(new Paragraph("Name")));
            table.addCell(new Cell().add(new Paragraph("Email")));
            table.addCell(new Cell().add(new Paragraph("Position")));
            table.addCell(new Cell().add(new Paragraph("Salary")));
            table.addCell(new Cell().add(new Paragraph("Department")));

            for (EmployeeDTO employee : employees) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getId()))));
                table.addCell(new Cell().add(new Paragraph(employee.getName())));
                table.addCell(new Cell().add(new Paragraph(employee.getEmail())));
                table.addCell(new Cell().add(new Paragraph(employee.getPosition())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getSalary()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getDepartment().getName()))));
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }
}
