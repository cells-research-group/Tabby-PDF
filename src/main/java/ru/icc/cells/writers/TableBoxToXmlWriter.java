package ru.icc.cells.writers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.icc.cells.common.TableBox;
import ru.icc.cells.common.TableRegion;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;

public class TableBoxToXmlWriter {
    public void write(List<TableBox> tableBoxes, String fileName, String path)
            throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder        docBuilder = docFactory.newDocumentBuilder();

        Document doc         = docBuilder.newDocument();
        Element  rootElement = doc.createElement("document");
        rootElement.setAttribute("filename", fileName);
        doc.appendChild(rootElement);

        for (int i = 0; i < tableBoxes.size(); i++) {
            Element table = doc.createElement("table");
            table.setAttribute("id", String.valueOf(i + 1));

            for (int j = 0; j < tableBoxes.get(i).getTableRegions().size(); j++) {
                TableBox tableBox = tableBoxes.get(i);
                Element  region   = doc.createElement("region");
                region.setAttribute("id", String.valueOf(j + 1));
                region.setAttribute("page", String.valueOf(tableBox.getPageNumber()));

                TableRegion tableRegion = tableBox.getTableRegions().get(j);
                Element     bBox        = doc.createElement("bounding-box");
                bBox.setAttribute("x1", String.valueOf((int) tableRegion.getLeft()));
                bBox.setAttribute("x2", String.valueOf((int) tableRegion.getRight()));
                bBox.setAttribute("y1", String.valueOf((int) tableRegion.getBottom()));
                bBox.setAttribute("y2", String.valueOf((int) tableRegion.getTop()));
                region.appendChild(bBox);
                table.appendChild(region);
            }
            rootElement.appendChild(table);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer        transformer        = transformerFactory.newTransformer();
        DOMSource          source             = new DOMSource(doc);
        StreamResult       result             = new StreamResult(path);

        transformer.transform(source, result);
    }

}