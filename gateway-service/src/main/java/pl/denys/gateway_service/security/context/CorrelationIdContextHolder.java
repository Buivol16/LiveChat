package pl.denys.gateway_service.security.context;

public class CorrelationIdContextHolder {
    private static final ThreadLocal<String> correlationId = new ThreadLocal<>();

    public static void setCorrelationId(String correlationId) {
        CorrelationIdContextHolder.correlationId.set(correlationId);
    }

    public static String getCorrelationId() {
        return correlationId.get();
    }

    public static void clear() {
        correlationId.remove();
    }
}
