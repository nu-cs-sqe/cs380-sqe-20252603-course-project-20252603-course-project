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
    TACOCAT,
    CATERMELLON,
    BEARD_CAT,
    CAT_CARD_4,
    FAVOR,
    BOUNTY,
    BURY,
    DRAW_TWO,
    HAIL_MARY,
    HOT_POTATO,
    REVERSE,
    SELFISH_ROBIN_HOOD,
    SWAP_TOP_AND_BOTTOM;

    public boolean canHaveTarget(){
        return this == TACOCAT || this == CATERMELLON || this == BEARD_CAT || this == CAT_CARD_4
                || this == FAVOR;
    }
}