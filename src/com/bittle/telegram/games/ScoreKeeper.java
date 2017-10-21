package com.bittle.telegram.games;

import com.bittle.telegram.main.MainClass;
import org.telegram.telegrambots.api.objects.Update;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by oscartorres on 6/28/17
 */
// takes care of game scores
public class ScoreKeeper {

    private class UserAndScore {
        String user;
        String score;

        UserAndScore(Node u, Node s) {
            user = u.getTextContent();
            score = s.getTextContent();
        }

        @Override
        public String toString() {
            return "User: " + user + ", Score: " + score;
        }
    }

    private String pathToXMLFile = MainClass.directory.games_dir();

    private final String TYPE_TAG = "Type-Players";
    private final String SCRAMBLE_TAG = "Scramble-Players";
    private final String TABOO_TAG = "Guess-Players";
    private final String BRICK_TAG = "Brick-Players";
    private final String HANGMAN_TAG = "Hangman-Players";

    public ScoreKeeper(Update update) {
        if (update == null || update.getMessage() == null || update.getMessage().getChat() == null) {
            pathToXMLFile += "null_text_file.xml";
        } else
            pathToXMLFile += update.getMessage().getChat().getId() + ".xml";

        // create the file if it isn\'t created already
        File file = new File(pathToXMLFile);
        if (file.exists())
            return;

        clearGames();
    }

