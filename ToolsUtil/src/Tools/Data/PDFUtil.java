package Tools.Data;

import java.io.IOException;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.*;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.*;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;


public class PDFUtil
{

	public Table addTableCell(int style, Table table, Cell cell, Color borderColor, Color backColor, Color fontColor,
			PdfFont font, float fontsize, String align, String value)
	{
		cell.add(new Paragraph(value).setFont(font));
		cell.setFontSize(fontsize);
		if (borderColor == null) {
			cell.setBorder(new SolidBorder(Color.BLACK, 0.5f));
		} else {
			cell.setBorder(new SolidBorder(borderColor, 0.5f));
		}

		/* 文字位置 */
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		if ("left".equalsIgnoreCase(align)) {
			cell.setTextAlignment(TextAlignment.LEFT);
		} else if ("center".equalsIgnoreCase(align)) {
			cell.setTextAlignment(TextAlignment.CENTER);
		} else if ("right".equalsIgnoreCase(align)) {
			cell.setTextAlignment(TextAlignment.RIGHT);
		}

		// 背景顏色
		if (backColor != null) {
			cell.setBackgroundColor(backColor);
		}

		if (fontColor != null) {
			cell.setFontColor(fontColor);
		}

		/* 標題欄或普通欄位 */
		if (style == 0) {
			cell.setBold();
			table.addHeaderCell(cell);
		} else if (style == 1) {
			table.addCell(cell);
		}

		return table;
	}

	/* 字體：本機實體Fonts位置 kaiu.ttf 為標楷體 mingliu.ttc,0 代表 "細明體" mingliu.ttc,1 代表
	 * "新細明體" msjh.ttc,1 代表 "微軟正黑體"
	 */
	public PdfFont createPdfFont(String fontName)
	{
		PdfFont font = null;
		try {
			font = PdfFontFactory.createFont("C:/Windows/Fonts/" + fontName, PdfEncodings.IDENTITY_H, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return font;
	}

}
