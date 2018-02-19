package com.minesweeper.view.textures;

/**
 * @author rawad
 *
 */
public enum TexturesEnum {

  BLANK("blank"), COVERED("covered"), FLAGGED("flagged"), FLAGGED_UNSURE(
      "flagged_unsure"), TRIGGERED_MINE("triggered_mine"), UNCOVERED_1("uncovered_1"), UNCOVERED_2(
          "uncovered_2"), UNCOVERED_3("uncovered_3"), UNCOVERED_4("uncovered_4"), UNCOVERED_5(
              "uncovered_5"), UNCOVERED_6("uncovered_6"), UNCOVERED_7(
                  "uncovered_7"), UNCOVERED_8("uncovered_8"), UNCOVERED_MINE("uncovered_mine");

  private final String name;

  /**
   * @param name
   */
  private TexturesEnum(String name) {
    this.name = name;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

}
