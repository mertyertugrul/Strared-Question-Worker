package report;

public record Report(int solved, int unsolved, int total, int star1, int star2, int star3, int star4, int star5) {
    @Override
    public String toString() {
        return """
                | %8s | %8s | %8s | %8s | %8s | %8s | %8s | %8s |
                | %8d | %8d | %8d | %8d | %8d | %8d | %8d | %8d |
                """.formatted("solved", "unsolved", "total", "star5", "star4", "star3", "star2", "star1",
                solved, unsolved, total, star5, star4, star3, star2, star1);
    }

    public void printReport() {
        System.out.println(this);
    }
}
