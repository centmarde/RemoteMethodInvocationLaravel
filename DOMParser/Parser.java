package DOMParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

    public static void main(String[] args) throws IOException {
        String url = "jdbc:mysql://localhost:3306/RMI-Laravel";
        String username = "root";
        String password = "";

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                // Create a DocumentBuilder
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                // Parse the XML file
                Document doc = builder.parse("C:\\laragon\\www\\RMI-Laravel\\server\\Products.xml");
                doc.getDocumentElement().normalize();
                System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

                // Get NodeList of Products elements
                NodeList nList = doc.getElementsByTagName("Products");

                // Prepare the insert query
                String insertQuery = "INSERT INTO tbl_products (product_code, name, retail_price, store_price, description) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    System.out.println("----------------------------");
                    for (int i = 0; i < nList.getLength(); i++) {
                        Node nNode = nList.item(i);
                        System.out.println("\nCurrent Element: " + nNode.getNodeName());
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;

                            String product_code = eElement.getAttribute("product_code");
                            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                            String retail_price = eElement.getElementsByTagName("retail_price").item(0).getTextContent();
                            String store_price = eElement.getElementsByTagName("store_price").item(0).getTextContent();
                            String description = eElement.getElementsByTagName("description").item(0).getTextContent();

                            // Set values for PreparedStatement
                            preparedStatement.setString(1, product_code);
                            preparedStatement.setString(2, name);
                            preparedStatement.setString(3, retail_price);
                            preparedStatement.setString(4, store_price);
                            preparedStatement.setString(5, description);

                            // Execute update query
                            int rowsAffected = preparedStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Data inserted successfully.");
                            } else {
                                System.out.println("Data insertion failed.");
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}
