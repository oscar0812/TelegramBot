package com.bittle.telegram.config;

import com.bittle.telegram.main.Constants;
import com.bittle.telegram.main.MainClass;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Created by oscartorres on 7/6/17.
 * Holds the values to be stored and loaded for the bot
 */
public class PerGroupBotConfig {

    private String PATH_TO_XML_FILE =
            MainClass.directory.settings_dir();

    private final String BOT_ON_TAG = "Bot-On";
    private final String GAMES_ON_TAG = "games-On";
    private final String ANTI_BOT_ON_TAG = "Anti-Bot-On";
    private final String NEED_PFP_TAG = "Need-Pfp";
    private final String PY_COMMAND_ON_TAG = "Py-Command-On";
    private final String WELCOME_MESSAGE_ON_TAG = "Welcome-Message-On";
    private final String BYE_MESSAGE_ON_TAG = "Bye-Message-On";

    private final String MAX_TEXT_LENGTH_TAG = "Max-text-Length";
    private final String WELCOME_MESSAGE_TAG = "Welcome-Message";
    private final String BYE_MESSAGE_TAG = "Bye-Message";

    public PerGroupBotConfig(Update update) {
        long chat_id = update.getMessage().getChatId();
        PATH_TO_XML_FILE += (chat_id + ".xml");
        set_initial_values();
    }

    private void set_initial_values() {

        if (new File(PATH_TO_XML_FILE).exists()) {
            return;
        }

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root element
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("Settings");
            doc.appendChild(root);

            // Settings
            Element botOn = doc.createElement(BOT_ON_TAG);
            botOn.setAttribute("boolean", "true");
            root.appendChild(botOn);

            Element gamesOn = doc.createElement(GAMES_ON_TAG);
            gamesOn.setAttribute("boolean", "true");
            root.appendChild(gamesOn);

            Element antiBot = doc.createElement(ANTI_BOT_ON_TAG);
            antiBot.setAttribute("boolean", "false");
            root.appendChild(antiBot);

            Element needPfp = doc.createElement(NEED_PFP_TAG);
            needPfp.setAttribute("boolean", "false");
            root.appendChild(needPfp);

            Element pyCommand = doc.createElement(PY_COMMAND_ON_TAG);
            pyCommand.setAttribute("boolean", "false");
            root.appendChild(pyCommand);

            Element welcomeOn = doc.createElement(WELCOME_MESSAGE_ON_TAG);
            welcomeOn.setAttribute("boolean", "true");
            root.appendChild(welcomeOn);

            Element byeOn = doc.createElement(BYE_MESSAGE_ON_TAG);
            byeOn.setAttribute("boolean", "true");
            root.appendChild(byeOn);

            Element maxText = doc.createElement(MAX_TEXT_LENGTH_TAG);
            maxText.setAttribute("int", ""+ Constants.MAX_MESSAGE_INT);
            root.appendChild(maxText);

            Element welcomeMsg = doc.createElement(WELCOME_MESSAGE_TAG);
            welcomeMsg.setAttribute("String", "Welcome _user_.");
            root.appendChild(welcomeMsg);

            Element byeMsg = doc.createElement(BYE_MESSAGE_TAG);
            byeMsg.setAttribute("String", "Bye _user_.");
            root.appendChild(byeMsg);

            // write the content into file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH_TO_XML_FILE));

            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    private void change_values(String tag, String value_name, String new_value) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(PATH_TO_XML_FILE);

            Node setting = doc.getElementsByTagName(tag).item(0);

            // update value
            NamedNodeMap attr = setting.getAttributes();
            Node nodeAttr = attr.getNamedItem(value_name);

            nodeAttr.setTextContent(new_value);

            // write the content into games file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH_TO_XML_FILE));

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String get_values(String tag, String value_name) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(PATH_TO_XML_FILE);

            Node setting = doc.getElementsByTagName(tag).item(0);

            // update value
            NamedNodeMap attr = setting.getAttributes();
            Node nodeAttr = attr.getNamedItem(value_name);

            return nodeAttr.getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // getters
    public boolean isBotOn() {
        String value = get_values(BOT_ON_TAG, "boolean");

        return value != null && value.equals("true");
    }

    public boolean areGamesOn() {
        String value = get_values(GAMES_ON_TAG, "boolean");

        return value != null && value.equals("true");
    }

    public boolean isAntiBotOn() {
        String value = get_values(ANTI_BOT_ON_TAG, "boolean");

        return value != null && value.equals("true");
    }

    public boolean needsPfp() {
        String value = get_values(NEED_PFP_TAG, "boolean");

        return value != null && value.equals("true");
    }

    public boolean isPyCommandOn() {
        String value = get_values(PY_COMMAND_ON_TAG, "boolean");

        return value != null && value.equals("true");
    }

    public boolean isWelcomeOn() {
        String value = get_values(WELCOME_MESSAGE_ON_TAG, "boolean");

        return value != null && value.equals("true");
    }

    public boolean isByeOn() {
        String value = get_values(BYE_MESSAGE_ON_TAG, "boolean");

        return value != null && value.equals("true");
    }

    public int getMaxTextLength() {
        String value = get_values(MAX_TEXT_LENGTH_TAG, "int");
        if(value == null){
            return Constants.MAX_MESSAGE_INT;
        }

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.MAX_MESSAGE_INT;
        }
    }

