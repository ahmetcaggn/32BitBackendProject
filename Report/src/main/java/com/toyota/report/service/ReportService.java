package com.toyota.report.service;

import com.toyota.report.dto.SaleDto;
import com.toyota.report.dto.SaleProductDto;
import com.toyota.report.interfaces.SaleInterface;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class ReportService {

    private final SaleInterface saleInterface;

    public ReportService(SaleInterface saleInterface) {
        this.saleInterface = saleInterface;
    }

    public SaleDto getSaleById(Long id) {
        return saleInterface.getSaleById(id).getBody();
    }

    public Page<SaleDto> getAllSalesByPage(Integer page, Integer rows, String sort, Long filterProductId, Long filterCampaignId, Float minPrice, Float maxPrice, String sortDirection) {
        return saleInterface.getAllSaleByPagination(page, rows, sort, filterProductId, filterCampaignId, minPrice, maxPrice, sortDirection).getBody();
    }

    public void createEmptyPdfBySaleId(Long id) throws IOException {
        SaleDto saleDto = saleInterface.getSaleById(id).getBody();
        PDDocument document = new PDDocument();
        int contentLength = 10;
        int rows = saleDto != null ? saleDto.getSaleProducts().size() : 0;
        rows += contentLength;
        PDPage page = new PDPage();
        float width = 300f;
        float height = 200f;
        float fontSize = 10f;
        float offsetX = 10f;
        float offsetY = fontSize + 15f;
        float text_width;


        height += rows * 30;

        page.setMediaBox(new PDRectangle(width, height));
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        PDType0Font font = PDType0Font.load(document, new File(String.format("%s%s", "Report/src/main/java/com/toyota/report/font/", "OpenSans-VariableFont.ttf")));
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(offsetX, height - offsetY);
        contentStream.setLeading(fontSize + 1f);

        //Content
        writeMessage(contentStream, "Thanks for your purchase");
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

        int value = contentLength * (int) (fontSize + 3f);
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

            text_width = (font.getStringWidth(String.format("*%s", saleProductDto.getProduct().getPrice())) / 1000.0f) * fontSize;
            contentStream.moveTextPositionByAmount(-text_width, 0);
            contentStream.drawString(String.format("*%s", saleProductDto.getProduct().getPrice()));
            contentStream.moveTextPositionByAmount(text_width, 0);

            value += (int) fontSize + 17;
            contentStream.endText();
        }

        contentStream.lineTo(page.getMediaBox().getLowerLeftX(), height - value);
        contentStream.stroke();
        //Content End
        contentStream.close();

        document.save(String.format("%s%s%s","/home/ubuntu/Documents/receipts/",saleDto.getId(),".pdf"));
        document.close();
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

}
