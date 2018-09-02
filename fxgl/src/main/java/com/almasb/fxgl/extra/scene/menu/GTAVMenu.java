/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */
package com.almasb.fxgl.extra.scene.menu;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.menu.MenuType;
import com.almasb.fxgl.core.util.Supplier;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class GTAVMenu extends FXGLMenu {

    private VBox vbox = new VBox(50);

    private Node menuBody;

    public GTAVMenu(GameApplication app, MenuType type) {
        super(app, type);

        menuBody = type == MenuType.MAIN_MENU
                ? createMenuBodyMainMenu()
                : createMenuBodyGameMenu();

        vbox.getChildren().addAll(new Pane(), new Pane());
        vbox.setTranslateX(50);
        vbox.setTranslateY(50);

        contentRoot.setTranslateX(280);
        contentRoot.setTranslateY(130);

        menuRoot.getChildren().add(vbox);
        contentRoot.getChildren().add(EMPTY);

        vbox.getChildren().set(0, makeMenuBar());

        activeProperty().addListener((observable, wasActive, isActive) -> {
            if (!isActive) {
                // the scene is no longer active so reset everything
                // so that next time scene is active everything is loaded properly
                switchMenuTo(menuBody);
                switchMenuContentTo(EMPTY);
            }
        });
    }

    @Override
    protected Node createBackground(double width, double height) {
        return new Rectangle(app.getWidth(), app.getHeight(), Color.BROWN);
    }

    @Override
    protected Node createTitleView(String title) {
        Text titleView = FXGL.getUIFactory().newText(app.getSettings().getTitle(), 18);
        titleView.setTranslateY(30);
        return titleView;
    }

    @Override
    protected Node createVersionView(String version) {
        Text view = FXGL.getUIFactory().newText(version, 16);
        view.setTranslateX(app.getWidth() - view.getLayoutBounds().getWidth());
        view.setTranslateY(20);
        return view;
    }

    @Override
    protected Node createProfileView(String profileName) {
        Text view = FXGL.getUIFactory().newText(profileName, 24);
        view.setTranslateX(app.getWidth() - view.getLayoutBounds().getWidth());
        view.setTranslateY(50);
        return view;
    }

    @Override
    protected void switchMenuTo(Node menuBox) {
        vbox.getChildren().set(1, menuBox);
    }

    @Override
    protected void switchMenuContentTo(Node content) {
        contentRoot.getChildren().set(0, content);
    }

    private HBox makeMenuBar() {
        ToggleButton tb1 = new ToggleButton("MAIN MENU");
        ToggleButton tb2 = new ToggleButton("OPTIONS");
        ToggleButton tb3 = new ToggleButton("EXTRA");
        tb1.textProperty().bind(FXGL.localizedStringProperty("menu.mainMenu"));
        tb2.textProperty().bind(FXGL.localizedStringProperty("menu.options"));
        tb3.textProperty().bind(FXGL.localizedStringProperty("menu.extra"));
        tb1.setFont(FXGL.getUIFactory().newFont(18));
        tb2.setFont(FXGL.getUIFactory().newFont(18));
        tb3.setFont(FXGL.getUIFactory().newFont(18));

        ToggleGroup group = new ToggleGroup();
        tb1.setToggleGroup(group);
        tb2.setToggleGroup(group);
        tb3.setToggleGroup(group);

        tb1.setUserData(menuBody);
        tb2.setUserData(makeOptionsMenu());
        tb3.setUserData(makeExtraMenu());

        group.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            if (newToggle == null) {
                group.selectToggle(old);
                return;
            }
            switchMenuTo((Node)newToggle.getUserData());
        });
        group.selectToggle(tb1);

        HBox hbox = new HBox(10, tb1, tb2, tb3);
        hbox.setAlignment(Pos.TOP_CENTER);
        return hbox;
    }

    private VBox createMenuBodyMainMenu() {
        Button btnContinue = createActionButton(FXGL.localizedStringProperty("menu.continue"), this::fireContinue);
        Button btnNew = createActionButton(FXGL.localizedStringProperty("menu.newGame"), this::fireNewGame);
        Button btnLoad = createContentButton(FXGL.localizedStringProperty("menu.load"), this::createContentLoad);
        Button btnLogout = createActionButton(FXGL.localizedStringProperty("menu.logout"), this::fireLogout);
        Button btnExit = createActionButton(FXGL.localizedStringProperty("menu.exit"), this::fireExit);

        btnContinue.disableProperty().bind(listener.hasSavesProperty().not());

        return new VBox(10, btnContinue, btnNew, btnLoad, btnLogout, btnExit);
    }

    private VBox createMenuBodyGameMenu() {
        Button btnResume = createActionButton(FXGL.localizedStringProperty("menu.resume"), this::fireResume);
        Button btnSave = createActionButton(FXGL.localizedStringProperty("menu.save"), this::fireSave);
        Button btnLoad = createContentButton(FXGL.localizedStringProperty("menu.load"), this::createContentLoad);
        Button btnExit = createActionButton(FXGL.localizedStringProperty("menu.exit"), this::fireExitToMainMenu);

        return new VBox(10, btnResume, btnSave, btnLoad, btnExit);
    }

    private VBox makeOptionsMenu() {
        Button btnGameplay = createContentButton(FXGL.localizedStringProperty("menu.gameplay"), this::createContentGameplay);
        Button btnControls = createContentButton(FXGL.localizedStringProperty("menu.controls"), this::createContentControls);
        Button btnVideo = createContentButton(FXGL.localizedStringProperty("menu.video"), this::createContentVideo);
        Button btnAudio = createContentButton(FXGL.localizedStringProperty("menu.audio"), this::createContentAudio);

        return new VBox(10, btnGameplay, btnControls, btnVideo, btnAudio);
    }

    private VBox makeExtraMenu() {
        Button btnCredits = createContentButton(FXGL.localizedStringProperty("menu.credits"), this::createContentCredits);
        Button btnTrophies = createContentButton(FXGL.localizedStringProperty("menu.trophies"), this::createContentAchievements);

        return new VBox(10, btnCredits, btnTrophies);
    }

    /**
     * Creates a new button with given name that performs given action on click/press.
     *
     * @param name  button name
     * @param action button action
     * @return new button
     */
    protected final Button createActionButton(String name, Runnable action) {
        Button btn = FXGL.getUIFactory().newButton(name);
        btn.setOnAction(e -> action.run());
        return btn;
    }

    /**
     * Creates a new button with given name that performs given action on click/press.
     *
     * @param name  button name (with binding)
     * @param action button action
     * @return new button
     */
    protected final Button createActionButton(StringBinding name, Runnable action) {
        Button btn = FXGL.getUIFactory().newButton(name);
        btn.setOnAction(e -> action.run());
        return btn;
    }

    /**
     * Creates a new button with given name that sets given content on click/press.
     *
     * @param name  button name
     * @param contentSupplier content supplier
     * @return new button
     */
    @SuppressWarnings("unchecked")
    protected final Button createContentButton(String name, Supplier<MenuContent> contentSupplier) {
        Button btn = FXGL.getUIFactory().newButton(name);
        btn.setUserData(contentSupplier);
        btn.setOnAction(e -> switchMenuContentTo(((Supplier<MenuContent>)btn.getUserData()).get()));
        return btn;
    }

    /**
     * Creates a new button with given name that sets given content on click/press.
     *
     * @param name  button name (with binding)
     * @param contentSupplier content supplier
     * @return new button
     */
    @SuppressWarnings("unchecked")
    protected final Button createContentButton(StringBinding name, Supplier<MenuContent> contentSupplier) {
        Button btn = FXGL.getUIFactory().newButton(name);
        btn.setUserData(contentSupplier);
        btn.setOnAction(e -> switchMenuContentTo(((Supplier<MenuContent>)btn.getUserData()).get()));
        return btn;
    }
}
