package be.sdlg.snt;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import be.sdlg.snt.reports.FormDataAuditReport;
import be.sdlg.snt.reports.ItemDataAuditItem;
import be.sdlg.snt.reports.ItemGroupDataAuditItem;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
public class PdfFormAuditReportView extends AbstractPdfView {

	private static Font mediumBold = new Font(Font.TIMES_ROMAN, 14,
		      Font.BOLD);
	private static Font smallBold = new Font(Font.TIMES_ROMAN, 12,
		      Font.BOLD);
	private static Font normal = new Font(Font.TIMES_ROMAN, 10,Font.NORMAL);

	protected void createFormDataAuditRow(FormDataAuditReport freport, Table table, int i) throws Exception {
		try {
			table.addCell(freport.getFormDataAuditItemList().get(i).userName);
			table.addCell(freport.getFormDataAuditItemList().get(i).timeStamp);
			table.addCell(freport.getFormDataAuditItemList().get(i).locationName);
			table.addCell(freport.getFormDataAuditItemList().get(i).reasonForChange);
			table.addCell(freport.getFormDataAuditItemList().get(i).oldStatus);
			table.addCell(freport.getFormDataAuditItemList().get(i).newStatus);
			table.addCell(freport.getFormDataAuditItemList().get(i).signatorName);
			table.addCell(freport.getFormDataAuditItemList().get(i).signLocation);
			table.addCell(freport.getFormDataAuditItemList().get(i).signStatus);
		}
		catch (Exception e) {
			throw e;
		}
	}
	protected Table createFormDataAuditTable(FormDataAuditReport freport, int idx) throws Exception {
		Table table = new Table(9);
		try {
			createFormDataAuditRow(freport, table, 0);
			createFormDataAuditRow(freport, table, idx);
			return table;
		} catch (Exception e) {
			throw e;
		}
		
	}
	protected void createItemGroupDataAuditRow(List<ItemGroupDataAuditItem> igList, Table table, int i) throws Exception {
		try {
			table.addCell(igList.get(i).userName);
			table.addCell(igList.get(i).timeStamp);
			table.addCell(igList.get(i).locationName);
			table.addCell(igList.get(i).itemGroupRepeatNewKey);
			table.addCell(igList.get(i).itemGroupRepeatOldKey);
		} catch (Exception e) {
			throw e;
		}
	}
	protected Table createItemGroupDataAudit(List<ItemGroupDataAuditItem> igList, int idx) throws Exception {
		Table  table = new Table(5);
		try {
			createItemGroupDataAuditRow(igList, table, 0);
			createItemGroupDataAuditRow(igList, table, idx);
			return table;
		} catch(Exception e) {
			throw e;
		}
	}
	protected void createItemDataAuditRow(List<ItemDataAuditItem> idList, Table table, int i) throws Exception {
		try {
			table.addCell(idList.get(i).userName);
			table.addCell(idList.get(i).timeStamp);
			table.addCell(idList.get(i).locationName);
			table.addCell(idList.get(i).codeValueNew);
			table.addCell(idList.get(i).valueNew);
			table.addCell(idList.get(i).codeValueOld);
			table.addCell(idList.get(i).valueOld);
		} catch (Exception e) {
			throw e;
		}
		
	}
	protected Table createItemDataAudit(List<ItemDataAuditItem> idList, int idx) throws Exception {
		Table table = new Table(7);
		try {
			createItemDataAuditRow(idList, table, 0);
			createItemDataAuditRow(idList, table, idx);
			return table;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Paragraph content = new Paragraph();
		Paragraph header = new Paragraph();
		Paragraph body = new Paragraph();
		
		Paragraph title = new Paragraph((String) model.get("titleReport"), mediumBold);
		Paragraph info = new Paragraph((String) model.get("info"), smallBold);
		
		header.add(title);
		header.add(new Paragraph(" "));
		header.add(info);
		FormDataAuditReport freport = (FormDataAuditReport) model.get("formDataAuditReport");
	
		
		for (int i=1 ; i<freport.getFormDataAuditItemList().size(); i++) {
			body.add(new Paragraph(freport.formSection, smallBold));

			Table table =  createFormDataAuditTable(freport, i);
			body.add(table);
			List<ItemGroupDataAuditItem> itemGroupList = freport.getFormDataAuditItemList().get(i).itemGroupDataAuditItemList ;
			for (int j=1 ; j<itemGroupList.size(); j++) {
				body.add(new Paragraph(freport.getItemGroupSection() + " -" +itemGroupList.get(j).itemGroupName, smallBold));
				Table tableItemGroup = createItemGroupDataAudit(itemGroupList, j);
				body.add(tableItemGroup);
				List<ItemDataAuditItem> itemList = itemGroupList.get(j).itemDataAuditList;
				for (int k=1; k< itemList.size(); k++) {
					body.add(new Paragraph(freport.getItemSection() + " - "+ itemList.get(k).itemName, smallBold));
					Table tableItem = createItemDataAudit(itemList, k);
					body.add(tableItem);
				}
			}
			body.add(new Paragraph(" "));

		}
	
		content.add(header);
		content.add(body);
		
		document.add(content);
		//document.newPage();
		
		
		
	}

}