    public String getWelcomeMessage() {
        return get_values(WELCOME_MESSAGE_TAG, "String");
    }

    public String getWelcomeMessage(List<User> list) {
        StringBuilder accumulator = new StringBuilder();
        for (User aList : list) {
            accumulator.append(aList.getUserName());
            accumulator.append(" ");
        }

        String members = accumulator.toString().trim().replaceAll("\\s+", ", ").trim();

        String value = get_values(WELCOME_MESSAGE_TAG, "String");

        if (value != null) {
            return value.replaceAll("_user_", members);
        } else {
            return "Error getting welcome message";
        }
    }

    public String getByeMessage() {
        return get_values(BYE_MESSAGE_TAG, "String");
    }

    public String getByeMessage(Update update) {
        String username = update.getMessage().getLeftChatMember().getUserName();
        String value = get_values(BYE_MESSAGE_TAG, "String");

        if (value != null)
            return value.replaceAll("_user_", username);
        else {
            return "Error getting bye message";
        }
    }

    //setters
    public void setBotOn(boolean turnBotOn) {
        String bool = bts(turnBotOn);
        change_values(BOT_ON_TAG, "boolean", bool);
    }

    public void setGamesOn(boolean turnGamesOn) {
        String bool = bts(turnGamesOn);
        change_values(GAMES_ON_TAG, "boolean", bool);
    }

    public void setAntiBotOn(boolean turnAntiBotOn) {
        String bool = bts(turnAntiBotOn);
        change_values(ANTI_BOT_ON_TAG, "boolean", bool);
    }

    public void setPfpNeeded(boolean pfpNeeded) {
        String bool = bts(pfpNeeded);
        change_values(NEED_PFP_TAG, "boolean", bool);
    }

    public void setPyCommandOn(boolean turnPyOn) {
        String bool = bts(turnPyOn);
        change_values(PY_COMMAND_ON_TAG, "boolean", bool);
    }

    public void setMaxTextLength(int max) {
        if (max > Constants.MAX_MESSAGE_INT) {
            max = Constants.MAX_MESSAGE_INT;
        }

        change_values(MAX_TEXT_LENGTH_TAG, "int", ("" + max));
    }

    public void setWelcomeMessageOn(boolean welcomeOn) {
        String bool = bts(welcomeOn);
        change_values(WELCOME_MESSAGE_ON_TAG, "boolean", bool);
    }

    public void setByeMessageOn(boolean byeOn) {
        String bool = bts(byeOn);
        change_values(BYE_MESSAGE_ON_TAG, "boolean", bool);
    }

    public void setWelcomeMessage(String wm) {
        change_values(WELCOME_MESSAGE_TAG, "String", wm);
    }

    public void setByeMessage(String bm) {
        change_values(BYE_MESSAGE_TAG, "String", bm);
    }

    public String getSettings() {
        // return all settings
        String all = "Bot on: " + bts(isBotOn()) + "\n";
        all += "games on: " + bts(areGamesOn()) + "\n";
        all += "AntiBot on: " + bts(isAntiBotOn()) + "\n";
        all += "Pfp needed when joining: " + bts(needsPfp()) + "\n";
        all += "Py command on: " + bts(isPyCommandOn()) + "\n";
        all += "Max message length: " + getMaxTextLength() + "\n";
        all += "Welcome message on: " + bts(isWelcomeOn()) + "\n";
        all += "Bye message on: " + bts(isByeOn());

        return all.trim();
    }

    // boolean to str
    private String bts(boolean b) {
        return b ? "true" : "false";
    }
}