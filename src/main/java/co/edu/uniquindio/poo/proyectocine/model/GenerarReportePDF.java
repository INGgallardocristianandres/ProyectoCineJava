package co.edu.uniquindio.poo.proyectocine.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenerarReportePDF implements GenerarReporte {
    private static final Path DIRECTORIO_REPORTES = Path.of("reportes");

    @Override
    public void generarReporte(String datos) {
        try {
            Files.createDirectories(DIRECTORIO_REPORTES);
            Path archivo = DIRECTORIO_REPORTES.resolve("reporte-cine.pdf");

            try (PDDocument documento = new PDDocument()) {
                PDPage pagina = new PDPage();
                documento.addPage(pagina);

                try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                    contenido.beginText();
                    contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                    contenido.newLineAtOffset(50, 750);
                    contenido.showText("Reporte CinemaUQ");
                    contenido.newLineAtOffset(0, -30);
                    contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

                    String texto = datos == null ? "" : datos;
                    for (String linea : texto.split("\\R", -1)) {
                        contenido.showText(linea.replaceAll("[^\\x20-\\x7E]", " "));
                        contenido.newLineAtOffset(0, -18);
                    }
                    contenido.endText();
                }
                documento.save(archivo.toFile());
            }

            System.out.println("Reporte PDF generado en: " + archivo.toAbsolutePath());
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo generar el reporte PDF", exception);
        }
    }
}
