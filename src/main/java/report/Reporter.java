package report;

import dao.QuestionDao;
import model.QuestionStar;

public class Reporter {
    private Reporter() {
    }
    private static final QuestionDao questionDao = new QuestionDao();

    public static Report createReport() {
        int unsolved = questionDao.getUnsolvedQuestionsCount();
        int solved = questionDao.getSolvedQuestionsCount();
        int total = unsolved + solved;
        int star1 = questionDao.getStarQuestionsCount(QuestionStar.ONE);
        int star2 = questionDao.getStarQuestionsCount(QuestionStar.TWO);
        int star3 = questionDao.getStarQuestionsCount(QuestionStar.THREE);
        int star4 = questionDao.getStarQuestionsCount(QuestionStar.FOUR);
        int star5 = questionDao.getStarQuestionsCount(QuestionStar.FIVE);

        return new Report(solved, unsolved, total, star1, star2, star3, star4, star5);
    }
}
