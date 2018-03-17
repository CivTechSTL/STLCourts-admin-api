package svc.security.jwt;

public interface TokenExtractor {
    public String extract(String payload);
}
