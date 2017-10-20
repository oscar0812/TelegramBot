package com.bittle.telegram.Configurations;

import com.bittle.telegram.Main.MainClass;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by oscartorres on 7/15/17.
 */
public class GlobalBotConfig {

    private final String ROOT_PATH =
            MainClass.directory.global_dir();

    private final String PATH_TO_ADMIN_XML= ROOT_PATH+ File.separator+"Admins.xml";

    public GlobalBotConfig(){
        File admin_file = new File(PATH_TO_ADMIN_XML);

        if(!admin_file.exists()){
            setInitialAdmins();
        }
    }

    private void setInitialAdmins(){
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root element
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("Admins");
            doc.appendChild(root);

            // write the content into file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH_TO_ADMIN_XML));

            transformer.transform(source, result);

            addNewAdmin(277439737, "OGBittle");
            addNewAdmin(428043035, "Lexilovestacos");
            addNewAdmin(250014386, "Disproven");
            addNewAdmin(130107302, "lntegrity");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    public void addNewAdmin(long id, String username){
        if(!adminExists(id)) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(PATH_TO_ADMIN_XML);

                Node root = doc.getElementsByTagName("Admins").item(0);

                // Player elements
                Element person = doc.createElement("User");
                person.setAttribute("id", id + "");
                person.setAttribute("username", username);
                root.appendChild(person);

                // write the content into Games file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(PATH_TO_ADMIN_XML));

                transformer.transform(source, result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeAdmin(String username){
        removeAdmin("username", username);
    }

    public void removeAdmin(long chat_id){
        removeAdmin("id", chat_id+"");
    }

    private void removeAdmin(String remove_by_this, String value_of_tag){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(PATH_TO_ADMIN_XML);

            Node root = doc.getElementsByTagName("Admins").item(0);

            //loop through users
            NodeList users = root.getChildNodes();

            for (int x = 0; x < users.getLength(); x++) {
                Node current_user = users.item(x);
                String val = current_user.getAttributes().getNamedItem(remove_by_this).getTextContent();

                if(val.equals(value_of_tag)){
                    // found user to remove
                    root.removeChild(current_user);
                }

            }

            // write the content into Games file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH_TO_ADMIN_XML));

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean adminExists(long user_id) {
        return adminExists("id", user_id+"");
    }

    public boolean adminExists(String username){
        return adminExists("username", username);
    }

    private boolean adminExists(String find_by_this, String value_of_tag){
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(PATH_TO_ADMIN_XML);

            // Get the type-game element by tag name directly
            Node root = doc.getElementsByTagName("Admins").item(0);

            //loop through users
            NodeList users = root.getChildNodes();

            boolean player_exists = false;
            for (int x = 0; x < users.getLength(); x++) {
                Node current_user = users.item(x);
                String current_id = current_user.getAttributes().getNamedItem(find_by_this).getTextContent();

                if(current_id.equals(value_of_tag)){
                    player_exists = true;
                    break;
                }
            }

            return player_exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getAdmins() {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(PATH_TO_ADMIN_XML);

            // Get the type-game element by tag name directly
            Node root = doc.getElementsByTagName("Admins").item(0);

            //loop through users
            NodeList users = root.getChildNodes();

            String all = "";
            for (int x = 0; x < users.getLength(); x++) {
                Node current_user = users.item(x);
                String current_u = current_user.getAttributes().getNamedItem("username").getTextContent();
                all+=("@"+current_u+"\n");
            }
            return all+"\n";

        } catch (Exception e) {
            e.printStackTrace();
            return "No admins";
        }
    }
}
