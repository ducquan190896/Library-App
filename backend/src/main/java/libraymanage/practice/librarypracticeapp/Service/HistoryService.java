package libraymanage.practice.librarypracticeapp.Service;

import java.util.List;

import libraymanage.practice.librarypracticeapp.Entity.History;

public interface HistoryService {
    List<History> getHistories();
    List<History> getHistoriesByAuthUser();
    List<History> getHistoriesByUser(Long userId);
    List<History> getHistoriesByBook(Long bookId);
    History getHistoryById(Long id);
    List<History> getHistoryByUserAndBook(long userId, Long bookId);
}