    private void clearGames() {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element gameRoot = doc.createElement("ScoreKeeper");
            doc.appendChild(gameRoot);

            // Players
            Element gameType = doc.createElement(TYPE_TAG);
            gameRoot.appendChild(gameType);

            Element gameType2 = doc.createElement(SCRAMBLE_TAG);
            gameRoot.appendChild(gameType2);

            Element gameType3 = doc.createElement(TABOO_TAG);
            gameRoot.appendChild(gameType3);

            Element gameType4 = doc.createElement(BRICK_TAG);
            gameRoot.appendChild(gameType4);

            Element gameType5 = doc.createElement(BRICK_TAG);
            gameRoot.appendChild(gameType5);

            // write the content into games file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathToXMLFile));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("New File Created!");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    private void addNewGame(String gameName) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(pathToXMLFile);

            // Get the type-game element by tag name directly
            Node root = doc.getElementsByTagName("TypingGames").item(0);

            // Players
            Element gameType = doc.createElement(gameName);
            root.appendChild(gameType);

            // write the content into games file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathToXMLFile));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPlayer(String username, String gameStyle) {
        if (!playerExists(username, gameStyle)) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(pathToXMLFile);

                Node gameType = doc.getElementsByTagName(gameStyle).item(0);

                // Player elements
                Element player = doc.createElement("Player");
                gameType.appendChild(player);

                // username elements
                Element user = doc.createElement("Username");
                user.appendChild(doc.createTextNode(username));
                player.appendChild(user);

                // score elements
                Element score = doc.createElement("Score");
                score.appendChild(doc.createTextNode("" + 1));
                player.appendChild(score);

                // write the content into games file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(pathToXMLFile));

                transformer.transform(source, result);

                System.out.println("Username " + username + " added to type game file!");

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("Username " + username + " already exists!");
        }
    }

    private boolean playerExists(final String username, String gameStyle) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(pathToXMLFile);

            // Get the type-game element by tag name directly
            Node gameType = doc.getElementsByTagName(gameStyle).item(0);

            //loop through players
            NodeList players = gameType.getChildNodes();

            boolean player_exists = false;
            for (int x = 0; x < players.getLength(); x++) {
                Node player = players.item(x);
                NodeList player_att = player.getChildNodes();
                for (int y = 0; y < player_att.getLength(); y++) {
                    Node att = player_att.item(y);
                    if (!att.getTextContent().trim().isEmpty()) {

                        if (att.getTextContent().equals(username) && att.getNodeName().equals("Username")) {
                            player_exists = true;
                        }
                    }
                }
            }
            return player_exists;
        } catch (Exception e) {
            return false;
        }
    }

    public void addToScore_scramble(String username, int increment) {
        addToScore(username, increment, SCRAMBLE_TAG);
    }

    public void addToScore_type(String username, int increment) {
        addToScore(username, increment, TYPE_TAG);
    }

    public void addToScore_taboo(String username, int increment) {
        addToScore(username, increment, TABOO_TAG);
    }

    public void addToScore_brick() {
        if (playerExists("BrickScoreKeeper", BRICK_TAG)) {
            addToScore("BrickScoreKeeper", 1, BRICK_TAG);
        } else {
            addPlayer("BrickScoreKeeper", BRICK_TAG);
        }
    }

    public void addToScore_hangman(String username, int increment) {
        addToScore(username, increment, HANGMAN_TAG);
    }

    private void addToScore(String username, int increment, String gameStyle) {
        if (playerExists(username, gameStyle)) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(pathToXMLFile);

                // Get the type-game element by tag name directly
                Node gameType = doc.getElementsByTagName(gameStyle).item(0);

                //loop through players
                NodeList players = gameType.getChildNodes();

                Node score = null;
                for (int x = 0; x < players.getLength(); x++) {
                    Node player = players.item(x);
                    NodeList player_att = player.getChildNodes();
                    Node user = player_att.item(0);
                    Node s = player_att.item(1);

                    if (user.getTextContent().equals(username)) {
                        score = s;
                        break;
                    }
                }

                try {
                    int number = Integer.parseInt(score.getTextContent()) + increment;
                    score.setTextContent(number + "");
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

                // write the content into games file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(pathToXMLFile));
                transformer.transform(source, result);

            } catch (ParserConfigurationException | TransformerException | SAXException | IOException pce) {
                pce.printStackTrace();
            }
        } else {
            addPlayer(username, gameStyle);
        }
    }

    public String getScores_scramble() {
        return getScores(SCRAMBLE_TAG, true);
    }

    public String getScores_type() {
        return getScores(TYPE_TAG, true);
    }

    public String getScores_taboo() {
        return getScores(TABOO_TAG, true);
    }

    public String getScores_hangman() {
        return getScores(HANGMAN_TAG, true);
    }

    public int getScores_brick() {
        String score = getScores(BRICK_TAG, false);
        if (score.contains("No")) {
            return 1;
        }
        try {
            return Integer.parseInt(score);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;

        }
    }

    public String getScores_all() {
        return (getScores_type() + "\n\n" + getScores_scramble() + "\n\n" + getScores_taboo()
                + "\n\n" + getScores_hangman()).trim();
    }

    private String getScores(String gameStyle, boolean need_whole_list) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(pathToXMLFile);

            // Get the type-game element by tag name directly
            Node gameType = doc.getElementsByTagName(gameStyle).item(0);
            ArrayList<UserAndScore> list = new ArrayList<>();
            //loop through players
            NodeList players = gameType.getChildNodes();
            for (int x = 0; x < players.getLength(); x++) {
                Node player = players.item(x);
                NodeList player_att = player.getChildNodes();
                Node user = player_att.item(0);
                Node score = player_att.item(1);

                list.add(new UserAndScore(user, score));
            }

            try {
                list.sort((o1, o2) -> {
                    Integer a = Integer.parseInt(o1.score);
                    Integer b = Integer.parseInt(o2.score);
                    return b.compareTo(a);
                });
            }catch (Exception e){
                e.printStackTrace();
            }

            // for cases like brick that only has one player
            if (!need_whole_list) {
                return list.get(0).score.trim();
            }

            StringBuilder text = new StringBuilder();
            for (UserAndScore u : list) {
                text.append(u.user);
                text.append(": ");
                text.append(u.score);
                text.append("\n");
            }

            String all;
            if (gameStyle.toLowerCase().contains("type"))
                all = "Type scores: \n";
            else if (gameStyle.toLowerCase().contains("scramble")) {
                all = "Scramble scores: \n";
            } else if (gameStyle.toLowerCase().contains("guess")) {
                all = "Taboo scores: \n";
            } else {
                all = "Hangman scores: \n";
            }

            if (list.isEmpty()) {
                return all + "No Scores.";
            } else
                return all + text.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            addNewGame(gameStyle);
            //addToScore("BrickScoreKeeper", 1,gameStyle);

            if (gameStyle.trim().toLowerCase().contains("brick")) {
                addToScore("BrickScoreKeeper", 1, gameStyle);
                return getScores_brick() + "";
            } else {
                return getScores(gameStyle, true);
            }
        }
    }
}