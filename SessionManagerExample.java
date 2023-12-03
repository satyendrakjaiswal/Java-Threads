import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class UserSession {
    private String userId;

    public UserSession(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}

public class SessionManagerExample {

    private static final ThreadLocal<UserSession> userSessionThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Simulate multiple users logging in concurrently
        for (int i = 1; i <= 3; i++) {
            final int userId = i;
            executorService.submit(() -> {
                // Start user session for the current thread
                SessionManager.startSession("user" + userId);

                // Perform some operations with the user session
                UserSession currentUserSession = SessionManager.getCurrentSession();
                System.out.println("User " + currentUserSession.getUserId() + " is performing some actions.");

                // End user session for the current thread
                SessionManager.endSession();
            });
        }

        // Shut down the executor service
        executorService.shutdown();
    }

    static class SessionManager {
        public static void startSession(String userId) {
            userSessionThreadLocal.set(new UserSession(userId));
            System.out.println("Session started for user: " + userId);
        }

        public static UserSession getCurrentSession() {
            return userSessionThreadLocal.get();
        }

        public static void endSession() {
            UserSession currentUserSession = userSessionThreadLocal.get();
            System.out.println("Ending session for user: " + currentUserSession.getUserId());
            userSessionThreadLocal.remove();
        }
    }
}
