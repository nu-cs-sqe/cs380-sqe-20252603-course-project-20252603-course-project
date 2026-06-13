package domain;

public enum CardType {
    TEST_TYPE,
    EXPLODING_KITTEN,
    DEFUSE,
    ATTACK,
    SKIP,
    SEE_THE_FUTURE,
    SHUFFLE,
    NOPE,
    DRAW_FROM_BOTTOM,
    CAT_CARD_1,
    CAT_CARD_2,
    CAT_CARD_3,
    CAT_CARD_4,
    FAVOR;

    public boolean canHaveTarget(){
        return this == CAT_CARD_1 || this == CAT_CARD_2 || this == CAT_CARD_3 || this == CAT_CARD_4;
    }
}