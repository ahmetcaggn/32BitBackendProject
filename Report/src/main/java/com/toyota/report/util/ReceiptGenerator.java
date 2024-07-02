package com.toyota.report.util;

import com.toyota.report.dto.SaleDto;
import com.toyota.report.dto.SaleProductDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import static com.toyota.report.util.DirectoryUtil.getFontNameFromDirectory;

@Component
public class ReceiptGenerator {
    private final int contentLengthIntroduction = 13;
    private final int contentLengthEnd = 9;
    private final float fontLeading = 12f;
    private float height = 0;
    private final float width = 300f;
    private final float offsetX = 10f;
    private final float offsetY = 25f;
    private final float offsetLine = 15f;
    private final float fontSize = 10f;

    private PDType0Font font;
    private float value = 0f;
    private float text_width = 0;


    public void generateReceipt(SaleDto saleDto) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();

            int rows = 0;
            int paymenRows = 0;
            value = offsetY;
            height = 0;
            if (saleDto != null && saleDto.getCashAmount()>0) paymenRows++;
            if (saleDto != null && saleDto.getCreditCardAmount()>0) paymenRows++;
            if (saleDto != null && saleDto.getDiscountAmount()>0) paymenRows++;
            rows = saleDto != null ? saleDto.getSaleProducts().size() : 0;
            height += contentLengthIntroduction * fontLeading;
            height += rows * fontLeading * 2.5f;
            height += paymenRows * fontLeading;
            height += contentLengthEnd * fontLeading;

            page.setMediaBox(new PDRectangle(width, height));
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            font = PDType0Font.load(document, new File(String.format("%s%s", "/app/fonts/", getFontNameFromDirectory("/app/fonts/"))));
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(offsetX, height - offsetY);
            contentStream.setLeading(fontLeading);
            value = contentLengthIntroduction * (int) fontLeading;
            //Content
            writeMessage(contentStream, "Alışverişiniz İçin Teşekkür Ederiz");
            writeMessage(contentStream, "Toyota Comp. Ltd.");
            writeMessage(contentStream, "Kemalpaşa Mah. Üniversite Cad.");
            writeMessage(contentStream, "No: 92/D Serdivan/Sakarya");

            contentStream.newLine();
            writeMessage(contentStream, String.format("%s %s", "Date        : ", saleDto.getLocalDateTime().toLocalDate()));
            writeMessage(contentStream, String.format("%s %s", "Time       : ", saleDto.getLocalDateTime().toLocalTime().toString().substring(0, 8)));
            writeMessage(contentStream, String.format("%s %s", "Sale No   : ", saleDto.getId()));
            contentStream.newLine();
            contentStream.newLine();
            contentStream.endText();

            for (SaleProductDto saleProductDto : saleDto.getSaleProducts()) {
                contentStream.beginText();

                contentStream.newLineAtOffset(offsetX, height - value);
                contentStream.showText(String.format("%d", saleProductDto.getProduct().getProductCode()));
                contentStream.newLine();

                String productName = saleProductDto.getProduct().getName().toUpperCase();
                int productNameLengthLimit = stringLengthCalculator(font, fontSize, productName);
                contentStream.showText(productName.substring(0, productNameLengthLimit));

                contentStream.newLineAtOffset(page.getMediaBox().getWidth() / 2 + 15, 0);
                contentStream.showText(String.format("%%%d", saleProductDto.getProduct().getTax().intValue()));

                contentStream.newLineAtOffset(page.getMediaBox().getWidth() / 2 - 15 - offsetX * 2, 0);

                String floatToString = String.format("%.2f", saleProductDto.getProduct().getPrice());
                text_width = (font.getStringWidth(String.format("*%s", floatToString)) / 1000.0f) * fontSize;
                contentStream.moveTextPositionByAmount(-text_width, 0);
                contentStream.drawString(String.format("*%s", floatToString));
                contentStream.moveTextPositionByAmount(text_width, 0);

                value += (int) (fontLeading * 2.5);
                contentStream.endText();
            }
            value += (int) fontLeading;

            writePrices("Ara Toplam",saleDto.getTotalAmount(), contentStream);
            if (saleDto.getDiscountAmount() > 0){
                writePrices("Indirim Tutari",saleDto.getDiscountAmount(), contentStream);
            }

            //drawing a line
            drawLine(contentStream, page.getMediaBox().getLowerLeftX(), page.getMediaBox().getUpperRightX(), height - value, offsetLine);

            writePrices("TOPKDV",saleDto.getTotalTax(), contentStream);
            writePrices("TOPLAM",saleDto.getLastPrice(), contentStream);

            drawLine(contentStream, 0, width, height - value, offsetLine);

            if (saleDto.getCashAmount() > 0) {
                writePrices("Nakit",saleDto.getCashAmount(), contentStream);
            }
            if (saleDto.getCreditCardAmount() > 0) {
                writePrices("Kredi kartı",saleDto.getCreditCardAmount(), contentStream);
            }

            //Content End
            contentStream.close();
            document.save(String.format("%s%s%s", "/app/receipts/", saleDto.getId(), ".pdf"));
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeMessage(PDPageContentStream contentStream, String message) throws IOException {
        contentStream.showText(message);
        contentStream.newLine();
    }

    private int stringLengthCalculator(PDType0Font font, float fontSize, String message) throws IOException {
        float length = 0;
        int counter = 0;
        for (char a : message.toCharArray()) {
            if (length < 150) {
                length += font.getStringWidth(String.valueOf(a)) / 1000.0f * fontSize;
                counter++;
            } else {
                break;
            }
        }
        return counter;
    }

    private void drawLine(PDPageContentStream contentStream, float xCoordinateStart, float xCoordinateEnd, float yCoordinate, float offsetX) throws IOException {
        contentStream.moveTo(xCoordinateStart + offsetX, yCoordinate);
        contentStream.lineTo(xCoordinateEnd - offsetX, yCoordinate);
        contentStream.stroke();
        value += (int) fontLeading * 2;
    }

    private void writeReverse(PDPageContentStream contentStream, float denme) throws IOException {
        String count = String.format("%.2f", denme);
        text_width = (font.getStringWidth(String.format("*%s", count)) / 1000.0f) * fontSize;
        contentStream.moveTextPositionByAmount(-text_width, 0);
        contentStream.drawString(String.format("*%s", count));
        contentStream.moveTextPositionByAmount(text_width, 0);
        value += (int) fontLeading;
    }

    private void writePrices(String message, float price, PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(offsetX, height - value);
        contentStream.showText(message);
        contentStream.newLineAtOffset(width - offsetX * 2, 0);
        writeReverse(contentStream, price);
        contentStream.endText();
    }
}
