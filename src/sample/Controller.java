package sample;

import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

public class Controller {

    @FXML
    public TextField textFieldMessage;

    @FXML
    private WebView web;
    private StringBuffer cacheChat = new StringBuffer(); // заглушка-имитатор сервера

    @FXML
    public void initialize() {
        //  в javaFX достаточно много багов оказывается (что подтверждается интернетом).
        //  Например, webview не умеет смещать свой контент после автоматического отображения скроллбара
        //  (из за чего он перекрывает отображаемый контент) поэтому я решил его скрыть =(
        web.getChildrenUnmodifiable().addListener((ListChangeListener<Node>) change -> {
            Set<Node> deadSeaScrolls = web.lookupAll(".scroll-bar");
            for (Node scroll : deadSeaScrolls) {
                scroll.setVisible(false);
            }
        });

        web.getEngine().loadContent("<h3 style=\"text-align: center;\"> Здесь будет чат! </h3>");
    }

    // клик на кнопку
    public void sendMessageBtn() {
        sendMessage();
    }

    //отправка по ENTER
    public void actionTextArea() {
        sendMessage();
    }

    // отправка сообщения
    private void sendMessage(){
        if (textFieldMessage.getText().isEmpty()) return;
        addMessage(textFieldMessage.getText());
        getMessageFromServer();
        updateUI();
    }

    // обновляем UI
    private void updateUI(){
        textFieldMessage.clear();
        textFieldMessage.requestFocus();

        // отображаем сообщения
        web.getEngine().loadContent(cacheChat.toString());

        web.getEngine().getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {

                    // перематываем в конец чата после добавления сообщения
                    if (newState == State.SUCCEEDED) {
                        web.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
                    }
                });
    }

    // имитация сохранения сообщения в БД (вдруг соединение будет разорвано - будет отправлено потом значит)
    private void addMessage(String message) {
        cacheChat.append("<p style=\"text-align:right; " + "color: red;\">")
                .append(Date.from(Instant.now()).toString())
                .append("</p>");

        cacheChat.append("<p style=\"text-align:right; \">").append(message)
                .append("</p>");
        cacheChat.append("<hr>");
    }

    // заглушка - имитатор поолучения ответа с сервера
    private void getMessageFromServer(){
        cacheChat.append("<p style=\"text-align:left; " + "color: blue;\">")
                 .append(Date.from(Instant.now()).toString())
                 .append("</p>");

        cacheChat.append("<p style=\"text-align:left; \">").append("Any message from server!")
                 .append("</p>");
        cacheChat.append("<hr>");
    }
}