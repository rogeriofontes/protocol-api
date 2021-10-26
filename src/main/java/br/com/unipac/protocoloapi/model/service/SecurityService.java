package br.com.unipac.protocoloapi.model.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);

    String getCurrentLogin();

    void logoff();
}
