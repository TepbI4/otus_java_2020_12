public enum BankNoteRating {

    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private final int ratingValue;

    BankNoteRating(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public int getRatingValue() {
        return this.ratingValue;
    }
}
